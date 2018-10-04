package com.example.eyos.guessinggame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mOne, mThree, mSix, mSeven, mZero;

    private EditText mAnswerOne, mAnswerTwo, mAnswerThree;

    private ArrayList<EditText> answerArray = new ArrayList<>();
    private ArrayList<EditText> suggetionArray = new ArrayList<>();

    private String answer = "761";
    private String enteredAnswer  = "";
    
    private String TAG = "0";

    private LinearLayout mAnswerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOne = findViewById(R.id.editText17);
        mThree = findViewById(R.id.editText16);
        mSix = findViewById(R.id.editText15);
        mSeven = findViewById(R.id.editText14);
        mZero = findViewById(R.id.editText13);

        mAnswerLayout = findViewById(R.id.topLayout);

        Utilities.createAnswerArray(answer, answerArray, mAnswerLayout, this);
//        mAnswerOne = findViewById(R.id.editText12);
//        mAnswerTwo = findViewById(R.id.editText11);
//        mAnswerThree = findViewById(R.id.editText10);

//        answerArray.add(mAnswerOne); answerArray.add(mAnswerTwo); answerArray.add(mAnswerThree);
        suggetionArray.add(mOne);  suggetionArray.add(mThree);  suggetionArray.add(mSix);  suggetionArray.add(mSeven);  suggetionArray.add(mZero);
    }

    @Override
    public void onClick(View v) {

        EditText et = (EditText)v;
        Object tag = et.getTag();
        String text = et.getText().toString();
        EditText selectedSuggestion, selectedAnswer;
        int fetchIndex, insertIndex;
        //if top layout
        if(tag != null) {
            // if answer is in place
            if (!TextUtils.isEmpty(text)) {

                 //insert back to suggetion array
                insertIndex = Utilities.getEditTextIndex(suggetionArray, text);
                selectedSuggestion = suggetionArray.get(insertIndex);
                suggetionArray.set(insertIndex, selectedSuggestion).setTextColor(Color.BLACK);
                suggetionArray.set(insertIndex, selectedSuggestion).setEnabled(true);


                //remove from answer array
                fetchIndex = answerArray.indexOf(et);
                answerArray.set(fetchIndex, et).setText("");

                //remove char from


            }
        }
        else{

            //insert to solution
          //  if(0 < answerArray.size() ){

                //insert to answer array
                insertIndex = Utilities.getEditTextIndex(answerArray, "");
                selectedAnswer = answerArray.get(insertIndex);
                answerArray.set(insertIndex, selectedAnswer).setText(text);

                //remove from possible solution
                fetchIndex = suggetionArray.indexOf(et);
                suggetionArray.set(fetchIndex, et).setTextColor(Color.GRAY);
                suggetionArray.set(fetchIndex, et).setEnabled(false);

           // }

            if (Utilities.checkIfAnswerArrayIsFull(answerArray)){
                for (int i = 0 ; i < answerArray.size(); i++ )
                    enteredAnswer += answerArray.get(i).getText().toString();
                Log.d(TAG, "enteredAnswer: " + enteredAnswer);

                if (enteredAnswer.equals(answer) )
                    Toast.makeText(this, "good", Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(this, "not good", Toast.LENGTH_LONG).show();
                    enteredAnswer = "";
                }

            }
        }


    }



}
