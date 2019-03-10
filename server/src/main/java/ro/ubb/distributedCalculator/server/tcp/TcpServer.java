package ro.ubb.distributedCalculator.server.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TcpServer {

    public void startServer() {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("server - started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("server - client connected");

                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (InputStream is = clientSocket.getInputStream();
                 OutputStream os = clientSocket.getOutputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String name = br.readLine();
                System.out.println("server - received request: " + name);

                if (name.equals("doSum")) {
                    doSum(name, is, os, br);
                } else if (name.equals("doProduct")) {
                    doProduct(name, is, os, br);
                } else if (name.equals("divideBy")) {
                    divideBy(name, is, os, br);
                } else {
                    lastTwoDigitsDivideBy(name, is, os, br);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        private void doSum(String name, InputStream is, OutputStream os,
                           BufferedReader br) throws IOException {
            String args = br.readLine();
            List<Integer> list = new ArrayList<>();

            String[] arr = args.split("[.]+");
            for (int i = 0; i < arr.length; i++) {
                list.add(Integer.valueOf(arr[i]));
            }
            double sum = list.stream()
                    .mapToDouble(a -> a)
                    .sum();
            String response = sum + "\n";
            System.out.println("server - computed response: " + sum);
            os.write(response.getBytes());
        }

        private void doProduct(String name, InputStream is, OutputStream os,
                               BufferedReader br) throws IOException {
            String args = br.readLine();
            List<Integer> list = new ArrayList<>();

            String[] arr = args.split("[.]+");
            for (int i = 0; i < arr.length ; i++) {
                list.add(Integer.valueOf(arr[i]));
            }
            int product = list.stream()
                    .reduce(1, (a, b) -> a * b);

            String response = product + "\n";
            System.out.println("server - computed response: " + product);
            os.write(response.getBytes());
        }

        private void divideBy(String name, InputStream is, OutputStream os,
                              BufferedReader br) throws IOException {

            String args = br.readLine();
            String[] arr = args.split(" ");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            int d = Integer.parseInt(arr[2]);

            List<Integer> list = IntStream.rangeClosed(1, 1)
                    .boxed()
                    .flatMap(value ->
                            IntStream.range(a, b)
                                    .mapToObj(suit -> suit)
                    )
                    .collect(Collectors.toList());

            List<Integer> resultList = list.stream()
                    .filter(x -> x % d == 0)
                    .collect(Collectors.toList());

            String response = resultList + "\n";
            System.out.println("server - computed response: " + response);
            os.write(response.getBytes());
        }

        private void lastTwoDigitsDivideBy(String name, InputStream is, OutputStream os,
                                           BufferedReader br) throws IOException {

            String args = br.readLine();
            String[] arr = args.split(" ");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            int d = Integer.parseInt(arr[2]);


            IntStream.range(a, b)
                    .filter(nr -> nr % d == 0)
                    .boxed()
                    .collect(Collectors.toList());
            List<Integer> list = IntStream.rangeClosed(1, 1)
                    .boxed()
                    .flatMap(value ->
                            IntStream.range(a, b)
                                    .mapToObj(suit -> suit)
                    )
                    .collect(Collectors.toList());

            List<Integer> xx = new ArrayList<>();
            list.forEach(x -> xx.add(x % 100));

            List<Integer> resultList = list.stream()
                    .filter(x -> x % d == 0)
                    .collect(Collectors.toList());

            String response = resultList + "\n";
            System.out.println("server - computed response: " + response);
            os.write(response.getBytes());
        }
    }
}



