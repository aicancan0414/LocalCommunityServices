package c.group24.localcommunityservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class StudentDescription extends Activity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference projectDatabase, studentDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String userID, orgID;

    private TextView name, description, location, requirements, date;
    private Button volunteer, viewProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_description);
        firebaseDatabase = FirebaseDatabase.getInstance();
        projectDatabase = firebaseDatabase.getReference("Projects");
        studentDatabase = firebaseDatabase.getReference("Student");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        //userID = "0Ai8COe2btWeeVPJMDT7kFEOGj02";

        name = findViewById(R.id.oppName);
        description = findViewById(R.id.description);
        location = findViewById(R.id.address);
        requirements = findViewById(R.id.requirements);
        date = findViewById(R.id.date);
        volunteer = findViewById(R.id.applyButton);
        viewProfile = findViewById(R.id.orgProfileButton);
        Intent intent = getIntent();

        final String project = intent.getStringExtra("project");
        name.setText(project);

        //Find information associated with the project
        projectDatabase.child(project).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setText(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getName(project);
                Toast.makeText(StudentDescription.this, "Successfully added to list of volunteers", Toast.LENGTH_SHORT).show();
            }
        });

        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDescription.this, OrgProfileActivity.class);
                intent.putExtra("uid", orgID);
                startActivity(intent);
            }
        });
    }

    private void setText(DataSnapshot ds){
        Map<String, String> map = (Map<String, String>) ds.getValue();

        if(map != null){
            description.setText(map.get("description"));
            location.setText(map.get("location"));
            requirements.setText(map.get("requirements"));
            date.setText(map.get("date"));
            orgID = map.get("orgID");
        }

    }

    private void getName(final String project){
        studentDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                if (map!= null) {
                    //volunteer.setText(map.get("Name"));
                    projectDatabase.child(project).child("students").child(userID).setValue(map.get("Name"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
