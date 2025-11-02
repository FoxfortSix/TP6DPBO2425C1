import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    View view;
    Image birdImage;
    Player player;

    Image lowerPipeImage;
    Image upperPipeImage;
    Image backgroundImage;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;

    int gravity = 1;
    int pipeVelocityX = -2;

    boolean gameStarted;
    boolean gameOver;
    int score;

    public Logic() {
        gameStarted = false;
        gameOver = false;
        score = 0;
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage, 0);

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Pipa");
                placePipes();
            }
        });

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void restartGame() {
        // Reset posisi & kecepatan player
        player.setPosY(playerStartPosY);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // Kosongkan semua pipa
        pipes.clear();

        // Reset skor
        score = 0;
        if (view != null) {
            view.updateScore(score);
        }

        // Reset status game
        gameStarted = false;
        gameOver = false;

        // Hentikan timer pipa (akan dimulai lagi saat spasi)
        pipesCooldown.stop();
        // Mulai lagi game loop utama
        gameLoop.start();

        // Repaint untuk menghapus tulisan "Game Over"
        if (view != null) {
            view.repaint();
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight,
                lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void move() {
        // Jika game over, hentikan semua timer dan jangan lakukan apa-apa
        if (gameOver) {
            gameLoop.stop();
            pipesCooldown.stop();
            return;
        }

        if (gameStarted) {
            // Fisika pemain (gravitasi)
            player.setVelocityY(player.getVelocityY() + gravity);
            player.setPosY(player.getPosY() + player.getVelocityY());
            player.setPosY((Math.max(player.getPosY(), 0)));

            Rectangle playerBounds = player.getBounds();

            // 1. Deteksi jatuh ke tanah
            if (player.getPosY() + player.getHeight() >= frameHeight) {
                gameOver = true;
            }

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.setPosX(pipe.getPosX() + pipeVelocityX);

                // 2. Deteksi tabrak pipa
                if (playerBounds.intersects(pipe.getBounds())) {
                    gameOver = true;
                }

                // 3. Deteksi skor (hanya cek pipa atas agar tidak dobel)
                if (pipe.getImage() == upperPipeImage && !pipe.isPassed() &&
                        pipe.getPosX() + pipe.getWidth() < player.getPosX())
                {
                    score++;
                    pipe.setPassed(true);
                    if (view != null) {
                        view.updateScore(score);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10); // Lompat

            if (!gameStarted) {
                gameStarted = true;
                pipesCooldown.start();
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}