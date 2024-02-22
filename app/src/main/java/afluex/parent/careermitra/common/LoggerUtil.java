package afluex.parent.careermitra.common;

import android.util.Log;

import afluex.parent.careermitra.BuildConfig;
import com.google.gson.Gson;

public class LoggerUtil {
    private static final String TAG = "OUTPUT";

    public static void logItem(Object src) {
        Gson gson = new Gson();
        if (BuildConfig.DEBUG)
            Log.e(TAG, "====:> " + gson.toJson(src));
    }
}
