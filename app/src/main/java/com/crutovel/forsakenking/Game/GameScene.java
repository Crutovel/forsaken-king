package com.crutovel.forsakenking.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.MButton;
import com.crutovel.forsakenking.Components.ProgressBar;
import com.crutovel.forsakenking.Components.Scene;
import com.crutovel.forsakenking.Enemy.EnemyPath;
import com.crutovel.forsakenking.Enemy.EnemyUnit;
import com.crutovel.forsakenking.R;
import com.crutovel.forsakenking.utils.Assets;
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

class GameScene extends Scene {

    private ProgressBar healthBar;
    private ProgressBar manaBar;
    private Paint bgPaint;
    private Bitmap healBgNotPressed;
    private Bitmap healBgPressed;
    private Bitmap healBgNotEnabled;
    private Bitmap freezeBgNotPressed;
    private Bitmap freezeBgPressed;
    private Bitmap freezeBgNotEnabled;
    private Bitmap soulKillBgNotPressed;
    private Bitmap soulKillBgPressed;
    private Bitmap soulKillBgNotEnabled;
    private Data d;

    MButton healSpell;
    MButton freezeSpell;
    MButton soulKillSpell;
    protected Level level;

    GameScene(RelativeLayout rLayout) {
        super(rLayout);

        level = new Level();
        d = new Data();
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        healSpell = (MButton) rLayout.getChildAt(0);
        freezeSpell = (MButton) rLayout.getChildAt(1);
        soulKillSpell = (MButton) rLayout.getChildAt(2);

        clear();
        initComponents();
    }

    void playerPerformAttack(MotionEvent motionEvent) {
        if (motionEvent.getRawX() < level.player.getLeftAttackSector()) {
            level.playerAttacks(EnemyPath.Type.left);
        } else {
            if (motionEvent.getRawX() < level.player.getFrontAttackSector()) {
                level.playerAttacks(EnemyPath.Type.front);
            } else {
                level.playerAttacks(EnemyPath.Type.right);
            }
        }
    }

    void playerPerformHealSpell() {
        level.playerHeal();
    }

    void playerPerformSoulKillSpell() {
        level.playerSoulKill();
    }

    void playerPerformFreezeSpell() {
        level.playerFreeze();
    }

    int getLevelStatus() {
        return level.status;
    }

    private void initUiBitmaps() {
        healSpell.setBitmaps(healBgNotPressed, healBgPressed, healBgNotEnabled);
        freezeSpell.setBitmaps(freezeBgNotPressed, freezeBgPressed, freezeBgNotEnabled);
        soulKillSpell.setBitmaps(soulKillBgNotPressed, soulKillBgPressed, soulKillBgNotEnabled);

    }

    private void drawUI(Canvas canvas) {
        healthBar.draw(canvas);
        manaBar.draw(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(level.conf.bg, 0, 0, bgPaint);
    }

    private void drawEnemyUnits(Canvas canvas) {
        for (EnemyUnit unit : level.activeEnemyUnits) {
            unit.graphics.draw(canvas);
        }
    }

    private void drawPlayer(Canvas canvas) {
        level.player.graphics.draw(canvas);
    }

    protected void initComponents() {
        double oneSq = ForsakenKingApp.getSectorDimen();

        healthBar = new ProgressBar();
        healthBar.setPosition(d.healthBarPosX, d.healthBarPosY);
        healthBar.setSize(d.healthBarSizeX, d.healthBarSizeY);
        healthBar.setBgColor(Color.DKGRAY);
        healthBar.setProgressColor(ForsakenKingApp.getResColor(R.color.healthBarProgress));
        healthBar.setStrokeWidth(d.healthBarStrokeWidth);
        healthBar.setText(String.valueOf(level.player.getHp()) + "/" +
                String.valueOf(level.player.getMaxHp()));
        Paint progressBarPaint = new Paint();
        progressBarPaint.setTextAlign(Paint.Align.CENTER);
        progressBarPaint.setTextSize(d.healthBarTextSize);
        progressBarPaint.setColor(Color.BLACK);
        progressBarPaint.setTypeface(ForsakenKingApp.getFontDiabloH());
        healthBar.setTextPaint(progressBarPaint);
        healthBar.setGradient(d.healthBarGradient, ForsakenKingApp.getResColor(R.color.healthBarGradient));
        progressBarPaint.setTextSize(d.manaBarTextSize);

        manaBar = new ProgressBar();
        manaBar.setPosition(d.manaBarPosX, d.manaBarPosY);
        manaBar.setSize(d.manaBarSizeX, d.manaBarSizeY);
        manaBar.setBgColor(Color.DKGRAY);
        manaBar.setProgressColor(ForsakenKingApp.getResColor(R.color.manaBarProgress));
        manaBar.setStrokeWidth(d.manaBarStrokeWidth);
        manaBar.setText(String.valueOf(level.player.getMana()) + "/" +
                String.valueOf(level.player.getMaxMana()));
        manaBar.setTextPaint(progressBarPaint);
        manaBar.setGradient(d.manaBarGradient, ForsakenKingApp.getResColor(R.color.manaBarGradient));

        healSpell.defaultInit(d.healSpellSizeX, d.healSpellSizeY, d.healSpellPosX,
                d.healSpellPosY, 0);
        freezeSpell.defaultInit(d.freezeSpellSizeX, d.freezeSpellSizeY, d.freezeSpellPosX,
                d.freezeSpellPosY, 0);
        soulKillSpell.defaultInit(d.soulKillSpellSizeX, d.soulKillSpellSizeY, d.soulKillSpellPosX,
                d.soulKillSpellPosY, 0);
    }

    protected void loadAssets() {
        healBgNotPressed = createBitmap(Assets.SPELL_BUTTON_HEAL_NOT_PRESSED);
        healBgPressed = createBitmap(Assets.SPELL_BUTTON_HEAL_PRESSED);
        healBgNotEnabled = createBitmap(Assets.SPELL_BUTTON_HEAL_NOT_ENABLED);
        freezeBgNotPressed = createBitmap(Assets.SPELL_BUTTON_FREEZE_NOT_PRESSED);
        freezeBgPressed = createBitmap(Assets.SPELL_BUTTON_FREEZE_PRESSED);
        freezeBgNotEnabled = createBitmap(Assets.SPELL_BUTTON_FREEZE_NOT_ENABLED);
        soulKillBgNotPressed = createBitmap(Assets.SPELL_BUTTON_SOULKILL_NOT_PRESSED);
        soulKillBgPressed = createBitmap(Assets.SPELL_BUTTON_SOULKILL_PRESSED);
        soulKillBgNotEnabled = createBitmap(Assets.SPELL_BUTTON_SOULKILL_NOT_ENABLED);
        initUiBitmaps();
    }

    protected void freeAssets() {
        Bitmap temp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        healBgNotPressed = temp;
        healBgPressed = temp;
        healBgNotEnabled = temp;
        freezeBgNotPressed = temp;
        freezeBgPressed = temp;
        freezeBgNotEnabled = temp;
        soulKillBgNotPressed = temp;
        soulKillBgPressed = temp;
        soulKillBgNotEnabled = temp;
        initUiBitmaps();
    }

    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawEnemyUnits(canvas);
        drawPlayer(canvas);
        drawUI(canvas);
    }

    public void update() {
        level.input();
        level.update();
        healthBar.setProgress(level.player.getHp() * 100 / level.player.getMaxHp());
        manaBar.setProgress(level.player.getMana() * 100 / level.player.getMaxMana());
        healthBar.setText(String.valueOf(level.player.getHp()) + "/" + String.valueOf(level.player.getMaxHp()));
        manaBar.setText(String.valueOf(level.player.getMana()) + "/" + String.valueOf(level.player.getMaxMana()));
    }

    public void prepare() {
        level.reset();
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

        private int healthBarSizeX;
        private int healthBarSizeY;
        private int healthBarPosX;
        private int healthBarPosY;
        private int healthBarStrokeWidth;
        private int healthBarGradient;
        private int healthBarTextSize;

        private int manaBarSizeX;
        private int manaBarSizeY;
        private int manaBarPosX;
        private int manaBarPosY;
        private int manaBarStrokeWidth;
        private int manaBarGradient;
        private int manaBarTextSize;

        private int healSpellSizeX;
        private int healSpellSizeY;
        private int healSpellPosX;
        private int healSpellPosY;

        private int freezeSpellSizeX;
        private int freezeSpellSizeY;
        private int freezeSpellPosX;
        private int freezeSpellPosY;

        private int soulKillSpellSizeX;
        private int soulKillSpellSizeY;
        private int soulKillSpellPosX;
        private int soulKillSpellPosY;

        private Data() {
            healthBarSizeX = getMeasure(10.0);
            healthBarSizeY = getMeasure(1.0);
            healthBarPosX = getMeasure(1.0);
            healthBarPosY = getMeasure(17.0, REST_HEIGHT);
            healthBarStrokeWidth = getMeasure(0.2);
            healthBarGradient = getMeasure(1.0);
            healthBarTextSize = getMeasure(1.0);

            manaBarSizeX = getMeasure(10.0);
            manaBarSizeY = getMeasure(1.0);
            manaBarPosX = getMeasure(1.0);
            manaBarPosY = getMeasure(15.0, REST_HEIGHT);
            manaBarStrokeWidth = getMeasure(0.2);
            manaBarGradient = getMeasure(1.0);
            manaBarTextSize = getMeasure(1.0);

            healSpellSizeX = getMeasure(3.0);
            healSpellSizeY = getMeasure(3.0);
            healSpellPosX = getMeasure(23.0);
            healSpellPosY = getMeasure(15.0, REST_HEIGHT);

            freezeSpellSizeX = getMeasure(3.0);
            freezeSpellSizeY = getMeasure(3.0);
            freezeSpellPosX = getMeasure(27.0);
            freezeSpellPosY = getMeasure(11.0, REST_HEIGHT);

            soulKillSpellSizeX = getMeasure(3.0);
            soulKillSpellSizeY = getMeasure(3.0);
            soulKillSpellPosX = getMeasure(27.0);
            soulKillSpellPosY = getMeasure(15.0, REST_HEIGHT);
        }
    }

}
