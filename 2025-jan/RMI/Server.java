import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
public class Server {
	ISistemZaOcenjivanje sistemZaOcenjivanje;

	public Server() throws RemoteException{
		this.sistemZaOcenjivanje = new SistemZaOcenjivanje();
	}

	public void start() throws RemoteException, MalformedURLException{
		LocateRegistry.createRegistry(1099);
		Naming.rebind("rmi://localhost:1099/sistemzaocenjivanje", this.sistemZaOcenjivanje);
	}
	public static void main(String[] args){
		try{
			Server server = new Server();
			server.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
