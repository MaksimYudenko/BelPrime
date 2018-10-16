package com.belprime.testTask;

import com.belprime.testTask.gui.MainFrame2;

import javax.swing.*;


public class AppRunner {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame2::new);
    }

}
