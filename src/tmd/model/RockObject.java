package tmd.model;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RockObject extends GameObject {

    private static Image rockImage;

    // blok static buat load gambar pas awal
    static {
        try {
            // muat gambar batu dari file
            // kredit aset: gambar dri canva
            rockImage = ImageIO.read(new File("rock.png"));
        } catch (IOException e) {
            System.err.println("error: gambar batu ga ketemu");
            e.printStackTrace();
        }
    }

    public RockObject(int x, int y) {
        // inisialisasi batu ukurannya lumayan gede 130x130 dan diem
        super(x, y, 130, 130, 0);
    }

    @Override
    public void render(Graphics g) {
        // gambar batu kalo asetnya ada
        if (rockImage != null) {
            g.drawImage(rockImage, x, y, width, height, null);
        }
        // kalo ga ada gambar pake kotak abu abu
        else {
            g.setColor(java.awt.Color.GRAY);
            g.fillRect(x, y, width, height);
        }
    }

    // karena batu itu benda mati jadi method tick kosong
    public void tick() {
        // do nothing
    }
}

// ini sejujurnya asteroid bu wkwk