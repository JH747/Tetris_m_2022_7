import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class game_panel extends JPanel {

    public game_panel(){
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.BLACK));
        this.setBackground(Color.white);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        for(int i = 0; i < 35; i++){
            for(int j = 0; j < 20; j++){
                if(Tetris_m.status[i][j] == 1){
                    g.setColor(Color.blue);
                    g.drawRect(j*20, i*20, 20, 20);
                    g.setColor(Color.yellow);
                    g.fillRect(j*20+1,i*20+1,18,18);
                }
            }
        }
    }
}
