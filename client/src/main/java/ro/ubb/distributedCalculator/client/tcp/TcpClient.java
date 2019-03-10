package ro.ubb.distributedCalculator.client.tcp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;


public class TcpClient {

    private ExecutorService executorService;

    public TcpClient(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void sumOfGivenIntegers() {

        System.out.println("Enter some numbers :");
        System.out.println("Press any other character to finish");
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.next());
        }

        StringBuilder stringList = new StringBuilder();

        list.forEach(e -> {
            stringList.append(e);
            stringList.append(".");
        });

        executorService.submit(() -> {

            try (Socket socket = new Socket("localhost", 1234);
                 InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String request = "doSum" + "\n" + stringList + "\n";
                System.out.println("client - sending request: " + request);
                os.write(request.getBytes());

                String result = br.readLine();

                System.out.println("client - result: " + result);
                System.out.println(".................................");


            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void productOfGivenIntegers() {

        System.out.println("Enter some numbers :");
        System.out.println("Press any other character to finish");
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> list = new ArrayList<>();

        while (scanner.hasNextInt()) {
            list.add(scanner.next());
        }

        StringBuilder stringList = new StringBuilder();

        list.forEach(element -> {
            stringList.append(element);
            stringList.append(".");
        });

        executorService.submit(() -> {

            try (Socket socket = new Socket("localhost", 1234);
                 InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String request = "doProduct" + "\n" + stringList + "\n";
                System.out.println("client - sending request " + request);
                os.write(request.getBytes());

                String result = br.readLine();

                System.out.println("client - result: " + result);
                System.out.println("........................................");


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void divideBy() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a :");
        int a = scanner.nextInt();

        System.out.println("Enter b :");
        int b = scanner.nextInt();

        System.out.println("Enter d :");
        int d = scanner.nextInt();

        executorService.submit(() -> {

            try (Socket socket = new Socket("localhost", 1234);
                 InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String request = "divideBy" + "\n" + a + " " + b + " " + d + "\n";
                System.out.println("client - sending request " + request);
                os.write(request.getBytes());

                String result = br.readLine();

                System.out.println("client - result: " + result);
                System.out.println("........................................");


            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void lastTwoDigitsDivideBy() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a :");
        int a = scanner.nextInt();

        System.out.println("Enter b :");
        int b = scanner.nextInt();

        System.out.println("Enter d :");
        int d = scanner.nextInt();

        executorService.submit(() -> {

            try (Socket socket = new Socket("localhost", 1234);
                 InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String request = "lastTwoDigitsDivideBy" + "\n" + a + " " + b + " " + d + "\n";
                System.out.println("client - sending request " + request);
                os.write(request.getBytes());

                String result = br.readLine();

                System.out.println("client - result: " + result);
                System.out.println("........................................");


            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
