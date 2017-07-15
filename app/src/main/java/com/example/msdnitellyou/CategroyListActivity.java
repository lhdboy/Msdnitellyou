package com.example.msdnitellyou;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msdnitellyou.model.CategoryItem;
import com.example.msdnitellyou.model.CategoryItemModel;
import com.example.msdnitellyou.model.ItemModel;
import com.example.msdnitellyou.model.KeyValModel;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

public class CategroyListActivity extends AppCompatActivity {
    private List<ItemModel> items;
    private CategroyItemAdapter adapter;
    private static final String TAG = "NetworkTest";
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;
    TextView headerText;

    private String currentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categroy_list);

        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.pull_layout);
        recyclerView = (RecyclerView)findViewById(R.id.categroy_list_view);
        headerText = (TextView)findViewById(R.id.header_textview);

        items = new ArrayList<>();
        adapter = new CategroyItemAdapter(this,items);
        adapter.setOnItemClick(new IItemClickListener() {
            @Override
            public void onClick(View view, ItemModel item) {
                Intent intent = new Intent(CategroyListActivity.this,DetailActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("name",item.getName());
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                adapter.notifyDataSetChanged();
                pullload(currentId);
            }
        });

        Intent intent = getIntent();
        currentId = intent.getStringExtra("id");
        headerText.setText(intent.getStringExtra("name"));
        pullload(currentId);
    }

    private void pullload(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<CategoryItem> model = RESTClient.postAsync("Category/Index",CategoryItem.class,new KeyValModel("id",currentId));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (CategoryItem item : model){
                                items.add(new ItemModel(item.getId(),item.getName()));
                            }
                            adapter.notifyDataSetChanged();
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                } catch (ServerException e) {
                    Toast.makeText(CategroyListActivity.this, e.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                }
            }
        }).start();
    }
}
