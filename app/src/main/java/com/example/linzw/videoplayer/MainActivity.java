package com.example.linzw.videoplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends Activity {

    public static final String TAG = "videoPlayer";

    private AsyncTask mVideoUpdateTask;
    private ListView mVideoListView;
    private List<VideoItem> mVideoList;
    private MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(R.string.title_name);
        mVideoList = new ArrayList<VideoItem>();
        mVideoListView = (ListView) findViewById(R.id.listview);

        VideoItemAdaptor adaptor = new VideoItemAdaptor(this, R.layout.listview, mVideoList);
        mVideoListView.setAdapter(adaptor);
        mVideoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoItem videoItem = mVideoList.get(position);
                // Intent i=new Intent(this,VideoPlay.class);
                Intent i = new Intent(MainActivity.this, VideoPlay.class);
                i.setData(Uri.parse(videoItem.path));
                startActivity(i);
            }
        });


        VideoUpdateTask updateTask = new VideoUpdateTask();
        updateTask.execute();

    }

    public class VideoUpdateTask extends AsyncTask<Object, VideoItem, Void> {
        protected List<VideoItem> mDataList = new ArrayList<VideoItem>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            updateDate();
        }

        @Override
        protected void onProgressUpdate(VideoItem... values) {
            VideoItem data = values[0];
            mVideoList.add(data);
            Log.i("TAG", "onProgressUpdate");
            VideoItemAdaptor adaptor = (VideoItemAdaptor) mVideoListView.getAdapter();
            adaptor.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Object... params) {

            Uri uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
            String[] searchkey = new String[]{
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DATE_ADDED
            };

            String where = MediaStore.Video.Media.DATA + "like+\"%" + getString(R.string.search_path) + "%\"";
            String[] keyword = null;
            String order = MediaStore.Video.Media.DEFAULT_SORT_ORDER;
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri, searchkey, where, keyword, order);
            if (cursor != null) {
                Log.i("TAG", where);

                while (cursor.moveToNext() && isCancelled()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    Log.i("TAG", path);
                    String createTime = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));
                    String tile = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    VideoItem videoItem = new VideoItem(tile, path, createTime);
                    if (!mVideoList.contains(videoItem)) {
                        videoItem.createThumbnail();
                        //mVideoList.add(videoItem);
                        publishProgress(videoItem);
                    }
                    mDataList.add(videoItem);
                }
            }
            cursor.close();
            return null;
        }


        private void updateDate() {
            // should do after the doInBackGround(),the mDataList is the newest data.
            //loop find the item in the mVideoList and just it delete or not.
            for (int i = 0; i < mVideoList.size(); i++) {
                if (!mDataList.contains(mVideoList.get(i))) {
                    mVideoList.get(i).releaseThumbnail();
                    mVideoList.remove(i);
                    i--;
                }
            }
            mDataList.clear();

            VideoItemAdaptor adaptor=(VideoItemAdaptor) mVideoListView.getAdapter();
            adaptor.notifyDataSetChanged();

        }

    }
}

