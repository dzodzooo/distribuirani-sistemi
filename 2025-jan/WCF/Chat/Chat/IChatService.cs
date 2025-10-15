using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace Chat
{
	// NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
	[ServiceContract(CallbackContract = typeof(IChatCallback))]
	public interface IChatService
	{
		[OperationContract(IsOneWay =true)]
		void SendMessage(string username, Message mess);
		[OperationContract(IsOneWay = true)]
		void Register(string username);
	}


	// Use a data contract as illustrated in the sample below to add composite types to service operations.
	[DataContract]
	public class Message
	{
		string text;
		string username;
		DateTime timestamp;

		[DataMember]
		public string Text { get; set; }
		
		[DataMember]
		public string Username { get; set; }

		[DataMember]
		public DateTime Timestamp { get; set; }
	}
}
