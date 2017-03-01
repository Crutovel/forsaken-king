package com.crutovel.forsakenking.Player;

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

class PlayerIdling implements PlayerState {

    Player player;

    PlayerIdling(Player player) {
        this.player = player;
    }

    private void chooseAttackFlank() {
        switch (player.getAttackFlank()) {
            case front: {
                player.changeState(player.attacking, PlayerAnimation.Type.frontAttacking);
                break;
            }
            case left: {
                player.changeState(player.attacking, PlayerAnimation.Type.leftAttacking);
                break;
            }
            case right: {
                player.changeState(player.attacking, PlayerAnimation.Type.rightAttacking);
                break;
            }
        }
    }

    public void inputHandler() {
        switch (player.input) {
            case LogicInput.PLAYER_ATTACK: {
                player.state = player.attacking;
                chooseAttackFlank();
                break;
            }
            case LogicInput.PLAYER_SOULKILL: {
                player.changeState(player.soulKilling, PlayerAnimation.Type.soulKilling);
                break;
            }
            case LogicInput.PLAYER_FREEZE: {
                player.changeState(player.freezing, PlayerAnimation.Type.freezing);
                break;
            }
            case LogicInput.PLAYER_HEAL: {
                player.changeState(player.healing, PlayerAnimation.Type.healing);
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
            player.graphics.resetCurrentAnimation();
        }
    }

    public void prepare() {
        player.input = LogicInput.NOTHING_TO_DO;
    }
}
