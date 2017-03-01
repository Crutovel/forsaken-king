package com.crutovel.forsakenking.Components;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.R;
import com.crutovel.forsakenking.utils.ForsakenKingApp;

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

public class MButton extends Button {

    protected int drawTextX;
    protected int drawTextY;
    protected Paint mTextPaint;
    protected Paint drawPaint;
    protected Bitmap bgDraw;
    protected Bitmap bgNotPressed;
    protected Bitmap bgPressed;
    protected Bitmap bgNotEnabled;
    protected int textColor;
    protected Rect srcBitmap;
    protected Rect destBitmap;


    public MButton(Context context) {
        super(context);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        bgNotPressed = Bitmap.createBitmap(1, 1, conf);
        bgPressed = bgNotPressed;
        bgNotEnabled = bgNotPressed;
        bgDraw = bgNotPressed;

        textColor = Color.BLACK;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(textColor);
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        srcBitmap = new Rect(0, 0, 0, 0);
        destBitmap = new Rect(0, 0, 0, 0);
        setBackgroundColor(Color.TRANSPARENT);
        setTextColor(Color.BLACK);
        setTypeface(ForsakenKingApp.getFontDiabloH());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bgDraw, srcBitmap, destBitmap, drawPaint);
        canvas.drawText(getText().toString(), drawTextX, drawTextY, mTextPaint);
    }

    @Override
    public void setTextColor(int color) {
        textColor = color;
        mTextPaint.setColor(textColor);
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        super.setEnabled(isEnabled);
        if (isEnabled) {
            bgDraw = bgNotPressed;
            setTextColor(Color.BLACK);
        } else {
            bgDraw = bgNotEnabled;
            setTextColor(ForsakenKingApp.getResColor(R.color.mButtonNotEnabledText));
        }
    }

    @Override
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        if (typeface != null && mTextPaint != null) {
            mTextPaint.setTypeface(typeface);
        }
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        mTextPaint.setTextSize(size);
    }

    public void setText(String str) {
        setText((CharSequence) str);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(getText().toString(), 0, getText().length(), bounds);
        drawTextX = getLayoutParams().width / 2 - bounds.width() / 2 - bounds.left;
        drawTextY = getLayoutParams().height / 2 + bounds.height() / 2 - bounds.bottom;
    }

    public void setBgNotPressedBitmap(Bitmap bitmap) {
        bgNotPressed = bitmap;
        bgDraw = bgNotPressed;
        srcBitmap.set(0, 0, bgDraw.getWidth(), bgDraw.getHeight());
        destBitmap.set(0, 0, getLayoutParams().width, getLayoutParams().height);
    }

    public void setBgPressedBitmap(Bitmap bitmap) {
        bgPressed = bitmap;
    }

    public void setBgNotEnabledBitmap(Bitmap bitmap) {
        bgNotEnabled = bitmap;
    }

    public void changeBgToNotPressed() {
        bgDraw = bgNotPressed;
    }

    public void changeBgToPressed() {
        bgDraw = bgPressed;
    }

    public void defaultInit(int sizeX, int sizeY, int leftMargin, int topMargin, int textSize) {
        RelativeLayout.LayoutParams paramsButton = new RelativeLayout.LayoutParams(sizeX, sizeY);
        paramsButton.leftMargin = leftMargin;
        paramsButton.topMargin = topMargin;
        setLayoutParams(paramsButton);
        setTextSize(textSize);
    }

    public void setBitmaps(Bitmap bgNotPressed, Bitmap bgPressed, Bitmap bgNotEnabled) {
        setBgNotPressedBitmap(bgNotPressed);
        setBgPressedBitmap(bgPressed);
        setBgNotEnabledBitmap(bgNotEnabled);
    }
}
