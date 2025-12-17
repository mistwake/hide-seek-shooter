package tmd.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuWindow extends JFrame {
    // komponen-komponen ui yang bakal dipake
    private JTextField inputUsername;
    private JButton btnPlay;
    private JButton btnQuit;
    private DefaultTableModel tableModel;

    public MenuWindow() {
        // settingan dasar window
        setTitle("Hide and Seek The Challenge");
        setSize(500, 500); // ukuran window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // layout manual
        setLocationRelativeTo(null); // biar muncul di tengah layar

        // label judul di paling atas
        JLabel labelTitle = new JLabel("HIDE AND SEEK THE CHALLENGE", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Serif", Font.BOLD, 18));
        labelTitle.setBounds(50, 20, 400, 30); // x, y, lebar, tinggi
        add(labelTitle);

        // label tulisan 'username'
        JLabel labelUser = new JLabel("Username:");
        labelUser.setBounds(100, 70, 80, 25);
        add(labelUser);

        // kotak input buat ngetik username
        inputUsername = new JTextField();
        inputUsername.setBounds(180, 70, 200, 25);
        add(inputUsername);

        // tabel buat nampilin highscore
        // nama kolom sesuai spesifikasi
        String[] columns = {"Username", "Skor", "Peluru Meleset", "Sisa Peluru"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // tabel harus dibungkus scrollpane biar bisa discroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 400, 200);
        add(scrollPane);

        // tombol play
        btnPlay = new JButton("Play");
        btnPlay.setBounds(120, 340, 100, 30);
        add(btnPlay);

        // tombol quit
        btnQuit = new JButton("Quit");
        btnQuit.setBounds(280, 340, 100, 30);
        add(btnQuit);

        // tampilin windownya
        setVisible(true);
    }

    // getter method biar presenter bisa akses komponen ini
    public String getUsername() { return inputUsername.getText(); }
    public JButton getBtnPlay() { return btnPlay; }
    public JButton getBtnQuit() { return btnQuit; }
    public DefaultTableModel getTableModel() { return tableModel; }
}