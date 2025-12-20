package tmd.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class MenuWindow extends JFrame {
    private JTextField inputUsername;
    private JButton btnPlay, btnQuit;
    private DefaultTableModel tableModel;
    private Image bgImage;

    public MenuWindow() {
        // setup frame dasar
        setTitle("Hide and Seek The Challenge");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // load gambar background
        try {
            bgImage = ImageIO.read(new File("assets/background.png"));
        } catch (Exception e) {
            System.out.println("background gagal load");
        }

        // pasang panel background
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
                else g.fillRect(0, 0, getWidth(), getHeight()); // layar hitam jika gambar gagal
            }
        });
        setLayout(null); // layout bebas

        // judul game
        JLabel labelTitle = new JLabel("HIDE AND SEEK SHOOTER", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Monospaced", Font.BOLD, 28));
        labelTitle.setForeground(Color.CYAN);
        labelTitle.setBounds(50, 30, 500, 40);
        add(labelTitle);

        // label dan input username
        JLabel labelUser = new JLabel("PILOT NAME:");
        labelUser.setFont(new Font("Monospaced", Font.BOLD, 14));
        labelUser.setForeground(Color.WHITE);
        labelUser.setBounds(130, 90, 100, 25);
        add(labelUser);

        inputUsername = new JTextField();
        inputUsername.setBounds(220, 90, 220, 25);
        inputUsername.setBackground(Color.DARK_GRAY);
        inputUsername.setForeground(Color.WHITE);
        inputUsername.setCaretColor(Color.CYAN);
        add(inputUsername);

        // tabel highscore
        String[] columns = {"PILOT", "SKOR", "MELESET", "PELURU"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // atur tampilan tabel pake renderer simpel di bawah
        table.setDefaultRenderer(Object.class, new SimpleRenderer());
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.CYAN);
        table.setOpaque(false); // biar transparan
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // scrollpane buat tabel
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 140, 485, 200);
        scroll.getViewport().setBackground(new Color(0, 0, 0, 100)); // background transparan
        scroll.setOpaque(false);
        add(scroll);

        // tombol play dan quit
        btnPlay = createButton("LAUNCH", 150, new Color(0, 100, 0));
        btnQuit = createButton("ABORT", 320, new Color(100, 0, 0));

        add(btnPlay);
        add(btnQuit);

        setVisible(true);
    }

    // helper buat bikin tombol biar coding ga berulang
    private JButton createButton(String text, int x, Color color) {
        JButton btn = new JButton(text);
        btn.setBounds(x, 380, 130, 45);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 16));
        btn.setFocusPainted(false);
        return btn;
    }

    // kelas pengatur warna tabel
    class SimpleRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            super.getTableCellRendererComponent(t, v, s, f, r, c);
            setHorizontalAlignment(CENTER); // rata tengah

            if (s) {
                // kalo baris dipilih
                setBackground(Color.CYAN);
                setForeground(Color.BLACK);
            } else {
                setBackground(new Color(0, 20, 60, 255));
                setForeground(Color.WHITE);
            }
            return this;
        }
    }

    // getter
    public String getUsername() { return inputUsername.getText(); }
    public JButton getBtnPlay() { return btnPlay; }
    public JButton getBtnQuit() { return btnQuit; }
    public DefaultTableModel getTableModel() { return tableModel; }
}