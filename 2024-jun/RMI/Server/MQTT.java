import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class MQTT extends UnicastRemoteObject implements IMQTT {

  private ArrayList<Topic> topics;

  public MQTT() throws RemoteException { this.topics = new ArrayList<Topic>(); }

  public void subscribe(String topicName, String username, ICallback callback)
      throws RemoteException {
    Topic newtopic = Topic.createTopic(topicName);
    if (topics.contains(newtopic)) {
      int index = topics.indexOf(newtopic);
      Topic oldtopic = topics.get(index);
      oldtopic.subscribeToTopic(callback, username);
    } else {
      newtopic.subscribeToTopic(callback, username);
      topics.add(newtopic);
    }
  }
  public void unsubscribe(String topicName, String username)
      throws RemoteException {
    Topic tmp = Topic.createTopic(topicName);
    if (topics.contains(tmp)) {
      int index = topics.indexOf(tmp);
      Topic topic = topics.get(index);
      topic.unsubscribeFromTopic(username);
    }
  }

  public void publish(String username, String topicName, String message) throws RemoteException {
    Topic tmp = Topic.createTopic(topicName);
    if (topics.contains(tmp)) {
      int index = topics.indexOf(tmp);
      Topic topic = topics.get(index);
      topic.publish(username, message);
    }
  }

}
