package tmd.model;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerObject extends GameObject {

    // variabel arah gerak (velocity)
    private int velX = 0;
    private int velY = 0;

    public PlayerObject(int x, int y) {
        // player ukuran 30x30, speed 5
        super(x, y, 30, 30, 5);
    }

    @Override
    public void render(Graphics g) {
        // gambar kotak biru sebagai player
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    // update posisi player
    public void tick() {
        x += velX;
        y += velY;

        // logic biar gak keluar layar kiri
        if (x < 0) x = 0;

        // logic biar gak keluar layar kanan (asumsi lebar 800)
        if (x > 800 - width - 15) x = 800 - width - 15;

        // logic biar gak keluar atas
        if (y < 0) y = 0;

        // logic biar gak keluar bawah (asumsi tinggi 600)
        if (y > 600 - height - 40) y = 600 - height - 40;
    }

    // setter buat set arah gerak
    public void setVelX(int velX) { this.velX = velX; }
    public void setVelY(int velY) { this.velY = velY; }
}