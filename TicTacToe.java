import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {

    public record Tuple(int first, int second) {}

    // Window variables
    int width = 750;
    int height = 750;

    JFrame frame = new JFrame();
    JLabel titLabel = new JLabel();
    JLabel info = new JLabel();
    JPanel tablepanel = new JPanel();

    // Game variables
    boolean to_start = false;
    JButton[][] table = new JButton[3][3];
    int[] checkwin = {0, 0, 0, 0, 0, 0, 0, 0};
    int player_to_play = 0;
    int winner = -1;

    void repaintframe() {
        frame.revalidate();
        frame.repaint();
    }

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        titLabel.setBackground(Color.black);
        titLabel.setForeground(Color.white);
        titLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titLabel.setHorizontalAlignment(JLabel.CENTER);
        titLabel.setText("TicTacToe");
        titLabel.setOpaque(true);

        info.setBackground(Color.black);
        info.setForeground(Color.white);
        info.setFont(new Font("Arial", Font.ITALIC, 20));
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setText("Press Space");
        info.setOpaque(true);

        frame.add(titLabel, BorderLayout.NORTH);
        frame.add(info);


        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                int keyCode = e.getKeyCode();
                String keyString = "" + KeyEvent.getKeyText(keyCode);

                if (keyString.equals("Space")) {
                    System.out.println("Game starts!!");
                    to_start = true;
                    frame.removeKeyListener(this);
                    play();
                }
            }
          });

    }

    void play() {
        frame.remove(titLabel);
        frame.remove(info);
        frame.add(titLabel, BorderLayout.NORTH);

        tablepanel.setLayout(new GridLayout(3, 3));
        tablepanel.setBackground(Color.BLACK);
        frame.add(tablepanel);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                JButton block = new JButton();
                table[i][j] = block;
                tablepanel.add(block);
                block.setBackground(Color.BLACK);
                block.setForeground(Color.white);
                block.setFont(new Font("Arial", Font.BOLD, 100));
                block.setFocusable(false);

                block.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (block.getText() != "") {
                            System.out.println("HUH?");
                        }
                        else{
                            Tuple tuple = searchButton(block);
                            if (player_to_play == 0) {
                                block.setText("X");
                                player_to_play = 1;
                                update_checkwin(tuple.first(), tuple.second(), 1);
                                
                            }
                            else {
                                block.setText("O");
                                player_to_play = 0;
                                update_checkwin(tuple.first(), tuple.second(), -1);
                            }
                            
                            win();
                        }
                    }
                });
            }
        }
    }

    Tuple searchButton(JButton button) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (table[i][j] == button) {
                    return new Tuple(i, j);
                }
            }
        }
        return new Tuple(-1, -1);
    }

    void update_checkwin(int row, int column, int player) {
        checkwin[row] += 1 * player;
        checkwin[3 + column] += 1 * player;
        if ((row == 0 && column == 0) || (row == 1 && column == 1) || (row == 2 && column == 2)) {
            checkwin[6] += 1 * player;
        }
        if ((row == 0 && column == 2) || (row == 1 && column == 1) || (row == 2 && column == 0)) {
            checkwin[7] += 1 * player;
        }
    }

    void win(){
        for (int i = 0; i < 8; ++i) {
            if (checkwin[i] == 3) {
                System.out.println("Player 0 Wins!!");
                winner = 0;
            }
            else if (checkwin[i] == -3) {
                System.out.println("Player 1 Wins!!");
                winner = 1;
            }
        }

        if (winner != -1) {
            frame.remove(tablepanel);
            frame.remove(titLabel);
            titLabel.setText("Player " + winner + " wins!!");
            frame.add(titLabel);

            repaintframe();
        }
    }

    public static void main(String[] args) throws Exception {
        TicTacToe ttt = new TicTacToe();
    }

}