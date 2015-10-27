package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Talha on 10/21/15.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle extras = getIntent().getExtras();
        String titleFromDashBoard;

        if (extras != null) {
            titleFromDashBoard = extras.getString("titleSentFromDashBoard");
            TextView textView = (TextView) findViewById(R.id.text_view_center);
            textView.setText(titleFromDashBoard);
        }

        Button closeButton = (Button) findViewById(R.id.button_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent = new Intent();
                dashboardIntent.putExtra("backToTheDashBoard", "I'm back!");
                setResult(123, dashboardIntent);
                finish();
            }
        });


        /**
         * ASSIGNMENT
         * Look at the XML file of this activity....You will find a relative layout.
         * Add 4 TextViews to this layout (should be on the top of the screen)
         *
         * TextView-1 and TextView-2 go in row 1
         * TextView-3 and TextView-4 go in row 2
         *
         * TextView-1878333 is labeled 1
         * TextView-2 is labeled 2
         * TextView-3 is labeled 3
         * TextView-4 is labeled 4
         *
         *
         * Let's make the TextView's perfect square with some background.
         *
         * Similarly add 4 buttons to this layout like we did with the LinearLayout
         * The buttons should be arranged in the similar fashion
         * Button-1 on the left, Button-2 to right of Button-1
         * Button-3 below Button-1 and Button-4 below Button-2.
         *
         * Add Click Listener to each Button and increment the text in respective
         * TextView.
         *
         * On closing the activity, return the value in each text view to main activity.
         * This will involve using an Intent and some Bundle
         *1878333
         * ASSIGNMENT 2
         * LEARN ABOUT EXPLICIT INTENT and IMPLICIT INTENTS
         * -- Read a PDF file from assets and open in an intent.
         * -- Open a web file from assets in an intent.
         *
         */
    }
}
