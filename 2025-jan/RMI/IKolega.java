import java.rmi.*;
public interface IKolega extends Remote{
	public void Print(String text) throws RemoteException;
	public void PrimiPoruku(Poruka poruka) throws RemoteException;
}
