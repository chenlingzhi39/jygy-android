package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.activity.VideoShowActivity;
import com.endeavour.jygy.record.activity.RecordShowActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.imagechoose.ShowActivity;

public class TaskGridAdapter extends BaseAdapter {
    public static final int MAX_IMG_SIZE = 9;

    public interface TaskGridAdapterCallBack {
        public void takePic();

        public void choosePic();

        public void takeVideo();

        public void showArcLayout();
    }

    private Context mContext;
    private List<String> paths = new ArrayList<String>();
    private DisplayImageOptions options;
    private TaskGridAdapterCallBack TaskGridAdapterCallBack;
    
    public TaskGridAdapter(Context context) {
        mContext = context;
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    ViewHolder mViewHolder;

    public List<String> getPaths() {
        return paths;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        mViewHolder = new ViewHolder();
        final int temp = arg0;
        if (arg1 == null) {
            arg1 = LayoutInflater.from(mContext).inflate(R.layout.itemview_images, null);
            mViewHolder.imageView = (ImageView) arg1.findViewById(R.id.ivSend);
            mViewHolder.imageButton = (ImageButton) arg1.findViewById(R.id.ibDelete);
            arg1.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) arg1.getTag();
        }
        //1录音 2视频 3照片 4相册选择
        String path = "";
        try{
        	path = paths.get(arg0);
        }catch(Exception ex){}
//        if (arg0 == paths.size()) {
//            mViewHolder.imageButton.setVisibility(View.GONE);
//            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.addphoto, mViewHolder.imageView, options);
//        } else 
        if (path.contains(".jpeg") || path.contains(".jpg")) {
            ImageLoader.getInstance().displayImage(paths.get(arg0), mViewHolder.imageView, options);
            mViewHolder.imageButton.setVisibility(View.VISIBLE);
        } else if (path.contains(".mp4")) {
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.video, mViewHolder.imageView, options);
            mViewHolder.imageButton.setVisibility(View.VISIBLE);
        } else if (path.contains(".amr")) {
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.sound, mViewHolder.imageView, options);
            mViewHolder.imageButton.setVisibility(View.VISIBLE);
        }
        mViewHolder.imageButton.setVisibility(View.GONE);
        mViewHolder.imageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                paths.remove(temp);
                TaskGridAdapter.this.notifyDataSetChanged();
            }
        });

        final String filepath = path;
        mViewHolder.imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp != paths.size()) {
                	 if (filepath.endsWith(".mp4")) {
                         Intent intent = new Intent(mContext, VideoShowActivity.class);
                         intent.putExtra("videopath", filepath);
                         mContext.startActivity(intent);
                     } else if (filepath.endsWith(".jpeg") || filepath.endsWith(".jpg")) {
                         Intent intent = new Intent(mContext, ShowActivity.class);
                         intent.putExtra("path", filepath);
                         mContext.startActivity(intent);
                     }else if (filepath.endsWith(".amr")) {
                    	 Intent intent = new Intent(mContext, RecordShowActivity.class);
                         intent.putExtra("recordpath", filepath);
                         mContext.startActivity(intent);
                     }
                } else {
                    //圆形菜单特效
                    if (TaskGridAdapterCallBack != null) {
                        TaskGridAdapterCallBack.showArcLayout();
                    }
                }
            }

        });
        return arg1;
    }

    class ViewHolder {
        ImageView imageView;
        ImageButton imageButton;
    }

    /*
     * 为照片命名
     */
    public String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public void setTaskGridAdapterCallBack(TaskGridAdapterCallBack TaskGridAdapterCallBack) {
        this.TaskGridAdapterCallBack = TaskGridAdapterCallBack;
    }

    public void setDataChanged(List<String> paths) {
        this.paths = paths;
        this.notifyDataSetChanged();
    }

    public void addDataChanged(List<String> paths) {
        if (this.paths.size() >= 9) {
            this.paths.clear();
        }
        this.paths.addAll(paths);
        this.notifyDataSetChanged();
    }

}
