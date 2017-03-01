package com.crutovel.forsakenking.Start;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

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

 class StartScene extends Scene {

     StartScene(RelativeLayout rLayout){
        super(rLayout);

        clear();
        initComponents();
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
    }

    public void initComponents(){}

    public void loadAssets(){}

    public void freeAssets(){}

    public void prepare(){
        loadAssets();
    }

    public void clear(){
        freeAssets();
    }

    public void hideSceneUi(){
        rLayout.setVisibility(View.GONE);
    }

    public void showSceneUi(){
        rLayout.setVisibility(View.VISIBLE);
    }
}
