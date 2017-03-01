package com.crutovel.forsakenking.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;

/*
 * Forsaken King
 * Copyright (C) 2016-2017 Lev Bukhanets
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

public class ForsakenKingApp extends Application {

    private static int ASPECT_RATIO_X = 16;
    private static int ASPECT_RATIO_Y = 9;

    private static Typeface diabloH;

    private void setFontDiabloH(String resPath) {
        diabloH = Typeface.createFromAsset(getAssets(), resPath);
    }

    public static Typeface getFontDiabloH() {
        return diabloH;
    }

    private static Typeface argor;

    private void setFontArgor(String resPath) {
        argor = Typeface.createFromAsset(getAssets(), resPath);
    }

    public static Typeface getFontArgor() {
        return argor;
    }

    private static int srcWidth;

    public static int getSrcWidth() {
        return srcWidth;
    }

    private static int srcHeight;

    public static int getSrcHeight() {
        return srcHeight;
    }

    private static double workWidth;

    public static double getWorkWidth() {
        return workWidth;
    }

    private static double workHeight;

    public static double getWorkHeight() {
        return workHeight;
    }

    private static double restHeight;

    public static double getRestHeight() {
        return restHeight;
    }

    private static double sectorDimen;

    public static double getSectorDimen() {
        return sectorDimen;
    }

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    private static Resources resources;
    private static DisplayMetrics metrics;

    public void onCreate() {
        super.onCreate();
        setFontDiabloH("fonts/diablo_h.ttf");
        setFontArgor("fonts/argor.ttf");

        resources = getResources();
        metrics = resources.getDisplayMetrics();
        context = getApplicationContext();

        DefSharedPrefManager.setPrefs(context);
    }

    private static boolean isBottomNavigationBar(int height, int heightFull) {
        return height - heightFull < 0;
    }

    public static void getScreenSize(int widthFull, int heightFull) {

        srcWidth = metrics.widthPixels;
        srcHeight = metrics.heightPixels;

        if (Build.VERSION.SDK_INT > 16) {
            if (isBottomNavigationBar(srcHeight, heightFull)) {
                workWidth = srcWidth;
                workHeight = ASPECT_RATIO_Y * srcWidth / ASPECT_RATIO_X;
                srcHeight = heightFull;
                restHeight = srcHeight - workHeight;
            } else {
                workWidth = srcWidth = widthFull;
                workHeight = ASPECT_RATIO_Y * srcWidth / ASPECT_RATIO_X;
                restHeight = srcHeight - workHeight;
            }
        } else {
            workWidth = srcWidth;
            workHeight = ASPECT_RATIO_Y * srcWidth / ASPECT_RATIO_X;
            restHeight = srcHeight - workHeight;
        }

        sectorDimen = workWidth / 32.0;
    }

    public static int getResColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId, null);
        } else {
            return context.getResources().getColor(colorId);
        }
    }
}
