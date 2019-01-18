package bytedance.com.helloworld;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = findViewById(R.id.button);
        SeekBar seekBar = findViewById(R.id.seekBar);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        final CheckBox checkBox = findViewById(R.id.checkBox);
        final TextView info = findViewById(R.id.info);
        final TextView seekbarinfo = findViewById(R.id.progress);

        progressBar.setMax(200);
        progressBar.setProgress(100);
        progressBar.setSecondaryProgress(150);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setText("has Changed!");
                Log.d("MainActivity",info.getText().toString());
                Log.d("MainActivity",checkBox.isChecked()?"true":"false");
                Log.d("MainActivity",progressBar.isShown()?"true":"false");
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbarinfo.setText("拖动停止");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekbarinfo.setText("开始拖动");
            }
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                seekbarinfo.setText("当前进度："+progress+"%");
            }
        });

    }
}
