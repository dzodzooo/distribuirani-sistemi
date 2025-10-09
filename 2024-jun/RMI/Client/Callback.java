import java.rmi.*;
import java.rmi.server.*;

public class Callback extends UnicastRemoteObject implements ICallback {
  String name;

  public Callback(String name) throws RemoteException { this.name = name; }

  public void print(String text) throws RemoteException {
    System.out.println(text);
  }

  public String getName() throws RemoteException { return this.name; }
  public void setName(String name) throws RemoteException { this.name = name; }
}
