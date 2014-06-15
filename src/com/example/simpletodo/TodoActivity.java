package com.example.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		
		lvItems = (ListView) findViewById(R.id.lvItems);
		items = new ArrayList<String>();
		readItems();
		
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
//		items.add("First Item");
//		items.add("Second Item");
		
		setupLisViewListener();

	}

	public void addTodoItem(View v) {
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}

 
	private void setupLisViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View item,
					int pos, long rowId) {
				Log.d("delete item-", "pos =" + pos + "size= "+ parent.getCount());
				Log.d(" items-size", " "+ items.size());
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
//				itemsAdapter.notifyDataSetInvalidated();
				saveItems();
				return true;
			}
		});
	}

	private void readItems()
	{
		Log.d("readItems", " "+ items.size());
	  File filesDir = getFilesDir();
	  File todoFile = new File(filesDir, "todo.text");
	  try {
	     items = new ArrayList<String>(FileUtils.readLines(todoFile));
	  } catch (IOException e) {
	     items = new ArrayList<String>();
	     e.printStackTrace();
	   }
	  
	  Log.d("readItems", " "+ items.size());
	}

	private void saveItems() {
		
		Log.d("saveItems", " "+ items.size());
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.text");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.d("saveItems", " "+ items.size());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

}
