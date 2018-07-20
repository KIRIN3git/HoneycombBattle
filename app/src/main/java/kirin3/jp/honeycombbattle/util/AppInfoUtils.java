/*!
 @file AppInfoUtils.java
 ITS AP Forum FUKUOKA

 @author
    Created by Nagakura Hideharu.
 @copyright
    Copyright (c) 2018 Jorudan Co.,Ltd. All rights reserved.
 */
package kirin3.jp.honeycombbattle.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppInfoUtils {

    /**
     * バージョンを取得します。
     */
    public static String getVersionName(Context context, String packageName) {
        String versionName = "";
        PackageManager pm = context.getPackageManager();
        PackageInfo info;
        try {
            info = pm.getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return versionName;
    }
}
