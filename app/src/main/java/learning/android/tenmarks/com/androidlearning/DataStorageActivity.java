package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import learning.android.tenmarks.com.androidlearning.database.LearnDatabaseHelper;

/**
 * Created by Talha on 11/4/15.
 */
public class DataStorageActivity extends Activity implements View.OnClickListener {

    private EditText et;
    private TextView mTextViewInfo1;
    private TextView mTextViewInfo2;

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_CAPTURE_INTERNAL = 102;

    private ImageView imgFromExternalDirectory;
    private ImageView imgFromInternalDirectory;

    private EditText etName, etAddress;

    private LearnDatabaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_storage);

        // TODO take a look at how I set a Click Listener for the two buttons
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);

        findViewById(R.id.btn_save_external_storage).setOnClickListener(this);
        findViewById(R.id.btn_read_external_storage).setOnClickListener(this);

        findViewById(R.id.btn_save_internal_storage).setOnClickListener(this);
        findViewById(R.id.btn_read_internal_storage).setOnClickListener(this);

        findViewById(R.id.btnAddToDb).setOnClickListener(this);
        findViewById(R.id.btnLoadDataFromDb).setOnClickListener(this);

        et = (EditText) findViewById(R.id.input_field);
        mTextViewInfo1 = (TextView) findViewById(R.id.infoText1);
        mTextViewInfo2 = (TextView) findViewById(R.id.infoText2);

        imgFromExternalDirectory = (ImageView) findViewById(R.id.imgExtDir);
        imgFromInternalDirectory = (ImageView) findViewById(R.id.imgInternalStorage);

        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etAddress);

        dbHelper = new LearnDatabaseHelper(this);

    }

    @Override
    public void onClick(View v) {
        // TODO you can use a switch statement instead of these
        if (v.getId() == R.id.btn_save) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                Toast.makeText(this, "Please enter some text!", Toast.LENGTH_SHORT).show();
            } else {
                // TODO first arg is the Preferences File Name, the second arg is mode which is private.
                SharedPreferences sp = getSharedPreferences("LearnAndroid", 0);
                // TODO note that getSharedPreferences makes multiple preferences files if you supply a different name
                // TODO you can also access sharedPreferences using getPreferences() which will create only one file

                // so to write a value you, simply access the preferences editor like the following
                SharedPreferences.Editor editor = sp.edit();
                // then next you use the put methods for different primitive types
                editor.putString("key_learn_android", et.getText().toString());
                // and finally you commit
                editor.commit();
                // you can do this in one line
                // sp.edit().putString("key_learn_android", et.getText().toString()).commit();

                Toast.makeText(this, "written to preferences", Toast.LENGTH_SHORT).show();

                // and just clearing the input field
                et.setText("");
            }
        } else if (v.getId() == R.id.btn_read) {
            String readValue = getSharedPreferences("LearnAndroid", 0).getString("key_learn_android", "No Value Found");
            Toast.makeText(this, "Read: " + readValue, Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_save_external_storage) {
            // TODO to write to external storage you need permission WRITE_EXTERNAL_STORAGE
            if (isExternalStorageWritable()) {
                File externalDir = getExternalFilesDir(null);
                if (externalDir != null) {
                    mTextViewInfo1.setText(externalDir.getAbsolutePath().toString());
                    // Let's capture an image and write it  to external storage

                    Intent imgCaptureIntent = new Intent();
                    imgCaptureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    // now to be safe you need to check if there is a provide that can actually do this for you
                    if (imgCaptureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(imgCaptureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            } else {
                Toast.makeText(this, "External storage is not writable", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_read_external_storage) {
            if (isExternalStorageReadable()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ALPHA_8;
                Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/captured-img.jpg", options);
                imgFromExternalDirectory.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "External storage is not readable", Toast.LENGTH_SHORT).show();
            }
        } else if(v.getId() == R.id.btn_save_internal_storage) {
            // TODO
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_INTERNAL);
            }
        } else if(v.getId() == R.id.btn_read_internal_storage) {
            // TODO
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ALPHA_8;
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(openFileInput("img-captured-internal.jpg"));
                imgFromInternalDirectory.setImageBitmap(bitmap);
            } catch (FileNotFoundException fnf) {
                 fnf.printStackTrace();
            }
        } else if (v.getId() == R.id.btnAddToDb) {
            if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAddress.getText().toString())) {
                Toast.makeText(this, "Please provide a Name and Email", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper == null) {
                    dbHelper = new LearnDatabaseHelper(this);
                }

                long rowId = dbHelper.addNameAddress(etName.getText().toString(), etAddress.getText().toString());
                Toast.makeText(this, "Added row at Id: " + rowId, Toast.LENGTH_SHORT).show();
                etName.setText("");
                etAddress.setText("");
            }
        } else if(v.getId() == R.id.btnLoadDataFromDb) {
            Intent intent = new Intent();
            intent.setClass(this, LoadDataFromDbActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(data != null) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                // TODO getExternalFilesDir creates a private directory on external storage.
                // TODO the arg is type which when null gives access to root directory
                // TODO other "type" can be DIRECTORY_MUSIC, DIRECTORY_PICTURES etc
                File file = new File(getExternalFilesDir(null), "captured-img.jpg");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_INTERNAL && resultCode == RESULT_OK) {
            if (data != null) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                // TODO getExternalFilesDir creates a private directory on external storage.
                // TODO the arg is type which when null gives access to root directory
                // TODO other "type" can be DIRECTORY_MUSIC, DIRECTORY_PICTURES etc
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput("img-captured-internal.jpg", Context.MODE_PRIVATE);
                    mTextViewInfo2.setText(fos.getFD().toString());
                    bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(this, "File written to internal storage", Toast.LENGTH_SHORT).show();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
