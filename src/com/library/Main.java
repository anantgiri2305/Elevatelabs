package com.library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book(1, "1984", "George Orwell");
        Book book2 = new Book(2, "The Hobbit", "J.R.R. Tolkien");

        User user1 = new User(101, "Alice");
        User user2 = new User(102, "Bob");

        library.addBook(book1);
        library.addBook(book2);

        library.addUser(user1);
        library.addUser(user2);

        library.issueBook(1, 101); // Alice borrows 1984
        library.issueBook(2, 102); // Bob borrows The Hobbit
        library.returnBook(1, 101); // Alice returns 1984
    }
}

