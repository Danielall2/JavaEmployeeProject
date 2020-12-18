package com.sparta.daniel.model;

import com.sparta.daniel.controller.JDBCThread;
import com.sparta.daniel.view.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CreatingNThreads {

    private static final Logger logger = LogManager.getLogger(CreatingNThreads.class);

    public static ArrayList<Thread> createThreads(int numberOfThreads, ArrayList<EmployeeDTO> data) {

        long start = System.nanoTime();

        Printer.printMessage("");
        Printer.printMessage("Creating Data for Threads....");
        Printer.printMessage("");

        ArrayList<Thread> threadList = new ArrayList<>();
        List<EmployeeDTO> testArray;

        // Code below separates the arrayList so that multiple threads can use it
        // Multiple lines of code intended for there to be no crossover when arranging the arrays for the threads

        List<List<EmployeeDTO>> listOfLists = new ArrayList<>(numberOfThreads);

        if (data.size() != 0) {
            int numberOfPartitions = Integer.divideUnsigned(data.size(), numberOfThreads);

            logger.debug("Number of partitions : " + numberOfPartitions);

            int counterForLastIndex = data.size() - (numberOfPartitions * numberOfThreads);
            int counterForFirstIndex = 0;
            int last = 0;
            int differenceDueToFloor = data.size() - (numberOfPartitions * numberOfThreads);

            for (int index = 0; index < numberOfThreads; index++) {
                int lastIndex = (index + 1) * numberOfPartitions;

                if (counterForLastIndex != 0 && counterForLastIndex != differenceDueToFloor) {
                    counterForFirstIndex = counterForFirstIndex + 1;
                } else if (last == 0 && counterForLastIndex != differenceDueToFloor) {
                    counterForFirstIndex = counterForFirstIndex + 1;
                    last = 1;
                }

                logger.debug("Counter for First Index : " + counterForFirstIndex);

                if (counterForLastIndex > 0) {
                    lastIndex = lastIndex + 1 + differenceDueToFloor - counterForLastIndex;
                    counterForLastIndex--;
                } else {
                    lastIndex = lastIndex + differenceDueToFloor - counterForLastIndex;
                }

                logger.debug("Counter for Last Index : " + counterForLastIndex);

                int firstIndex = (numberOfPartitions * index) + counterForFirstIndex;
                testArray = data.subList(firstIndex, lastIndex);
                listOfLists.add(testArray);

                logger.debug("First Index : " + firstIndex);
                logger.debug("Last Index : " + lastIndex);
                logger.debug("Size of testArray : " + testArray.size());

            }
        }

        long finish = System.nanoTime();
        Printer.printMessage("Time taken to create sub data : " + (finish - start) / 1_000_000_000 + " " + "second(s)");
        Printer.printMessage("");

        Printer.printMessage("Inserting Data via " + numberOfThreads + " thread(s)....");

        for (int i = 1; i <= numberOfThreads; i++) {
            threadList.add(new Thread(new JDBCThread(listOfLists.get(i - 1))));
        }

        return threadList;
    }

}
