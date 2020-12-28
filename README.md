# 自动换行布局

创建adapter
```
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
```

设置adapter
```
MyAdapter adapter = new MyAdapter(R.layout.main_item, list);
wrapLayout.setAdapter(adapter);
```
