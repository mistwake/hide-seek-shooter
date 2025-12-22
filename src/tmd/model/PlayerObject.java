package tmd.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerObject extends GameObject {

    private int velX = 0; // utk nyimpen kecepatan gerak horizontal
    private int velY = 0; // utk nyimpen kecepatan gerak vertikal
    private static Image playerImage; // variabel untuk menyimpan gambar player

    // blok static buat load gambar pas awal
    static {
        try {
            // muat gambar player dari file assets
            playerImage = ImageIO.read(new File("assets/player.png"));
        } catch (IOException e) {
            System.err.println("error: gambar player ga ketemu");
            e.printStackTrace();
        }
    }

    public PlayerObject(int x, int y) {
        // kita panggil konstruktor induknya
        // set posisi awal x y, terus ukurannya 30x30, sama speednya 5
        super(x, y, 40, 65, 5);
    }

    @Override
    public void render(Graphics g) {
        // jika gambar berhasil dimuat, gunakan gambar
        if (playerImage != null) {
            g.drawImage(playerImage, x, y, width, height, null);
        }
        // jika gambar gagal dimuat, kembali ke kotak biru (fallback)
        else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }
    }

    // fungsi ini dipanggil terus menerus buat update posisi
    public void tick() {
        // update posisi x ditambah kecepatan saat ini
        x += velX;

        // batas kiri
        if (x < 0) x = 0;
        // batas kanan
        if (x > 1000 - width - 15) x = 1000 - width - 15;

        // update posisi y ditambah kecepatan saat ini
        y += velY;

        // batas atas
        if (y < 0) y = 0;
        // batas bawah
        if (y > 800 - height - 40) y = 800 - height - 40;
    }

    // ini setter buat ngubah kecepatan gerak dari input keyboard nanti
    public void setVelX(int velX) { this.velX = velX; }
    public void setVelY(int velY) { this.velY = velY; }
}