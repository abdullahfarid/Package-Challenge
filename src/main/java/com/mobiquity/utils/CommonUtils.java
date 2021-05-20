package com.mobiquity.utils;

import com.mobiquity.beans.PackageInfo;

public class CommonUtils {

    private CommonUtils () {

    }

    public static boolean isNullOrEmptyArray(String[] array) {
        return (array == null || array.length == 0);
    }
    public static String removeSpecialCharacher(String str) {
        return str.replaceAll("[^\\p{ASCII}]", "");
    }
    public static boolean isNotNullorEmptyPackage(PackageInfo packageInfo) {
        return null != packageInfo.getItemInfoList() && !packageInfo.getItemInfoList().isEmpty();
    }
}
