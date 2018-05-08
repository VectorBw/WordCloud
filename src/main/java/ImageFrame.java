import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageFrame extends JFrame {
    public ImageFrame(String imagePath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(imagePath)));
        this.setBounds(700,100,bufferedImage.getWidth()+20,bufferedImage.getHeight()+50);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icon  = new ImageIcon(imagePath);
        icon = new ImageIcon(icon.getImage().getScaledInstance(bufferedImage.getWidth(),bufferedImage.getHeight(), Image.SCALE_DEFAULT));
        JLabel image = new JLabel();
        image.setBounds(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
        image.setIcon(icon);
        this.add(image);
//        this.revalidate();
        this.setVisible(true);

    }
}
