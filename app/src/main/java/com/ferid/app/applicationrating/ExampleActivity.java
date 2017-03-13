package com.ferid.app.applicationrating;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ferid.lib.applicationrating.ApplicationRating;

/**
 * Created by ferid.cafer on 3/13/2017.
 */

public class ExampleActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        ApplicationRating.initRatingPopupManager(this, 10, getString(R.string.rateApp),
                getString(R.string.yes), getString(R.string.no));
    }
}
