package com.crutovel.forsakenking.Components;

import android.content.Context;
import android.widget.RelativeLayout;

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

public class RadioButton extends RelativeLayout {

    public UpgradeButton selectedButton;

    public RadioButton(Context context) {
        super(context);
    }

    public void select(UpgradeButton button) {
        selectedButton = button;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setSelected(getChildAt(i) == selectedButton);
            getChildAt(i).invalidate();
        }
    }

    public UpgradeButton getSelectedUpgradeButton() {
        return selectedButton;
    }

}
