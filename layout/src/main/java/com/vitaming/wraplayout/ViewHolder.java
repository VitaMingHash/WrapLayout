package com.vitaming.wraplayout;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Ming
 * 2020/12/11
 */
public class ViewHolder {
    private View view;

    public ViewHolder(View view) {
        this.view = view;
    }

    public void setText(int resourceId, String s) {
        TextView textView = view.findViewById(resourceId);
        textView.setText(s);
    }

    /**
     * 通过id获取控件
     * @param resourceId
     * @return
     */
    public View getView(int resourceId) {
        return view.findViewById(resourceId);
    }


}
