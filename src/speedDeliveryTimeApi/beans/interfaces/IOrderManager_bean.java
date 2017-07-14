package speedDeliveryTimeApi.beans.interfaces;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.ejb.Remote;

import speedDeliveryTimeApi.beans.utils.WrongListenerException;

@Remote
public interface IOrderManager_bean {
	
	public void storeOrder();
	public void planOrder();
	public void notifyValidateOrder();
	public void createOrder();
	public void modifyOrder();
	public void addRemoteListener(int port, InetAddress addr);
	public void startListenOrder() throws WrongListenerException, SocketException, UnknownHostException;
	
}
