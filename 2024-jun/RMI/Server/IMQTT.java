import java.rmi.*;

public interface IMQTT extends Remote {
  public void subscribe(String topic, String username, ICallback callback)
      throws RemoteException;
  public void unsubscribe(String topic, String username) throws RemoteException;
  public void publish(String username, String topicName, String message) throws RemoteException;
}
