package jexam;

import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

class Question {
    public String title;
    public String description;
    public String start_data;
    public String end_data;
    public String p_mark;
    public String n_mark;
    public Vector<String> options;
    public  int correct_option;
}

class Exam {
    public String title;
    public String start_data;
    public String end_data;
    public boolean randomize;
    public Vector<Question> questions;
}

public class Main {

    public static void log(String t) {
        System.out.println(t);
    }

    public static void showError(String text, Component parent) {
        JOptionPane.showMessageDialog(parent, text, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void showInfo(String text, Component parent) {
        JOptionPane.showMessageDialog(parent, text, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static MongoDatabase db = null;

    public static void main(String[] args) {

        try {
//            if(System.getProperty("os.name").equals("Linux")) {
//                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//            }
            System.setProperty("awt.useSystemAAFontSettings","on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var login = new LoginForm();
        login.setMinimumSize(new Dimension(300,190));
        login.setVisible(true);

        MongoClient mongoClient = MongoClients.create(login.url);
        System.out.println("login done");

        db = mongoClient.getDatabase("jexams");

        var exams_list_dialog= new ExamsListDialog();
        exams_list_dialog.pack();
        exams_list_dialog.setVisible(true);
    }

    public static Vector<Exam> getExam() {

        var exams_collection = db.getCollection("exams");
        var exams = new Vector<Exam>();

        for(var doc : exams_collection.find()) {
            var exam = new Exam();
            exam.title = doc.getString("title");
            exam.start_data = doc.getString("start_date");
            exam.end_data = doc.getString("end_date");
            exam.randomize = doc.getBoolean("randomize");
            exam.questions = getQuestions(doc);
            exams.add(exam);
        }
        return exams;
    }

    static public Vector<Question> getQuestions(Document exam) {
        var questions = new Vector<Question>();
        var q_doc = exam.getList("questions", new Document().getClass() );
        for (var q : q_doc) {
            var q_ = new Question();
            q_.title = q.getString("title");
//            q_.options = new Vector(q.getList("options", new String().getClass()));
            questions.add(q_);
        }
        return questions;
    }
}