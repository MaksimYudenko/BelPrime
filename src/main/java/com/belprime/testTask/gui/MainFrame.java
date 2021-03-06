package com.belprime.testTask.gui;

import com.belprime.testTask.logic.PageExtractor;
import com.belprime.testTask.logic.WebSearchService;
import com.belprime.testTask.util.MessageProvider;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import static com.belprime.testTask.util.Constants.TITLE;

public class MainFrame extends JFrame {

    private GridBagConstraints gbc = new GridBagConstraints();
    private static String message;
    private final JTextField textField = new JTextField(30);
    private JTable table;
    private JScrollPane sp;
    private AbstractTableModel model;

    public MainFrame() {
        super(TITLE);
        Map<String, String> map = new ConcurrentHashMap<>();
        model = new TableModel(map);
        table = new JTable(model);
        showFrame();
    }

    private void showFrame() {
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;

        textField.setHorizontalAlignment(0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(30, 100, 20, 5);
        add(textField, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(26, 5, 20, 100);
        add(searchButton, gbc);

        sp = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(650, 175));
        table.setFillsViewportHeight(true);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(20, 20, 5, 20);
        sp.setOpaque(true);
        add(sp, gbc);
        searchButton.addActionListener(e -> {
            MainFrame.message = textField.getText();
            start();
        });
        setSize(700, 330);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
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
                            remove(sp);
                            ConcurrentHashMap<String, String> map = get();
                            model = new TableModel(map);
                            table = new JTable(model);
                            table.setPreferredScrollableViewportSize(new Dimension(650, 175));
                            table.setFillsViewportHeight(true);
                            sp = new JScrollPane(table);
                            model.fireTableDataChanged();
                            add(sp, gbc);
                            pack();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                };
        worker.execute();
    }

}