package com.belprime.testTask.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Table extends JPanel implements Runnable {

    private static ConcurrentHashMap<String, String> map;
    private static Integer mapSize;

    public Table(ConcurrentHashMap<String, String> map) {
        super(new GridLayout(1, 0));
        Table.map = map;
        mapSize = map.size();
        String[] columnNames = {"No", "URL", "Title",};
        Object[][] data = getData(map);
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, mapSize * 30));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
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

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("BelPrime web search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Table table = new Table(map);
        table.setOpaque(true);
        frame.setContentPane(table);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        createAndShowGUI();
    }

}