import java.io.*;
public class Poruka implements Serializable{
	 String sifraPredmeta;
	 String sifraPitanja;
	 String tekstPoruke;
	 IKolega posiljalac;

	public static Poruka napraviPoruku(String sifraPredmeta, String sifraPitanja, String tekstPoruke, IKolega posiljalac){
		Poruka poruka = new Poruka();
		poruka.sifraPredmeta = sifraPredmeta;
		poruka.sifraPitanja = sifraPitanja;
		poruka.tekstPoruke = tekstPoruke;
		poruka.posiljalac = posiljalac;
		return poruka;
	}
}

