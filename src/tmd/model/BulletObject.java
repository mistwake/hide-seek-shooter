package tmd.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BulletObject extends GameObject {
    private boolean isEnemy;

    public BulletObject(int x, int y, boolean isEnemy) {
        // ukuran 10x10 biar buletnya pas
        // speed alien 5 (pelan), player 10 (cepet)
        super(x, y, 10, 10, isEnemy ? 5 : 10);
        this.isEnemy = isEnemy;
    }

    @Override
    public void render(Graphics g) {
        // ubah ke graphics2d dulu biar bisa atur ketebalan garis
        Graphics2D g2d = (Graphics2D) g;

        // simpen settingan garis lama biar ga ngerusak gambar lain
        java.awt.Stroke oldStroke = g2d.getStroke();

        // logika gambar visualnya

        if (isEnemy) {
            // tipe peluru alien (cincin merah)

            // 1. bikin efek glow di belakang
            // warna merah transparan (alpha 80)
            g2d.setColor(new Color(255, 0, 0, 80));
            // gambar oval transparan yg lebih gede dikit dari pelurunya
            g2d.fillOval(x - 4, y - 4, width + 8, height + 8);

            // 2. bikin inti peluru (cincin kosong)
            g2d.setColor(Color.RED);
            // tebelin garisnya jadi 2 px biar jelas
            g2d.setStroke(new BasicStroke(2));
            // pake drawOval biar tengahnya bolong
            g2d.drawOval(x, y, width, height);

        } else {
            // tipe peluru player (cincin cyan)

            // 1. bikin efek glow
            // warna cyan transparan (alpha 80)
            g2d.setColor(new Color(0, 255, 255, 80));
            g2d.fillOval(x - 4, y - 4, width + 8, height + 8);

            // 2. bikin inti peluru
            g2d.setColor(Color.CYAN);
            g2d.setStroke(new BasicStroke(2));
            // gambar garis lingkarannya doang
            g2d.drawOval(x, y, width, height);
        }

        // balikin settingan garis ke asal
        g2d.setStroke(oldStroke);
    }

    public void tick() {
        // logika gerak lurus vertikal
        if (isEnemy) {
            // alien di bawah nembak ke atas
            y -= speed;
        } else {
            // player di tengah nembak ke bawah
            y += speed;
        }
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}