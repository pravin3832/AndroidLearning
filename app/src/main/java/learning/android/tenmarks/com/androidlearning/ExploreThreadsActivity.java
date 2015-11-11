package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ExploreThreadsActivity extends Activity {

    private static final String TAG = "ExploreThreadsActivity";


    ProgressBar mProgressBar;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_threads);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        findViewById(R.id.download_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MySongDownloader().execute("http://zmnegxwfeezafpotkgrb.folkpunjab.org/Abida Parveen/Abida Parveen - Ranjha Shah Hazare.mp3");
            }
        });

        mImageView = (ImageView) findViewById(R.id.image_view);
        findViewById(R.id.btn_download_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL imageUrl = null;
                        HttpURLConnection connection = null;
                        try {
                            Log.d(TAG, "Inside try/catch to download image");
                            imageUrl = new URL("http://image.tmdb.org/t/p/w500/sEgULSEnywgdSesVHFHpPAbOijl.jpg");
                            connection = (HttpURLConnection) imageUrl.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream is = connection.getInputStream();
                            Log.d(TAG, "Got input stream for image");
                            Bitmap myBitmap = BitmapFactory.decodeStream(is);
                            mImageView.setImageBitmap(myBitmap);// can crash...since this is non-ui thread
                            Log.d(TAG, "After setting image bitmap");
                        } catch (IOException ioex) {
                            ioex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            // TODO not this, you can't show a toast by simply doing Toast.makeText and show();
                            // TODO you should be calling one the methods to display this on UI Thread.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ExploreThreadsActivity.this, "ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its view®s.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_download_image_one).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL imageUrl = null;
                        HttpURLConnection connection = null;
                        try {
                            Log.d(TAG, "Inside try/catch to download image");
                            imageUrl = new URL("http://image.tmdb.org/t/p/w500/sEgULSEnywgdSesVHFHpPAbOijl.jpg");
                            connection = (HttpURLConnection) imageUrl.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream is = connection.getInputStream();
                            Log.d(TAG, "Got input stream for image");
                            final Bitmap myBitmap = BitmapFactory.decodeStream(is);
                            mImageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mImageView.setImageBitmap(myBitmap);
                                }
                            });
                            Log.d(TAG, "After setting image bitmap");
                        } catch (IOException ioex) {
                            ioex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        // TODO this will be using Handler
        // Handler allows you to post or process a message in a thread's message queue.
        findViewById(R.id.btn_download_image_two).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL imageUrl = null;
                        HttpURLConnection connection = null;
                        try {
                            Log.d(TAG, "Inside try/catch to download image");
                            imageUrl = new URL("http://image.tmdb.org/t/p/w500/lfNUaC9hHPEt0ZoMlngjmCTgJGq.jpg");
                            connection = (HttpURLConnection) imageUrl.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream is = connection.getInputStream();
                            Log.d(TAG, "Got input stream for image");

                            Bitmap myBitmap = BitmapFactory.decodeStream(is);
                            Bundle bitmapBundle = new Bundle();
                            // TODO following is a very bad approach, eats up lots of memory but
                            // TODO just for the sake of telling you how to deal with a handle and a message
                            bitmapBundle.putParcelable("my_bitmap", myBitmap);
                            Message bitmapMessage = new Message();
                            bitmapMessage.what = 1;
                            bitmapMessage.setData(bitmapBundle);

                            myHandler.sendMessage(bitmapMessage);

                            Log.d(TAG, "After setting image bitmap");

                        } catch (IOException ioex) {
                            ioex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.getData() != null) {
                        Bitmap bmp = (Bitmap) msg.getData().getParcelable("my_bitmap");
                        mImageView.setImageBitmap(bmp);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    class MySongDownloader extends AsyncTask<String, Integer, File> {

        @Override
        protected File doInBackground(String... params) {
            String baseUrl = "http://zmnegxwfeezafpotkgrb.folkpunjab.org/";
            String extended = "";
            try {
                extended = URLEncoder.encode("Abida Parveen/Abida Parveen - Ranjha Shah Hazare.mp3", "utf-8");
            } catch (UnsupportedEncodingException unse) {
                unse.printStackTrace();
            }

            URL songUrl = null;
            try {
                // songUrl = new URL(Uri.parse(url).toString());
                // songUrl = new URL(baseUrl + extended);
                songUrl = new URL("http://zmnegxwfeezafpotkgrb.folkpunjab.org/Abida%20Parveen/Abida%20Parveen%20-%20Ranjha%20Shah%20Hazare.mp3");
                HttpURLConnection urlConnection = (HttpURLConnection) songUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                // urlConnection.setDoOutput(true);
                urlConnection.connect();

                int lengthOfFile = urlConnection.getContentLength();

                Log.d(TAG, "After opening connection");

                String PATH = Environment.getExternalStorageDirectory() + "/Download";
                File file = new File(PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }

                Log.d(TAG, "After creating file");

                File outputFile = new File(file, "my-song.mp3");
                FileOutputStream fos = new FileOutputStream(outputFile);
                Log.d(TAG, "before getInputStream");
                InputStream is = urlConnection.getInputStream();

                Log.d(TAG, "after getInputStream");
                byte[] buffer = new byte[1024];
                int len = 0;
                int total = 0;

                while ((len = is.read(buffer)) != -1) {
                    total += len;
                    fos.write(buffer, 0, len);
                    publishProgress((int)((total*100)/lengthOfFile));
                }

                fos.flush();
                fos.close();
                is.close();
                Log.d(TAG, "After closing everything");
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("ExploreThreads", "value is: " + values[0]);
            mProgressBar.setProgress(values[0]);
        }
    }

}
