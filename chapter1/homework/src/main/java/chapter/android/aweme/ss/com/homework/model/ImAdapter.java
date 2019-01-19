package chapter.android.aweme.ss.com.homework.model;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import chapter.android.aweme.ss.com.homework.R;
import chapter.android.aweme.ss.com.homework.widget.CircleImageView;


/**
 * 适配器
 */
public class ImAdapter extends RecyclerView.Adapter<ImAdapter.NumberViewHolder> {

    private static final String TAG = "MyAdapter";


//    private final ListItemClickListener mOnClickListener;

    List<Message> messages;
    public ImAdapter(List<Message> data) {
        messages = data;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);


        int backgroundColorForViewHolder = ContextCompat.getColor(context,R.color.colorBackground);
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder myViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
        Message message = messages.get(position);
        myViewHolder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final FrameLayout avatarHeader;
        private  final CircleImageView avatar;
        private final ImageView robotNotice;
        private final TextView title;
        private final TextView description;
        private final TextView time;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);

            avatarHeader = (FrameLayout) itemView.findViewById(R.id.iv_avatar_header);
            avatar = (CircleImageView)itemView.findViewById(R.id.iv_avatar);
            robotNotice = (ImageView)itemView.findViewById(R.id.robot_notice);
            title = (TextView)itemView.findViewById(R.id.tv_title);
            description = (TextView)itemView.findViewById(R.id.tv_description) ;
            time = (TextView)itemView.findViewById(R.id.tv_time) ;

            itemView.setOnClickListener(this);
        }

        public void bind(Message message) {
            int Icon = R.drawable.icon_girl;
            if(message.getIcon().equals("TYPE_ROBOT"))
            {
               Icon = R.drawable.session_robot;
            }else if(message.getIcon().equals("TYPE_STRANGER")){
                Icon = R.drawable.session_stranger;
            }
            else if(message.getIcon().equals("TYPE_SYSTEM")){
                Icon = R.drawable.session_system_notice;
            }
            else if(message.getIcon().equals("TYPE_GAME")){
                Icon = R.drawable.icon_micro_game_comment;
            }
            avatar.setImageResource(Icon);
            title.setText(message.getTitle());
            description.setText(message.getDescription());
            time.setText(message.getTime());
            if(message.isOfficial()==true){
                robotNotice.setVisibility(View.VISIBLE);
            }
            else  robotNotice.setVisibility(View.INVISIBLE);

        }



        @Override
        public void onClick(View v) {
//            if (mOnClickListener != null) {
//                mOnClickListener.onListItemClick(clickedPosition);
//            }
        }
    }

}

