package com.example.linzw.videoplayer;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends Activity {

    public static final  String  TAG="videoPlayer";

    private  AsyncTask mVideoUpdateTask;
    private  ListView mVideoListView;
    private  List<VideoItem> mVideoList;
    private  MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(R.string.title_name);
        mVideoList=new ArrayList<VideoItem>();
        mVideoListView =(ListView)findViewById(R.id.listview);


        }
    }
