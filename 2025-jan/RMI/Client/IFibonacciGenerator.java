import java.rmi.*;
public interface IFibonacciGenerator extends Remote{
	public void generateFibonacciSequence(int N, ICallback client) throws RemoteException;
}
