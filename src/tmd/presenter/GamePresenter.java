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

    public GamePresenter(String username) {
        this.username = username; // simpan username
        this.model = new TBenefitModel(); // siapin koneksi db

        // bikin window game baru
        view = new GameWindow();

        // bikin player di posisi tengah (400, 300)
        player = new PlayerObject(400, 300);

        // inisialisasi list alien
        aliens = new ArrayList<>();
        random = new Random();

        // kasih tau view soal player ini
        view.setPlayer(player);
        // kasih tau view soal list alien ini
        view.setAliens(aliens);

        // inisialisasi list bullets
        bullets = new java.util.ArrayList<>();
        view.setBullets(bullets); // kasih tau view

        // setup kontrol keyboard
        setupInput();

        // mulai game loop (60 fps)
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
        // pasang pendengar keyboard di window
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                // kalau tombol ditekan, set kecepatan
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

                // kalau tombol dilepas, set kecepatan jadi 0 (berhenti)
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) player.setVelY(0);
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) player.setVelY(0);
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.setVelX(0);
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.setVelX(0);
            }
        });
    }

    private void shootBullet() {
        // cuma bisa nembak kalau punya peluru
        if (currentAmmo > 0) {
            currentAmmo--; // kurangi peluru

            // munculin peluru di tengah posisi player
            // player.getX() + 12 biar pas di tengah (karena lebar player 30)
            BulletObject b = new BulletObject(player.getX() + 12, player.getY());
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

            // hapus peluru kalau udah lewat layar atas
            if (b.getY() < 0) {
                bullets.remove(i);
                i--;
            }
        }

        // 3. update alien
        for (int i = 0; i < aliens.size(); i++) {
            AlienObject alien = aliens.get(i);
            alien.tick();

            boolean alienMati = false; // penanda

            // 1. Cek Kena Player
            if (player.getBounds().intersects(alien.getBounds())) {
                gameOver();
                return;
            }

            // 2. Cek Kena Peluru
            for (int j = 0; j < bullets.size(); j++) {
                BulletObject b = bullets.get(j);
                if (alien.getBounds().intersects(b.getBounds())) {
                    aliens.remove(i);
                    i--;
                    bullets.remove(j);

                    currentScore += 10; // nambah skor
                    alienMati = true; // tandai mati
                    break;
                }
            }

            if (alienMati) continue; // kalau udah mati, lanjut ke alien berikutnya

            // 3. Cek Lewat Layar (Meleset)
            if (alien.getY() < -30) {
                aliens.remove(i);
                i--;
                currentMissed++;
                if (currentMissed % 5 == 0) {
                    currentAmmo++;
                }
            }
        }

        // 4. spawn alien
        if (random.nextDouble() < 0.02) {
            spawnAlien();
        }

        // 5. Update Tampilan Skor di View
        view.setGameStats(currentScore, currentMissed, currentAmmo);

        // 6. gambar ulang
        view.getCanvas().repaint();
    }

    private void spawnAlien() {
        // muncul di posisi X acak (antara 0 sampai 750)
        int randomX = random.nextInt(750);
        // muncul di paling bawah layar (Y = 600)
        // speed acak antara 1 sampai 3 biar variasi
        int randomSpeed = random.nextInt(3) + 1;

        AlienObject newAlien = new AlienObject(randomX, 600, randomSpeed);
        aliens.add(newAlien);
    }

    private void gameOver() {
        // 1. matiin timer biar game berhenti
        gameTimer.stop();

        // update data si player ke database sebelum keluar
        model.updatePlayerData(username, currentScore, currentMissed, currentAmmo);

        // 2. kasih pesan kalah
        javax.swing.JOptionPane.showMessageDialog(view, "yah game over! kena alien.");

        // 3. tutup window game
        view.dispose();

        // 4. balik lagi ke menu awal
        // kita bikin window baru dan presenter baru
        tmd.view.MenuWindow menu = new tmd.view.MenuWindow();
        new MenuPresenter(menu);
    }
}