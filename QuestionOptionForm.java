package jexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuestionOptionForm extends JDialog {
    private JPanel contentPane;
    private JButton b_ok;
    private JButton b_cancel;
    private JTextField t_title;

    public String title;

    public QuestionOptionForm(JDialog parent) {
        super(parent, "Question Form", true) ;
        pack();

        setContentPane(contentPane);
        getRootPane().setDefaultButton(b_ok);

        b_ok.addActionListener(e -> {
            title = t_title.getText();
            if(title.isEmpty() || title.isBlank()) {
                JOptionPane.showMessageDialog(this, "title can't be empty or blank.");
            }
            else {
                dispose();
            }
        });

        b_cancel.addActionListener(e -> dispose());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        QuestionOptionForm dialog = new QuestionOptionForm(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
