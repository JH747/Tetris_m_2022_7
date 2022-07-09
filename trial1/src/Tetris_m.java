import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class Tetris_m extends JFrame {

    int row, column;

    static int[][] status = new int[35][20];
    static JPanel timer_panel; // 공유 위한 static 선언

    public Tetris_m(){
        this.setTitle("Tetris Game 2022_7");
        //JFrame i_frame = new JFrame("Tetris Game 2022_7");
        this.setSize(600, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO : game_panel

        status[4][1] = 1;

        game_panel g_panel = new game_panel(status);
        g_panel.setBounds(10, 30, 400, 700);
        place_game_panel_Components(g_panel);

        JPanel nextBock_panel = new JPanel();
        timer_panel = new JPanel();
        JPanel score_panel = new JPanel();

        this.setLayout(null);
        this.add(g_panel);
        this.add(nextBock_panel);
        this.add(timer_panel);
        this.add(score_panel);



        nextBock_panel.setBounds(420, 30, 150, 200);
        nextBock_panel.setBorder(new LineBorder(Color.BLACK));
        place_nextBock_panel_Components(nextBock_panel);

        timer_panel.setBounds(420, 300, 150, 150);
        timer_panel.setBorder(new LineBorder(Color.BLACK));
        place_timer_panel_Components(timer_panel);

        score_panel.setBounds(420, 500, 150, 150);
        score_panel.setBorder(new LineBorder(Color.BLACK));
        place_score_panel_Components(timer_panel);

        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);



        for(int i = 0; i < 10; i++){
            System.out.println(i);
            g_panel.setVisible(false);
            status[i][i] = 1;
            g_panel = new game_panel(status);
            g_panel.setBounds(10, 30, 400, 700);
            this.add(g_panel);

            try{
                Thread.sleep(1000);
            }catch (Exception e){}

        }


    }

    private static void place_game_panel_Components(JPanel panel){
        panel.setLayout(null); // absolute layout

        JLabel userLabel = new JLabel("User");

        userLabel.setBounds(10, 20, 80, 25); // 앞 두 개는 위치, 뒤 두 개는 크기
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
    }

    public void place_nextBock_panel_Components(JPanel panel){

    }

    public void place_timer_panel_Components(JPanel panel){
        panel.setLayout(null);
        timer_thread th1 = new timer_thread();
        th1.start();
    }

    public void place_score_panel_Components(JPanel panel){

    }

    public static void main(String args[]){
        new Tetris_m();
    }
}

