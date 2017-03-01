package com.crutovel.forsakenking.Enemy;

import com.crutovel.forsakenking.utils.Measure;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class EnemyPath {
    private Data d;

    List<PathPoint> points;
    public Type name;

    public EnemyPath() {
        d = new Data();
        name = Type.none;
        points = new ArrayList<>();
    }
    private EnemyPath(Type name) {
        this.name = name;
    }

    private void addPath(List<PathPoint> points) {
        this.points = points;
    }

    public EnemyPath createEnemyPath(Type name){
        EnemyPath path = new EnemyPath(name);
        switch (name){
            case left:{
                path.addPath(d.path2);
                break;
            }
            case front:{
                path.addPath(d.path1);
                break;
            }
            case right:{
                path.addPath(d.path3);
                break;
            }
        }
        return path;
    }

    public enum Type {
        none(-1), front(0), left(1), right(2);

        private static final Map<Integer, Type> lookup = new HashMap();

        static {
            for (Type w : EnumSet.allOf(Type.class))
                lookup.put(w.getCode(), w);
        }

        private int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static int getSize() {
            return lookup.size();
        }

        public static Type get(int code) {
            return lookup.get(code);
        }
    }

    private class Data extends Measure {
        private  List<PathPoint> path1;
        private  List<PathPoint> path2;
        private  List<PathPoint> path3;

        private Data(){
            path1 = new ArrayList<>();
            path1.add(new PathPoint(getMeasure(16.0), getMeasure(5.0, REST_HEIGHT), 0.5, 80));
            path1.add(new PathPoint(getMeasure(16.0), getMeasure(8.0, REST_HEIGHT), 0.75, 40));
            path1.add(new PathPoint(getMeasure(16.0), getMeasure(9.5, REST_HEIGHT), 1.0, 0));

            path2 = new ArrayList<>();
            path2.add(new PathPoint(getMeasure(16.0), getMeasure(5.0, REST_HEIGHT), 0.5, 80));
            path2.add(new PathPoint(getMeasure(10.0), getMeasure(9.0, REST_HEIGHT), 0.75, 40));
            path2.add(new PathPoint(getMeasure(10.0), getMeasure(10.0, REST_HEIGHT), 1.0, 0));

            path3 = new ArrayList<>();
            path3.add(new PathPoint(getMeasure(16.0), getMeasure(5.0, REST_HEIGHT), 0.5, 80));
            path3.add(new PathPoint(getMeasure(22.0), getMeasure(9.0, REST_HEIGHT), 0.75, 40));
            path3.add(new PathPoint(getMeasure(22.0), getMeasure(10.0, REST_HEIGHT), 1.0, 0));

        }
    }
}
