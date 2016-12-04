package net.hy.android.media;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Author  hycoolman
 * Description video列表adapter
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    // private List<VideoItemData> list;
    public VideoAdapter(Context context) {
        list = new ArrayList<>();
    }

    private List<ChildrenAnimation> list;

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        VideoViewHolder holder = new VideoViewHolder(view);
        view.setTag(holder);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(List<ChildrenAnimation> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout showView;
        private TextView tvTitle, tvDes, tvShare;


        public VideoViewHolder(View itemView) {
            super(itemView);
            showView = (RelativeLayout) itemView.findViewById(R.id.showview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDes = (TextView) itemView.findViewById(R.id.tvDes);
            tvShare = (TextView) itemView.findViewById(R.id.tvShare);
        }

        public void update(final int position) {
            tvTitle.setText(list.get(position).getTitle());
            tvDes.setText(list.get(position).getDescn());
            tvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onShare(position);
                    }
                }
            });
            showView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showView.setVisibility(View.GONE);
                    if (clickListener != null)
                        clickListener.onclick(position);
                }
            });
        }
    }

    private onClick clickListener;

    public void setClickListener(onClick clickListener) {
        this.clickListener = clickListener;
    }

    public interface onClick {
        void onclick(int position);

        void onShare(int position);
    }
}
