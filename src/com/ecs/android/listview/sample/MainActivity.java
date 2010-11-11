package com.ecs.android.listview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btnAllOnUI = (Button) findViewById(R.id.btn_launch_all_on_ui);
		Button btnAsyncSimple = (Button) findViewById(R.id.btn_async_task_simple);
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
