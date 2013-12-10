package com.sibext.android_shelf;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.d4a.tobias.R;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ImportBooks extends Activity {

	int index =0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.import_books);

		populateTotal();
		try {
			copybooks();
		} catch (Exception e) {
		}
	}


	public void copybooks() throws Exception{
		String rootPath = Environment.getExternalStorageDirectory().toString();
		List<String> books = fetchToneFiles(getApplicationContext(), rootPath);
		String desPath = rootPath+File.separator+"shelf"+File.separator;

		for(String book : books){
			File file = new File(desPath+fetchBookName(book));
			if(!file.exists()){
				Log.d("[BOOK_Status]", "--Not Exist in shelf-- :"+book);

				File from = new File(book);
				File to = new File(desPath+fetchBookName(book));
				copyDirectoryOneLocationToAnotherLocation(from, to);
				index++;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						//
						((TextView) findViewById(R.id.total)).setText(String.valueOf(index));
					}
				}, 2 * 1000);	
			}
		}

		if(index == 0){
			Toast.makeText(getApplicationContext(), "No new book found...", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "All Books imported successfully...", Toast.LENGTH_LONG).show();
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				//Intent in = new Intent(getApplicationContext(), MainActivity_old.class);
				//startActivity(in);
				finish();
			}
		}, 7 * 1000);
	}

	public void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation)
			throws Exception {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < sourceLocation.listFiles().length; i++) {

				copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
						new File(targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);

			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}

	}

	public void populateTotal(){
		String rootPath = Environment.getExternalStorageDirectory().toString();
		List<String> _str = fetchToneFiles(getApplicationContext(), rootPath);
		int index = 0;
		for(String str : _str){
			//Toast.makeText(getApplicationContext(), fetchBookName(str), Toast.LENGTH_LONG).show();
			index++;
		}
		((TextView) findViewById(R.id.imported)).setText(String.valueOf(index));
	}

	public static List<String> fetchToneFiles(Context ctx, String rootPath) {
		final List<String> tFileList = new ArrayList<String>();
		String[] imageTypes = {"pdf"};
		FilenameFilter[] filter = new FilenameFilter[imageTypes.length];

		int i = 0;
		for (final String type : imageTypes) {
			filter[i] = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith("." + type);
				}
			};
			i++;
		}

		FileUtils fileUtils = new FileUtils();
		File[] allMatchingFiles = fileUtils.listFilesAsArray(
				new File(rootPath), filter, -1);
		for (File f : allMatchingFiles) {
			tFileList.add(f.getAbsolutePath());
		}
		return tFileList;
	}

	public String fetchBookName(String basepath){
		String bookname = null;
		if(basepath.length()>6){
			int index = basepath.lastIndexOf("/");
			return basepath.substring(index+1, basepath.length());

		}else{
			return bookname;
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

}
