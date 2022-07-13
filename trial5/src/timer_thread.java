import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class timer_thread extends Thread{
    public void run(){

        long time_start = System.currentTimeMillis();

        while(true){
            long time_passed = System.currentTimeMillis() - time_start;
            int rounded_time = (int) (time_passed/1000);
            Tetris_m.time_spent = rounded_time;
            int min = rounded_time/60;
            int sec = rounded_time%60;
            StringBuilder sb = new StringBuilder();
            if(min < 10) sb.append(0);
            sb.append(min);
            sb.append(":");
            if(sec < 10) sb.append(0);
            sb.append(sec);
            Tetris_m.t_label.setText(sb.toString());
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("timer thread shut down");
                break;
            }
            System.out.println(rounded_time);
        }

    }
}