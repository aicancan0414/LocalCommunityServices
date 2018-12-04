package c.group24.localcommunityservices;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class OrgProfileFragment extends Fragment{


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String userID;

    private EditText nameView, addressView, emailView, infoView;
    private RatingBar ratingView;
    private TextView noRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.org_layout, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Organization");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        nameView = view.findViewById(R.id.org_name);
        addressView = view.findViewById(R.id.address);
        emailView = view.findViewById(R.id.email);
        infoView = view.findViewById(R.id.org_info);
        ratingView = view.findViewById(R.id.ratingBar);
        noRating = view.findViewById(R.id.noRating);

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
        if (map.get("Rating").equals("")) {
            ratingView.setVisibility(View.GONE);
            noRating.setVisibility(View.VISIBLE);
        }
        else {
            ratingView.setRating(Float.parseFloat(map.get("Rating")));
            ratingView.setVisibility(View.GONE);
            noRating.setVisibility(View.VISIBLE);
        }


    }
}
