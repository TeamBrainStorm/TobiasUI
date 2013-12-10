package com.artifex.mupdfdemo;

import android.util.Log;

public class LinkInfoInternal extends LinkInfo {
	final public int pageNumber;

	public LinkInfoInternal(float l, float t, float r, float b, int p) {
		super(l, t, r, b);
		pageNumber = p;
		Log.d("MuPDF","LinkInfoInternal");
		Log.d("MuPDF",pageNumber+"");
	}

	public void acceptVisitor(LinkInfoVisitor visitor) {
		visitor.visitInternal(this);
	}
}
