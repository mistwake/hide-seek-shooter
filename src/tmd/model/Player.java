package tmd.model;

public class Player {
    private String username;
    private int skor;
    private int peluruMeleset;
    private int sisaPeluru;

    public Player(String username, int skor, int peluruMeleset, int sisaPeluru) {
        this.username = username;
        this.skor = skor;
        this.peluruMeleset = peluruMeleset;
        this.sisaPeluru = sisaPeluru;
    }

    // getter
    public String getUsername() { return username; }
    public int getSkor() { return skor; }
    public int getPeluruMeleset() { return peluruMeleset; }
    public int getSisaPeluru() { return sisaPeluru; }

    // setter
    public void setSkor(int skor) { this.skor = skor; }
    public void setPeluruMeleset(int peluruMeleset) { this.peluruMeleset = peluruMeleset; }
    public void setSisaPeluru(int sisaPeluru) { this.sisaPeluru = sisaPeluru; }
}
