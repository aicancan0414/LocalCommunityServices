package c.group24.localcommunityservices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EditProfileFragment extends Fragment{


    private ImageView imageView;
    private EditText nameView, ageView, workView, phoneView, emailView;
    private Button save;
    private LinearLayout past_activities;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String name, age, work, phone, email;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
        save = view.findViewById(R.id.save_button);


        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Test", "loading data");
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                name = map.get("Name");
                age = map.get("Age");
                phone = map.get("Phone Number");
                email = map.get("Email");
                work = map.get("Work");

                Log.d("Test", name);

                setViewEditable();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViews4Display();
                databaseReference.child(userID).child("Name").setValue(nameView.getText().toString());
                databaseReference.child(userID).child("Age").setValue(ageView.getText().toString());
                databaseReference.child(userID).child("Phone Number").setValue(phoneView.getText().toString());
                databaseReference.child(userID).child("Work").setValue(workView.getText().toString());
                databaseReference.child(userID).child("Email").setValue(emailView.getText().toString());

            }
        });

        return view;
    }

    private void setViewEditable() {

        //Hide all activities
        past_activities.setVisibility(View.GONE);
        //Unhide save button
        save.setVisibility(View.VISIBLE);

        nameView.setVisibility(View.VISIBLE);
        nameView.setFocusable(true);
        nameView.setClickable(true);
        nameView.setInputType(InputType.TYPE_CLASS_TEXT);

        ageView.setVisibility(View.VISIBLE);
        ageView.setFocusable(true);
        ageView.setClickable(true);
        ageView.setInputType(InputType.TYPE_CLASS_NUMBER);

        workView.setVisibility(View.VISIBLE);
        workView.setFocusable(true);
        workView.setClickable(true);
        workView.setInputType(InputType.TYPE_CLASS_TEXT);

        phoneView.setVisibility(View.VISIBLE);
        phoneView.setFocusable(true);
        phoneView.setClickable(true);
        phoneView.setInputType(InputType.TYPE_CLASS_PHONE);

        emailView.setVisibility(View.VISIBLE);
        emailView.setFocusable(true);
        emailView.setClickable(true);
        emailView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        // Set the editText with its original info
        nameView.setText(name);
        ageView.setText(age);
        workView.setText(work);
        phoneView.setText(phone);
        emailView.setText(email);
    }

    // Make all the views uneditable
    private void setViews4Display() {
        nameView.setFocusable(false);
        nameView.setClickable(false);
        nameView.setInputType(InputType.TYPE_NULL);

        ageView.setFocusable(false);
        ageView.setClickable(false);
        ageView.setInputType(InputType.TYPE_NULL);

        phoneView.setFocusable(false);
        phoneView.setClickable(false);
        phoneView.setInputType(InputType.TYPE_NULL);

        workView.setFocusable(false);
        workView.setClickable(false);
        workView.setInputType(InputType.TYPE_NULL);

        emailView.setFocusable(false);
        emailView.setClickable(false);
        emailView.setInputType(InputType.TYPE_NULL);

        save.setVisibility(View.GONE);
    }
}
