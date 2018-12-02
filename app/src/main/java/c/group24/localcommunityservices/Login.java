package c.group24.localcommunityservices;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_process);
        Button login = findViewById(R.id.loginl);
        Button goRegister = findViewById(R.id.registerl);
        final EditText edEmail = ((EditText)findViewById(R.id.logEmail));
        final EditText edPass = ((EditText)findViewById(R.id.logPass));

        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goReg = new Intent(Login.this,Register.class);
                startActivity(goReg);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "Please wait...", "Proccessing...", true);

                (FirebaseAuth.getInstance().signInWithEmailAndPassword(edEmail.getText().toString(), edPass.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if (task.isSuccessful() && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
                                    int sp = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().indexOf(':');
                                    String identity = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().substring(0,sp);
                                    if(identity.equals("Student")){
                                        Intent goToStudentPg = new Intent(Login.this, StudentMainActivity.class);
                                        startActivity(goToStudentPg);

                                    }else{
                                        Intent goToOrg = new Intent();
                                    }
                                }
                                else if (!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                    Intent goVerify = new Intent(Login.this,EmailVerificatiion.class);
                                    startActivity(goVerify);
                                }
                                else{
                                    Log.e("ERROR", task.getException().toString());
                                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

}
