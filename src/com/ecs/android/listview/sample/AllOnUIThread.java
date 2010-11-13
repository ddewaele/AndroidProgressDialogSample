package com.ecs.android.listview.sample;

import android.view.MenuItem;

/**
 * This activity attempts to show the dialog on the main UI thread.
 * It shows the dialog before the list (and its data) is refreshed, and dismisses it afterwards.
 * As you'll notice, the progress dialog never shows, as all of this is done on the UI thread.  
 *
 */
public class AllOnUIThread extends AbstractListActivity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.refresh_list:
		    	showDialog(LOADING_DIALOG);
		    	refreshListLongRunning();
		    	refreshListView();
		    	dismissDialog(LOADING_DIALOG);
		        return true;	
		    case R.id.clear_list:
		    	clearList();
		    	refreshListView();
		        return true;		        
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	    
	}	
}