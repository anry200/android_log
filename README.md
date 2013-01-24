android_log
===========

Custom android Log class with some features :)


## Instalation

1. Copy Log class to you project

2. Replace import for Log class

        //before
        import android.util.Log;  //Android SDK Log class
        //after
        import com.example.Log; //custom Log class

## Usage


* Log to file.

        Log.setLogPath("/sdcard/app_name.log");
        //default value Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "default.log";
        Log.setLogToFileEnabled(true); //disable by default

* set log level for all Tags

        Log.setLogLevel( level ) // level one of VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT

* set log level for each Tag

        Log.setLogLevelForTag("MyTag", level) //level one of  VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT

* disable all Log messages for release build

        class Log {
        private static final boolean mLoginingEnabled = false; 
        // final modifier used to remove unsused sources by Proguard. (All logging information in build)


### Example

        class Some {
            public static final String TAG = Some.class.getSimpleName(); 

            public void method() {

                Log.setLogLevel(Log.ERROR);
                Log.d(TAG, "test1.1");
                Log.setLogLevelForTag(TAG, Log.VERBOSE);
                Log.d(TAG, "test1.2");

            }
        }
        //output "V/Some ( ): test1.2"
