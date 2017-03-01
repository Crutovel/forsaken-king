package com.crutovel.forsakenking.Game;


import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.MButton;
import com.crutovel.forsakenking.Components.Scene;
import com.crutovel.forsakenking.Player.Player;
import com.crutovel.forsakenking.utils.DefSharedPrefManager;
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

class GameLogic {

    private GameViewActivity activity;
    private Scene currentScene;
    GameScene gameScene;
    LoadingScene loadingScene;
    FinishScene finishScene;

    GameLogic(GameViewActivity activity) {
        this.activity = activity;
        currentScene = new Scene(new RelativeLayout(ForsakenKingApp.getContext())) {
            public void initComponents() {
            }

            public void loadAssets() {
            }

            public void freeAssets() {
            }

            public void draw(Canvas canvas) {
            }

            public void update() {
            }

            public void prepare() {
            }

            public void clear() {
            }

            public void hideSceneUi() {
            }

            public void showSceneUi() {
            }
        };
    }

    private void createHealSpellTouchListener() {
        gameScene.healSpell.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        gameScene.playerPerformHealSpell();
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createFreezeSpellTouchListener() {
        gameScene.freezeSpell.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        gameScene.playerPerformFreezeSpell();
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createSoulKillSpellTouchListener() {
        gameScene.soulKillSpell.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        gameScene.playerPerformSoulKillSpell();
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createRestartTouchListener() {
        finishScene.restart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        setScene(GameGraphicsScene.Game);
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private boolean gameFinishCheck() {
        return DefSharedPrefManager.getNextGameLevel() == finishScene.getLastLevel();
    }

    private void menuOrNextActionNextLevel() {
        DefSharedPrefManager.setPreviousGameExist(true);
        DefSharedPrefManager.setNextGameLevel(DefSharedPrefManager.getNextGameLevel() + 1);
        DefSharedPrefManager.setPlayerRestoreExp(DefSharedPrefManager.getPlayerCurrentExp() +
                gameScene.level.conf.expReward);
        for (Player.Upgrades item : Player.Upgrades.values()) {
            DefSharedPrefManager.setPlayerRestoreNextUpdate(item,
                    DefSharedPrefManager.getPlayerCurrentNextUpdate(item));
        }
    }

    private void createMenuOrNextTouchListener() {
        finishScene.menuOrNext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (finishScene.resultLevel) {
                            if (gameFinishCheck()) {
                                DefSharedPrefManager.restoreDefault();
                                DefSharedPrefManager.setGameFinished(true);
                            } else {
                                menuOrNextActionNextLevel();
                            }
                        }

                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        activity.finish();
                        return true;
                }
                return false;
            }
        });
    }

    void createOnTouchListeners(GameGraphicsScene scene) {
        switch (scene) {
            case Loading: {
                break;
            }
            case Game: {
                createHealSpellTouchListener();
                createFreezeSpellTouchListener();
                createSoulKillSpellTouchListener();
                break;
            }
            case Finish: {
                createRestartTouchListener();
                createMenuOrNextTouchListener();
                break;
            }
        }
    }

    public void update() {
        currentScene.update();
    }

    protected void setScene(GameGraphicsScene scene) {
        if (currentScene != null) {
            currentScene.hideSceneUi();
            currentScene.clear();
        }
        switch (scene) {
            case Loading: {
                currentScene = loadingScene;
                break;
            }
            case Game: {
                currentScene = gameScene;
                break;
            }
            case Finish: {
                currentScene = finishScene;
                break;
            }
        }

        currentScene.prepare();
        currentScene.showSceneUi();
        activity.surfaceView.setCurrentScene(currentScene);
        activity.input.setCurrentScene(scene);
    }

    void createScene(GameGraphicsScene scene, RelativeLayout rLayout) {
        switch (scene) {
            case Loading: {
                loadingScene = new LoadingScene(rLayout);
                break;
            }
            case Game: {
                gameScene = new GameScene(rLayout);
                break;
            }
            case Finish: {
                finishScene = new FinishScene(rLayout);
                break;
            }
        }
    }
}
