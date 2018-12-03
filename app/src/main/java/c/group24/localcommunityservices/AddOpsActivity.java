package c.group24.localcommunityservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddOpsActivity extends AppCompatActivity {

    private EditText mTitleText;
    private EditText mLocationText;
    private EditText mDescriptionText;
    private EditText mRequirementsText;
    private Date mDate;

    private FirebaseDatabase firebaseDatabase
    private DatabaseReference organization;
    private DatabaseReference project;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ops);

        findViewById(R.id.opsTitleLabel);
        findViewById(R.id.opsDateLabel);
        findViewById(R.id.opsLocationLabel);
        findViewById(R.id.opsDescriptionLabel);
        findViewById(R.id.opsRequirementsLabel);
        mTitleText = findViewById(R.id.opsTitle);
        mDate = findViewById(R.id.opsDate);
        mLocationText = findViewById(R.id.opsLocation);
        mDescriptionText = findViewById(R.id.opsDescription);
        mRequirementsText = findViewById(R.id.opsRequirements);

        final Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                organization = firebaseDatabase.getReference("Organization");
                project = firebaseDatabase.getReference("Projects");
                UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String title = mTitleText.getText().toString();
                project.child(title).push();
                project.child(title).child("contact").push().setValue(organization.child(UID).child("Email"));
                project.child(title).child("location").push().setValue(mLocationText.getText());
                project.child(title).child("description").push().setValue(mDescriptionText.getText());
                project.child(title).child("requirements").push().setValue(mRequirementsText.getText());
                finish();
            }
        });

        final Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText.setText("");
                mLocationText.setText("");
                mDescriptionText.setText("");
                mRequirementsText.setText("");
            }
        });

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
