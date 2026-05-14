package ConsoleProjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class swings {

    JFrame frame;
    JLabel questionLabel, moneyLabel, title;
    JButton[] options = new JButton[4];
    JButton lifelineBtn, quitBtn;

    int current = 0;
    int money = 0;

    int[] rewards = {1000, 2000, 5000, 10000, 20000, 40000, 80000, 160000, 320000, 700000};

    boolean used5050 = false;
    boolean usedAudience = false;
    boolean usedSkip = false;

    Color bg = new Color(18,18,18);
    Color btnColor = new Color(40,40,40);

    Question[] questions = {
        new Question("Capital of India?", new String[]{"Mumbai","Delhi","Chennai","Kolkata"},1),
        new Question("2 + 2 = ?", new String[]{"3","4","5","6"},1),
        new Question("Java is?", new String[]{"Language","Animal","Car","Food"},0),
        new Question("Sun rises from?", new String[]{"West","East","North","South"},1),
        new Question("5 * 5 = ?", new String[]{"10","20","25","30"},2),
        new Question("HTML stands for?", new String[]{"Hyper Text Markup Language","High Text","Tool","None"},0),
        new Question("CSS used for?", new String[]{"Styling","Logic","DB","Server"},0),
        new Question("JS is?", new String[]{"Language","OS","Browser","DB"},0),
        new Question("1 byte = ?", new String[]{"4","8","16","32"},1),
        new Question("Largest planet?", new String[]{"Earth","Mars","Jupiter","Saturn"},2)
    };

    public swings() {

        frame = new JFrame("Quiz Game");
        frame.setSize(700,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(bg);

        // Title
        title = new JLabel("QUIZ GAME");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.CYAN);
        title.setFont(new Font("Arial",Font.BOLD,24));

        // Question
        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setForeground(new Color(255,215,0));
        questionLabel.setFont(new Font("Segoe UI",Font.BOLD,20));

        // Money
        moneyLabel = new JLabel("Money: ₹0");
        moneyLabel.setHorizontalAlignment(JLabel.CENTER);
        moneyLabel.setForeground(new Color(0,255,150));
        moneyLabel.setFont(new Font("Verdana",Font.BOLD,16));

        JPanel top = new JPanel(new GridLayout(3,1));
        top.setBackground(bg);
        top.add(title);
        top.add(questionLabel);
        top.add(moneyLabel);

        frame.add(top,BorderLayout.NORTH);

        // Options Panel
        JPanel panel = new JPanel(new GridLayout(2,2,15,15));
        panel.setBackground(bg);

        for(int i=0;i<4;i++){
            options[i]=new JButton();
            options[i].setBackground(btnColor);
            options[i].setForeground(Color.WHITE);
            options[i].setFont(new Font("Arial",Font.BOLD,14));
            options[i].setFocusPainted(false);
            options[i].setBorder(BorderFactory.createLineBorder(Color.GRAY,2));

            int index=i;
            options[i].addActionListener(e -> checkAnswer(index));

            // Hover effect
            options[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){
                    options[index].setBackground(new Color(70,70,70));
                }
                public void mouseExited(MouseEvent e){
                    options[index].setBackground(btnColor);
                }
            });

            panel.add(options[i]);
        }

        frame.add(panel,BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottom = new JPanel();
        bottom.setBackground(bg);

        lifelineBtn = new JButton("Lifeline");
        quitBtn = new JButton("Quit");

        styleButton(lifelineBtn,new Color(255,140,0));
        styleButton(quitBtn,new Color(220,20,60));

        lifelineBtn.addActionListener(e -> useLifeline());
        quitBtn.addActionListener(e -> quitGame());

        bottom.add(lifelineBtn);
        bottom.add(quitBtn);

        frame.add(bottom,BorderLayout.SOUTH);

        loadQuestion();
        frame.setVisible(true);
    }

    void styleButton(JButton btn, Color c){
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial",Font.BOLD,14));
        btn.setFocusPainted(false);
    }

    void loadQuestion(){
        if(current < questions.length){
            Question q = questions[current];
            questionLabel.setText("Q"+(current+1)+": "+q.question);

            for(int i=0;i<4;i++){
                options[i].setText(q.options[i]);
                options[i].setEnabled(true);
            }

            if(current>=8){
                lifelineBtn.setEnabled(false);
            }

        }else{
            JOptionPane.showMessageDialog(frame,"🎉 You won ₹"+money);
        }
    }

    void checkAnswer(int selected){
        if(selected == questions[current].correct){
            money = rewards[current];
            moneyLabel.setText("Money: ₹"+money);

            JOptionPane.showMessageDialog(frame,"Correct!");

            if(current==4){
                JOptionPane.showMessageDialog(frame,"Safe Point ₹20000 secured");
            }

            if(current==6){
                JOptionPane.showMessageDialog(frame,"Safe Point ₹80000 secured");
            }

            current++;
            loadQuestion();
        }else{
            wrongAnswer();
        }
    }

    void wrongAnswer(){
        if(current<5) money=0;
        else if(current<7) money=20000;
        else money=80000;

        JOptionPane.showMessageDialog(frame,"Wrong! You won ₹"+money);
        System.exit(0);
    }

    void quitGame(){
        JOptionPane.showMessageDialog(frame,"You quit with ₹"+money);
        System.exit(0);
    }

    void useLifeline(){

        if(current>=8){
            JOptionPane.showMessageDialog(frame,"No lifelines allowed!");
            return;
        }

        String[] ops={"50-50","Audience Poll","Skip"};

        int choice = JOptionPane.showOptionDialog(frame,"Choose Lifeline","Lifeline",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,ops,ops[0]);

        switch(choice){
            case 0:
                if(!used5050){
                    used5050=true;
                    apply5050();
                }else JOptionPane.showMessageDialog(frame,"Already used!");
                break;

            case 1:
                if(!usedAudience){
                    usedAudience=true;
                    audiencePoll();
                }else JOptionPane.showMessageDialog(frame,"Already used!");
                break;

            case 2:
                if(!usedSkip){
                    usedSkip=true;
                    current++;
                    loadQuestion();
                }else JOptionPane.showMessageDialog(frame,"Already used!");
                break;
        }
    }

    void apply5050(){
        int correct = questions[current].correct;
        Random r = new Random();

        int remove = r.nextInt(4);

        for(int i=0;i<4;i++){
            if(i!=correct && i==remove){
                options[i].setEnabled(false);
            }
        }
    }

    void audiencePoll(){
        Random r = new Random();
        String msg="";

        for(int i=0;i<4;i++){
            int p=r.nextInt(50);
            if(i==questions[current].correct) p+=50;
            msg += "Option "+(i+1)+": "+p+"%\n";
        }

        JOptionPane.showMessageDialog(frame,msg);
    }

    public static void main(String[] args){
        new swings();
    }
}

class Question{
    String question;
    String[] options;
    int correct;

    Question(String q,String[] o,int c){
        question=q;
        options=o;
        correct=c;
    }
}