import javax.swing.*;

public class game_thread extends Thread{

    JPanel gp = Tetris_m.g_panel;
    //JFrame mf = Tetris_m.m_frame;

    int x = Tetris_m.cBlock_x;
    int y = Tetris_m.cBlock_y;

    public void run(){
        for(int i = 0; i < 20; i++){

            check_shape();

//            System.out.println(i);
//            System.out.println("--------");
//            System.out.println(System.identityHashCode(Tetris_m.g_panel));
//            System.out.println(System.identityHashCode(gp));
//            System.out.println("--------");

            Tetris_m.status[i][i] = 1;
            Tetris_m.g_panel = new game_panel(); // modified game_panel will be made
            gp.setBounds(10, 30, 400, 700);
            gp.revalidate();
            gp.repaint();

//            gp.setVisible(false);
//            gp.setVisible(true); //  이걸로 해도 됨.

//            Tetris_m.g_panel.setBounds(10, 30, 400, 700);
//            Tetris_m.g_panel.revalidate();
//            Tetris_m.g_panel.repaint(); // 얘네는 왜 안되는 건가...

            try{
                Thread.sleep(1000);
            }catch (Exception e){}

        }
    }

    private void check_shape(){
        switch (Tetris_m.cBlock){
            case 11:
                Tetris_m.status[y][x] = 1;
                Tetris_m.status[y][x+1] = 1;
                Tetris_m.status[y+1][x] = 1;
                Tetris_m.status[y+1][x+1] = 1;
                break;
            case 2:

        }
    }
}
