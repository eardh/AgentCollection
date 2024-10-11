package com.huang.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author lei.huang
 * @Description TODO
 **/
public class MessageClient {

	public static void sendMessage(int port, int message) {
		try (DatagramChannel datagramChannel = DatagramChannel.open()) {
			SocketAddress address = new InetSocketAddress("127.0.0.1", port);
			ByteBuffer allocate = ByteBuffer.allocate(36);
			allocate.putInt(message);
			allocate.flip();
			datagramChannel.send(allocate, address);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendMessage(int port, String message) {
		try (DatagramChannel datagramChannel = DatagramChannel.open()) {
			SocketAddress address = new InetSocketAddress("127.0.0.1", port);
			ByteBuffer allocate = ByteBuffer.allocate(1024);
			byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
			allocate.putInt(bytes.length);
			allocate.put(bytes);
			allocate.flip();
			datagramChannel.send(allocate, address);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}