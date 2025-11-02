import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

public class App {

    public static void main(String[] args) {
        // Buat Jendela (JFrame) baru untuk menu
        JFrame menuFrame = new JFrame("Flappy Bird Menu");

        // Buat instance PlayMenu
        PlayMenu menu = new PlayMenu(menuFrame);

        // Atur agar konten jendela adalah panel1 dari GUI Form Anda
        menuFrame.setContentPane(menu.getPanel1()); // Kita akan buat getter ini

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.pack(); // Atur ukuran jendela agar pas
        menuFrame.setLocationRelativeTo(null); // Tampilkan di tengah
        menuFrame.setVisible(true);
    }

    public static void startGame() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.BLACK);

        Logic logika = new Logic();
        // Berikan scoreLabel ke View
        View tampilan = new View(logika, scoreLabel);

        logika.setView(tampilan);

        frame.add(tampilan);
        frame.pack();
        frame.setVisible(true);

        tampilan.requestFocusInWindow();
    }
}