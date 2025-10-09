namespace MessageQueueGRPC.Services;

class DatabaseService
{
    private List<Message> messages;
    private static DatabaseService? messageQueue;
    private static object lockObj = new object();

    public DatabaseService()
    {
        messages = new List<Message>();
    }
    public static DatabaseService getInstance()
    {
        if (messageQueue == null)
        {
            lock (lockObj)
            {
                if (messageQueue == null)
                    messageQueue = new DatabaseService();
            }

        }
        return messageQueue;
    }

    public void addMessage(Message message)
    {
        lock (lockObj)
        {
            messages.Add(message);
        }
    }

    public Message removeMessage(Id id)
    {
        Message message;
        lock (lockObj)
        {
            message = messages.ElementAt(id.Id_);
            messages.RemoveAt(id.Id_);
        }
        return message;
    }

    public List<Message> GetMessages()
    {
        return messages;
    }
}