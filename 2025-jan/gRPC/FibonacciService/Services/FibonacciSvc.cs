using Grpc.Core;
namespace FibonacciService.Services;

public class FibonacciSvc : FibonacciServiceProvider.FibonacciServiceProviderBase
{
	public async override Task generateFibonacciSequence(Number number, IServerStreamWriter<Number> responseStream, ServerCallContext context){
		int a = 1;
		int b = 1;
		int max = number.Value;
		do {
			int tmp = a + b;
			if(tmp > max) break;
			a = b;
			b = tmp;
			await responseStream.WriteAsync(new Number(){ Value = tmp });
		}
		while(true);
	}
}
