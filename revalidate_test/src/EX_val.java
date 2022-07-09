import javax.swing.*;
import javax.swing.border.LineBorder;

public class EX_val extends JFrame {

    static int[][] status = new int[35][20];

    public EX_val(){

        status[5][1] = 1;

        this.setTitle("val_test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setLayout(null);

        game_panel p1 = new game_panel();
        this.add(p1);

        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        for(int i = 0; i < 19; i++){
            System.out.println(i);
            status[i][i] = 1;
            //p1 = new game_panel();
            p1.revalidate();
            p1.repaint();
            try{
                Thread.sleep(1000);
            }catch (Exception e){}

        }

    }

    public static void main(String args[]){
        new EX_val();
    }
}
