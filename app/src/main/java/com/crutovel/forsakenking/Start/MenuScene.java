package com.crutovel.forsakenking.Start;

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
import com.crutovel.forsakenking.utils.DefSharedPrefManager;
import com.crutovel.forsakenking.utils.ForsakenKingApp;
import com.crutovel.forsakenking.utils.Measure;

import java.util.ArrayList;
import java.util.Collections;
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

class MenuScene extends Scene {

    private static String GAME_NAME = "Forsaken King";
    private static int MAX_BG_ITERATIONS = 150;
    private static int BG_SPEED = 1;
    private static double MENU_BUTTON_MARGIN_FACTOR = 5.8;

    private Data d;
    private Bitmap buttonBgNotPressed;
    private Bitmap buttonBgPressed;
    private Bitmap buttonBgNotEnabled;
    private Bitmap menuBg;
    private Rect rectMenuBg;
    private Rect drawBg;
    private Paint drawPaint;
    private int iterBg;
    private int widthdiff;
    private int iterBgHelp;
    private int iterBgMax;
    private Paint textGameNamePaint;
    private int textPosX;
    private int textPosY;
    MButton continueButton;
    MButton newGame;
    MButton scores;
    MButton exit;

    MenuScene(RelativeLayout rLayout) {
        super(rLayout);
        d = new Data();
        drawBg = new Rect(0, 0, ForsakenKingApp.getSrcWidth(), ForsakenKingApp.getSrcHeight());
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectMenuBg = new Rect(0, 0, 0, 0);
        iterBg = BG_SPEED;

        textGameNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textGameNamePaint.setColor(Color.rgb(70, 0, 0));
        textGameNamePaint.setTextAlign(Paint.Align.CENTER);
        textGameNamePaint.setTextSize((int) (ForsakenKingApp.getWorkHeight() / 5 + ForsakenKingApp.getRestHeight()));
        textGameNamePaint.setTypeface(ForsakenKingApp.getFontArgor());

        textPosX = ((int) ForsakenKingApp.getWorkWidth() / 2);
        textPosY = (int) ((ForsakenKingApp.getWorkHeight() / 5 + ForsakenKingApp.getRestHeight()) -
                ((textGameNamePaint.descent() + textGameNamePaint.ascent()) / 2));

        continueButton = (MButton) rLayout.getChildAt(0);
        newGame = (MButton) rLayout.getChildAt(1);
        scores = (MButton) rLayout.getChildAt(2);
        exit = (MButton) rLayout.getChildAt(3);

        clear();
        initComponents();
    }

    private void initUiBitmaps() {
        continueButton.setBitmaps(buttonBgNotPressed, buttonBgPressed, buttonBgNotEnabled);
        newGame.setBitmaps(buttonBgNotPressed, buttonBgPressed, buttonBgNotEnabled);
        scores.setBitmaps(buttonBgNotPressed, buttonBgPressed, buttonBgNotEnabled);
        exit.setBitmaps(buttonBgNotPressed, buttonBgPressed, buttonBgNotEnabled);
    }

    public void initComponents() {
        String[] menuStrings = ForsakenKingApp.getContext().getResources().getStringArray(R.array.menuButtons);
        List<String> menuStringsList = new ArrayList<>();
        Collections.addAll(menuStringsList, menuStrings);

        int topMargin = d.buttonPosY;
        int textSize;
        textSize = getTextSizeForMenuButtons(d.buttonSizeX -
                (int) (Math.round(d.buttonSizeX / MENU_BUTTON_MARGIN_FACTOR)),menuStringsList );

        continueButton.defaultInit(d.buttonSizeX, d.buttonSizeY, d.buttonPosX, topMargin, textSize);
        continueButton.setText(menuStrings[0]);

        topMargin += d.buttonSizeY;
        newGame.defaultInit(d.buttonSizeX, d.buttonSizeY, d.buttonPosX, topMargin, textSize);
        newGame.setText(menuStrings[1]);

        topMargin += d.buttonSizeY;
        scores.defaultInit(d.buttonSizeX, d.buttonSizeY, d.buttonPosX, topMargin, textSize);
        scores.setText(menuStrings[2]);

        topMargin += d.buttonSizeY;
        exit.defaultInit(d.buttonSizeX, d.buttonSizeY, d.buttonPosX, topMargin, textSize);
        exit.setText(menuStrings[3]);
    }

    public void loadAssets() {

        buttonBgNotPressed = createBitmap(Assets.MENU_BUTTON_BG_NOT_PRESSED);
        buttonBgPressed = createBitmap(Assets.MENU_BUTTON_BG_PRESSED);
        buttonBgNotEnabled = createBitmap(Assets.MENU_BUTTON_BG_NOT_ENABLED);

        initUiBitmaps();

        menuBg = createBitmap(Assets.MENU_BG);
        int rightMargin = ForsakenKingApp.getSrcHeight() * menuBg.getWidth() / menuBg.getHeight();
        menuBg = Bitmap.createScaledBitmap(menuBg, rightMargin, ForsakenKingApp.getSrcHeight(), false);
        rectMenuBg.left = 0;
        rectMenuBg.right = ForsakenKingApp.getSrcWidth();
        widthdiff = menuBg.getWidth() - ForsakenKingApp.getSrcWidth();
        rectMenuBg.bottom = menuBg.getHeight();
        iterBgMax = 0;
        while (MAX_BG_ITERATIONS > widthdiff * (iterBgMax + 1)) {
            ++iterBgMax;
        }
        iterBgHelp = iterBgMax;
    }

    public void freeAssets() {
        Bitmap temp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        buttonBgNotPressed = temp;
        buttonBgPressed = temp;
        buttonBgNotEnabled = temp;
        initUiBitmaps();
        menuBg = temp;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(menuBg, rectMenuBg, drawBg, drawPaint);
        canvas.drawText(GAME_NAME, textPosX, textPosY, textGameNamePaint);
    }

    public void update() {
        if (rectMenuBg.left > widthdiff || rectMenuBg.left < 0) {
            iterBg = -iterBg;
            iterBgHelp = 0;
        }
        if (iterBgHelp == 0) {
            iterBgHelp = iterBgMax;
            rectMenuBg.left += iterBg;
            rectMenuBg.right += iterBg;
        } else {
            --iterBgHelp;
        }
    }

    public void prepare() {
        continueButton.setEnabled(DefSharedPrefManager.getPreviousGameExist());
        loadAssets();
    }

    public void clear() {
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
        private int buttonPosX;
        private int buttonPosY;

        private Data() {
            buttonSizeX = getMeasure(12.0);
            buttonSizeY = getMeasure(3.0);
            buttonPosX = getMeasure(10.0);
            buttonPosY = getMeasure(6.0, HALF_REST_HEIGHT);
        }
    }
}
