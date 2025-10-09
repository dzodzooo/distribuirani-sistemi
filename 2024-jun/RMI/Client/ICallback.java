import java.rmi.*;

public interface ICallback extends Remote {
  public void print(String text) throws RemoteException;
}
