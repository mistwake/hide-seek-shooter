package tmd.model;

import java.awt.Graphics;
import java.awt.Rectangle;

// kelas abstrak induk buat semua objek di game
public abstract class GameObject {
    // variable posisi dan ukuran
    protected int x, y;
    protected int width, height;

    // variable kecepatan gerak
    protected int speed;

    // konstruktor untuk menginisialisasi properti dasar setiap objek game
    public GameObject(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // method render wajib ada biar objek bisa digambar
    public abstract void render(Graphics g);

    // ini buat deteksi tabrakan (collision)
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