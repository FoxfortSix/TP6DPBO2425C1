import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayMenu {
    private JPanel panel1;
    private JButton startButton;
    private JButton quitButton;
    private JFrame menuFrame;

    public PlayMenu(JFrame frame) {
        this.menuFrame = frame;

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Panggil metode startGame() dari App.java
                App.startGame();

                // 2. Tutup jendela menu ini
                menuFrame.dispose();
            }
        });

        // Aksi untuk tombol QUIT
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Keluar dari seluruh program
                System.exit(0);
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}