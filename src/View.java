import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    int width = 360;
    int height = 640;

    private Logic logic; // tambahkan atribut ini
    private JLabel scoreLabel;

    // constructor
    public View(Logic logic, JLabel scoreLabel) {
        this.logic = logic; // memasukkan ke dalam constructor
        this.scoreLabel = scoreLabel;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);

        this.setLayout(null); // Gunakan null layout agar bisa atur posisi manual
        scoreLabel.setBounds(10, 10, 150, 30); // Atur posisi (x, y, width, height)
        this.add(scoreLabel); // Tambahkan label ke panel ini

        // tambahkan fokus dan Key Listener
        // untuk menerima input keyboard
        setFocusable(true);
        addKeyListener(logic);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        Image bg = logic.getBackgroundImage();
        if (bg != null) {
            g.drawImage(bg, 0, 0, width, height, null);
        }

        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        if (logic.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));

            // Kode untuk menengahkan teks "Game Over"
            String gameOverText = "Game Over";
            FontMetrics fm = g.getFontMetrics(); // Dapatkan FontMetrics UNTUK FONT SAAT INI (Arial, Bold, 48)
            int xGameOver = (width - fm.stringWidth(gameOverText)) / 2; // Hitung x untuk Game Over
            g.drawString(gameOverText, xGameOver, height / 2 - 50);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20)); // Ganti font ke Arial, Bold, 20

            // Dapatkan FontMetrics BARU UNTUK FONT YANG BARU (Arial, Bold, 20)
            fm = g.getFontMetrics(); // <-- PENTING: Panggil lagi getFontMetrics() setelah ganti font
            String restartText = "Press 'R' to Restart";
            int xRestart = (width - fm.stringWidth(restartText)) / 2; // Hitung x untuk Restart
            g.drawString(restartText, xRestart, height / 2);
        }
        // 2. ATAU Tampilkan "Start" JIKA game BELUM dimulai
        else if (!logic.isGameStarted()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            // Kode untuk menengahkan teks
            String startText = "Press Space to Start";
            FontMetrics fm = g.getFontMetrics();
            int x = (width - fm.stringWidth(startText)) / 2;
            g.drawString(startText, x, height / 2 - 25);
        }
    }
}