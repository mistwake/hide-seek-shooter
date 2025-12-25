package tmd.presenter;

import tmd.model.Player;
import tmd.model.TBenefitModel;
import tmd.view.MenuWindow;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MenuPresenter {
    private MenuWindow view;
    private TBenefitModel model;
    private Clip menuClip;

    public MenuPresenter(MenuWindow view) {
        this.view = view;
        this.model = new TBenefitModel(); // siapin model buat koneksi database

        // muat data tabel pas pertama kali dibuka
        loadTableData();

        // load dan putar musik khusus menu
        menuClip = loadAudio("assets/menu.wav");
        playMusic(menuClip);

        // kasih aksi buat tombol play
        view.getBtnPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // kasih aksi buat tombol quit langsung keluar program
        view.getBtnQuit().addActionListener(e -> {
            stopMusic(); // matikan musik sebelum keluar
            System.exit(0);
        });
    }

    // fungsi load audio (sama kayak di GamePresenter)
    private Clip loadAudio(String path) {
        try {
            File audioFile = new File(path);
            if (!audioFile.exists()) {
                System.out.println("Audio menu tidak ditemukan: " + path);
                return null;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }

    // fungsi putar musik looping
    private void playMusic(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    // fungsi stop musik
    private void stopMusic() {
        if (menuClip != null && menuClip.isRunning()) {
            menuClip.stop();
            menuClip.close();
        }
    }

    // fungsi buat ngambil data dari db dan nampilin ke tabel
    private void loadTableData() {
        // minta list player ke model
        List<Player> players = model.getAllPlayers(); //

        // ambil model tabel dari view biar bisa diisi
        DefaultTableModel tableModel = view.getTableModel();

        // kosongin dulu isinya biar ga numpuk
        tableModel.setRowCount(0);

        // loop buat ngisi baris tabel satu satu
        for (Player p : players) {
            tableModel.addRow(new Object[]{
                    p.getUsername(),
                    p.getSkor(),
                    p.getPeluruMeleset(),
                    p.getSisaPeluru()
            });
        }
    }

    // logika pas tombol play ditekan
    private void startGame() {
        String username = view.getUsername();

        // cek username gaboleh kosong
        if (username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "username diisi dulu dong");
            return;
        }

        // simpen user baru ke database kalo emang belum ada
        model.addPlayerIfNew(username);

        // matikan musik menu sebelum masuk game
        // Supaya tidak tabrakan dengan musik game
        stopMusic();

        // tutup menu window
        view.dispose();

        // mulai masuk ke game (GamePresenter akan memutar musiknya sendiri)
        new GamePresenter(username);
    }
}