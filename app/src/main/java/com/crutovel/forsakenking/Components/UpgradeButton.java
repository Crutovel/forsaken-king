package com.crutovel.forsakenking.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Player.Player;
import com.crutovel.forsakenking.utils.ForsakenKingApp;
import com.crutovel.forsakenking.utils.RomanNum;

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

public class UpgradeButton extends MButton {
    private static final String MAX_UPGRADE_LEVEL = "VIII";
    private static final double LEFT_MARGIN_BMP_FACTOR = 0.125;
    private static final double RIGHT_MARGIN_BMP_FACTOR = 0.916;
    private static final double TOP_MARGIN_BMP_FACTOR = 0.083;
    private static final double BOTTOM_MARGIN_BMP_FACTOR = 0.583;
    private static final double UPGRADE_NUM_POS_X_FACTOR = 0.1428;

    private Player.Upgrades name;
    private String strName;
    protected int drawColorSelect;
    protected Bitmap upgrade;
    protected Rect srcUpgradeBitmap;
    protected Rect destUpgradeBitmap;
    protected Paint drawUpgradePaint;
    protected Paint upgradeLvlTextPaint;
    protected int drawLevelNumX;
    protected int drawLevelNumY;
    protected int upgradeLevel;
    protected int upgradeCost;
    protected boolean isLastUpgrade;
    protected HashMap<Integer, Integer> upgradesCost;

    public UpgradeButton(Context context) {
        super(context);
        strName = "";
        isLastUpgrade = false;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        upgrade = Bitmap.createBitmap(1, 1, conf);
        srcUpgradeBitmap = new Rect(0, 0, 0, 0);
        destUpgradeBitmap = new Rect(0, 0, 0, 0);
        drawUpgradePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        textColor = Color.BLACK;
        upgradeLvlTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        upgradeLvlTextPaint.setTextAlign(Paint.Align.LEFT);
        upgradeLvlTextPaint.setColor(textColor);
        upgradeLvlTextPaint.setTypeface(mTextPaint.getTypeface());
    }

    public void setUpgradeName(Player.Upgrades name, String strName) {
        this.name = name;
        this.strName = strName;
    }

    public Player.Upgrades getUpgradeName() {
        return name;
    }

    public int getUpgradeLvl() {
        return upgradeLevel;
    }

    public void setNextLevelUpgrade() {
        setCurrentUpgrade(++upgradeLevel);
    }

    public boolean isLastUpgrade() {
        return isLastUpgrade;
    }

    public void setCurrentUpgrade(int upgradeLevel) {
        if (upgradeLevel >= upgradesCost.size()) {
            setText("");
            this.upgradeLevel = upgradesCost.size() - 1;
            isLastUpgrade = true;
            setSelected(false);
            setEnabled(false);
        } else {
            isLastUpgrade = false;
            this.upgradeLevel = upgradeLevel;
            upgradeCost = upgradesCost.get(upgradeLevel);
            setText(String.valueOf(upgradeCost));
        }

    }

    public void setUpgradesCost(HashMap<Integer, Integer> upgradesCost) {
        this.upgradesCost = upgradesCost;
    }

    public int getCurrentUpgradeCost() {
        return upgradeCost;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(drawColorSelect);
        canvas.drawBitmap(upgrade, srcUpgradeBitmap, destUpgradeBitmap, drawUpgradePaint);
        canvas.drawText(RomanNum.values()[upgradeLevel].toString(), drawLevelNumX, drawLevelNumY, upgradeLvlTextPaint);
        super.onDraw(canvas);
    }

    public void setUpgradeBitmap(Bitmap upgrade) {
        this.upgrade = upgrade;
        srcUpgradeBitmap.set(0, 0, upgrade.getWidth(), upgrade.getHeight());
        int leftMargin = (int) Math.round(getLayoutParams().width * LEFT_MARGIN_BMP_FACTOR);
        int topMargin = (int) Math.round(getLayoutParams().height * TOP_MARGIN_BMP_FACTOR);
        int rightMargin = (int) Math.round(getLayoutParams().width * RIGHT_MARGIN_BMP_FACTOR);
        int bottomMargin = (int) Math.round(getLayoutParams().height * BOTTOM_MARGIN_BMP_FACTOR);
        destUpgradeBitmap.set(leftMargin, topMargin, rightMargin, bottomMargin);
    }

    @Override
    public void setBgNotPressedBitmap(Bitmap bitmap) {
        bgNotPressed = bitmap;
        bgDraw = bgNotPressed;
        srcBitmap.set(0, 0, bgDraw.getWidth(), bgDraw.getHeight());
        destBitmap.set(0, (int) Math.round(getLayoutParams().height *
                        (TOP_MARGIN_BMP_FACTOR + BOTTOM_MARGIN_BMP_FACTOR)),
                getLayoutParams().width, getLayoutParams().height);
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        mTextPaint.setTextSize(size);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(getText().toString(), 0, getText().length(), bounds);
        drawTextX = getLayoutParams().width / 2 - bounds.width() / 2 - bounds.left;
        drawTextY = getLayoutParams().height - (getLayoutParams().height / 6) + bounds.height() / 2 - bounds.bottom;
        String lvlNum = RomanNum.values()[upgradeLevel].toString();
        mTextPaint.getTextBounds(lvlNum, 0, lvlNum.length(), bounds);
        drawLevelNumX = (int) Math.round(getLayoutParams().width * UPGRADE_NUM_POS_X_FACTOR);
        drawLevelNumY = getLayoutParams().height / 2 + bounds.height() / 2 - bounds.bottom;

        float tSize = size;
        int desiredSize = getLayoutParams().width / 2 - (getLayoutParams().width / 8);
        bounds = new Rect();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.LEFT);
        Typeface diabloH = ForsakenKingApp.getFontDiabloH();
        paint.setTypeface(diabloH);
        paint.setTextSize(tSize);
        paint.getTextBounds(MAX_UPGRADE_LEVEL, 0, MAX_UPGRADE_LEVEL.length(), bounds);

        while (bounds.width() >= desiredSize) {
            tSize--;
            paint.setTextSize(tSize);
            paint.getTextBounds(MAX_UPGRADE_LEVEL, 0, MAX_UPGRADE_LEVEL.length(), bounds);
        }
        upgradeLvlTextPaint.setTextSize(tSize);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            drawColorSelect = Color.BLACK;
        } else {
            drawColorSelect = Color.TRANSPARENT;
        }
    }

    @Override
    public void setText(String str) {
        setText((CharSequence) str);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(getText().toString(), 0, getText().length(), bounds);
        drawTextX = getLayoutParams().width / 2 - bounds.width() / 2 - bounds.left;
        drawTextY = getLayoutParams().height - (getLayoutParams().height / 6) + bounds.height() / 2 - bounds.bottom;
    }

    @Override
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        if (typeface != null && upgradeLvlTextPaint != null) {
            upgradeLvlTextPaint.setTypeface(typeface);
        }
    }

    @Override
    public void defaultInit(int sizeX, int sizeY, int leftMargin, int topMargin, int textSize) {
        RelativeLayout.LayoutParams paramsButton = new RelativeLayout.LayoutParams(sizeX, sizeY);
        paramsButton.leftMargin = leftMargin;
        paramsButton.topMargin = topMargin;
        setLayoutParams(paramsButton);
        setTextSize(textSize);
    }
}
