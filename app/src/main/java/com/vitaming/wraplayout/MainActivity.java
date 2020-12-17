package com.vitaming.wraplayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private WrapLayout wrapLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wrapLayout = findViewById(R.id.wraplayout);

        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("2222222222222");
        list.add("33333");
        list.add("4");
        list.add("5555");
        list.add("6");
        list.add("77777777");
        list.add("88888");
        list.add("99999999");
        list.add("0000");
        list.add("1111111");
        list.add("222");

        MyAdapter adapter = new MyAdapter(R.layout.main_item, list);
        wrapLayout.setAdapter(adapter);
    }
}