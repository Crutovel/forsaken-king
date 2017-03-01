package com.crutovel.forsakenking.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.crutovel.forsakenking.Components.Scene;


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

public class GameViewSurface extends SurfaceView {

    private SurfaceHolder holder;
    private GameLoopThread loopThread;
    private Scene currentScene;


    public GameViewSurface(Context context) {
        super(context);

        currentScene = new Scene(null) {
            public void loadAssets() {
            }

            public void freeAssets() {
            }

            public void initComponents() {
            }

            public void draw(Canvas canvas) {
            }

            public void update() {
            }

            public void prepare() {
            }

            public void clear() {
            }

            public void hideSceneUi() {
            }

            public void showSceneUi() {
            }
        };

        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                loopThread.setRunning(true);
                loopThread.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }

    protected void Draw(Canvas canvas) {
        currentScene.draw(canvas);
    }

    public void setThread(GameLoopThread thread) {
        this.loopThread = thread;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
}


