import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Tetris_m extends JFrame {

    static int[][] status = new int[35][20];
    static JPanel t_panel;
    static game_panel g_panel;
    static JPanel s_panel;
    static JPanel nb_panel;
    static JFrame m_frame;

    static int score = 0;
    static JLabel s_label = new JLabel();

    // TODO : current block and next block
    static int cBlock = 21; // 현재 block 종류 (11, 21, 22, 23, 24, 31, 32, 33, 34, 41, 42, 51, 52, 53, 54)
    static ArrayList<uPoint> cBlock_loc = new ArrayList<>(); // 현재 block 위치
//    static int nBlock = 31;
//    static ArrayList<uPoint> nBlock_loc = new ArrayList<>();

    static int[] blockShapes = {11, 21, 22, 23, 24, 31, 32, 33, 34, 41, 42, 51, 52, 53, 54}; // 15 개 종류

    static boolean hit_floor_or_block = false;

    public Tetris_m(){

        // TODO : for block shape check, must be removed
        cBlock_loc.add(new uPoint(9,0));
        cBlock_loc.add(new uPoint(9,1));
        cBlock_loc.add(new uPoint(9,2));
        cBlock_loc.add(new uPoint(10,2));
        // TODO ----------------------------------------


        this.setTitle("Tetris_m 2022_7");
        this.setSize(600, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        m_frame = this;

        // TODO : timer_panel
        t_panel = new JPanel();
        this.add(t_panel);
        set_t_panel();

        // TODO : game_panel
        g_panel = new game_panel();
        this.add(g_panel);
        set_g_panel();

        // TODO : score_panel
        s_panel = new JPanel();
        this.add(s_panel);
        set_s_panel();

        // TODO : next_block_panel
        nb_panel = new JPanel();
        this.add(nb_panel);
        set_nb_panel();

        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void set_t_panel(){
        t_panel.setBounds(420, 300, 150, 150);
        t_panel.setBorder(new LineBorder(Color.BLACK));
        t_panel.setLayout(null);
        timer_thread th1 = new timer_thread();
        th1.start();
    }

    private void set_g_panel(){
        g_panel.setBounds(10, 30, 400, 700);
        game_thread th2 = new game_thread();
        th2.start();
    }

    private void set_s_panel(){
        s_panel.setBounds(420, 500, 150, 150);
        s_panel.setBorder(new LineBorder(Color.BLACK));
        s_panel.setLayout(null);
        s_label.setBounds(15,20,120,50);
        s_label.setBorder(new LineBorder(Color.BLACK));
        s_label.setHorizontalAlignment(JLabel.CENTER);
        s_label.setText(Integer.toString(score));
        s_panel.add(s_label);

    }

    private void set_nb_panel(){
        nb_panel.setBounds(420, 30, 150, 200);
        nb_panel.setBorder(new LineBorder(Color.BLACK));
        nb_panel.setLayout(null);
    }


    public static void main(String args[]){
        new Tetris_m();
    }
}


