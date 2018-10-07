package com.example.student.project1;
//Ciara Pride
//csc 200
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private static final String MYTAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERED = "answered";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
    private ArrayList<Integer> mAnsweredQuestions = new ArrayList<>();


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_europe, true),
            new Question(R.string.question_japan, true),
            new Question(R.string.question_korea, false),
            new Question(R.string.question_hawaii, true)
    };

    private int mCurrentIndex=0;
    private int mNumberOfCorrectAnswers = 0;
    private int mNumberOfIncorrectAnswers = 0;


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
          super.onSaveInstanceState(saveInstanceState);
          saveInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        mAnsweredQuestions = saveInstanceState.getIntegerArrayList(KEY_ANSWERED);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(MYTAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MYTAG, "onPause() called");
        }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MYTAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MYTAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MYTAG, "onDestroy() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) mCurrentIndex=savedInstanceState.getInt(KEY_INDEX, 0);
        Log.d(MYTAG, "called onCreate()");
        setContentView(R.layout.activity_quiz);



        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
       updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast toast = Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show(); */
                checkAnswer(true);
                mTrueButton.setClickable(false);
                mFalseButton.setClickable(false);
                mAnsweredQuestions.add(mCurrentIndex);

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Toast toast = Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show(); */
                checkAnswer(false);
                mFalseButton.setClickable(false);
                mTrueButton.setClickable(false);
                mAnsweredQuestions.add(mCurrentIndex);


            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                mFalseButton.setClickable(true);
                mTrueButton.setClickable(true);
                updateQuestion();
            }

        });

        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentIndex = (mCurrentIndex + (mQuestionBank.length - 1)) % mQuestionBank.length;
                mFalseButton.setClickable(true);
                mTrueButton.setClickable(true);
                updateQuestion();
            }

        });

    }
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        if(mAnsweredQuestions.contains(mCurrentIndex)) {
            mTrueButton.setClickable(false);
            mFalseButton.setClickable(false);
        } else {
            mTrueButton.setClickable(true);
            mFalseButton.setClickable(true);
        }

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
          //  Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_LONG).show();
            mNumberOfCorrectAnswers += 1;
            messageResId = R.string.correct_toast;
        }
        else {
        //    Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_LONG).show();
            mNumberOfIncorrectAnswers += 1;
            messageResId = R.string.incorrect_toast;
        }


        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();

        Toast.makeText(
                QuizActivity.this,
                getString(R.string.amount_of_correct_answers) + Integer.toString(mNumberOfCorrectAnswers) + "\n" +
                        getString(R.string.percentage_of_correct_answers, percentageOfCorrectAnswers), Toast.LENGTH_LONG).show();
    }

}