package c.group24.localcommunityservices;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<OpportunityListItem> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            //System.out.println("HEREHEHEHEHEHEHEH");
            // Display a Toast message indicting the selected item
            Toast.makeText(view.getContext(),
                    mTitle.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<OpportunityListItem> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
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
        holder.mTitle.setText(mDataset.get(position).title);
        holder.mDescription.setText(mDataset.get(position).descrition);

        //holder.mTextView.setText(mDataset.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
