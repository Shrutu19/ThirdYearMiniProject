package jexam;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class QuestionForm extends JDialog {
    private JPanel contentPane;
    private JButton b_submit;
    private JButton buttonCancel;
    private JTextField t_title;
    private JSpinner s_pmarks;
    private JSpinner s_nmarks;
    private JTextArea ta_desc;
    private JPanel p_options;
    private JButton addOptionButton;

    private ButtonGroup bg_options = new ButtonGroup();
//    private Vector<String> options = new Vector();

    public Document doc;

    public QuestionForm(JDialog parent) {
        super(parent,true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(b_submit);

        b_submit.addActionListener(e -> {
            doc = new Document();
            doc.put("title", t_title.getText());
            doc.put("p_marks", s_pmarks.getValue());
            doc.put("n_marks", s_nmarks.getValue());
            doc.put("desc", ta_desc.getText());

            //get options text and selected index
            Document options_doc = new Document();
            ArrayList<AbstractButton> listRadioButton = Collections.list(bg_options.getElements());
            Vector<String> options = new Vector();
            int index = -1;
            for (int i = 0; i < listRadioButton.size(); i++) {
                final var btn = listRadioButton.get(i);
                options.add(btn.getText());
                if (btn.isSelected()) {
                    index = i;
                }
            }
            options_doc.put("options",options);
            if(index == -1) {
                Main.showError( "no correct option is give", this );
                return;
            }
            options_doc.put("correct_option",index);
            dispose();
        });

        var options_layout = new BoxLayout(p_options,BoxLayout.Y_AXIS);
        p_options.setLayout(options_layout);

        buttonCancel.addActionListener(e -> dispose());

        addOptionButton.addActionListener(e -> {
            var new_option_dialog = new QuestionOptionForm(this);
            var title = new_option_dialog.title;
            if(title.isBlank() ||title.isBlank()) {
                return;
            }
            var new_option = new JRadioButton(title);
            p_options.add(new_option);
            bg_options.add(new_option);
            pack();
            repaint();
        });
        setLocationRelativeTo(null);
        pack();
    }

    public static void main(String[] args) {
//                JOptionPane.showMessageDialog(null, message);
            QuestionForm dialog = new QuestionForm(null);
            dialog.setModalityType(JDialog.ModalityType.DOCUMENT_MODAL);

            dialog.pack();
            dialog.setVisible(true);

        System.exit(12);
    }
}
