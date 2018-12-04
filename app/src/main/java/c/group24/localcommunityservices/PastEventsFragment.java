package c.group24.localcommunityservices;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class PastEventsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<OpportunityListItem> list = new ArrayList<>();

    private DatabaseReference student, project;
    private String UID;
    private String date, description, orgID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_events, container, false);
        mRecyclerView = view.findViewById(R.id.pastOps);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FeedbackAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);

        project = FirebaseDatabase.getInstance().getReference("Projects");
        student = FirebaseDatabase.getInstance().getReference("Student");
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        populateList();

        return view;
    }

    private void populateList() {
        DatabaseReference ref = student.child(UID).child("Opportunities");
        if (ref != null) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    int y = c.get(Calendar.YEAR);
                    int m = c.get(Calendar.MONTH);
                    int d = c.get(Calendar.DAY_OF_MONTH);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String proj = snapshot.getKey();
                        project.child(proj).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                                date = map.get("date");
                                description = map.get("description");
                                orgID = map.get("orgID");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        if (date != null) {
                            int year = Integer.parseInt(date.substring(0, 4));
                            int month = Integer.parseInt(date.substring(5, 7));
                            int day = Integer.parseInt(date.substring(8));
                            if (year < y || (year == y && month < m) || (year == y && month == m && day < d)) {
                                list.add(new OpportunityListItem(proj, description, orgID));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        student.child(UID).child("Projects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Opportunity opportunity = snapshot.getValue(Opportunity.class);
                    String date = opportunity.getDate();
                    int year = Integer.parseInt(date.substring(0, 4));
                    int month = Integer.parseInt(date.substring(5, 7));
                    int day = Integer.parseInt(date.substring(8));
                    if (year < y || (year == y && month < m) || (year == y && month == m && day < d)) {
                        list.add(new OpportunityListItem("", opportunity.getDescription(), opportunity.getOrgID()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
