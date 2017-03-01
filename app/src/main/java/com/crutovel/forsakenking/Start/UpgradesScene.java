package com.crutovel.forsakenking.Start;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.MButton;
import com.crutovel.forsakenking.Components.RadioButton;
import com.crutovel.forsakenking.Components.Scene;
import com.crutovel.forsakenking.Components.UpgradeButton;
import com.crutovel.forsakenking.Player.Player;
import com.crutovel.forsakenking.R;
import com.crutovel.forsakenking.utils.Assets;
import com.crutovel.forsakenking.utils.DefSharedPrefManager;
import com.crutovel.forsakenking.utils.ForsakenKingApp;
import com.crutovel.forsakenking.utils.Measure;
import com.crutovel.forsakenking.utils.XmlParcerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

class UpgradesScene extends Scene {

    private static final int MAX_BACKGROUND_ITERATIONS = 150;
    private static final double MENU_BUTTON_MARGIN_FACTOR = 5.8;
    private static final double UPGRADE_BUTTON_MARGIN_FACTOR = 5.0;
    private static final int BACKGROUND_SPEED = 1;

    private Data d;
    private String chooseUpgrade;

    private Bitmap butBgNotPressed;
    private Bitmap butBgPressed;
    private Bitmap butBgNotEnabled;
    private Bitmap longButBgNotPressed;
    private Bitmap longButBgPressed;
    private Bitmap longButBgNotEnabled;
    private Bitmap upgButBgNotPressed;
    private Bitmap upgButBgNotEnabled;
    private Bitmap upgButDamage;
    private Bitmap upgButHp;
    private Bitmap upgButMana;

    private int currentExp;

    private Bitmap menuBg;
    private Rect rectMenuBg;
    private Rect drawBg;
    private Paint drawPaint;
    private int iterBg;
    private int widthDiff;
    private int iterBgHelp;
    private int iterBgMax;

    MButton undo;
    MButton exp;
    MButton upgrade;
    MButton back;
    MButton start;
    RadioButton upgrades;
    UpgradeButton damage;
    UpgradeButton hp;
    UpgradeButton mana;

    UpgradesScene(RelativeLayout rLayout) {

        super(rLayout);
        d = new Data();
        drawBg = new Rect(0, 0, ForsakenKingApp.getSrcWidth(), ForsakenKingApp.getSrcHeight());
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectMenuBg = new Rect(0, 0, 0, 0);
        iterBg = BACKGROUND_SPEED;

        undo = (MButton) rLayout.getChildAt(0);
        exp = (MButton) rLayout.getChildAt(1);
        upgrade = (MButton) rLayout.getChildAt(2);
        back = (MButton) rLayout.getChildAt(3);
        start = (MButton) rLayout.getChildAt(4);
        upgrades = (RadioButton) rLayout.getChildAt(5);

        damage = (UpgradeButton) upgrades.getChildAt(0);
        hp = (UpgradeButton) upgrades.getChildAt(1);
        mana = (UpgradeButton) upgrades.getChildAt(2);

        String[] menuStrings = ForsakenKingApp.getContext().getResources().getStringArray(R.array.upgradeMenuButtons);
        chooseUpgrade = menuStrings[2];

        clear();
        initComponents();
    }

    private void initUiBitmaps() {
        undo.setBitmaps(butBgNotPressed, butBgPressed, butBgNotEnabled);
        exp.setBitmaps(butBgNotPressed, butBgPressed, butBgNotEnabled);
        upgrade.setBitmaps(longButBgNotPressed, longButBgPressed, longButBgNotEnabled);
        back.setBitmaps(butBgNotPressed, butBgPressed, butBgNotEnabled);
        start.setBitmaps(butBgNotPressed, butBgPressed, butBgNotEnabled);

        damage.setBitmaps(upgButBgNotPressed, upgButBgNotEnabled, upgButBgNotEnabled);
        damage.setUpgradeBitmap(upgButDamage);
        hp.setBitmaps(upgButBgNotPressed, upgButBgNotEnabled, upgButBgNotEnabled);
        hp.setUpgradeBitmap(upgButHp);
        mana.setBitmaps(upgButBgNotPressed, upgButBgNotEnabled, upgButBgNotEnabled);
        mana.setUpgradeBitmap(upgButMana);
    }

    void setDefaultUpgrades() {
        setCurrentExp(DefSharedPrefManager.getPlayerRestoreExp());
        upgrade.setText(chooseUpgrade);

        damage.setCurrentUpgrade(DefSharedPrefManager.getPlayerRestoreNextUpdate(Player.Upgrades.damageUpgrade));
        hp.setCurrentUpgrade(DefSharedPrefManager.getPlayerRestoreNextUpdate(Player.Upgrades.hpUpgrade));
        mana.setCurrentUpgrade(DefSharedPrefManager.getPlayerRestoreNextUpdate(Player.Upgrades.manaUpgrade));
        checkEnabledUpgradeButtons();
        upgrades.select(null);
    }

    void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
        exp.setText(String.valueOf(ForsakenKingApp.getContext().getResources().getStringArray(R.array.upgradeMenuButtons)[1])
                + String.valueOf(currentExp));
    }

    int getCurrentExp() {
        return currentExp;
    }

    void checkEnabledUpgradeButtons() {
        UpgradeButton upgradeButton;

        for (int i = 0; i < upgrades.getChildCount(); i++) {
            upgradeButton = (UpgradeButton) upgrades.getChildAt(i);

            upgradeButton.setEnabled(upgradeButton.getCurrentUpgradeCost() <= currentExp);

            if (upgradeButton.isLastUpgrade() && upgradeButton.isEnabled()) {
                upgradeButton.setEnabled(false);
            }
            upgrades.getChildAt(i).invalidate();
        }
    }

    private void setCurrentPlayerUpgrades() {
        HashMap<Player.Upgrades, HashMap<Integer, Integer>> upgrades = XmlParcerService.getPlayerUpgradesCost();

        for (Player.Upgrades item : Player.Upgrades.values()) {
            switch (item) {
                case damageUpgrade: {
                    damage.setUpgradesCost(upgrades.get(item));
                    break;
                }
                case hpUpgrade: {
                    hp.setUpgradesCost(upgrades.get(item));
                    break;
                }
                case manaUpgrade: {
                    mana.setUpgradesCost(upgrades.get(item));
                    break;
                }
            }
        }
    }

    protected void initComponents() {
        Context con = ForsakenKingApp.getContext();

        String[] menuUpgradesStrings = con.getResources().getStringArray(R.array.upgradeMenuButtons);
        List<String> menuStringsList = new ArrayList<>();
        String[] menuButtonsStrings = con.getResources().getStringArray(R.array.menuButtons);
        Collections.addAll(menuStringsList, menuButtonsStrings);

        int textSize;
        textSize = getTextSizeForMenuButtons(d.buttonSizeX -
                (int) (Math.round(d.buttonSizeX / MENU_BUTTON_MARGIN_FACTOR)), menuStringsList);

        undo.defaultInit(d.buttonSizeX, d.buttonSizeY, d.undoPosX, d.undoPosY, textSize);
        undo.setText(menuUpgradesStrings[0]);
        exp.defaultInit(d.buttonSizeX, d.buttonSizeY, d.expPosX, d.expPosY, textSize);
        exp.setText(menuUpgradesStrings[1]);
        upgrade.defaultInit(d.upgradeSizeX, d.upgradeSizeY, d.upgradePosX, d.upgradePosY, textSize);
        upgrade.setText(menuUpgradesStrings[2]);
        back.defaultInit(d.buttonSizeX, d.buttonSizeY, d.backPosX, d.backPosY, textSize);
        back.setText(menuUpgradesStrings[3]);
        start.defaultInit(d.buttonSizeX, d.buttonSizeY, d.startPosX, d.startPosY, textSize);
        start.setText(menuUpgradesStrings[4]);

        List<String> upgradesString = new ArrayList<>();
        upgradesString.add("0000");

        textSize = getTextSizeForMenuButtons(d.upgradesSizeX -
                (int) (Math.round(d.upgradesSizeX / UPGRADE_BUTTON_MARGIN_FACTOR)), upgradesString);


        damage.defaultInit(d.upgradesSizeX, d.upgradesSizeY, d.damagePosX, d.damagePosY, textSize);
        damage.setUpgradeName(Player.Upgrades.damageUpgrade, con.getString(R.string.damageUpgrade));
        damage.setText("0000");

        hp.defaultInit(d.upgradesSizeX, d.upgradesSizeY, d.hpPosX, d.hpPosY, textSize);
        hp.setUpgradeName(Player.Upgrades.hpUpgrade, con.getString(R.string.hpUpgrade));
        hp.setText("0000");

        mana.defaultInit(d.upgradesSizeX, d.upgradesSizeY, d.manaPosX, d.manaPosY, textSize);
        mana.setUpgradeName(Player.Upgrades.manaUpgrade, con.getString(R.string.manaUpgrade));
        mana.setText("0000");

        setCurrentPlayerUpgrades();
    }

    protected void loadAssets() {
        butBgNotPressed = createBitmap(Assets.MENU_BUTTON_BG_NOT_PRESSED);
        butBgPressed = createBitmap(Assets.MENU_BUTTON_BG_PRESSED);
        butBgNotEnabled = createBitmap(Assets.MENU_BUTTON_BG_NOT_ENABLED);
        longButBgNotPressed = createBitmap(Assets.UPGRADE_LONG_BUTTON_BG_NOT_PRESSED);
        longButBgPressed = createBitmap(Assets.UPGRADE_LONG_BUTTON_BG_PRESSED);
        longButBgNotEnabled = createBitmap(Assets.UPGRADE_LONG_BUTTON_BG_NOT_ENABLED);
        upgButBgNotPressed = createBitmap(Assets.UPGRADE_BUTTON_BG_NOT_PRESSED);
        upgButBgNotEnabled = createBitmap(Assets.UPGRADE_BUTTON_BG_NOT_ENABLED);
        upgButDamage = createBitmap(Assets.UPGRADE_BUTTON_DAMAGE);
        upgButHp = createBitmap(Assets.UPGRADE_BUTTON_HP);
        upgButMana = createBitmap(Assets.UPGRADE_BUTTON_MANA);

        initUiBitmaps();

        menuBg = createBitmap(Assets.MENU_BG);

        int rightMargin = ForsakenKingApp.getSrcHeight() * menuBg.getWidth() / menuBg.getHeight();

        menuBg = Bitmap.createScaledBitmap(menuBg, rightMargin, ForsakenKingApp.getSrcHeight(), false);
        rectMenuBg.left = 0;
        rectMenuBg.right = ForsakenKingApp.getSrcWidth();
        widthDiff = menuBg.getWidth() - ForsakenKingApp.getSrcWidth();
        rectMenuBg.bottom = menuBg.getHeight();
        iterBgMax = 0;
        while (MAX_BACKGROUND_ITERATIONS > widthDiff * (iterBgMax + 1)) {
            ++iterBgMax;
        }
        iterBgHelp = iterBgMax;
    }

    protected void freeAssets() {
        Bitmap temp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

        butBgNotPressed = temp;
        butBgPressed = temp;
        butBgNotEnabled = temp;
        longButBgNotPressed = temp;
        longButBgPressed = temp;
        longButBgNotEnabled = temp;
        upgButBgNotPressed = temp;
        upgButBgNotEnabled = temp;
        upgButDamage = temp;
        upgButHp = temp;
        upgButMana = temp;
        initUiBitmaps();
        menuBg = temp;
    }

    public void update() {
        if (rectMenuBg.left > widthDiff || rectMenuBg.left < 0) {
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

    public void draw(Canvas canvas) {
        canvas.drawBitmap(menuBg, rectMenuBg, drawBg, drawPaint);
    }

    public void prepare() {
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
        private int undoPosX;
        private int undoPosY;
        private int expPosX;
        private int expPosY;
        private int backPosX;
        private int backPosY;
        private int startPosX;
        private int startPosY;
        private int upgradeSizeX;
        private int upgradeSizeY;
        private int upgradePosX;
        private int upgradePosY;

        private int upgradesSizeX;
        private int upgradesSizeY;
        private int damagePosX;
        private int damagePosY;
        private int hpPosX;
        private int hpPosY;
        private int manaPosX;
        private int manaPosY;

        private Data() {
            buttonSizeX = getMeasure(12.0);
            buttonSizeY = getMeasure(3.0);
            undoPosX = getMeasure(1.0);
            undoPosY = getMeasure(1.0, HALF_REST_HEIGHT);
            expPosX = getMeasure(19.0);
            expPosY = getMeasure(1.0, HALF_REST_HEIGHT);
            backPosX = getMeasure(1.0);
            backPosY = getMeasure(15.0, HALF_REST_HEIGHT);
            startPosX = getMeasure(19.0);
            startPosY = getMeasure(15.0, HALF_REST_HEIGHT);

            upgradeSizeX = getMeasure(24.0);
            upgradeSizeY = getMeasure(3.0);
            upgradePosX = getMeasure(4.0);
            upgradePosY = getMeasure(4.0, HALF_REST_HEIGHT);

            upgradesSizeX = getMeasure(4.0);
            upgradesSizeY = getMeasure(6.0);
            damagePosX = getMeasure(4.0);
            damagePosY = getMeasure(8.0, HALF_REST_HEIGHT);
            hpPosX = getMeasure(14.0);
            hpPosY = getMeasure(8.0, HALF_REST_HEIGHT);
            manaPosX = getMeasure(24.0);
            manaPosY = getMeasure(8.0, HALF_REST_HEIGHT);
        }
    }
}
