package c.group24.localcommunityservices;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
    private RecyclerView mRecyclerViewDone;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;
    private ArrayList<Opportunity> list = new ArrayList<>();
    private ArrayList<Opportunity> listDone = new ArrayList<>();

    private DatabaseReference student;
    private String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_events, container, false);
        mRecyclerView = view.findViewById(R.id.writeFeedback);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewDone = view.findViewById(R.id.pastOps);
        mRecyclerViewDone.setLayoutManager(new LinearLayoutManager(getActivity()));
        populateList();
        mAdapter = new EventAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapterDone = new EventAdapter(listDone);
        mRecyclerViewDone.setAdapter(mAdapterDone);
        mRecyclerViewDone.setHasFixedSize(true);
        setHasOptionsMenu(true);

        student = FirebaseDatabase.getInstance().getReference("Student");
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return view;
    }

    private void populateList() {
        student.child(UID).child("Project").addListenerForSingleValueEvent(new ValueEventListener() {
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
                        list.add(opportunity);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
