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

class PlayerDying implements PlayerState {

    Player player;

    PlayerDying(Player player) {
        this.player = player;
    }

    public void inputHandler() {
        switch (player.input) {
            case LogicInput.PLAYER_DEAD: {
                player.graphics.setAnimation(PlayerAnimation.Type.dead);
                player.setIsDead(true);
                player.input = LogicInput.NOTHING_TO_DO;
                break;
            }
        }
    }

    public void update() {
        if (player.isDead()) {
            if (!player.graphics.nextFrame()) {
                player.graphics.resetCurrentAnimation();
            }
        } else {
            if (!player.graphics.nextFrame()) {
                player.input = LogicInput.PLAYER_DEAD;
            }
        }
    }

    public void prepare() {
        player.input = LogicInput.NOTHING_TO_DO;
    }
}
