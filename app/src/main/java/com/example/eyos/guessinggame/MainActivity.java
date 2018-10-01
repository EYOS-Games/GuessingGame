package com.example.eyos.guessinggame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mOne, mThree, mSix, mSeven, mZero;

    private EditText mAnswerOne, mAnswerTwo, mAnswerThree;

    private ArrayList<EditText> answerArray = new ArrayList<>();

    private String answer = "136";
    private String enteredAnswer  = "";
    
    private String TAG = "0";

    private  int mIndex = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOne = findViewById(R.id.editText17);
        mThree = findViewById(R.id.editText16);
        mSix = findViewById(R.id.editText15);
        mSeven = findViewById(R.id.editText14);
        mZero = findViewById(R.id.editText13);

        mAnswerOne = findViewById(R.id.editText12);
        mAnswerTwo = findViewById(R.id.editText11);
        mAnswerThree = findViewById(R.id.editText10);

        answerArray.add(mAnswerOne); answerArray.add(mAnswerTwo); answerArray.add(mAnswerThree);
    }

    @Override
    public void onClick(View v) {
        EditText et = (EditText)v;
        //remove from possible solution
        et.setEnabled(false);
        et.setBackgroundColor(Color.GRAY);
        String text = et.getText().toString();

        //insert to solution
        if(mIndex < answerArray.size() ){
            answerArray.set(mIndex, et).setText(text);
            mIndex++;
        }

        if (mIndex == answerArray.size()){
            for (int i = 0 ; i < answerArray.size(); i++ )
                enteredAnswer += answerArray.get(i).getText().toString();
            Log.d(TAG, "enteredAnswer: " + enteredAnswer);

            if (enteredAnswer.equals(answer) )
                Toast.makeText(this, "good", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "not good", Toast.LENGTH_LONG).show();
        }
    }
}
