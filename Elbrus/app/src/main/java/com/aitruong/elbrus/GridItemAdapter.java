package com.aitruong.elbrus;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> myList;
    private Context mContext;
    private TextView name_tv;
    private ImageView img;
    private View deleteView;
    private boolean isShowDelete;

    public GridItemAdapter(Context mContext,
                           ArrayList<HashMap<String, Object>> myList) {
        this.mContext = mContext;
        this.myList = myList;
    }

    public void setMyList(ArrayList<HashMap<String, Object>> myList) {
        this.myList = myList;
    }

    public void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item,
                null);
        img = (ImageView) convertView.findViewById(R.id.ItemImage);
        name_tv = (TextView) convertView.findViewById(R.id.ItemText);
        deleteView = convertView.findViewById(R.id.delete_markView);
        deleteView.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
        img.setBackgroundResource((int)myList.get(position).get("ItemImage"));
        name_tv.setText(myList.get(position).get("ItemText").toString());
        return convertView;
    }
}
