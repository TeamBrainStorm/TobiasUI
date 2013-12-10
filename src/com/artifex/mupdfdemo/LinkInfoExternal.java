package com.artifex.mupdfdemo;

import android.util.Log;

public class LinkInfoExternal extends LinkInfo {
	final public String url;

	public LinkInfoExternal(float l, float t, float r, float b, String u) {
		super(l, t, r, b);
		url = u;
		Log.d("MuPDF","LinkInfoExternal");
		Log.d("MuPDF",url);
		
	}

	public void acceptVisitor(LinkInfoVisitor visitor) {
		visitor.visitExternal(this);
	}
}
