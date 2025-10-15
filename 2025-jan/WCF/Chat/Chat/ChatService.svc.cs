using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace Chat
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode=InstanceContextMode.Single)]
    public class ChatService : IChatService
    {
        Dictionary<string, IChatCallback> users;

        public ChatService()
        {
           this.users = new Dictionary<string, IChatCallback>();
        }
        public void Register(string username)
        {
            this.users.Add(username, OperationContext.Current.GetCallbackChannel<IChatCallback>());
        }

        public void SendMessage(string username, Message mess)
        {
            if (this.users.ContainsKey(username))
            {
                IChatCallback user = this.users[username];
                user.SendMessage(mess);
            }
        }
    }
}
