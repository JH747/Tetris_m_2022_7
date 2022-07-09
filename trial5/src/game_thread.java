public class game_thread extends Thread{

    //JFrame mf = Tetris_m.m_frame;

    public void run(){
        for(int i = 0; i < 1000; i++){

            if(i < 20) Tetris_m.status[34][i] = 1;
            //else Tetris_m.status[i-20][i-20] = 2;

            update_cBock_loc();
            update_shape_to_stat();

            //Tetris_m.g_panel = new game_panel(); // 이 부분이 문제. 새로운 패널을 만드는 것이 아니라 기존 패널을 revalidate해야 한다.
            Tetris_m.g_panel.revalidate();
            Tetris_m.g_panel.repaint(); //

            // 1. 현재는 여기서 하도록 되어있지만 나중에는 입력에 따라 즉시 동작할 수 있도록 수정해야함
            // 2. 아마 actionlistener로 입력을 받을 때 즉각 업데이트 할 수 있도록 해야하며 그러기 위해
            // revalidate, repaint를 특정 시간마다 호출하는 것이 아닌, 특정 사건마다 호출할 수 잇도록 해야한다.
            // 3. 아마 메인 thread에서 타이머를 잡고 이 시간을 따라가되 입력이 들어올 경우 즉시 반응하도록 구현 메인 class에 작성할 수도
            // 아니면 일정시간마다 revalidate하되 입력이 들어오는 경우도 revalidate하도록 따로 할 수도, 이 경우 synchronize가 필요할 것

            try{
                Thread.sleep(200);
            }
            catch(Exception e){}
        }
    }
    /*
    current block 위치를 다음 위치로 업데이트
    다음 위치에서 block 자신을 제외하고 충돌이 일어나는 경우 can_proceed를 false로 hit_floor_or_block을 true로 전환한다.
    충돌이 없는 경우 기존에 stat에 있던 block를 제거하고 자기 자신의 위치값을 업데이트한다.
    stat에 새 위치를 찍는 것은 update_shape_to_stat에서 진행한다.
     */
    private void update_cBock_loc(){
        boolean can_proceed = true;
        // 여기에서 현위치를 주고 다음 위치가 가능한 위치인지 아닌지 판단해 can_proceed를 정한다.

        // 그 뒤 bool 값을 토대로 true이면 기존 위치의 stat을 초기화하고 다음 위치에 찍지 않고 냅두며
        // false이면 hit_floor_or_block을 true로 바꾼다.
        for(uPoint p : Tetris_m.cBlock_loc){
            boolean collapse_self = false;

            // 최하단에 닿은 경우
            if(p.y == 34){
                can_proceed = false;
                break;
            }
            collapse_self = uPoint.doesHave(p.x, p.y+1, Tetris_m.cBlock_loc); // 자기 자신과 겹치는 것은 상관 없도록

            if(Tetris_m.status[p.y+1][p.x] != 0 && !collapse_self) can_proceed = false; // 아래에 무언가와 만나고 자신과 충돌한 것이 아닌 경우에만
        }
        if(!can_proceed){
            Tetris_m.hit_floor_or_block = true;
            return;
        }
        for(uPoint p : Tetris_m.cBlock_loc){
            Tetris_m.status[p.y][p.x] = 0; // 기존에 있던 위치의 stat 초기화
            p.y++;
        }
    }
    /*

     */
    private void update_shape_to_stat(){
        // 블록의 위치 표시는 두 가지,
        // 1. 블록당 20*35 크기의 배열을 하나씩 할당하여 그 위에서의 절대적 좌표를 잡는 방식 또는 pair 꼴로 그 좌표를 전부 저장
        // 2. 블록 기준점 x, y를 메인 클래스에 static 변수로 두고 그로부터 각 블록별로 모양에 따라 상대적 좌표를 잡아 계산하는 것.
        // 1은 블록의 좌표 표시 자체에는 오버헤드가 크나 2가 이후 연산에서 훨씬 큰 오버헤드를 내므로 1로 한다.

        // TODO : 이 아래 부분은 새 블록 추가하는 함수로 따로 구현 필요
        int block_color = Tetris_m.cBlock / 10;
        // 1 : light gray
        // 2 : red
        // 3 : blue
        // 4 : green
        // 5 : purple

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
            line_clear = 0;
            n = 0;
        }
        Tetris_m.status = tmp_stat;

        // 바닥을 치지 않은 경우 마지막에 추가하여 line clearing의 대상이 되지 않도록 한다.
        if(!Tetris_m.hit_floor_or_block){
            for(uPoint p : Tetris_m.cBlock_loc){
                Tetris_m.status[p.y][p.x] = block_color;
            }
        }
    }

}
