package com.example.mediaplayerjavafx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MediaPlayerController implements Initializable{

    private String path;
    private MediaPlayer mediaplayer;

    @FXML
    private MediaView mediaView;



    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider progressBar;

    @FXML
    private Label label;

    @FXML
    private StackPane pane;

    @FXML
    private  void chooseFileMethod(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if(path != null){
            Media media = new Media(path);
            mediaplayer = new MediaPlayer(media);

            mediaView.setMediaPlayer(mediaplayer);


            // creating binding

            DoubleProperty widthProp = mediaView.fitWidthProperty();
            DoubleProperty heightprop = mediaView.fitHeightProperty();

            widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            heightprop.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));


            volumeSlider.setValue(mediaplayer.getVolume()*100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaplayer.setVolume(volumeSlider.getValue()/100);
                }
            });

            mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                    progressBar.setValue(newValue.toSeconds());
                }
            });


            progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaplayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
                }
            });

            progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaplayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
                }
            });

            mediaplayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    javafx.util.Duration total = media.getDuration();
                    progressBar.setMax(total.toSeconds());
                }
            });

            mediaplayer.play();


        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void pausevideo(ActionEvent event){
        mediaplayer.pause();
    }
    public void stopVideo(ActionEvent event){
        mediaplayer.stop();
    }
    public void playVideo(ActionEvent event){
        mediaplayer.play();
        mediaplayer.setRate(1);
    }

    public void skip10(ActionEvent event){
        mediaplayer.seek(mediaplayer.getCurrentTime().add(javafx.util.Duration.seconds(10)));

    }

    public void FurtherSpeedVideo(ActionEvent event){
        mediaplayer.setRate(2);
    }

    public void back10(ActionEvent event){
        mediaplayer.seek(mediaplayer.getCurrentTime().add(javafx.util.Duration.seconds(10)));
    }
    public void furtherSlowdownVideo(ActionEvent event){
        mediaplayer.setRate(0.5);
    }

}

