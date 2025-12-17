package tmd.model;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerObject extends GameObject {

    private int velX = 0;
    private int velY = 0;

    public PlayerObject(int x, int y) {
        super(x, y, 30, 30, 5);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    // KEMBALI KE METHOD LAMA (Tanpa List<RockObject>)
    public void tick() {
        // Gerak X
        x += velX;

        // Batas layar kiri/kanan
        if (x < 0) x = 0;
        if (x > 800 - width - 15) x = 800 - width - 15;

        // Gerak Y
        y += velY;

        // Batas layar atas/bawah
        if (y < 0) y = 0;
        if (y > 600 - height - 40) y = 600 - height - 40;
    }

    public void setVelX(int velX) { this.velX = velX; }
    public void setVelY(int velY) { this.velY = velY; }
}