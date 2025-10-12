using Grpc.Core;
using Grpc.Net.Client;
using Client;

using var channel = GrpcChannel.ForAddress("http://localhost:5208");
var client = new FibonacciServiceProvider.FibonacciServiceProviderClient(channel);

var responseStream = client.generateFibonacciSequence(new Number{ Value = 13}).ResponseStream;

while(await responseStream.MoveNext()){
	Console.WriteLine(responseStream.Current.Value);
}

