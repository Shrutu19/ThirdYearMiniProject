package jexam;

import javax.swing.*;
import java.awt.*;

public class Test extends JDialog {
    Test(JDialog parent) {
        super(parent, true);
        setMinimumSize(new Dimension(300,200));
        var btn = new JButton("click me");
        setContentPane(btn);
        btn.addActionListener(e -> {
            System.out.println("button clicked");
            var t2 = new Test(this);
            t2.setVisible(true);
        });
    }

    public static void main(String[] args) {
        var t1 = new Test(null);
        t1.setVisible(true);
        System.out.println("now exiting");
        System.exit(3);

    }
}
