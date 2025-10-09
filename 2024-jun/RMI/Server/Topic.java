import java.rmi.*;
import java.util.ArrayList;
public class Topic extends Object {
  String name;
  public ArrayList<ICallback> callbacks;
  public ArrayList<String> usernames;

  public Topic(String name) {
    this.name = name;
    this.callbacks = new ArrayList<ICallback>();
    this.usernames = new ArrayList<String>();
  }

  public void subscribeToTopic(ICallback userCb, String username) {
    if (this.usernames.contains(username)) {
      return;
    }
    callbacks.add(userCb);
    usernames.add(username);
    System.out.println(username + " subscribed to topic " + this.name + "\n");
  }

  public void unsubscribeFromTopic(String username) {
    if (this.usernames.contains(username)) {
      int index = this.usernames.indexOf(username);
      this.usernames.remove(index);
      this.callbacks.remove(index);
      System.out.println(username + " unsubscribed from topic " + this.name + "\n");
    }
  }

  public void publish(String username, String message) throws RemoteException {
    System.out.println(username + " published " + message +" to topic "+ this.name);  
    for (ICallback cb : callbacks) {
      cb.print(message);
    }
  }

  public static Topic createTopic(String topicName){
 	Topic topic = new Topic(topicName); 
	return topic;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Topic)) {
      return false;
    }

    if (this.name.equals(((Topic)obj).name)) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = this.name != null ? this.name.hashCode() : 0;
    return hash;
  }
}
