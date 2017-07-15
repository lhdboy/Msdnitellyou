package com.example.msdnitellyou;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.msdnitellyou.model.ItemModel;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class CategroyAdapter extends ArrayAdapter<ItemModel> {

    public CategroyAdapter(Context context, int resource, List<ItemModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ItemModel item = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.categroy_item,parent,false);

        TextView textView = (TextView)view.findViewById(R.id.categroy_item_textview);
        textView.setText(item.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CategroyListActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("name",item.getName());
                getContext().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
