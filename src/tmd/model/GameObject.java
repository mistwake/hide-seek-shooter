package tmd.model;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    // posisi dan ukuran
    protected int x, y;
    protected int width, height;

    // kecepatan gerak
    protected int speed;

    public GameObject(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // method render wajib ada biar objek bisa digambar
    public abstract void render(Graphics g);

    // ini buat deteksi tabrakan (collision) nanti
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // getter setter
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}