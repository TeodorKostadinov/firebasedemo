package com.softuni.softunidemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.softuni.softunidemo.R;
import com.softuni.softunidemo.data.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_email) EditText edtEmail;
    @BindView(R.id.edt_password) EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(LoginManager.getInstance().hasLoggedUser()) {
            redirectToMainScreen();
        }
    }

    @OnClick(R.id.btn_login)
    public void onLoginClicked() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        LoginManager.getInstance().loginUserWithEmail(email, password, new LoginManager.AuthListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "Logged user successfully!", Toast.LENGTH_SHORT).show();
                redirectToMainScreen();
            }

            @Override
            public void onError() {
                Toast.makeText(LoginActivity.this, "Logged user unsuccessfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClicked() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        LoginManager.getInstance().registerUserWithEmail(email, password, new LoginManager.AuthListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "Created user successfully!", Toast.LENGTH_SHORT).show();
                redirectToMainScreen();
            }

            @Override
            public void onError() {
                Toast.makeText(LoginActivity.this, "Created user failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToMainScreen() {
        startActivity(new Intent(this, Main2Activity.class));
        finish();
    }
}
