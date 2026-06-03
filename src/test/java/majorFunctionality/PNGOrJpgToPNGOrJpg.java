package majorFunctionality;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class PNGOrJpgToPNGOrJpg {

    static int TARGET_KB = 30;
    static int TARGET_BYTES = TARGET_KB * 1024;

    public static void main(String[] args) throws Exception {

        File input = new File("/home/apmosys/Videos/Monitoring & Floor Automation/Img_Compress/BNK_Yes RIB Bill Payment Section Web/Manage Biller Landing Page_16_10_44.png");   // PNG or JPG
        File output = new File("/home/apmosys/Videos/Monitoring & Floor Automation/Img_Compress/CompressedJpg/Manage Biller Landing Page_16_10_44.png");

        compressImage(input, output, TARGET_BYTES);
    }

    public static void compressImage(File inputFile, File outputFile, int targetBytes) throws Exception {

        BufferedImage image = ImageIO.read(inputFile);

        byte[] bestData = compressWithQualitySearch(image, targetBytes);

        double scale = 0.9;

        while (bestData == null || bestData.length > targetBytes) {

            image = resize(image, scale);

            bestData = compressWithQualitySearch(image, targetBytes);

            if (image.getWidth() < 200 || image.getHeight() < 200) {
                break;
            }
        }

        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(bestData);
        fos.close();

        System.out.println("Final size: " + bestData.length / 1024 + " KB");
    }

    private static byte[] compressWithQualitySearch(BufferedImage image, int targetBytes) throws Exception {

        float low = 0.1f;
        float high = 0.95f;

        byte[] bestData = null;

        while (low <= high) {

            float mid = (low + high) / 2;

            byte[] data = compressJPEG(image, mid);

            if (data.length <= targetBytes) {
                bestData = data;
                low = mid + 0.01f;
            } else {
                high = mid - 0.01f;
            }
        }

        return bestData;
    }

    private static byte[] compressJPEG(BufferedImage image, float quality) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        writer.setOutput(ios);

        writer.write(null, new IIOImage(image, null, null), param);

        ios.close();
        writer.dispose();

        return baos.toByteArray();
    }

    private static BufferedImage resize(BufferedImage original, double scale) {

        int width = (int) (original.getWidth() * scale);
        int height = (int) (original.getHeight() * scale);

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(original, 0, 0, width, height, null);
        g.dispose();

        return resized;
    }
}