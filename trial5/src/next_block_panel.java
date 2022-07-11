import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Collections;

public class next_block_panel extends JPanel {

    public next_block_panel(){
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.BLACK));
        this.setBackground(Color.white);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        switch (Tetris_m.nBlock){
            case 1:
                g.drawRect(60, 80, 20, 20);
                g.drawRect(60, 100, 20, 20);
                g.drawRect(80, 80, 20, 20);
                g.drawRect(80, 100, 20, 20);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(61, 81, 19, 19);
                g.fillRect(61, 101, 19, 19);
                g.fillRect(81, 81, 19, 19);
                g.fillRect(81, 101, 19, 19);
                break;
            case 2:
                g.drawRect(60, 60, 20, 20);
                g.drawRect(60, 80, 20, 20);
                g.drawRect(60, 100, 20, 20);
                g.drawRect(80, 100, 20, 20);
                g.setColor(Color.red);
                g.fillRect(61, 61, 19, 19);
                g.fillRect(61, 81, 19, 19);
                g.fillRect(61, 101, 19, 19);
                g.fillRect(81, 101, 19, 19);
                break;
            case 3:
                g.drawRect(80, 60, 20, 20);
                g.drawRect(80, 80, 20, 20);
                g.drawRect(80, 100, 20, 20);
                g.drawRect(60, 100, 20, 20);
                g.setColor(Color.blue);
                g.fillRect(81, 61, 19, 19);
                g.fillRect(81, 81, 19, 19);
                g.fillRect(81, 101, 19, 19);
                g.fillRect(61, 101, 19, 19);
                break;
            case 4:
                g.drawRect(60, 60, 20, 20);
                g.drawRect(60, 80, 20, 20);
                g.drawRect(60, 100, 20, 20);
                g.drawRect(60, 120, 20, 20);
                g.setColor(Color.green);
                g.fillRect(61, 61, 19, 19);
                g.fillRect(61, 81, 19, 19);
                g.fillRect(61, 101, 19, 19);
                g.fillRect(61, 121, 19, 19);
                break;
            case 5:
                g.drawRect(60, 100, 20, 20);
                g.drawRect(80, 100, 20, 20);
                g.drawRect(100, 100, 20, 20);
                g.drawRect(80, 80, 20, 20);
                g.setColor(Color.magenta);
                g.fillRect(61, 101, 19, 19);
                g.fillRect(81, 101, 19, 19);
                g.fillRect(101, 101, 19, 19);
                g.fillRect(81, 81, 19, 19);
                break;

        }
    }
}
