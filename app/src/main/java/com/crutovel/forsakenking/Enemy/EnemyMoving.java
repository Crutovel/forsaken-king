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

class EnemyMoving implements EnemyState {

    private EnemyUnit unit;
    private int beginPointIndex;
    private int endPointIndex;
    private int moveStepsElapsed;
    private double posX;
    private double posY;
    private double iterationDistanceX;
    private double iterationDistanceY;
    private double iterationScaleSize;

    EnemyMoving(EnemyUnit unit) {
        this.unit = unit;
        beginPointIndex = -1;
        endPointIndex = 0;
        posX = unit.posX;
        posY = unit.posY;
        iterationDistanceX = 0.0;
        iterationDistanceY = 0.0;
        moveStepsElapsed = 0;
    }

    public void inputHandler() {
        switch (unit.input) {
            case LogicInput.ENEMY_JUBILATE: {
                unit.changeState(unit.jubilating, EnemyAnimation.Type.jubilating);
                break;
            }
            case LogicInput.ENEMY_PRE_ATTACK: {
                unit.changeState(unit.preattacking, EnemyAnimation.Type.preAttacking);
                break;
            }
            case LogicInput.ENEMY_FREEZE: {
                unit.changeState(unit.freezing, EnemyAnimation.Type.freezing);
                break;
            }
            case LogicInput.ENEMY_SOUL_KILLED: {
                unit.changeState(unit.dying, EnemyAnimation.Type.soulDying);
                break;
            }
        }
    }


    private void nextIteration() {
        moveStepsElapsed--;
        posX = posX + iterationDistanceX;
        unit.posX = (int) posX;
        posY = posY + iterationDistanceY;
        unit.posY = (int) posY;
        unit.scaleSize = unit.scaleSize + iterationScaleSize;
    }

    private PathPoint bPoint;
    private PathPoint ePoint;

    private void nextPathPoint() {
        beginPointIndex++;
        endPointIndex++;
        if (endPointIndex > unit.path.points.size() - 1) {
            unit.input = LogicInput.ENEMY_PRE_ATTACK;
        } else {
            bPoint = unit.path.points.get(beginPointIndex);
            ePoint = unit.path.points.get(endPointIndex);
            moveStepsElapsed = bPoint.moveSteps;
            iterationDistanceX = (ePoint.posX - bPoint.posX) / (double) bPoint.moveSteps;
            iterationDistanceY = (ePoint.posY - bPoint.posY) / (double) bPoint.moveSteps;
            iterationScaleSize = (ePoint.scaleSize - bPoint.scaleSize) / bPoint.moveSteps;
            unit.scaleSize = bPoint.scaleSize;
            posX = bPoint.posX;
            posY = bPoint.posY;
            unit.posX = (int) posX;
            unit.posY = (int) posY;
        }
    }


    public void update() {
        if (!unit.graphics.nextFrame()) {
            unit.graphics.resetCurrentAnimation();
        }
        if (moveStepsElapsed > 0) {
            nextIteration();
        } else {
            nextPathPoint();
        }
    }

    public void prepare() {
        unit.input = LogicInput.NOTHING_TO_DO;
    }
}
