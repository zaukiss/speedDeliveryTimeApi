package speedDeliveryTimeApi.beans.interfaces;

import javax.ejb.Remote;

import speedDeliveryTimeApi.exeption.ConnectionException;

@Remote
public interface IUserBean {

	public boolean connect(String login, char[] password) throws ConnectionException;
	public void disconnect(String login);
	public void saveUser(String login, char[] password);
	
}
