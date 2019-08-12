package op.beishi7.core.http;

import android.util.Log;

public class HttpLogger {
    static boolean sLogEnable = false;
    static String sLogTag = "CzHttp";
    
    public static void logD(String msg) {
        if (sLogEnable) {
            Log.d(sLogTag, msg);
        }
    }
    
    public static void logW(String msg) {
        if (sLogEnable) {
            Log.w(sLogTag, msg);
        }
    }
    
    public static void logE(String msg) {
        if (sLogEnable) {
            Log.e(sLogTag, msg);
        }
    }
    
    public static void log(Exception e) {
        if (sLogEnable && null != e) {
            Log.e(sLogTag, "", e);
        }
    }
    
}
