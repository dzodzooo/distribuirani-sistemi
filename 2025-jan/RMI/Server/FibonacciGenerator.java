import java.rmi.*;
import java.rmi.server.*;

public class FibonacciGenerator  extends UnicastRemoteObject implements IFibonacciGenerator{

	public FibonacciGenerator() throws RemoteException {}
	public void generateFibonacciSequence(int N, ICallback client) throws RemoteException{
		int a=1, b=1;
		client.printNumber(a);
		client.printNumber(b);
		do
		{
			int tmp=a+b;
			if(a+b>N) break;
			a=b;
			b=tmp;
			client.printNumber(tmp);
		}
		while(true);
	}

}
