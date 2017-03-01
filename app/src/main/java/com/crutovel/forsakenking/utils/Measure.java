package com.crutovel.forsakenking.utils;

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

public abstract class Measure {

    protected static final int REST_HEIGHT = 1;
    protected static final int HALF_REST_HEIGHT = 2;

    public static int getMeasure(double dataConst) {
        return (int) Math.round(dataConst * ForsakenKingApp.getSectorDimen());
    }

    public static int getMeasure(double dataConst, int restHeight) {
        double restH = 0.0;
        if (REST_HEIGHT == restHeight) {
            restH = ForsakenKingApp.getRestHeight();
        }
        if (HALF_REST_HEIGHT == restHeight) {
            restH = ForsakenKingApp.getRestHeight() / 2;
        }
        return (int) Math.round(dataConst * ForsakenKingApp.getSectorDimen() + restH);
    }
}
