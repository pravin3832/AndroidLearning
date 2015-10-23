package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * An activity is a single, focused thing that the user can do.
 * Almost all activities interact with the user, so the Activity
 * class takes care of creating a window for you in which you can
 * place your UI with setContentView(View). While activities are
 * often presented to the user as full-screen windows, they can
 * also be used in other ways: as floating windows (via a theme
 * with windowIsFloating set) or embedded inside of another activity
 * (using ActivityGroup).
 *
 * More you can read here: http://developer.android.com/reference/android/app/Activity.html
 *
 */
public class DashBoardActivity extends Activity {

    private static final String TAG = "DashboardActivity";

    /**
     * This is where you set the content to display in the activity.
     * @param savedInstanceState This is the Budle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * TIPS:
         * 1. To quickly move to a particular method press Command + F12 on you mac and press the
         * method name in the dialog that opens
         * 2. Press control + F search for R.layout and you can see what layouts are
         *      being loaded and you can quickly navigate to the Activity Layout File.
         * ASSIGNMENT:
         * 1. Can you look for a shortcut to directly open the xml file of an activity?
         * 2. Can you look if we can create a view in code and assign to activity rather than loading
         * from xml?
         */

        setContentView(R.layout.activity_dash_board);


        // After you set the contents of the activity, you can reference the individual widgets used
        TextView myTextView = (TextView) findViewById(R.id.my_text_view);
        // on the above line findViewById returns a View, so you need to do an appropriate cast.


        // next you can use myTextView to modify its properties.
        myTextView.setText("Hello! I just started learning Android");

        // Let's refer to the other View inside out layout
        EditText myEditText = (EditText) findViewById(R.id.my_edit_text);

        /**
         * Let's add a key listener to this EditText and block any digits 0-9
         */
        myEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode >= event.KEYCODE_0 && keyCode <= event.KEYCODE_9) {
                    // means we just handled it, don't wanna show this
                    return true;
                }
                // return false otherwise.
                return false;
            }
        });

        /**
         * ASSIGNMENT
         * 1. Can you create a TextChangedListener for this EditText and
         * on Text Change count the number of items and when it exceeds 10, display a Toast
         *
         * A Toast is a small piece of UI that appears for a few seconds on the screen
         * and is helpful to show useful information to users.
         * You create a Toast like this:
         * Toast.makeText(DashBoardActivity.this, "Hey! stop entering more text", Toast.LENGTH_SHORT);
         */

        /**
         * you can add callbacks to this View as per your requirement
         * Some of the code will look like the following
         */
        /*myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


        /**
         * ASSIGNMENT
         * Open the xml file of this Activity
         * Add 4 buttons in such a way that Button-1 and Button-2 are in one row
         * and Button-3 and Button-4 are in the other row.
         * implement the OnClickListener interface on this Activity and on click of each button
         * put some text in myEditText.
         */


        // let go to another Activity
        Button btnSettings = (Button) findViewById(R.id.button_settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(DashBoardActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        // button_with_drawable
        Button btnWithDrawable = (Button) findViewById(R.id.button_with_drawable);
        btnWithDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashBoardActivity.this, "You just clicked me :)", Toast.LENGTH_LONG).show();
            }
        });

        // button_with_image_selector_no_text
        Button btnWithImageSelector = (Button) findViewById(R.id.button_with_image_selector_no_text);
        btnWithImageSelector.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                v.playSoundEffect(SoundEffectConstants.CLICK);
                Toast.makeText(DashBoardActivity.this, "Do you hear the click sound?", Toast.LENGTH_SHORT).show();
            }
        });

        // show me some nine patches now
        Button btnNinePatch = (Button) findViewById(R.id.button_nine_patch);
        btnNinePatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, NinePatchesActivity.class));
            }
        });

        Button btnListView = (Button) findViewById(R.id.button_list_view);
        btnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, MovieDbDiscoverActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dash_board, menu);
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
            // Launch settings intent from menu
            Intent settingsIntent = new Intent(DashBoardActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
