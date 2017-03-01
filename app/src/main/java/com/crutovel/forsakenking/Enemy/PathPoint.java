package com.crutovel.forsakenking.Enemy;

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

class PathPoint {

    int posX;
    int posY;
    double scaleSize;
    int moveSteps;

    PathPoint(int posX, int posY, double scaleSize, int moveSteps) {
        this.posX = posX;
        this.posY = posY;
        this.scaleSize = scaleSize;
        this.moveSteps = moveSteps;
    }
}