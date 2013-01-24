
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;


public final class Log {

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;
    
    private static final boolean mLoginingEnabled = true;
    private static int mLogLevel = INFO;
    private static Map<String, Integer> mTagLogLevel = new HashMap<String, Integer>();
    private static boolean mLogToFile = false; //disable by default, It's expensive operation
    private static String mLogPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "default.log";

    
    private Log() {
        
    }
    
    /**
     * Send a {@link #VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int v(String tag, String msg) {
        return Log.write(VERBOSE, tag, msg, null);
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int v(String tag, String msg, Throwable tr) {
        return Log.write(VERBOSE, tag, msg, tr);
    }
    
    /**
     * Send a {@link #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(String tag, String msg) {
        return Log.write(DEBUG, tag, msg, null);
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int d(String tag, String msg, Throwable tr) {
        return Log.write(DEBUG, tag, msg, tr);
    }

    /**
     * Send a {@link #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(String tag, String msg) {
        return Log.write(INFO, tag, msg, null);
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int i(String tag, String msg, Throwable tr) {
        return Log.write(INFO, tag, msg, tr);
    }
    
    /**
     * Send a {@link #WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int w(String tag, String msg) {
        return Log.write(WARN, tag, msg, null);
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int w(String tag, String msg, Throwable tr) {
        return Log.write(WARN, tag, msg, tr);
    }
    
    
    /**
     * Send a {@link #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg) {
        return Log.write(ERROR, tag, msg, null);
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int e(String tag, String msg, Throwable tr) {
        return Log.write(ERROR, tag, msg, tr);
    }

    
    /**
     * Send a logLevel log message.
     * @param logLevel Used to identify log level/
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int write(int logLevel, String tag, String msg) {
        return write(logLevel, tag, msg, null);
    }
   
    /**
     * Send a logLevel log message and log the exception.
     * @param logLevel Used to identify log level/
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int write(int logLevel, String tag, String msg, Throwable tr) {
        int result = 0;
        if (mLoginingEnabled) {
            if (getLogLevelForTag(tag) <= logLevel) {
                String message = msg;
                
                if (tr != null) {
                    message += "\n" + android.util.Log.getStackTraceString(tr);
                }
                
                if (mLogToFile) {
                    logToFile(logLevel, tag, message);
                }
                
                result = android.util.Log.println(logLevel, tag, message);                
            }
        }
        return result;
    }
    
    private static void logToFile(int logLevel, String tag, String msg) {
        File logFile = new File(mLogPath);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String timeLog = new SimpleDateFormat("dd.MM.yy hh:mm:ss")
                    .format(new Date());

            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(logLevel + "\t" + timeLog + " (" + tag + ")\t" + msg + "\n");
            buf.newLine();
            buf.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
    
    public static void setLogToFileEnabled(boolean enabled) {
        mLogToFile = enabled;
    }
    
    public static boolean getLogToFileEnabled() {
        return mLogToFile;
    }
    
    public static void setLogLevel(int logLevel) {
        mLogLevel = logLevel;
    }
    
    public static int getLogLevel() {
        return mLogLevel;
    }
    
    public static void setLogLevelForTag(String tag, int logLevel) {
        mTagLogLevel.put(tag, logLevel);
    }
    
    public static int getLogLevelForTag(String tag) {
        int result = mLogLevel;
        if (mTagLogLevel.containsKey(tag)) {
            result = mTagLogLevel.get(tag);
        }
        return result;
    }
    
    public static void setLogPath(String path) {
        mLogPath = path;
    }
    
    public static String getLogPath() {
        return mLogPath;
    }
}
