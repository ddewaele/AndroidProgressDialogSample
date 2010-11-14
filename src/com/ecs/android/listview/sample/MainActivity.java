package com.ecs.android.listview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Puts 4 buttons on the screen to demonstrate the different behavior of the progress dialog.
 * For each button, an OnClickListener is defined that starts the appropriate activity. 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btnAllOnUI = (Button) findViewById(R.id.btn_launch_all_on_ui);
		Button btnAsyncSimple = (Button) findViewById(R.id.btn_async_task_simple);
		Button btnAsyncSimpleStateSaving = (Button) findViewById(R.id.btn_async_task_simple_state);
		Button btnAsyncSimpleConfigured = (Button) findViewById(R.id.btn_async_task_simple_configured);
		Button btnAsyncComplex = (Button) findViewById(R.id.btn_async_task_complex);
		btnAllOnUI.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent().setClass(v.getContext(),
						AllOnUIThread.class));
			}
		});

		btnAsyncSimple.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent().setClass(v.getContext(),
						AsyncTaskSimple.class));
			}
		});
		
		btnAsyncSimpleStateSaving.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent().setClass(v.getContext(),
						AsyncTaskSimpleStateSaving.class));
			}
		});
		
		
		
		btnAsyncSimpleConfigured.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent().setClass(v.getContext(),
						AsyncTaskSimpleConfigured.class));
			}
		});		

		btnAsyncComplex.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent().setClass(v.getContext(),
						AsyncTaskComplex.class));
			}
		});
	
	}
}
