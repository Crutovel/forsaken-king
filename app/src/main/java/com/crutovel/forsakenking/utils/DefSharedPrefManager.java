package com.crutovel.forsakenking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crutovel.forsakenking.Player.Player;

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

public class DefSharedPrefManager {

    private static final String PREVIOUS_GAME_EXIST = "previousGameExist";
    private static final String NEXT_GAME_LEVEL = "nextGameLevel";
    private static final String PLAYER_CURRENT = "playerCurrent";
    private static final String PLAYER_RESTORE = "playerRestore";
    private static final String PLAYER_EXP = "exp";
    private static final String GAME_FINISHED = "gameFinished";

    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor edit;

    static void setPrefs(Context con) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(con);
        edit = sharedPrefs.edit();
    }

    public static void setPreviousGameExist(boolean flag) {
        edit.putBoolean(PREVIOUS_GAME_EXIST, flag);
        edit.commit();
    }

    public static boolean getPreviousGameExist() {
        if (!sharedPrefs.contains(PREVIOUS_GAME_EXIST)) {
            restoreDefault();
        }
        return sharedPrefs.getBoolean(PREVIOUS_GAME_EXIST, false);
    }

    public static void setNextGameLevel(int level) {
        edit.putInt(NEXT_GAME_LEVEL, level);
        edit.commit();
    }

    public static int getNextGameLevel() {
        return sharedPrefs.getInt(NEXT_GAME_LEVEL, 0);
    }

    public static void setPlayerRestoreExp(int exp) {
        edit.putInt(PLAYER_RESTORE + PLAYER_EXP, exp);
        edit.commit();
    }

    public static int getPlayerRestoreExp() {
        return sharedPrefs.getInt(PLAYER_RESTORE + PLAYER_EXP, 0);
    }

    public static void setPlayerCurrentExp(int exp) {
        edit.putInt(PLAYER_CURRENT + PLAYER_EXP, exp);
        edit.commit();
    }

    public static int getPlayerCurrentExp() {
        return sharedPrefs.getInt(PLAYER_CURRENT + PLAYER_EXP, 0);
    }

    public static void setPlayerRestoreNextUpdate(Player.Upgrades update, int level) {
        edit.putInt(PLAYER_RESTORE + update.toString(), level);
        edit.commit();
    }

    public static int getPlayerRestoreNextUpdate(Player.Upgrades update) {
        return sharedPrefs.getInt(PLAYER_RESTORE + update.toString(), 0);
    }

    public static int getPlayerCurrentNextUpdate(Player.Upgrades update) {
        return sharedPrefs.getInt(PLAYER_CURRENT + update.toString(), 0);
    }

    public static void setPlayerCurrentNextUpdate(Player.Upgrades update, int level) {
        edit.putInt(PLAYER_CURRENT + update.toString(), level);
        edit.commit();
    }

    public static void setGameFinished(boolean isFinished) {
        edit.putBoolean(GAME_FINISHED, isFinished);
        edit.commit();
    }

    public static boolean getGameFinished() {
        return sharedPrefs.getBoolean(GAME_FINISHED, false);
    }

    public static void restoreDefault() {
        edit.clear();
        edit.commit();

        setGameFinished(false);
        setPreviousGameExist(false);
        setNextGameLevel(1);
        setPlayerRestoreExp(0);
        setPlayerCurrentExp(0);

        for (Player.Upgrades item : Player.Upgrades.values()) {
            setPlayerRestoreNextUpdate(item, 1);
            setPlayerCurrentNextUpdate(item, 1);
        }

    }
}
