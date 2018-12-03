package c.group24.localcommunityservices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class StudentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Menu menu;
    private Fragment fragment;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String userID;
    private TextView name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Get the firebase reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Student");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                name.setText(map.get("Name"));
                email.setText(map.get("Email"));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        //Instantiate the opportunity fragment
        fragment = new OpportunityFragment();
        //fragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_main, menu);
        this.menu = menu;


        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.editProfile) {
            fragment = new EditProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            MenuItem settingsMenuItem = menu.findItem(R.id.action_settings);
            settingsMenuItem.setVisible(false);
            MenuItem ediMenuItem = menu.findItem(R.id.editProfile);
            ediMenuItem.setVisible(true);
            setTitle("Profile");
            //getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

        } else if (id == R.id.nav_pastevents) {


        } else if(id == R.id.nav_futureevents) {
            fragment = new OpportunityFragment();
            setTitle("Opportunities");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
