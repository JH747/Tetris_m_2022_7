import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game_thread extends Thread{

    public boolean work = true;

    public void run(){
        int defaultFallingTime = 0;
        boolean need_repaint = false; // key 

        while(true){
            if(!work) Thread.yield();
            else{
                // key press response should go here
                need_repaint = cBlock_updates.update_cBlock_loc_by_key();

                if(defaultFallingTime > Tetris_m.speed){
                    cBlock_updates.update_cBlock_loc_by_time(); // 
                    defaultFallingTime = 0;
                    need_repaint = true;
                }

                update_stat();

                if(Tetris_m.hit_floor_or_block) update_nBlock_and_update_stat();

                // TODO : game progress control
                if(Tetris_m.gameOver){
                    view_gameOver_frame();
                    break;
                }

                //Tetris_m.g_panel = new game_panel(); // 
                if(need_repaint){
                    Tetris_m.g_panel.revalidate();
                    Tetris_m.g_panel.repaint(); //
                    Tetris_m.nb_panel.revalidate();
                    Tetris_m.nb_panel.repaint();
                }
                need_repaint = false;


                try{
                    Thread.sleep(1);
                }
                catch(Exception e){}
                defaultFallingTime++;
            }
        }
    }

    private void update_stat(){


        int block_color = Tetris_m.cBlock / 10;
        // 1 : light gray   2 : red     3 : blue
        // 4 : green        5 : purple


        if(Tetris_m.hit_floor_or_block){
            for(uPoint p : Tetris_m.cBlock_loc){
                Tetris_m.status[p.y][p.x] = block_color;
            }
        }


        int line_clear = 0;
        int[][] tmp_stat = new int[35][20];
        int m = 34, n = 0;
        for(int i = 34; i >= 0; i--){
            for(int j = 0; j < 20; j++){
                tmp_stat[m][n] = Tetris_m.status[i][j];
                n++;
                if(Tetris_m.status[i][j] != 0) line_clear++;
            }
            if(line_clear != 20) m--;
            else{
                Tetris_m.score++;
                Tetris_m.s_label.setText(Integer.toString(Tetris_m.score));
            }
            line_clear = 0;
            n = 0;
        }
        Tetris_m.status = tmp_stat;


        if(!Tetris_m.hit_floor_or_block){
            for(uPoint p : Tetris_m.cBlock_loc){
                Tetris_m.status[p.y][p.x] = block_color;
            }
        }
    }


    private void update_nBlock_and_update_stat(){

        Tetris_m.cBlock_loc.clear();
        switch (Tetris_m.nBlock){
            case 1:
                Tetris_m.cBlock = 11;
                Tetris_m.cBlock_ref_point = new uPoint(9,1);
                Tetris_m.cBlock_loc.add(new uPoint(9,0));
                Tetris_m.cBlock_loc.add(new uPoint(10,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(10,1));
                break;
            case 2:
                Tetris_m.cBlock = 21;
                Tetris_m.cBlock_ref_point = new uPoint(9,2);
                Tetris_m.cBlock_loc.add(new uPoint(9,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(9,2));
                Tetris_m.cBlock_loc.add(new uPoint(10,2));
                break;
            case 3:
                Tetris_m.cBlock = 31;
                Tetris_m.cBlock_ref_point = new uPoint(9,1);
                Tetris_m.cBlock_loc.add(new uPoint(10,0));
                Tetris_m.cBlock_loc.add(new uPoint(10,1));
                Tetris_m.cBlock_loc.add(new uPoint(10,2));
                Tetris_m.cBlock_loc.add(new uPoint(9,2));
                break;
            case 4:
                Tetris_m.cBlock = 41;
                Tetris_m.cBlock_ref_point = new uPoint(9,3);
                Tetris_m.cBlock_loc.add(new uPoint(9,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(9,2));
                Tetris_m.cBlock_loc.add(new uPoint(9,3));
                break;
            case 5:
                Tetris_m.cBlock = 51;
                Tetris_m.cBlock_ref_point = new uPoint(10,0);
                Tetris_m.cBlock_loc.add(new uPoint(10,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(10,1));
                Tetris_m.cBlock_loc.add(new uPoint(11,1));
                break;
            default:
                break;
        }
        if(uPoint.doesCollide(Tetris_m.cBlock_loc, Tetris_m.status)) Tetris_m.gameOver = true;
        Tetris_m.hit_floor_or_block = false; // 

        Tetris_m.nBlock = (int)(Math.random()*5) + 1; // 
    }

    private void view_gameOver_frame(){

        Tetris_m.t_thread.interrupt(); // 

        JFrame gameOver_frame = new JFrame();
        gameOver_frame.setTitle("Warning!");
        gameOver_frame.setSize(310, 200);
        gameOver_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOver_frame.setLayout(null);

        // TODO : gameOver message, score, time
        JLabel l1 = new JLabel();
        l1.setText("Game Over");
        l1.setFont(new Font("Arial Bold", Font.ITALIC, 20));
        l1.setBounds(50,0,200,50);
        l1.setHorizontalAlignment(JLabel.CENTER);
        //l1.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(l1);

        JLabel l2 = new JLabel();
        l2.setText("Time : " + Tetris_m.time_spent);
        l2.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        l2.setBounds(0,50,150,50);
        l2.setHorizontalAlignment(JLabel.CENTER);
        l2.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(l2);

        JLabel l3 = new JLabel();
        l3.setText("Score : " + Tetris_m.score);
        l3.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        l3.setBounds(150,50,150,50);
        l3.setHorizontalAlignment(JLabel.CENTER);
        l3.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(l3);

        // TODO : restart, exit btn
        JButton restart_btn = new JButton();
        restart_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameOver_frame.setVisible(false);
                Tetris_m.restart(); // 

                Tetris_m.g_thread = new game_thread();
                Tetris_m.t_thread = new timer_thread();
                Tetris_m.g_thread.start();
                Tetris_m.t_thread.start();
            }
        });
        restart_btn.setText("Restart");
        restart_btn.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        restart_btn.setBounds(25,110,100,40);
        restart_btn.setHorizontalAlignment(JLabel.CENTER);
        //restart_btn.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(restart_btn);

        JButton exit_btn = new JButton();
        exit_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit_btn.setText("Exit");
        exit_btn.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        exit_btn.setBounds(175,110,100,40);
        exit_btn.setHorizontalAlignment(JLabel.CENTER);
        //exit_btn.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(exit_btn);

        gameOver_frame.setVisible(true);
        gameOver_frame.setResizable(false);
        gameOver_frame.setLocationRelativeTo(null);
    }

}
