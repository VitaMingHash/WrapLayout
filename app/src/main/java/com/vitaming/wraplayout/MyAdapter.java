package com.vitaming.wraplayout;

import android.widget.TextView;

import java.util.List;

/**
 * Created by Ming
 * 2020/12/11
 */
public class MyAdapter extends BaseAdapter<String> {
    public MyAdapter(int layout, List<String> list) {
        super(layout, list);
    }

    @Override
    public void convert(ViewHolder viewHolder, String s, int position) {
        TextView textView = (TextView) viewHolder.getView(R.id.text);
        textView.setText(s);
    }
}
