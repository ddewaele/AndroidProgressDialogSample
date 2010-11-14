package com.ecs.android.listview.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SimpleAdapter;

/**
 * Abstract ListActivity class that implements some basic functionality for the samples.
 * It basically creates our list layout, and offers the following methods 
 * 
 * refreshListLongRunning (refreshes our list data)
 * refreshListView (refreshes our list control with the new data)
 * clearList (clears the list)
 * onCreateOptionsMenu (creates our menu - all activities in the sample have a refresh and clear list)
 * onCreateDialog (creates our dialog - LOADING_DIALOG)
 * 
 */
public abstract class AbstractListActivity extends ListActivity {
	
	protected static final int LOADING_DIALOG = 0;
	protected static final int LONG_RUNNING_TIME = 5000;

	private static final String TAG =  MainActivity.class.getSimpleName();
	
	protected List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
	}
	
	protected List<Map<String, String>> retrieveListLongRunning() {
		List<Map<String, String>> listItems;
		SystemClock.sleep(LONG_RUNNING_TIME);
		listItems = new ArrayList<Map<String, String>>();		
		for (int i=1 ; i<=10 ; i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			itemData.put("name", "Item " + i);
			listItems.add(itemData);
		}
		Log.i(TAG, "done refreshList");
		return listItems;
	}
	
	protected void refreshListView() {
		SimpleAdapter notes = new SimpleAdapter(getApplicationContext(),
	            this.listItems,
	            R.layout.row,
	            new String[] { "name"},
	            new int[] { R.id.row_name,} );
	
		setListAdapter(notes);		
	}
	
	protected void clearList() {
		listItems = new ArrayList<Map<String, String>>();		
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.list_menu, menu);
	    return true; 
	}
	
	protected Dialog onCreateDialog(int id) {
		if(id == LOADING_DIALOG){
			ProgressDialog loadingDialog = new ProgressDialog(this);
			loadingDialog.setMessage("Loading records ...");
			loadingDialog.setIndeterminate(true);
			loadingDialog.setCancelable(true);
			return loadingDialog;
		} 
		return super.onCreateDialog(id);
	}	
}
