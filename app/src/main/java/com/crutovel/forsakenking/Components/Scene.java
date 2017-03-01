package com.crutovel.forsakenking.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.utils.ForsakenKingApp;

import java.util.List;

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

public abstract class Scene {
    private static final String DRAWABLE_RESOURCE = "drawable";

    protected RelativeLayout rLayout;

    public Scene(RelativeLayout rLayout) {
        this.rLayout = rLayout;
    }

    protected abstract void initComponents();

    protected abstract void loadAssets();

    protected abstract void freeAssets();

    public abstract void draw(Canvas canvas);

    public abstract void update();

    public abstract void prepare();

    public abstract void clear();

    public abstract void hideSceneUi();

    public abstract void showSceneUi();

    protected static Bitmap createBitmap(String filename) {
        Context context = ForsakenKingApp.getContext();
        return BitmapFactory.decodeResource(context.getResources(),
                context.getResources().getIdentifier(filename, DRAWABLE_RESOURCE, context.getPackageName()));
    }

    private String getBiggestString(List<String> list) {
        int tSize = 0;
        String text = "";
        for (String item : list) {
            if (tSize < item.length()) {
                tSize = item.length();
                text = item;
            }
        }
        return text;
    }

    protected int getTextSizeForMenuButtons(int size, List<String> list) {
        String text = getBiggestString(list);
        int tSize = 1;
        Rect bounds = new Rect();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.LEFT);
        Typeface diabloH = ForsakenKingApp.getFontDiabloH();
        paint.setTypeface(diabloH);
        paint.setTextSize(tSize);
        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.width() <= size) {
            tSize++;
            paint.setTextSize(tSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }
        return --tSize;
    }
}
