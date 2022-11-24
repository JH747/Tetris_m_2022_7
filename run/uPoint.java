import java.util.List;

public class uPoint {
    int x;
    int y;

    public uPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public static boolean doesHave(int a, int b, List<uPoint> c){
        for(uPoint p : c){
            if(p.x == a && p.y == b) return true;
        }
        return false;
    }

    public static boolean doesCollide(List<uPoint> c, int[][] arr){
        for(uPoint p : c){
            if(arr[p.y][p.x] != 0) return true;
        }
        return false;
    }

    public static uPoint make_new_uPoint_by_ref(int a, int b, uPoint p){
        return new uPoint(p.x + a, p.y + b);
    }
}
