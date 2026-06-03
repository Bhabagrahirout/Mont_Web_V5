package majorFunctionality;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRReading {

	public static void main(String[] args) {

//				getTextFromImage("/usr/share/tesseract-ocr/4.00/tessdata/", "/home/apmosys/Downloads/Screenshot from 2025-03-07 11-19-33.png");
		getTextFromImage("/usr/share/tesseract-ocr/4.00/tessdata/", "/home/apmosys/Downloads/WhatsApp Image 2025-10-31 at 4.11.46 PM.jpeg");

	}

	public static void main1(String[] args) {
//		    	tessarct path may Like
//		    	/usr/share/tesseract-ocr/4.00/tessdata/
//		    	OR
//		    	/usr/share/tesseract-ocr/tessdata/

//		    	String folderPath="/home/apmosys/Pictures/CaptchImage";
//				File folder=new File(folderPath);
//				File[] al=folder.listFiles();

		File imgFile = new File("/home/apmosys/Pictures/CaptchImage/Pmkisan.aspx");
		File[] al = { imgFile };
		for (File f : al) {

			String path = f.getAbsolutePath();
			System.out.println("---------" + path + "--------");
			getTextFromImage("/usr/share/tesseract-ocr/4.00/tessdata/", path);

		}

	}

	public static String getTextFromImage(String tessDataPath, String imagePath) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imagePath));
			if (img == null) {
				System.err.println("Error: Unable to read image file.");
				return "";
			}
		} catch (IOException e) {
			System.err.println("Error reading image file: " + e.getMessage());
			return "";
		}

		Tesseract it = new Tesseract();
		it.setDatapath(tessDataPath);
		it.setLanguage("eng");

		String text = null;
		try {
			text = it.doOCR(img);
		} catch (TesseractException e) {
			System.err.println("Error during OCR: " + e.getMessage());
			return "";
		}

		System.out.println("========================");
		System.out.println(text);

		return text;
	}

}
