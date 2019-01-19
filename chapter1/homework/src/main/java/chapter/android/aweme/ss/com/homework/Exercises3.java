package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.io.InputStream;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.ImAdapter;
import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;

/**
 * 大作业:实现一个抖音消息页面,所需资源已放在res下面
 */
public class Exercises3 extends AppCompatActivity  {

    private static final String TAG = "Gechen";

    private ImAdapter mAdapter;
    private RecyclerView mNumbersListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        mNumbersListView = findViewById(R.id.rv_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNumbersListView.setLayoutManager(linearLayoutManager);

        try {
            InputStream assetInput = getAssets().open("data.xml");
            List<Message> messages = PullParser.pull2xml(assetInput);
            mAdapter = new ImAdapter(messages);
            mNumbersListView.setAdapter(mAdapter);
        } catch (Exception exception) {
            exception.printStackTrace();
    }



    }

}
