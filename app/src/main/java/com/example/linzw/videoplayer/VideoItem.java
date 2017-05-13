package com.example.linzw.videoplayer;

import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.util.Date;

/**
 * Created by LinZW on 2017/5/13.
 */

public class VideoItem {
    String name;
    String path;
    Bitmap thumbPicture;
    String createTime;

    VideoItem(String strName,String strPath,String createTime)
    {
        this.path=strPath;
        this.name=strName;

        SimpleDateFormat dateFormer=new SimpleDateFormat("yyyy年MM月HH时mm分");
        Date  date=new Date(Long.valueOf(createTime)*1000);
        this.createTime=dateFormer.format(date);

        this.thumbPicture=ThumbnailUtils.createVideoThumbnail(path,MediaStore.Images.Thumbnails.MINI_KIND);
    }
}
