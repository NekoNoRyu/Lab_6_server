package support;

import commands.Command;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
public class InteractionWithClient {
    private static SocketChannel socketChannel;
    private static ServerSocketChannel serverSocketChannel;
    public static void init() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(6701);
            serverSocketChannel.bind(socketAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void destroy() {
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Command receiveCommand() {
        byte[] arr = new byte[1000];
        try {
            socketChannel = serverSocketChannel.accept();
            ByteBuffer buf = ByteBuffer.wrap(arr);
            socketChannel.read(buf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Command.deserialize(arr);
    }

    public static void sendMessage(String message) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(message);
            int counter = message.length();
            while (counter != 1000) {
                builder.append("\0");
                counter ++;
            }
            byte[] arr = builder.toString().getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(arr);
            socketChannel.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

