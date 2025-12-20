package tmd.presenter;

import tmd.model.PlayerObject;
import tmd.view.GameWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import tmd.model.AlienObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tmd.model.TBenefitModel;
import tmd.model.BulletObject;
import tmd.model.RockObject;
import javax.swing.JOptionPane;

public class GamePresenter {
    private GameWindow view;
    private PlayerObject player;
    private Timer gameTimer;

    // list buat nampung objek objek game
    private List<AlienObject> aliens;
    private List<BulletObject> bullets;
    private List<RockObject> rocks;

    private Random random;
    private TBenefitModel model;

    // variabel status permainan sementara
    private int currentScore = 0;
    private int currentMissed = 0;
    private int currentAmmo = 0;
    private String username;

    private int waveNumber = 0;

    public GamePresenter(String username) {
        this.username = username;
        this.model = new TBenefitModel();

        // ambil sisa peluru dari database buat modal awal main
        // jadi peluru itu akumulasi dari game sebelumnya
        this.currentAmmo = model.getSisaPeluru(username);

        view = new GameWindow();

        // player spawn di tengah (koordinat disesuaikan dikit biar pas di canvas baru)
        player = new PlayerObject(500, 400);

        aliens = new ArrayList<>();
        random = new Random();

        // inisialisasi list rocks dan spawn rocks nya
        rocks = new ArrayList<>();
        spawnRocks();

        // kasih referensi objek ke view biar bisa digambar
        view.setPlayer(player);
        view.setAliens(aliens);
        view.setRocks(rocks);

        bullets = new ArrayList<>();
        view.setBullets(bullets);

        // siapin kontrol keyboard
        setupInput();

        // timer jalan tiap 16ms
        gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLoop();
            }
        });

        // mulai game
        gameTimer.start();
        view.setVisible(true);
    }

    private void setupInput() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                // kontrol gerak player pake wasd atau arrow
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) player.setVelY(-5);
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) player.setVelY(5);
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.setVelX(-5);
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.setVelX(5);

                // tombol 'f' buat nembak
                if (key == KeyEvent.VK_F) {
                    shootBullet();
                }

                // tombol 'space' buat berhenti dan kembali ke menu
                if (key == KeyEvent.VK_SPACE) {
                    gameTimer.stop();
                    view.dispose();
                    tmd.view.MenuWindow menu = new tmd.view.MenuWindow();
                    new MenuPresenter(menu);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                // pas tombol dilepas player berhenti gerak
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) player.setVelY(0);
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) player.setVelY(0);
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.setVelX(0);
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.setVelX(0);
            }
        });
    }

    private void shootBullet() {
        // cuma bisa nembak kalo ada peluru
        if (currentAmmo > 0) {
            currentAmmo--;
            // player cmn bisa nembak lurus ke bawah
            // Target X = posisi player, Target Y = posisi jauh di bawah (e.g. y + 1000)
            BulletObject b = new BulletObject(player.getX() + 12, player.getY() + 30,
                    player.getX() + 12, player.getY() + 1000, false);
            bullets.add(b);
        }
    }

    // loop utama game yang dipanggil terus menerus
    private void gameLoop() {
        // update posisi player
        player.tick();

        // loop update semua peluru
        for (int i = 0; i < bullets.size(); i++) {
            BulletObject b = bullets.get(i);
            b.tick();

            // cek tabrakan peluru sama batu
            boolean hitRock = false;
            for (RockObject rock : rocks) {
                if (b.getBounds().intersects(rock.getBounds())) {

                    // peluru alien miss ke batu, maka
                    if (b.isEnemy()) {
                        currentAmmo++; // inc kita dapat peluru tambahan
                    }

                    // hapus pelurunya
                    bullets.remove(i);
                    i--;
                    hitRock = true;
                    break;
                }
            }
            if (hitRock) continue; // lanjut ke peluru berikutnya

            // logika kalo peluru punya musuh
            if (b.isEnemy()) {
                // kalo kena player berarti game over
                if (b.getBounds().intersects(player.getBounds())) {
                    gameOver();
                    return;
                }

                // dan cek juga kalo keluar kiri kanan atas
                if (b.getY() < 0 || b.getY() > 800 || b.getX() < 0 || b.getX() > 1000) {
                    bullets.remove(i);
                    i--;
                    currentMissed++; // inc itung statistik meleset
                    currentAmmo++;   // inc kita dapet peluru tambahan dari musuh yang meleset
                }
            }
            // logika kalo peluru punya player
            else {
                // hapus kalo keluar layar bawah
                if (b.getY() > 800) {
                    bullets.remove(i);
                    i--;
                }
            }
        }

        // update semua alien
        for (int i = 0; i < aliens.size(); i++) {
            AlienObject alien = aliens.get(i);
            alien.tick();

            // alien nembak secara acak probabilitas kecil tiap frame
            if (random.nextDouble() < 0.01) {
                // alien nembak mengarah ke posisi player saat ini (DIAGONAL)
                // Kita oper koordinat player sebagai target
                BulletObject bullet = new BulletObject(alien.getX() + 15, alien.getY(),
                        player.getX(), player.getY(), true);
                bullets.add(bullet);
            }

            // cek tabrakan peluru player sama alien
            for (int j = 0; j < bullets.size(); j++) {
                BulletObject b = bullets.get(j);

                // cuma peluru player yang bisa hancurin alien
                if (!b.isEnemy() && b.getBounds().intersects(alien.getBounds())) {
                    aliens.remove(i); // hapus alien
                    i--;
                    bullets.remove(j); // hapus peluru

                    currentScore += 10; // nambah skor
                    break;
                }
            }
        }

        // spawn wave baru kalo alien abis
        if (aliens.isEmpty()) {
            spawnWave();
        }

        // update tampilan dan refresh gambar
        view.setGameStats(currentScore, currentMissed, currentAmmo);
        view.getCanvas().repaint();
    }

    // logika spawn alien per gelombang
    private void spawnWave() {
        waveNumber++;
        // alien makin banyak tiap wave maksimal 10
        int count = Math.min(3 + (waveNumber - 1), 10);

        for (int i = 0; i < count; i++) {
            spawnAlien();
        }
    }

    // inisialisasi batu pelindung di posisi acak
    private void spawnRocks() {
        int targetRocks = 5;
        int attempts = 0;

        // coba spawn batu sampe target terpenuhi atau batas percobaan abis
        while (rocks.size() < targetRocks && attempts < 200) {
            // area spawn batu
            int rockX = random.nextInt(900); // max width - margin
            int rockY = random.nextInt(500); // area sebaran batu

            RockObject candidateRock = new RockObject(rockX, rockY);
            boolean isSafe = true;

            // pastiin batu ga numpuk sama batu lain
            for (RockObject existingRock : rocks) {
                double distance = Math.sqrt(
                        Math.pow(candidateRock.getX() - existingRock.getX(), 2) +
                                Math.pow(candidateRock.getY() - existingRock.getY(), 2)
                );
                if (distance < 200) {
                    isSafe = false;
                    break;
                }
            }

            // pastiin batu ga nimpa posisi spawn player (500, 400)
            double distToPlayer = Math.sqrt(
                    Math.pow(candidateRock.getX() - 500, 2) +
                            Math.pow(candidateRock.getY() - 400, 2)
            );
            if (distToPlayer < 100) {
                isSafe = false;
            }

            // kalo posisi aman masukin ke list
            if (isSafe) {
                rocks.add(candidateRock);
            }
            attempts++;
        }
    }

    // spawn satu alien di posisi acak bawah
    private void spawnAlien() {
        // area spawn alien
        int randomY = random.nextInt(50) + 650; // area bawah (dekat 800)
        int randomX = random.nextInt(950);
        int fixedSpeed = 1;

        // player dimasukkan ke konstruktor alien biar bisa dikejar
        AlienObject newAlien = new AlienObject(randomX, randomY, fixedSpeed, player);
        aliens.add(newAlien);
    }

    // logika pas game over
    private void gameOver() {
        gameTimer.stop();
        // simpen skor dulu
        model.updatePlayerData(username, currentScore, currentMissed, currentAmmo);

        // bikin opsi tombol
        Object[] options = {"Restart", "Menu"};

        // tampilin popup
        int choice = JOptionPane.showOptionDialog(view,
                "Game Over! Skor: " + currentScore + "\nMau main lagi?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        view.dispose(); // tutup window lama

        if (choice == 0) {
            // kalo pilih restart jalanin ulang gamenya
            new GamePresenter(username);
        } else {
            // kalo pilih menu balik ke menu awal
            tmd.view.MenuWindow menu = new tmd.view.MenuWindow();
            new MenuPresenter(menu);
        }
    }
}