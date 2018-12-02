package c.group24.localcommunityservices;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpportunityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabaseRef;
    private ArrayList<OpportunityListItem> list = new ArrayList<>();
    //private Menu menuRef;



    public OpportunityFragment() {
        // Required empty public constructor
        // setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);

        //junk items (for right now) for list.
        //String[] items = {"Do something", "Do something else", "Another thing"};

        mRecyclerView = view.findViewById(R.id.oppsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mAdapter = new MyAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //System.out.println("IN ADD CHILD EVENT LISTENER");
                Opportunity opp = dataSnapshot.getValue(Opportunity.class);
                String description = opp.getDescription();
                list.add(new OpportunityListItem(dataSnapshot.getKey(), description));
                //mAdapter.notifyItemInserted(list.size()-1);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    System.out.println(dataSnapshot1);
                    list.add(dataSnapshot1.getKey());
                    //list.dataSnapshot1.toString();
                }

                mAdapter = new MyAdapter(list);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        return view;
    }

    public List<OpportunityListItem> getAllOpportunities() {
        return list;
    }

}