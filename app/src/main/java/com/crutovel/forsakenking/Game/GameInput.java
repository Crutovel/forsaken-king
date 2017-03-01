package com.crutovel.forsakenking.Game;

import android.view.MotionEvent;

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

class GameInput {

    private GameGraphicsScene currentScene;
    private GameLogic logic;

    GameInput(GameLogic logic) {
        this.logic = logic;
    }

    void setCurrentScene(GameGraphicsScene currentScene) {
        this.currentScene = currentScene;
    }

    private void gameSceneAction(MotionEvent event) {
        switch (logic.gameScene.getLevelStatus()) {
            case Level.VICTORY: {
                logic.finishScene.setResult(true);
                logic.setScene(GameGraphicsScene.Finish);
                break;
            }
            case Level.GAME_OVER: {
                logic.finishScene.setResult(false);
                logic.setScene(GameGraphicsScene.Finish);
                break;
            }
            case Level.PLAYING: {
                logic.gameScene.playerPerformAttack(event);
                break;
            }
            case Level.PAUSED: {
                break;
            }
        }
    }

    public void inputHandler(int actionEvent, MotionEvent event) {
        switch (currentScene) {
            case Loading: {
                if (actionEvent == MotionEvent.ACTION_DOWN && logic.loadingScene.isReady()) {
                    logic.setScene(GameGraphicsScene.Game);
                }
                break;
            }
            case Game: {
                if (actionEvent == MotionEvent.ACTION_DOWN) {
                    gameSceneAction(event);
                }
                break;
            }
        }
    }
}
