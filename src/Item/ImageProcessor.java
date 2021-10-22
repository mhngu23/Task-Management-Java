package Item;

import Item.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageProcessor {

    public byte[] imageToByteArray(File file) {
        byte[] fileContent;
        {
            try {
                try {
                    fileContent = Files.readAllBytes(file.toPath());
                    return fileContent;
                }catch (NullPointerException a){
                    return null;
                }
            } catch (IOException e) {
                return null;
            }
        }
    }

    public Image profileImageProcessing(User user) throws IOException {
        BufferedImage profile_picture = ImageIO.read(new ByteArrayInputStream(user.getProfile_picture()));
        Image profile_picture_processed = SwingFXUtils.toFXImage(profile_picture, null);
        return profile_picture_processed;
    }
}
