package tmd.model;

import java.awt.Color;
import java.awt.Graphics;

public class BulletObject extends GameObject {

    public BulletObject(int x, int y) {
        // ukuran peluru kecil aja 5x10, speed lumayan cepet (10)
        super(x, y, 5, 10, 10);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK); // warna peluru hitam
        g.fillRect(x, y, width, height);
    }

    public void tick() {
        // gerak lurus ke atas
        y -= speed;
    }
}