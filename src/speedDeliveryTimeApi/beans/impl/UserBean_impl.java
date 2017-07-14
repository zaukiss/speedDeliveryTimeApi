package speedDeliveryTimeApi.beans.impl;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.ejb.Stateless;

import speedDeliveryTimeApi.beans.interfaces.IUserBean;
import speedDeliveryTimeApi.beans.utils.ConstantsSDT;
import speedDeliveryTimeApi.exeption.ConnectionException;
import speedDeliveryTimeApi.exeption.ExceptionConstants;

@Stateless(mappedName=ConstantsSDT.ORDER_USER_BEAN_MAPPED_NAME)
public class UserBean_impl implements IUserBean{

	private Hashtable<String, char[]> loginsTab = new Hashtable<>();//just for demonstration of first delivery
	private boolean _loginFounded;
	public boolean connect(String login, char[] password) throws ConnectionException{
		
		setTestLogin("userTest");
		Enumeration<String> loginKeys =  loginsTab.keys();
		_loginFounded = false;
		while(loginKeys.hasMoreElements()){

			String nextKey =  loginKeys.nextElement(); 
			char[] pass = loginsTab.get(nextKey);
			if( nextKey.equals(login) && isSameContent(password, pass)) return true;
			else if(nextKey.equals(login) && !isSameContent(password, pass)){
				_loginFounded = true;
				throw new ConnectionException(ExceptionConstants.CONNECTION_EXCEPTION_BAD_PASSWORD, "wrong password"); 
			}
		}
		if(!_loginFounded) throw new ConnectionException(ExceptionConstants.CONNECTION_EXCEPTION_BAD_LOGIN, "login not found"); 
		return false;
	}

	public void disconnect(String login) {


	}

	public void saveUser(String login, char[] password) {

		if(!loginsTab.contains(login))
			loginsTab.put(login, password);

	}
	
	private void setTestLogin(String log)
	{
		
		if(!loginsTab.contains(log)){
			char[] pass = new char[4];
			pass[0] = '1';
			pass[1] = '2';
			pass[2] = '3';
			pass[3] = '4';
			loginsTab.put(log, pass);
		}
	}
	
	private boolean isSameContent(char[] pass1, char[] pass2){
		
		if( pass1.length !=  pass2.length) return false;
		
		for(int i = 0; i < pass1.length; i++ ){
			
			if(pass1[i] != pass2[i]  ) return false;
			
		}
		
		return true;
	}

}
