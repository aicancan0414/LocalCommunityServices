package c.group24.localcommunityservices;

import android.app.Fragment;
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
    private ArrayList<Opportunity> list = new ArrayList<>();

    private DatabaseReference student;
    private String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_events, container, false);
        mRecyclerView = view.findViewById(R.id.writeFeedback);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        populateList();
        mAdapter = new EventAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        public CardView cv;
        public TextView mTitle;
        public TextView mDescription;
        public MyViewHolder(View v) {
            super(v);


            cv = (CardView)itemView.findViewById(R.id.cv);

            mTitle = (TextView) itemView.findViewById(R.id.opportunity_title);
            mDescription = (TextView) itemView.findViewById(R.id.opportunity_description);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            //Opportunity opportunity = list.remove(getAdapterPosition());
        }
    }

    public class EventAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private ArrayList<Opportunity> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder


        // Provide a suitable constructor (depends on the kind of dataset)
        public EventAdapter(ArrayList<Opportunity> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            //System.out.println("HEREHEHEHEHEHEHEH");
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mDescription.setText(mDataset.get(position).description);

            //holder.mTextView.setText(mDataset.get(position));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    }

}
