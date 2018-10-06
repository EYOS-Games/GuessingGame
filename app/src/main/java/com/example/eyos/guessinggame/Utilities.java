package com.example.eyos.guessinggame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Utilities {

    private static final int DISPLAY_PXL = 40;
    private static final int NUM_OF_ROWS = 2;
    private static final int NUM_PER_ROW = 8;

    private static final int SUGGETION_ARR_SIZE = NUM_OF_ROWS * NUM_PER_ROW;

    static Random random = new Random();

    public static int getEditTextIndex(ArrayList<EditText> arrayList , String text, boolean getEnabled) {
        int index = -1;
        for (int i = 0; i < arrayList.size(); i++)
            if (arrayList.get(i).getText().toString().equals(text) && ( getEnabled == arrayList.get(i).isEnabled() ) ) {
                index = i;
                break;
            }

        //get  suggested in specific index
        return index;
    }

    public static boolean checkIfAnswerArrayIsFull(ArrayList<EditText> arrayList) {
        boolean ans = true;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getText().toString().equals(""))
                ans = false;
        }

        return ans;
    }

    public static void createAnswerArray(String answer, ArrayList<EditText> answerArray, LinearLayout layout, final MainActivity context) {
        for (int i = 0; i < answer.length(); i++) {
            EditText editText = new EditText(context);
            //pixel is 40dp of any display screen and than convert to int
            int pixels = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DISPLAY_PXL, context.getResources().getDisplayMetrics()));
            editText.setLayoutParams(new ViewGroup.LayoutParams(pixels, ViewGroup.LayoutParams.WRAP_CONTENT));
            editText.setTag(i);
            editText.setGravity(Gravity.CENTER);
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

    public static void createSuggetionArray(String answer, ArrayList<EditText> answerArray, TableLayout layout, final MainActivity context) {
        char[] answerArr = answer.toCharArray();
        // layout.setGravity(Gravity.CENTER);
        for (int i = 0; i < NUM_OF_ROWS; i++) {

            //create ne row in table
            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            row.setGravity(Gravity.CENTER);

            for (int j = 0; j < NUM_PER_ROW; j++) {
                EditText editText = new EditText(context);

                //pixel is 40dp of any display screen and than convert to int
                int pixels = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics()));
                editText.setLayoutParams(new TableRow.LayoutParams(pixels, ViewGroup.LayoutParams.WRAP_CONTENT));
                editText.setGravity(Gravity.CENTER);
                editText.setFocusable(false);

                //    editText = addAnswerCharsToArray(answer, editText);

                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.onClick(v);
                    }
                });

                row.addView(editText);
                answerArray.add(editText);
            } //inner for
            layout.addView(row);
        } //outer for

        //insert answer to suggetion array in random places
        for (int i = 0; i < answer.length(); i++) {
            int randomIndex = random.nextInt(SUGGETION_ARR_SIZE);
            EditText editText = answerArray.get(randomIndex);

            if (editText.getText().toString().equals("")) {
                String c = answerArr[i] + "";
                editText.setText(c);
                answerArray.set(randomIndex, editText);
            } else
                i--;

        }

        for (int i = 0; i < SUGGETION_ARR_SIZE - answer.length(); i++) {
            int randomIndex = random.nextInt(SUGGETION_ARR_SIZE);
            EditText editText = answerArray.get(randomIndex);
            if (editText.getText().toString().equals("")) {
                String c = (char) (random.nextInt(26) + 'a') + "";
                editText.setText(c);
                answerArray.set(randomIndex, editText);
            } else
                i--;
        }
    }

    public static ArrayList<String> readFileWords(String path, final MainActivity context) {
        String  line = null;
        try {
            ArrayList<String> words = new ArrayList<>();
            BufferedReader bufferedReader = readInputStreamFile(path,context);
            while((line = bufferedReader.readLine()) != null) {
                line = line.toLowerCase().trim();
                words.add(line);
            }


            return words;
            //return word;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getImage(Context context, String name) {
        return context.getResources().getDrawable(context.getResources().getIdentifier(name, "drawable", context.getPackageName()));
    }



    //region Helpers functions

//    private static String readLineIgnoreCR(BufferedReader reader) {
//        try {
//            int c = 0;
//            String line = "";
//            while(c >= 0) {
//                c = reader.read();
//                if((char) c == '\r' || (char) c == '\t' || (char) c == ' ')
//                    continue;
//                else if((char) c == '\n')
//                    return line;
//                line += (char) c;
//            }
//            return line;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static BufferedReader readInputStreamFile(String path, MainActivity context){
        try{

            InputStream inputStream = context.getAssets().open(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-16");
            BufferedReader buffer = new BufferedReader(inputStreamReader);

            return buffer;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    //endregion


}
