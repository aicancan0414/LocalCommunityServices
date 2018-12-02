package c.group24.localcommunityservices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class ProfileFragment extends Fragment {

    private ImageView imageView;
    private EditText nameView, ageView, workView, phoneView, emailView;
    private LinearLayout past_activities;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Student");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        View view = inflater.inflate(R.layout.user_layout, container, false);

        imageView = view.findViewById(R.id.profile_pic);
        nameView = view.findViewById(R.id.name);
        ageView = view.findViewById(R.id.age);
        workView = view.findViewById(R.id.work);
        phoneView = view.findViewById(R.id.phone);
        emailView = view.findViewById(R.id.email);
        past_activities = view.findViewById(R.id.user_activities);


        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                load_data(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return view;
    }

    private void load_data(DataSnapshot ds) {

        Map<String, String> map = (Map<String, String>) ds.getValue();
        if (map != null) {
            nameView.setText(map.get("Name"));
            ageView.setText(map.get("Age"));
            emailView.setText(map.get("Email"));
            workView.setText(map.get("Work"));
            phoneView.setText(map.get("Phone Number"));
        }

        // hide all views that are empty
        if (nameView.getText().toString().equals(""))
            nameView.setVisibility(View.GONE);
        if (ageView.getText().toString().equals(""))
            ageView.setVisibility(View.GONE);
        if (emailView.getText().toString().equals(""))
            emailView.setVisibility(View.GONE);
        if (workView.getText().toString().equals(""))
            workView.setVisibility(View.GONE);
        if (phoneView.getText().toString().equals(""))
            phoneView.setVisibility(View.GONE);


        // check if user have past activities
        int childCount = past_activities.getChildCount();
        if (childCount == 1)
            past_activities.setVisibility(View.GONE);
    }
}
