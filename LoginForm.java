package jexam;

import javax.swing.*;
import java.awt.event.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginForm extends JDialog {
    private JPanel contentPane;
    private JButton b_ok;
    private JButton b_cancel;
    private JTextField t_uname;
    private JPasswordField t_passwd;
    private JTextField t_host_adr;
    private JTextField t_host_port;

    public LoginForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(b_ok);

        //testing
        t_uname.setText("titan");
        t_passwd.setText("phobos");

        b_ok.addActionListener(e -> {
            var port =  t_host_port.getText();
            var addr = t_host_adr.getText();
            var uname = t_uname.getText();
            var passwd = t_passwd.getPassword();
            url = "mongodb://" + uname + ':' +
//                    URLEncoder.encode(passwd, StandardCharsets.UTF_8)
                    new String(passwd)
                    + '@' + addr + ':' + port;
            Main.log("connecting with url "+ url);
            dispose();
        });

        b_cancel.addActionListener(e -> dispose());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setLocationRelativeTo(null);
    }

    public String url;

    public static void main(String[] args) {
        LoginForm dialog = new LoginForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
