package com.crutovel.forsakenking.Enemy;

import com.crutovel.forsakenking.utils.LogicInput;

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

abstract public class EnemyUnit {

    private boolean isDead;
    private double power;

    int posX;
    int posY;
    double scaleSize;
    int currentHp;
    int maxHp;
    int maxSizeOnScreen;
    EnemyAnimation.Type previousAnim;
    EnemyPath path;


    protected EnemyState previousState;
    protected EnemyWaiting waiting;
    protected EnemyMoving moving;
    protected EnemyDying dying;
    protected EnemyJubilating jubilating;
    protected EnemyFreezing freezing;
    protected EnemyUnfreezing unfreezing;
    public EnemyAttacking attacking;
    public EnemyPreAttacking preattacking;

    public EnemyState state;
    public EnemyGraphics graphics;
    public int input;
    public int xmlResource;
    public String spriteSheetName;
    public boolean attackPerformed;
    public int damage;

    public EnemyUnit() {
        spriteSheetName = "";
        isDead = false;
        attackPerformed = false;
        input = LogicInput.NOTHING_TO_DO;
        damage = 0;
        posX = 0;
        posY = 0;
        scaleSize = 1.0;
        power = 1.0;
        currentHp = 0;
        maxHp = 0;
        maxSizeOnScreen = 0;
    }

    public void inputHandler() {
        state.inputHandler();
    }

    public void update() {
        state.update();
    }

    public void setPath(EnemyPath path) {
        this.path = path;
    }

    public EnemyPath getPath() {
        return path;
    }

    public boolean isDeadCheck() {
        return isDead;
    }

    public void setGraphics(EnemyGraphics graphics) {
        this.graphics = graphics;
        graphics.attachUnit(this);
    }

    public boolean takeDamage(int damage) {
        currentHp -= damage;
        return currentHp > 0;
    }

    public void changeState(EnemyState state) {
        if(state != unfreezing) {
            previousState = this.state;
            previousAnim = ((EnemyAnimation) (this.graphics.getCurrentAnimation())).getName();
        }
        this.state = state;
        state.prepare();
    }

    public void changeState(EnemyState state, EnemyAnimation.Type anim) {
        changeState(state);
        graphics.setAnimation(anim);
    }

    void destroy() {
        isDead = true;
    }
}
