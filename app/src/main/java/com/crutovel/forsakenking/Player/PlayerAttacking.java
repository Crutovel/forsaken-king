package com.crutovel.forsakenking.Player;

import com.crutovel.forsakenking.Enemy.EnemyPath;
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

class PlayerAttacking implements PlayerState {

    private Player player;
    private int attackSpriteIndex;


    PlayerAttacking(Player player, int attackSpriteIndex) {

        this.player = player;
        this.attackSpriteIndex = attackSpriteIndex;
    }

    public void inputHandler() {
        switch (player.input) {

            case LogicInput.PLAYER_IDLE: {
                player.changeState(player.idling, PlayerAnimation.Type.simpleIdling);
                player.setAttackFlank(EnemyPath.Type.none);
                break;
            }
            case LogicInput.PLAYER_DYING: {
                player.changeState(player.dying, PlayerAnimation.Type.dying);
                break;
            }
        }
    }

    public void update() {
        if (!player.graphics.nextFrame()) {
            player.input = LogicInput.PLAYER_IDLE;
        }

        if (player.graphics.currentSpriteIndex == attackSpriteIndex &&
                player.graphics.frameCount == 0) {
            player.attackPerformed = true;
        }
    }

    public void prepare() {
        player.input = LogicInput.NOTHING_TO_DO;
    }
}
