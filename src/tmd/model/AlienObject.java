package tmd.model;

import java.awt.Color;
import java.awt.Graphics;

public class AlienObject extends GameObject {

    public AlienObject(int x, int y, int speed) {
        // panggil konstruktor induk (gameobject)
        // ukuran alien 30x30
        super(x, y, 30, 30, speed);
    }

    @Override
    public void render(Graphics g) {
        // kita gambar alien warna merah bulat
        g.setColor(Color.RED);
        g.fillOval(x, y, width, height);
    }

    // method gerak otomatis
    public void tick() {
        // y berkurang artinya bergerak ke atas
        y -= speed;
    }
}