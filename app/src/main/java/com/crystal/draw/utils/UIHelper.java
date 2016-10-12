package com.crystal.draw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/10/12 0012.
 */

public class UIHelper {

    public static void start(Context context, Class<? extends Activity> clazz){
        context.startActivity(new Intent(context, clazz));
    }
}
