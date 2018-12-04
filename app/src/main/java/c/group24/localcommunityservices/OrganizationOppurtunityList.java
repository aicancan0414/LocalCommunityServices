package c.group24.localcommunityservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizationOppurtunityList extends Activity {

    ExpandableListView expandableListView;
    ///OrganizationAdapter customExpandableListViewAdapter;

    OrgOppAdpterTest customExpandableListViewAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_oppurtunity_list);

        database = FirebaseDatabase.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        final String uidStr=auth.getCurrentUser().getUid();
        //final String user=auth.getCurrentUser();
        Log.e("uid","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        Log.e("uid", uidStr );

        myRef = database.getReference("Organization").child(uidStr).child("Offered Projects");


        Log.e("test","1");

        expandableListView = findViewById(R.id.lvExp);
        SetStandardGroups();
        customExpandableListViewAdapter = new OrgOppAdpterTest(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(customExpandableListViewAdapter);
        Button post = findViewById(R.id.button4);
        Log.e("test","2");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrganizationOppurtunityList.this,  AddOpsActivity.class    );////////////
                startActivity(i);
            }
        });

        Button editProf = findViewById(R.id.profile);
        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrganizationOppurtunityList.this,EditOrganizationActivity.class);
                startActivity(i);
            }
        });


    }

    public void SetStandardGroups() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        Log.e("test","3");
        myRef.addChildEventListener(new ChildEventListener() {

            int counter = 0;
            List<String> childItem = new ArrayList<>();



            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                listDataHeader.add(dataSnapshot.getKey());

                Log.e("TAG1", listDataHeader.get(counter));
                String k  = listDataHeader.get(counter);
                Log.e("test",k);
                childItem = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String childNames = (String) ds.getValue();
                    String key = ds.getKey();
                    Log.e("test","5");
                    Log.e("TAG", "childNames :" + childNames);
                    childItem.add(key + " : " +childNames);
                }

                listDataChild.put(listDataHeader.get(counter), childItem);
                counter++;
                Log.e("TAG", "counter :" + counter);

                customExpandableListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Intent i = getIntent();
                startActivity(i);
                finish();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}