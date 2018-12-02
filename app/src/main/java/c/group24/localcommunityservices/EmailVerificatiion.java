package c.group24.localcommunityservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmailVerificatiion extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emailver);
        Button nextStep = findViewById(R.id.log);
        TextView verified = findViewById(R.id.verifyText);
        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
            verified.setText("Your email already verified, please go login");
        }
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String identity_pre = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    int index = identity_pre.indexOf(':');
                    String identity = identity_pre.substring(0,index);
                    String name = identity_pre.substring(index+1,identity_pre.length());
                    database.child(identity).child(uid).child("Name").setValue(name);
                    database.child(identity).child(uid).child("Email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    if(identity.equals("Student")) {
                        database.child(identity).child(uid).child("Age").setValue("");
                        database.child(identity).child(uid).child("Work").setValue("");
                        database.child(identity).child(uid).child("Phone Number").setValue("");
                    }else{
                        database.child(identity).child(uid).child("Number of Volunteers").setValue("");
                        database.child(identity).child(uid).child("Description").setValue("");
                        database.child(identity).child(uid).child("Feedback").setValue("");
                    }
                    Toast.makeText(EmailVerificatiion.this, "Email Verification successful", Toast.LENGTH_LONG).show();
                    Intent log = new Intent(EmailVerificatiion.this, Login.class);
                    startActivity(log);
                }else{
                    Toast.makeText(EmailVerificatiion.this, "Please verify your email address!", Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().getCurrentUser().reload();
                }

            }
        });


    }

}
