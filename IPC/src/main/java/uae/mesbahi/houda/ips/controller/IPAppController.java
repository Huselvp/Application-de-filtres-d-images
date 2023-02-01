package uae.mesbahi.houda.ips.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import uae.mesbahi.houda.ips.models.Image;
import uae.mesbahi.houda.ips.service.BlurClient;
import uae.mesbahi.houda.ips.service.EdgeDetectionClientService;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;

public class IPAppController {

    private final EdgeDetectionClientService rmiClientService =
            new EdgeDetectionClientService();

    @FXML
    public ImageView withoutFilter;

    @FXML
    public ImageView withFilter;

    @FXML
    public TextArea log;

    private Image image;
    private Image imageWithFilter;
    private String fileName;
    private String fileExt;
    private final FileChooser fileChooser = new FileChooser();



    @FXML
    protected void onLoadImageClick() throws IOException {
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(
                        "Image files (*.jpg, *.jpeg, *.png)",
                        "*.jpeg" ,"*.jpg", "*.png");

        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            image = new Image(selectedFile.getAbsolutePath());
            fileName = selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf("."));
            fileExt = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            javafx.scene.image.Image imageFx = image.toFXImage();
            List.of(withoutFilter, withFilter).forEach(imageView -> {
                imageView.setImage(imageFx);
                centerImage(imageView);
            });
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Erreur: Aucun fichier choisi").show();
        }
    }

    @FXML
    protected void onSobelButtonClick() throws NotBoundException, IOException {
        log("Applying Sobel to image...");
        edgeDetectionClientClick(rmiClientService.applySobel(image));
        log("Sobel applied to image.");

    }

    @FXML
    protected void onPrewittButtonClick() throws NotBoundException, IOException {
        log("Applying Prewitt to image...");
        edgeDetectionClientClick(rmiClientService.applyPrewitt(image));
        log("Prewitt applied to image.");
    }

    private void edgeDetectionClientClick(byte[] edgeImg) {
        log("Applying edge detection to image...");
        try {
            imageWithFilter = new Image(edgeImg);
            withFilter.setImage(imageWithFilter.toFXImage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onSaveImageClick() {
        try {
            File selectedDir = new DirectoryChooser().showDialog(null);
            imageWithFilter.write(
                    new File(selectedDir.getAbsoluteFile() + "/" + fileName + "_filter." + fileExt));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Takes care of centering images in the image view.
    private void centerImage(ImageView imageView) {
        javafx.scene.image.Image img = imageView.getImage();
        if (img != null) {
            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = Math.min(ratioX, ratioY);

            imageView.setX((imageView.getFitWidth() - img.getWidth() * reducCoeff) / 2);
            imageView.setY((imageView.getFitHeight() - img.getHeight() * reducCoeff) / 2);

        }
    }

    private void blurClientClick(Image blurImg) {
        log("Blur image...");
        imageWithFilter = blurImg;
        try {
            log("\n" + "Blur image done.");
            withFilter.setImage(imageWithFilter.toFXImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMedianButtonClick() {
        log("Applying median filter...");
        BlurClient blurClient = new BlurClient();
        blurClientClick(blurClient.applyMedianBlur(image));
        blurClient.close();
        log("Applying median filter done.");
    }

    public void onGaussianButtonClick() {
        log("Applying gaussian filter...");
        BlurClient blurClient = new BlurClient();
        blurClientClick(blurClient.applyGaussianBlur(image));
        blurClient.close();
        log("Applying gaussian filter done.");
    }

    public void onMeanButtonClick() {
        log("Applying mean filter...");
        BlurClient blurClient = new BlurClient();
        blurClientClick(blurClient.applyMeanBlur(image));
        blurClient.close();
        log("Applying mean filter done.");
    }

    public void onLaplaceButtonClick() {
        try {
            edgeDetectionClientClick(rmiClientService.applyLaplace(image));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        Platform.runLater(() -> {
            log.appendText(message + "\n");
        });
    }

}
