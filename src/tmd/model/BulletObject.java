package tmd.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BulletObject extends GameObject {
    private boolean isEnemy;

    // variabel double dipakai supaya gerakan peluru bisa miring
    private double velX;
    private double velY;
    private double exactX, exactY; // posisi hitungan yang presisi (bisa menampung nilai koma)

    public BulletObject(int x, int y, int targetX, int targetY, boolean isEnemy) {
        // panggil pengaturan dasar dari induk (gameobject). kecepatan musuh 5, pemain 10.
        super(x, y, 10, 10, isEnemy ? 5 : 10);
        this.isEnemy = isEnemy;

        // simpan posisi awal ke dalam variabel presisi
        this.exactX = x;
        this.exactY = y;

        // logika membidik target (menggunakan perbandingan jarak)

        // hitung selisih jarak mendatar (dx) dan tegak (dy) menuju target
        double dx = targetX - x;
        double dy = targetY - y;

        // hitung panjang lintasan miring (diagonal) menggunakan rumus pythagoras
        double distance = Math.sqrt(dx * dx + dy * dy);

        // hitung kecepatan gerak untuk setiap langkah
        // caranya jarak per sumbu dibagi jarak total, lalu dikalikan dengan kecepatan (speed)
        if (distance != 0) {
            this.velX = (dx / distance) * speed;
            this.velY = (dy / distance) * speed;
        } else {
            // jaga-jaga jika target berada tepat di posisi yang sama dengan peluru (jarak 0)
            this.velX = 0;
            this.velY = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        java.awt.Stroke oldStroke = g2d.getStroke();

        // bagian menggambar visual peluru
        if (isEnemy) {
            // jika ini peluru alien (warna merah)

            // gambar efek cahaya pudar/transparan di sekitar peluru
            g2d.setColor(new Color(255, 0, 0, 80));
            g2d.fillOval(x - 4, y - 4, width + 8, height + 8);

            // gambar lingkaran inti peluru yang tegas
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, width, height);
        } else {
            // jika ini peluru pemain (warna biru muda/cyan)

            // gambar efek cahaya pudar/transparan di sekitar peluru
            g2d.setColor(new Color(0, 255, 255, 80));
            g2d.fillOval(x - 4, y - 4, width + 8, height + 8);

            // gambar lingkaran inti peluru yang tegas
            g2d.setColor(Color.CYAN);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, width, height);
        }

        g2d.setStroke(oldStroke);
    }

    public void tick() {
        // perbarui posisi pada variabel presisi
        exactX += velX;
        exactY += velY;

        // konversi kembali ke bilangan bulat (integer) agar bisa digambar di layar
        x = (int) exactX;
        y = (int) exactY;
    }

    public boolean isEnemy() {
        return isEnemy;
    }
}