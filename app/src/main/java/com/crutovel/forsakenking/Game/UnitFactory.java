package com.crutovel.forsakenking.Game;

import com.crutovel.forsakenking.Enemy.EnemyGraphics;
import com.crutovel.forsakenking.Enemy.EnemyUnit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

class UnitFactory {

    private static HashMap<Class, EnemyGraphics> enemyGraphics;

    UnitFactory() {
        enemyGraphics = new HashMap<>();
    }

    EnemyUnit createUnit(Class<?> unitClass, double power) {

        EnemyUnit unit;

        if (!(EnemyUnit.class.isAssignableFrom(unitClass) && EnemyUnit.class != unitClass)) {
            return null;
        }

        try {
            Constructor con = unitClass.getConstructor(double.class);
            unit = (EnemyUnit) con.newInstance(power);

            if (!enemyGraphics.containsKey(unitClass)) {
                enemyGraphics.put(unitClass, new EnemyGraphics(unit.spriteSheetName, unit.xmlResource));
            }
            unit.setGraphics(enemyGraphics.get(unitClass).cloneUnit());
            return unit;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
