package com.example.msdnitellyou;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msdnitellyou.model.FeedAdapter;
import com.example.msdnitellyou.model.ItemModel;
import com.example.msdnitellyou.model.RSSFeed;
import com.example.msdnitellyou.model.RSSItem;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    //声明相关变量
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<RSSItem> rssItemList;
    private ListView lvLeftMenu;
    private Button aboutBtn;
    FeedAdapter feedAdapter;
    private ListView lvRightMenu;
    private String[] lvs = {"企业解决方案", "MSDN 技术资源库", "工具和资源","应用程序","开发人员工具","操作系统","服务器","设计人员工具"};
    private String[] lvi = {"aff8a80f-2dee-4bba-80ec-611ac56d3849", "aff8a80f-2dee-4bba-80ec-611ac56d3849", "051d75ee-ff53-43fe-80e9-bac5c10fc0fb","051d75ee-ff53-43fe-80e9-bac5c10fc0fb","fcf12b78-0662-4dd4-9a82-72040db91c9e","7ab5f0cb-7607-4bbe-9e88-50716dc43de6","5d6967f0-b58d-4385-8769-b886bfc2b78c","5d6967f0-b58d-4385-8769-b886bfc2b78c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews(); //获取控件

        toolbar.setTitle("最近更新");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //设置菜单列表
        List<ItemModel> items = new ArrayList<>();
        for (int i = 0;i < lvs.length;i++){
            items.add(new ItemModel(lvi[i],lvs[i]));
        }

        rssItemList = new ArrayList<>();
        feedAdapter = new FeedAdapter(this,R.layout.feed_item,rssItemList);
        lvRightMenu.setAdapter(feedAdapter);

        CategroyAdapter categroyAdapter = new CategroyAdapter(this,R.layout.categroy_item,items);
        lvLeftMenu.setAdapter(categroyAdapter);

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutDialog dialog = new AboutDialog();
                dialog.show(getFragmentManager(),"about");
            }
        });
        Button viewBtn = (Button)findViewById(R.id.web_btn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("http://msdn.itellyou.cn"));
                intent.setAction("android.intent.action.VIEW");
                startActivity(intent);
            }
        });

        initRss();
    }

    private void findViews() {
        aboutBtn = (Button)findViewById(R.id.about_btn);
        toolbar = (Toolbar)findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.dl_left);
        lvLeftMenu = (ListView)findViewById(R.id.lv_left_menu);
        lvRightMenu = (ListView)findViewById(R.id.right_view_list);
    }

    private void initRss(){
        XmlPullParserUtil.parseXml(new RSSCallback() {
            @Override
            public void onSuccess(final RSSFeed feeds) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RSSItem item : feeds.getAllItems()){
                            rssItemList.add(item);
                        }
                        feedAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
