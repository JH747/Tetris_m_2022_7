import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class timer_thread extends Thread{
    public void run(){

        long time_start = System.currentTimeMillis();

        for(int i = 60; i > 0; i--){
            long time_passed = System.currentTimeMillis() - time_start;
            int rounded_time = (int) (time_passed/1000);
            Tetris_m.time_spent = rounded_time;
            Tetris_m.t_label.setText(Integer.toString(rounded_time));
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("timer thread shut down");
                break;
            }
            System.out.println(i);
        }

    }
}