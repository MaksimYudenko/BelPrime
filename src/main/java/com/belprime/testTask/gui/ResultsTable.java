package com.belprime.testTask.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResultsTable extends JPanel implements Runnable {
    private static ConcurrentHashMap<String, String> map;
    private final Integer mapSize;

    public ResultsTable(ConcurrentHashMap<String, String> map) {
        super(new GridLayout(1, 0));
        ResultsTable.map = map;
        this.mapSize = map.size();
        String[] columnNames = {"No", "URL", "Title",};
        Object[][] data = getData(map);
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, mapSize * 20));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("ITSM web search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ResultsTable newContentPane = new ResultsTable(map);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    private Object[][] getData(ConcurrentHashMap<String, String> map) {
        Object[][] data = new Object[mapSize][3];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            data[i][0] = i + 1;
            data[i][1] = entry.getKey();
            data[i][2] = entry.getValue();
            i++;
        }
        return data;
    }

    @Override
    public void run() {
        createAndShowGUI();
    }

}