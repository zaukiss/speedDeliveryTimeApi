package speedDeliveryTimeApi.exeption;

public class ConnectionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private int codeExeption;
	private String msg;
	
	public ConnectionException(int exCode, String mess){
		
		codeExeption = exCode;
		msg = mess;
		
	}

	public int getCodeExeption() {
		return codeExeption;
	}

	public String getMsg() {
		return msg;
	}
	
	

}
