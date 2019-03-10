package ro.ubb.distributedCalculator.client;

import ro.ubb.distributedCalculator.client.tcp.TcpClient;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientApp {

    public static void main(String[] args) {

        ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient(executorService);





        Scanner scanner = new Scanner(System.in);
        printMenu();
        while (true) {

            String option = scanner.next();

            if (option.equals("x")) {
                break;
            }
            switch (option) {
                case "1":
                    tcpClient.sumOfGivenIntegers();
                    break;
                case "2":
                    tcpClient.productOfGivenIntegers();
                    break;
                case "3":
                    tcpClient.divideBy();
                    break;
                case "4":
                    tcpClient.lastTwoDigitsDivideBy();
                    break;
                default:
                    System.out.println("this option is not yet implemented");
            }


        }

        executorService.shutdownNow();


    }

    private static void printMenu() {
        System.out.println(
                "\n............................................................\n" +
                        "1 - Sum of given integers \n" +
                        "2 - Product of given integers \n" +
                        "3 - Numbers in the interval [a,b) divided  by d\n" +
                        "4 - Numbers in the interval [a,b) whose last two digits are divisible by d \n" +
                        "x - Exit \n" +
                        "...........................................................");
    }

}


