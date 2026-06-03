package majorFunctionality;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class PNGOrJpgToPNGOrJpg_FolderAllImages {

    static int TARGET_KB = 30;
    static int TARGET_BYTES = TARGET_KB * 1024;

    public static void main(String[] args) throws Exception {

        String inputFolder = "/home/apmosys/Videos/Monitoring & Floor Automation/Img_Compress/BNK_Yes RIB Bill Payment Section Web"; // change path

        compressFolder(inputFolder);
    }

    public static void compressFolder(String inputFolderPath) throws Exception {

        File inputFolder = new File(inputFolderPath);

        if (!inputFolder.exists() || !inputFolder.isDirectory()) {
            System.out.println("Invalid folder path");
            return;
        }

        File outputFolder = new File(inputFolder, "CompressNewImages");
        outputFolder.mkdirs();

        File[] files = inputFolder.listFiles();

        for (File file : files) {

            if (file.isFile() && isImage(file)) {

                File outputFile = new File(
                        outputFolder,
                        file.getName().replaceAll("\\.(png|jpg|jpeg)$", "_compressed.png")
                );

                compressImage(file, outputFile);

                System.out.println("Compressed: " + file.getName());
            }
        }

        System.out.println("All images compressed in folder: " + outputFolder.getAbsolutePath());
    }

    private static boolean isImage(File file) {

        String name = file.getName().toLowerCase();

        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
    }

    private static void compressImage(File inputFile, File outputFile) throws Exception {

        BufferedImage image = ImageIO.read(inputFile);

        byte[] bestData = compressWithQualitySearch(image);

        double scale = 0.9;

        while (bestData == null || bestData.length > TARGET_BYTES) {

            image = resize(image, scale);

            bestData = compressWithQualitySearch(image);

            if (image.getWidth() < 200 || image.getHeight() < 200) {
                break;
            }
        }

        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(bestData);
        fos.close();
    }

    private static byte[] compressWithQualitySearch(BufferedImage image) throws Exception {

        float low = 0.1f;
        float high = 0.95f;

        byte[] bestData = null;

        while (low <= high) {

            float mid = (low + high) / 2;

            byte[] data = compressJPEG(image, mid);

            if (data.length <= TARGET_BYTES) {
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
