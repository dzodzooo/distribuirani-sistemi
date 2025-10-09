using System.Threading.Tasks;
using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Grpc.Net.Client;
using MessageQueueGRPC;

using var channel = GrpcChannel.ForAddress("http://localhost:5006");
var client = new MessageQueueSvc.MessageQueueSvcClient(channel);

client.SendMessage(new Message { Id = 0, Text = "Hello0" });
client.SendMessage(new Message { Id = 1, Text = "Hello1" });
client.SendMessage(new Message { Id = 2, Text = "Hello2" });
var responseStream = client.ListMessages(new Empty());
while (await responseStream.ResponseStream.MoveNext())
{
    Console.WriteLine(responseStream.ResponseStream.Current.Text);
}
Console.WriteLine("NOW DELETING");
client.DeleteMessage(new Id { Id_ = 0 });
responseStream = client.ListMessages(new Empty());
while (await responseStream.ResponseStream.MoveNext())
{
    Console.WriteLine(responseStream.ResponseStream.Current.Text);
}
