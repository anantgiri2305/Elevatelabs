package com.library;

import java.util.HashMap;
import java.util.Map;

public class Library {
    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, User> users = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
        System.out.println("Book added: " + book);
    }

    public void addUser(User user) {
        users.put(user.getUserId(), user);
        System.out.println("User added: " + user);
    }

    public void issueBook(int bookId, int userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);

        if (book == null) {
            System.out.println("Book not found.");
        } else if (user == null) {
            System.out.println("User not found.");
        } else if (book.isIssued()) {
            System.out.println("Book is already issued.");
        } else {
            book.setIssued(true);
            user.borrowBook(book);
            System.out.println("Book issued: " + book + " to " + user);
        }
    }

    public void returnBook(int bookId, int userId) {
        Book book = books.get(bookId);
        User user = users.get(userId);

        if (book == null || user == null) {
            System.out.println("Invalid book or user ID.");
        } else if (!user.getBorrowedBooks().contains(book)) {
            System.out.println(user + " has not borrowed " + book);
        } else {
            book.setIssued(false);
            user.returnBook(book);
            System.out.println("Book returned: " + book + " by " + user);
        }
    }
}

