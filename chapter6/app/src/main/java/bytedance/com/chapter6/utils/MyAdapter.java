package bytedance.com.chapter6.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import bytedance.com.chapter6.R;
import bytedance.com.chapter6.bean.Video;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private final Context context;
    private List<Video> videos;
    private OnClickPlayListener onClickPlayListener;

    public void setOnClickPlayListener(OnClickPlayListener onClickPlayListener) {
        this.onClickPlayListener = onClickPlayListener;
    }

    public MyAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Video video = videos.get(position);
        Glide.with(holder.itemView.getContext()).load(video.getImageId()).crossFade().into(holder.mImageView);
        holder.mImageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickPlayListener != null) {
                    onClickPlayListener.onPlayClick(holder.mImageView, video.getVideoPath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        ImageView mImageViewPlay;
        FrameLayout mVideoRootFl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoRootFl = (FrameLayout) itemView.findViewById(R.id.item_video_root_fl);
            mImageView = (ImageView) itemView.findViewById(R.id.item_imageview);
            mImageViewPlay = (ImageView) itemView.findViewById(R.id.item_imageplay);
        }
    }

    public interface OnClickPlayListener {
        void onPlayClick(View view, String videoPath);
    }
}
