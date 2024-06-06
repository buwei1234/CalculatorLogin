package com.exmple.calculatorlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.exmple.calculatorlogin.databinding.ActivityLoginBinding;

import java.util.Locale;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String password = "123456";
    private String verifyCode;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnForget.setOnClickListener(view -> {
            if (binding.rbPassword.isChecked()) {
                Intent intent = new Intent(this, ForgetActivity.class);
                intent.putExtra("phone", binding.etPhone.getText().toString());
                register.launch(intent);

            } else {
                verifyCode = String.format(Locale.getDefault(), "%06d", new Random().nextInt(999999));
                Toast.makeText(LoginActivity.this, "本次验证码" + verifyCode, Toast.LENGTH_SHORT).show();
            }

        });

        binding.btnLogin.setOnClickListener(view -> {
            if (binding.etPhone.length() < 0) {
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binding.rbPassword.isChecked()) {
                if (!binding.etPassword.getText().toString().equals(password)) {
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }


            } else {
                if (!binding.etPassword.getText().toString().equals(verifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    password = intent.getStringExtra("password");
                }
            }
        });
        binding.rgLogin.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_password) {
                binding.tvPassword.setText("登录密码：");
                binding.etPassword.setHint("请输入密码");
                binding.btnForget.setText("忘记密码");
                binding.ckRemember.setVisibility(View.VISIBLE);

            } else {
                binding.tvPassword.setText("验证码：");
                binding.etPassword.setHint("请输入验证码");
                binding.ckRemember.setVisibility(View.INVISIBLE);
                binding.btnForget.setText("点击获取验证码");
            }
        });
    }
}