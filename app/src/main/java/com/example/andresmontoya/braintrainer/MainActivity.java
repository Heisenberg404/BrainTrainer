package com.example.andresmontoya.braintrainer;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    Context contexto = this;
    ImageView imageView;
    ImageView imgIndicator;
    TextView txtOp;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    RelativeLayout gameRelativeLayout;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int location_correct;
    int score = 0;
    int number_question;
    TextView txtPoints;
    TextView txtTime;
    public void start(View view){
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);
        imageView.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.btnStart);
        imgIndicator = (ImageView)findViewById(R.id.imgIndicator);
        txtOp = (TextView)findViewById(R.id.textView3);
        btn0 = (Button)findViewById(R.id.btn0);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);

        txtPoints = (TextView)findViewById(R.id.txtPoints);
        txtTime = (TextView)findViewById(R.id.txtTime);
        playAgain();
    }

    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(location_correct))){

            score++;
            imgIndicator.setImageResource(R.drawable.brainsuccess);

        }
        else {
            imgIndicator.setImageResource(R.drawable.brainerror);
        }
        number_question++;
        txtPoints.setText(Integer.toString(score)+"/"+Integer.toString(number_question));
        generateQuestion();

    }

    public void generateQuestion(){
        Random random = new Random();
        int a = random.nextInt(99);
        int b = random.nextInt(99);
        txtOp.setText(Integer.toString(a)+" + "+Integer.toString(b));
        location_correct = random.nextInt(4);
        answers.clear();
        int incorrect_answer;
        for (int i = 0; i<4; i++){
            if(i == location_correct){
                answers.add(a+b);
            }
            else
            {
                incorrect_answer = random.nextInt(99);
                while (incorrect_answer == a+b){
                    incorrect_answer = random.nextInt(99);
                }
                answers.add(incorrect_answer);
            }
        }

        btn0.setText(Integer.toString(answers.get(0)));
        btn1.setText(Integer.toString(answers.get(1)));
        btn2.setText(Integer.toString(answers.get(2)));
        btn3.setText(Integer.toString(answers.get(3)));

    }

    public void playAgain(){
        score = 0;
        number_question = 0;
        txtTime.setText("30");
        txtPoints.setText("0/0");
        generateQuestion();
        new CountDownTimer(30100, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                txtTime.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                imgIndicator.setImageResource(R.drawable.brain);
                txtTime.setText("00");

                new SweetAlertDialog(contexto, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Time is over!")
                        .setContentText("your score is: "+Integer.toString(score)+"/"+Integer.toString(number_question))
                        .setConfirmText("Play again!!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                playAgain();
                            }
                        }).show();

            }
        }.start();
    }
}
