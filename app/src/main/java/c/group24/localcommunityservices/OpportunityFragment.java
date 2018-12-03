package c.group24.localcommunityservices;


import android.app.SearchManager;
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
import android.widget.SearchView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpportunityFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabaseRef;
    private ArrayList<OpportunityListItem> list = new ArrayList<>();
    private SearchView searchView;
    //private Menu menuRef;



    public OpportunityFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);


        mRecyclerView = view.findViewById(R.id.oppsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mAdapter = new MyAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);




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

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem actionSetting = menu.findItem(R.id.action_settings);
        searchItem.setVisible(true);
        actionSetting.setVisible(false);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
    }

    public ArrayList<OpportunityListItem> getList() {
        return list;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s == null || s.trim().isEmpty()) {
            resetSearch();
            return false;
        }
        ArrayList<OpportunityListItem> filteredList = new ArrayList<OpportunityListItem>(list);
        for(OpportunityListItem item : list) {
            if(!item.descrition.toLowerCase().contains(s.toLowerCase()) && !item.title.toLowerCase().contains(s.toLowerCase())) {
                filteredList.remove(item);
            }
        }
        mAdapter = new MyAdapter(filteredList);
        mRecyclerView.setAdapter(mAdapter);
        return false;
    }

    private void resetSearch() {

        mAdapter = new MyAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

    }
}
