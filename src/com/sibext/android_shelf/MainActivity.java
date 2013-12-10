package com.sibext.android_shelf;



import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;
import com.d4a.tobias.R;
 
public class MainActivity extends FragmentActivity {
    private MyAdapter mAdapter;
    private ViewPager mPager;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
 
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
    }

 
    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
 
        public int getCount() {
            return 3;
        }
 
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
            case 0:
                return new One();
            case 1:
            	return new Two();
            case 2:
            	return new Three();
 
            default:
                return null;
            }
        }
    }
    
	public void onImportClicked(View v){
		Toast.makeText(v.getContext(), "Please wait...", Toast.LENGTH_LONG).show();
		Intent in = new Intent(v.getContext(), ImportBooks.class);
		startActivity(in);
	}
	

    /** Called when the user clicks the apps button */
    public void apps(View view) {
    	 Intent myIntent1 = new Intent(MainActivity.this, Launchalot.class);
    	 MainActivity.this.startActivity(myIntent1);
    }
    /** Called when the user clicks the web button */
    public void web(View view) {
    	String url = "http://www.google.com";
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	
    }	
 
    /** Called when the user clicks the tools button */
    public void tools(View view) {
    	Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName("com.d4a.stz","com.d4a.stz.ToolsHome"));
        intent.putExtra("grace", "Hi");
        startActivity(intent);
        
        
    }	
    
    /** Called when the user clicks the play button */
    public void play(View view) {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://play.google.com/store/apps"));
        startActivity(intent);
        
    }  
    
    /** Called when the user clicks the youtube button */
    public void youtube(View view) {
    	Intent videoClient = new Intent(Intent.ACTION_VIEW);
        videoClient.setClassName("com.google.android.youtube", "com.google.android.youtube.WatchActivity");
        startActivity(videoClient);

        
    }  
 
    
    /** Called when the user clicks the email button */
    public void email(View view) {
    	  Intent intent = new Intent(Intent.ACTION_MAIN);
          intent.setComponent(new ComponentName("com.d4a.eMailTime","com.fsck.k9.activity.Accounts"));
          intent.putExtra("grace", "Hi");
          startActivity(intent);
        
    }  
    
    
}

