package com.crutovel.forsakenking.Enemy;


import android.graphics.Canvas;

import com.crutovel.forsakenking.utils.Animation;
import com.crutovel.forsakenking.utils.Graphics;
import com.crutovel.forsakenking.utils.Sprite;
import com.crutovel.forsakenking.utils.SpriteSheet;
import com.crutovel.forsakenking.utils.XmlParcerService;

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

public class EnemyGraphics extends Graphics {

    private EnemyUnit unit;

    private EnemyGraphics(List<SpriteSheet> sheets, List<Animation> animations) {
        this();
        this.sheets = sheets;
        this.animations = animations;
    }

    private EnemyGraphics() {
        super();
        currentAnimation = new EnemyAnimation();
        currentSprite = new Sprite();
    }

    public EnemyGraphics(String spriteSheetName, int xmlResource) {
        this();
        sheets = getSpriteSheets(spriteSheetName);
        animations = XmlParcerService.getEnemyAnimations(xmlResource);

    }

    private Animation getEnemyAnimation(EnemyAnimation.Type name, EnemyPath.Type path) {
        for (Animation item : animations) {
            if (((EnemyAnimation) item).getName() == name &&
                    ((EnemyAnimation) item).getPath() == path) {
                return item;
            }
        }
        return new EnemyAnimation();
    }

    protected double getScreenScaleFactor() {
        double maxSize = 0;
        for (Sprite item : getEnemyAnimation(EnemyAnimation.Type.moving, EnemyPath.Type.front).sprites) {
            if (item.sizeX > maxSize) {
                maxSize = item.sizeX;
            }
            if (item.sizeY > maxSize) {
                maxSize = item.sizeY;
            }
        }
        return unit.maxSizeOnScreen / maxSize;
    }

    protected void setAnimation(EnemyAnimation.Type name) {
        currentAnimation = getEnemyAnimation(name, unit.path.name);
        setAnimation();
    }

    void attachUnit(EnemyUnit unit) {
        this.unit = unit;
        setSpriteDrawRects();
    }

    public void draw(Canvas canvas) {
        src.set(currentSprite.startPointX, currentSprite.startPointY,
                currentSprite.startPointX + currentSprite.sizeX, currentSprite.startPointY + currentSprite.sizeY);
        dest.left = (int) Math.round(unit.posX - currentSprite.draw.left * unit.scaleSize);
        dest.right = (int) Math.round(unit.posX + currentSprite.draw.right * unit.scaleSize);
        dest.top = (int) Math.round(unit.posY - currentSprite.draw.top * unit.scaleSize);
        dest.bottom = (int) Math.round(unit.posY + currentSprite.draw.bottom * unit.scaleSize);

        canvas.drawBitmap(sheets.get(currentSprite.sheetNum).bitmap, src, dest, spritePaint);
    }

    public EnemyGraphics cloneUnit() {
        return new EnemyGraphics(this.sheets, this.animations);
    }
}
