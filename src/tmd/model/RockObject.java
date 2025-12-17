package tmd.model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RockObject extends GameObject {

    // Variabel static untuk menyimpan gambar di memori (cuma 1 untuk semua batu)
    private static Image rockImage;

    // Blok static ini jalan otomatis HANYA SEKALI saat program pertama kali butuh batu
    static {
        try {
            // Mencoba membaca file "rock.png" dari folder project root
            rockImage = ImageIO.read(new File("rock.png"));
        } catch (IOException e) {
            System.err.println("Error: Gambar batu tidak ditemukan! Pastikan rock.png ada di root folder project.");
            e.printStackTrace();
            // Kalau gambar gagal dimuat, program mungkin akan error nanti pas nge-render.
            // Pastikan filenya bener ya.
        }
    }

    public RockObject(int x, int y) {
        // UBAH DISINI: Ganti 50, 50 jadi 80, 80 biar lebih gede
        super(x, y, 130, 130, 0);
    }

    @Override
    public void render(Graphics g) {
        // Jika gambar berhasil dimuat, gambar batunya
        if (rockImage != null) {
            // g.drawImage menggambar image di posisi x, y dengan ukuran width, height
            g.drawImage(rockImage, x, y, width, height, null);
        }
        // (Opsional) Jika gambar gagal muat, gambar kotak abu-abu sebagai cadangan
        else {
            g.setColor(java.awt.Color.GRAY);
            g.fillRect(x, y, width, height);
        }
    }

    // Batu tidak bergerak, method tick kosong saja
    public void tick() {
        // Do nothing
    }
}