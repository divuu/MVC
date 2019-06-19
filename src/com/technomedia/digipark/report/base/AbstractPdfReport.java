/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.technomedia.digipark.report.base;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * Refer http://tutorials.jenkov.com/java-itext/index.html
 * @author Bharat
 */
public abstract class AbstractPdfReport {
	
	protected final Font bigFontNormal = FontFactory.getFont("/fonts/Verdana.ttf", 18, Font.NORMAL);
	
	protected final Font bigFontBold = FontFactory.getFont("/fonts/Verdana.ttf", 18, Font.BOLD);
	
	protected final Font bigFontBoldUnderline = FontFactory.getFont("/fonts/Verdana.ttf", 18, Font.BOLD|Font.UNDERLINE);
	
	
	protected final Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
	
	protected final Font normalboldFont = FontFactory.getFont("/fonts/Verdana.ttf", 11, Font.BOLD, BaseColor.BLACK);
	
	protected final Font normalboldUnderlinedFont = FontFactory.getFont("/fonts/Pacifico.ttf", 12, Font.BOLD, BaseColor.BLACK);
		
	
	protected final Font subFont = FontFactory.getFont("/fonts/Verdana.ttf", 16, Font.BOLD);
	
	protected final Font smallBold = FontFactory.getFont("/fonts/Verdana.ttf", 12, Font.BOLD);

	protected final Font smallFont = FontFactory.getFont("/fonts/Verdana.ttf", 12, Font.NORMAL);

	protected final Font smallerFont = FontFactory.getFont("/fonts/Verdana.ttf", 10, Font.NORMAL);

	protected final Font smallerBoldFont = FontFactory.getFont("/fonts/Verdana.ttf", 10, Font.BOLD);

	protected final Font smallestBoldFont = FontFactory.getFont("/fonts/Verdana.ttf", 10, Font.BOLD);
	
	
	protected final Font mediumBoldFont = FontFactory.getFont("/fonts/Verdana.ttf", 15, Font.BOLD, BaseColor.BLACK);

	protected final Font mediumNormalFont = FontFactory.getFont("/fonts/Verdana.ttf", 15, Font.NORMAL, BaseColor.BLACK);
	
	protected final Font mediumBoldUnderlineFont = FontFactory.getFont("/fonts/Verdana.ttf", 15, Font.UNDERLINE|Font.BOLD, BaseColor.BLACK);
		
	
	//Certificate Header
	protected final Font certificateHeaderFont = FontFactory.getFont("/fonts/Verdana.ttf", 30, Font.BOLD|Font.UNDERLINE, BaseColor.BLACK);
	
	//Signature Header
	protected final Font signatureHeaderFont = FontFactory.getFont("/fonts/Verdana.ttf", 6, Font.NORMAL, BaseColor.BLACK);
	
//	protected final int PAGE_MODE_PORTRAIT = 2;
	
	
	
	/**
	 * 
	 * @param fileName file to be created (with location)
	 * @param pageSize PageSize.A4 or PageSize.A4.rotate()
	 * @param author 
	 * @param creator 
	 * @return created fileName on success, else null
	 */
	public final Document generatePage( String pageName, Rectangle pageSize, String author, String creator) {

		if (pageName == null)
			throw new NullPointerException("File name cannot be null");

		if (pageName.endsWith(".pdf") == false)
			pageName += ".pdf";

		Document document = new Document(pageSize); // PageSize.A4 or PageSize.A4.rotate()

		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pageName));

//			HeaderFooter event = new HeaderFooter();

			// writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
//			writer.setPageEvent(event);

//			document.open();

//			addPageMetaData(document, author, creator);

//			addHeader(document);
//
//			addContent(document);
//			
//			addFooter(document);
//
//			document.close();

			return document;

		} catch (DocumentException ex) {

			Logger.getLogger( AbstractPdfReport.class.getName() ).log( Level.SEVERE, null, ex );
			document.close();

		} catch( FileNotFoundException ex ) {
			Logger.getLogger(AbstractPdfReport.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	/**
	 * 
	 * @param fileName file to be created (with location)
	 * @return created fileName on success, else null
	 */
/*	
	public final String generateLandScapePage(String fileName)
	{

		if(fileName == null)
			throw new NullPointerException("File name cannot be null");

		if(fileName.endsWith(".pdf") == false)
			fileName += ".pdf";

		Document document = new Document(PageSize.A4.rotate());

		try {

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			HeaderFooter event = new HeaderFooter();

			//writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
			writer.setPageEvent(event);

			document.open();

			addPageMetaData(document);
			addCommonHeader(document);

			addContent(document);

			document.close();

			return fileName;

		} catch (DocumentException ex) {

			Logger.getLogger(AbstractPdfReport.class.getName()).log(Level.SEVERE, null, ex);
			document.close();

		} catch (FileNotFoundException ex) {
			Logger.getLogger(AbstractPdfReport.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}
*/
	
	public void addTableColumns(String[] cols, PdfPTable table){

		if(cols != null){

			for( String colName : cols){

				PdfPCell cell = new PdfPCell(new Phrase(colName,smallerBoldFont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorderWidth(0.8f);
				table.addCell(cell);
			}
		}

	}


	/**
	 * virtual method to add meta data for the page
	 * @param document Document where meta data need to be added.
	 * @param author 
	 * @param creator 
	 */
	public void addPageMetaData(Document document, String author, String creator){

		String title = getPageTitle();
		String subjct = getPageSubject();

		if(title != null)
			document.addTitle(title);
		if(subjct != null)
			document.addSubject(subjct);

		document.addAuthor( author );		// "Report generator"
		document.addCreator( creator );	// "Genius Organic"
		document.addCreationDate();

//		addLineSeperator(document);

	}

	/**
	 * @param document
	 * Virtual Function for adding Line Separator.
	 */
	protected void addLineSeperator( Document document ){
		LineSeparator ls = new LineSeparator();
		try {
			document.add(new Chunk(ls));
			document.add(new Phrase("\n"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	/**
	 * Override this method to add the page contents
	 * @param document Document object to where content to be added
	 * @throws com.itextpdf.text.DocumentException
	 */
//	public abstract void addContent(Document document, NegotiationData data, String toe, byte typDoc) throws DocumentException;
//	/**
//	 * Override this method to add header footer to a PDF document
//	 * @param pageNo current page number
//	 * @param writer
//	 * @param document Document object.
//	 * @param rect Page Rectangle
//	 */
//	public abstract void addPageHeaderFooter(int pageNo,PdfWriter writer,Document document,Rectangle rect);
//	
//	public abstract void addHeader(Document document, NegotiationData data) throws DocumentException;
//	public abstract void addFooter(Document document, NegotiationData data, byte typDoc) throws DocumentException;
//
	/**
	 * get the page title
	 * @return Page title
	 */
	public abstract String getPageTitle();

	/**
	 * get the page subject
	 * @return page subject
	 */
	public abstract String getPageSubject();


	

}
