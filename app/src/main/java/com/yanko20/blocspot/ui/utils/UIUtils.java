package com.yanko20.blocspot.ui;

import android.support.v4.content.ContextCompat;

import com.yanko20.blocspot.BlocSpotApp;

import java.util.Random;

/**
 * Created by yanko on 3/24/2017.
 */

public class UIUtils {

    public static int generateRandomColor() {
        Random random = new Random();
        int baseColor = ContextCompat.getColor(
                BlocSpotApp.getAppContext(), android.R.color.white);
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        int baseRed = (baseColor & 0xFF0000) >> 16;
        int baseGreen = (baseColor & 0xFF00) >> 8;
        int baseBlue = baseColor & 0xFF;

        red = (red + baseRed) / 2;
        green = (green + baseGreen) / 2;
        blue = (blue + baseBlue) / 2;
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }
}
