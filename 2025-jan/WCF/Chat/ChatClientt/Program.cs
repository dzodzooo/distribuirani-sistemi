using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ChatClientt.Chat;
using System.Net.Http.Headers;
using System.ServiceModel;
using System.ServiceModel.Security.Tokens;


namespace ChatClientt { 
    class Program
    {
        static void SendMessage(string username, ChatServiceClient client)
        {
            Console.WriteLine();
            Console.WriteLine("Who do you wish to contact?");
            string receiver=Console.ReadLine();
            Console.WriteLine("What do you want to send?");
            string message = Console.ReadLine();
            client.SendMessage(receiver, new Message { Username=username, Timestamp=DateTime.Now, Text=message});
        }

        static void Register(string username, ChatServiceClient client)
        {
            client.Register(username);
        }
        
        static void Main(string[] args)
        {

            IChatServiceCallback cb = new ChatCallback();
            ChatServiceClient client = new ChatServiceClient(new InstanceContext(cb));
            Console.WriteLine("Welcome to Chat! What's your username?");
            string username = Console.ReadLine();
            Register(username, client);
            while (true)
            {
                Console.WriteLine();
                Console.WriteLine("Press m to send a message");
                Console.WriteLine("Press q to quit");
                string input = Console.ReadLine();
                switch (input)
                {
                    case "m": SendMessage(username, client); break;
                    case "q": return;
                    default: break;
                }
            }
        }
    }
}
