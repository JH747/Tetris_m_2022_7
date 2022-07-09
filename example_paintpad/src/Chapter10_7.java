
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Chapter10_7 extends JFrame implements ActionListener{

    public Chapter10_7(String title) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setTitle(title);
        settingMenu();
        setVisible(true);
    }
    public void settingMenu() {

        JMenu mainMenu = new JMenu("주메뉴");
        JMenu subMenu = new JMenu("바탕색상");
        JMenuItem yellowItem = new JMenuItem("노랑");
        yellowItem.addActionListener(this);
        JMenuItem redItem = new JMenuItem("빨강");
        redItem.addActionListener(this);
        JMenuItem grayItem =new JMenuItem("회색");
        grayItem.addActionListener(this);

        subMenu.add(yellowItem);
        subMenu.add(redItem);
        subMenu.add(grayItem);

        mainMenu.add(subMenu);
        JMenuBar menubar = new JMenuBar();
        menubar.add(mainMenu);
        setJMenuBar(menubar);
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Chapter10_7 c = new Chapter10_7("메뉴처리");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String str = e.getActionCommand();
        switch (str) {
            case "노랑":
                getContentPane().setBackground(Color.yellow);
                break;
            case "빨강":
                getContentPane().setBackground(Color.red);
                break;
            case "회색":
                getContentPane().setBackground(Color.gray);
                break;
            default:
                break;
        }
    }

}