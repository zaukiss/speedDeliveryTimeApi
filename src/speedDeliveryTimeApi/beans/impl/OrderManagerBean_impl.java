package speedDeliveryTimeApi.beans.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import speedDeliveryTimeApi.beans.interfaces.IOrderManager_bean;
import speedDeliveryTimeApi.beans.utils.ConstantsSDT;
import speedDeliveryTimeApi.beans.utils.NetworkUtile;
import speedDeliveryTimeApi.beans.utils.WrongListenerException;

@Stateless(mappedName=ConstantsSDT.ORDER_MANAGER_BEAN_MAPPED_NAME)
public class OrderManagerBean_impl implements IOrderManager_bean {

	AtomicBoolean _waitForOrder = new AtomicBoolean(false); 
	volatile DatagramSocket _sockOrderWait ;
	volatile DatagramSocket _sockOrderToSend ;
	InetAddress _remoteIPAddress;
	int _remotePort;
	volatile byte[] _receiveData = new byte[ConstantsSDT.DATA_BUFFER_SIZE];
	volatile DatagramPacket _datgrameReceiver = new DatagramPacket(_receiveData, ConstantsSDT.DATA_BUFFER_SIZE);


	public void storeOrder() {
		// TODO Auto-generated method stub

	}


	public void planOrder() {
		// TODO Auto-generated method stub

	}


	public void notifyValidateOrder() {
		// TODO Auto-generated method stub

	}

	public void createOrder() {
		// TODO Auto-generated method stub

	}

	public void modifyOrder() {
		// TODO Auto-generated method stub

	}

	public void addRemoteListener(int port, InetAddress addr) {

			this._remoteIPAddress = addr;
			this._remotePort = port;

	}


	@Asynchronous
	public void startListenOrder() throws WrongListenerException, SocketException, UnknownHostException {
		
		synchronized (_remoteIPAddress) {
			
			if(!_waitForOrder.get() && _remoteIPAddress != null && _remotePort > 1024){

				startWaitOrder();

			}else{

				if(!_waitForOrder.get()){
					
					System.out.println("_waitForOrde =  "+_waitForOrder+" _remoteIPAddress = "+_remoteIPAddress+" _remotePort = "+_remotePort);
					throw new  WrongListenerException("Listener is not set properly check it please : "
							+ " it need right remote addres and port must be up to 1024");
				}

		}
}


	}

	//PRIVATE METHODS

	private void startWaitOrder() throws SocketException, UnknownHostException {

		System.out.println("OrderNotify_impl.startWaitOrder : start... ");
		_waitForOrder.set(true);
		_sockOrderWait = new DatagramSocket(ConstantsSDT.LINSTEN_ORDER_PORT, NetworkUtile.getLocalHostLANAddress());
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("starting wait for order with addres = "+ _sockOrderWait.getLocalAddress().toString() + 
						" and port = "+_sockOrderWait.getLocalPort());
				while(_waitForOrder.get()){

					try {

						_sockOrderWait.receive(_datgrameReceiver);
						byte[] contentReceive =  _datgrameReceiver.getData();
						System.out.println("OrderNotify_impl.run : i receive some datas");
						if(contentReceive.length > 0){
							System.out.println("OrderNotify_impl.run : gonne send data");
							sendNotification(contentReceive);
						}

					} catch ( IOException e ) {

						e.printStackTrace();

					}

				}			

			}

		}).start();

	}

	private void sendNotification(byte[] data) throws IOException{

		if(_remoteIPAddress != null && _remotePort > 1024){

			_sockOrderToSend = new DatagramSocket();
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, _remoteIPAddress, _remotePort);
			_sockOrderToSend.send(sendPacket);
			_sockOrderToSend.close();

		}
	}

}
