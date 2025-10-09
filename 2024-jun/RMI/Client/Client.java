import java.net.*;
import java.rmi.server.*;
import java.rmi.*;
import java.util.Scanner;

public class Client {

  IMQTT broker;
  ICallback callbackProvider;
  Scanner scanner;
  String name;

  public Client(String name) throws RemoteException, NotBoundException, MalformedURLException {
    this.callbackProvider = new Callback("");
    this.scanner = new Scanner(System.in);
    this.broker = (IMQTT)Naming.lookup("rmi://localhost:1099/mqttService");
    this.name=name;
  }

  public void publishMessageToTopic() throws RemoteException {
    System.out.println("Input message to publish");
    String message = this.scanner.nextLine();
    System.out.println("Input topic to publish the message to");
    String topic = this.scanner.nextLine();
    this.broker.publish(this.name, topic, message);
  }

  public void subscribeToTopic() throws RemoteException {
    System.out.println("\nInput topic to subscribe to");
    String topic = this.scanner.nextLine();
    this.broker.subscribe(topic, this.name, this.callbackProvider);
  }

  public void unsubscribeFromTopic() throws RemoteException{
    System.out.println("\nInput topic to unsubscribe from");
    String topic = this.scanner.nextLine();
    this.broker.unsubscribe(topic, this.name);
    UnicastRemoteObject.unexportObject(this.callbackProvider, true);
  }

  public void runBroker() throws RemoteException{
	  String input;
    do {    
      System.out.println("\nt to subscribe to topic");
      System.out.println("p to publish a message");
      System.out.println("q to quit");
      input = this.scanner.nextLine();
      switch (input) {
      case "q":
        this.unsubscribeFromTopic();
        break;
      case "p":
        this.publishMessageToTopic();
        break;
      case "t":
        this.subscribeToTopic();
        break;
      default:
        break;
      }
    } while (!input.equals("q"));
  }

  public static void main(String[] args) {
    try {
      System.out.println("Username:");
      String name = (new Scanner(System.in)).nextLine();
      Client client = new Client(name);
      client.runBroker();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
