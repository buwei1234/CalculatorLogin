package com.exmple.calculatorlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.exmple.calculatorlogin.databinding.ActivityForgetBinding;

import java.util.Locale;
import java.util.Random;

public class ForgetActivity extends AppCompatActivity {
    private ActivityForgetBinding binding;
    private String verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String phone = getIntent().getStringExtra("phone");

        binding.btnVerifycode.setOnClickListener(view -> {
            verifyCode = String.format(Locale.getDefault(), "%06d", new Random().nextInt(999999));
            Toast.makeText(ForgetActivity.this, phone + "本次验证码" + verifyCode, Toast.LENGTH_SHORT).show();
        });

        binding.btnConfirm.setOnClickListener(view -> {
            if (binding.etPasswordFirst.length() < 6 || binding.etPasswordSecond.length() < 6) {
                Toast.makeText(this, "请输入正确的新密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!binding.etPasswordFirst.getText().toString().equals(binding.etPasswordSecond.getText().toString())) {
                Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                return;

            }
            if (!binding.etVerifycode.getText().toString().equals(verifyCode)) {
                Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("password", binding.etPasswordSecond.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}