import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.TextField;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.net.DatagramSocket;

import java.net.InetAddress;



import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JTextField;

import javax.swing.WindowConstants;



public class ChatWindows extends JFrame implements ActionListener {

	

	DatagramSocket mSocket;

	InetAddress iNetAddress;

	JTextField tfIP;

	JTextField tfPort;

	int portNum;

	JButton ChatBtn;

	

	public ChatWindows(DatagramSocket mSocket) {

		

		super();

		

		this.mSocket = mSocket;

		

		

		JFrame menu = new JFrame("Conversation");

		menu.setPreferredSize( new Dimension(600, 150) );

		

		JPanel top = new JPanel();

		

		JLabel txtIP = new JLabel("IP Address");

		this.tfIP = new JTextField("",20);

		top.add(txtIP, BorderLayout.EAST);

		top.add(tfIP, BorderLayout.CENTER);

		

		JPanel bottom = new JPanel();

		

		JLabel txtPort = new JLabel("Port Number.");

		this.tfPort = new JTextField("",20);

		bottom.add(txtPort,  BorderLayout.EAST);

		bottom.add(tfPort, BorderLayout.CENTER);

				

	    /*JButton ChatBtn = new JButton("Chat");

        ChatBtn.addActionListener(this);*/

		ChatBtn = new JButton("Chat");

		ChatBtn.addActionListener(this);

        



		menu.add(top, BorderLayout.NORTH);

		menu.add(bottom, BorderLayout.CENTER);		

		menu.add(ChatBtn, BorderLayout.SOUTH);
		
		
		menu.pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		menu.setVisible(true);

		

	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	



	}



	@Override

	public void actionPerformed(ActionEvent e) {

		

		

		if (e.getSource() == ChatBtn){


		try {

			String address = tfIP.getText();

			InetAddress senderAddress = Driver.createInetObj(address);

		

			int senderPort = Integer.parseInt(tfPort.getText());

		

			ChatFrame mFrame = new ChatFrame(mSocket, senderAddress, senderPort);

			Driver.addToList(mFrame);

		}

		catch (NullPointerException nl) {

			nl.printStackTrace();

		}



	}

	}

	



}