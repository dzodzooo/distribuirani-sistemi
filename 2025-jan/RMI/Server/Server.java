import java.rmi.registry.*;
import java.rmi.*;
import java.net.*;
public class Server{
	IFibonacciGenerator generator;	
	public void start() throws RemoteException, MalformedURLException{
		this.generator=new FibonacciGenerator();
		LocateRegistry.createRegistry(1099);
		Naming.rebind("rmi://localhost:1099/generatefib", generator);
	}

	public static void main(String[] args){
	
		try{
			Server server = new Server();
			server.start();
		}
		catch(Exception e){e.printStackTrace();}
	}

}

