package c.group24.localcommunityservices;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
                firebaseDatabase = FirebaseDatabase.getInstance();
                organization = firebaseDatabase.getReference("Organization");
                project = firebaseDatabase.getReference("Projects");
                UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Opportunity opportunity = new Opportunity();
                opportunity.setTitle(mTitleText.getText().toString());
                opportunity.setContact(organization.child(UID).child("Email").toString());
                opportunity.setDate(mDateString);
                opportunity.setLocation(mLocationText.getText().toString());
                opportunity.setDescription(mDescriptionText.getText().toString());
                opportunity.setDescription(mDescriptionText.getText().toString());
                project.push().setValue(opportunity);
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
