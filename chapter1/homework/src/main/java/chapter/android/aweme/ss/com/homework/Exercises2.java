package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        View view;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_relativelayout);
        view = findViewById(R.id.rootgc);
        int result = this.getAllChildViewCount(view);
        final String res = Integer.toString(result);
       final TextView textView = findViewById(R.id.tv_center);
       final Button button = findViewById(R.id.search);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(res);
            }
        });

    }

    public int getAllChildViewCount(View view) {

        ViewGroup viewGroup =(ViewGroup)view;

        int count = viewGroup.getChildCount();

        return count;
    }
}
