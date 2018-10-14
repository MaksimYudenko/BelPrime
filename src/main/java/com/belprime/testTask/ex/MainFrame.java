package com.belprime.testTask.ex;

import com.belprime.testTask.logic.WebSearchService;
import com.belprime.testTask.util.MessageProvider;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import static com.belprime.testTask.util.Constants.TITLE;

class MainFrame extends JFrame {
    private String message;
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private static Integer mapSize;
    private final JTextField textField = new JTextField(50);
    private Object[][] data;
    private final String[] columnNames = {"No", "URL", "Title",};
    private JTable table = new JTable();

  /*  private JLabel countLabel1 = new JLabel("0");
    private JLabel statusLabel = new JLabel("Task not completed.");
    private JButton startButton = new JButton("Start");*/

    MainFrame() {
        super(TITLE);


        getUI();


        start();
//        map.put("url1", "title1");
//        map.put("url2", "title2");
//        map.put("url3", "title3");
//        map.put("url4", "title3");
//        map.put("url5", "title3");
//        map.put("url6", "title3");
//        mapSize = map.size();
//        data = getData(map);
    }

    public void getUI() {
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(textField, gbc);

        final JButton searchButton = new JButton("Search");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(searchButton, gbc);

        gbc.ipady = 10;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(table, gbc);

        searchButton.addActionListener(e -> {
            message = textField.getText();
            System.out.println("Your request:" + message);
//            start();
        });

        setSize(800, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
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

    private void start() {

        // Use SwingWorker<Void, Void> and return null from doInBackground if
        // you don't want any final result and you don't want to update the GUI
        // as the thread goes along.
        // First argument is the thread result, returned when processing finished.
        // Second argument is the value to update the GUI with via publish() and process()
        SwingWorker<Object[][], JTable> worker =
                new SwingWorker<Object[][], JTable>() {

                    @Override
                    /*
                     * Note: do not update the GUI from within doInBackground.
                     */
                    protected Object[][] doInBackground() {
                        final WebSearchService service = new WebSearchService(
                                MessageProvider.getUserRequestsViaSwing("Gomel weather"));
                        Thread serviceThread = new Thread(service);
                        serviceThread.start();
                        try {
                            serviceThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        map = service.getMap();
                        mapSize = map.size();
                        System.out.println("MAP from doInBackground() " + map + ", size = " + mapSize);
                        data = getData(map);

            /*    // Simulate useful work
                for (int i = 0; i < 30; i++) {
                    Thread.sleep(100);
                    System.out.println("Hello: " + i);

                    // optional: use publish to send values to process(), which
                    // you can then use to update the GUI.
                    publish(i);
                }*/

                        return data;
                    }
/*

                    @Override
                    // This will be called if you call publish() from doInBackground()
                    // Can safely update the GUI here.
                    protected void process(List<Map.Entry<String, String>> chunks) {

                        table.setValueAt(chunks.size() - 1, 0, 0);
                        table.setValueAt(chunks.get(1), 0, 1);
                        table.setValueAt(chunks.get(2), 0, 2);

//                Integer value = chunks.get(chunks.size() - 1);
//                countLabel1.setText("Current value: " + value);
                    }
*/

                    @Override
                    // This is called when the thread finishes.
                    // Can safely update GUI here.
                    protected void done() {
                        try {
                            Object[][] d = get();
                            table = new JTable(d, columnNames);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }

                };

        worker.execute();
    }
}