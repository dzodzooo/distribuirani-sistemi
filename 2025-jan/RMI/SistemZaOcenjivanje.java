import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;
import java.util.HashMap;

public class SistemZaOcenjivanje  extends UnicastRemoteObject implements ISistemZaOcenjivanje { 
	HashMap<String, Predmet> predmeti;

	public SistemZaOcenjivanje() throws RemoteException{
		this.predmeti=new HashMap<String, Predmet>();
	}

	public void Pokreni(boolean nastavnik, String sifraPredmeta, IKolega kolega){
		Predmet predmet=predmeti.get(sifraPredmeta);

		if(nastavnik){
			if(predmet==null) {
				predmet = Predmet.KreirajPredmet(sifraPredmeta);
				predmeti.put(sifraPredmeta, predmet);
			}
			predmet.nastavnici.add(kolega);	
		}
		else
		{
			if(predmet==null) {
				System.out.println("Predmet ne postoji.");
				return;
			}

			predmet.studenti.add(kolega);
			System.out.println("Student je registrovan.");
		}
	}
	
	public void PosaljiPitanje(Poruka poruka)throws RemoteException{
		if(!predmeti.containsKey(poruka.sifraPredmeta)) return;
		Predmet predmet = predmeti.get(poruka.sifraPredmeta);
		predmet.PosaljiPitanje(poruka);
		System.out.println("Pitanje je prosledjeno.");
	}

}
