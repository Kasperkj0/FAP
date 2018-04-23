package fap.test;


import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Bloon on 20/04/18.
 */
public class UStats extends HomePage_Activity {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static final String TAG = UStats.class.getSimpleName();
    @SuppressWarnings("ResourceType")
    public static void getStats(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        int interval = UsageStatsManager.INTERVAL_YEARLY;
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        UsageEvents uEvents = usm.queryEvents(startTime, endTime);
        while (uEvents.hasNextEvent()){
            UsageEvents.Event e = new UsageEvents.Event();
            uEvents.getNextEvent(e);

            if (e != null){
                Log.d(TAG, "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
            }
        }
    }

    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 18);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);

        long endTime = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_WEEK, -1);
        //calendar.set(Calendar.DATE, 1);
        //calendar.set(Calendar.MONTH, Calendar.JANUARY);
        //calendar.set(Calendar.YEAR, 2015);

        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST,startTime, endTime);
        return usageStatsList;
    }

    public static void printUsageStats(List<UsageStats> usageStatsList,Context context){

        String totalTIF = new String();

        for (UsageStats u : usageStatsList) {
            PackageManager pm = context.getPackageManager();
            PackageInfo foregroundAppPackageInfo = null;
            try {
                foregroundAppPackageInfo = pm.getPackageInfo(u.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (u.getTotalTimeInForeground() > 0) {
                totalTIF = "Pkg: " + foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString() + "\t"
//                        + String.valueOf(((u.getTotalTimeInForeground() / 60000) / 60) / 24) + "d "
                        + String.valueOf(((u.getTotalTimeInForeground() / 60000) / 60) % 24) + "hr "
                        + String.valueOf((u.getTotalTimeInForeground() / 60000) % 60) + "m "
                        + String.valueOf((u.getTotalTimeInForeground() / 1000) % 60) + "s";


                Log.d(TAG, "Pkg: " + foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString() + "\t"
//                        + String.valueOf(((u.getTotalTimeInForeground() / 60000) / 60) / 24) + "d "
                        + String.valueOf(((u.getTotalTimeInForeground() / 60000) / 60) % 24) + "hr "
                        + String.valueOf((u.getTotalTimeInForeground() / 60000) % 60) + "m "
                        + String.valueOf((u.getTotalTimeInForeground() / 1000) % 60) + "s");
            }
        }

    }

    public static void printCurrentUsageStatus(Context context){
        printUsageStats(getUsageStatsList(context),context);
    }
    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }
}