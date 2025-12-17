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
import javax.swing.JOptionPane; // import ini penting buat popup

public class GamePresenter {
    private GameWindow view;
    private PlayerObject player;
    private Timer gameTimer;
    private List<AlienObject> aliens;
    private Random random;
    private int currentScore = 0;
    private int currentMissed = 0;
    private int currentAmmo = 0;
    private String username;
    private TBenefitModel model;
    private List<BulletObject> bullets;
    private List<RockObject> rocks;

    private int waveNumber = 0;

    public GamePresenter(String username) {
        this.username = username;
        this.model = new TBenefitModel();

        view = new GameWindow();

        // player spawn di tengah (y=300)
        player = new PlayerObject(400, 300);

        aliens = new ArrayList<>();
        random = new Random();

        // inisialisasi list rocks
        rocks = new ArrayList<>();
        spawnRocks();

        view.setPlayer(player);
        view.setAliens(aliens);
        view.setRocks(rocks);

        bullets = new ArrayList<>();
        view.setBullets(bullets);

        setupInput();

        gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLoop();
            }
        });

        gameTimer.start();
        view.setVisible(true);
    }

    private void setupInput() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) player.setVelY(-5);
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) player.setVelY(5);
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.setVelX(-5);
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.setVelX(5);
                if (key == KeyEvent.VK_SPACE) {
                    shootBullet();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) player.setVelY(0);
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) player.setVelY(0);
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.setVelX(0);
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.setVelX(0);
            }
        });
    }

    private void shootBullet() {
        if (currentAmmo > 0) {
            currentAmmo--;
            // peluru kita (isEnemy = false) gerak ke bawah dari posisi player
            BulletObject b = new BulletObject(player.getX() + 12, player.getY() + 30, false);
            bullets.add(b);
        }
    }

    private void gameLoop() {
        // 1. update player
        player.tick();

        // 2. update peluru
        for (int i = 0; i < bullets.size(); i++) {
            BulletObject b = bullets.get(i);
            b.tick();

            // --- cek tabrakan peluru vs batu ---
            boolean hitRock = false;
            for (RockObject rock : rocks) {
                if (b.getBounds().intersects(rock.getBounds())) {

                    // fitur baru: kalau peluru alien kena batu, jadi amunisi kita
                    if (b.isEnemy()) {
                        currentAmmo++;
                    }

                    bullets.remove(i);
                    i--;
                    hitRock = true;
                    break;
                }
            }
            if (hitRock) continue; // lanjut ke peluru berikutnya

            // logika peluru musuh (gerak naik)
            if (b.isEnemy()) {
                // kena player -> game over
                if (b.getBounds().intersects(player.getBounds())) {
                    gameOver();
                    return;
                }

                // peluru musuh lolos ke atas (miss) -> tambah amunisi
                if (b.getY() < 0) {
                    bullets.remove(i);
                    i--;
                    currentMissed++;
                    currentAmmo++;
                }
            }
            // logika peluru player (gerak turun)
            else {
                // hapus kalau keluar layar bawah
                if (b.getY() > 600) {
                    bullets.remove(i);
                    i--;
                }
            }
        }

        // 3. update alien
        for (int i = 0; i < aliens.size(); i++) {
            AlienObject alien = aliens.get(i);
            alien.tick();

            // alien nembak acak ke atas
            if (random.nextDouble() < 0.01) {
                BulletObject bullet = new BulletObject(alien.getX() + 15, alien.getY(), true);
                bullets.add(bullet);
            }

            // cek collision: peluru kita vs alien
            for (int j = 0; j < bullets.size(); j++) {
                BulletObject b = bullets.get(j);

                // cuma peluru kita yang bisa bunuh alien
                if (!b.isEnemy() && b.getBounds().intersects(alien.getBounds())) {
                    aliens.remove(i);
                    i--;
                    bullets.remove(j);

                    currentScore += 10;
                    break;
                }
            }
        }

        // 4. update wave
        if (aliens.isEmpty()) {
            spawnWave();
        }

        // 5. update hud
        view.setGameStats(currentScore, currentMissed, currentAmmo);
        view.getCanvas().repaint();
    }

    private void spawnWave() {
        waveNumber++;
        int count = Math.min(3 + (waveNumber - 1), 10);

        for (int i = 0; i < count; i++) {
            spawnAlien();
        }
    }

    private void spawnRocks() {
        int targetRocks = 5;
        int attempts = 0;

        while (rocks.size() < targetRocks && attempts < 200) {

            int rockX = random.nextInt(720);
            int rockY = random.nextInt(350);

            RockObject candidateRock = new RockObject(rockX, rockY);

            boolean isSafe = true; // anggap aman dulu

            // cek jarak ke sesama batu (biar gak dempet)
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

            // kalau semua aman, baru masukin
            if (isSafe) {
                rocks.add(candidateRock);
            }

            attempts++;
        }
    }

    private void spawnAlien() {
        int randomY = random.nextInt(50) + 480;
        int randomX = random.nextInt(750);
        int randomSpeed = random.nextInt(2) + 1;

        AlienObject newAlien = new AlienObject(randomX, randomY, randomSpeed);
        aliens.add(newAlien);
    }

    // ini udah diubah biar ada tombol restart
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
            // kalo pilih restart, jalanin ulang gamenya
            new GamePresenter(username);
        } else {
            // kalo pilih menu, balik ke menu awal
            tmd.view.MenuWindow menu = new tmd.view.MenuWindow();
            new MenuPresenter(menu);
        }
    }
}