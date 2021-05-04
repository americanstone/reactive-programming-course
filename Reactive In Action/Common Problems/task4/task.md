1. send data to a server using the given client
2. MAX amount of sent buffers MUST be less or equals to 50 per request
3. frequency of client#send invocation MUST be not often than once per 500 Milliseconds
4. delivered results MUST be ordered
5. in case if send operation take more than 1 second it MUST be considered as hanged and
 be restarted