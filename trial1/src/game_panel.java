import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class game_panel extends JPanel {

    int[][] current_stat;

    public game_panel(int[][] shape_arr){
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.BLACK));
        this.setBackground(Color.white);
        this.current_stat = shape_arr;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        for(int i = 0; i < 35; i++){
            for(int j = 0; j < 20; j++){
                if(current_stat[i][j] == 1){
                    g.setColor(Color.blue);
                    g.drawRect(j*20, i*20, 20, 20);
                    g.setColor(Color.yellow);
                    g.fillRect(j*20,i*20,20,20);
                }
            }
        }
    }
}
