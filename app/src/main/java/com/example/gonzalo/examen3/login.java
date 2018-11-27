package com.example.gonzalo.examen3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class login extends AppCompatActivity {

    private static final String TAG = "";
    private EditText inputEmail, inputPassword;
    private TextInputLayout usernameWrapper, passwordWrapper;
    private ImageView img;

    private FirebaseAuth mAuth;

    SignInButton button;
    private final static int RC_SIGN_IN = 123;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(mAuthListner);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        //check the current user
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(login.this, Principal.class));
            finish();
        }
        inputEmail = (EditText) findViewById(R.id.edtUsuario);
        inputPassword = (EditText) findViewById(R.id.edtPass);
        usernameWrapper = findViewById(R.id.tiluser);
        passwordWrapper = findViewById(R.id.tilPass);
        Button ahlogin = (Button) findViewById(R.id.btn);
        img = findViewById(R.id.imageView);
        Picasso.get().load("https://www.fmdos.cl/wp-content/uploads/2018/01/GettyImages-543565980-e1516223290559.jpg").into(img);

        mAuth = FirebaseAuth.getInstance();

        // Checking the email id and password is Empty
        ahlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                ComprobarVacios(email, password);
            }
        });


        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(login.this, Principal.class));
                }
            }
        };
    }

    private void ComprobarVacios(String usuario, String pass)
    {
        passwordWrapper.setError(null);
        usernameWrapper.setError(null);
        if (!usuario.equals("") && !pass.equals(""))
        {
            passwordWrapper.setErrorEnabled(false);
            usernameWrapper.setErrorEnabled(false);
            mAuth.signInWithEmailAndPassword(usuario, pass)
                    .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // there was an error
                                Log.d(TAG, "signInWithEmail:success");
                                Intent intent = new Intent(login.this, Principal.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Log.d(TAG, "singInWithEmail:Fail");
                                Toast.makeText(login.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
        }
        else {
            passwordWrapper.setError("Fallor de llenar ambos campos");
            usernameWrapper.setError("Fallor de llenar ambos campos");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(login.this, "Aut Fail", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}