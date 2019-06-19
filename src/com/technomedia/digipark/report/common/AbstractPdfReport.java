/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.technomedia.digipark.report.common;

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

	/**
	 * Big bold font
	 */
	protected final Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
			Font.BOLD);
	/**
	 * normal font
	 */
	protected final Font normalFont = new Font(Font.FontFamily.HELVETICA, 10,
			Font.NORMAL, BaseColor.BLACK);
	/**
	 * normal bold font
	 */
	protected final Font normalboldFont = new Font(Font.FontFamily.HELVETICA, 11,
			Font.BOLD, BaseColor.BLACK);

	/**
	 * medium bold font
	 */
	protected final Font subFont = new Font(Font.FontFamily.HELVETICA, 16,
			Font.BOLD);
	/**
	 * small bold font
	 */
	protected final Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,
			Font.BOLD);

	protected final Font smallFont = new Font(Font.FontFamily.HELVETICA, 10,
			Font.NORMAL);

	protected final Font smallerFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL);

	protected final Font smallerBoldFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.BOLD);

	protected final Font smallestBoldFont = new Font(Font.FontFamily.HELVETICA, 8,
			Font.BOLD);

	protected final int PAGE_MODE_PORTRAIT = 2; 
	/**
	 * 
	 * @param fileName file to be created (with location)
	 * @return created fileName on success, else null
	 */
	public final String generatePage(String fileName)
	{

		if(fileName == null)
			throw new NullPointerException("File name cannot be null");

		if(fileName.endsWith(".pdf") == false)
			fileName += ".pdf";

		Document document = new Document(PageSize.A4);

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

	/**
	 * 
	 * @param fileName file to be created (with location)
	 * @return created fileName on success, else null
	 */
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
	 */
	public void addPageMetaData(Document document){

		String title = getPageTitle();
		String subjct = getPageSubject();

		if(title != null)
			document.addTitle(title);
		if(subjct != null)
			document.addSubject(subjct);

		document.addAuthor("Report generator");
		document.addCreator("Genius Organic");
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
	public abstract void addContent(Document document) throws DocumentException;
	/**
	 * Override this method to add header footer to a PDF document
	 * @param pageNo current page number
	 * @param writer
	 * @param document Document object.
	 * @param rect Page Rectangle
	 */
	public abstract void addPageHeaderFooter(int pageNo,PdfWriter writer,Document document,Rectangle rect);
	
	public abstract void addCommonHeader(Document document) throws DocumentException;

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

	private final class HeaderFooter extends PdfPageEventHelper{

		int pageNumber = 0;

		/**
		 * Initialize one of the headers.
		 *
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
		 * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		@Override
		public void onOpenDocument(PdfWriter writer, Document document) {

		}

		/**
		 * Initialize one of the headers, based on the chapter title; reset the
		 * page number.
		 *
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onChapter(
		 * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document, float,
		 * com.itextpdf.text.Paragraph)
		 */
		@Override
		public void onChapter(PdfWriter writer, Document document,
				float paragraphPosition, Paragraph title) {

		}

		/**
		 * Increase the page number.
		 *
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
		 * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		@Override
		public void onStartPage(PdfWriter writer, Document document) {
			
			pageNumber ++;

			Rectangle rect = writer.getPageSize();

			addPageHeaderFooter(pageNumber, writer, document, rect);

			
		}

		/**
		 * Adds the header and the footer.
		 *
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
		 * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		@Override
		public void onEndPage(PdfWriter writer, Document document) {

			

		}
	}
}
