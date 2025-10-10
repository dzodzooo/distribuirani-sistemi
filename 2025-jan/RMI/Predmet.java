import java.rmi.*;
import java.util.*;
public class Predmet{
	String sifraPredmeta;
	HashMap<String, IKolega> profesorOdgovoranZaPitanje;
	ArrayList<IKolega> studenti;
	ArrayList<IKolega> nastavnici;

	public void PosaljiPitanje(Poruka pitanje) throws RemoteException{
		for(IKolega student : studenti){
			student.PrimiPoruku(pitanje);
		}
	}

	public static Predmet KreirajPredmet(String sifraPredmeta)
	{
		Predmet predmet = new Predmet();
		predmet.sifraPredmeta=sifraPredmeta;
		predmet.profesorOdgovoranZaPitanje=new HashMap<String, IKolega>();
		predmet.studenti=new ArrayList<IKolega>();
		predmet.nastavnici=new ArrayList<IKolega>();
		return predmet;
	}
}
