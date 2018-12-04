package c.group24.localcommunityservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class OrgProfileActivity extends Activity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userID;

    private EditText nameView, addressView, emailView, infoView;
    private RatingBar ratingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.org_layout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Organization");

        Intent intent = getIntent();
        userID = intent.getStringExtra("uid");
        nameView = findViewById(R.id.org_name);
        addressView = findViewById(R.id.address);
        emailView = findViewById(R.id.email);
        infoView = findViewById(R.id.org_info);
        ratingView = findViewById(R.id.ratingBar);

        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                load_data(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void load_data(DataSnapshot ds) {
        Map<String, String> map = (Map<String, String>) ds.getValue();
        if (map != null) {
            nameView.setText(map.get("Name"));
            addressView.setText(map.get("Address"));
            emailView.setText(map.get("Email"));
            infoView.setText(map.get("Description"));
        }

        if (nameView.getText().toString().equals(""))
            nameView.setVisibility(View.GONE);
        else
            nameView.setVisibility(View.VISIBLE);
        if (emailView.getText().toString().equals(""))
            emailView.setVisibility(View.GONE);
        else
            emailView.setVisibility(View.VISIBLE);
        if (addressView.getText().toString().equals(""))
            addressView.setVisibility(View.GONE);
        else
            addressView.setVisibility(View.VISIBLE);
        if (infoView.getText().toString().equals(""))
            infoView.setVisibility(View.GONE);
        else
            infoView.setVisibility(View.VISIBLE);
        /*
        if (map.get("Rating").equals("")) {
            ratingView.setVisibility(View.GONE);
            findViewById(R.id.noRating).setVisibility(View.VISIBLE);
        }
        else {
            ratingView.setRating(Float.parseFloat(map.get("Rating")));
            ratingView.setVisibility(View.GONE);
            findViewById(R.id.noRating).setVisibility(View.VISIBLE);
        }*/

    }
}
