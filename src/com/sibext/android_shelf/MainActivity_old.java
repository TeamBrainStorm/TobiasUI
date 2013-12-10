package com.sibext.android_shelf;

import java.io.File;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.d4a.tobias.R;
import com.sibext.android_shelf.adapter.ShelfAdapter;

public class MainActivity_old extends Activity {

	private static final String TARGET_DIRECTORY = "mnt/sdcard/shelf/";

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent in = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(in);
		
		/*
		File dir = new File(TARGET_DIRECTORY);
		if(!dir.exists()){
			dir.mkdirs();
			//past here
			addBooksFromAssetsToCard();
		}else{
			String files[] = dir.list();
			if(files.length == 0){
				//past here
				addBooksFromAssetsToCard();
			}
		}

		list = (ListView)findViewById(R.id.list);

		ShelfAdapter adapter = new ShelfAdapter(this, TARGET_DIRECTORY);
		adapter.setToListView(list);
		*/
	}
	
	public void addBooksFromAssetsToCard(){
		List<String> books;
		try {
			books = getBooksFromAsset(getApplicationContext());

			for(String book : books){
				copyFromAssets(book);
			}

		} catch (Exception e) {
		}
	}

	public List<String> getBooksFromAsset(Context ctx) throws Exception
	{
		AssetManager assetManager =ctx.getAssets();
		String[] files = assetManager.list("books");
		List<String> it=Arrays.asList(files);
		return it;
	}

	public void copyFromAssets(String book)
	{
		AssetManager assetManager = getAssets();
		String[] files = null;
		InputStream in = null;
		OutputStream out = null;
		//String filename = "filename.ext";
		try
		{
			in = assetManager.open("books/"+book);
			out = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"/shelf/"+book);
			Log.d("Copying...", ""+book);
			copyFile(in, out); 
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		}
		catch(Exception e)
		{ 
			Log.e("tag", "Failed to copy asset file: " + book, e);
		}      
	}
	public void copyFile(InputStream in, OutputStream out) throws Exception
	{
		byte[] buffer = new byte[1024];
		int read;
		while((read = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, read);
		}
		Log.d("Copy_State", "Done...");
	}
	
	public void onImportClicked(View v){
		Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_LONG).show();
		Intent in = new Intent(getApplicationContext(), ImportBooks.class);
		startActivity(in);
	}
}
