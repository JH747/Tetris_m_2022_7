import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class key_input implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        Tetris_m.k_code = e.getKeyCode();
        // up : 38      down : 40
        // left : 37    right : 39
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
