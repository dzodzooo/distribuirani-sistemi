using System.ServiceModel;

namespace Chat
{
    [ServiceContract]
    public interface IChatCallback
    {
        [OperationContract(IsOneWay =true, Name = "CallbackSendMessage")]
        void SendMessage(Message mess);
    }
}