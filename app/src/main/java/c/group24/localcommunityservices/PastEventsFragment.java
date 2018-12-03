package c.group24.localcommunityservices;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class PastEventsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewDone;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapterDone;
    private DatabaseReference student;
    private ArrayList<OpportunityListItem> list = new ArrayList<>();
    private ArrayList<OpportunityListItem> listDone = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_events, container, false);
        mRecyclerView = view.findViewById(R.id.writeFeedback);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewDone = view.findViewById(R.id.pastOps);
        mRecyclerViewDone.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapterDone = new MyAdapter(listDone);
        mRecyclerViewDone.setAdapter(mAdapterDone);
        mRecyclerViewDone.setHasFixedSize(true);
        setHasOptionsMenu(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return view;
    }

}
