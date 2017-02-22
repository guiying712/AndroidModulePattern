package debug;

import com.guiying.common.base.BaseApplication;
import com.guiying.common.http.HttpClient;
import com.guiying.common.http.OnResultListener;
import com.orhanobut.logger.Logger;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/2/15 20:11
 * @version V1.2.0
 * @name NewsApplication
 */
public class NewsApplication extends BaseApplication {

    String GAN_HUO_API = "http://gank.io/";

    @Override
    public void onCreate() {
        super.onCreate();
        HttpClient client = new HttpClient.Builder()
                .baseUrl(GAN_HUO_API)
                .url("api/data")
                .params("type", "福利")
                .params("count", String.valueOf(20))
                .params("page", String.valueOf(1))
                .build();
        client.post(new OnResultListener<String>() {

            @Override
            public void onSuccess(String result) {
                Logger.e(result);
            }

            @Override
            public void onError(int code, String message) {
                Logger.e(message);
            }

            @Override
            public void onFailure(String message) {
                Logger.e(message);
            }
        });
    }
}
