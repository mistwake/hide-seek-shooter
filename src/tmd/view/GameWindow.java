package tmd.view;

import tmd.model.PlayerObject;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import tmd.model.AlienObject;
import tmd.model.BulletObject;
import tmd.model.RockObject;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO; // import buat baca gambar

public class GameWindow extends JFrame {
    private GameCanvas canvas;
    private PlayerObject player;
    private List<AlienObject> aliens;
    private List<BulletObject> bullets;
    private List<RockObject> rocks;

    // variabel skor
    private int score = 0;
    private int missed = 0;
    private int ammo = 0;

    // variabel gambar background
    private Image bgImage;

    public GameWindow() {
        setTitle("Hide and Seek Gameplay");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // load gambar background
        try {
            bgImage = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            System.out.println("background.png ga ketemu, nanti layarnya putih polos.");
            e.printStackTrace();
        }

        canvas = new GameCanvas();
        add(canvas);
    }

    // setter
    public void setPlayer(PlayerObject player) { this.player = player; }
    public void setAliens(List<AlienObject> aliens) { this.aliens = aliens; }
    public void setBullets(List<BulletObject> bullets) { this.bullets = bullets; }
    public void setRocks(List<RockObject> rocks) { this.rocks = rocks; }

    public JPanel getCanvas() { return canvas; }

    public void setGameStats(int score, int missed, int ammo) {
        this.score = score;
        this.missed = missed;
        this.ammo = ammo;
    }

    class GameCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // bg
            if (bgImage != null) {
                // gambar image seukuran layar penuh
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
            } else {
                // fallback warna putih kalo gambar ga ketemu
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            // 2. baru gambar objek lain di atasnya

            // gambar player
            if (player != null) {
                player.render(g);
            }

            // gambar alien
            if (aliens != null) {
                for (int i = 0; i < aliens.size(); i++) {
                    aliens.get(i).render(g);
                }
            }

            // gambar batu
            if (rocks != null) {
                for (RockObject rock : rocks) {
                    rock.render(g);
                }
            }

            // gambar peluru
            if (bullets != null) {
                for (int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).render(g);
                }
            }

            // hud (skor)
            // ganti warna font biar kebaca (misal kuning/putih)
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Skor: " + score, 20, 30);
            g.drawString("Meleset: " + missed, 20, 50);
            g.drawString("Peluru: " + ammo, 20, 70);
        }
    }
}