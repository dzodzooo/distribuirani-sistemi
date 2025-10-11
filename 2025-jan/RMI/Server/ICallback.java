import java.rmi.*;

public interface ICallback extends Remote{

	public void printNumber(int x) throws RemoteException;
}
