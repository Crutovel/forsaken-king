package com.crutovel.forsakenking.Player;

import com.crutovel.forsakenking.Enemy.EnemyPath;
import com.crutovel.forsakenking.utils.LogicInput;
import com.crutovel.forsakenking.utils.Measure;
import com.crutovel.forsakenking.utils.XmlParcerService;

import java.util.HashMap;

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

public class Player {

    private static final int HEAL_VALUE = 40;
    private static final int HEAL_MANA_COST = 25;
    private static final int SOUL_KILL_MANA_COST = 100;
    private static final int FREEZE_MANA_COST = 100;
    private static final int ATTACK_SPRITE_INDEX = 2;
    private static final int FREEZE_SPRITE_INDEX = 6;
    private static final int SOUL_KILL_SPRITE_INDEX = 5;
    private static final int HEAL_SPRITE_INDEX = 6;

    private Data d;
    private boolean isDead;
    private int hp;
    private int mana;
    private int damage;
    private int currentHp;
    private int currentMana;
    private EnemyPath.Type attackFlank;
    private int posX;
    private int posY;

    PlayerState state;
    PlayerAttacking attacking;
    PlayerIdling idling;
    PlayerSoulKilling soulKilling;
    PlayerHealing healing;
    protected PlayerDying dying;
    protected PlayerFreezing freezing;
    public int input;
    public boolean attackPerformed;
    public boolean soulKillPerformed;
    public boolean freezePerformed;
    public PlayerGraphics graphics;


    public Player() {
        d = new Data();
        attacking = new PlayerAttacking(this, ATTACK_SPRITE_INDEX);
        idling = new PlayerIdling(this);
        dying = new PlayerDying(this);
        freezing = new PlayerFreezing(this, FREEZE_SPRITE_INDEX);
        soulKilling = new PlayerSoulKilling(this, SOUL_KILL_SPRITE_INDEX);
        healing = new PlayerHealing(this, HEAL_SPRITE_INDEX);
        isDead = false;

        setCharatrsticsFromUpgrades();

        state = idling;
        attackPerformed = false;
        soulKillPerformed = false;
        freezePerformed = false;
        attackFlank = EnemyPath.Type.none;
        graphics = new PlayerGraphics();
        graphics.attachPlayer(this);

        posX = d.playerPosX;
        posY = d.playerPosY;
    }

    private void setCharatrsticsFromUpgrades() {
        HashMap<Upgrades, Integer> upgrades = XmlParcerService.getCurrentPlayerUpgrades();

        for (Player.Upgrades item : Player.Upgrades.values()) {
            switch (item) {
                case damageUpgrade: {
                    damage = upgrades.get(item);
                    break;
                }
                case hpUpgrade: {
                    hp = upgrades.get(item);
                    break;
                }
                case manaUpgrade: {
                    mana = upgrades.get(item);
                    break;
                }
            }
        }
    }

    public void update() {
        state.update();
    }

    public void inputHandler() {
        state.inputHandler();
    }

    public boolean isDead() {
        return isDead;
    }

    void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getHp() {
        return currentHp;
    }

    public int getMana() {
        return currentMana;
    }

    public int getMaxHp() {
        return hp;
    }

    public int getMaxMana() {
        return mana;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        if (input != LogicInput.PLAYER_DYING) {
            currentHp -= damage;
            if (currentHp <= 0) {
                currentHp = 0;
                input = LogicInput.PLAYER_DYING;
            }
        }
    }

    void soulKill() {
        currentMana -= SOUL_KILL_MANA_COST;
    }

    public boolean spellSoulKill() {
        return currentMana >= SOUL_KILL_MANA_COST && !isDead();
    }

    void heal() {
        currentMana -= HEAL_MANA_COST;
        currentHp += HEAL_VALUE;
        if (currentHp > hp) {
            currentHp = hp;
        }
    }

    public boolean spellHeal() {
        return currentMana >= HEAL_MANA_COST && !isDead();
    }

    void freeze() {
        currentMana -= FREEZE_MANA_COST;
    }

    public boolean spellFreeze() {
        return currentMana >= FREEZE_MANA_COST && !isDead();
    }

    public void changeState(PlayerState state, PlayerAnimation.Type animation) {
        this.state = state;
        state.prepare();
        graphics.setAnimation(animation);
    }

    public void reset() {
        currentHp = hp;
        currentMana = mana;
        attackPerformed = false;
        isDead = false;
        state = idling;
        graphics.setAnimation(PlayerAnimation.Type.simpleIdling);
    }

    public void setAttackFlank(EnemyPath.Type attackFlank) {
        this.attackFlank = attackFlank;
    }

    public EnemyPath.Type getAttackFlank() {
        return attackFlank;
    }

    int getPlayerSize() {
        return d.playerSize;
    }

    int getPlayerPosX() {
        return d.playerPosX;
    }

    int getPlayerPosY() {
        return d.playerPosY;
    }

    public int getLeftAttackSector() {
        return d.leftAttackSector;
    }

    public int getFrontAttackSector() {
        return d.frontAttackSector;
    }

    public int getRightAttackSector() {
        return d.rightAttackSector;
    }

    public enum Upgrades {
        hpUpgrade, damageUpgrade, manaUpgrade
    }

    private class Data extends Measure {

        private int playerSize;
        private int playerPosX;
        private int playerPosY;
        private int leftAttackSector;
        private int frontAttackSector;
        private int rightAttackSector;

        private Data() {
            playerSize = getMeasure(8.0);
            playerPosX = getMeasure(16.0);
            playerPosY = getMeasure(11.0, REST_HEIGHT);
            leftAttackSector = getMeasure(12.0);
            frontAttackSector = getMeasure(20.0);
            rightAttackSector = getMeasure(32.0);
        }
    }
}
