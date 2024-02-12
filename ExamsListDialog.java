package jexam;

import com.mongodb.client.model.Updates;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import static com.mongodb.client.model.Filters.eq;

public class ExamsListDialog extends JDialog {
    private JSplitPane splitPane1;
    private JList l_exams;

    private JList l_questions;

    private JButton b_create_exams;
    private JButton b_add_question;

    private Vector<Exam> exams;

    public ExamsListDialog() {
        setContentPane(splitPane1);
        setModal(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        splitPane1.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        refresh_list();

        //show questions of selected
        l_exams.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()){
//                refresh_list();
                 refresh_questions();
            }
        });

        b_add_question.addActionListener(e -> {
            var question_form = new QuestionForm(this);
            question_form.setVisible(true);
            if (question_form.doc == null) return;
            Main.db.getCollection("exams")
                .updateOne(eq("title", getSelectedExam().title), Updates.addToSet("questions", question_form.doc));
            //very bad code
            refresh_list();
            refresh_questions();
        });
        b_create_exams.addActionListener(e -> {
            var exams_form = new ExamForm();
            exams_form.pack();
            exams_form.setVisible(true);
            refresh_list();
            System.out.println(exams);
        });
        setMinimumSize(new Dimension(600, 300));
        setLocationRelativeTo(null);
    }

    Exam getSelectedExam(){
        if (l_exams.getSelectedValue() == null)
            return null;
        String selected = l_exams.getSelectedValue().toString();

        System.out.println("selected is " + selected);

        Exam selected_exam = null;
        for(var e : exams) {
            System.out.println("==selected is " + selected +" and e.title is " + e.title);
            if (e.title == selected) {
                selected_exam = e;
            }
        }
        return selected_exam;
    }

    void refresh_list() {
        exams = Main.getExam();
        DefaultListModel md_exams = new DefaultListModel();
        for(var e : exams) {
            System.out.println("== adding " + e.title);
            md_exams.addElement(e.title);
        }
        l_exams.setModel(md_exams);
    }

    void refresh_questions() {
        Exam selected_exam = getSelectedExam();
        DefaultListModel md_question = new DefaultListModel();
        if (selected_exam != null) {
            for(var q : selected_exam.questions) {
                md_question.addElement(q.title);
            }
        }
        l_questions.setModel(md_question);
    }

    public static void main(String[] args) {
        ExamsListDialog dialog = new ExamsListDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
