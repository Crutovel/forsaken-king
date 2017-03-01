package com.crutovel.forsakenking.Game;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.MButton;

import java.util.concurrent.TimeUnit;

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

public class GameViewActivity extends Activity implements View.OnTouchListener {

    private int visibility;

    protected RelativeLayout parentLayout;
    protected RelativeLayout loadingSceneLayout;
    protected RelativeLayout gameSceneLayout;
    protected RelativeLayout finishSceneLayout;

    public GameLogic logic;
    public GameLoopThread thread;
    public GameViewSurface surfaceView;
    public GameInput input;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visibility = 0;
        logic = new GameLogic(this);
        thread = new GameLoopThread(logic);
        input = new GameInput(logic);

        createComponents();

        logic.createScene(GameGraphicsScene.Loading, loadingSceneLayout);
        logic.createOnTouchListeners(GameGraphicsScene.Loading);
        logic.setScene(GameGraphicsScene.Loading);

        setContentView(parentLayout);

        LoadingTask task = new LoadingTask();
        task.execute();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT > 16) {
            visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT > 19) {
                visibility = visibility | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            getWindow().getDecorView().setSystemUiVisibility(visibility);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        input.inputHandler(event.getAction(), event);
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void clearResources() {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
        thread.interrupt();
        thread = null;
    }

    private void createComponents() {
        parentLayout = new RelativeLayout(this);

        loadingSceneLayout = new RelativeLayout(this);
        loadingSceneLayout.setVisibility(View.GONE);
        gameSceneLayout = new RelativeLayout(this);
        gameSceneLayout.setVisibility(View.GONE);
        finishSceneLayout = new RelativeLayout(this);
        finishSceneLayout.setVisibility(View.GONE);

        surfaceView = new GameViewSurface(this);
        ViewGroup.LayoutParams paramsSurface = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        surfaceView.setLayoutParams(paramsSurface);
        surfaceView.setOnTouchListener(this);

        parentLayout.addView(surfaceView);
        parentLayout.addView(loadingSceneLayout);
        parentLayout.addView(gameSceneLayout);
        parentLayout.addView(finishSceneLayout);

        MButton healSpell = new MButton(this);
        MButton freezeSpell = new MButton(this);
        MButton soulKillSpell = new MButton(this);

        gameSceneLayout.addView(healSpell);
        gameSceneLayout.addView(freezeSpell);
        gameSceneLayout.addView(soulKillSpell);

        MButton restart = new MButton(this);
        MButton back = new MButton(this);

        finishSceneLayout.addView(restart);
        finishSceneLayout.addView(back);

    }

    @Override
    public void onPause() {
        super.onPause();
        clearResources();
    }

    @Override
    public void onResume() {
        super.onResume();
        thread = new GameLoopThread(logic);
        thread.setSurface(surfaceView);
        surfaceView.setThread(thread);
    }

    class LoadingTask extends AsyncTask<Void, Double, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            gentleProgress(0, 20);
            logic.createScene(GameGraphicsScene.Game, gameSceneLayout);
            gentleProgress(20, 40);
            logic.createOnTouchListeners(GameGraphicsScene.Game);
            gentleProgress(40, 60);
            logic.createScene(GameGraphicsScene.Finish, finishSceneLayout);
            gentleProgress(60, 80);
            logic.createOnTouchListeners(GameGraphicsScene.Finish);
            gentleProgress(80, 100);

            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            super.onProgressUpdate(values);
            logic.loadingScene.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            logic.loadingScene.setReady();
        }

        void gentleProgress(double begin, double end) {
            try {
                for (double progress = begin; progress <= end; progress += 0.5) {
                    TimeUnit.MILLISECONDS.sleep(5);
                    publishProgress(progress);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

