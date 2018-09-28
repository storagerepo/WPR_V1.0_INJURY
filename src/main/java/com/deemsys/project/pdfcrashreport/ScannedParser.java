package com.deemsys.project.pdfcrashreport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.common.InjuryProperties;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.ITesseract.RenderedFormat;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.PdfUtilities;

@Service
public class ScannedParser {

	@Autowired
	InjuryProperties injuryProperties;
	
	public String convertToSearchablePDF(MultipartFile file) throws TesseractException, IOException{	        
        
        File pdfDoc=new File(injuryProperties.getProperty("tempFolderTesseract"),file.getOriginalFilename());        
        FileUtils.writeByteArrayToFile(pdfDoc, file.getBytes());
        
        File pngImageFiles = PdfUtilities.convertPdf2Tiff(pdfDoc);        
        ITesseract instance = new Tesseract();
        List<RenderedFormat> list = new ArrayList<RenderedFormat>();
        list.add(RenderedFormat.PDF);
        instance.setLanguage("eng");
        instance.setDatapath(injuryProperties.getProperty("tesseractDataFolder"));
        System.out.println(injuryProperties.getProperty("tempFolder")+injuryProperties.getProperty("tesseractDataConverted")+file.getOriginalFilename());
        instance.createDocuments(pngImageFiles.getAbsolutePath(),injuryProperties.getProperty("tempFolderTesseract")+injuryProperties.getProperty("tesseractDataConverted")+file.getOriginalFilename(), list);
        return "Good";
	}
	
	public String convertToSearchablePDF(File file) throws TesseractException, IOException{	        
        
        File pngImageFiles = PdfUtilities.convertPdf2Tiff(file);        
        ITesseract instance = new Tesseract();
        List<RenderedFormat> list = new ArrayList<RenderedFormat>();
        list.add(RenderedFormat.PDF);
        instance.setLanguage("eng");
        instance.setDatapath(injuryProperties.getProperty("tesseractDataFolder"));
        String destFlder=injuryProperties.getProperty("tempFolderTesseract")+injuryProperties.getProperty("tesseractDataConverted")+file.getName().substring(0,file.getName().indexOf("."));
        instance.createDocuments(pngImageFiles.getAbsolutePath(),destFlder, list);
        return destFlder+".pdf";
		//return "D:/InjuryCrashReports/converted/beachwood_2.pdf";
	}
	
}
