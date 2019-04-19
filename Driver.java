import java.awt.BorderLayout;

import java.awt.Dimension;

import java.awt.TextArea;

import java.awt.TextField;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

import java.net.UnknownHostException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JTextField;

    public class Driver extends Thread {

	public static int PORT = 6899;

	public static int BUFFER_SIZE = 2048;

	static DatagramSocket mSocket;

	static ArrayList<ChatFrame> mChats = new ArrayList<ChatFrame>();

	public void run() {

		// clear buffer

byte[] incMsg = new byte[BUFFER_SIZE];

clearArray(incMsg);

// create receive packet attempt to receive the packet
while (true) {

	System.out.println("Listening . . .");

	DatagramPacket incPacket = new DatagramPacket(incMsg, incMsg.length);

	try {

		mSocket.receive(incPacket);

	} catch (IOException e) {

		e.printStackTrace();

	}

	// Extracting information from the packet

	String incMessage = new String(incPacket.getData());

	int senderPort = incPacket.getPort();

	InetAddress senderAddress = incPacket.getAddress();

	clearArray(incMsg);

	// checks ArrayList to see if the chat exists

	boolean chat_exists = false;

	int index = -1;

	System.out.print("about to search");

	for (int i = 0; i < mChats.size(); i++) {

		InetAddress chkAddress = mChats.get(i).getAddress();

		int chkPort = mChats.get(i).getPort();

		// this line

		System.out.println("Address " + senderAddress + " send: " + senderPort);

				if (chkAddress.equals(senderAddress) && chkPort == senderPort) {

					chat_exists = true;

					index = i;

					break;

				}
			}
			
			System.out.println(incMessage);

			if (chat_exists == true) {


				mChats.get(index).msgRec(incMessage);

			} 
			else { 
				
				ChatFrame mFrame = new ChatFrame(mSocket, senderAddress, senderPort);
        		mFrame.msgRec(incMessage);
        		addToList(mFrame);
				
			}

		}
	}

	

	public static void main(String[] args) {


		DatagramSocket socket = createSocket();


		ChatWindows menu = new ChatWindows(socket);

		Thread thread = new Driver();

		thread.start();

	}


	private static DatagramSocket createSocket() {

		try {

			mSocket = new DatagramSocket(PORT);

		} catch (SocketException e) {

			e.printStackTrace();

		}

		return mSocket;

	}
	
 // my method add Tolist

public static void addToList(ChatFrame mFrame) {

	mChats.add(mFrame);

}

private static byte[] clearArray(byte[] message) {

	for (int i = 0; i < message.length; i++) {

		message[i] = 0;

	}

	return message;

}


public static InetAddress createInetObj(String ipAddress) {

	InetAddress address = null;

	try {

		address = InetAddress.getByName(ipAddress);

	} catch (UnknownHostException e) {

		e.printStackTrace();

	}

	return address;

}


private static byte[] createMessage() {


	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	System.out.println("Enter a message: ");

		String msgToReceiver = null;

		try {

			msgToReceiver = input.readLine();

		} catch (IOException e) {

			e.printStackTrace();

		}

		byte[] message = new byte[BUFFER_SIZE];

		message = msgToReceiver.getBytes();

		return message;

	}


	public static void removeChatFrame(InetAddress otherIP, int otherPort) {

		int index = -1;

		boolean chat_exists = false;

		for (int i = 0; i < mChats.size(); i++) {

			InetAddress chkAddress = mChats.get(i).getAddress();

			int chkPort = mChats.get(i).getPort();

			if (chkAddress.equals(otherIP) && chkPort == otherPort) {

				chat_exists = true;

				index = i;

			}

		}

		if (chat_exists == true) {

			mChats.remove(index);

		}

	}

}