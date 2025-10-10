import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
public class Client{
	boolean nastavnik;
	IKolega kolega;
	ISistemZaOcenjivanje sistemZaOcenjivanje;

	public Client(boolean nastavnik) throws RemoteException{
		this.nastavnik=nastavnik;
		if(nastavnik)
			this.kolega=new Profesor();
		else
			this.kolega=new Student();
	}


	public void Setup() throws RemoteException, NotBoundException, MalformedURLException{
		this.sistemZaOcenjivanje = (ISistemZaOcenjivanje) Naming.lookup("rmi://localhost:1099/sistemzaocenjivanje");
	}
	public static void main(String[] args){
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.println("Da li si student ili profesor? profesor-p, student-s");
			String akcija=scanner.nextLine();
			Client client = KreirajKlijenta(akcija);
			client.Setup();
			if(client==null) return;
			System.out.println("Uneti sifru predmeta");
			String sifraPredmeta=scanner.nextLine();
			client.sistemZaOcenjivanje.Pokreni(client.nastavnik,  sifraPredmeta,client.kolega);
			if(!client.nastavnik)
				scanner.nextLine();
			System.out.println("Uneti sifru pitanja");
			String sifraPitanja=scanner.nextLine();
			System.out.println("Uneti pitanje");
			String tekst=scanner.nextLine();
			Poruka poruka = Poruka.napraviPoruku(sifraPredmeta, sifraPitanja, tekst, client.kolega);
			client.sistemZaOcenjivanje.PosaljiPitanje(poruka);
		}
		catch(Exception e){
			e.printStackTrace();	
		}
	
	}
	public static Client KreirajKlijenta(String uloga) throws RemoteException{
		Client client=null;
		switch(uloga){
			case "p":client= new Client(true); break;
			case "s":client= new Client(false); break;
			default: break;
		}
		return client;
	}
}
