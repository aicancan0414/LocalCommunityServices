package c.group24.localcommunityservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    public final static String RATING = "rating";
    public final static String FEEDBACK = "feedback";

    private RatingBar mFeedbackRating;
    private EditText mFeedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        findViewById(R.id.opsRatingLabel);
        findViewById(R.id.opsFeedbackLabel);
        mFeedbackRating = findViewById(R.id.opsRating);
        mFeedbackText = findViewById(R.id.opsFeedback);

        final Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String UID = intent.getStringExtra("UID");
                DatabaseReference organization = FirebaseDatabase.getInstance().getReference("Organization").child(UID);
                float overall = Float.parseFloat(organization.child("Rating").toString());
                int num = Integer.parseInt(organization.child("Num").toString());
                num++;
                float newRating = (overall * num + mFeedbackRating.getRating()) / num;
                organization.child("Rating").setValue(String.valueOf(newRating));
                organization.child("Num").setValue(String.valueOf(num));
                organization.child("Review").child(String.valueOf(num)).setValue(mFeedbackText.getText());
                finish();
            }
        });

        final Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedbackRating.setRating(0);
                mFeedbackText.setText("");
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

}
