import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Collections;

public class next_block_panel extends JPanel {

    JLabel nbtext = new JLabel();

    public next_block_panel(){
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.BLACK));
        this.setBackground(Color.white);

        nbtext.setBounds(20, 25, 120, 50);
        nbtext.setFont(new Font("Arial Bold", Font.ITALIC, 20));
        nbtext.setHorizontalAlignment(JLabel.CENTER);
        nbtext.setText("Next Block");
        nbtext.setBackground(Color.gray);
        this.add(nbtext);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        switch (Tetris_m.nBlock){
            case 1:
                g.drawRect(60, 120, 20, 20);
                g.drawRect(60, 140, 20, 20);
                g.drawRect(80, 120, 20, 20);
                g.drawRect(80, 140, 20, 20);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(61, 121, 19, 19);
                g.fillRect(61, 141, 19, 19);
                g.fillRect(81, 121, 19, 19);
                g.fillRect(81, 141, 19, 19);
                break;
            case 2:
                g.drawRect(60, 100, 20, 20);
                g.drawRect(60, 120, 20, 20);
                g.drawRect(60, 140, 20, 20);
                g.drawRect(80, 140, 20, 20);
                g.setColor(Color.red);
                g.fillRect(61, 101, 19, 19);
                g.fillRect(61, 121, 19, 19);
                g.fillRect(61, 141, 19, 19);
                g.fillRect(81, 141, 19, 19);
                break;
            case 3:
                g.drawRect(80, 100, 20, 20);
                g.drawRect(80, 120, 20, 20);
                g.drawRect(80, 140, 20, 20);
                g.drawRect(60, 140, 20, 20);
                g.setColor(Color.blue);
                g.fillRect(81, 101, 19, 19);
                g.fillRect(81, 121, 19, 19);
                g.fillRect(81, 141, 19, 19);
                g.fillRect(61, 141, 19, 19);
                break;
            case 4:
                g.drawRect(70, 100, 20, 20);
                g.drawRect(70, 120, 20, 20);
                g.drawRect(70, 140, 20, 20);
                g.drawRect(70, 160, 20, 20);
                g.setColor(Color.green);
                g.fillRect(71, 101, 19, 19);
                g.fillRect(71, 121, 19, 19);
                g.fillRect(71, 141, 19, 19);
                g.fillRect(71, 161, 19, 19);
                break;
            case 5:
                g.drawRect(50, 140, 20, 20);
                g.drawRect(70, 140, 20, 20);
                g.drawRect(90, 140, 20, 20);
                g.drawRect(70, 120, 20, 20);
                g.setColor(Color.magenta);
                g.fillRect(51, 141, 19, 19);
                g.fillRect(71, 141, 19, 19);
                g.fillRect(91, 141, 19, 19);
                g.fillRect(71, 121, 19, 19);
                break;

        }
    }
}
