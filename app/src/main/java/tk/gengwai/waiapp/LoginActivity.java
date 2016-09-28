package tk.gengwai.waiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister, btnForgetPassword;
    private EditText email, password;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String LOG_TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new LoginListener();

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);

        btnForgetPassword = (Button) findViewById(R.id.btn_forget_password);
        btnLogin.setOnClickListener(new BtnLoginOnClick());
        btnRegister.setOnClickListener(new BtnRegisterOnClick());
        btnForgetPassword.setOnClickListener(new BtnForgetPasswordOnClick());
    }

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

    class BtnLoginOnClick implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new LoginOnComplete());
        }
    }

    class BtnRegisterOnClick implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent createUserIntent = new Intent(context, CreateUserActivity.class);
            startActivity(createUserIntent);
        }
    }

    class BtnForgetPasswordOnClick implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                mAuth.sendPasswordResetEmail(email.getText().toString())
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, getText(R.string.email_wrong), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                btnForgetPassword.setEnabled(false);
                                Toast.makeText(context, getText(R.string.reset_password), Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(context, getText(R.string.email_wrong), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LoginOnComplete implements OnCompleteListener<AuthResult> {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            try {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
            } catch (FirebaseAuthException e) {
                switch (e.getErrorCode()) {
                    case "ERROR_INVALID_EMAIL":
                        Toast.makeText(context, R.string.email_invalid, Toast.LENGTH_SHORT).show();
                        break;
                    case "ERROR_USER_NOT_FOUND":
                    case "ERROR_WRONG_PASSWORD":
                        Toast.makeText(context, R.string.incorrect_password, Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (FirebaseNetworkException e) {
                Toast.makeText(context, getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                Log.e(LOG_TAG, e.toString());
            }
        }
    }

    class LoginListener implements FirebaseAuth.AuthStateListener {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                if (user.isEmailVerified()) {
                    final Intent chatIntent = new Intent(context, ChatActivity.class);
                    startActivity(chatIntent);
                } else {
                    Toast.makeText(context, getText(R.string.email_not_verified), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
