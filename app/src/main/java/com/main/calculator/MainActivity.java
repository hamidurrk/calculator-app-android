package com.main.calculator;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    EditText input1;
    EditText input2;
    TextView resultTv;
    MaterialButton bAdd, bSub;
    ImageButton bMul, bDiv;
    MaterialButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bDot, bNeg;
    MaterialButton bAllClear;
    ImageButton bDel, bFlip, bDown;
    int inputCell;
    String operation;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            evaluateInput();
        }
    };

    private android.os.Handler handler = new Handler(Looper.getMainLooper());
    private Runnable deleteRunnable = new Runnable() {
        @Override
        public void run() {
            delete();
            handler.postDelayed(this, 200);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputCell = 1;
        operation = "+";
        input1 = findViewById(R.id.input_1);
        input2 = findViewById(R.id.input_2);
        b0 = findViewById(R.id.b_0);
        b1 = findViewById(R.id.b_1);
        b2 = findViewById(R.id.b_2);
        b3 = findViewById(R.id.b_3);
        b4 = findViewById(R.id.b_4);
        b5 = findViewById(R.id.b_5);
        b6 = findViewById(R.id.b_6);
        b7 = findViewById(R.id.b_7);
        b8 = findViewById(R.id.b_8);
        b9 = findViewById(R.id.b_9);
        bDot = findViewById(R.id.b_dot);
        bNeg = findViewById(R.id.b_neg);
        bAdd = findViewById(R.id.b_add);
        bSub = findViewById(R.id.b_sub);
        bMul = findViewById(R.id.b_mul);
        bDiv = findViewById(R.id.b_div);

        bAllClear = findViewById(R.id.b_ac);
//        bClear = findViewById(R.id.b_c);

        bFlip = findViewById(R.id.b_flip);
        bDel = findViewById(R.id.b_del);
        bDown = findViewById(R.id.b_down);

        resultTv = findViewById(R.id.result_tv);

        disableSoftInputFromAppearing(input1);
        disableSoftInputFromAppearing(input2);

        input1.addTextChangedListener(textWatcher);
        input2.addTextChangedListener(textWatcher);

        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input1.setSelection(input1.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input2.setSelection(input2.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        b0.setOnClickListener(v -> addNumber("0"));
        b1.setOnClickListener(v -> addNumber("1"));
        b2.setOnClickListener(v -> addNumber("2"));
        b3.setOnClickListener(v -> addNumber("3"));
        b4.setOnClickListener(v -> addNumber("4"));
        b5.setOnClickListener(v -> addNumber("5"));
        b6.setOnClickListener(v -> addNumber("6"));
        b7.setOnClickListener(v -> addNumber("7"));
        b8.setOnClickListener(v -> addNumber("8"));
        b9.setOnClickListener(v -> addNumber("9"));
        bDot.setOnClickListener(v -> addNumber("."));
        bNeg.setOnClickListener(v -> addNumber("-"));

        bAdd.setOnClickListener(v -> changeOperation("+"));
        bSub.setOnClickListener(v -> changeOperation("-"));
        bMul.setOnClickListener(v -> changeOperation("*"));
        bDiv.setOnClickListener(v -> changeOperation("/"));

//        b_eq.setOnClickListener(v -> calculate());
        updateFocus();
        updateOperationUI();
        resultTv.setText("");

        bDown.setOnClickListener(v -> {
            inputCell = (inputCell == 1) ? 2 : 1;
            updateFocus();
        });

        input1.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                inputCell = 1;
                updateFocus();
            }
            return true;
        });

        input2.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                inputCell = 2;
                updateFocus();
            }
            return true;
        });

        bDel.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                handler.post(deleteRunnable); // Start deleting
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                handler.removeCallbacks(deleteRunnable); // Stop deleting
            }
            return true;
        });
        bAllClear.setOnClickListener(v -> {
            input1.setText("");
            input2.setText("");
            resultTv.setText("");
            inputCell = 1;
            updateFocus();
        });
//        bClear.setOnClickListener(v -> {
//            if (inputCell == 1) {
//                input1.setText("");
//            } else {
//                input2.setText("");
//            }
//            resultTv.setText("");
//        });
        bFlip.setOnClickListener(v -> {
            String temp = input1.getText().toString();
            input1.setText(input2.getText().toString());
            input2.setText(temp);
            updateResult();
            inputCell = (inputCell == 1) ? 2 : 1;
            updateFocus();
        });
    }

    private void updateFocus() {
        if (inputCell == 1) {
            input1.requestFocus();
            input2.clearFocus();
        } else {
            input2.requestFocus();
            input1.clearFocus();
        }
    }

    private void updateOperationUI() {
        bAdd.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));
        bSub.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));
        bMul.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));
        bDiv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));
        if (operation.equals("+")) {
            bAdd.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        } else if (operation.equals("-")) {
            bSub.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        } else if (operation.equals("*")) {
            bMul.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        } else if (operation.equals("/")) {
            bDiv.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        }

    }

    public void disableSoftInputFromAppearing(EditText editText) {
        editText.setShowSoftInputOnFocus(false);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);

        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editText.post(() -> {
                    ic.clearMetaKeyStates(Integer.MAX_VALUE);
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void addNumber(String number) {
        if (inputCell == 1) {
            String currentText = input1.getText().toString();
            input1.setText(currentText + number);
        } else {
            String currentText = input2.getText().toString();
            input2.setText(currentText + number);
        }
        updateResult();
    }

    public void changeOperation(String newOperation) {
        operation = newOperation;
        updateOperationUI();
        updateResult();
    }

    private void delete() {
        EditText focusedEditText = (inputCell == 1) ? input1 : input2;
        int length = focusedEditText.getText().length();
        if (length > 0) {
            focusedEditText.getText().delete(length - 1, length);
        }
    }

    @SuppressLint("SetTextI18n")
    public boolean evaluateInput() {
        String text1 = input1.getText().toString();
        String text2 = input2.getText().toString();
        boolean isText1Valid = true;
        boolean isText2Valid = true;

        try {
            if (!text1.isEmpty()){
                if (!(text1.charAt(0) == '-' && text1.length() == 1)) {
                    Double.parseDouble(text1);
                }
            }
        } catch (NumberFormatException e) {
            isText1Valid = false;
        }
        try {
            if (!text2.isEmpty()){
                if (!(text2.charAt(0) == '-' && text2.length() == 1)) {
                    Double.parseDouble(text2);
                }
            }
        } catch (NumberFormatException e) {
            isText2Valid = false;
        }
        if (!isText1Valid && !isText2Valid){
            resultTv.setText("Invalid");
            input1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            input2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            return false;
        }
        if (!isText1Valid) {
            resultTv.setText("Invalid");
            input1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            input2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
            return false;
        }
        if (!isText2Valid) {
            resultTv.setText("Invalid");
            input2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            input1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
            return false;
        }
        input1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
        input2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
        resultTv.setText("");
        return true;
    }

    public void updateResult() {
        String text1 = input1.getText().toString();
        String text2 = input2.getText().toString();
        if (!text1.isEmpty() && !text2.isEmpty()) {
            if (!evaluateInput()){
                return;
            }

            double number1 = Double.parseDouble(text1);
            double number2 = Double.parseDouble(text2);

            double result = 0;
            switch (operation) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
                case "/":
                    result = number1 / number2;
                    break;
            }
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedResult = df.format(result);

            if (formattedResult.endsWith(".00")) {
                formattedResult = formattedResult.substring(0, formattedResult.length() - 3);
            } else if(formattedResult.endsWith(".0")){
                formattedResult = formattedResult.substring(0, formattedResult.length() - 2);
            }

            resultTv.setText(formattedResult);
        } else {
            if (!resultTv.getText().equals("Invalid")){
                resultTv.setText("");
            }
        }
    }
}