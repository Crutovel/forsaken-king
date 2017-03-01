package com.crutovel.forsakenking.Player;

import android.graphics.Canvas;

import com.crutovel.forsakenking.R;
import com.crutovel.forsakenking.utils.Animation;
import com.crutovel.forsakenking.utils.Assets;
import com.crutovel.forsakenking.utils.Graphics;
import com.crutovel.forsakenking.utils.Sprite;
import com.crutovel.forsakenking.utils.XmlParcerService;

import java.util.ArrayList;

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


public class PlayerGraphics extends Graphics {

    private int xmlResource;
    private Player player;

    PlayerGraphics() {
        super();
        sheets = getSpriteSheets(Assets.PLAYER_SPRITE_SHEET);
        animations = new ArrayList<>();
        xmlResource = R.xml.playerspritesheetnodpi;
        animations = XmlParcerService.getPlayerAnimations(xmlResource);

    }

    public void draw(Canvas canvas) {
        src.set(currentSprite.startPointX, currentSprite.startPointY,
                currentSprite.startPointX + currentSprite.sizeX, currentSprite.startPointY + currentSprite.sizeY);

        dest.left = player.getPlayerPosX() - currentSprite.draw.left;
        dest.right = player.getPlayerPosX() + currentSprite.draw.right;
        dest.top = player.getPlayerPosY() - currentSprite.draw.top;
        dest.bottom = player.getPlayerPosY() + currentSprite.draw.bottom;

        canvas.drawBitmap(sheets.get(currentSprite.sheetNum).bitmap, src, dest, spritePaint);
    }

    protected double getScreenScaleFactor() {
        double maxSize = 0;
        for (Sprite item : getPlayerAnimation(PlayerAnimation.Type.simpleIdling).sprites) {
            if (item.sizeX > maxSize) maxSize = item.sizeX;
            if (item.sizeY > maxSize) maxSize = item.sizeY;
        }
        return player.getPlayerSize() / maxSize;
    }

    private Animation getPlayerAnimation(PlayerAnimation.Type name) {
        for (Animation item : animations) {
            if (((PlayerAnimation) item).name == name) {
                return item;
            }
        }
        return null;
    }

    protected void setAnimation(PlayerAnimation.Type name) {
        currentAnimation = getPlayerAnimation(name);
        setAnimation();
    }

    void attachPlayer(Player player) {
        this.player = player;
        setSpriteDrawRects();
    }
}
