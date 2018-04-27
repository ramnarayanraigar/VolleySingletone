package raigar.ramnarayan.volleysingletone;

import android.app.Application;

/**
 * Created by ramnarayan raigar on 27/4/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance(this);
    }
}
