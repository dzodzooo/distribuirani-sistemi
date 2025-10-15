using Chat;
internal class ChatCallback : IChatCallback
{
    public void SendMessage(Message mess) {
        Console.WriteLine(mess.Timestamp + " " + mess.Username + ": " + mess.Text);
    }
}