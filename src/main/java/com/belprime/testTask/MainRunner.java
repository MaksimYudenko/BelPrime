package com.belprime.testTask;

import com.belprime.testTask.gui.Table;
import com.belprime.testTask.logic.WebSearchService;
import com.belprime.testTask.util.MessageProvider;

import javax.swing.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.belprime.testTask.util.Constants.WELCOME_PHRASE;

public class MainRunner {

    public static void main(String[] args) {
        String str = JOptionPane.showInputDialog(WELCOME_PHRASE);
//      final WebSearchService service = new WebSearchService(MessageProvider.getUserRequests());
        final WebSearchService service = new WebSearchService(MessageProvider.getUserRequestsViaSwing(str));
        Thread serviceThread = new Thread(service);
        serviceThread.start();
        try {
            serviceThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConcurrentHashMap<String, String> map = service.getMap();
//        new Thread(new Table(map));
        javax.swing.SwingUtilities.invokeLater(Table::createAndShowGUI);
    }

}