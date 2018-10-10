package com.belprime.testTask;

import com.belprime.testTask.logic.PageExtractor;
import com.belprime.testTask.util.MessageProvider;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.belprime.testTask.util.Constants.LINKS_QUANTITY;

public class ConsoleRunner {

    public static void main(String[] args) {
        for (String message : MessageProvider.getUserRequests()) {
            final List<Element> searchList;
            try {
                searchList = new PageExtractor(message).getSearchList();

                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                for (Element link : searchList) {
                    if (map.size() == LINKS_QUANTITY) break;
                    final String url = PageExtractor.getUrl(link);
                    if (url.isEmpty()) continue;
                    final String title = PageExtractor.getTitle(url);
                    map.put(url, title);
                }

                PageExtractor.displayItems(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}