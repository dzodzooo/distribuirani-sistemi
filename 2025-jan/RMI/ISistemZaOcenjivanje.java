import java.rmi.*;

public interface ISistemZaOcenjivanje extends Remote {
	public void Pokreni(boolean nastavnik, String sifraPredmeta, IKolega kolega) throws RemoteException;
	public void PosaljiPitanje(Poruka poruka) throws RemoteException;
}
