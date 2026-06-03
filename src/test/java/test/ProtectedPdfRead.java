package test;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ProtectedPdfRead {

	public static void main(String[] args) {
		 try {
	            File file = new File("/home/apmosys/Downloads/Interest_Statement_Report_1748849729081_protected.pdf");
	            String password = "Bhaba@123";

	            PDDocument document = PDDocument.load(file, password);

	            PDFTextStripper pdfStripper = new PDFTextStripper();
	            String text = pdfStripper.getText(document);

	            System.out.println("PDF Content:\n" + text);

	            document.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
