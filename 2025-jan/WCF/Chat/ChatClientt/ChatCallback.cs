using System;
using ChatClientt.Chat;
internal class ChatCallback : IChatServiceCallback
{
   
    public void CallbackSendMessage(Message mess) {
        Console.WriteLine(mess.Timestamp + " " + mess.Username + ": " + mess.Text);
    }
}