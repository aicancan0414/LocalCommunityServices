package c.group24.localcommunityservices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> headerItem;
    private HashMap<String, List<String>> childItem;


    public OrganizationAdapter(Context context, List<String> headerItem, HashMap<String, List<String>> childItem) {
        this.context = context;
        this.headerItem = headerItem;
        this.childItem = childItem;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItem.get(headerItem.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(!isLastChild) {
                convertView = inflater.inflate(R.layout.list_item, null);
                Log.i("Lastlines",Integer.toString(childPosition));
            }else{
                Log.i("Lastlines",Integer.toString(childPosition));
                convertView = inflater.inflate(R.layout.list_item_with_button, null);
                Button join = convertView.findViewById(R.id.myButton);
                join.setFocusable(false);
                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        final String uidStr = auth.getCurrentUser().getUid();
                        String emailStr = auth.getCurrentUser().getEmail();
                        final String proj_name = headerItem.get(groupPosition);
                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Taken projects and members").child(proj_name);
                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Projects").child(proj_name);
                        ref.child(uidStr).setValue(emailStr);
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference ref_seats=FirebaseDatabase.getInstance().getReference().child("Projects").child(proj_name).child("Seats");


                        ref_seats.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String seats=dataSnapshot.getValue(String.class);
                                int seat_num=Integer.parseInt(seats);
                                if(seat_num<=0){
                                    seat_num=0;
                                }else{
                                    seat_num=seat_num-1;
                                }
                                seats=Integer.toString(seat_num);

                                FirebaseDatabase.getInstance().getReference().child("Projects").child(proj_name).child("Seats").setValue(seats);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Log.e("what","ha");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String,Object> add = new HashMap<String, Object>();
                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    String key = ds.getKey();
                                    Object value =  ds.getValue();
                                    add.put(key,value);
                                }
                                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();

                                ref2.child(uidStr).child(proj_name).updateChildren(add);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }
                });
            }
        }else{
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(!isLastChild) {
                convertView = inflater.inflate(R.layout.list_item, null);
                Log.i("Lastlines",Integer.toString(childPosition));
            }else{
                Log.i("Lastlines",Integer.toString(childPosition));
                convertView = inflater.inflate(R.layout.list_item_with_button, null);
                Button join = convertView.findViewById(R.id.myButton);
                join.setFocusable(false);
                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        final String uidStr = auth.getCurrentUser().getUid();
                        String emailStr = auth.getCurrentUser().getEmail();
                        final String proj_name = headerItem.get(groupPosition);
                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Taken projects and members").child(proj_name);
                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Projects").child(proj_name);
                        ref.child(uidStr).setValue(emailStr);
                        DatabaseReference ref_seats=FirebaseDatabase.getInstance().getReference().child("Projects").child(proj_name).child("Seats");


                        ref_seats.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String seats=dataSnapshot.getValue(String.class);
                                int seat_num=Integer.parseInt(seats);
                                if(seat_num<=0){
                                    seat_num=0;
                                }else{
                                    seat_num=seat_num-1;
                                }
                                seats=Integer.toString(seat_num);

                                FirebaseDatabase.getInstance().getReference().child("Projects").child(proj_name).child("Seats").setValue(seats);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
                        Log.e("what","ha");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Map<String,Object> add = new HashMap<String, Object>();
                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    String key = ds.getKey();
                                    Object value =  ds.getValue();
                                    add.put(key,value);
                                }
                                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();

                                ref2.child(uidStr).child(proj_name).updateChildren(add);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });






                    }
                });
            }
        }
        TextView tv = convertView.findViewById(R.id.lblListItem);
        tv.setText(childText);


        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childItem.get(headerItem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerItem.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return headerItem.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        TextView tv = convertView.findViewById(R.id.lblListHeader);
        tv.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}


