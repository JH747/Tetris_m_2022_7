import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class Tetris_m extends JFrame {

    // TODO : basic components
    static int[][] status = new int[35][20];

    static game_panel g_panel;
    static next_block_panel nb_panel;
    static JFrame m_frame;

    static game_thread g_thread;
    static timer_thread t_thread;

    // TODO : game progress control
    static boolean pause = false;
    static boolean gameOver = false;

    // TODO : key input
    static key_input keys_c;
    static int k_code = 0;

    // TODO : score and time
    static int score = 0;
    static int time_spent = 0;
    static JPanel s_panel;
    static JLabel s_label = new JLabel();
    static JPanel t_panel;
    static JLabel t_label = new JLabel();

    // TODO : current block and next block
    static int cBlock = 21; // 현재 block 종류 (11, 21, 22, 23, 24, 31, 32, 33, 34, 41, 42, 51, 52, 53, 54)
    static ArrayList<uPoint> cBlock_loc = new ArrayList<>(); // 현재 block 위치
    static int nBlock = 1;
//    static ArrayList<uPoint> nBlock_loc = new ArrayList<>();
    static boolean hit_floor_or_block = true;

    //static List<Integer> nBlockShapes = new ArrayList<>(asList(11,21,31,41,51));

    public Tetris_m(){

        // TODO : for block shape check, must be removed
//        cBlock_loc.add(new uPoint(9,0));
//        cBlock_loc.add(new uPoint(9,1));
//        cBlock_loc.add(new uPoint(9,2));
//        cBlock_loc.add(new uPoint(10,2));
        // ---------------------------------------------

        this.setTitle("Tetris_m 2022_7");
        this.setSize(605, 800);
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
        nb_panel = new next_block_panel();
        this.add(nb_panel);
        set_nb_panel();

        // TODO : key_input
        keys_c = new key_input();
        this.addKeyListener(keys_c);


        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void set_t_panel(){
        t_panel.setBounds(420, 300, 160, 150);
        t_panel.setBorder(new LineBorder(Color.BLACK));
        t_panel.setLayout(null);
        t_label.setBounds(15,20,120,50);
        t_label.setBorder(new LineBorder(Color.BLACK));
        t_label.setHorizontalAlignment(JLabel.CENTER);
        t_panel.add(t_label);
        t_thread = new timer_thread();
        t_thread.start();
    }

    private void set_g_panel(){
        g_panel.setBounds(10, 30, 400, 700);
        g_thread = new game_thread();
        g_thread.start();
    }

    private void set_s_panel(){
        s_panel.setBounds(420, 500, 160, 150);
        s_panel.setBorder(new LineBorder(Color.BLACK));
        s_panel.setLayout(null);
        s_label.setBounds(15,20,120,50);
        s_label.setBorder(new LineBorder(Color.BLACK));
        s_label.setHorizontalAlignment(JLabel.CENTER);
        s_label.setText(Integer.toString(score));
        s_panel.add(s_label);

    }

    private void set_nb_panel(){
        nb_panel.setBounds(420, 30, 160, 200);
    }

    public static void restart(){
        status = new int[35][20];
        pause = false;
        gameOver = false;

        score = 0;
        time_spent = 0;
        cBlock = 21;
        cBlock_loc = new ArrayList<>();
        hit_floor_or_block = true; // so that block can be generated automatically
    }


    public static void main(String args[]){
        new Tetris_m();
    }
}


