import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;

public class Student extends UnicastRemoteObject implements IKolega{
	public Student() throws RemoteException{}
	public void PrimiPoruku(Poruka poruka) throws RemoteException{
		this.Print(poruka.sifraPredmeta + " " + poruka.sifraPitanja + " " +poruka.tekstPoruke);
	}

	public void Print(String text){
		System.out.println(text);	
	}
}
