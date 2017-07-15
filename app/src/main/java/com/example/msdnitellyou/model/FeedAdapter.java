package com.example.msdnitellyou.model;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msdnitellyou.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class FeedAdapter extends ArrayAdapter<RSSItem> {
    public FeedAdapter(Context context, int resource, List<RSSItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RSSItem rssItem = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.feed_item,parent,false);

        TextView fileName = (TextView)view.findViewById(R.id.file_name);
        TextView dateName = (TextView)view.findViewById(R.id.file_date);

        fileName.setText(rssItem.getTitle());
        dateName.setText(rssItem.getCategory());
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager)getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("text",rssItem.getDescription()));

                Toast.makeText(getContext(), "复制下载地址成功", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return view;
    }
}
