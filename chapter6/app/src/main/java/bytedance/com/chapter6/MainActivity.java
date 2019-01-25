package bytedance.com.chapter6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

import java.util.List;

import bytedance.com.chapter6.bean.Video;
import bytedance.com.chapter6.utils.MyAdapter;
import bytedance.com.chapter6.utils.MyVideoView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FrameLayout videoRootViewFl;
    private FrameLayout fullScreen;
    private MyVideoView myVideoView;
    private List<Video> videos;
    private MyAdapter myAdapter;
    private int videoPosition = -1;
    private View lastView;


    private int[] imageIds = new int[]{R.drawable.tiktok};
    private static String videoPath = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        videoRootViewFl = (FrameLayout) findViewById(R.id.video_root_fl);
        fullScreen = (FrameLayout) findViewById(R.id.video_full_screen);
        prepareData();
        prepareAdapter();
        start();
    }
    protected void showVideo(View view,final String videoPath1){
        View view1;
        removeVideoView();
        if (videoRootViewFl.getVisibility() == View.VISIBLE) {
            videoRootViewFl.removeAllViews();
            videoRootViewFl.setVisibility(View.GONE);
        }
        if (myVideoView == null) {
            myVideoView = new MyVideoView(MainActivity.this);
        }
        myVideoView.stop();
        view1 = view.findViewById(R.id.item_imageview);
        if (view1 != null) view1.setVisibility(View.INVISIBLE);
        view1 = view.findViewById(R.id.item_imageplay);
        if (view1 != null) view1.setVisibility(View.INVISIBLE);
        view1 = view.findViewById(R.id.item_video_root_fl);
        if (view1 != null) {
            view1.setVisibility(View.VISIBLE);
            FrameLayout fl = (FrameLayout) view1;
            fl.removeAllViews();
            fl.addView(myVideoView, new ViewGroup.LayoutParams(-1, -1));
            videoPath = videoPath1;
            myVideoView.setVideoPath(videoPath);
            myVideoView.start();
        }
        lastView = view;
    }

    private void removeVideoView() {
        View v;
        if (lastView != null) {
            v = lastView.findViewById(R.id.item_imageview);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_imageplay);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_video_root_fl);
            if (v != null) {
                FrameLayout ll = (FrameLayout) v;
                ll.removeAllViews();
                v.setVisibility(View.GONE);
            }
        }
    }

    private void start() {
        myAdapter.setOnClickPlayListener(new MyAdapter.OnClickPlayListener() {
            @Override
            public void onPlayClick(View view, String videoPath) {
                showVideo(view, videoPath);
            }
        });
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if (videoPosition == -1 || videoRootViewFl.getVisibility() != View.VISIBLE) {
                    return;
                }
                if (videoPosition == recyclerView.getChildAdapterPosition(view)) {
                    videoPosition = -1;
                    showVideo(view, videoPath);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (myVideoView == null || videoRootViewFl.getVisibility() == View.VISIBLE) return;
                View v = view.findViewById(R.id.item_video_root_fl);
                if (v != null) {
                    FrameLayout fl = (FrameLayout) v;
                    videoPosition = recyclerView.getChildAdapterPosition(view);
                    if (fl.getChildCount() > 0) {
                        fl.removeAllViews();
                        int position = 0;
                        if (myVideoView.isPlaying()) {
                            position = myVideoView.getPosition();
                            myVideoView.stop();
                        }
                        videoRootViewFl.setVisibility(View.VISIBLE);
                        videoRootViewFl.removeAllViews();
                        lastView = videoRootViewFl;
                        videoRootViewFl.addView(myVideoView, new ViewGroup.LayoutParams(-1, -1));
                        myVideoView.setVideoPath(videoPath);
                        myVideoView.start();
                        myVideoView.seekTo(position);

                    }
                    fl.setVisibility(View.GONE);
                }
                v = view.findViewById(R.id.item_imageview);
                if (v != null) {
                    if (v.getVisibility() != View.VISIBLE) {
                        v.setVisibility(View.VISIBLE);
                    }
                }
                v = view.findViewById(R.id.item_imageplay);
                if (v != null) {
                    if (v.getVisibility() != View.VISIBLE) {
                        v.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void prepareAdapter() {

        myAdapter = new MyAdapter(MainActivity.this,videos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    public void prepareData(){
        for (int i = 0;i < 100;i++ ){
            Video video = new Video(imageIds[i],videoPath);
            videos.add(video);
        }
    }
}
