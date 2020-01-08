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
import sockets.ReceiverTCPController;
import sockets.ThreadReceiverUDP;
import sockets.ThreadReceiverMulticast;

public class NetworkController extends Thread {

	private enum State {
		CONNECTED, UNCONNECTED, CHECKINGPSEUDO
	};

	private ThreadReceiverMulticast threadRecvMulti;
	private ThreadReceiverUDP threadRecv;
	private MessageSender messageSender;
	private InetAddress local_inetAdr;
	private String local_pseudo = "unconnected_user";
	private BlockingQueue<DatagramPacket> messages_Queue;
	private boolean isPseudoValid = true;
	private State current_state;
	private MainController controller;
	// private ReceiverTCPController threadRecvTCP;
	// For TCP reception

	public NetworkController(MainController maincontroller) {

		this.current_state = State.UNCONNECTED;
		this.messages_Queue = new LinkedBlockingQueue<>();
		this.threadRecvMulti = new ThreadReceiverMulticast(this.messages_Queue);
		this.threadRecv = new ThreadReceiverUDP(this.messages_Queue);

		// this.threadRecvTCP = new ReceiverTCPController(this.messages_Queue);
		// For TCP reception

		this.messageSender = new MessageSender();
		this.local_inetAdr = this.getLocalAddress();

	}

	public void start() {

		threadRecvMulti.start();
		threadRecv.start();

		// threadRecvTCP.start();
		// For TCP reception

		super.start();

	}

	public void run() {

		while (this.isNetworkOk()) {

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

					if (received.contains("isPseudoValid>")) {

						String pseudo_received = received.substring(14);

						if (pseudo_received.compareTo(this.local_pseudo) == 0) {

							this.sendPseudoReponseNOK(address);

						} else {

							// Pseudo OK - Nothing to do
						}

					} else if (received.compareTo("whoIsConnected") == 0) {

						if (this.local_pseudo.compareTo("unconnected_user") != 0) {

							this.login(this.local_pseudo);

						}

					} else if (received.contains("disconnect>")) {

						String pseudo_received = received.substring(11);

						this.controller.removeUser(pseudo_received);

					} else if (address.isMulticastAddress()) {

						this.controller.addUser(received, address);

					} else {

						System.out.println("Received " + received + " from " + packet.getAddress().getHostAddress()
								+ " at " + this.getTime(";", "-", "/"));
						System.out.flush();

						writeFileReceived(received, packet.getAddress());

					}
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

					System.out.println("Pseudo is valid , state is now CONNECTED");
					this.current_state = State.CONNECTED;

				}

			} else {

				// Nothing to do - State is UNCONNECTED
			}

		}

		System.out.println("NETWORK NOK - NETWORK CONTROLLER RUN DOWN");

	}

	public void setPseudo(String pseudo) {

		this.local_pseudo = pseudo;

	}

	public void setStateCheck() {

		this.current_state = State.CHECKINGPSEUDO;

	}

	public void login(String pseudo) {

		this.local_pseudo = pseudo;

		this.messageSender.sendMessageMulticast(this.local_pseudo);
	}

	public void testUserConnected() {

		this.messageSender.sendMessageMulticast("whoIsConnected");

	}

	public void sendMessageUDP(String message_to_send, InetAddress target_address) {

		this.messageSender.sendMessageUDP(message_to_send, target_address);

		this.writeFileSend(message_to_send, target_address);

	}

	public void sendMessageMulticast(String message_to_send) {

		this.messageSender.sendMessageMulticast(message_to_send);

	}

	public void sendMessageTCP(Socket socket, String message_to_send, InetAddress target_address) {

		this.messageSender.sendMessageTCP(socket, message_to_send, target_address);

		this.writeFileSend(message_to_send, target_address);

	}

	public void disconnect() {

		this.sendMessageMulticast("disconnect>" + this.local_pseudo);

	}

	public MessageSender getMessageSender() {
		return this.messageSender;
	}

	public State getNetworkState() {

		return this.current_state;

	}

	public boolean isConnected() {

		return this.current_state == State.CONNECTED;
	}

	private void writeFileReceived(String message_received, InetAddress inetAdr_sources) {

		String file_ipAdr = inetAdr_sources.getHostAddress().replace('.', '_') + ".txt";

		try {

			File fileHistory = new File(file_ipAdr);

			if (!fileHistory.exists()) {
				fileHistory.createNewFile();
			}

			FileWriter fwriter = new FileWriter(fileHistory, true);
			PrintWriter pwriter = new PrintWriter(fwriter);

			pwriter.write(inetAdr_sources.getHostAddress() + ";" + this.local_inetAdr.getHostAddress() + ";"
					+ message_received + ";" + this.getTime("/", "/", "/"));
			pwriter.flush();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	private void writeFileSend(String message_sended, InetAddress inetAdr_target) {

		String file_ipAdr = inetAdr_target.getHostAddress().replace('.', '_') + ".txt";

		try {

			File fileHistory = new File(file_ipAdr);

			if (!fileHistory.exists()) {
				fileHistory.createNewFile();
			}

			FileWriter fwriter = new FileWriter(fileHistory, true);
			PrintWriter pwriter = new PrintWriter(fwriter);

			pwriter.write(this.local_inetAdr.getHostAddress() + ";" + inetAdr_target.getHostAddress() + ";"
					+ message_sended + ";" + this.getTime("/", "/", "/"));
			pwriter.flush();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	private void sendPseudoReponseNOK(InetAddress target_address) {

		this.messageSender.sendMessageUDP("pseudoNOK", target_address);

	}

	private boolean isNetworkOk() {

		return (threadRecvMulti.isAlive() && threadRecv.isAlive());

		// return (threadRecvMulti.isAlive() && threadRecvTCP.isAlive());
		// For TCP
	}

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
