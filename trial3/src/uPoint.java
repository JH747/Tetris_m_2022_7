import java.util.List;

public class uPoint {
    int x;
    int y;

    public uPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean doesHave(int a, int b, List<uPoint> c){
        for(uPoint p : c){
            if(p.x == a && p.y == b) return true;
        }
        return false;
    }
}
