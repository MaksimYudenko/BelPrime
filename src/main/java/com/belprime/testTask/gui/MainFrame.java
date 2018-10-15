package com.belprime.testTask.gui;

import com.belprime.testTask.logic.PageExtractor;
import com.belprime.testTask.logic.WebSearchService;
import com.belprime.testTask.util.MessageProvider;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import static com.belprime.testTask.util.Constants.TITLE;

public class MainFrame extends JFrame {
    private static String message;
    private GridBagConstraints gbc;
    private final JTextField textField = new JTextField(30);
    private final String[] columnNames = {"No", "URL", "Title",};
    private Object[][] tmpData = new Object[10][3];
    private JTable tmpTable = new JTable(tmpData, columnNames);

    public MainFrame() {
        super(TITLE);
        showFrame(tmpTable);

        setSize(800, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void showFrame(JTable t) {
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;

        textField.setHorizontalAlignment(0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(30, 200, 20, 5);
        add(textField, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(26, 5, 20, 200);
        add(searchButton, gbc);
        searchButton.addActionListener(e -> {
            MainFrame.message = textField.getText();
            start();
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(20, 20, 5, 20);
        add(t, gbc);
    }

    private void showResultTable(ConcurrentHashMap<String, String> map) {
        repaint();
        String[] columnNames = {"No", "URL", "Title",};
        Object[][] data = getData(map);
        JTable table = new JTable(data, columnNames);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(20, 20, 5, 20);
        add(table, gbc);

        setSize(800, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private Object[][] getData(ConcurrentHashMap<String, String> map) {
        Object[][] data = new Object[map.size()][3];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            data[i][0] = i + 1;
            data[i][1] = entry.getKey();
            data[i][2] = entry.getValue();
            i++;
        }
        return data;
    }

    private void start() {
        SwingWorker<ConcurrentHashMap<String, String>, JTable> worker =
                new SwingWorker<ConcurrentHashMap<String, String>, JTable>() {

                    protected ConcurrentHashMap<String, String> doInBackground() {
                        final WebSearchService service = new WebSearchService(
                                MessageProvider.getUserRequestsViaSwing(message));
                        Thread serviceThread = new Thread(service);
                        serviceThread.start();
                        try {
                            serviceThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ConcurrentHashMap<String, String> results = service.getMap();
                        PageExtractor.displayItems(results);
                        return results;
                    }

                    protected void done() {
                        try {
                            ConcurrentHashMap<String, String> map = get();
                            remove(tmpTable);
                            showResultTable(map);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                };

        worker.execute();
    }

}