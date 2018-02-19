package com.softuni.softunidemo;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class LoginManager {

    private static LoginManager instance;
    private final FirebaseAuth mAuth;

    public static LoginManager getInstance(){
        if(instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    private LoginManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean hasLoggedUser() {
        return mAuth.getCurrentUser() != null;
    }

    public void registerUserWithEmail(String email, String password, final AuthListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    public void loginUserWithEmail(String email, String password, final AuthListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    public String getUserEmail() {
        return mAuth.getCurrentUser().getEmail();
    }

    public void logout() {
        mAuth.signOut();
    }

    public interface AuthListener {
        void onSuccess();
        void onError();
    }
}
