package c.group24.localcommunityservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

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

        final Button createButton = findViewById(R.id.submitButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(RATING, mFeedbackRating.getRating());
                intent.putExtra(FEEDBACK, mFeedbackText.getText().toString());
                setResult(RESULT_OK, intent);
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
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

}
