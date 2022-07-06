package com.example.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private Connection connection;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/test",
                    "root",
                    "");
            System.out.println("Thanh cong nha");
        } catch (SQLException e) {
            connection = null;
            System.out.println(e);
        }
    }

    List<Book> getBook() {
        ArrayList<Book> Book = new ArrayList<>();
        try {
            ResultSet result = connection.prepareStatement("SELECT * from books").executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String image = result.getString("image");
                String author = result.getString("author");
                String Category = result.getString("Category");
                float price = result.getFloat("price");
                int quantityNow = result.getInt("quantityNow");
                System.out.println("=====");
                System.out.println(id);
                System.out.println(name);
                System.out.println(image);
                System.out.println(author);
                System.out.println(Category);
                System.out.println(price);
                System.out.println(quantityNow);
                Book.add(new Book(id, name, image, author, Category, price, quantityNow));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Book;
    }
    public void insertBook(Book books){
        String sql = "INSERT INTO books (name, image,author,Category,price,quantityNow) VALUES ('"+books.name+"','"+books.image+"','"+books.author+"','"+books.Category+"','"+books.price+"',"+books.quantityNow+")";
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateBook(Book books){
        String sql = "UPDATE books SET name = '"+ books.name +"', image = '"+books.image+"', author = '"
                +books.author+"', Category = '"+books.Category+"',price='"+books.price+"',quantityNow='"+books.quantityNow
                +"' WHERE id = "+ books.id;
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(int id){
        String sql="DELETE FROM books WHERE id="+id;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

