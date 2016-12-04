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
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.parent.activity.ImageBrowserActivity;
import com.endeavour.jygy.parent.activity.VideoShowActivity;
import com.endeavour.jygy.record.activity.RecordShowActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskEditImgAdapter extends BaseAdapter {
    public static final int MAX_IMG_SIZE = 6;

    public interface ChooseImageAdapterCallBack {
        public void takePic();

        public void choosePic();

        public void takeVideo();

        public void showArcLayout();
    }

    private Context mContext;
    private List<String[]> paths = new ArrayList<String[]>();
    private DisplayImageOptions options;
    private ChooseImageAdapterCallBack chooseImageAdapterCallBack;
    //private int type = 1; //1录音 2视频 3照片 4相册选择 放到paths集合里面，因为类型混合
    private int videoflag = 0; //0拍过 1视频 2录音 3拍照或者相册
    private int delflag = 0; //0能删除 1不能删除

    public TaskEditImgAdapter(Context context) {
        mContext = context;
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();
    }

    public int getDelflag() {
        return delflag;
    }

    public void setDelflag(int delflag) {
        this.delflag = delflag;
    }

    public int getVideFlag() {
        return this.videoflag;
    }

    public void setVideoFlag(int videoflag) {
        this.videoflag = videoflag;
    }

    @Override
    public int getCount() {
        int size = paths.size();
        if (size >= 6) {
            return 6;
        }
        return size + 1;
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

    public List<String[]> getPaths() {
        return paths;
    }

    public int getPathSize(){
        if(paths != null){
            return paths.size();
        }
        return  0;
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
        if (arg0 == paths.size()) {
            String path = "drawable://" + R.drawable.addphoto;
            mViewHolder.imageButton.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(path, mViewHolder.imageView, options);
        } else if (paths.get(arg0)[0].equals("3") || paths.get(arg0)[0].equals("4")) {
            ImageLoader.getInstance().displayImage(paths.get(arg0)[1], mViewHolder.imageView, options);
            mViewHolder.imageButton.setVisibility(View.VISIBLE);
        } else if (paths.get(arg0)[0].equals("2")) {
            ImageLoader.getInstance().displayImage(paths.get(arg0)[1], mViewHolder.imageView, options);
            mViewHolder.imageButton.setVisibility(View.VISIBLE);
        } else if (paths.get(arg0)[0].equals("1")) {
            String path = "drawable://" + R.drawable.sound;
            ImageLoader.getInstance().displayImage(path, mViewHolder.imageView, options);
            mViewHolder.imageButton.setVisibility(View.VISIBLE);
        }
        if (delflag == 1)
            mViewHolder.imageButton.setVisibility(View.GONE);
        mViewHolder.imageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
            	if (paths.get(temp)[0].equals("1")) {
                    if (chooseImageAdapterCallBack != null) {
                        chooseImageAdapterCallBack.takePic(); //清空录音状态
                    }
                }
                if (paths.get(temp)[0].equals("2")) {
                    if (chooseImageAdapterCallBack != null) {
                        chooseImageAdapterCallBack.takeVideo(); //清空拍摄视频的状态
                    }
                }
                paths.remove(temp);
                if (videoflag == 1 || videoflag == 2 || videoflag == 3) //视频，录音,拍照或者相册
                    videoflag = 0;
                TaskEditImgAdapter.this.notifyDataSetChanged();
            }
        });

        mViewHolder.imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (temp != paths.size()) {
                	 String fpath = paths.get(temp)[1];
                	 if(fpath.contains("recording")){
                		 if(fpath.contains(".jpeg"))
                			 fpath=fpath.replace(".jpeg", ".mp4");
                		 Intent intent = new Intent(mContext, VideoShowActivity.class);
                         intent.putExtra("videopath", fpath);
                         intent.putExtra("islocal", ""); //1本地视频 0网络视频
                         mContext.startActivity(intent);
                	 }else if(fpath.contains(".jpeg") || fpath.contains(".jpg")|| fpath.contains(".png")){
                		 Intent intent = new Intent(mContext, ImageBrowserActivity.class);
                         int position = 0;
                         List<String> list = new ArrayList<String>();
                         list.add(fpath);
                         intent.putExtra("paths", (Serializable) list);
                         intent.putExtra("position", position);
                         mContext.startActivity(intent);
                	 }else if(fpath.contains(".amr")){
                		 Intent intent = new Intent(mContext, RecordShowActivity.class);
                		 intent.putExtra("islocal", ""); //1本地 0网络
                         intent.putExtra("recordpath", fpath);
                         mContext.startActivity(intent);
                	 }
                } else {
                    //圆形菜单特效
                    if (chooseImageAdapterCallBack != null) {
                        chooseImageAdapterCallBack.showArcLayout();
                    }
                    //底部弹出框特效
//					final String[] stringItems = { "拍照", "拍摄视频", "从相册选择" };
//					final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, mViewHolder.imageView);
//					dialog.isTitleShow(false).show();
//
//					dialog.setOnOperItemClickL(new OnOperItemClickL() {
//						@Override
//						public void onOperItemClick(AdapterView<?> parent, View view,
//								int position, long id) {
//							switch (position) {
//							case 0: {
//								if (chooseImageAdapterCallBack != null) {
//									chooseImageAdapterCallBack.takePic();
//								}
//								break;
//							}
//							case 1: {
//								if (chooseImageAdapterCallBack != null) {
//									chooseImageAdapterCallBack.takeVideo();
//								}
//								break;
//							}
//							case 2: {
//								if (chooseImageAdapterCallBack != null) {
//									chooseImageAdapterCallBack.choosePic();
//								}
//								break;
//							}
//							}
//							dialog.dismiss();
//						}
//					});
                }
            }

        });

        return arg1;
    }

    class ViewHolder {
        ImageView imageView;
        ImageButton imageButton;
    }

    public void setChooseImageAdapterCallBack(ChooseImageAdapterCallBack chooseImageAdapterCallBack) {
        this.chooseImageAdapterCallBack = chooseImageAdapterCallBack;
    }

    public void setDataChanged(List<String[]> paths) {
        this.paths = paths;
        this.notifyDataSetChanged();
    }

    public void addDataChanged(List<String[]> paths) {
        if (this.paths.size() >= MAX_IMG_SIZE) {
        	Tools.toastMsg(mContext, "最多上传"+MAX_IMG_SIZE+"个文件");
        	return;
            //this.paths.clear();
        }
        this.paths.addAll(paths);
        this.notifyDataSetChanged();
    }

}
