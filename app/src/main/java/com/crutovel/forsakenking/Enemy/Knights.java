package com.crutovel.forsakenking.Enemy;

import com.crutovel.forsakenking.utils.Assets;
import com.crutovel.forsakenking.R;
import com.crutovel.forsakenking.utils.Measure;

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

public class Knights extends EnemyUnit {

    private static final int DAMAGE = 20;
    private static final int MAX_HP = 100;
    private static final int ATTACK_SPRITE_INDEX = 1;
    private static final double MAX_SIZE_ON_SCREEN = 6.0;

    public Knights(double power) {
        super();

        spriteSheetName = Assets.UNIT_KNIGHTS_SPRITE_SHEET;
        xmlResource = R.xml.knightsspritesheetnodpi;
        maxSizeOnScreen = Measure.getMeasure(MAX_SIZE_ON_SCREEN);

        waiting = new EnemyWaiting(this);
        moving = new EnemyMoving(this);
        attacking = new EnemyAttacking(this, ATTACK_SPRITE_INDEX);
        preattacking = new EnemyPreAttacking(this);
        dying = new EnemyDying(this);
        jubilating = new EnemyJubilating(this);
        freezing = new EnemyFreezing(this);
        unfreezing = new EnemyUnfreezing(this);

        damage = (int) Math.round(DAMAGE * power);
        maxHp = (int) Math.round(MAX_HP * power);
        currentHp = maxHp;
        state = waiting;

    }

    public void update() {
        super.update();
    }

}
