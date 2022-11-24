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
        super.paint(g); // 
        for(int i = 0; i < 35; i++){
            for(int j = 0; j < 20; j++){

                if(Tetris_m.status[i][j] == 0) continue; // pass if 0

                g.setColor(Color.black);
                g.drawRect(j*20, i*20, 20, 20);
                switch (Tetris_m.status[i][j]){
                    case 1:
                        g.setColor(Color.LIGHT_GRAY);
                        break;
                    case 2:
                        g.setColor(Color.red);
                        break;
                    case 3:
                        g.setColor(Color.blue);
                        break;
                    case 4:
                        g.setColor(Color.green);
                        break;
                    case 5:
                        g.setColor(Color.magenta);
                        break;
                }
                g.fillRect(j*20+1,i*20+1,19,19);

            }
        }
    }
}
