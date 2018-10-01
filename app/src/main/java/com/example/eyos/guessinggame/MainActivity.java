package com.example.eyos.guessinggame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mOne, mThree, mSix, mSeven, mZero;

    private EditText mAnswerOne, mAnswerTwo, mAnswerThree;

    private ArrayList<EditText> answerArray = new ArrayList<>();
    private ArrayList<EditText> suggetionArray = new ArrayList<>();

    private ArrayList<String> answerArrayStr = new ArrayList<>();
    private ArrayList<String> suggetionArrayStr = new ArrayList<>();

    private String answer = "136";
    private String enteredAnswer  = "";
    
    private String TAG = "0";

    private  int mAvilableAnswerIndex = 0 ;
    private  int mPressedIndex = 0 ;

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
        suggetionArray.add(mOne);  suggetionArray.add(mThree);  suggetionArray.add(mSix);  suggetionArray.add(mSeven);  suggetionArray.add(mZero);
    }

    @Override
    public void onClick(View v) {

        EditText et = (EditText)v;
        Object tag = et.getTag();
        String text = et.getText().toString();


        //if top layout
        if(tag != null) {
            // if answer is in place
            if (!TextUtils.isEmpty(text)) {
               // et.setText(""); //remove text

                //enter back to suggetion array
                et.setTextColor(Color.BLACK);
                et.setEnabled(true);
                suggetionArray.set(mPressedIndex, et).setTextColor(Color.BLACK);
                suggetionArray.set(mPressedIndex, et).setEnabled(true);


                //remove from answer\
                et.setText("");
                mAvilableAnswerIndex--;
                answerArray.set(mAvilableAnswerIndex, et);


            }
        }
        else{
            mPressedIndex = suggetionArray.indexOf(et);
            //remove from possible solution
            suggetionArray.set(mPressedIndex, et).setTextColor(Color.GRAY);
            suggetionArray.set(mPressedIndex, et).setEnabled(false);
         //   et.setEnabled(false);
          //  et.setTextColor(Color.GRAY);

            //insert to solution
            if(mAvilableAnswerIndex < answerArray.size() ){
                answerArray.set(mAvilableAnswerIndex, et).setText(text);
                mAvilableAnswerIndex++;
            }

            if (mAvilableAnswerIndex == answerArray.size()){
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



}
