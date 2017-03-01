package com.crutovel.forsakenking.Enemy;


import com.crutovel.forsakenking.utils.Animation;

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

public class EnemyAnimation extends Animation {

    private Type name;
    private EnemyPath.Type path;


    public EnemyAnimation(int frameDelayed, Type name, EnemyPath.Type path) {
        super(frameDelayed);
        this.name = name;
        this.path = path;
    }

    public EnemyAnimation() {
        super();
        name = Type.none;
        path = EnemyPath.Type.none;
    }

    public EnemyAnimation.Type getName() {
        return name;
    }

    public EnemyPath.Type getPath() {
        return path;
    }

    public enum Type {
        none, attacking, preAttacking, moving, jubilating, freezing, unfreezing, simpleDying, soulDying
    }
}
