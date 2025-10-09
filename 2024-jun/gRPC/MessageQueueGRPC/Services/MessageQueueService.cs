using Grpc.Core;
using Google.Protobuf.WellKnownTypes;
namespace MessageQueueGRPC.Services;
public class MessageQueueService : MessageQueueSvc.MessageQueueSvcBase {
    
    public override Task<Empty> SendMessage(Message message, ServerCallContext context)
    {
        DatabaseService db = DatabaseService.getInstance();
        db.addMessage(message);

        return Task.FromResult(new Empty());
    }

    public override Task<Message> DeleteMessage(Id messageId, ServerCallContext context) {
        DatabaseService db = DatabaseService.getInstance();
        Message message = db.removeMessage(messageId);
        return Task.FromResult(message);
    }

    public override async Task ListMessages(Empty empty, IServerStreamWriter<Message> responseStream, ServerCallContext context)
    {
        List<Message> messages = DatabaseService.getInstance().GetMessages();
        foreach (Message message in messages)
        {
            await responseStream.WriteAsync(message);
            await Task.Delay(TimeSpan.FromSeconds(1));
        }
    }
}

