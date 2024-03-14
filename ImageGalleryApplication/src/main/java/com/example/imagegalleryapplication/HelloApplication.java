package com.example.imagegalleryapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    // Declaring and initializing currentImage to display
    private int currentImage = 0;

    @Override
    public void start(Stage stage) {
        // Setting up row and column for images to be in a grid form
        int row = 0;
        int column = 0;
        int thumbnails = images.length;

        stage.setTitle("..GalleryApp..");
        // Creatting VBox to hold thumbnail images in a grid form
        VBox root = new VBox();
        root.setSpacing(6);
        root.setAlignment(Pos.CENTER);

        // HBox for setting up row images alignment
        HBox rowBox = new HBox();
        rowBox.setAlignment(Pos.CENTER);
        rowBox.setSpacing(6);

        // Adding thumbnails to the grid using a for loop
        for (int thumbnail = 0; thumbnail < thumbnails; thumbnail++) {
            ImageView thumbnail1 = getThumbnail(images[thumbnail], thumbnail);
            rowBox.getChildren().add(thumbnail1);
            column++;

            // After adding 3 thumbnails in a row,then add the row to the root VBox
            if (column == 3 || thumbnail == thumbnails - 1) {
                root.getChildren().add(rowBox);
                rowBox = new HBox();
                rowBox.setAlignment(Pos.CENTER);
                rowBox.setSpacing(5);
                column = 0;
                row++;
            }
        }

        // Navigation controls to go forth and backwards to view images
        Button prevBtn = new Button("Previous");
        prevBtn.setOnAction(e -> showPreviousImage());
        Button nextBtn = new Button("Next");
        nextBtn.setOnAction(e -> showNextImage());
        HBox navigationBox = new HBox(30, prevBtn, nextBtn);
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.getStyleClass().add("navigation-box");

        // Wrapping the grid pane in a scroll pane to be able to scroll down to view all images
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Layout for the scene and adjustments for scrollpane as well as navigationBox
        BorderPane layout = new BorderPane();
        layout.setCenter(scrollPane);
        layout.setBottom(navigationBox);

        // creating scene to load CSS styling
        Scene scene = new Scene(layout, 500, 500);
        scene.getRoot().getStyleClass().add("root"); // Add root class
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    // Method to create thumbnail ImageView and setting the size of images
    private ImageView getThumbnail(String imagePath, int index) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView thumbnail = new ImageView(image);
        thumbnail.setFitWidth(150);
        thumbnail.setFitHeight(150);
        thumbnail.getStyleClass().add("thumbnail");

        // Setting on click event for each thumbnail to display full image
        thumbnail.setOnMouseClicked(e -> displayFullImage(images[index]));

        return thumbnail;
    }

    // Method to display full image and
    // Including scale factor to give images its size so that when clicking to view full image ,
    // so the image won't loose its format
    private void displayFullImage(String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        double scaleFactor = Math.min(1.0, Math.min(500 / image.getWidth(), 500 / image.getHeight()));
        ImageView fullImageView = new ImageView(image);
        fullImageView.setPreserveRatio(true);
        fullImageView.setFitWidth(image.getWidth() * scaleFactor);
        fullImageView.setFitHeight(image.getHeight() * scaleFactor);

        // Creating an ImageView for the backward arrow icon button and setting its size
        Image backwardArrowImage = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView backwardArrowImageView = new ImageView(backwardArrowImage);
        backwardArrowImageView.setFitWidth(20);
        backwardArrowImageView.setFitHeight(20);

        // Setting the action for the backward arrow icon button and closing image after view
        backwardArrowImageView.setOnMouseClicked(e -> {
            Stage stage = (Stage) backwardArrowImageView.getScene().getWindow();
            stage.close();
        });

        // Create a VBox to hold the full image and the backward arrow icon for returning to thumnails
        VBox fullImageBox = new VBox(backwardArrowImageView, fullImageView);
        fullImageBox.setAlignment(Pos.TOP_LEFT);
        fullImageBox.setPadding(new Insets(10));

        // Creation of a scene and stage for the full image to be displayed
        Scene scene = new Scene(fullImageBox);
        Stage fullImageStage = new Stage();
        fullImageStage.setScene(scene);
        fullImageStage.show();
    }

    // Method to show previous image when clicking on it
    private void showPreviousImage() {
        currentImage = (currentImage - 1 + images.length) % images.length;
        displayFullImage(images[currentImage]);
    }

    // Method to show next image when clicking on it
    private void showNextImage() {
        currentImage = (currentImage + 1) % images.length;
        displayFullImage(images[currentImage]);
    }

    // Array of image paths to display
    String[] images = {
            "/images/comp1.jpg",
            "/images/comp2.jpg",
            "/images/comp3.jpg",
            "/images/comp4.jpg",
            "/images/comp5.jpg",
            "/images/comp6.jpg",
            "/images/comp7.jpg",
            "/images/comp8.jpg",
            "/images/comp9.jpg",
            "/images/comp10.jpg",
            "/images/comp11.jpg",
            "/images/comp12.jpg",
    };
    public static void main(String[] args) {
        launch(args);
    }
}
