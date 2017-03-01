package com.crutovel.forsakenking.Components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

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

public class ProgressBar extends SceneView {
    private static final double PROGRESS_MAX = 100.0;
    private double currentProgress;
    private String text;
    private Paint bgPaint;
    private Paint textPaint;
    private Paint strokePaint;
    private int textPosX;
    private int textPosY;
    private Paint progressPaint;

    public ProgressBar() {
        currentProgress = 0.0;
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(0);
    }

    public void setProgress(double progress) {
        if (progress <= PROGRESS_MAX) {
            currentProgress = progress;
        } else {
            currentProgress = PROGRESS_MAX;
        }
    }

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setSize(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void setStrokeWidth(int width) {
        strokePaint.setStrokeWidth(width);
    }

    public void setGradient(int y, int color) {
        Shader shader = new LinearGradient(0, 0, 0, y, color, progressPaint.getColor(), Shader.TileMode.MIRROR);
        progressPaint.setShader(shader);
    }

    public void setBgColor(int color) {
        bgPaint.setColor(color);
    }

    public void setProgressColor(int color) {
        progressPaint.setColor(color);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextPaint(Paint paint) {
        textPaint = paint;

        switch (paint.getTextAlign()) {
            case CENTER: {
                textPosX = posX + sizeX / 2;
                break;
            }
            case LEFT: {
                textPosX = posX;
                break;
            }
            case RIGHT: {
                if (textPaint.measureText(text) < sizeX) {
                    textPosX = posX + (int) textPaint.measureText(text);
                } else {
                    textPosX = posX;
                }
                break;
            }
        }
        //center text by Y
        textPosY = (int) (posY + sizeY / 2 - (textPaint.descent() + textPaint.ascent()) / 2);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(posX, posY, posX + sizeX, posY + sizeY, bgPaint);
        canvas.drawRect(posX, posY, (int) (posX + (sizeX / PROGRESS_MAX) * currentProgress), posY + sizeY, progressPaint);
        canvas.drawRect(posX, posY, posX + sizeX, posY + sizeY, strokePaint);
        canvas.drawText(text, textPosX, textPosY, textPaint);
    }
}
