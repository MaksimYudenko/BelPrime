/*
package com.belprime.testTask;

import com.belprime.testTask.gui.FrameProcessor;
import com.belprime.testTask.gui.Table;
import com.belprime.testTask.logic.WebSearchService;
import com.belprime.testTask.util.MessageProvider;

import javax.swing.*;

import static com.belprime.testTask.util.Constants.TITLE;

public class AppRunner {

    public static void main(String[] args) {


        final WebSearchService service = new WebSearchService(
                MessageProvider.getUserRequestsViaSwing("belprime"));
        Table table = new Table(service.getMap());

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                JFrame frame = new JFrame(TITLE);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(500, 500);
                frame.pack();
                frame.setVisible(true);
                new FrameProcessor().createAndShowGUI(frame.getContentPane(),table);

            }
        });
    }

}*/
