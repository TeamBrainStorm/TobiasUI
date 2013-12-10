package com.sibext.android_shelf.adapter;

import java.io.File;



import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;
import com.d4a.tobias.R;
import com.sibext.android_shelf.MainActivity_old;
import com.sibext.android_shelf.shelf.ShelfItem;

public class ShelfAdapter extends BaseAdapter {
    
    private static final int ROW_COUNT_DEFAULT = 2;
    private static final int ROW_COUNT_LAND_DEFAULT = 4;
    private static final int ROW_HEIGHT_DEFAULT = 150;
    
    private Context context;
    
    private int rowCount = ROW_COUNT_DEFAULT;
    private int rowCountLand = ROW_COUNT_LAND_DEFAULT;
    private int rowHeight = ROW_HEIGHT_DEFAULT;
    
    private SparseArray<LoadPreviewTask> taskPool;

    private ArrayList<ShelfItem> items;
    String PATH;
    ShelfItem item;
    
    
    public ShelfAdapter(Context context, String targetDir) {
        this.context = context;
        
        items = new ArrayList<ShelfItem>();
        taskPool = new SparseArray<ShelfAdapter.LoadPreviewTask>();
        
        if (targetDir == null || context == null) {
            throw new NullPointerException("ShelfAdapter: wrong paramenters - " +
                    (targetDir == null ? "Target directory " : "Context ") + "is null");
        }
        
        File dir = new File(targetDir);
        if(dir.exists() && dir.isDirectory()){
            for(File f : dir.listFiles()){
                if(f != null && f.getName().endsWith(".pdf")){
                    items.add(new ShelfItem(f));
                }
            }
        }
    }
    
    public void setToListView(ListView list){
        list.setDividerHeight(0);
        list.setAdapter(this);
    }
    
    public void setRowCount(int rowCount, int rowCountLand) {
        this.rowCount = rowCount;
        this.rowCountLand = rowCountLand;
    }
    
    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    @Override
    public int getCount() {
        int sub = getSubItemsCount(); 
        return (int)(sub/getRowCount()) + (sub%getRowCount() == 0 ? 0 : 1);
    }
    
    public int getSubItemsCount(){
        return items.size();
    }

    @Override
    public ShelfItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.shelf_row, null);
            AbsListView.LayoutParams itemParams = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, rowHeight);
            convertView.setLayoutParams(itemParams);
            
            LinearLayout.LayoutParams subItemParams = getSubViewParams();
            for(int i = 0; i < getRowCount(); i++){
                View sub = getSubView(getSubPosition(position, i), null);
                sub.setLayoutParams(subItemParams);
                ((ViewGroup)convertView).addView(sub);
                holder.subViews.add(sub);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
            for(int i = 0; i < holder.subViews.size(); i++){
                getSubView(getSubPosition(position, i), holder.subViews.get(i));
            }
        }
        return convertView;
    }
    
    
    public View getSubView(final int position, View convertView){
        final SubViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.shelf_item, null);
            holder = new SubViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.shelf_item_image);
            convertView.setTag(holder);
        } else {
            holder = (SubViewHolder)convertView.getTag();
        }
        
        if(position >= getSubItemsCount()){
            holder.img.setImageBitmap(null);
            return convertView;
        }
        
        item = getItem(position);
        PATH = item.getFile().getAbsolutePath();
        Bitmap preview = item.getPreviewFromSD();
        if(preview != null){
            holder.img.setImageBitmap(preview);
        } else {
            holder.img.setImageResource(R.drawable.ic_launcher);
            LoadPreviewTask task = taskPool.get(holder.hashCode());
            if (task != null) {
                task.cancel(true);
                taskPool.remove(holder.hashCode());
                task = null;
            }
            task = new LoadPreviewTask(holder, rowHeight);
            taskPool.put(holder.hashCode(), task);
            task.execute(item);
        }

        holder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	final ShelfItem si = items.get(position);
            	/*Toast.makeText(context, "Item #" + position+"\n"+si.getFile().getAbsolutePath(), Toast.LENGTH_SHORT).show();
                */
            	
        		final CharSequence[] items = {"View", "Delete"};
        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        		builder.setTitle("Please choose:");
        		builder.setItems(items, new DialogInterface.OnClickListener() {
        			//Toast.makeText(getApplicationContext(), ""+items[0], Toast.LENGTH_SHORT).show();
        			public void onClick(DialogInterface dialog, int index) {
        				if(index == 0){
        	                Intent intent = new Intent(Intent.ACTION_VIEW);
        	                intent.setDataAndType(Uri.fromFile(si.getFile()),"application/pdf");
        	                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        	                context.startActivity(intent);
        				}else if(index == 1){
        							 File file = new File(si.getFile().getAbsoluteFile().toString());
        							 boolean deleteStatus = file.delete();
        							 if(deleteStatus){
        								 Toast.makeText(context, "Deleted Successfully...", Toast.LENGTH_SHORT).show(); 
        								 Intent in = new Intent(context, MainActivity_old.class);
        								 context.startActivity(in);
        							 
        							 }else{
        								 Toast.makeText(context, "Unable to delete...", Toast.LENGTH_SHORT).show();
        							 }
        				}
        			}
        		});
        		AlertDialog alert = builder.create();
        		alert.show();
                
            }
        });
        return convertView;
    }
    
    private int getSubPosition(int listItemPosition, int offset){
        return listItemPosition * getRowCount() + offset;
    }
    
    private LinearLayout.LayoutParams getSubViewParams(){
        LinearLayout.LayoutParams p = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        p.weight = 1f;
        return p;
    }
    
    private int getRowCount() {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT 
                ? rowCount : rowCountLand;
    }

    private static class ViewHolder{
        public ArrayList<View> subViews;

        public ViewHolder() {
            this.subViews = new ArrayList<View>();
        }
    }
    
    private static class SubViewHolder{
        public ImageView img;
    }
    
    private class LoadPreviewTask extends AsyncTask<ShelfItem, Void, Bitmap>{
        private SubViewHolder holder;
        private int rowHeight;
        
        public LoadPreviewTask(SubViewHolder holder, int rowHeight) {
            super();
            this.holder = holder;
            this.rowHeight = rowHeight;
        }

        @Override
        protected Bitmap doInBackground(ShelfItem... params) {
            ShelfItem item = (ShelfItem)params[0];
            Bitmap preview = item.getPreview(rowHeight);
            item.savePreview(preview);
            return preview;
        }

        @Override
        protected void onPostExecute(final Bitmap result) {
            if(isCancelled()){
               holder = null;
               return;
            }
            holder.img.setImageBitmap(result);
            taskPool.remove(holder.hashCode());
        }
    }

}
