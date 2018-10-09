package com.belprime.testTask;

import com.belprime.testTask.logic.PageExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleRunner {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type phrase to search (as many as you'd like):");
        String msgLine = null;
        try {
            msgLine = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert msgLine != null;
        String[] messages = msgLine.split("\\s*(\\s{2,}|,|!|\\.)\\s*");
        for (String message : messages) {
            new PageExtractor(message).displayItems();
        }
    }

}