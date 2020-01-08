
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import controller.MainController;
import view.ConversationFrame;

public class Main {

	public static void main(String[] args) {
<<<<<<< HEAD:sockets/src/sockets/Main.java
		
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
		
		MainController mc = new MainController();
		new ConversationFrame("Kilian",localAdr, mc);
=======

		new MainController();
>>>>>>> a379e4cb06a536d62fd480efa65898c6ad00c2a0:sockets/src/Main.java
	}

}
