import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class timer_thread extends Thread{

    JPanel tp = Tetris_m.t_panel;

    public void run(){

        JLabel t_label = new JLabel();
        t_label.setBounds(15,20,120,50);
        t_label.setBorder(new LineBorder(Color.BLACK));
        t_label.setHorizontalAlignment(JLabel.CENTER);
        tp.add(t_label);
        long time_start = System.currentTimeMillis();

        for(int i = 60; i > 0; i--){
            long time_passed = System.currentTimeMillis() - time_start;
            int rounded_time = (int) (time_passed/1000);
            t_label.setText(Integer.toString(rounded_time));
            try{
                Thread.sleep(1000);
            }catch (Exception e){}
            System.out.println(i);
        }

    }
}