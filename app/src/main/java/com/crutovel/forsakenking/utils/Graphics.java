package com.crutovel.forsakenking.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

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


abstract public class Graphics {

    protected List<SpriteSheet> sheets;
    protected List<Animation> animations;
    protected Animation currentAnimation;
    protected Sprite currentSprite;
    protected Paint spritePaint;
    protected Rect src;
    protected Rect dest;
    public int currentSpriteIndex;
    public int frameCount;

    public Graphics() {
        sheets = new ArrayList<>();
        animations = new ArrayList<>();
        spritePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        src = new Rect();
        dest = new Rect();
    }

    protected abstract void draw(Canvas canvas);

    protected abstract double getScreenScaleFactor();

    protected List<SpriteSheet> getSpriteSheets(String name) {
        List<SpriteSheet> sh = new ArrayList<>();
        Context con = ForsakenKingApp.getContext();
        int id;
        for (int i = 0; ; i++) {
            id = con.getResources().getIdentifier(name + String.valueOf(i), "drawable", con.getPackageName());
            if (id != 0) {
                sh.add(new SpriteSheet(id));
            } else {
                break;
            }
        }
        return sh;
    }

    private boolean nextSprite() {
        if (currentSpriteIndex == currentAnimation.sprites.size() - 1) {
            return false;
        } else {
            currentSpriteIndex++;
            currentSprite = currentAnimation.sprites.get(currentSpriteIndex);
            frameCount = currentSprite.frameCount + currentAnimation.framesDelayed;
            return true;
        }
    }

    public boolean nextFrame() {
        if (frameCount == 0) return nextSprite();
        else {
            frameCount--;
            return true;
        }
    }

    protected void setSpriteDrawRects() {
        for (Animation item : animations) {
            item.setDrawRectSprite(getScreenScaleFactor());
        }
    }

    protected void setAnimation() {
        currentSpriteIndex = -1;
        currentSprite = currentAnimation.sprites.get(0);
        frameCount = currentSprite.frameCount + currentAnimation.framesDelayed;
    }

    public void resetCurrentAnimation() {
        currentSpriteIndex = -1;
        currentSprite = currentAnimation.sprites.get(0);
        frameCount = currentSprite.frameCount + currentAnimation.framesDelayed;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }
}
