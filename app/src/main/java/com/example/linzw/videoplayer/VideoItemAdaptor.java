package com.example.linzw.videoplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
// 总结：
/*
*
*   public class videoAdapter extends ArrayAdapter<videoItem>
*       {
*           //member variable
*           // construct function
*           videoAdapter(Context context,int resource,List<videoItem>)
*           {
*
*           }
*
*           @Override
*           private View getView(int postion,View convertView,ViewGroup parent)
*           {
*           }
*
*       }
* */







/**
 * Created by LinZW on 2017/5/13.
 */

public class VideoItemAdaptor extends ArrayAdapter<VideoItem> {

    public final LayoutInflater mInflater;
    public final int mResouce;

    VideoItemAdaptor(Context pContext, int pResource, List<VideoItem> object)
    {
            super(pContext,pResource,object);
            mInflater=LayoutInflater.from(pContext);
            mResouce=pResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        VideoItem item=getItem(position);

        if(convertView==null)
        {
            convertView=mInflater.inflate(mResouce,parent);
        }
        ImageView imgview=(ImageView)convertView.findViewById(R.id.imageview);
        imgview.setImageBitmap(item.thumbPicture);

        TextView titletxtview=(TextView)convertView.findViewById(R.id.textviewTitle);
        titletxtview.setText(item.name);

        TextView datetxtview=(TextView)convertView.findViewById(R.id.textviewDate);
        datetxtview.setText(item.createTime);
        return  convertView;
    }
}

