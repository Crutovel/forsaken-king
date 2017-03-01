package com.crutovel.forsakenking.utils;

import android.content.Context;
import android.graphics.Color;

import com.crutovel.forsakenking.Enemy.EnemyAnimation;
import com.crutovel.forsakenking.Enemy.EnemyPath;
import com.crutovel.forsakenking.Player.Player;
import com.crutovel.forsakenking.Player.PlayerAnimation;
import com.crutovel.forsakenking.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
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

public class XmlParcerService {

    private static int upgradeId = 0;

    private static void getUpgradeId(String upgradeName) {
        switch (upgradeName) {
            case "damageUpgrade": {
                upgradeId = DefSharedPrefManager.getPlayerCurrentNextUpdate(Player.Upgrades.damageUpgrade) - 1;
                break;
            }
            case "hpUpgrade": {
                upgradeId = DefSharedPrefManager.getPlayerCurrentNextUpdate(Player.Upgrades.hpUpgrade) - 1;
                break;
            }
            case "manaUpgrade": {
                upgradeId = DefSharedPrefManager.getPlayerCurrentNextUpdate(Player.Upgrades.manaUpgrade) - 1;
                break;
            }
        }
    }

    private static int value = 0;

    private static void putUpgradeValue(HashMap<Player.Upgrades, Integer> upgrades, String upgradeName) {
        switch (upgradeName) {
            case "damageUpgrade": {
                upgrades.put(Player.Upgrades.damageUpgrade, value);
                break;
            }
            case "hpUpgrade": {
                upgrades.put(Player.Upgrades.hpUpgrade, value);
                break;
            }
            case "manaUpgrade": {
                upgrades.put(Player.Upgrades.manaUpgrade, value);
                break;
            }
        }
    }

    public static HashMap<Player.Upgrades, Integer> getCurrentPlayerUpgrades() {
        HashMap<Player.Upgrades, Integer> upgrades = new HashMap<>();

        upgradeId = 0;
        value = 0;
        try {

            XmlPullParser xppLevel = ForsakenKingApp.getContext().getResources().getXml(R.xml.playerupgrades);

            while (xppLevel.getEventType() != XmlPullParser.END_DOCUMENT) {

                switch (xppLevel.getEventType()) {
                    case XmlPullParser.START_TAG: {
                        getUpgradeId(xppLevel.getName());

                        if (xppLevel.getName().equals("level")) {
                            if (upgradeId == Integer.parseInt(xppLevel.getAttributeValue(0))) {
                                value = Integer.parseInt(xppLevel.getAttributeValue(2));
                            }
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        putUpgradeValue(upgrades, xppLevel.getName());
                        break;
                    }
                }
                xppLevel.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return upgrades;
    }

    private static void putUpgradesCost(HashMap<Player.Upgrades, HashMap<Integer, Integer>> upgrades,
                                        String upgradeName, HashMap<Integer, Integer> upgradesCost) {
        switch (upgradeName) {
            case "hpUpgrade": {
                upgrades.put(Player.Upgrades.hpUpgrade, upgradesCost);
                break;
            }
            case "damageUpgrade": {
                upgrades.put(Player.Upgrades.damageUpgrade, upgradesCost);
                break;
            }
            case "manaUpgrade": {
                upgrades.put(Player.Upgrades.manaUpgrade, upgradesCost);
                break;
            }
        }
    }

    public static HashMap<Player.Upgrades, HashMap<Integer, Integer>> getPlayerUpgradesCost() {
        HashMap<Player.Upgrades, HashMap<Integer, Integer>> upgrades = new HashMap<>();

        try {
            XmlPullParser xppLevel = ForsakenKingApp.getContext().getResources().getXml(R.xml.playerupgrades);
            HashMap<Integer, Integer> upgradesCost = new HashMap<>();

            while (xppLevel.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xppLevel.getEventType()) {
                    case XmlPullParser.START_TAG: {
                        if (xppLevel.getName().equals("level")) {
                            upgradesCost.put(Integer.parseInt(xppLevel.getAttributeValue(0)),
                                    Integer.parseInt(xppLevel.getAttributeValue(1)));
                        } else {
                            upgradesCost = new HashMap<>();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        putUpgradesCost(upgrades, xppLevel.getName(), upgradesCost);
                    }
                }
                xppLevel.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return upgrades;
    }

    private static int skyColor = 0;
    private static String skyline = "";
    private static String terrain = "";
    private static int expReward = 0;
    private static HashMap<String, String> units;

    private static void getLevelConfigInfo(XmlPullParser xppLevel) {
        switch (xppLevel.getName()) {
            case LevelConfig.SKY: {
                skyColor = Color.rgb(Integer.parseInt(xppLevel.getAttributeValue(0)),
                        Integer.parseInt(xppLevel.getAttributeValue(1)),
                        Integer.parseInt(xppLevel.getAttributeValue(2)));
                break;
            }
            case LevelConfig.SKYLINE: {
                skyline = xppLevel.getAttributeValue(0);
                break;
            }
            case LevelConfig.TERRAIN: {
                terrain = xppLevel.getAttributeValue(0);
                break;
            }
            case LevelConfig.EXP_REWARD: {
                expReward = Integer.parseInt(xppLevel.getAttributeValue(0));
                break;
            }
            case LevelConfig.UNIT: {
                units.put(xppLevel.getAttributeValue(0),
                        xppLevel.getAttributeValue(1) + "_" + xppLevel.getAttributeValue(2));
                break;
            }
        }
    }

    public static LevelConfig getLevelConfig(int level) {
        skyColor = 0;
        skyline = "";
        terrain = "";
        expReward = 0;
        units = new HashMap<>();

        try {
            Context con = ForsakenKingApp.getContext();
            int levelId = con.getResources().getIdentifier("level" + level,
                    "xml", con.getPackageName());
            if (levelId == 0) return null;
            XmlPullParser xppLevel = con.getResources().getXml(levelId);

            while (xppLevel.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xppLevel.getEventType() == XmlPullParser.START_TAG) {
                    getLevelConfigInfo(xppLevel);
                }
                xppLevel.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LevelConfig(skyColor, skyline, terrain, expReward, units);
    }

    private static Animation animation;
    private static int sheetNum;

    private static void setPlayerAnimation(XmlPullParser xppPlayer) {
        animation = new PlayerAnimation(Integer.parseInt(xppPlayer.getAttributeValue(1)),
                PlayerAnimation.Type.valueOf(xppPlayer.getAttributeValue(2)));
        sheetNum = Integer.parseInt(xppPlayer.getAttributeValue(0));
    }

    private static int startPointX, startPointY;
    private static int sizeX, sizeY;
    private static double pivotPointX, pivotPointY;
    private static int frameCount;

    private static void addPlayerSprite(XmlPullParser xppPlayer) {
        startPointX = Integer.parseInt(xppPlayer.getAttributeValue(0));
        startPointY = Integer.parseInt(xppPlayer.getAttributeValue(1));

        sizeX = Integer.parseInt(xppPlayer.getAttributeValue(2));
        sizeY = Integer.parseInt(xppPlayer.getAttributeValue(3));

        if (Double.parseDouble(xppPlayer.getAttributeValue(4)) != 0.0)
            pivotPointX = Double.parseDouble(xppPlayer.getAttributeValue(4));
        else pivotPointX = 0.0;

        if (Double.parseDouble(xppPlayer.getAttributeValue(5)) != 0.0)
            pivotPointY = Double.parseDouble(xppPlayer.getAttributeValue(5));
        else pivotPointY = 0.0;

        frameCount = Integer.parseInt(xppPlayer.getAttributeValue(6));
        animation.addSprite(startPointX, startPointY, sizeX, sizeY,
                pivotPointX, pivotPointY, frameCount, sheetNum);
    }

    private static void setPlayerAnimation(List<Animation> animations, String animationTag) {
        if (animationTag.equals("animation")) {
            for (Animation item : animations) {
                if (((PlayerAnimation) item).name == ((PlayerAnimation) animation).name) {
                    item.addSprites(animation.sprites);
                    return;
                }
            }
            animations.add(animation);
        }
    }

    public static List<Animation> getPlayerAnimations(int xmlResource) {
        List<Animation> animations = new ArrayList<>();
        try {
            XmlPullParser xppPlayer = ForsakenKingApp.getContext().getResources().getXml(xmlResource);

            animation = new PlayerAnimation();

            while (xppPlayer.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xppPlayer.getEventType()) {
                    case XmlPullParser.START_TAG: {
                        if (xppPlayer.getName().equals("animation")) {
                            setPlayerAnimation(xppPlayer);
                        }
                        if (xppPlayer.getName().equals("sprite")) {
                            addPlayerSprite(xppPlayer);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        setPlayerAnimation(animations, xppPlayer.getName());
                    }
                    default: {
                        break;
                    }
                }
                xppPlayer.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animations;
    }

    private static void setEnemyAnimation(XmlPullParser xppEnemy) {
        animation = new EnemyAnimation(Integer.parseInt(xppEnemy.getAttributeValue(1)),
                EnemyAnimation.Type.valueOf(xppEnemy.getAttributeValue(2)),
                EnemyPath.Type.valueOf(xppEnemy.getAttributeValue(3)));
        sheetNum = Integer.parseInt(xppEnemy.getAttributeValue(0));
    }

    private static void addEnemySprite(XmlPullParser xppPlayer) {
        startPointX = Integer.parseInt(xppPlayer.getAttributeValue(0));
        startPointY = Integer.parseInt(xppPlayer.getAttributeValue(1));

        sizeX = Integer.parseInt(xppPlayer.getAttributeValue(2));
        sizeY = Integer.parseInt(xppPlayer.getAttributeValue(3));

        if (Double.parseDouble(xppPlayer.getAttributeValue(4)) != 0.0)
            pivotPointX = Double.parseDouble(xppPlayer.getAttributeValue(4));
        else pivotPointX = 0.0;

        if (Double.parseDouble(xppPlayer.getAttributeValue(5)) != 0.0)
            pivotPointY = Double.parseDouble(xppPlayer.getAttributeValue(5));
        else pivotPointY = 0.0;

        frameCount = Integer.parseInt(xppPlayer.getAttributeValue(6));

        animation.addSprite(startPointX, startPointY, sizeX, sizeY,
                pivotPointX, pivotPointY, frameCount, sheetNum);
    }

    private static void setEnemyAnimation(List<Animation> animations, String animationTag) {
        if (animationTag.equals("animation")) {
            for (Animation item : animations) {
                if (((EnemyAnimation) item).getName() == ((EnemyAnimation) animation).getName() &&
                        ((EnemyAnimation) item).getPath() == ((EnemyAnimation) animation).getPath()) {
                    return;
                }
            }
            animations.add(animation);
        }
    }

    public static List<Animation> getEnemyAnimations(int xmlResource) {
        List<Animation> animations = new ArrayList<>();
        try {
            XmlPullParser xppEnemy = ForsakenKingApp.getContext().getResources().getXml(xmlResource);

            animation = new EnemyAnimation();

            while (xppEnemy.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xppEnemy.getEventType()) {
                    case XmlPullParser.START_TAG: {
                        if (xppEnemy.getName().equals("animation")) {
                            setEnemyAnimation(xppEnemy);
                            break;
                        }
                        if (xppEnemy.getName().equals("sprite")) {
                            addEnemySprite(xppEnemy);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if (xppEnemy.getName().equals("animation")) {
                            setEnemyAnimation(animations, xppEnemy.getName());
                            break;
                        }
                        break;
                    }
                }
                xppEnemy.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animations;
    }
}
