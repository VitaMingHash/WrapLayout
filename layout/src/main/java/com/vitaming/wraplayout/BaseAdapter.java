package com.vitaming.wraplayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming
 * 2020/12/11
 */
public abstract class BaseAdapter<T> {
    private int layout;
    private List<T> list;

    public BaseAdapter(int layout) {
        this.layout = layout;
    }

    public BaseAdapter(int layout, List<T> list) {
        this.layout = layout;
        this.list = list;
    }

    public int getLayout() {
        return layout;
    }

    public void setData(List<T> list) {
        this.list = list;
    }

    public List<T> getData() {
        return list;
    }

    public abstract void convert(ViewHolder viewHolder, T t, int position);
}
