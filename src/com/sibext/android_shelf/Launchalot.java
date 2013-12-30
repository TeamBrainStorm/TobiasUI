package com.sibext.android_shelf;

import java.util.List;

import com.d4a.tobias.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;

public class Launchalot extends Activity {
	DrawerAdapter drawerAdapterObject;
	GridView drawerGrid;
	class Pac{
		Drawable icon;
		String name;
		String label;
	}
	Pac[] pacs;
	PackageManager pm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcherdrawer);
		drawerGrid = (GridView) findViewById(R.id.content);
		pm =getPackageManager();
		set_pacs();
		drawerAdapterObject = new DrawerAdapter(this, pacs);
		drawerGrid.setAdapter(drawerAdapterObject);
		drawerGrid.setOnItemClickListener(new DrawerClickListener(this, pacs, pm));
	}
	
	public void set_pacs(){
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> pacsList = pm.queryIntentActivities(mainIntent, 0);
		pacs = new Pac[pacsList.size()];
		for(int I=0;I<pacsList.size();I++){
			pacs[I]= new Pac();
			pacs[I].icon=pacsList.get(I).loadIcon(pm);
			pacs[I].name=pacsList.get(I).activityInfo.packageName;
			pacs[I].label=pacsList.get(I).loadLabel(pm).toString();
		}
		new SortApps().exchange_sort(pacs);
			
	}

}
