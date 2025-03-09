package com.main.calculator;

import android.os.Bundle;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText input1;
    EditText input2;
    TextView resultTv;
    MaterialButton bAdd, bSub;
    ImageButton bMul, bDiv;
    MaterialButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bDot;
    MaterialButton bClear, bAllClear;
    ImageButton bDel, bFlip, bDown;
    int inputCell;
    String operation;

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
        bAdd = findViewById(R.id.b_add);
        bSub = findViewById(R.id.b_sub);
        bMul = findViewById(R.id.b_mul);
        bDiv = findViewById(R.id.b_div);

        bAllClear = findViewById(R.id.b_ac);
        bClear = findViewById(R.id.b_c);

        bFlip = findViewById(R.id.b_flip);
        bDel = findViewById(R.id.b_del);
        bDown = findViewById(R.id.b_down);

        resultTv = findViewById(R.id.result_tv);

        disableSoftInputFromAppearing(input1);
        disableSoftInputFromAppearing(input2);

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

        bAdd.setOnClickListener(v -> changeOperation("+"));
        bSub.setOnClickListener(v -> changeOperation("-"));
        bMul.setOnClickListener(v -> changeOperation("*"));
        bDiv.setOnClickListener(v -> changeOperation("/"));

//        b_eq.setOnClickListener(v -> calculate());
        updateFocus();
        resultTv.setText("");

        bDown.setOnClickListener(v -> {
            inputCell = (inputCell == 1) ? 2 : 1;
            updateFocus();
        });

        input1.setOnClickListener(v -> {
            inputCell = 1;
            updateFocus();
        });
        input2.setOnClickListener(v -> {
            inputCell = 2;
            updateFocus();
        });

        bDel.setOnClickListener(v -> {
            if (inputCell == 1) {
                if (input1.getText().length() > 0) {
                    input1.setText(input1.getText().toString().substring(0, input1.getText().length() - 1));
                }
            } else {
                if (input2.getText().length() > 0) {
                    input2.setText(input2.getText().toString().substring(0, input2.getText().length() - 1));
                }
            }
            updateResult();
        });
        bAllClear.setOnClickListener(v -> {
            input1.setText("");
            input2.setText("");
            resultTv.setText("");
            inputCell = 1;
            updateFocus();
        });
        bClear.setOnClickListener(v -> {
            if (inputCell == 1) {
                input1.setText("");
            } else {
                input2.setText("");
            }
            resultTv.setText("");
        });
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
        updateResult();
    }

    public void updateResult() {
        String text1 = input1.getText().toString();
        String text2 = input2.getText().toString();
        if (!text1.equals("") && !text2.equals("")) {
            double number1 = Double.NaN;
            try {
                number1 = Double.parseDouble(text1);
            } catch (NumberFormatException e) {
                resultTv.setText("First number invalid");
                return;
            }
            double number2 = Double.NaN;
            try {
                number2 = Double.parseDouble(text2);
            } catch (NumberFormatException e) {
                resultTv.setText("Second number invalid");
                return;
            }
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
            resultTv.setText("");
        }
    }
}