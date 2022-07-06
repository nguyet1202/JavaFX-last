package com.example.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    Label lbName = new Label("Enter name:");
    TextField tfName = new TextField();


    Label lbImage = new Label("Enter image:");
    TextField tfImage = new TextField();


    Label lbAuthor = new Label("Enter Author:");
    TextField tfAuthor = new TextField();

    Label lbCategory = new Label("Enter Category:");
    TextField tfCategory = new TextField();


    Label lbPrice = new Label("Enter price:");
    TextField tfPrice = new TextField();


    Label lbQuantityNow = new Label("Enter quantityNow:");
    TextField tfQuantityNow = new TextField();



    @Override
    public void start(Stage stage) throws IOException {

        boolean status = false;

        DBConnection connection = new DBConnection();

        VBox root = new VBox();

        VBox booksRoot = new VBox();

        HBox vInsertbooks = new HBox();

        Button btnAdd = new Button("Add");
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                connection.insertBook(new Book(tfName.getText(),tfImage.getText(),
                        tfAuthor.getText(),tfCategory.getText(),Float.parseFloat(tfPrice.getText()),
                        Integer.parseInt(tfQuantityNow.getText())));
                getThenDisplayBooks(booksRoot, connection);
            }
        });

        Button btnEdit = new Button("Update");
        btnEdit.setOnAction(event -> {
            connection.updateBook(new Book(tfName.getText(),tfImage.getText(),
                    tfAuthor.getText(),tfCategory.getText(),Float.parseFloat(tfPrice.getText()),
                    Integer.parseInt(tfQuantityNow.getText()),Integer.parseInt(tfName.getId())));
            getThenDisplayBooks(booksRoot, connection);
        });

        vInsertbooks.getChildren().addAll(lbName, tfName, lbImage, tfImage ,lbAuthor, tfAuthor,lbCategory,tfCategory,
                lbPrice,tfPrice,lbQuantityNow, btnAdd, btnEdit);

        root.getChildren().addAll(vInsertbooks, booksRoot);

        getThenDisplayBooks(booksRoot, connection);
        Scene scene = new Scene(root, 1500, 800);
        stage.setTitle("Welocome !");
        stage.setScene(scene);
        stage.show();
    }

    void DisplayBooks(DBConnection connection, VBox root, List<Book> books) {
        root.getChildren().clear();
        for (int i = 0; i < books.size(); i++) {
            final int finialI = i;
            HBox bookBox = new HBox();
            Label lbId = new Label("" + books.get(i).id);
            Label lbName = new Label(books.get(i).name);
            Image image = new Image(""+books.get(i).image);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            imageView.setPreserveRatio(true);



            Label lbAuthor= new Label(""+books.get(i).author);
            Label lbCategory = new Label("" + books.get(i).Category);
            Label lbPrice= new Label(""+books.get(i).price);
            Label lbQuantityNow = new Label("" + books.get(i).quantityNow);


            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(actionEvent -> {
                System.out.println("Click delete " + books.get(finialI).id);

                connection.deleteBook(books.get(finialI).id);
                getThenDisplayBooks(root, connection);



            });
            Button btnUpdate = new Button("Update");
            btnUpdate.setOnAction(actionEvent->{
                tfName.setText(String.valueOf((books.get(finialI).name)));
                tfImage.setText(String.valueOf((books.get(finialI).image)));
                tfName.setId(""+books.get(finialI).id);
                tfAuthor.setText(String.valueOf((books.get(finialI).author)));
                tfCategory.setText(String.valueOf((books.get(finialI).Category)));
                tfPrice.setText(String.valueOf((books.get(finialI).price)));
                tfQuantityNow.setText(String.valueOf((books.get(finialI).quantityNow)));

            });
            bookBox.setSpacing(90);
            bookBox.getChildren().addAll(lbId, lbName, imageView,lbAuthor,lbCategory,lbPrice,lbQuantityNow, btnDelete, btnUpdate);
            root.getChildren().add(bookBox);
        }
    }

    private void getThenDisplayBooks(VBox root, DBConnection connection) {
        List<Book> books = connection.getBook();
        DisplayBooks(connection, root, books);
    }

    public static void main(String[] args) {
        launch();
    }
}