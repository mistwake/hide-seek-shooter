package tmd.model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AlienObject extends GameObject {

    private int velX; // arah gerak alien ke kiri atau kanan
    private static Image alienImage; // variabel static biar gambarnya dimuat sekali aja

    // blok static ini jalan otomatis pas program pertama kali butuh alien
    static {
        try {
            // baca file gambar alien dari folder proyek
            // kredit aset: dari canva bu hehe
            alienImage = ImageIO.read(new File("alien.png"));
        } catch (IOException e) {
            System.err.println("error: gambar alien.png ga ketemu nih");
            e.printStackTrace();
        }
    }

    public AlienObject(int x, int y, int speed) {
        // panggil konstruktor induk, set ukuran alien agak gedean 85x60
        super(x, y, 85, 60, speed);
        // kita acak dia gerak ke kiri atau kanan pas muncul
        this.velX = Math.random() > 0.5 ? speed : -speed;
    }

    @Override
    public void render(Graphics g) {
        // kalo gambarnya berhasil dimuat ya kita gambar
        if (alienImage != null) {
            g.drawImage(alienImage, x, y, width, height, null);
        }
        // kalo gagal muat gambar kita kasih kotak merah, biar tau error
        else {
            g.setColor(java.awt.Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    // logika update alien tiap frame
    public void tick() {
        x += velX;

        // kalo nabrak tembok kiri dia mantul ke kanan
        if (x < 0) {
            x = 0;
            velX = -velX;
        }

        // kalo nabrak tembok kanan dia mantul ke kiri
        if (x > 800 - width) {
            x = 800 - width;
            velX = -velX;
        }
    }
}