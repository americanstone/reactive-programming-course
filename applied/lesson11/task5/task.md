Main) Write data to both databases
1) Ensure Transaction is rolled back in case of failure
2) Ensure All transactions are rolled back in case any of written operations has failed
3) Ensure Transaction lasts less than 1 sec
4) Retry connection opening if it has failed (connection opening is retryable operation)