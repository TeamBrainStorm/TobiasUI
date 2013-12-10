package com.sibext.android_shelf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.d4a.tobias.R;
import com.sibext.android_shelf.adapter.ShelfAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class Two extends Fragment{

	private static final String TARGET_DIRECTORY = "mnt/sdcard/shelf/";
	private ListView list;
	Context ctx;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	    }
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.two, container, false);
	        this.ctx = view.getContext();
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

			list = (ListView) view.findViewById(R.id.list);

			ShelfAdapter adapter = new ShelfAdapter(view.getContext(), TARGET_DIRECTORY);
			adapter.setToListView(list);
	        
	        return view;
	    }
	    
		
		public void addBooksFromAssetsToCard(){
			List<String> books;
			try {
				books = getBooksFromAsset(ctx);

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
			AssetManager assetManager = ctx.getAssets();
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
		
		/*public void onImportClicked(View v){
			Toast.makeText(v.getContext(), "Please wait...", Toast.LENGTH_LONG).show();
			Intent in = new Intent(v.getContext(), ImportBooks.class);
			startActivity(in);
		}*/
}
