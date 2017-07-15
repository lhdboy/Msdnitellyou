package com.example.msdnitellyou;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msdnitellyou.model.KeyValModel;
import com.example.msdnitellyou.model.LangItemModel;
import com.example.msdnitellyou.model.LangResult;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private FragmentViewAdapter viewAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private TextView label;
    private ImageView loadingImage;
    private FrameLayout loadingBorder;
    private String id;
    private ObjectAnimator animator;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tipLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        loadingBorder = (FrameLayout)findViewById(R.id.loading_border);
        loadingImage = (ImageView)findViewById(R.id.loading_anim);
        tipLabel = (TextView)findViewById(R.id.detail_label);
        animator = ObjectAnimator.ofFloat(loadingImage,"scaleY",1f,1.3f,1f);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(200);
        animator.start();

        label = (TextView)findViewById(R.id.header_textview);
        tabLayout = (TabLayout)findViewById(R.id.tab_view);
        viewPager = (ViewPager)findViewById(R.id.tab_page);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        label.setText(intent.getStringExtra("name"));

        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();

        //获取语言列表

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewAdapter = new FragmentViewAdapter(getSupportFragmentManager(),fragmentList,titleList);

        viewPager.setAdapter(viewAdapter);

        tabLayout.setupWithViewPager(viewPager);

        getLang();
    }

    private void getLang(){
        RESTClient.postAsync("Category/GetLang", LangItemModel.class, new IHttpCallback() {
            @Override
            public <T> void onSuccess(T result) {
                final LangItemModel items = (LangItemModel)result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (items.getResult().size() > 0){
                            for (LangResult lang : items.getResult()){
                                fragmentList.add(PageFragment.newInstance(id,lang.getId()));
                                titleList.add(lang.getLang());
                            }
                            viewAdapter.notifyDataSetChanged();
                            animator.end();
                            loadingBorder.setVisibility(View.GONE);
                        }else {
                            animator.end();
                            tipLabel.setText("暂无数据");
                        }

                    }
                });
            }
        },new KeyValModel("id",id));
    }
}
