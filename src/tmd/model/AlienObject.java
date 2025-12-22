package tmd.model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AlienObject extends GameObject {

    private static Image alienImage;
    private PlayerObject targetPlayer;

    static {
        try {
            alienImage = ImageIO.read(new File("assets/alien.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AlienObject(int x, int y, int speed, PlayerObject targetPlayer) {
        super(x, y, 85, 60, speed);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void render(Graphics g) {
        if (alienImage != null) {
            g.drawImage(alienImage, x, y, width, height, null);
        } else {
            g.setColor(java.awt.Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public void tick() {
        if (targetPlayer != null) {
            // membandingkan posisi, lalu gerak mendekat

            // cek Sumbu X
            // kalau player ada di sebelah kanan alien, alien jalan ke kanan
            if (targetPlayer.getX() > x) {
                x += speed;
            }
            // kalau player ada di sebelah kiri alien, alien jalan ke kiri
            else if (targetPlayer.getX() < x) {
                x -= speed;
            }

            // cek Sumbu Y
            // kalau player ada di bawah alien, alien jalan ke bawah
            if (targetPlayer.getY() > y) {
                y += speed;
            }
            // kalau player ada di atas alien, alien jalan ke atas
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