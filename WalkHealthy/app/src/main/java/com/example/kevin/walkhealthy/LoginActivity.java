package com.example.kevin.walkhealthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //Views and widget fields
    Button loginButton;
    EditText userEmailEdit, userPasswordEdit;

    //String fields
    String userEmailString, userPasswordString;

    //Firebase authentication fields
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assign ID's
        loginButton = (Button) findViewById(R.id.loginActivityButton);
        userEmailEdit = (EditText) findViewById(R.id.loginEmailEditText);
        userPasswordEdit = (EditText) findViewById(R.id.loginPasswordEditText);

        //Assign Instances

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null)
                {

                    startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));

                }
                else
                {

                }

            }
        };

        //On click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Perform login operation
                userEmailString = userEmailEdit.getText().toString().trim();
                userPasswordString = userPasswordEdit.getText().toString().trim();

                if(!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPasswordString))
                {

                    mAuth.signInWithEmailAndPassword(userEmailString, userPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));


                            }
                            else
                            {

                                Toast.makeText(LoginActivity.this, "User Login Failed", Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }

            }
        });



    }
}
