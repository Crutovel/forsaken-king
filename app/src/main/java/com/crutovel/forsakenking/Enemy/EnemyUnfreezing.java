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

class EnemyUnfreezing implements EnemyState {

    private EnemyUnit unit;

    EnemyUnfreezing(EnemyUnit enemyunit) {
        unit = enemyunit;
    }

    public void inputHandler() {
        switch (unit.input) {
            case LogicInput.ENEMY_PREVIOUS_STATE: {
                unit.changeState(unit.previousState, unit.previousAnim);
                break;
            }
        }
    }

    public void update() {
        if (!unit.graphics.nextFrame()) {
            unit.input = LogicInput.ENEMY_PREVIOUS_STATE;
        }
    }

    public void prepare() {
        unit.input = LogicInput.NOTHING_TO_DO;
    }
}
