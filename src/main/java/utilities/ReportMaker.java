package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.time.ZonedDateTime;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportMaker {

	public ReportMaker() {
		super();
	}

	public void createPdf(String filePath, String ImagePath) throws DocumentException, IOException {
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filePath));
		// step 3
		document.open();
		// step 4
		Paragraph paragraph = new Paragraph();
		paragraph.add("Report Created on " + ZonedDateTime.now().toString() + "\n" );
		
		Image image =Image.getInstance((new File(ImagePath).toURI().toURL()));
	
		image.scaleToFit((float)(document.getPageSize().getWidth()*0.84),document.getPageSize().getHeight()-200);
		image.setAlignment(Image.LEFT | Image.TEXTWRAP);
		
		for(int i=0; i< 10; i++) {
			paragraph.add(new Chunk("\n\n\n"));
			paragraph.add(new Chunk(image, 0, 0, true));
		}
		document.add(paragraph);
		// step 5
		document.close();
	}
	
}
