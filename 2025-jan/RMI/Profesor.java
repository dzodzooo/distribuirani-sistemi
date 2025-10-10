import java.rmi.*;
import java.rmi.server.*;
public class Profesor extends UnicastRemoteObject implements IKolega{
	public Profesor() throws RemoteException{}

	public void Print(String text) throws RemoteException{
		System.out.println(text);
	}

	public void PrimiPoruku(Poruka poruka) throws RemoteException{
	
	}

}
