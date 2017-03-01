package com.crutovel.forsakenking.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.MButton;
import com.crutovel.forsakenking.Components.Scene;
import com.crutovel.forsakenking.R;
import com.crutovel.forsakenking.utils.Assets;
import com.crutovel.forsakenking.utils.ForsakenKingApp;
import com.crutovel.forsakenking.utils.Measure;

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

class FinishScene extends Scene {

    private static final String XML_LEVEL_NAME = "level";
    private static final double MENU_BUTTON_MARGIN_FACTOR = 5.8;
    private Data d;
    private Paint finishPaint;
    private int textPosX;
    private int textPosY;
    private Bitmap buttonBgNotPressed;
    private Bitmap buttonBgPressed;
    private Bitmap buttonBgNotEnabled;
    private String resultText;

    MButton restart;
    MButton menuOrNext;
    boolean resultLevel;


    FinishScene(RelativeLayout rLayout) {
        super(rLayout);
        d = new Data();
        resultLevel = false;

        Typeface argor = ForsakenKingApp.getFontArgor();
        int workWidth = (int) ForsakenKingApp.getWorkWidth();
        int workHeight = (int) ForsakenKingApp.getWorkHeight();
        int restHeight = (int) ForsakenKingApp.getRestHeight();

        restart = (MButton) rLayout.getChildAt(0);
        menuOrNext = (MButton) rLayout.getChildAt(1);

        resultText = "";
        finishPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        finishPaint.setColor(Color.WHITE);
        finishPaint.setTextAlign(Paint.Align.CENTER);
        finishPaint.setTextSize(workHeight / 4 + restHeight);
        finishPaint.setTypeface(argor);
        textPosX = (workWidth / 2);
        textPosY = (int) ((workHeight / 3 + restHeight) - ((finishPaint.descent() + finishPaint.ascent()) / 2));

        clear();
        initComponents();
    }

    private List<String> getListTextMenuButtons() {
        List<String> list = new ArrayList<>();
        Context con = ForsakenKingApp.getContext();
        list.add(con.getString(R.string.restart));
        list.add(con.getString(R.string.back_to_menu));
        list.add(con.getString(R.string.next));
        return list;
    }

    private void initUiBitmaps() {
        restart.setBgNotPressedBitmap(buttonBgNotPressed);
        restart.setBgPressedBitmap(buttonBgPressed);
        restart.setBgNotEnabledBitmap(buttonBgNotEnabled);
        menuOrNext.setBgNotPressedBitmap(buttonBgNotPressed);
        menuOrNext.setBgPressedBitmap(buttonBgPressed);
        menuOrNext.setBgNotEnabledBitmap(buttonBgNotEnabled);
    }

    int getLastLevel() {
        int levelRes;
        for (int i = 1; ; i++) {
            levelRes = ForsakenKingApp.getContext().getResources().getIdentifier(XML_LEVEL_NAME + i,
                    "xml", ForsakenKingApp.getContext().getPackageName());
            if (levelRes == 0) {
                return i - 1;
            }
        }
    }

    void setResult(boolean resultLevel) {
        this.resultLevel = resultLevel;
    }

    protected void initComponents() {

        int textSize;
        textSize = getTextSizeForMenuButtons(d.buttonSizeX -
                (int) (Math.round(d.buttonSizeX / MENU_BUTTON_MARGIN_FACTOR)),getListTextMenuButtons());

        restart.defaultInit(d.buttonSizeX, d.buttonSizeY, d.restartPosX, d.restartPosY, textSize);
        restart.setText(ForsakenKingApp.getContext().getString(R.string.restart));

        menuOrNext.defaultInit(d.buttonSizeX, d.buttonSizeY, d.menuOrNextPosX, d.menuOrNextPosY, textSize);
        menuOrNext.setText(ForsakenKingApp.getContext().getString(R.string.back_to_menu));

    }

    protected void loadAssets() {
        buttonBgNotPressed = createBitmap(Assets.MENU_BUTTON_BG_NOT_PRESSED);
        buttonBgPressed = createBitmap(Assets.MENU_BUTTON_BG_PRESSED);
        buttonBgNotEnabled = createBitmap(Assets.MENU_BUTTON_BG_NOT_ENABLED);

        initUiBitmaps();
    }

    protected void freeAssets() {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap temp = Bitmap.createBitmap(1, 1, conf);
        buttonBgNotPressed = temp;
        buttonBgPressed = temp;
        buttonBgNotEnabled = temp;
        initUiBitmaps();
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawText(resultText, textPosX, textPosY, finishPaint);
    }

    public void update() {
    }

    public void prepare() {
        if (resultLevel) {
            resultText = ForsakenKingApp.getContext().getString(R.string.victory);
            menuOrNext.setText(ForsakenKingApp.getContext().getString(R.string.next));
        } else {
            resultText = ForsakenKingApp.getContext().getString(R.string.game_over);
            menuOrNext.setText(ForsakenKingApp.getContext().getString(R.string.back_to_menu));
        }

        loadAssets();
    }

    public void clear() {
        resultLevel = false;
        freeAssets();
    }

    public void hideSceneUi() {
        rLayout.setVisibility(View.GONE);
    }

    public void showSceneUi() {
        rLayout.setVisibility(View.VISIBLE);
    }

    private class Data extends Measure {

        private int buttonSizeX;
        private int buttonSizeY;
        private int restartPosX;
        private int restartPosY;
        private int menuOrNextPosX;
        private int menuOrNextPosY;

        private Data() {
            buttonSizeX = getMeasure(12.0);
            buttonSizeY = getMeasure(3.0);

            restartPosX = getMeasure(2.0);
            restartPosY = getMeasure(13.0, HALF_REST_HEIGHT);
            menuOrNextPosX = getMeasure(18.0);
            ;
            menuOrNextPosY = getMeasure(13.0, HALF_REST_HEIGHT);
            ;
        }
    }
}
