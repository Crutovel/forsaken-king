package com.crutovel.forsakenking.utils;

import java.util.ArrayList;
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

abstract public class Animation {


    public List<Sprite> sprites;
    int framesDelayed;

    public Animation(int frameDelayed) {
        this();
        this.framesDelayed = frameDelayed;
    }

    public Animation() {
        sprites = new ArrayList<>();
    }

    void addSprite(int startPointX, int startPointY, int sizeX, int sizeY,
                   double pivotPointX, double pivotPointY, int frameCount, int sheetNum) {
        sprites.add(new Sprite(startPointX, startPointY, sizeX, sizeY,
                pivotPointX, pivotPointY, frameCount, sheetNum));
    }

    void addSprites(List<Sprite> sprites) {
        for (int i = 0; i < sprites.size(); i++) {
            this.sprites.add(sprites.get(i));
        }
    }

    void setDrawRectSprite(double scaleScreenFactor) {
        int scaleSizeX, scaleSizeY;
        for (Sprite item : sprites) {
            scaleSizeX = (int) Math.round(scaleScreenFactor * item.sizeX);
            item.draw.left = (int) Math.round(scaleSizeX * item.pivotPointX);
            item.draw.right = scaleSizeX - item.draw.left;

            scaleSizeY = (int)Math.round (scaleScreenFactor * item.sizeY);
            item.draw.top = (int) Math.round(scaleSizeY * item.pivotPointY);
            item.draw.bottom = scaleSizeY - item.draw.top;
        }
    }
}
