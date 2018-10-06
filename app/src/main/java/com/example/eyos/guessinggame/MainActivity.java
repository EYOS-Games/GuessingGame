package com.example.eyos.guessinggame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int answerindex = 0;
    private ArrayList<EditText> answerArray = new ArrayList<>();
    private ArrayList<EditText> suggetionArray = new ArrayList<>();

    private String answer = "";
    private String enteredAnswer  = "";
    
    private String TAG = "0";

    private LinearLayout mAnswerLayout;
    private TableLayout mSuggetionLayout;
    private ImageView mImageView;

    private String[] wordsArray;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordsArray = Utilities.readFileWords("words.txt",this);
        answer = wordsArray[answerindex].toLowerCase().trim(); //should be according to picture number or something

        mAnswerLayout = findViewById(R.id.topLayout);
        mSuggetionLayout = findViewById(R.id.bottomLayout);
        mImageView = findViewById(R.id.imageView);

        Utilities.createAnswerArray(answer, answerArray, mAnswerLayout, this);
        Utilities.createSuggetionArray(answer, suggetionArray, mSuggetionLayout, this);
        mImageView.setImageDrawable(Utilities.getImage(this,answer));
    }

    @Override
    public void onClick(View v) {

        EditText et = (EditText) v;
        Object tag = et.getTag();
        String text = et.getText().toString();
        EditText selectedSuggestion, selectedAnswer;
        int fetchIndex, insertIndex;
        //if answer array
        if (tag != null) {
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
            }
        }
        //if suggetion array
        else {
            //insert to answer array
            insertIndex = Utilities.getEditTextIndex(answerArray, "");
            selectedAnswer = answerArray.get(insertIndex);
            answerArray.set(insertIndex, selectedAnswer).setText(text);

            //remove from possible solution
            fetchIndex = suggetionArray.indexOf(et);
            suggetionArray.set(fetchIndex, et).setTextColor(Color.GRAY);
            suggetionArray.set(fetchIndex, et).setEnabled(false);

            if (Utilities.checkIfAnswerArrayIsFull(answerArray)) {
                for (int i = 0; i < answerArray.size(); i++)
                    enteredAnswer += answerArray.get(i).getText().toString();
                Log.d(TAG, "enteredAnswer: " + enteredAnswer);

                if (enteredAnswer.equals(answer))
                {
                    Toast.makeText(this, "good", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "not good", Toast.LENGTH_LONG).show();
                    enteredAnswer = "";
                }
            }
        }
    }

}
