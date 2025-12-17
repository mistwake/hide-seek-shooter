package tmd.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AlienObject extends GameObject {

    private int velX;

    private static Image alienImage;

    // Blok static ini jalan otomatis HANYA SEKALI saat program pertama kali butuh alien
    static {
        try {
            // Mencoba membaca file "alien.png" dari folder project root
            // PASTIKAN NAMA FILENYA SESUAI DENGAN PUNYAMU
            alienImage = ImageIO.read(new File("alien.png"));
        } catch (IOException e) {
            System.err.println("Error: Gambar alien.png tidak ditemukan! Cek folder project.");
            e.printStackTrace();
        }
    }

    public AlienObject(int x, int y, int speed) {
        super(x, y, 85, 60, speed);
        // gerak random ke kiri atau kanan
        this.velX = Math.random() > 0.5 ? speed : -speed;
    }

    @Override
    public void render(Graphics g) {
        // Jika gambar berhasil dimuat, gambar aliennya
        if (alienImage != null) {
            // g.drawImage menggambar image di posisi x, y dengan ukuran width, height
            g.drawImage(alienImage, x, y, width, height, null);
        }
        // (Cadangan) Jika gambar gagal muat, gambar kotak merah biar keliatan error
        else {
            g.setColor(java.awt.Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public void tick() {
        x += velX;

        // pantul tembok kiri
        if (x < 0) {
            x = 0;
            velX = -velX;
        }

        // pantul tembok kanan
        if (x > 800 - width) {
            x = 800 - width;
            velX = -velX;
        }
    }
}