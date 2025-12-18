package tmd.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BulletObject extends GameObject {
    private boolean isEnemy; // penanda ini peluru musuh atau punya kita (player)

    public BulletObject(int x, int y, boolean isEnemy) {
        // set ukuran peluru kecil aja 10x10
        // kalo punya musuh speednya 5 kalo punya kita lebih cepet 10
        super(x, y, 10, 10, isEnemy ? 5 : 10);
        this.isEnemy = isEnemy;
    }

    @Override
    public void render(Graphics g) {
        // kita ubah jadi graphics2d biar bisa atur ketebalan garis
        Graphics2D g2d = (Graphics2D) g;

        // simpen settingan garis lama biar ga ngerusak gambar objek lain
        java.awt.Stroke oldStroke = g2d.getStroke();

        // logika gambar visualnya
        if (isEnemy) {
            // ini visual buat peluru alien warna merah

            // bikin efek cahaya merah transparan di sekelilingnya
            g2d.setColor(new Color(255, 0, 0, 80));
            g2d.fillOval(x - 4, y - 4, width + 8, height + 8);

            // bikin inti pelurunya cincin merah
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(2)); // garis tebal dikit
            g2d.drawOval(x, y, width, height);

        } else {
            // ini visual buat peluru player warna cyan

            // efek cahaya cyan transparan
            g2d.setColor(new Color(0, 255, 255, 80));
            g2d.fillOval(x - 4, y - 4, width + 8, height + 8);

            // inti peluru cincin cyan
            g2d.setColor(Color.CYAN);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, width, height);
        }

        // balikin settingan garis ke semula
        g2d.setStroke(oldStroke);
    }

    public void tick() {
        // logika gerak lurus vertikal
        if (isEnemy) {
            // peluru alien gerak ke atas karena alien muncul dari bawah
            y -= speed;
        } else {
            // peluru kita gerak ke bawah dari posisi player
            y += speed;
        }
    }

    // getter buat ngecek ini peluru siapa
    public boolean isEnemy() {
        return isEnemy;
    }
}