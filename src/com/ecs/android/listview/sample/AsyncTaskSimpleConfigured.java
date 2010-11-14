package com.ecs.android.listview.sample;

import android.content.res.Configuration;

/**
 * For this Activity, we're just extending the basic asynctask activity.
 * 
 * By having a seperate Activity, we can change the manifest 
 * for this activity with the following activity property : 
 * 
 * android:configChanges="keyboardHidden|orientation"
 * 
 */
public class AsyncTaskSimpleConfigured extends AsyncTaskSimple {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  setContentView(R.layout.list);
	}

}
