package com.crutovel.forsakenking.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

public class SpriteSheet {

    public Bitmap bitmap;
    public int width;
    public int height;

    SpriteSheet(int id) {
        Context con = ForsakenKingApp.getContext();
        bitmap = BitmapFactory.decodeResource(con.getResources(), id);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }

    SpriteSheet() {
        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
}
