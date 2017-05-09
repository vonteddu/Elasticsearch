package com.whitehawk.elastictransportclient;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
/**
 * Created by ruthv on 5/9/2017.
 */
public class ReadPdf {
    public static void main(String args[]) {
        try {
            //Create PdfReader instance.
            PdfReader pdfReader = new PdfReader("C:\\Users\\ruthv\\testFile.pdf");
            // Get the number of pages in pdf.
            int pages = pdfReader.getNumberOfPages();
            //Iterate the pdf through pages.
            for(int i=1; i<=pages; i++)
            {
                //Extract the page content using PdfTextExtractor.
                String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);
                //Print the page content on console.
                System.out.println("Content on Page " + i + ": " + pageContent);
            }   //Close the PdfReader.
             pdfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}