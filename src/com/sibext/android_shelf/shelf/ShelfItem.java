package com.sibext.android_shelf.shelf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.artifex.mupdfdemo.MuPDFCore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

public class ShelfItem {
    
    private File file;
    private String imgPath;
    
    public ShelfItem(File file){
        if(file == null){
            throw new NullPointerException("Impossible init ShelfItem by null file");
        }
        this.file = file;
        imgPath = file.getName().replace(".", "") + ".png";
        
        createPreviewDir();
    }
    
    private void createPreviewDir(){
        File dir = new File(getPreviewDir());
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
    
    public File getFile() {
        return file;
    }
    
    private String getPreviewPath(){
        return getPreviewDir() + imgPath;
    }
    
    private String getPreviewDir(){
        return file.getParent() + File.separator + "preview" + File.separator;
    }
    
    public void savePreview(Bitmap b){
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
    
            File f = new File(getPreviewPath());
            
            
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
    
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Bitmap getPreview(int rowHeight){
        MuPDFCore core = null;
        try {
            core = new MuPDFCore(getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(core == null){
            return null;
        }
        core.countPages();
        PointF size = core.getPageSize(0);
        int h = rowHeight;
        int w = (int)(h * (size.x/size.y));
        return core.drawPage(0, w, h, 0, 0, w, h);
    }
    
    public Bitmap getPreviewFromSD(){
        return BitmapFactory.decodeFile(getPreviewPath());
    }
    
}
