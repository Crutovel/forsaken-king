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

class EnemyAttacking implements EnemyState {

    private EnemyUnit unit;
    private int attackSpriteIndex;

    EnemyAttacking(EnemyUnit enemyunit, int attackSpriteIndex) {
        unit = enemyunit;
        this.attackSpriteIndex = attackSpriteIndex;
    }

    public void inputHandler() {
        switch (unit.input) {
            case LogicInput.ENEMY_SIMPLE_KILLED: {
                unit.changeState(unit.dying, EnemyAnimation.Type.simpleDying);
                break;
            }
            case LogicInput.ENEMY_SOUL_KILLED: {
                unit.changeState(unit.dying, EnemyAnimation.Type.soulDying);
                break;
            }
            case LogicInput.ENEMY_JUBILATE: {
                unit.changeState(unit.jubilating, EnemyAnimation.Type.jubilating);
                break;
            }
            case LogicInput.ENEMY_FREEZE: {
                unit.changeState(unit.freezing, EnemyAnimation.Type.freezing);
                break;
            }
        }
    }

    public void update() {
        if (unit.graphics.currentSpriteIndex == attackSpriteIndex &&
                unit.graphics.frameCount == 0) {
            unit.attackPerformed = true;
        }

        if (!unit.graphics.nextFrame()) {
            unit.graphics.resetCurrentAnimation();
        }
    }

    public void prepare() {
        unit.input = LogicInput.NOTHING_TO_DO;
    }
}
