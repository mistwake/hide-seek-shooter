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
import javax.imageio.ImageIO;

public class GameWindow extends JFrame {
    private GameCanvas canvas; // area buat ngegambar semua objek game

    // referensi ke objek objek game yang mau digambar
    private PlayerObject player;
    private List<AlienObject> aliens;
    private List<BulletObject> bullets;
    private List<RockObject> rocks;

    // variabel buat nyimpen status game buat ditampilin di layar
    private int score = 0;
    private int missed = 0;
    private int ammo = 0;

    private Image bgImage;

    public GameWindow() {
        setTitle("Hide and Seek Gameplay");
        // pakai window ukuran 1000x800
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // set ke false biar gabisa diresize
        setLocationRelativeTo(null);

        // muat gambar background
        try {
            // kredit aset: background space diambil canva jg
            bgImage = ImageIO.read(new File("assets/background.png"));
        } catch (IOException e) {
            System.out.println("gagal memuat background.png");
            e.printStackTrace();
        }

        // inisialisasi kanvas dan tempel ke frame
        canvas = new GameCanvas();
        add(canvas);
    }

    // setter buat nerima data objek dari presenter
    public void setPlayer(PlayerObject player) { this.player = player; }
    public void setAliens(List<AlienObject> aliens) { this.aliens = aliens; }
    public void setBullets(List<BulletObject> bullets) { this.bullets = bullets; }
    public void setRocks(List<RockObject> rocks) { this.rocks = rocks; }

    // akses ke canvas buat repaint nanti
    public JPanel getCanvas() { return canvas; }

    // update status skor dan peluru buat ui
    public void setGameStats(int score, int missed, int ammo) {
        this.score = score;
        this.missed = missed;
        this.ammo = ammo;
    }

    // kelas dalam buat ngatur penggambaran kustom
    class GameCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 1. gambar background dulu paling bawah
            if (bgImage != null) {
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
            } else {
                // kalo gambar ga ada kasih warna putih poloss
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            // 2. gambar objek objek game di atas background
            // gambar player
            if (player != null) player.render(g);

            // gambar alien alien
            if (aliens != null) {
                for (int i = 0; i < aliens.size(); i++) {
                    aliens.get(i).render(g);
                }
            }

            // gambar batu pelindung
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

            // 3. gambar tulisan status di pojok kiri atas
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Skor: " + score, 20, 30);
            g.drawString("Meleset: " + missed, 20, 50);
            g.drawString("Peluru: " + ammo, 20, 70);
        }
    }
}