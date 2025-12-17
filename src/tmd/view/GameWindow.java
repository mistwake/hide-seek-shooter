package tmd.view;

import tmd.model.PlayerObject;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import tmd.model.AlienObject;
import tmd.model.BulletObject;

public class GameWindow extends JFrame {
    // ini kanvas khusus buat gambar game
    private GameCanvas canvas;
    // kita butuh akses ke player buat digambar
    private PlayerObject player;
    //daftar alien
    private List<AlienObject> aliens;
    private List<BulletObject> bullets;

    // Variabel buat nampung info skor
    private int score = 0;
    private int missed = 0;
    private int ammo = 0;

    public GameWindow() {
        // settingan window game
        setTitle("Hide and Seek Gameplay");
        setSize(800, 600); // ukuran area main
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // biar gak bisa diresize user
        setLocationRelativeTo(null);

        // pasang kanvas ke window
        canvas = new GameCanvas();
        add(canvas);

        // set visible true nya nanti dipanggil sama presenter
    }

    // method buat oper data player dari presenter ke sini
    public void setPlayer(PlayerObject player) {
        this.player = player;
    }

    // method buat terima daftar alien dari presenter
    public void setAliens(List<AlienObject> aliens) {
        this.aliens = aliens;
    }

    // setter buat bullets
    public void setBullets(List<BulletObject> bullets) {
        this.bullets = bullets;
    }

    // getter canvas biar bisa direpaint sama presenter
    public JPanel getCanvas() {
        return canvas;
    }

    // method buat update info skor dari presenter
    public void setGameStats(int score, int missed, int ammo) {
        this.score = score;
        this.missed = missed;
        this.ammo = ammo;
    }

    // class dalam (inner class) buat area gambar
    class GameCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // warnain background jadi putih
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            // kalau player udah ada, gambar playernya
            if (player != null) {
                player.render(g);
            }

            // gambar semua alien yang ada di list
            if (aliens != null) {
                // pakai loop buat gambar satu-satu
                for (int i = 0; i < aliens.size(); i++) {
                    AlienObject alien = aliens.get(i);
                    alien.render(g);
                }
            }

            // gambar semua peluru
            if (bullets != null) {
                for (int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).render(g);
                }
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 14));

            // Gambar kotak info di pojok kiri atas
            g.drawString("Skor: " + score, 20, 30);
            g.drawString("Meleset: " + missed, 20, 50);
            g.drawString("Peluru: " + ammo, 20, 70);
        }
    }
}