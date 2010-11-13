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

    private ListRefresher mTask; 
    private boolean mShownDialog; 
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object retained = getLastNonConfigurationInstance(); 
        // After a screen orientation change, we associate the current ( = newly created) activity
        // with the restored asyncTask.
        if ( retained instanceof ListRefresher ) { 
                Log.i(TAG, "Reclaiming previous background task."); 
                mTask = (ListRefresher) retained; 
                mTask.setActivity(this); 
        // On a clean activity startup, we create a new task and associate the current activity.
        } else { 
                Log.i(TAG, "Creating new background task."); 
                mTask = new ListRefresher(this); 
                mTask.execute(); 
        }         
    }
	
	/**
	 * After a screen orientation change, this method is invoked.
	 * We disassociate the activity from the task.
	 */
    @Override 
    public Object onRetainNonConfigurationInstance() { 
            mTask.setActivity(null); 
            return mTask; 
    } 	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * When the aSyncTask has notified the activity that it has completed,
	 * we can refresh the list control, and attempt to dismiss the dialog.
	 * We'll only dismiss the dialog 
	 */
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
	
    /**
     * Our complex async task that holds a reference to the Activity that started it,
     * and a boolean to determine if the task completed.
     * @author Davy
     *
     */
	private class ListRefresher extends AsyncTask<Uri, Void, Void> {

		 private AsyncTaskComplex activity; 
         private boolean completed; 
        
         private ListRefresher(AsyncTaskComplex activity) { 
                 this.activity = activity; 
         } 
         
        /**
         * Showing the dialog on the UI thread.
         */
		@Override
		protected void onPreExecute() {
			activity.showDialog(LOADING_DIALOG);
		}
		
        /**
         * Performing the heavy lifting in the background thread thread.
         */
		@Override
		protected Void doInBackground(Uri... params) {
			refreshListLongRunning();
			return null;
		}

		/**
		 * When the task is completed, notifiy the Activity.
		 */
        @Override 
        protected void onPostExecute(Void unused) { 
                completed = true; 
                notifyActivityTaskCompleted(); 
        } 
        
        /**
         * Helper method to notify the activity that this task was completed.
         */
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
	
	/**
	 * Here, we're maintaining the mShownDialog flag in the activity so that it knows that the 
	 * progress dialog has been shown. The flag is required when dismissing the dialog, as the only 
	 * activity that is allowed to dismiss the dialog is the activity that has also created it.
	 */
    @Override 
    protected void onPrepareDialog(int id, Dialog dialog) { 
            super.onPrepareDialog(id, dialog); 
            if ( id == LOADING_DIALOG ) { 
                    mShownDialog = true; 
            } 
    } 	

    /**
     * Here, we're executing the async task by passing on our activity.
     * Inside the async task, a reference to this activity is kept for proper dialog progress handling.
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.refresh_list:
		    	mTask = new ListRefresher(this); 
		    	mTask.execute();
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