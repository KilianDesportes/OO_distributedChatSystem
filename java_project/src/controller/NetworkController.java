/**
 * NetworkController class, used to control network linked process.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */

package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import sockets.MessageSender;
import sockets.ThreadReceiverUDP;
import view.ConversationFrame;
import sockets.ThreadReceiverMulticast;

public class NetworkController extends Thread {

	/**
	 * Enum for every possible state of the connection
	 */
	private enum State {
		CONNECTED, UNCONNECTED, CHECKINGPSEUDO, PSEUDOOK
	};

	private ThreadReceiverMulticast threadRecvMulti;
	private ThreadReceiverUDP threadRecv;
	private MessageSender messageSender;
	private InetAddress local_inetAdr;
	private String local_pseudo = "unconnected_user";
	private BlockingQueue<DatagramPacket> messages_Queue;
	private boolean isPseudoValid = true;
	private State current_state = State.UNCONNECTED;
	private MainController controller;
	// private ReceiverTCPController threadRecvTCP;
	// For TCP reception

	public NetworkController(MainController maincontroller) {

		this.controller = maincontroller;

		this.current_state = State.UNCONNECTED;
		this.messages_Queue = new LinkedBlockingQueue<>();
		this.threadRecvMulti = new ThreadReceiverMulticast(this.messages_Queue);
		this.threadRecv = new ThreadReceiverUDP(this.messages_Queue);

		// this.threadRecvTCP = new ReceiverTCPController(this.messages_Queue);
		// For TCP reception

		this.messageSender = new MessageSender();
		this.local_inetAdr = this.getLocalAddress();

	}

	/**
	 * Start network control by starting both receptions threads (Multicast and UDP
	 * ones).
	 * 
	 * @see ThreadReceiverUDP
	 * @see ThreadReceiverMulticast
	 */
	public void start() {

		threadRecvMulti.start();
		threadRecv.start();

		// threadRecvTCP.start();
		// For TCP reception

		super.start();

	}

	/**
	 * Thread run method to control the state of the connection and the Queue linked
	 * to every message reception. When a message is received, reception thread will
	 * put it into the Queue and this method will get it and treat it to make some
	 * action in function of this message.
	 */
	@Override
	public void run() {

		System.out.println("network controller started");

		long start = System.currentTimeMillis();

		while (this.isNetworkOk()) {

			if (System.currentTimeMillis() - start > 5000) {

				this.testUserConnected();

				start = System.currentTimeMillis();

			}

			DatagramPacket packet = null;

			if (this.current_state == State.CONNECTED) {

				try {

					packet = messages_Queue.poll(200, TimeUnit.MILLISECONDS);

				} catch (InterruptedException e) {

					e.printStackTrace();

				}

				if (packet != null) {
					

					InetAddress address = packet.getAddress();

					String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
		
					System.out.println("\n_____Message received : " + received + "_____");
					
					if (!address.equals(this.local_inetAdr)) {
						
						System.out.println("Adresse différente");

						if (received.contains("isPseudoValid>")) {

							String pseudo_received = received.substring(14);

							if (pseudo_received.compareTo(this.local_pseudo) == 0) {

								this.sendPseudoReponseNOK(address);

							} else {

								this.controller.addUser(pseudo_received, address);
							}

						} else if (received.compareTo("whoIsConnected") == 0) {

							if (this.local_pseudo.compareTo("unconnected_user") != 0) {

								this.login(this.local_pseudo);

							}

						} else if (received.contains("disconnect>")) {

							String pseudo_received = received.substring(11);

							this.controller.removeUser(pseudo_received);

						} else if (received.contains("login>")
								&& (received.substring(6).compareTo(this.local_pseudo) != 0)) {

							String pseudoRecv = received.substring(6);
							
							System.out.println(pseudoRecv + " is logged in, we add him in list with address " + address);

							this.controller.addUser(pseudoRecv, address);

						} else {

							if (received.compareTo("pseudoNOK") != 0) {
								
								System.out.println("Message received : " + received + " from "
										+ packet.getAddress().getHostAddress() + " at "
										+ this.getTime(":", " - ", "/"));

								writeFileReceived(received, address);
								
							} else {
								
								System.out.println("PSEUDONOK received as an udp message - debugging print");
							}

						}

					} else {
						
						System.out.println("Adresse recue égale a la mienne mienne");

					}
					
					System.out.println("______________________\n");
					
				}
				
			} else if (this.current_state == State.CHECKINGPSEUDO) {

				this.isPseudoValid = true;

				System.out.println("State CHECKINGPSEUDO");

				long time = System.currentTimeMillis();

				while (System.currentTimeMillis() - time < 2000) {

					try {
						packet = messages_Queue.poll(200, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (packet != null) {

						String received = new String(packet.getData(), packet.getOffset(), packet.getLength());

						if (received.compareTo("pseudoNOK") == 0) {

							this.isPseudoValid = false;

							this.current_state = State.UNCONNECTED;

						}

					}

				}

				if (this.isPseudoValid == true) {

					System.out.println("Pseudo is valid , state is now PSEUDOOK");
					this.current_state = State.PSEUDOOK;

				}

			} else {

				// Nothing to do - State is UNCONNECTED
			}
			

		}

		System.out.println("NETWORK NOK - NETWORK CONTROLLER RUN DOWN");

	}
	
	public boolean isPseudoOK() {
		return this.current_state == State.PSEUDOOK;
	}
	
	public void networkConnected() {
		System.out.println("State is now CONNECTED");
		this.current_state = State.CONNECTED;
	}

	/**
	 * Set the local pseudo of this controller with the given pseudo.
	 * 
	 * @param pseudo Pseudo you want to set.
	 */
	public void setPseudo(String pseudo) {

		this.local_pseudo = pseudo;

	}
	public String getPseudo() {

		return this.local_pseudo;

	}

	/**
	 * Set the state of the connection as CHECKINGPSEUDO
	 * 
	 * @see State
	 */
	public void setStateCheck() {

		this.current_state = State.CHECKINGPSEUDO;

	}

	/**
	 * Method to login yourself on the network with the given pseudo via the
	 * messageSender. Other users will be able to see you after this method.
	 * 
	 * @param pseudo Your pseudo
	 * @see MessageSender
	 */
	public void login(String pseudo) {

		this.local_pseudo = pseudo;

		this.messageSender.sendMessageMulticast("login>" + this.local_pseudo);
	}

	/**
	 * Method to test who is connected on the network via the messageSender. Other
	 * users should respond to this request.
	 * 
	 * @see MessageSender
	 */
	public void testUserConnected() {

		this.messageSender.sendMessageMulticast("whoIsConnected");

	}

	/**
	 * Method to send an UDP message to a given address using MessageSender and
	 * write this sent message to the history file linked to the address you sent
	 * the message to.
	 * 
	 * @param message_to_send The message you want to send
	 * @param target_address  IP Address of the user you want to send the message to
	 * @see MessageSender
	 */
	public void sendMessageUDP(String message_to_send, InetAddress target_address) {

		this.messageSender.sendMessageUDP(message_to_send, target_address);

		this.writeFileSend(message_to_send, target_address);

	}

	/**
	 * Method to send a multicast message using MessageSender.
	 * 
	 * @param message_to_send The message you want to send
	 * @see MessageSender
	 */
	public void sendMessageMulticast(String message_to_send) {

		this.messageSender.sendMessageMulticast(message_to_send);

	}

	/**
	 * This method is currently not used in the application, which is UDP based.
	 * 
	 * Method to send an TCP message to a given address using MessageSender and
	 * write this sent message to the history file linked to the address you sent
	 * the message to.
	 * 
	 * @param message_to_send The message you want to send
	 * @param target_address  IP Address of the user you want to send the message to
	 * @see MessageSender
	 */
	public void sendMessageTCP(Socket socket, String message_to_send, InetAddress target_address) {

		this.messageSender.sendMessageTCP(socket, message_to_send, target_address);

		this.writeFileSend(message_to_send, target_address);

	}

	/**
	 * Method to disconnect yourself on the network via the messageSender. Other
	 * users will be aware of your disconnection.
	 * 
	 * @see MessageSender
	 */
	public void disconnect() {

		this.sendMessageMulticast("disconnect>" + this.local_pseudo);

	}

	/**
	 * Return the local MessageSender
	 * 
	 * @return the local MessageSender
	 * @see MessageSender
	 */
	public MessageSender getMessageSender() {
		return this.messageSender;
	}

	/**
	 * Return the current state of the connection
	 * 
	 * @return The current state of the connection.
	 * @see State
	 */
	public State getNetworkState() {

		return this.current_state;

	}

	/**
	 * Return true if the network state is CONNECTED.
	 * 
	 * @return True if CONNECTED, false otherwise.
	 * @see State
	 */
	public boolean isConnected() {

		return this.current_state == State.CONNECTED;
	}

	/**
	 * This method is used when a message is received from another user. It write
	 * the received message on a file named with the source address (where '.' are
	 * replaced by '_'). This file will be used as history for the conversation with
	 * the sender.
	 * 
	 * @param message_received The message you received and you will write into the
	 *                         file.
	 * @param inetAdr_sources  The source address of the message you received.
	 */
	private void writeFileReceived(String message_received, InetAddress inetAdr_sources) {

		if(controller.isConversation(inetAdr_sources) != true)
		{
			System.out.println("New conv with adr " + inetAdr_sources);
			controller.createConv(inetAdr_sources);
		}
		controller.msgReceived(message_received, inetAdr_sources);
		
		new File("/HISTORY").mkdirs();
		
		String file_ipAdr = "HISTORY" + File.separator + inetAdr_sources.getHostAddress().replace('.', '_') + ".txt";

		try {

			File fileHistory = new File(file_ipAdr);

			if (!fileHistory.exists()) {
				fileHistory.createNewFile();
			}

			FileWriter fwriter = new FileWriter(fileHistory, true);
			PrintWriter pwriter = new PrintWriter(fwriter);

			pwriter.write(inetAdr_sources.getHostAddress() + ";" + this.local_inetAdr.getHostAddress() + ";"
					 + this.getTime(":", ":", ":") + ";" + message_received);
			pwriter.flush();
			
			pwriter.close();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * This method is used when a message is sent to another user. It write the sent
	 * message on a file named with the destination address (where '.' are replaced
	 * by '_'). This file will be used as history for the conversation with the
	 * receiver.
	 * 
	 * @param message_received The message you sent and you will write into the
	 *                         file.
	 * @param inetAdr_sources  The destination address of the message you sent.
	 */
	public void writeFileSend(String message_sended, InetAddress inetAdr_target) {
		
		new File("HISTORY").mkdirs();
		
		String file_ipAdr = "HISTORY" + File.separator + inetAdr_target.getHostAddress().replace('.', '_') + ".txt";

		try {

			File fileHistory = new File(file_ipAdr);

			if (!fileHistory.exists()) {
				fileHistory.createNewFile();
			}

			FileWriter fwriter = new FileWriter(fileHistory, true);
			PrintWriter pwriter = new PrintWriter(fwriter);

			pwriter.write(this.local_inetAdr.getHostAddress() + ";" + inetAdr_target.getHostAddress() + ";"
					 + this.getTime(":", ":", ":") + ";" + message_sended);
			
			pwriter.flush();
			
			pwriter.close();
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * Method used to respond to a "isPseudoValid" request from another user. This
	 * method respond that the pseudo is already taken by an user and the sender of
	 * the request cannot choose this pseudo. It use messageSender.
	 * 
	 * @param target_address Request sender address.
	 * @see MessageSender
	 */
	private void sendPseudoReponseNOK(InetAddress target_address) {

		this.messageSender.sendMessageUDP("pseudoNOK", target_address);

	}

	/**
	 * Return true if reception threads are alive, false otherwise.
	 * 
	 * @return  true if reception threads are alive, false otherwise.
	 * @see ThreadReceiverMulticast
	 * @see ThreadReceiverUDP
	 */
	private boolean isNetworkOk() {

		return (threadRecvMulti.isAlive() && threadRecv.isAlive());

		// return (threadRecvMulti.isAlive() && threadRecvTCP.isAlive());
		// For TCP
	}

	/**
	 * Method used to get the local time and return a string formatted as wanted.
	 * This method is mainly use to write hour in message view and history.
	 * 
	 * @param separatorHour Separator between hour numbers
	 * @param separatorHourDate Separator between hour and date 
	 * @param separatorDate Separator between date numbers
	 * @return String of formated hour
	 * @see LocalDateTime
	 */
	private String getTime(String separatorHour, String separatorHourDate, String separatorDate) {

		int year = LocalDateTime.now().getYear();
		int month = LocalDateTime.now().getMonthValue();
		int day = LocalDateTime.now().getDayOfMonth();
		int hour = LocalDateTime.now().getHour();
		int min = LocalDateTime.now().getMinute();

		String str_hour = "" + hour + separatorHour + min + separatorHourDate + day + separatorDate + month
				+ separatorDate + year;

		return str_hour;
	}

	/**
	 * Method used to find the local IP address of the computer.
	 * 
	 * @param paramName description
	 * @return InetAddress local IP address
	 */
	private InetAddress getLocalAddress() {

		InetAddress localAdr = null;

		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface interfaceN = (NetworkInterface) interfaces.nextElement();
				Enumeration<InetAddress> iEnum = interfaceN.getInetAddresses();

				while (iEnum.hasMoreElements()) {
					InetAddress ia = (InetAddress) iEnum.nextElement();
					if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
						localAdr = ia;
					}

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return localAdr;

	}

}
