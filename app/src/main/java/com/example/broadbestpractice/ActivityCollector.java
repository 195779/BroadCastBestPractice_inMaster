package com.example.broadbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    /*设置vector存储activity 还是用List的ArrayList存储activity？？？？？
     * Java中的List不具备线程安全性但是据说效率比vector高一点，在这呢？用哪个更好呢？*/

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity : activities){
            activity.finish();
        }
    }
}
