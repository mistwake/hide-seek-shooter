package tmd.model;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerObject extends GameObject {

    private int velX = 0; // utk nyimpen kecepatan gerak horizontal
    private int velY = 0; // utk nyimpen kecepatan gerak vertikal

    public PlayerObject(int x, int y) {
        // kita panggil konstruktor induknya
        // set posisi awal x y, terus ukurannya 30x30, sama speednya 5
        super(x, y, 30, 30, 5);
    }

    @Override
    public void render(Graphics g) {
        // warnain player jadi biru
        g.setColor(Color.BLUE);
        // gambar kotak sesuai posisi dan ukuran
        g.fillRect(x, y, width, height);
    }

    // fungsi ini dipanggil terus menerus buat update posisi
    public void tick() {
        // update posisi x ditambah kecepatan saat ini
        x += velX;

        // kita cek biar player ga bablas keluar layar kiri
        if (x < 0) x = 0;
        // REVISI: Batas kanan diupdate jadi 1000 (sesuai canvas baru)
        if (x > 1000 - width - 15) x = 1000 - width - 15;

        // update posisi y ditambah kecepatan saat ini
        y += velY;

        // cek biar ga nembus atap
        if (y < 0) y = 0;
        // REVISI: Batas bawah diupdate jadi 800 (sesuai canvas baru)
        if (y > 800 - height - 40) y = 800 - height - 40;
    }

    // ini setter buat ngubah kecepatan gerak dari input keyboard nanti
    public void setVelX(int velX) { this.velX = velX; }
    public void setVelY(int velY) { this.velY = velY; }
}