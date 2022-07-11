import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game_thread extends Thread{

    //JFrame mf = Tetris_m.m_frame;

    public void run(){
        int defaultFallingTime = 0;
        while(true){
            // key press response should go here
            update_cBlock_loc_by_key();

            if(defaultFallingTime > 100){
                update_cBlock_loc_by_time(); // 시간 경과 따른 block loc 업데이트, 바닥에 닿았는지 여기서 확인됨
                defaultFallingTime = 0;
            }

            update_stat();

            if(Tetris_m.hit_floor_or_block) update_nBlock_and_update_stat();

            // TODO : game progress control
            if(Tetris_m.gameOver){
                view_gameOver_frame();
                break;
            }

            //Tetris_m.g_panel = new game_panel(); // 이 부분이 문제. 새로운 패널을 만드는 것이 아니라 기존 패널을 revalidate해야 한다.
            Tetris_m.g_panel.revalidate();
            Tetris_m.g_panel.repaint(); //
            Tetris_m.nb_panel.revalidate();
            Tetris_m.nb_panel.repaint();

            // 일정시간마다 revalidate하되 입력이 들어오는 경우도 revalidate하도록 따로 할 수도, 이 경우 synchronize가 필요할 것
            try{
                Thread.sleep(1);
            }
            catch(Exception e){}
            defaultFallingTime++;
        }
    }
    /*
    current block 위치를 다음 위치로 업데이트
    다음 위치에서 block 자신을 제외하고 충돌이 일어나는 경우 can_proceed를 false로 hit_floor_or_block을 true로 전환한다.
    충돌이 없는 경우 기존에 stat에 있던 block를 제거하고 자기 자신의 위치값을 업데이트한다.
    stat에 새 위치를 찍는 것은 update_shape_to_stat에서 진행한다.
     */

    // 지난 1ms동안 키입력이 있었는지 확인한다.
    private void update_cBlock_loc_by_key(){

        // update_cBock_loc_by_time()와 마찬가지로 자기 자신과 겹치는 것은 상관 없지만 다른 놈이랑 겹치는 것은 불가하도록 구현
        // 생각하니까 그냥 첫 시점에 날려버리고 시작하면 불편하게 안해도 되네?

        for(uPoint p : Tetris_m.cBlock_loc){
            Tetris_m.status[p.y][p.x] = 0;
        }

        boolean can_proceed = true;
        switch (Tetris_m.k_code){
            case 37:
                // left move
                for(uPoint p : Tetris_m.cBlock_loc){
                    if(p.x == 0){
                        can_proceed = false;
                        break;
                    }
                    if(Tetris_m.status[p.y][p.x-1] != 0) can_proceed = false;
                }
                if(can_proceed){
                    for(uPoint p : Tetris_m.cBlock_loc) p.x--;
                }
                break;
            case 39:
                // right move
                for(uPoint p : Tetris_m.cBlock_loc){
                    if(p.x == 19){
                        can_proceed = false;
                        break;
                    }
                    if(Tetris_m.status[p.y][p.x+1] != 0) can_proceed = false;
                }
                if(can_proceed){
                    for(uPoint p : Tetris_m.cBlock_loc) p.x++;
                }
                break;
            case 40:
                // down move
                for(uPoint p : Tetris_m.cBlock_loc){
                    if(p.y == 34){
                        can_proceed = false;
                        break;
                    }
                    if(Tetris_m.status[p.y+1][p.x] != 0) can_proceed = false;
                }
                if(can_proceed){
                    for(uPoint p : Tetris_m.cBlock_loc) p.y++;
                }
                break;
            case 38:
                // up invert

                break;
            case 32:
                // space
                while(true){
                    for(uPoint p : Tetris_m.cBlock_loc){
                        if(p.y == 34){
                            can_proceed = false;
                            break;
                        }
                    }
                    if(!can_proceed) break;
                    for(uPoint p : Tetris_m.cBlock_loc) p.y++;
                    if(uPoint.doesCollide(Tetris_m.cBlock_loc, Tetris_m.status)){
                        for(uPoint p : Tetris_m.cBlock_loc) p.y--;
                        break;
                    }
                }
                break;
        }

        // k_code 키보드 입력변수 원위치
        Tetris_m.k_code = 0;
    }

    // 지정된 시간이 지났다면 -> cBlock을 아래로 한칸 이동시키되 불가한 경우 hit_floor_or_block = true가 된다.
    private void update_cBlock_loc_by_time(){

        // moved to here so that there may no need to compare if block collides itself
        for(uPoint p : Tetris_m.cBlock_loc){
            Tetris_m.status[p.y][p.x] = 0; // 기존에 있던 위치의 stat 초기화
        }

        boolean can_proceed = true;

        for(uPoint p : Tetris_m.cBlock_loc){
            // 여기에서 현위치를 주고 다음 위치가 가능한 위치인지 아닌지 판단해 can_proceed를 정한다.

            // boolean collapse_self = false;
            // 최하단에 닿은 경우
            if(p.y == 34){
                can_proceed = false;
                Tetris_m.hit_floor_or_block = true;
                break;
            }
            if(Tetris_m.status[p.y+1][p.x] != 0) can_proceed = false;
        }

        // can_proceed 값이 true이면 cBlock_loc 업데이트, false이면 전역변수 업데이트 후 리턴
        if(!can_proceed){
            Tetris_m.hit_floor_or_block = true;
            return;
        }
        for(uPoint p : Tetris_m.cBlock_loc){
            p.y++;
        }
    }

    // 현재 게임 상태를 패널에 업데이트시킨다. line clearing을 처리한다.
    private void update_stat(){
        // 블록의 위치 표시는 두 가지 방식 가능
        // 한 개 기준점 중심 상대 위치와 절대 위치, 전자는 이후 오버헤드가 크므로 후자로 한다.

        int block_color = Tetris_m.cBlock / 10;
        // 1 : light gray   2 : red     3 : blue
        // 4 : green        5 : purple

        // 바닥을 친 경우 stat의 일부로 보고 이후의 line clearing의 적용 대상이 되도록 한다.
        if(Tetris_m.hit_floor_or_block){
            for(uPoint p : Tetris_m.cBlock_loc){
                Tetris_m.status[p.y][p.x] = block_color;
            }
        }

        // 여기에서 g_panel revalidate하기 전 stat의 모든 행을 한 번 확인한다. 확인 후 한 행이 모두 0 이 아닌 경우 그 행을 날린다.
        // tmp_stat에 저장해 둔 뒤 이를 원래 행렬에 덮어씌운다.
        int line_clear = 0;
        int[][] tmp_stat = new int[35][20];
        int m = 34, n = 0;
        for(int i = 34; i >= 0; i--){
            for(int j = 0; j < 20; j++){
                tmp_stat[m][n] = Tetris_m.status[i][j];
                n++;
                if(Tetris_m.status[i][j] != 0) line_clear++;
            }
            if(line_clear != 20) m--;
            else{
                Tetris_m.score++;
                Tetris_m.s_label.setText(Integer.toString(Tetris_m.score));
            }
            line_clear = 0;
            n = 0;
        }
        Tetris_m.status = tmp_stat;

        // 바닥을 치지 않은 경우 stat의 일부로 보지 않고 마지막에 추가하여 line clearing의 대상이 되지 않도록 한다.
        if(!Tetris_m.hit_floor_or_block){
            for(uPoint p : Tetris_m.cBlock_loc){
                Tetris_m.status[p.y][p.x] = block_color;
            }
        }
    }

    // hit_floor_or_block == true였을 경우 -> 다음 블록을 업데이트 한다. 업데이트된 블록은 다음 loop에 패널에 반영
    private void update_nBlock_and_update_stat(){
        // cBlock을 update_stat 이전에 바꾸는 경우 코드가 꼬이므로 update_stat 이후에 바뀌도록 한다.
        // cBlock_loc은 물론 stat도 업데이트 하되 여기서 충돌이 나는 경우 game over 이다.
        //int nBlockShape = (int)(Math.random()*5) + 1; // 0부터 4의 난수
        Tetris_m.cBlock_loc.clear();
        switch (Tetris_m.nBlock){
            case 1:
                Tetris_m.cBlock = 11;
                Tetris_m.cBlock_loc.add(new uPoint(9,0));
                Tetris_m.cBlock_loc.add(new uPoint(10,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(10,1));
                break;
            case 2:
                Tetris_m.cBlock = 21;
                Tetris_m.cBlock_loc.add(new uPoint(9,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(9,2));
                Tetris_m.cBlock_loc.add(new uPoint(10,2));
                break;
            case 3:
                Tetris_m.cBlock = 31;
                Tetris_m.cBlock_loc.add(new uPoint(10,0));
                Tetris_m.cBlock_loc.add(new uPoint(10,1));
                Tetris_m.cBlock_loc.add(new uPoint(10,2));
                Tetris_m.cBlock_loc.add(new uPoint(9,2));
                break;
            case 4:
                Tetris_m.cBlock = 41;
                Tetris_m.cBlock_loc.add(new uPoint(9,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(9,2));
                Tetris_m.cBlock_loc.add(new uPoint(9,3));
                break;
            case 5:
                Tetris_m.cBlock = 51;
                Tetris_m.cBlock_loc.add(new uPoint(10,0));
                Tetris_m.cBlock_loc.add(new uPoint(9,1));
                Tetris_m.cBlock_loc.add(new uPoint(10,1));
                Tetris_m.cBlock_loc.add(new uPoint(11,1));
                break;
            default:
                break;
        }
        if(uPoint.doesCollide(Tetris_m.cBlock_loc, Tetris_m.status)) Tetris_m.gameOver = true;
        Tetris_m.hit_floor_or_block = false; // 원위치

        Tetris_m.nBlock = (int)(Math.random()*5) + 1; // 다음 블록 설정
    }

    private void view_gameOver_frame(){

        Tetris_m.t_thread.interrupt(); // 먼저 timer_thread를 날린다.

        JFrame gameOver_frame = new JFrame();
        gameOver_frame.setTitle("Warning!");
        gameOver_frame.setSize(300, 200);
        gameOver_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOver_frame.setLayout(null);

        // TODO : gameOver message, score, time
        JLabel l1 = new JLabel();
        l1.setText("Game Over");
        l1.setFont(new Font("Arial Bold", Font.ITALIC, 20));
        l1.setBounds(50,0,200,50);
        l1.setHorizontalAlignment(JLabel.CENTER);
        //l1.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(l1);

        JLabel l2 = new JLabel();
        l2.setText("Time : " + Tetris_m.time_spent);
        l2.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        l2.setBounds(0,50,150,50);
        l2.setHorizontalAlignment(JLabel.CENTER);
        l2.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(l2);

        JLabel l3 = new JLabel();
        l3.setText("Score : " + Tetris_m.score);
        l3.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        l3.setBounds(150,50,150,50);
        l3.setHorizontalAlignment(JLabel.CENTER);
        l3.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(l3);

        // TODO : restart, exit btn
        JButton restart_btn = new JButton();
        restart_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameOver_frame.setVisible(false);
                Tetris_m.restart(); // shared 전역변수 초기화
                // IllegalThreadStateException 제거, 하나의 thread를 여러번 start하지 않도록 새로운 thread 생성
                Tetris_m.g_thread = new game_thread();
                Tetris_m.t_thread = new timer_thread();
                Tetris_m.g_thread.start();
                Tetris_m.t_thread.start();
            }
        });
        restart_btn.setText("Restart");
        restart_btn.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        restart_btn.setBounds(0,125,100,50);
        restart_btn.setHorizontalAlignment(JLabel.CENTER);
        restart_btn.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(restart_btn);

        JButton exit_btn = new JButton();
        exit_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit_btn.setText("Exit");
        exit_btn.setFont(new Font("Arial Bold", Font.PLAIN, 15));
        exit_btn.setBounds(150,125,100,50);
        exit_btn.setHorizontalAlignment(JLabel.CENTER);
        exit_btn.setBorder(new LineBorder(Color.BLACK));
        gameOver_frame.add(exit_btn);

        gameOver_frame.setVisible(true);
        gameOver_frame.setResizable(false);
        gameOver_frame.setLocationRelativeTo(null);
    }

}
