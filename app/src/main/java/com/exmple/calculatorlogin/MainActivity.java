package com.exmple.calculatorlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.exmple.calculatorlogin.databinding.ActivityMainBinding;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMainBinding binding;
    private String operator = ""; //操作符
    private String firstNum = ""; //前一个操作数
    private String nextNum = ""; //后一个操作数
    private String result = ""; // 当前计算结果
    private String showText = ""; //显示的文本内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnCancel.setOnClickListener(this);
        binding.btnClear.setOnClickListener(this);
        binding.btnEqual.setOnClickListener(this);
        binding.btnSqrt.setOnClickListener(this);
        binding.btnDivide.setOnClickListener(this);
        binding.btnDot.setOnClickListener(this);
        binding.btnEight.setOnClickListener(this);
        binding.btnFour.setOnClickListener(this);
        binding.btnMinus.setOnClickListener(this);
        binding.btnMultiply.setOnClickListener(this);
        binding.btnNine.setOnClickListener(this);
        binding.btnOne.setOnClickListener(this);
        binding.btnPlus.setOnClickListener(this);
        binding.btnSeven.setOnClickListener(this);
        binding.btnSix.setOnClickListener(this);
        binding.btnThree.setOnClickListener(this);
        binding.btnTwo.setOnClickListener(this);
        binding.btnZero.setOnClickListener(this);
        binding.btnFive.setOnClickListener(this);
    }

    public void onClick(View v) {
        int resid = v.getId();
        String inputText;
        if (resid == R.id.btn_sqrt) {
            inputText = "√";
        } else {
            inputText = ((TextView) v).getText().toString();
        }
        Log.d("sqrt", "resid=" + resid + ",inputText=" + inputText);
        if (resid == R.id.btn_clear) {
            clear("0");
        } else if (resid == R.id.btn_cancel) {
            if (operator.equals("=") == true){
                Toast.makeText(this, "没有可取消的数字了", Toast.LENGTH_SHORT).show();
                return;
            }
            if (operator.equals("") == true) {
                if (firstNum.length() == 1) {
                    firstNum = "0";
                } else if (firstNum.length() > 0) {
                    firstNum = firstNum.substring(0, firstNum.length() - 1);
                } else if (showText == "0") {
                    firstNum = "0";
                } else {
                    Toast.makeText(this, "没有可取消的数字了", Toast.LENGTH_SHORT).show();
                }
                showText = firstNum;
                binding.tvResult.setText(showText);
            } else {
                if (nextNum.length() == 1) {
                    nextNum = "";
                } else if (nextNum.length() > 0) {
                    nextNum = nextNum.substring(0, nextNum.length() - 1);
                } else {
                    Toast.makeText(this, "没有可取消的数字了", Toast.LENGTH_SHORT).show();
                    return;
                }
                showText = showText.substring(0, showText.length() - 1);
                binding.tvResult.setText(showText);
            }
        } else if (resid == R.id.btn_equal) {
            if (operator.length() == 0 || operator.equals("=") == true) {
                Toast.makeText(this, "请输入运算符", Toast.LENGTH_SHORT).show();
                return;
            } else if (nextNum.length() <= 0) {
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return;
            }
            if(operator == "√"){
                if(convertToDouble(firstNum) < 0){
                    Toast.makeText(this, "开根号的数值不能小于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                result = String.valueOf(Math.sqrt(convertToDouble(firstNum)));
                if((result.substring(result.length() - 2, result.length() - 1).equals(".") == true) && (result.substring(result.length() - 1).equals("0") == true)){
                    result = result.substring(0, result.length() - 2);
                }
                operator = inputText;
                showText = result;
                binding.tvResult.setText(showText);
            } else if (calculate() == true) {
                operator = inputText;
                showText = result;
                binding.tvResult.setText(showText);
            } else {
                return;
            }
        } else if(resid == R.id.btn_minus){
            if(operator.length() == 0 || operator.equals("=") == true || operator.equals("√") == true){
                operator = inputText;
                showText = showText + operator;
                binding.tvResult.setText(showText);
            } else if(operator.length() == 1) {
                operator = inputText;
                showText = showText.substring(0, showText.length()-1) + operator;
                binding.tvResult.setText(showText);
            }else{
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if(resid == R.id.btn_plus || resid == R.id.btn_divide || resid == R.id.btn_multiply){
            if(firstNum.length() <= 0){
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return;
            }
            if(operator.length() == 0 || operator.equals("=") == true || operator.equals("√") == true){
                operator = inputText;
                showText = showText + operator;
                binding.tvResult.setText(showText);
            } else if(operator.length() == 1) {
                operator = inputText;
                showText = showText.substring(0, showText.length()-1) + operator;
                binding.tvResult.setText(showText);
            }else{
                Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if(resid == R.id.btn_sqrt){
            if(showText == "0" || firstNum.equals("0") == true){
                firstNum = showText = "";
            }
            operator = inputText;
            showText = showText + operator;
            binding.tvResult.setText(showText);
        } else{
            if(operator.equals("=") == true){
                operator = "";
                firstNum = "";
                showText = "";
            }
            if(operator.equals("√")){
                firstNum = firstNum + inputText;
                nextNum = "";
            }
            if(resid == R.id.btn_dot){
                inputText = ".";
            }
            if(operator.equals("") == true){
                if(showText == "0" || firstNum.equals("0") == true){
                    firstNum = showText = "";
                }
                firstNum = firstNum + inputText;
            } else {
                nextNum = nextNum + inputText;
            }
            showText = showText + inputText;
            binding.tvResult.setText(showText);
        }
    }

    private double convertToDouble(String number){
        return Double.parseDouble(number);
    }

    private boolean calculate() {
        if (operator.equals("+") == true) {
            result = String.valueOf(Arith.add(convertToDouble(firstNum), convertToDouble(nextNum)));
        } else if (operator.equals("-") == true) {
            result = String.valueOf(Arith.sub(convertToDouble(firstNum), convertToDouble(nextNum)));
        } else if (operator.equals("×") == true) {
            result = String.valueOf(Arith.mul(convertToDouble(firstNum), convertToDouble(nextNum)));
        } else if (operator.equals("÷") == true) {
            if ("0".equals(nextNum)) {
                Toast.makeText(this, "被除数不能为零", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                result = String.valueOf(Arith.div(convertToDouble(firstNum), convertToDouble(nextNum)));
            }
        }
        if((result.substring(result.length() - 2, result.length() - 1).equals(".") == true) && (result.substring(result.length() - 1).equals("0") == true)){
            result = result.substring(0, result.length() - 2);
        }
        firstNum = result;
        nextNum = "";
        return true;
    }

    private void clear(String text){
        showText = text;
        binding.tvResult.setText(showText);
        operator = "";
        firstNum = "0";
        nextNum = "";
        result = "";
    }

    static class Arith {
        //默认除法运算精度
        private static final int DEF_DIV_SCALE = 10;

        //构造器私有，让这个类不能实例化
        private Arith() {
        }

        //提供精确的加法运算
        public static double add(double v1, double v2) {
            BigDecimal b1 = BigDecimal.valueOf(v1);
            BigDecimal b2 = BigDecimal.valueOf(v2);
            return b1.add(b2).doubleValue();
        }

        //提供精确的减法运算
        public static double sub(double v1, double v2) {
            BigDecimal b1 = BigDecimal.valueOf(v1);
            BigDecimal b2 = BigDecimal.valueOf(v2);
            return b1.subtract(b2).doubleValue();
        }

        //提供精确的乘法运算
        public static double mul(double v1, double v2) {
            BigDecimal b1 = BigDecimal.valueOf(v1);
            BigDecimal b2 = BigDecimal.valueOf(v2);
            return b1.multiply(b2).doubleValue();
        }

        //提供精确的除法运算
        public static double div(double v1, double v2) {
            BigDecimal b1 = BigDecimal.valueOf(v1);
            BigDecimal b2 = BigDecimal.valueOf(v2);
            return b1.divide(b2).doubleValue();
        }
    }
}