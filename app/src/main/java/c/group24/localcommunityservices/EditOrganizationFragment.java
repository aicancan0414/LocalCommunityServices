package c.group24.localcommunityservices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class EditOrganizationFragment extends Fragment{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String userID;

    private String name, numVolunteers, address, info, email;
    private EditText nameView, addressView, numVolunteerView, emailView, infoView;
    private Button save;
    private LinearLayout feedback;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.org_layout, container, false);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Student");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        nameView = view.findViewById(R.id.org_name);
        addressView = view.findViewById(R.id.address);
        emailView = view.findViewById(R.id.email);
        infoView = view.findViewById(R.id.org_info);
        feedback = view.findViewById(R.id.to_hide_feedback);
        save = view.findViewById(R.id.save_button);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Test", "loading data");
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                name = map.get("Name");
                numVolunteers = map.get("Number of Volunteers");
                address = map.get("Address");
                email = map.get("Email");
                info = map.get("Description");

                setViewEditable();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView4Display();

                databaseReference.child(userID).child("Name").setValue(nameView.getText().toString());
                databaseReference.child(userID).child("Description").setValue(infoView.getText().toString());
                databaseReference.child(userID).child("Address").setValue(addressView.getText().toString());
                databaseReference.child(userID).child("Email").setValue(emailView.getText().toString());
            }
        });


        return view;
    }

    private void setViewEditable() {

        //Hide feedback
        feedback.setVisibility(View.GONE);
        //Unhide save button
        save.setVisibility(View.VISIBLE);

        nameView.setVisibility(View.VISIBLE);
        nameView.setFocusable(true);
        nameView.setClickable(true);
        nameView.setInputType(InputType.TYPE_CLASS_TEXT);

        addressView.setVisibility(View.VISIBLE);
        addressView.setFocusable(true);
        addressView.setClickable(true);
        addressView.setInputType(InputType.TYPE_CLASS_NUMBER);

        infoView.setVisibility(View.VISIBLE);
        infoView.setFocusable(true);
        infoView.setClickable(true);
        infoView.setInputType(InputType.TYPE_CLASS_TEXT);

        emailView.setVisibility(View.VISIBLE);
        emailView.setFocusable(true);
        emailView.setClickable(true);
        emailView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


        // Set the textView with their original text
        nameView.setText(name);
        addressView.setText(address);
        infoView.setText(info);
        emailView.setText(email);
    }

    private void setView4Display() {

        nameView.setFocusable(false);
        nameView.setClickable(false);
        nameView.setInputType(InputType.TYPE_NULL);


        addressView.setFocusable(false);
        addressView.setClickable(false);
        addressView.setInputType(InputType.TYPE_NULL);


        infoView.setFocusable(false);
        infoView.setClickable(false);
        infoView.setInputType(InputType.TYPE_NULL);


        emailView.setFocusable(false);
        emailView.setClickable(false);
        emailView.setInputType(InputType.TYPE_NULL);


        save.setVisibility(View.GONE);

        // Set EditView invisible if it is empty
        if (nameView.getText().toString().equals(""))
            nameView.setVisibility(View.GONE);
        if (emailView.getText().toString().equals(""))
            emailView.setVisibility(View.GONE);
        if (addressView.getText().toString().equals(""))
            addressView.setVisibility(View.GONE);
        if (infoView.getText().toString().equals(""))
            infoView.setVisibility(View.GONE);
    }
}
