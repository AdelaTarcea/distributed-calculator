package ro.ubb.distributedCalculator.server;

import ro.ubb.distributedCalculator.server.tcp.TcpServer;

public class ServerApp {
    public static void main(String[] args) {
        TcpServer tcpServer=new TcpServer();

        tcpServer.startServer();

        System.out.println("bye - server");
    }

}
