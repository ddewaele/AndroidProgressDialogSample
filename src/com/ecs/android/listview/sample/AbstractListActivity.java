package com.ecs.android.listview.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SimpleAdapter;

public class AbstractListActivity extends ListActivity {
	
	private static final int LONG_RUNNING_TIME = 2000;

	private static final String TAG =  MainActivity.class.getSimpleName();
	
	protected static final int LOADING_DIALOG = 0;
	protected List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
	}
	
	protected void refreshListLongRunning() {
		
		try {
			Thread.sleep(LONG_RUNNING_TIME);
		} catch (InterruptedException e) {
		}
		listItems = new ArrayList<Map<String, String>>();		
		for (int i=1 ; i<=10 ; i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			itemData.put("name", "Item " + i);
			listItems.add(itemData);
		}
		Log.i(TAG, "done refreshList");
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
