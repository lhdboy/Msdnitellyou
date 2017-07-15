package com.example.msdnitellyou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msdnitellyou.model.CategoryItem;
import com.example.msdnitellyou.model.ItemObject;
import com.example.msdnitellyou.model.KeyValModel;
import com.example.msdnitellyou.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/4/25.
 */

public class PageFragment extends Fragment {
    public static final String CATEGROY_ID ="CATEGROY_ID";
    public static final String LANG_ID ="LANG_ID";
    private RecyclerView recyclerView;
    private List<Project> mList;
    private ProductItemsAdapter adapter;

    private String categroyId;
    private String langId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_item,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.detail_item_listview);

        mList = new ArrayList<>();
        adapter = new ProductItemsAdapter(getContext(),mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        initData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            categroyId = getArguments().getString(CATEGROY_ID);
            langId = getArguments().getString(LANG_ID);
        }
    }

    public static PageFragment newInstance(String categroyId,String langId){
        Bundle bundle = new Bundle();
        bundle.putString(CATEGROY_ID,categroyId);
        bundle.putString(LANG_ID,langId);

        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    private void initData(){
        RESTClient.postAsync("Category/GetList", ItemObject.class, new IHttpCallback() {
            @Override
            public <T> void onSuccess(T result) {
                final ItemObject itemObject = (ItemObject)result;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Project p : itemObject.getResult()){
                            mList.add(p);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        },new KeyValModel("id",categroyId),new KeyValModel("lang",langId),new KeyValModel("filter","true"));
    }
}
