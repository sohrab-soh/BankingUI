To build the java executable open the terminal and navigate to project directory and  run the following command:
[mvn clean compile assembly:single]
To run the generated executable file, run the following command:
[java -jar target/BankingUI-1.0-SNAPSHOT-jar-with-dependencies.jar]
Once you run this command, you should see the following output:

Missing required subcommand
Usage:  [-hV] [COMMAND]
This is Azkivam CLI
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  createAccount   Defines a new Account
  deposit         Adds funds to the account balance
  getAccountInfo  Retrieve an account
  transfer        Moves funds from one account to another
  withdraw        Withdraw a specified amount from the account balance
  
for example to run withdraw command use folowing command:
[java -jar target/BankingUI-1.0-SNAPSHOT-jar-with-dependencies.jar --accountId=1 --amount=200000]
