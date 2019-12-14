package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import model.UserList;
import sockets.MessageSender;
import sockets.ThreadReceiverUDP;
import sockets.ThreadReceiverMulticast;

public class NetworkController {

	private ThreadReceiverMulticast threadRecvMulti;
	private ThreadReceiverUDP threadRecv;
	private MessageSender messageSender;
	private InetAddress local_inetAdr;
	private String local_pseudo = "unconnected_user";
	private LinkedBlockingQueue<DatagramPacket> messages_Queue;

	private boolean isPseudoValid = true;

	private enum State {
		CONNECTED, UNCONNCTED, CHECKINGPSEUDO, REBOOTING
	};

	private State current_state;

	// FAIRE UNE SORTE D ENUM AVEC PLUSIEURS ETATS (checking pseudo, non connecté,
	// connecté, redemarrage?) POUR FAIRE MOINS DE IF DANS LE MESSAGE TREATMENT

	private UserList main_userList;

	public NetworkController(UserList mainMaincontroller_UserList) {

		this.current_state = State.UNCONNCTED;

		this.messages_Queue = new LinkedBlockingQueue<>();
		this.main_userList = mainMaincontroller_UserList;

		this.threadRecvMulti = new ThreadReceiverMulticast(this.messages_Queue);
		this.threadRecv = new ThreadReceiverUDP(this.messages_Queue);
		this.messageSender = new MessageSender();

		this.local_inetAdr = this.getLocalAddress();

	}

	public void messageTreatment() {

		while (this.isNetworkOk() && this.current_state == State.CONNECTED) {

			DatagramPacket packet = messages_Queue.peek();

			InetAddress address = packet.getAddress();

			String received = new String(packet.getData(), packet.getOffset(), packet.getLength());

			System.out.println("Multicast packet ? -> " + address.isMulticastAddress());

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

			} else if (address.isMulticastAddress()) {

				// Test SYSO au dessus du if

				main_userList.addUser(received, address);

			} else {

				System.out.println("Received " + received + " from " + packet.getAddress().getHostAddress() + " at "
						+ this.getTime(";", "-", "/"));
				System.out.flush();

				writeFileReceived(received, packet.getAddress());

			}

		}

	}

	public State getState() {

		return this.current_state;

	}

	public boolean isPseudoValid(String pseudo) {

		this.sendingIsPseudoOK(pseudo);

		this.current_state = State.CHECKINGPSEUDO;

		long time = System.currentTimeMillis();

		while (System.currentTimeMillis() - time < 4000 || this.current_state == State.CHECKINGPSEUDO) {
			
			//IL FAUT REGLER LE SOUCIS DE LA QUEUE PARTAGE, ON DIRAIT QUE RIEN NE RENTRE, TROUVER UNE SORTIE DE PEEK BLOQUANT POUR PAS AVOIR DE NULL EXCEPTION?

			DatagramPacket packet = messages_Queue.peek();

			String received = new String(packet.getData(), packet.getOffset(), packet.getLength());

			if (received.compareTo("pseudoNOK") == 0) {

				this.isPseudoValid = false;

				this.current_state = State.UNCONNCTED;

			}

		}

		return this.isPseudoValid;
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

	private void sendingIsPseudoOK(String pseudo) {

		String msg_to_send = "isPseudoValid>" + pseudo;

		this.messageSender.sendMessageMulticast(msg_to_send);
	}

	public void sendPseudoReponseNOK(InetAddress target_address) {

		this.messageSender.sendMessageUDP("pseudoNOK", target_address);

	}

	public void login(String pseudo) {

		this.local_pseudo = pseudo;

		this.messageSender.sendMessageMulticast(this.local_pseudo);
	}

	public void testUserConnected() {

		this.messageSender.sendMessageMulticast("whoIsConnected");

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

	public void startReceptionThread() {
		threadRecvMulti.start();
		threadRecv.start();
	}

	public boolean isNetworkOk() {

		return (threadRecvMulti.isAlive() && threadRecv.isAlive());

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

}
