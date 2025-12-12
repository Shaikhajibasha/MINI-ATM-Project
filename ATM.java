import java.io.*;
import java.util.Scanner;
class InvalidLoginException extends Exception
{
    public InvalidLoginException(String message)
	{
        super(message);
    }
}
class BankAccount
{
    private double balance;

    public BankAccount(double balance)
	{
        this.balance = balance;
    }
    public void deposit(double amount)
	{
        balance += amount;
    }
    public boolean withdraw(double amount)
	{
        if (amount <= balance)
		{
            balance -= amount;
            return true;
        }
        return false;
    }
    public double getBalance()
	{
        return balance;
    }
}
class UserAuthentication
{
    private final String userId = "haji";
    private final String pin = "558";
    public boolean login(String uid, String upin)
	{
        return uid.equals(userId) && upin.equals(pin);
    }
}
class ATM
{
    private static double loadBalance()
	{
        try
		{
            File file = new File("balance.txt");
            if (file.exists())
			{
                Scanner sc = new Scanner(file);
                double bal = Double.parseDouble(sc.nextLine());
                sc.close();
                return bal;
            }
        }
		catch (Exception e)
		{
            System.out.println("Error reading balance file. Starting with default balance.");
        }
        return 5000.0;
    }
    private static void saveBalance(double balance)
	{
        try
		{
            FileWriter fw = new FileWriter("balance.txt");
            fw.write(String.valueOf(balance));
            fw.close();
        } 
		catch (IOException e)
		{
            System.out.println("Error saving balance!");
        }
    }
    public static void main(String[] args)
	{
        Scanner sc = new Scanner(System.in);
        UserAuthentication auth = new UserAuthentication();
        BankAccount account = new BankAccount(loadBalance());
        boolean loggedIn = false;
		System.out.println("\n=== MINI-ATM==");
        while (!loggedIn)
		{
            try
			{
                System.out.print("Enter User ID: ");
                String id = sc.nextLine();
                System.out.print("Enter PIN: ");
                String pin = sc.nextLine();
                if (!auth.login(id, pin))
				{
                    throw new InvalidLoginException("Invalid Login! Try Again.\n");
                }
                System.out.println("Login Successful!\n");
                loggedIn = true;
            }
			catch (InvalidLoginException e)
			{
                System.out.println(e.getMessage());
            }
        }
        while (true)
		{
            System.out.println("\n=== ATM MENU ===");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            switch (choice)
			{
                case 1:
                    System.out.println("Current Balance: " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double deposit = sc.nextDouble();
                    account.deposit(deposit);
                    System.out.println("Deposited Successfully!");
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdraw = sc.nextDouble();
                    if (account.withdraw(withdraw))
                        System.out.println("Withdrawal Successful!");
                    else
                        System.out.println("Insufficient Balance!");
                    break;
                case 4:
                    saveBalance(account.getBalance());
                    System.out.println("Balance saved successfully.");
                    System.out.println("Thank you for using ATM!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid Choice!\nEnter Again:");
            }
        }
    }
}
