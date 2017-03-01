package com.crutovel.forsakenking.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.ProgressBar;
import com.crutovel.forsakenking.Components.Scene;
import com.crutovel.forsakenking.R;
import com.crutovel.forsakenking.utils.ForsakenKingApp;
import com.crutovel.forsakenking.utils.Measure;

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

class LoadingScene extends Scene {

    private Data d;
    private ProgressBar loadingPrBar;
    private Paint textPaint;
    private boolean isReady;

    LoadingScene(RelativeLayout rLayout) {
        super(rLayout);
        d = new Data();
        clear();
        initComponents();
    }

    void setProgress(Double value) {
        loadingPrBar.setProgress(value);
    }

    void setReady() {
        isReady = true;
        Context con = ForsakenKingApp.getContext();
        loadingPrBar.setText(con.getString(R.string.ready));
    }

    boolean isReady() {
        return isReady;
    }

    protected void initComponents() {
        Typeface diabloH = ForsakenKingApp.getFontDiabloH();
        Context con = ForsakenKingApp.getContext();

        loadingPrBar = new ProgressBar();
        loadingPrBar.setPosition(d.loadingBarPosX, d.loadingBarPosY);
        loadingPrBar.setSize(d.loadingBarSizeX, d.loadingBarSizeY);
        loadingPrBar.setBgColor(Color.DKGRAY);
        loadingPrBar.setProgressColor(ForsakenKingApp.getResColor(R.color.loadingProgressBar));

        loadingPrBar.setText(con.getString(R.string.loading));
        Paint progressBarPaint = new Paint();
        progressBarPaint.setTextAlign(Paint.Align.CENTER);
        progressBarPaint.setTextSize(d.loadingBarTextSize);
        progressBarPaint.setColor(Color.BLACK);
        progressBarPaint.setTypeface(diabloH);
        loadingPrBar.setTextPaint(progressBarPaint);
        loadingPrBar.setGradient(d.loadingBarGradient, Color.BLACK);
    }

    protected void loadAssets() {
    }

    protected void freeAssets() {
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        loadingPrBar.draw(canvas);
    }

    public void update() {
    }

    public void prepare() {
        loadAssets();
    }

    public void clear() {
        isReady = false;
        freeAssets();
    }

    public void hideSceneUi() {
        rLayout.setVisibility(View.GONE);
    }

    public void showSceneUi() {
        rLayout.setVisibility(View.VISIBLE);
    }

    private class Data extends Measure {
        private int loadingBarSizeX;
        private int loadingBarSizeY;
        private int loadingBarPosX;
        private int loadingBarPosY;
        private int loadingBarTextSize;
        private int loadingBarGradient;

        private Data() {
            loadingBarSizeX = getMeasure(16.0);
            loadingBarSizeY = getMeasure(2.5);
            loadingBarPosX = getMeasure(8.0);
            loadingBarPosY = getMeasure(7.5, HALF_REST_HEIGHT);
            loadingBarTextSize = getMeasure(3.0);
            loadingBarGradient = getMeasure(2.5);
        }
    }
}
