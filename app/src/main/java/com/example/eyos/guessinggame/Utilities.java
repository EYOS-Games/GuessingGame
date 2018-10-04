package com.example.eyos.guessinggame;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Utilities {

    public static int getEditTextIndex(ArrayList<EditText> arrayList, String text){
        int index = -1;
        for (int i = 0 ; i < arrayList.size(); i++)
            if (arrayList.get(i).getText().toString().equals(text))
            {
                index = i;
                break;
            }

        //get  suggested in specific index
        return index;
    }

    public  static boolean checkIfAnswerArrayIsFull(ArrayList<EditText> arrayList){
        boolean ans = true;
        for (int i = 0; i < arrayList.size() ; i++) {
            if (arrayList.get(i).getText().toString().equals(""))
                ans = false;
        }

        return ans;
    }

    public static void createAnswerArray(String answer, ArrayList<EditText> answerArray, LinearLayout layout, final MainActivity context){
        for (int i = 0; i < answer.length(); i++) {
            EditText editText = new EditText(context);
//            editText.setHeight(40);
//            editText.setWidth(layout.getWidth());

            //pixel is 40dp of any display screen and than convert to int
            int pixels = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics()));
            editText.setLayoutParams(new ViewGroup.LayoutParams(pixels,ViewGroup.LayoutParams.WRAP_CONTENT));
            editText.setTag(i);
            editText.setFocusable(false);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.onClick(v);
                }
            });

            layout.addView(editText);
            answerArray.add(editText);
        }
    }
}
