package debug;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guiying.module.news.detail.NewsDetailActivity;

/**
 * <p>组件开发模式下，用于传递数据的启动Activity，集成模式下无效</p>
 *
 * @author 张华洋
 * @version V1.2.0
 * @name LauncherActivity
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里传值给需要调试的Activity
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("id", "9500116");
        startActivity(intent);
        finish();
    }

}
