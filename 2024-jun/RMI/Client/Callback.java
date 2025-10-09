import java.rmi.*;
import java.rmi.server.*;

public class Callback extends UnicastRemoteObject implements ICallback {
  public Callback() throws RemoteException {}
  public void print(String text) throws RemoteException {
    System.out.println(text);
  }
}
