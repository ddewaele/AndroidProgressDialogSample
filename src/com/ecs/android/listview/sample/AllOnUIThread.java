package com.ecs.android.listview.sample;

import android.view.MenuItem;

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