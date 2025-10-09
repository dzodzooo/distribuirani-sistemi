import java.rmi.*;

public interface ICallback extends Remote {
  public void print(String text) throws RemoteException;
  public String getName() throws RemoteException;
  public void setName(String name) throws RemoteException;
}
