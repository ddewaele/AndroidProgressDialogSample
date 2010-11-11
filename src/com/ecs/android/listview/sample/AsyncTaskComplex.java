package com.ecs.android.listview.sample;

import android.app.Dialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class AsyncTaskComplex extends AbstractListActivity {

	private static final String TAG =  AsyncTaskComplex.class.getSimpleName(); 
	private static final int LOADING_DIALOG = 0;
	private static final int CLEANING_DIALOG = 1;

    private ListRefresher mTask; 
    private boolean mShownDialog; 
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object retained = getLastNonConfigurationInstance(); 
        if ( retained instanceof ListRefresher ) { 
                Log.i(TAG, "Reclaiming previous background task."); 
                mTask = (ListRefresher) retained; 
                mTask.setActivity(this); 
        } else { 
                Log.i(TAG, "Creating new background task."); 
                mTask = new ListRefresher(this); 
                mTask.execute(); 
        }         
    }
	
    @Override 
    public Object onRetainNonConfigurationInstance() { 
            mTask.setActivity(null); 
            return mTask; 
    } 	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//removeDialog(LOADING_DIALOG);
		
	}
	
    private void onTaskCompleted() { 
        Log.i(TAG, "Activity " + this + " has been notified the task is complete."); 
        //Check added because dismissDialog throws an exception if the current 
        //activity hasn't shown it. This Happens if task finishes early enough 
        //before an orientation change that the dialog is already gone when 
        //the previous activity bundles up the dialogs to reshow. 
        refreshListView();
        if ( mShownDialog ) { 
                dismissDialog(LOADING_DIALOG); 
                Toast.makeText(this, "Finished..", Toast.LENGTH_LONG).show(); 
        } 
} 	
	
	private class ListRefresher extends AsyncTask<Uri, Void, Void> {

		 private AsyncTaskComplex activity; 
         private boolean completed; 
        
         private ListRefresher(AsyncTaskComplex activity) { 
                 this.activity = activity; 
         } 
         
		@Override
		protected void onPreExecute() {
			activity.showDialog(LOADING_DIALOG);
		}
		
		@Override
		protected Void doInBackground(Uri... params) {
			refreshListLongRunning();
			return null;
		}

        @Override 
        protected void onPostExecute(Void unused) { 
                completed = true; 
                refreshListView();
                notifyActivityTaskCompleted(); 
        } 
        private void notifyActivityTaskCompleted() { 
                if ( null != activity ) { 
                        activity.onTaskCompleted(); 
                } 
        } 
        
        private void setActivity(AsyncTaskComplex activity) { 
                this.activity = activity; 
                if ( completed ) { 
                        notifyActivityTaskCompleted(); 
                } 
        } 
	}
	
	private class ListClearer extends AsyncTask<Uri, Void, Void> {

		

		@Override
		protected void onPreExecute() {
			//super.onPreExecute();
			showDialog(CLEANING_DIALOG);
		}
		
		@Override
		protected Void doInBackground(Uri... params) {
			clearList();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			//super.onPostExecute(result);
			//STATIC_ACTIVITY_VARIABLE.dismissDialog(CLEANING_DIALOG);
			dismissDialog(CLEANING_DIALOG);
			refreshListView();
		}
	}	
	
    @Override 
    protected void onPrepareDialog(int id, Dialog dialog) { 
            super.onPrepareDialog(id, dialog); 
            if ( id == LOADING_DIALOG ) { 
                    mShownDialog = true; 
            } 
    } 	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.refresh_list:
		    	//showDialog(PROGRESS_DIALOG);
		    	//refreshList();
		    	//dismissDialog(PROGRESS_DIALOG);
		    	mTask = new ListRefresher(this); 
		    	mTask.execute();
		        return true;	
		    case R.id.clear_list:
		    	new ListClearer().execute();
		        return true;		        
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	    
	}

	
	
	
}