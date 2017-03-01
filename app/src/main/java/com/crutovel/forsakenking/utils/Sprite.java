package com.crutovel.forsakenking.utils;

import android.graphics.Rect;

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

public class Sprite {

    double pivotPointX;
    double pivotPointY;
    public int startPointX;
    public int startPointY;
    public int sizeX;
    public int sizeY;

    public int frameCount;
    public Rect draw;
    public int sheetNum;

    Sprite(int startPointX, int startPointY, int sizeX, int sizeY,
           double pivotPointX, double pivotPointY, int frameCount, int sheetNum) {
        this.startPointX = startPointX;
        this.startPointY = startPointY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.pivotPointX = pivotPointX;
        this.pivotPointY = pivotPointY;
        this.frameCount = frameCount;
        this.sheetNum = sheetNum;
        draw = new Rect();
    }

    public Sprite() {

    }
}
