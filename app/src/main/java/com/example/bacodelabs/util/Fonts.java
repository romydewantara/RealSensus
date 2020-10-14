package com.example.bacodelabs.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by: kamikaze
 * on October, 12 2020
 * */

public class Fonts {

    private Context context;

    public Fonts(Context context) {
        this.context = context;
    }

    // get fonts collections
    // dehasta-momentos fonts
    public Typeface dmRegular() {
        Typeface dmRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/DM Regular.otf");

        return dmRegular;
    }

    public Typeface dmItalic() {
        Typeface dmItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/DM Italic.otf");

        return dmItalic;
    }

    // Smoolthan fonts
    public Typeface stBold() {
        Typeface stBold = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Bold.otf");
        return stBold;
    }

    public Typeface stMedium() {
        Typeface stMedium = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Medium.otf");
        return stMedium;
    }

    public Typeface stRegular() {
        Typeface stRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Regular.otf");
        return stRegular;
    }

    public Typeface stThin() {
        Typeface stThin = Typeface.createFromAsset(context.getAssets(),
                "fonts/Smoolthan Thin.otf");
        return stThin;
    }


}
