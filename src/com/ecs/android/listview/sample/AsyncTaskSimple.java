package com.ecs.android.listview.sample;

import android.net.Uri;
import android.os.AsyncTask;
import android.view.MenuItem;

public class AsyncTaskSimple extends AbstractListActivity {

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.refresh_list:
		    	new ListRefresher().execute(); 
		        return true;	
		    case R.id.clear_list:
		    	clearList();
		    	refreshListView();
		        return true;		        
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	    
	}
	
	private class ListRefresher extends AsyncTask<Uri, Void, Void> {

		@Override
		protected void onPreExecute() {
			showDialog(LOADING_DIALOG);
		}
		
		@Override
		protected Void doInBackground(Uri... params) {
			refreshListLongRunning();
			return null;
		}

       @Override 
       protected void onPostExecute(Void unused) { 
    	   dismissDialog(LOADING_DIALOG);
    	   refreshListView();
       } 
	}
	
}
