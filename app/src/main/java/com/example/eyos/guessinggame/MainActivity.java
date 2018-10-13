package com.example.eyos.guessinggame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static int answerindex = 0;
    private ArrayList<EditText> answerArray = new ArrayList<>();
    private ArrayList<EditText> suggetionArray = new ArrayList<>();

    private Button mHelpBtn, mClearAnswerBtn;
    Context context = this;

    private String answer = "";
    private String enteredAnswer  = "";
    
    private String TAG = "0";

    private LinearLayout mAnswerLayout;
    private TableLayout mSuggetionLayout;
    private ImageView mImageView;

    private ArrayList<String> wordsArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordsArray = Utilities.readFileWords("words.txt",this);
        answer = wordsArray.get(answerindex); //should be according to picture number or something

        mAnswerLayout = findViewById(R.id.topLayout);
        mSuggetionLayout = findViewById(R.id.bottomLayout);
        mImageView = findViewById(R.id.imageView);

        mHelpBtn = findViewById(R.id.help_btn);

        Utilities.createAnswerArray(answer, answerArray, mAnswerLayout, this);
        Utilities.createSuggetionArray(answer, suggetionArray, mSuggetionLayout, this);
        mImageView.setImageDrawable(Utilities.getImage(this,answer));

        mClearAnswerBtn = findViewById(R.id.clear_answer_btn);

        mClearAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredAnswer = "";
               // for (int i = 0; i < answerArray.size(); i++) {
                    answerArray.clear(); mAnswerLayout.removeAllViews();
                    Utilities.createAnswerArray(answer, answerArray, mAnswerLayout, MainActivity.this);
               // }
            }
        });


        mHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //List of items to be show in  list
                final String[] items = {"HELP ONE","HELP TWO", "HELP THREE","HELP FOUR"};
                //create the builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //set the title for alert dialog
                builder.setTitle("Choose names: ");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(context, items[which] +  " is Selected", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setCancelable(false).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.popup_menu, menu);
//        return  true;
//    }

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
                insertIndex = Utilities.getEditTextIndex(suggetionArray, text, false);
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
            insertIndex = Utilities.getEditTextIndex(answerArray, "", true);
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
                    answerindex++;
                    answerArray.clear(); suggetionArray.clear(); mAnswerLayout.removeAllViews(); mSuggetionLayout.removeAllViews(); enteredAnswer = "";
                    answer = wordsArray.get(answerindex); //should be according to picture number or something
                    Utilities.createAnswerArray(answer, answerArray, mAnswerLayout, this);
                    Utilities.createSuggetionArray(answer, suggetionArray, mSuggetionLayout, this);
                    mImageView.setImageDrawable(Utilities.getImage(this,answer));

                }
                else {
                    Toast.makeText(this, "not good", Toast.LENGTH_LONG).show();
                    enteredAnswer = "";
                }
            }
        }
    }

    




}
