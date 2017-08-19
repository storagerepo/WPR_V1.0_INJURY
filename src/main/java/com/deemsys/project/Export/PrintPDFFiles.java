package com.deemsys.project.Export;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintJobAttributeSet;
import javax.print.attribute.PrintJobAttributeSet;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deemsys.project.common.InjuryProperties;
@Service
public class PrintPDFFiles {

	@Autowired
	InjuryProperties injuryProperties;
	
	public void printPDFFiles(PrintService printService,String printerName,String fileName) throws PrinterException, IOException, PrintException{
		
		if(printService!=null){
				/*// Figure out what type of file we're printing
		    	    DocFlavor flavor = getFlavorFromFilename(file);
		    	    // Open the file
		    	    InputStream in = new FileInputStream("D:/Works/Learning/"+file);
		    	    // Create a Doc object to print from the file and flavor.
		    	    SimpleDoc doc = new SimpleDoc(in, flavor, null);
		    	    // Create a print job from the service
		    	    DocPrintJob job = printService.createPrintJob();
		    	    
		    	    // Monitor the print job with a listener
		    	    job.addPrintJobListener(new PrintJobAdapter() {
		    	      public void printJobCompleted(PrintJobEvent e) {
		    	        System.out.println("Print job complete");
		    	        System.exit(0);
		    	      }

		    	      public void printDataTransferCompleted(PrintJobEvent e) {
		    	        System.out.println("Document transfered to printer");
		    	      }

		    	      public void printJobRequiresAttention(PrintJobEvent e) {
		    	        System.out.println("Print job requires attention");
		    	        System.out.println("Check printer: out of paper?");
		    	      }

		    	      public void printJobFailed(PrintJobEvent e) {
		    	        System.out.println("Print job failed");
		    	      //  System.exit(1);
		    	      }
		    	    });
		    	job.print(doc, null);*/
		    	System.out.println("Printer Found");
		    	PDDocument document = PDDocument.load(new File("D:/Works/Learning/Report Location.pdf"));
		    	PrinterJob job = PrinterJob.getPrinterJob();
			    job.setPageable(document);
			    job.setPrintService(printService);
			    job.print();
			    document.close();
		}else{
	    	System.out.println("Printer Not Found");
	    }
	    
	}
	
	
	public String doPrintService(String fileName) throws PrinterException, IOException, PrintException {
		
		String printJobResponse="";
		String printerName=injuryProperties.getProperty("MICROSOFT_DEFAULT_VIEWER");
		// Default Printer Look up
		//PrintService printServices = PrintServiceLookup.lookupDefaultPrintService();
		// Set Attribute to printing job
		//PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		PrintJobAttributeSet printJobAttributeSet = new HashPrintJobAttributeSet();
		// List Of Printer Look up
	    PrintService[] printServiceses = PrintServiceLookup.lookupPrintServices(null, printJobAttributeSet);
	    // Match With Corresponding printer name
	    for (PrintService printService : printServiceses) {
	    	
	    	AttributeSet attributeSet=printService.getAttributes();
	    	
	    	// Printer Name Checking
	    	if (printService.getName().trim().equals(printerName)) {
	        	System.out.println("Printer Found");
	        	String printerAcceptingJobs=attributeSet.get(PrinterIsAcceptingJobs.class).toString();
	        	// Printer Job Accepting Status
	        	if(printerAcceptingJobs.equals(PrinterIsAcceptingJobs.ACCEPTING_JOBS.toString())){
	        		System.out.println("Call Printer");
	        		this.printPDFFiles(printService, printerName,fileName);
	        	}else if(printerAcceptingJobs.equals(PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS.toString())){
	        		System.out.println("Not Accepting Jobs");
	        		printJobResponse="Not Accepting Jobs";
	        	}
	        	
	            
	        }else{
	        	System.out.println("Printer Not Found");
	        }
	    }
	    return printJobResponse;
	}
	
	// extension of the filename.
	  public static DocFlavor getFlavorFromFilename(String filename) {
	    String extension = filename.substring(filename.lastIndexOf('.') + 1);
	    extension = extension.toLowerCase();
	    if (extension.equals("gif"))
	      return DocFlavor.INPUT_STREAM.GIF;
	    else if (extension.equals("jpeg"))
	      return DocFlavor.INPUT_STREAM.JPEG;
	    else if (extension.equals("jpg"))
	      return DocFlavor.INPUT_STREAM.JPEG;
	    else if (extension.equals("png"))
	      return DocFlavor.INPUT_STREAM.PNG;
	    else if (extension.equals("ps"))
	      return DocFlavor.INPUT_STREAM.POSTSCRIPT;
	    else if (extension.equals("txt"))
	      return DocFlavor.INPUT_STREAM.TEXT_PLAIN_US_ASCII;
	    // Fallback: try to determine flavor from file content
	    else
	      return DocFlavor.INPUT_STREAM.AUTOSENSE;
	  }
}
