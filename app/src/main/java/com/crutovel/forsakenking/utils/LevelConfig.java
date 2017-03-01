package com.crutovel.forsakenking.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.HashMap;

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

public class LevelConfig {

    static final String SKY = "sky";
    static final String SKYLINE = "skyline";
    static final String TERRAIN = "terrain";
    static final String EXP_REWARD = "expReward";
    static final String UNIT = "unit";

    private Data d;
    private int skyColor;
    private SpriteSheet skylineSheet;
    private SpriteSheet terrainSheet;
    public Bitmap bg;
    public int expReward;
    public HashMap<String, String> units;

    LevelConfig(int skyColor, String skyline, String terrain, int expReward, HashMap<String, String> units) {
        d = new Data();
        this.skyColor = skyColor;
        this.expReward = expReward;
        this.units = units;
        skylineSheet = new SpriteSheet();
        terrainSheet = new SpriteSheet();

        getSpriteSheets(skyline, terrain);
        createBackgroundBitmap();
    }

    private void getSpriteSheets(String skyline, String terrain) {
        Context con = ForsakenKingApp.getContext();
        int id;
        id = con.getResources().getIdentifier(skyline, "drawable", con.getPackageName());
        if (id != 0) skylineSheet = new SpriteSheet(id);
        id = con.getResources().getIdentifier(terrain, "drawable", con.getPackageName());
        if (id != 0) terrainSheet = new SpriteSheet(id);
    }

    private void createBackgroundBitmap() {
        Rect src = new Rect();
        Rect dst = new Rect();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bg = Bitmap.createBitmap(ForsakenKingApp.getSrcWidth(), ForsakenKingApp.getSrcHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        src.set(0, 0, skylineSheet.bitmap.getWidth(), skylineSheet.bitmap.getHeight());
        dst.set(0, d.skylinePosY, bg.getWidth(), d.skylineSizeY);
        canvas.drawBitmap(skylineSheet.bitmap, src, dst, paint);

        src.set(0, 0, terrainSheet.bitmap.getWidth(), terrainSheet.bitmap.getHeight());
        dst.set(0, d.skylineSizeY, bg.getWidth(), ForsakenKingApp.getSrcHeight());
        canvas.drawBitmap(terrainSheet.bitmap, src, dst, paint);
        paint.setColor(skyColor);

        dst.set(0, 0, ForsakenKingApp.getSrcWidth(), (int) ForsakenKingApp.getRestHeight());
        canvas.drawRect(dst, paint);
    }

    private class Data extends Measure {

        private int skylinePosY;
        private int skylineSizeY;

        private Data() {
            skylinePosY = getMeasure(0.0, REST_HEIGHT);
            skylineSizeY = getMeasure(4.0, REST_HEIGHT);
        }
    }
}
