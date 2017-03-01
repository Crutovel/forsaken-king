package com.crutovel.forsakenking.Start;

import android.graphics.Canvas;


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

class StartLoopThread extends Thread {

    private static final long ONE_SEC = 1000;
    private static final long FPS = 40;

    private StartLogic logic;
    private StartSurface mSurface;
    private boolean running = false;

    StartLoopThread(StartLogic logic) {
        this.logic = logic;
    }

    void setSurface(StartSurface surface) {
        mSurface = surface;
    }

    void setRunning(boolean running) {
        this.running = running;
    }

    public void run() {
        long ticks = ONE_SEC / FPS;
        long startTime;
        long sleepTime;
        Canvas c;
        while (running) {
            c = null;
            startTime = System.currentTimeMillis();
            logic.update();
            try {
                c = mSurface.getHolder().lockCanvas();
                synchronized (mSurface.getHolder()) {
                    mSurface.Draw(c);
                }
            } finally {
                if (c != null) {
                    mSurface.getHolder().unlockCanvasAndPost(c);
                }
            }
            try {
                sleepTime = ticks - (System.currentTimeMillis() - startTime);
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}
