package com.sibext.android_shelf;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.d4a.tobias.R;

public class Three extends Fragment  {

	Button drivebtn;
	Button adddrivebtn;
	Button fcbtn;
	Button addfcbtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  View v=inflater.inflate(R.layout.three, container, false);
		    drivebtn=(Button)v.findViewById(R.id.button2);
		    adddrivebtn=(Button)v.findViewById(R.id.button);
		    fcbtn=(Button)v.findViewById(R.id.button4);
		    addfcbtn=(Button)v.findViewById(R.id.button3);
	
	
		   
		
	
	return v;
	


	}

	

	

}
