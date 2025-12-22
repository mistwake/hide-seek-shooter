package tmd.model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AlienObject extends GameObject {
    // variabel untuk menyimpan gambar alien
    private static Image alienImage;

    // referensi ke player yang akan dikejar alien
    private PlayerObject targetPlayer;

    // blok static buat load gambar pas awal
    static {
        try {
            // muat gambar alien dari file assets
            alienImage = ImageIO.read(new File("assets/alien.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AlienObject(int x, int y, int speed, PlayerObject targetPlayer) {
        // panggil konstruktor parent
        super(x, y, 85, 60, speed);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void render(Graphics g) {
        // jika gambar berhasil dimuat, pakai gambar tsb
        if (alienImage != null) {
            g.drawImage(alienImage, x, y, width, height, null);
        }
        // jika gambar gagal dimuat, tampilkan kotak merah
        else {
            g.setColor(java.awt.Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public void tick() {
        // cek apakah target player ada
        if (targetPlayer != null) {

            // pergerakan alien ke arah player

            // cek sumbu x
            // jika player berada di kanan alien, alien bergerak ke kanan
            if (targetPlayer.getX() > x) {
                x += speed;
            }
            // jika player berada di kiri alien, alien bergerak ke kiri
            else if (targetPlayer.getX() < x) {
                x -= speed;
            }

            // cek sumbu y
            // jika player berada di bawah alien, alien bergerak ke bawah
            if (targetPlayer.getY() > y) {
                y += speed;
            }
            // jika player berada di atas alien, alien bergerak ke atas
            else if (targetPlayer.getY() < y) {
                y -= speed;
            }
        }

        // cek tabrakan dinding (biar ga keluar layar)
        if (x < 0) x = 0;
        if (x > 1000 - width) x = 1000 - width;
        if (y < 0) y = 0;
        if (y > 800 - height) y = 800 - height;
    }
}