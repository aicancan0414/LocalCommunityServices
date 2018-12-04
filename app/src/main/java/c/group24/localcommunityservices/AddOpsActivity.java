package c.group24.localcommunityservices;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AddOpsActivity extends AppCompatActivity {

    private static final int SEVEN_DAYS = 604800000;

    private static String mDateString;
    private static TextView mDateView;

    private EditText mTitleText;
    private EditText mLocationText;
    private EditText mDescriptionText;
    private EditText mRequirementsText;
    private Date mDate;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference organization;
    private DatabaseReference project;
    private String UID;

    private Opportunity opportunity = new Opportunity();
    private String mOrg, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ops);

        findViewById(R.id.opsTitleLabel);
        findViewById(R.id.opsDateLabel);
        findViewById(R.id.opsDateLabel);
        findViewById(R.id.opsLocationLabel);
        findViewById(R.id.opsDescriptionLabel);
        findViewById(R.id.opsRequirementsLabel);
        mTitleText = findViewById(R.id.opsTitle);
        mDateView = findViewById(R.id.opsDate);
        mLocationText = findViewById(R.id.opsLocation);
        mDescriptionText = findViewById(R.id.opsDescription);
        mRequirementsText = findViewById(R.id.opsRequirements);

        setDefaultDate();

        firebaseDatabase = FirebaseDatabase.getInstance();
        organization = firebaseDatabase.getReference("Organization");
        project = firebaseDatabase.getReference("Projects");

        final Button datePickerButton = findViewById(R.id.opsDateButton);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        final Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                UID = "EJ4sDWRJh8TaesIr07BdshGD4Fu1";

                organization.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                        mOrg = map.get("Name");
                        mEmail = map.get("Email");
                        opportunity.setOrg(mOrg);
                        opportunity.setContact(mEmail);
                        Toast.makeText(AddOpsActivity.this, mEmail, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Toast.makeText(AddOpsActivity.this, mEmail,Toast.LENGTH_LONG).show();
                //opportunity.setOrg(organization.child(UID).child("Name").toString());

                opportunity.setOrgID(UID);
                //opportunity.setContact(organization.child(UID).child("Email").toString());

                opportunity.setDate(mDateString);
                opportunity.setLocation(mLocationText.getText().toString());
                opportunity.setDescription(mDescriptionText.getText().toString());

                opportunity.setDescription(mDescriptionText.getText().toString());
                project.push().setValue(opportunity);

                DatabaseReference ref = firebaseDatabase.getReference().child("Organization").child(UID).child("Offered Projects").child(mTitleText.getText().toString());
                ref.child("Title").setValue(mTitleText.getText().toString());
                ref.child("Date").setValue(mDateString.toString());
                ref.child("Location").setValue(mLocationText.getText().toString());
                ref.child("Description").setValue(mDescriptionText.getText().toString());


                opportunity.setRequirements(mRequirementsText.getText().toString());
                project.child(mTitleText.getText().toString()).setValue(opportunity);

                finish();
            }
        });

        final Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText.setText("");
                setDefaultDate();
                mLocationText.setText("");
                mDescriptionText.setText("");
                mRequirementsText.setText("");
            }
        });

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setDefaultDate() {
        mDate = new Date();
        mDate = new Date(mDate.getTime() + SEVEN_DAYS);
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        mDateView.setText(mDateString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;
        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        mDateString = year + "-" + mon + "-" + day;
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);
            mDateView.setText(mDateString);
        }

    }

}
