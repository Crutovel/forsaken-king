package com.crutovel.forsakenking.Game;

import android.content.Context;

import com.crutovel.forsakenking.Enemy.EnemyPath;
import com.crutovel.forsakenking.Enemy.EnemyUnit;
import com.crutovel.forsakenking.utils.LevelConfig;
import com.crutovel.forsakenking.utils.LogicInput;
import com.crutovel.forsakenking.Player.Player;
import com.crutovel.forsakenking.utils.DefSharedPrefManager;
import com.crutovel.forsakenking.utils.FrameManager;
import com.crutovel.forsakenking.utils.ForsakenKingApp;
import com.crutovel.forsakenking.utils.XmlParcerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

class Level {
    private static final int LOADING = 3;
    static final int VICTORY = 1;
    static final int GAME_OVER = 2;
    static final int PAUSED = 4;
    static final int PLAYING = 5;
    public static final int READY_TO_START_STATUS = 6;

    private Context context;
    private List<EnemyUnit> waitingEnemyUnits;
    private UnitFactory factory;
    private HashMap<EnemyPath.Type, EnemyPath> paths;
    private int pathSpawn = 0;


    LevelConfig conf;
    int status;
    List<EnemyUnit> activeEnemyUnits;
    protected Player player;
    public int frameCount = 0;

    Level() {
        status = Level.LOADING;
        context = ForsakenKingApp.getContext();
        loadLevel();
        initLevel();
    }

    void playerAttacks(EnemyPath.Type flank) {
        player.setAttackFlank(flank);
        playerAction(LogicInput.PLAYER_ATTACK);
    }

    private void createUnits() {
        factory = new UnitFactory();

        waitingEnemyUnits = new ArrayList<>();
        Class unitClass;
        int n;
        double power;
        String str;
        try {
            for (String key : conf.units.keySet()) {
                unitClass = Class.forName(this.context.getPackageName() + ".Enemy." + key);
                str = conf.units.get(key);
                n = Integer.parseInt(str.substring(0, str.indexOf("_")));
                power = Double.parseDouble(str.substring(str.indexOf("_") + 1));

                for (int i = 0; i < n; i++) {
                    waitingEnemyUnits.add(factory.createUnit(unitClass, power));
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        activeEnemyUnits = new CopyOnWriteArrayList<>();
    }

    private void createPlayer() {
        player = new Player();
    }

    private void createPaths() {
        paths = new HashMap<>();
        EnemyPath path = new EnemyPath();
        paths.put(EnemyPath.Type.front, path.createEnemyPath(EnemyPath.Type.front));
        paths.put(EnemyPath.Type.left, path.createEnemyPath(EnemyPath.Type.left));
        paths.put(EnemyPath.Type.right, path.createEnemyPath(EnemyPath.Type.right));
    }

    private void loadLevel() {
        conf = XmlParcerService.getLevelConfig(DefSharedPrefManager.getNextGameLevel());
    }

    private void initLevel() {
        createPaths();
        createPlayer();
        createPaths();
    }

    private void spawnUnits() {
        if (waitingEnemyUnits.size() != 0) {

            if (frameCount % FrameManager.ENEMY_SPAWN_TIME == 0) {
                EnemyUnit unit = waitingEnemyUnits.get(waitingEnemyUnits.size() - 1);
                if (pathSpawn >= EnemyPath.Type.getSize() - 1) pathSpawn = 0;
                unit.setPath(paths.get(EnemyPath.Type.get(pathSpawn++)));
                unit.input = LogicInput.ENEMY_MOVE;
                unit.inputHandler();
                activeEnemyUnits.add(unit);
                waitingEnemyUnits.remove(waitingEnemyUnits.size() - 1);
                frameCount = 0;
            }
        }
    }

    void reset() {
        frameCount = 0;
        pathSpawn = 0;
        createUnits();
        player.reset();
        status = Level.PLAYING;
    }

    public void input() {
        switch (status) {
            case Level.PLAYING: {
                for (EnemyUnit unit : activeEnemyUnits) {
                    unit.inputHandler();
                }

                player.inputHandler();
                break;
            }
            case Level.VICTORY: {
                player.inputHandler();
                break;
            }
            case Level.GAME_OVER: {
                for (EnemyUnit unit : activeEnemyUnits) {
                    unit.inputHandler();
                }

                player.inputHandler();
                break;
            }
        }
    }

    private void playerAction(int input) {
        player.input = input;
    }

    void playerHeal() {
        if (player.spellHeal()) {
            player.input = LogicInput.PLAYER_HEAL;
        }
    }

    void playerSoulKill() {
        if (player.spellSoulKill()) {
            player.input = LogicInput.PLAYER_SOULKILL;
        }
    }

    void playerFreeze() {
        if (player.spellFreeze()) {
            player.input = LogicInput.PLAYER_FREEZE;
        }
    }

    private void unitsAction() {
        for (EnemyUnit unit : activeEnemyUnits) {
            if (unit.isDeadCheck()) {
                activeEnemyUnits.remove(unit);
            } else {
                unit.inputHandler();
                unit.update();
                if (unit.attackPerformed) {
                    unit.attackPerformed = false;
                    player.takeDamage(unit.damage);
                }
            }
        }
    }

    private boolean isVictory() {
        return waitingEnemyUnits.size() == 0 && activeEnemyUnits.size() == 0;
    }

    private boolean isGameOver() {
        return player.isDead();
    }

    private void checkLevelStatus() {
        if (isVictory()) {
            status = VICTORY;
        }
        if (isGameOver()) {
            status = GAME_OVER;
            for (EnemyUnit unit : activeEnemyUnits) {
                unit.input = LogicInput.ENEMY_JUBILATE;
            }
        }
    }

    private void checkPlayerAttackPerformed() {
        if (player.attackPerformed) {
            for (EnemyUnit unit : activeEnemyUnits) {
                if (paths.get(player.getAttackFlank()) == unit.getPath() &&
                        (unit.state == unit.preattacking || unit.state == unit.attacking)) {
                    if (!unit.takeDamage(player.getDamage()))
                        unit.input = LogicInput.ENEMY_SIMPLE_KILLED;
                }
            }
            player.attackPerformed = false;
        }
    }

    private void checkPlayerFreezePerformed() {
        if (player.freezePerformed) {
            for (EnemyUnit unit : activeEnemyUnits) {
                unit.input = LogicInput.ENEMY_FREEZE;
            }
            player.freezePerformed = false;
        }
    }

    private void checkPlayerSoulKillPerformed() {
        if (player.soulKillPerformed) {
            for (EnemyUnit unit : activeEnemyUnits) {
                unit.input = LogicInput.ENEMY_SOUL_KILLED;
            }
            player.soulKillPerformed = false;
        }

    }

    public void update() {
        switch (status) {
            case Level.PLAYING: {
                checkLevelStatus();
                spawnUnits();
                unitsAction();

                checkPlayerAttackPerformed();
                checkPlayerFreezePerformed();
                checkPlayerSoulKillPerformed();
                player.update();
                frameCount++;

                break;
            }
            case Level.GAME_OVER: {
                for (EnemyUnit unit : activeEnemyUnits) {
                    if (unit.isDeadCheck()) {
                        activeEnemyUnits.remove(unit);
                    } else {
                        unit.update();
                    }
                }
                player.update();
                frameCount++;
                break;
            }
            case Level.VICTORY: {
                player.update();
                frameCount++;
                break;
            }
        }
    }
}
