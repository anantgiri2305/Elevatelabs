package task5;

public class BankSimulation {
    public static void main(String[] args) {
        BankAccount acc1 = new BankAccount("1001", "Alice");

        acc1.deposit(500);
        acc1.withdraw(200);
        acc1.deposit(150);

        System.out.println("\nCurrent Balance: $" + acc1.getBalance());
        acc1.printTransactionHistory();
    }
}
