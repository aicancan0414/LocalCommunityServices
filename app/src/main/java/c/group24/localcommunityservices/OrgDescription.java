package c.group24.localcommunityservices;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class OrgDescription extends Activity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference projectDatabase, studentDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String userID;

    private TextView name;
    private LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_description);
        firebaseDatabase = FirebaseDatabase.getInstance();
        projectDatabase = firebaseDatabase.getReference("Projects");
        studentDatabase = firebaseDatabase.getReference("Student");
        firebaseAuth = FirebaseAuth.getInstance();
        /*user = firebaseAuth.getCurrentUser();
        userID = user.getUid();*/

        //Set Header Text to the project name
        name = findViewById(R.id.orgName);
        Intent intent = getIntent();
        String project = "project1"; //intent.getStringExtra("project");
        name.setText(project);
        list = findViewById(R.id.studentList);

        //Find list of students associated with the project
        projectDatabase.child(project).child("students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                readList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void readList(DataSnapshot ds){
        Map <String, String> map = (Map <String, String>) ds.getValue();
        for (String uid : map.keySet()){
            final Button button = new Button(this);
            final String id = uid; //because in order to use in an inner class it must be final
            //button.setText(uid);
            //Get Student Name for the button
            studentDatabase.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map <String, String> map = (Map <String, String>) dataSnapshot.getValue();
                    if (map != null) {
                        button.setText(map.get("Name"));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            //make button transparent
            button.setBackgroundColor(Color.TRANSPARENT);

            //Start student profile activity when button is clicked
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrgDescription.this, ProfileFragment.class);
                    intent.putExtra("uid", id);
                    startActivity(intent);

                }
            });
            list.addView(button);
        }


    }
}
