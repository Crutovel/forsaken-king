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

class EnemyJubilating implements EnemyState {

    private EnemyUnit unit;

    EnemyJubilating(EnemyUnit enemyUnit) {
        unit = enemyUnit;
    }

    public void inputHandler() {
        switch (unit.input) {
            case LogicInput.ENEMY_DELETE: {
                unit.destroy();
                break;
            }
        }
    }

    public void update() {
        if (!unit.graphics.nextFrame()) {
            unit.graphics.resetCurrentAnimation();
        }
    }

    public void prepare() {
        unit.input = LogicInput.NOTHING_TO_DO;
    }
}

