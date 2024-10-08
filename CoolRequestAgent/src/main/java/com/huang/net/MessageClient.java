package com.huang.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @Author lei.huang
 * @Description TODO
 **/
public class MessageClient {

	public static void sendMessage(int message) {
		try (DatagramChannel datagramChannel = DatagramChannel.open()) {
			SocketAddress address = new InetSocketAddress("127.0.0.1", 9527);
			ByteBuffer allocate = ByteBuffer.allocate(36);
			allocate.putInt(message);
			allocate.flip();
			datagramChannel.send(allocate, address);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}