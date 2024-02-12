package jexam;

import org.bson.Document;

import javax.print.Doc;
import javax.swing.*;
import java.util.Vector;

public class ExamForm extends JDialog {
    private JPanel contentPane;
    private JButton b_add_question;
    private JButton b_cancel;
    private JTextField t_title;
    private JCheckBox c_randomize;
    private JTextField t_start_time;
    private JTextField t_end_time;
    private JButton b_ok;

    public Vector<Document> questions = new Vector();

    public ExamForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(b_add_question);

        b_add_question.addActionListener(e -> {
            var new_question_dialog = new QuestionForm(this);
            new_question_dialog.setVisible(true);
            if(new_question_dialog.doc != null) {
                questions.add(new_question_dialog.doc);
            }
            Main.showInfo("question was added", this);
        });

        b_ok.addActionListener(e -> {
            var title = t_title.getText();
            var randomize = c_randomize.isSelected();
            var start_time = t_start_time.getText();
            var end_time = t_end_time.getText();

            //todo:- check for valid data

            var doc = new Document();
            doc.put("title", title);
            doc.put("randomize", randomize);
            doc.put("start_time", start_time);
            doc.put("end_time", end_time);
            doc.put("questions", questions);

//            try {
                Main.db.getCollection("exams").insertOne(doc);
                Main.showInfo("exam was inserted", this);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//                Main.showError("Failed to insert exam", this);
//            }
            dispose();
        });

        b_cancel.addActionListener(e -> dispose() );
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }


    public static void main(String[] args) {
        ExamForm dialog = new ExamForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
