package com.crutovel.forsakenking.Start;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.crutovel.forsakenking.Components.MButton;
import com.crutovel.forsakenking.Components.RadioButton;
import com.crutovel.forsakenking.Components.UpgradeButton;
import com.crutovel.forsakenking.Game.GameViewActivity;
import com.crutovel.forsakenking.utils.DefSharedPrefManager;
import com.crutovel.forsakenking.utils.ForsakenKingApp;
import java.lang.reflect.InvocationTargetException;
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

public class StartActivity extends Activity implements View.OnTouchListener {

    private int visibility;
    public StartLogic logic;
    public StartLoopThread thread;
    public StartSurface surfaceView;
    public StartInput input;

    protected RelativeLayout parentLayout;
    protected RelativeLayout startSceneLayout;
    protected RelativeLayout menuSceneLayout;
    protected RelativeLayout upgradesSceneLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenWidth = 0;
        int screenHeight = 0;
        WindowManager w = getWindowManager();
        Display d = w.getDefaultDisplay();
        Point point = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            d.getRealSize(point);
            screenWidth = point.x;
            screenHeight = point.y;
        } else {

            try {
                screenWidth = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ForsakenKingApp.getScreenSize(screenWidth, screenHeight);

        logic = new StartLogic(this);
        thread = new StartLoopThread(logic);
        input = new StartInput(logic);

        createComponents();

        logic.createScene(StartGraphicsScene.Start, startSceneLayout);
        logic.createOnTouchListeners(StartGraphicsScene.Start);
        logic.setScene(StartGraphicsScene.Start);

        setContentView(parentLayout);

        StartingTask task = new StartingTask();
        task.execute();
    }

    public void startGameActivity() {
        Intent intent = new Intent(this, GameViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT > 16) {
            visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= 19) {
                visibility = visibility | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            getWindow().getDecorView().setSystemUiVisibility(visibility);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        input.inputHandler(event.getAction());
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

        startSceneLayout = new RelativeLayout(this);
        startSceneLayout.setVisibility(View.GONE);
        menuSceneLayout = new RelativeLayout(this);
        menuSceneLayout.setVisibility(View.GONE);
        upgradesSceneLayout = new RelativeLayout(this);
        upgradesSceneLayout.setVisibility(View.GONE);

        surfaceView = new StartSurface(this);
        ViewGroup.LayoutParams paramsSurface = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        surfaceView.setLayoutParams(paramsSurface);
        surfaceView.setOnTouchListener(this);

        parentLayout.addView(surfaceView);
        parentLayout.addView(startSceneLayout);
        parentLayout.addView(menuSceneLayout);
        parentLayout.addView(upgradesSceneLayout);

        MButton continueGame = new MButton(this);
        MButton newGame = new MButton(this);
        MButton scores = new MButton(this);
        MButton exit = new MButton(this);

        menuSceneLayout.addView(continueGame);
        menuSceneLayout.addView(newGame);
        menuSceneLayout.addView(scores);
        menuSceneLayout.addView(exit);

        MButton undo = new MButton(this);
        MButton exp = new MButton(this);
        MButton upgrade = new MButton(this);
        MButton back = new MButton(this);
        MButton start = new MButton(this);
        RadioButton upgradesRadioButton = new RadioButton(this);
        UpgradeButton damage = new UpgradeButton(this);
        UpgradeButton hp = new UpgradeButton(this);
        UpgradeButton mana = new UpgradeButton(this);
        upgradesRadioButton.addView(damage);
        upgradesRadioButton.addView(hp);
        upgradesRadioButton.addView(mana);

        upgradesSceneLayout.addView(undo);
        upgradesSceneLayout.addView(exp);
        upgradesSceneLayout.addView(upgrade);
        upgradesSceneLayout.addView(back);
        upgradesSceneLayout.addView(start);
        upgradesSceneLayout.addView(upgradesRadioButton);
    }

    @Override
    public void onPause() {
        super.onPause();
        clearResources();
    }

    @Override
    public void onResume() {
        super.onResume();
        thread = new StartLoopThread(logic);
        thread.setSurface(surfaceView);
        surfaceView.setThread(thread);
        if (logic.upgradesScene != null) logic.upgradesScene.setDefaultUpgrades();
        if (DefSharedPrefManager.getGameFinished() && logic.menuScene != null) {
            logic.setScene(StartGraphicsScene.Menu);
            DefSharedPrefManager.setGameFinished(false);
        }
    }

    class StartingTask extends AsyncTask<Void, Double, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(1);
                logic.createScene(StartGraphicsScene.Menu, menuSceneLayout);
                logic.createOnTouchListeners(StartGraphicsScene.Menu);
                publishProgress(50.0);
                logic.createScene(StartGraphicsScene.Upgrades, upgradesSceneLayout);
                logic.createOnTouchListeners(StartGraphicsScene.Upgrades);
                TimeUnit.SECONDS.sleep(1);
                publishProgress(100.0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            logic.setScene(StartGraphicsScene.Menu);
        }
    }
}
