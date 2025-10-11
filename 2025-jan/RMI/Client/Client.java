import java.rmi.*;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client{
	IFibonacciGenerator generator;
	ICallback callback;
	public void start() throws NotBoundException, MalformedURLException, RemoteException{
		this.callback=new Callback();
		this.generator=(IFibonacciGenerator)Naming.lookup("rmi://localhost:1099/generatefib");
	}

	public static void main(String[] args){
		try{	
			Client client = new Client();
			client.start();
			Scanner scanner = new Scanner(System.in);
			while(true){
				int number=scanner.nextInt();	
				client.generator.generateFibonacciSequence(number, client.callback);
			}
		}
		catch(Exception e){
			e.printStackTrace();
	 	}
	}
}
