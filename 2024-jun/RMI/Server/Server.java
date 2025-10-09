import java.rmi.server.*;
import java.lang.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;
public class Server {
  MQTT mqttService;
  public Server()throws RemoteException{
    this.mqttService = new MQTT();
  }
  
  public void start() throws RemoteException, MalformedURLException {
    System.out.println("Server running...");
    LocateRegistry.createRegistry(1099);
    Naming.rebind("rmi://localhost:1099/mqttService", this.mqttService);
    Scanner scanner = new Scanner(System.in);
    String input;
    do {
      System.out.println("q to quit server");
      input = scanner.nextLine();
    } while (!input.equals("q"));
  }

  public void stop() throws NoSuchObjectException{
	System.out.println("Server stopping...");
  	UnicastRemoteObject.unexportObject(this.mqttService, true);	
  }
  
  public static void main(String[] args) {
    try {
      Server server = new Server();
      server.start();
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
