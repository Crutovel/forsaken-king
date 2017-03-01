package com.crutovel.forsakenking.Start;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.MButton;
import com.crutovel.forsakenking.Components.Scene;
import com.crutovel.forsakenking.Components.UpgradeButton;
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

class StartLogic {

    private StartActivity activity;
    private Scene currentScene;
    StartScene startScene;
    MenuScene menuScene;
    UpgradesScene upgradesScene;

    StartLogic(StartActivity activity) {
        this.activity = activity;
        currentScene = new Scene(new RelativeLayout(ForsakenKingApp.getContext())) {
            public void loadAssets() {
            }

            public void freeAssets() {
            }

            public void initComponents() {
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

    void createScene(StartGraphicsScene scene, RelativeLayout rLayout) {
        switch (scene) {
            case Start: {
                startScene = new StartScene(rLayout);
                break;
            }
            case Menu: {
                menuScene = new MenuScene(rLayout);
                break;
            }
            case Upgrades: {
                upgradesScene = new UpgradesScene(rLayout);
                break;
            }
        }
    }

    protected void setScene(StartGraphicsScene scene) {
        if (currentScene != null) {
            currentScene.hideSceneUi();
            currentScene.clear();
        }
        switch (scene) {
            case Start: {
                currentScene = startScene;
                break;
            }
            case Menu: {
                currentScene = menuScene;
                break;
            }
            case Upgrades: {
                currentScene = upgradesScene;
                upgradesScene.setDefaultUpgrades();
            }
        }

        currentScene.prepare();
        currentScene.showSceneUi();
        activity.surfaceView.setCurrentScene(currentScene);
        activity.input.setCurrentScene(scene);
    }

    private void createContinueTouchListener() {
        menuScene.continueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        setScene(StartGraphicsScene.Upgrades);
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createNewGameTouchListener() {
        menuScene.newGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        DefSharedPrefManager.restoreDefault();
                        setScene(StartGraphicsScene.Upgrades);
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createAboutTouchListener() {
        menuScene.scores.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createExitTouchListener() {
        menuScene.exit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        activity.finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void createUndoTouchListener() {
        upgradesScene.undo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        upgradesScene.setDefaultUpgrades();
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createExpTouchListener() {
        upgradesScene.exp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createUpgradeTouchListener() {
        upgradesScene.upgrade.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        UpgradeButton selectedUpgrBut = upgradesScene.upgrades.getSelectedUpgradeButton();

                        if (selectedUpgrBut != null && selectedUpgrBut.isEnabled()) {
                            upgradesScene.setCurrentExp(upgradesScene.getCurrentExp() -
                                    selectedUpgrBut.getCurrentUpgradeCost());
                            selectedUpgrBut.setNextLevelUpgrade();
                        }

                        upgradesScene.checkEnabledUpgradeButtons();
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createBackTouchListener() {
        upgradesScene.back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        upgradesScene.upgrades.select(null);
                        setScene(StartGraphicsScene.Menu);
                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        return true;
                }
                return false;
            }
        });
    }

    private void createStartTouchListener() {
        upgradesScene.start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((MButton) v).changeBgToPressed();
                        v.invalidate();
                        return true;
                    case MotionEvent.ACTION_UP:
                        DefSharedPrefManager.setPlayerCurrentExp(upgradesScene.getCurrentExp());
                        DefSharedPrefManager.setPlayerCurrentNextUpdate(Player.Upgrades.damageUpgrade,
                                upgradesScene.damage.getUpgradeLvl());
                        DefSharedPrefManager.setPlayerCurrentNextUpdate(Player.Upgrades.hpUpgrade,
                                upgradesScene.hp.getUpgradeLvl());
                        DefSharedPrefManager.setPlayerCurrentNextUpdate(Player.Upgrades.manaUpgrade,
                                upgradesScene.mana.getUpgradeLvl());

                        ((MButton) v).changeBgToNotPressed();
                        v.invalidate();
                        activity.startGameActivity();
                        return true;
                }
                return false;
            }
        });
    }

    private void onTouchUpgradeButton(View v) {
        UpgradeButton upgradeButton = (UpgradeButton) v;
        upgradesScene.upgrades.select(upgradeButton);
        switch (upgradeButton.getUpgradeName()) {
            case damageUpgrade: {
                upgradesScene.upgrade.setText("+ " + upgradesScene.damage.getUpgradeName() + " +");
                break;
            }
            case hpUpgrade: {
                upgradesScene.upgrade.setText("+ " + upgradesScene.hp.getUpgradeName() + " +");
                break;
            }
            case manaUpgrade: {
                upgradesScene.upgrade.setText("+ " + upgradesScene.mana.getUpgradeName() + " +");
                break;
            }
        }
    }

    private void createUpgradesTouchListener() {
        for (int i = 0; i < upgradesScene.upgrades.getChildCount(); i++) {
            upgradesScene.upgrades.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            return true;
                        case MotionEvent.ACTION_UP:
                            onTouchUpgradeButton(v);
                            return true;
                    }
                    return false;
                }
            });
        }
    }

    void createOnTouchListeners(StartGraphicsScene scene) {
        switch (scene) {
            case Start: {
                break;
            }
            case Menu: {
                createContinueTouchListener();
                createNewGameTouchListener();
                createAboutTouchListener();
                createExitTouchListener();
                break;
            }
            case Upgrades: {
                createUndoTouchListener();
                createExpTouchListener();
                createUpgradeTouchListener();
                createBackTouchListener();
                createStartTouchListener();

                createUpgradesTouchListener();
                break;
            }
        }

    }

    protected void update() {
        currentScene.update();
    }


}
