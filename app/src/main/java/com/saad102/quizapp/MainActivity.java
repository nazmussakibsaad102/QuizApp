package com.saad102.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private Button btnTrue;
    private Button btnFalse;
    private int mQuestion;
    private ProgressBar mProgressBar;
    private TextView mQuizStat;
    private int mUserScore;
    private final String USERSCORE_KEY = "Score Key";
    private static final String USERQUSTION_KEY = "Indwx Key";


    private QuizModel[] questionCollection = new QuizModel[]{
            new QuizModel(R.string.q1, true),
            new QuizModel(R.string.q2, false),
            new QuizModel(R.string.q3, true),
            new QuizModel(R.string.q4, true),
            new QuizModel(R.string.q5, false),
            new QuizModel(R.string.q6, false),
            new QuizModel(R.string.q7, false),
            new QuizModel(R.string.q8, true),
            new QuizModel(R.string.q9, false),
            new QuizModel(R.string.q10, true),
    };
    final int USER_PROGRESS =(int) Math.ceil(100.0 / questionCollection.length);
    private int mQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mUserScore = savedInstanceState.getInt(USERSCORE_KEY);
            mQuestionIndex = savedInstanceState.getInt(USERQUSTION_KEY);
        }else {
            mUserScore = 0;
        mQuestionIndex = 0;
        }

        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);
        txtQuestion = findViewById(R.id.txtQuestion);
        mProgressBar = findViewById(R.id.quizPB);
        mQuizStat = findViewById(R.id.txtQuizStat);
        mQuizStat.setText(mUserScore + "");
        QuizModel question = questionCollection[mQuestionIndex];
        mQuestion = question.getQuestion();
        txtQuestion.setText(mQuestion);

        View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnTrue){
                    evaluateUserAnswer(true);
                    getQuestionChangeOnClick();
                    mQuizStat.setText(mUserScore + "");


                }
                else if(view.getId() == R.id.btnFalse){
                    evaluateUserAnswer(false);
                    getQuestionChangeOnClick();
                }
            }
        };

        btnTrue.setOnClickListener(myClickListener);
        btnFalse.setOnClickListener(myClickListener);



    }
    private void getQuestionChangeOnClick(){
        mQuestionIndex = (mQuestionIndex + 1) % 10;
        if (mQuestionIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setCancelable(false);
            alert.setTitle("Game Over");
            alert.setMessage("Your score is: " + mUserScore);
            alert.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }
        QuizModel question = questionCollection[mQuestionIndex];
        mQuestion = question.getQuestion();
        txtQuestion.setText(mQuestion);
        mProgressBar.incrementProgressBy(USER_PROGRESS);
        mQuizStat.setText(mUserScore + "");

    }
    private void evaluateUserAnswer(boolean userGuess){
        boolean currentQuestionAnswer = questionCollection[mQuestionIndex].isAnswer();
        if (currentQuestionAnswer == userGuess){
            Toast.makeText(getApplicationContext(), R.string.correct_text, Toast.LENGTH_SHORT).show();
            mUserScore = mUserScore + 1;
        }else if(currentQuestionAnswer != userGuess) {
            Toast.makeText(getApplicationContext(), R.string.incorrect_text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(USERSCORE_KEY, mUserScore);
        outState.putInt(USERQUSTION_KEY, mQuestionIndex);
    }
}