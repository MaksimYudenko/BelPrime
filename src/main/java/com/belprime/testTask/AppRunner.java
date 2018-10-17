package com.belprime.testTask;

import com.belprime.testTask.gui.MainFrame;

import javax.swing.*;


public class AppRunner {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

}