package com.example.msdnitellyou;

import android.util.Log;

import com.example.msdnitellyou.model.RSSFeed;
import com.example.msdnitellyou.model.RSSItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XmlPullParserUtil {

    public static void parseXml(final RSSCallback callback) {
            try {
                RESTClient.getAsync("rss.ashx", new HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        RSSFeed rssFeed = new RSSFeed();
                        RSSItem item = null;
                        Log.d("TAG", "onSuccess: " + result);
                        try {
                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(true); //设置为true，则factory创建的XmlPullParser提供对xml 命名空间的支持
                            XmlPullParser parser = factory.newPullParser();
                            parser.setInput(new StringReader(result.replace("</image>", "<image>test</image>"))); //解析读取的xml文件流
                            parser.nextTag(); //此时解析的标签为rss，parser.nextTag()：调用next()方法，并返回该事件是START_TAG 还是 END_TAG
                            parser.nextTag(); //此时解析的标签为channel
                            while (parser.nextTag() == XmlPullParser.START_TAG) {
                                String name1 = parser.getName(); //获得该标签的名字
                                if (name1.equals("item")) {
                                    item = new RSSItem();

                                    while (parser.nextTag() == XmlPullParser.START_TAG) {

                                        String name2 = parser.getName();

                                        if (name2.equals("title")) {
                                            item.setTitle(parser.nextText());
                                        } else if (name2.equals("category")) {
                                            item.setCategory(parser.nextText());
                                        } else if (name2.equals("description")) {
                                            item.setDescription(parser.nextText());
                                        } else if (name2.equals("pubDate")) {
                                            item.setPubDate(parser.nextText());
                                        } else {
                                            skipUnknownTag(parser);
                                        }
                                    }
                                    rssFeed.addItem(item);

                                } else {
                                    skipUnknownTag(parser);
                                }
                            }
                        } catch (Exception e) {
                        }
                        callback.onSuccess(rssFeed);
                    }
                });
            }catch (Exception ex){
            }
        }

    /**
     * 标签结束时进行的处理
     * @param parser
     * @throws Exception
     */
    private static void skipUnknownTag(XmlPullParser parser) throws Exception {
        // 事件为START_DOCUMENT时，parser.next()=0
        while (parser.next() > 0) {
            if (parser.getEventType() == XmlPullParser.END_TAG)
                break;
        }
    }

}
