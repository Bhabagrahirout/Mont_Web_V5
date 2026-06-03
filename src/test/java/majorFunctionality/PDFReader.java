package majorFunctionality;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {

	public static void main(String[] args) {

		String propertyValueRDS = "/home/apmosys/Downloads";
		String dataFieldRDS = "HOLIDAY LIST-2026#pdf";
		File folder = new File(propertyValueRDS.trim());
		String actFileName = dataFieldRDS.split("#")[0].toLowerCase();
		String extension = dataFieldRDS.split("#")[1].toLowerCase();
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null && files.length > 0) {
				File latestFile = Arrays.stream(files).filter(File::isFile)
						.max(Comparator.comparingLong(File::lastModified)).orElse(null);

				if (latestFile == null) {
					System.out.println("No files found in the folder.");
					return;
				}

				System.out.println("Latest File found: " + latestFile.getName());

				if (latestFile.getName().toLowerCase().startsWith(actFileName)
						&& latestFile.getName().toLowerCase().endsWith(extension)) {
					System.out.println("<------------ File Matched ------------>");
					canaraBankfilecheck(latestFile);
					latestFile.delete();
					System.out.println("✅ File Deleted successfully");

				} else {
					System.out.println("❌ Latest file name does not start with expected: " + actFileName);
				}
			} else {
				System.out.println("The folder is empty or cannot be accessed.");
			}
		} else {
			System.out.println("Provided path is not a directory.");
		}

	}

	public static void canaraBankfilecheck(File file) {
		try {
			PDDocument document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(document);
//			System.out.println("PDF Content:");
//			System.out.println(text);
//			document.close();
			if (text.equalsIgnoreCase("Name of the State")) {
				System.out.println("PDF verify successfully");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
