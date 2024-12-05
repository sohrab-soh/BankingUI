<html >
<body>
<p>
To build the java executable open the terminal and navigate to project directory and  run the following command:<br>
[mvn clean compile assembly:single]<br>
To run the generated executable file, run the following command:<br>
[java -jar target/BankingUI-1.0-SNAPSHOT-jar-with-dependencies.jar]<br>
Once you run this command, you should see the following output:<br>
<br>
Missing required subcommand<br>
Usage:  [-hV] [COMMAND]<br>
This is Azkivam CLI<br>
  -h, --help      Show this help message and exit.<br>
  -V, --version   Print version information and exit.<br>
Commands:<br>
  createAccount   Defines a new Account<br>
  deposit         Adds funds to the account balance<br>
  getAccountInfo  Retrieve an account<br>
  transfer        Moves funds from one account to another<br>
  withdraw        Withdraw a specified amount from the account balance<br>
  <br>
for example to run withdraw command use folowing command:<br>
[java -jar target/BankingUI-1.0-SNAPSHOT-jar-with-dependencies.jar --accountId=1 --amount=200000]<br>
</p>
</body>
</html>
