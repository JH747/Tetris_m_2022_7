import java.util.ArrayList;

public class cBlock_updates {

    public static boolean update_cBlock_loc_by_key(){


        for(uPoint p : Tetris_m.cBlock_loc){
            Tetris_m.status[p.y][p.x] = 0;
        }
        boolean need_repaint = true;
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
                    Tetris_m.cBlock_ref_point.x--;
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
                    Tetris_m.cBlock_ref_point.x++;
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
                    Tetris_m.cBlock_ref_point.y++;
                }
                break;
            case 38:
                // up invert
                spin_cBlock_by_ref();
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
                    Tetris_m.cBlock_ref_point.y++;
                    if(uPoint.doesCollide(Tetris_m.cBlock_loc, Tetris_m.status)){
                        for(uPoint p : Tetris_m.cBlock_loc) p.y--;
                        Tetris_m.cBlock_ref_point.y--;
                        break;
                    }
                }
                break;
            default:
                need_repaint = false;
                break;
        }

        Tetris_m.k_code = 0;
        return need_repaint;
    }

    public static void update_cBlock_loc_by_time(){

        // moved to here so that there may no need to compare if block collides itself
        for(uPoint p : Tetris_m.cBlock_loc){
            Tetris_m.status[p.y][p.x] = 0; // 
        }

        boolean can_proceed = true;

        for(uPoint p : Tetris_m.cBlock_loc){

            if(p.y == 34){
                can_proceed = false;
                Tetris_m.hit_floor_or_block = true;
                break;
            }
            if(Tetris_m.status[p.y+1][p.x] != 0) can_proceed = false;
        }


        if(!can_proceed){
            Tetris_m.hit_floor_or_block = true;
            return;
        }

        for(uPoint p : Tetris_m.cBlock_loc){
            p.y++;
        }
        Tetris_m.cBlock_ref_point.y++;
    }

    public static void spin_cBlock_by_ref(){

        ArrayList<uPoint> tmp_cBlock_loc = new ArrayList<>();
        int tmp_cBlock_shape;

        switch (Tetris_m.cBlock){
            case 21:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 22;
                break;
            case 22:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 23;
                break;
            case 23:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 24;
                break;
            case 24:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 21;
                break;
            case 31:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 32;
                break;
            case 32:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 33;
                break;
            case 33:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 34;
                break;
            case 34:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 31;
                break;
            case 41:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(2,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 42;
                break;
            case 42:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,-3,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,-2,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 41;
                break;
            case 51:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 52;
                break;
            case 52:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 53;
                break;
            case 53:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,-1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 54;
                break;
            case 54:
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,0,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(-1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(0,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_loc.add(uPoint.make_new_uPoint_by_ref(1,1,Tetris_m.cBlock_ref_point));
                tmp_cBlock_shape = 51;
                break;
            default:
                return;
        }
        for(uPoint p : tmp_cBlock_loc){
            if(p.x < 0 || p.x > 19 || p.y < 0 || p.y > 34) return;
        }
        if(uPoint.doesCollide(tmp_cBlock_loc, Tetris_m.status)) return;


        Tetris_m.cBlock_loc = tmp_cBlock_loc;
        Tetris_m.cBlock = tmp_cBlock_shape;

    }

}
