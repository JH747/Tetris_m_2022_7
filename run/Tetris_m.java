import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tetris_m extends JFrame {

    // TODO : basic components
    static int[][] status = new int[35][20];

    static int speed = 500;

    static JMenuBar mb;

    static game_panel g_panel;
    static next_block_panel nb_panel;
    static JFrame m_frame;

    static game_thread g_thread;
    static timer_thread t_thread;

    // TODO : game progress control
    static JPanel pr_panel;
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
    static int cBlock = 21; // 
    static ArrayList<uPoint> cBlock_loc = new ArrayList<>(); // 
    static uPoint cBlock_ref_point;
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

        // TODO : pause
        pr_panel = new JPanel();
        this.add(pr_panel);
        set_pr_panel();

        // TODO : menu bar
        mb = new JMenuBar();
        this.setJMenuBar(mb);
        set_mb();

        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void set_t_panel(){
        t_panel.setBounds(420, 255, 160, 150);
        t_panel.setBorder(new LineBorder(Color.BLACK));
        t_panel.setLayout(null);
        JLabel ttext = new JLabel();
        ttext.setBounds(20, 25, 120, 50);
        ttext.setFont(new Font("Arial Bold", Font.ITALIC, 20));
        ttext.setHorizontalAlignment(JLabel.CENTER);
        ttext.setText("Time Spent");
        t_panel.add(ttext);
        t_label.setBounds(20,75,120,50);
        //t_label.setBorder(new LineBorder(Color.BLACK));
        t_label.setFont(new Font("Arial Bold", Font.PLAIN, 20));
        t_label.setHorizontalAlignment(JLabel.CENTER);
        t_panel.add(t_label);

        t_thread = new timer_thread();
        t_thread.start();
    }

    private void set_g_panel(){
        g_panel.setBounds(10, 10, 401, 701);
        g_thread = new game_thread();
        g_thread.start();
    }

    private void set_s_panel(){
        s_panel.setBounds(420, 450, 160, 150);
        s_panel.setBorder(new LineBorder(Color.BLACK));
        s_panel.setLayout(null);
        JLabel stext = new JLabel();
        stext.setBounds(20, 25, 120, 50);
        stext.setFont(new Font("Arial Bold", Font.ITALIC, 20));
        stext.setHorizontalAlignment(JLabel.CENTER);
        stext.setText("Score");
        s_panel.add(stext);
        s_label.setBounds(20,75,120,50);
        //s_label.setBorder(new LineBorder(Color.BLACK));
        s_label.setHorizontalAlignment(JLabel.CENTER);
        s_label.setText(Integer.toString(score));
        s_label.setFont(new Font("Arial Bold", Font.PLAIN, 20));
        s_panel.add(s_label);

    }

    private void set_nb_panel(){
        nb_panel.setBounds(420, 10, 160, 200);
    }

    private void set_pr_panel(){
        pr_panel.setBounds(420, 610, 160, 100);
        //pr_panel.setBorder(new LineBorder(Color.BLACK));
        pr_panel.setLayout(null);

        JButton pause_btn = new JButton();
        pause_btn.setFocusable(false); // so that keyboard input not be intercepted
        pause_btn.setText("pause");
        pause_btn.setFont(new Font("Arial Bold", Font.ITALIC, 12));
        pause_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g_thread.work = false;
                t_thread.work = false;
            }
        });
        pause_btn.setBounds(35,10,90, 40);
        pr_panel.add(pause_btn);

        JButton resume_btn = new JButton();
        resume_btn.setFocusable(false); // so that keyboard input not be intercepted
        resume_btn.setText("resume");
        resume_btn.setFont(new Font("Arial Bold", Font.ITALIC, 12));
        resume_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g_thread.work = true;
                t_thread.work = true;
            }
        });
        resume_btn.setBounds(35, 60, 90, 40);
        pr_panel.add(resume_btn);
    }

    private static void set_mb(){
        JMenu settings = new JMenu("Settings");
        mb.add(settings);

        JMenu difficulty = new JMenu("difficulty");
        settings.add(difficulty);

        JMenuItem easyMode = new JMenuItem("easy");
        easyMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = 1000;
            }
        });
        difficulty.add(easyMode);

        JMenuItem normalMode = new JMenuItem("normal");
        normalMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = 500;
            }
        });
        difficulty.add(normalMode);

        JMenuItem hardMode = new JMenuItem("hard");
        hardMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = 250;
            }
        });
        difficulty.add(hardMode);
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


