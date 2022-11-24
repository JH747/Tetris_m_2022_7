import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class timer_thread extends Thread{

    public boolean work = true;

    public void run(){

        boolean stopped = false;
        long c_time;
        long time_started = System.currentTimeMillis();
        long time_stopped_at = 0;
        long time_stopped_for = 0;
        long time_passed;

        while(true){
            if(!work){
                if(!stopped) time_stopped_at = System.currentTimeMillis();
                stopped = true;
                Thread.yield(); // 무의미한 반복 줄이기 위해 다른 thread에 양보 가능하도록 함.
            }
            else{
                c_time = System.currentTimeMillis();
                if(stopped){
                    time_stopped_for += (c_time - time_stopped_at);
                    stopped = false;
                }
                time_passed = c_time - time_started - time_stopped_for;
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
                    return;
                }
                System.out.println(rounded_time);
            }

        }

    }
}