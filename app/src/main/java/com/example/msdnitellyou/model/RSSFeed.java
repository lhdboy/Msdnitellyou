package com.example.msdnitellyou.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/25.
 */

public class RSSFeed {

    private String title = null; //新闻标题
    private String pubdate = null;
    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    private int itemCount; //新闻数
    private List<RSSItem> itemList; //存放的是所有新闻，一个item标签代表一条新闻


    public RSSFeed() {
        itemList = new ArrayList<RSSItem>();
    }

    /**
     * 负责把一个RSSItem加入到RSSFeed里面
     * @param item
     * @return
     */
    public int addItem(RSSItem item) {
        itemList.add(item);
        itemCount ++;
        return itemCount;
    }

    public RSSItem getItem(int location) {
        return itemList.get(location);
    }

    /**
     * 返回所有的新闻列表
     * @return
     */
    public List<RSSItem> getAllItems() {
        return itemList;
    }

    /**
     * List<Map<String,Object>>存放着要在ListView显示的数据
     * @return
     */
    public List<Map<String,Object>> getAllItemsForListView() {
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        int size = itemList.size();
        for(int i=0 ;i<size; i++) {
            HashMap<String,Object> item = new HashMap<String,Object>();
            item.put(RSSItem.TITLE, itemList.get(i).getTitle());
            item.put(RSSItem.PUBDATE, itemList.get(i).getPubDate());
            data.add(item);
        }
        return data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemCount() {
        return itemCount;
    }
}
