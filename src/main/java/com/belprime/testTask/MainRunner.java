package com.belprime.testTask;

import com.belprime.testTask.gui.ResultsTable;
import com.belprime.testTask.logic.WebSearchService;
import com.belprime.testTask.util.MessageProvider;

import java.util.concurrent.ConcurrentHashMap;

public class MainRunner {

    public static void main(String[] args) {
        final WebSearchService service = new WebSearchService(MessageProvider.getUserRequests());
        Thread serviceThread = new Thread(service);
        serviceThread.start();
        try {
            serviceThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConcurrentHashMap<String, String> map = service.getMap();
        new Thread(new ResultsTable(map));
        javax.swing.SwingUtilities.invokeLater(ResultsTable::createAndShowGUI);
    }

}