module com.example.imagegalleryapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.imagegalleryapplication to javafx.fxml;
    exports com.example.imagegalleryapplication;
}