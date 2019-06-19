//package com.technomedia.routealert.report;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.technomedia.logger.MLogger;
//import com.technomedia.routealert.database.CommercialInvoice;
//import com.technomedia.routealert.database.CommercialInvoiceDetails;
//import com.technomedia.routealert.database.Customer;
//import com.technomedia.routealert.database.LetterOfCredit;
//import com.technomedia.routealert.database.Organization;
//import com.technomedia.routealert.database.Product;
//import com.technomedia.routealert.database.PurchaseOrder;
//import com.technomedia.routealert.database.PurchaseOrderDetails;
//import com.technomedia.routealert.database.utils.NumberToWords;
//import com.technomedia.routealert.report.base.AbstractPdfReport;
//import com.technomedia.routealert.server.common.DefaultServerSettings;
//
//public class InvoiceDocuments extends AbstractPdfReport {
//
//	
//	protected class FileNameGenerator {
//		
//		public String mLocalFileName = null;		// file with path used by the Java program to write the pdf file
//		public String mServerFileName = null;		// file with path used by the browser to access the pdf file
//		
//		public void generateFileName( String documentNumber, String orgShortName, String contextPath ) {
//
//			
//			String fileName = documentNumber;
//			fileName = fileName.replace( '/', '_' );
//			fileName = fileName.replace( '-', '_' );
//			fileName += ".pdf";
//
////			
////			String folderPath = contextPath + orgShortName;
////			
////			System.out.println( "ContextPath = " + contextPath );
////			System.out.println( "folderPath = " + folderPath );
////
////			folderPath = DefaultServerSettings.DEFAULT_REPORT_CREATION_FOLDER;
////			System.out.println( "folderPath = " + folderPath );
//
//			
//			
//			String folderPath = DefaultServerSettings.DEFAULT_REPORT_CREATION_FOLDER + orgShortName;
//		
//			System.out.println( "ContextPath = " + contextPath );
//			System.out.println( "folderPath = " + folderPath );
//
//			System.out.println( "folderPath = " + folderPath );
//			File myFileFolder = new File(folderPath);
//			
//			if( !myFileFolder.exists() ){
//				
//				boolean ret = myFileFolder.mkdir();
//				if( ret == false )
//					MLogger.i( MLogger.MOD_DB, "Error: Couldnt create folder for reports :" + folderPath );
//
//			}		
//			pathToDocuments = folderPath;
//			
//			mLocalFileName = folderPath + "\\" + fileName;
//
//			MLogger.i( MLogger.MOD_DB, "Local System Path pdf :" + mLocalFileName );
//
////			DefaultServerSettings defaultServerSettings = new DefaultServerSettings();
//			mServerFileName = DefaultServerSettings.DEFAULT_WEB_REPORT_FOLDER + orgShortName + "/" + fileName;
//
//			System.out.println( "mServerFileName = " + mServerFileName );
//
//			MLogger.i( MLogger.MOD_DB, "Server Path pdf :" + mServerFileName );
//		}
//	};
//	
//
//
//	Font smallFont = new Font(FontFamily.HELVETICA, 7);
//	Font smallBoldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
//	Font labelBoldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
//	Font tinyfont = new Font(FontFamily.HELVETICA, 6);
//	Font tinyBoldFont = new Font(FontFamily.HELVETICA, 6, Font.BOLD);
//	Font mediumBoldFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
//	
//	private String DateFormat = "dd.MM.yyyy";
//	public String pathToDocuments;
//	
//	
//	public static void main(String[] args) throws MalformedURLException, IOException, DocumentException {
//		InvoiceDocuments invoiceDocuments = new InvoiceDocuments();
//		
//		CommercialInvoice commercialInvoice = new CommercialInvoice();
//		
////		ReportFormat reportFormatData = new ReportFormat();
//		
//		
//		
//		commercialInvoice.mCommercialInvoiceNumber = "Teximco/INV/2018-2019/2";	
//		commercialInvoice.mCommercialInvoiceDate = new Date().getTime();	
//		commercialInvoice.mExporter = "Teximco Enterprise \n1/430, GARIAHAT ROAD,\nKOLKATA - 700068\nINDIA"; 
//		commercialInvoice.mProformaInvoiceId = 1; 
//		commercialInvoice.mLetterOfCreditId = 1;	
//		commercialInvoice.mPurchaseOrderId = 1; 
//		commercialInvoice.mCustomerId = 1;	
//		commercialInvoice.mConsignee = "MEGHNA INDUSTRIAL ECONOMIC ZONE,\nSONARGAON, NARAYANGONJ, BANGLADESH";	
//		commercialInvoice.mBuyerReferenceNo = "123"; 
//		commercialInvoice.mBuyerReferenceDate = new Date().getTime(); 
//		commercialInvoice.mBuyer = "OTHER BIUYER";	
//		commercialInvoice.mOriginCountryId = 1; 
//		commercialInvoice.mDestinationCountryId = 1; 
//		commercialInvoice.mVesselFlightNo = "BY SEA"; 
//		commercialInvoice.mLoadingPortId = 1; 
//		commercialInvoice.mDischargePortId = 2; 
//		commercialInvoice.mFinalDestination ="Bangladesh"; 
//		commercialInvoice.mCurrencyId = 1;	
//		commercialInvoice.mSubTotalAmount = 4231f;	
//		commercialInvoice.mTotalDiscount = 0;	
//		commercialInvoice.mAddOtherCharges = 0;	
//		commercialInvoice.mOtherChargesText = null; 
//		commercialInvoice.mTaxText = null;	
//		commercialInvoice.mCgst = 0;	
//		commercialInvoice.mSgst = 0; 
//		commercialInvoice.mIgst = 0; 
//		commercialInvoice.mTotalGst = 0;	
//		commercialInvoice.mTotalAmount = 0;	
//		commercialInvoice.mNetQuantity = 2340f;	
//		commercialInvoice.mGrossQuantity = 2340f;	
//		commercialInvoice.mFreightAmount = 2340f; 
////		commercialInvoice.mNegotiationDocDate = new Date().getTime();	
//		commercialInvoice.mShippingMark = "TEB, B-DESH";	
//		commercialInvoice.mLcRemarks = "LC REMARKS";	
//		commercialInvoice.mTermsOfDeliveryAndPayment = "TNC"; 
//		commercialInvoice.mHsnDescription = "HSN DESCRIPTION"; 
////		commercialInvoice.mOrganizationId = 1;	
////		commercialInvoice.mSysUserId = 1;	
////		commercialInvoice.mFilePath;	
////		commercialInvoice.mExtraData;	
//		                                                        
//		 // for pdf                                   
//		commercialInvoice.mConsigneeName = "MS THAI FOILS AND POLYMERS LTD."; 
//		commercialInvoice.mCountryOfOrigin = "INDIA"; 
//		commercialInvoice.mDestinationCountry = "BANGLADESH"; 
//		commercialInvoice.mLoadingPort = "NHAVA SHEVA, MUMBAI PORT"; 
//		commercialInvoice.mDischargePort = "CHATTOGRAM SEA PORT"; 
//		                                                        
////		commercialInvoice.mBankAccount = new Bank; 
//		                                                        
////		commercialInvoice.mCurrency; 
////		commercialInvoice.mProformaInvoice = new ProformaInvoice(); 
//		commercialInvoice.mLetterOfCredit = new LetterOfCredit(); 
//		commercialInvoice.mOrganization = new Organization(); 
//		commercialInvoice.mOrganization.mIec = "0295002875";
//		commercialInvoice.mOrganization.mContactPersonName = "UDYAAN SEN";
//		commercialInvoice.mCustomer = new Customer(); 
//	
///*
//		purchaseOrder.mFreightAmount = 2730.00f;
////		purchaseOrder.mFobAmount = 96444.00f;
//		purchaseOrder.mBankAccount = new BankAccount();
//		purchaseOrder.mBankAccount.mBankName = "UNION BANK OF INDIA";
//		purchaseOrder.mBankAccount.mAccountNumber = "495601010028027";
//		purchaseOrder.mBankAccount.mBranchName = "OVERSEAS BRANCH";
//		purchaseOrder.mBankAccount.mIfsCode = "UBIN0549568";
//		purchaseOrder.mBankAccount.mSwift = "UBININBBOCL";
//		purchaseOrder.mLcRemarks = "L/C MUST ALLOW TOLERANCE OF PLUS/MINUS 10% ON BOTH QUANTITY AND VALUE";
//		purchaseOrder.mTermsOfDeliveryAndPayment = 	"DELIVERY: WITHIN 45 DAYS AFTER RECEIPTS OF L/C"
//														+ "PART SHIPMENT: TO BE ALLOWED"
//														+ "TRANS SHIPMENT: TO BE ALLOWED"
//														+ "PAYMENT: BY IRREVOCABLE CONFIRMED, UNRISTRECTED L/C"
//														+ "AT SIGHT AND IN FOVOUR AND NEGOTIABLE IN ANY BANK IN INDIA";
//	
//*/		
//		/////////////////////////////////////////////////////////////////////////////////////////////////////
//		ArrayList<CommercialInvoiceDetails> commercialInvoiceDetailsList = new ArrayList<CommercialInvoiceDetails>();
//		
//		CommercialInvoiceDetails commercialInvoiceDetails = new CommercialInvoiceDetails();
//
//		for(int i = 1; i<=10; i++){
//			
//			commercialInvoiceDetails = new CommercialInvoiceDetails();
//			commercialInvoiceDetails.mProductId = 1;
//			commercialInvoiceDetails.mProduct = new Product();
//			commercialInvoiceDetails.mProduct.mProductCode = "MIL-72914";
//			commercialInvoiceDetails.mProduct.mBillingProductName= "MICRO ESTERLAM PLUS WHITE ST C 8";
//			commercialInvoiceDetails.mProduct.mHsnCode = "3215 19.10";
//			commercialInvoiceDetails.mQuantity = 6800f;		//Quantity of purchase in kgs
//			commercialInvoiceDetails.mRate = 161f;		//Selling Rate after negotiation with customer
//			commercialInvoiceDetails.mAmount = 109480f;		//( Quantity X Rate - Discount ) * ( 1 + tax Percent )
//			commercialInvoiceDetails.mTaxPercent = 0.10f;
//			commercialInvoiceDetails.mTaxAmount = 1094f;
//			
//			commercialInvoiceDetailsList.add(commercialInvoiceDetails);
//
//		}
//
//		
//		
//		
//		invoiceDocuments.pathToDocuments = "D:\\TMSS\\Project\\texERP\\PDF Generated";
//
////		Document document = invoiceDocuments.generatePage( invoiceDocuments.pathToDocuments + "\\CertificateOfOrigin.pdf", PageSize.A4, "author", "creator" );
////		document.open();
//
////		JRC invoiceDocuments.generatePurchaseOrder(document, purchaseOrder, puchaseOrderDetailsList);
//		
////		invoiceDocuments.generateProformaInvoice(document, proformaInvoice, list);	
////		invoiceDocuments.generateCommercialInvoice(document, reportFormatData);
////		invoiceDocuments.generatePackingList(document, reportFormatData);
//		invoiceDocuments.generateCertificateOfOrigin(commercialInvoice, commercialInvoiceDetailsList, invoiceDocuments.pathToDocuments);
//
////		
////		document.close();
//		System.out.println("Generation Completed");
//	}
//
//	
//	/**
//	 * @param document
//	 * @param purchaseOrder
//	 * @param purchaseOrderDetails
//	 * @return
//	 */
//	public String generatePurchaseOrder( PurchaseOrder purchaseOrder, ArrayList<PurchaseOrderDetails> purchaseOrderDetails, String filePath ){
//
//		if( purchaseOrder == null )
//			return null;
//		
//		FileNameGenerator fileNameGenerator = new FileNameGenerator();
//		
//		fileNameGenerator.generateFileName( purchaseOrder.mPurchaseOrderNumber, purchaseOrder.mOrganization.mShortName, filePath );
//		
//		if( fileNameGenerator.mLocalFileName == null && fileNameGenerator.mServerFileName == null ) {
//			
//			return null;
//		}
//
//		Document document = generatePage( fileNameGenerator.mLocalFileName, PageSize.A4, "author", "creator" );
//		document.open();
//		document.newPage();
//		
//		document.newPage();
//		Paragraph heading = new Paragraph("PURCHASE ORDER");
//		heading.setAlignment(Element.ALIGN_CENTER);
//		heading.setSpacingAfter(5);
//		Paragraph labelPara = null;
//		
////		top - exporter/consignee /order no/ so no/ so date....
//		PdfPTable headerTable = new PdfPTable(3);
//		headerTable.setWidthPercentage(100F);
//		PdfPCell cell = null;
//
////		cell = new PdfPCell(new Paragraph("PURCHASE ORDER"));
////		cell.setColspan(3);
////		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
////		cell.setBorder(0);
////		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph("Exporter", labelBoldFont);
//		cell = new PdfPCell();
//		cell.setRowspan(3);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(purchaseOrder.mOrganization.mOrganizationName, smallBoldFont);
//		cell.addElement(labelPara);
//		
//		labelPara = new Paragraph(purchaseOrder.mOrganization.mRegisteredAddress + "\nPhone No - " + purchaseOrder.mOrganization.mLandlineNumber + " / "
//				+ purchaseOrder.mOrganization.mFaxNumber + "\nE-mail: " + purchaseOrder.mOrganization.mEmail, smallFont);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph("Purchase Order No: " , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph( purchaseOrder.mPurchaseOrderNumber , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		String DateFormat = "dd.MM.yyyy";
//		String formattedDate = "";
//		if( purchaseOrder.mPurchaseOrderDate > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(purchaseOrder.mPurchaseOrderDate);
//		}
//		
//		labelPara = new Paragraph("Purchase Order Date: " , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph( formattedDate , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph("Quotation No: " , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph( purchaseOrder.mQuotationNo, smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph("Supplier " , smallBoldFont);
//		cell = new PdfPCell();
//		cell.setRowspan(3);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(purchaseOrder.mVendorObject.mVendorName, smallBoldFont);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(purchaseOrder.mVendorObject.mAddress, smallFont);
//		cell.addElement(labelPara);
//		
//	    labelPara = new Paragraph();
//	    labelPara.add(new Chunk("CIN - ", smallFont ));
//	    labelPara.add(new Chunk( purchaseOrder.mVendorObject.mCin, smallBoldFont)); 
//	    labelPara.add(new Chunk(", PAN - ", smallFont ));
//	    labelPara.add(new Chunk( purchaseOrder.mVendorObject.mPan, smallBoldFont ));
//	    cell.addElement(labelPara);
//		
//		labelPara = new Paragraph();
//	    labelPara.add(new Chunk("GSTIN - ", smallFont ));
//	    labelPara.add(new Chunk( purchaseOrder.mVendorObject.mGstin, smallBoldFont));
//	    cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		
//		labelPara = new Paragraph("SO No: " , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph( purchaseOrder.mSoNo , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph("SO Date: " , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		if( purchaseOrder.mSoDate > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(purchaseOrder.mSoDate);
//		}
//		
//		labelPara = new Paragraph( formattedDate, smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph("Ship To: " , smallFont);
//		cell = new PdfPCell();
//		cell.setColspan(2);
//		cell.addElement(labelPara);
//		
//		if( purchaseOrder.mShipTo != "" ){
//			
//			labelPara = new Paragraph( purchaseOrder.mShipTo , smallBoldFont);
//			cell.addElement(labelPara);
//			
//		}
//		
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph( "Please supply the undermentioned goods as per the following terms & conditions.", tinyfont);
//		cell = new PdfPCell();
//		cell.setColspan(3);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		
////		body
//		PdfPTable bodyTable = new PdfPTable(10);
//
//		bodyTable.setWidthPercentage(100F);
//
//		labelPara = new Paragraph("SL. No.", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("Description", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("Product Code", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("HSN", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("Quantity in Kgs/Ltrs", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("Rate/Kg or Lt (" + purchaseOrder.mCurrency.mCurrencySymbol + ")" , smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		
//		labelPara = new Paragraph("Bill Value", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("GST%", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("GST Amount", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("Total Value", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		
//		PdfPCell emptyCell = new PdfPCell(new Paragraph("\n"));
//		PdfPCell infoCell = null;
//		
//		for (int i = 1; i <= purchaseOrderDetails.size(); i++) {
//			
//
////			sno  desc code hsn  qty  rate bill gstr  gsta
//			
//			infoCell = new PdfPCell(new Paragraph( Integer.toString(i), smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			infoCell = new PdfPCell(new Paragraph(purchaseOrderDetails.get(i - 1).mProduct.mProductName, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			infoCell = new PdfPCell(new Paragraph(purchaseOrderDetails.get(i - 1).mProduct.mProductCode, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph(purchaseOrderDetails.get(i - 1).mProduct.mHsnCode, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			infoCell = new PdfPCell(new Paragraph("" + purchaseOrderDetails.get(i - 1).mQuantity, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			infoCell = new PdfPCell(new Paragraph("" + purchaseOrderDetails.get(i - 1).mRate, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			
//			double subBillValue = Math.round(( purchaseOrderDetails.get(i - 1).mAmount - purchaseOrderDetails.get(i - 1).mTaxAmount ) * 100D) / 100D;
//			infoCell = new PdfPCell(
//					new Paragraph( "" +  subBillValue, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);	
//
//			infoCell = new PdfPCell(new Paragraph("" + purchaseOrderDetails.get(i - 1).mTaxPercent, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			infoCell = new PdfPCell(new Paragraph("" + purchaseOrderDetails.get(i - 1).mTaxAmount, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			
//			infoCell = new PdfPCell(new Paragraph("" + purchaseOrderDetails.get(i - 1).mAmount, smallFont)); // total bill value for "this" product
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
////			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			if( i == purchaseOrderDetails.size() ){
//				emptyCell.setColspan(1);
//				emptyCell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM);
//				for(int j = 1; j<= 30; j++ ){
//					
//					bodyTable.addCell(emptyCell);					
//					
//				}
//			}
//		}
//		
//
//		
//		labelPara = new Paragraph("TOTAL", smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.setColspan(4);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("" + purchaseOrder.mQuantityInKgs, smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		emptyCell.enableBorderSide(Rectangle.BOX);
//		emptyCell.setColspan(1);
//		bodyTable.addCell(emptyCell);
//		
//		labelPara = new Paragraph("" + purchaseOrder.mSubTotalAmount, smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		
//		bodyTable.addCell(emptyCell);
//		
//		labelPara = new Paragraph("" + purchaseOrder.mTotalGst, smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("" + purchaseOrder.mTotalAmount, smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		
//		PdfPTable footTable = new PdfPTable(3);
//		footTable.setWidthPercentage(100F);
//
//		labelPara = new Paragraph("Conditions:", smallBoldFont);
//		cell = new PdfPCell();
//		cell.setColspan(3);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(purchaseOrder.mConditionFooter, smallFont);
//		cell.addElement(labelPara);;
//		footTable.addCell(cell);
//
//		labelPara = new Paragraph(purchaseOrder.mFooter, smallFont);
//		cell = new PdfPCell();
//		cell.setColspan(3);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph();
//		labelPara.add(new Chunk("Shipping Mark: ", smallFont));
//		labelPara.add(new Chunk(purchaseOrder.mShippingMark, smallBoldFont));
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//		
//		
//		cell = new PdfPCell();
//		labelPara = new Paragraph();
//		labelPara.add( new Chunk( "Delivery Address: ", smallFont));
//		labelPara.add( new Chunk( purchaseOrder.mVendorObject.mPort.mDispatchAddress, smallBoldFont));
//		cell.addElement(labelPara);
//		
//		labelPara = new Paragraph();
//		labelPara.add(new Chunk("Packing/Forwarding: ", smallFont));
//		labelPara.add(new Chunk("Export Worthy Pack", smallBoldFont));
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//		
//		cell = new PdfPCell();
//		cell.setColspan(2);
//		labelPara = new Paragraph();
//		labelPara.add( new Chunk("Export Border: ", smallFont) );
//		labelPara.add( new Chunk(purchaseOrder.mVendorObject.mPort.mPortName + " (" + purchaseOrder.mVendorObject.mPort.mPortCode + ") ", smallBoldFont) );
//		cell.addElement(labelPara);
//		labelPara.add( new Chunk("Exporter IEC No: ", smallFont) );
//		labelPara.add( new Chunk(purchaseOrder.mOrganization.mIec, smallBoldFont) );
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//		
//		cell = new PdfPCell();
//		cell.setColspan(2);
//		labelPara = new Paragraph();
//		labelPara.add( new Chunk("GST ARN NO: ", smallFont) );
//		labelPara.add( new Chunk(purchaseOrder.mOrganization.mGstArn, smallBoldFont) );
//		cell.addElement(labelPara);
//		
//		labelPara = new Paragraph();
//		labelPara.add( new Chunk("GST IN NO: ", smallFont) );
//		labelPara.add( new Chunk(purchaseOrder.mOrganization.mGstin, smallBoldFont) );
//		cell.addElement(labelPara);
//		
//		if( purchaseOrder.mOrganization.mExportPromotionCouncilRegValidity > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(purchaseOrder.mOrganization.mExportPromotionCouncilRegValidity);
//		}
//		
//		
//		labelPara = new Paragraph();
//		labelPara.add( new Chunk("Export Promotion Council Reg No: ", smallFont) );
//		labelPara.add( new Chunk(purchaseOrder.mOrganization.mExportPromotionCouncilRegNo, smallBoldFont) );
//		labelPara.add( new Chunk(" Valid Upto: ", smallFont) );
//		labelPara.add( new Chunk(formattedDate + "\n\n", smallBoldFont) );
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//		
//		labelPara = new Paragraph("Signature", smallBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//		
//		cell = new PdfPCell(new Paragraph(
//				"TechnoMedia ERP",
//				tinyfont));
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.setColspan(3);
//		cell.setPaddingTop(5f);
//		footTable.addCell(cell);
//		
//		try {
//			
//			headerTable.setWidths( new float[] { 60f, 20f, 20f} );  
////											 sno  desc code hsn  qty  rate bill gstr  gsta tot
//			bodyTable.setWidths(new float[] { 4f, 30f, 12f, 8f,  7f,  6f,  8f,  5f,   8f,  8f });
//			footTable.setWidths(new float[] { 40f, 25f, 35f });
//			document.add(heading);
//			document.add(headerTable);
//			document.add(bodyTable);
//			document.add(footTable);
//		} catch (DocumentException e) {
//			
//			e.printStackTrace();
//			return "UNABLE TO GENERATE";
//		}
//		
//		document.close();
//		
//		MLogger.i( MLogger.MOD_DB, "Server Path Purchase Order pdf :" + fileNameGenerator.mServerFileName );
//
//		return fileNameGenerator.mServerFileName;
//		
//	}
///*	
//	public String generateProformaInvoice( ProformaInvoice proformaInvoice, ArrayList<ProformaInvoiceDetails> proformaDetails, String filePath ) {
//		
//		FileNameGenerator fileNameGenerator = new FileNameGenerator();
//		
//		fileNameGenerator.generateFileName( proformaInvoice.mProformaInvoiceNumber, proformaInvoice.mOrganization.mShortName, filePath );
//		
//		if( fileNameGenerator.mLocalFileName == null && fileNameGenerator.mServerFileName == null ) {
//			
//			return null;
//		}
//
//		Document document = generatePage( fileNameGenerator.mLocalFileName, PageSize.A4, "author", "creator" );
//		document.open();
//		document.newPage();
//		
//		Paragraph labelPara = null;
//
////		top part
//		PdfPTable headerTable = new PdfPTable(4);
//		headerTable.setWidthPercentage(100F);
//		PdfPCell cell = null;
//
//		cell = new PdfPCell(new Paragraph("PROFORMA INVOICE"));
//		cell.setColspan(4);
//		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setBorder(0);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Exporter Information", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mExporter, smallBoldFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(2);
//		cell.setColspan( 2 );
//		headerTable.addCell(cell);
//
//		String DateFormat = "dd.MM.yyyy";
//		String formattedDate = "";
//		if( proformaInvoice.mProformaInvoiceDate > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(proformaInvoice.mProformaInvoiceDate);
//		}
//
//		labelPara = new Paragraph("Pro Invoice No. & Date", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mProformaInvoiceNumber + "\nDT: " + formattedDate, smallFont);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Exporter's Reference", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("IEC : "  + proformaInvoice.mOrganization.mIec, smallFont);  // data.mIecNo need IEC Number of the E
// 		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Buyer's Ref no and Date", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		if( proformaInvoice.mBuyerReferenceDate > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(proformaInvoice.mBuyerReferenceDate);
//			labelPara = new Paragraph(proformaInvoice.mBuyerReferenceNo + "\nDT: " + 
//					formattedDate, 
//					smallFont);
//			cell.addElement(labelPara);
//		}
//		
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Buyer's Email", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Consignee", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mConsigneeName, smallBoldFont); 
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("ADD: "+ proformaInvoice.mConsignee, smallFont);  // Consignee is the address of the consignee from the pojo
//		cell.addElement(labelPara);
//		cell.setRowspan(2);
//		cell.setColspan( 2 );
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Buyer (if other than consignee)\n", labelBoldFont);
//		labelPara.setSpacingAfter(5f);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mBuyer, smallFont);
//		cell.addElement(labelPara);
//		cell.setColspan(2);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Country of Origin of Goods", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mCountryOfOrigin, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Country of Final Destination", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mDestinationCountry, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		labelPara = new Paragraph("Pre Carriage By", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Place of Receipt by Pre Carrier", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//
//		labelPara = new Paragraph("Terms of Delivery and Payment", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mTermsOfDeliveryAndPayment, smallFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(3);
//		cell.setColspan( 2 );
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Road/Sea/Air", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mVesselFlightNo, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Port of Loading", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mLoadingPort, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Port of Discharge", labelBoldFont);
//		cell = new PdfPCell();
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mDischargePort, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Final Destination", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(proformaInvoice.mFinalDestination, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
////		body
//		PdfPTable bodyTable = new PdfPTable(6);
//
//		bodyTable.setWidthPercentage(100F);
//
//		labelPara = new Paragraph("Marks & no/ Container No", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(Rectangle.RIGHT);
////		cell.disableBorderSide(Rectangle.BOTTOM);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("No. & Kind of Pkgs", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Description and Tarrif of Goods", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(3);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Quantity in Kgs/Ltrs", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(3);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Rate In " + proformaInvoice.mCurrency.mCurrencySymbol + " on "+ proformaInvoice.mRateBasedOnLookup.mLookupName +" Basis per Kgs/Ltrs", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Amount\n In " + proformaInvoice.mCurrency.mCurrencySymbol, labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(3);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph(proformaInvoice.mShippingMark, smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Standard Export Worthy Packing", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph(proformaInvoice.mHsnDescription, smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
// 		PdfPCell emptyCell = new PdfPCell(new Paragraph("")); // adding to fill place;
////		cell.disableBorderSide(3);
//		emptyCell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM);
//		bodyTable.addCell(emptyCell); // adding to fill place;
//		bodyTable.addCell(emptyCell); // adding to fill place;
//		bodyTable.addCell(emptyCell);
//
//		// products part
//		emptyCell.setColspan(2);
//		emptyCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
////		emptyCell.disableBorderSide(3);
//
//		// get list of products object and add to the table
////		ArrayList<Product> products = new ArrayList<Product>(); //passed as a parameter
//		PdfPCell infoCell = null;
//		
//		for (int i = 1; i <= proformaDetails.size(); i++) {
//			bodyTable.addCell(emptyCell);
//			
//			infoCell = new PdfPCell(new Paragraph(i +". " + proformaDetails.get(i - 1).mProduct.mBillingProductName, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + proformaDetails.get(i - 1).mQuantity, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + proformaDetails.get(i - 1).mRate, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell( new Paragraph("" + proformaDetails.get(i - 1).mAmount, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//		}
//		
//		//fill space to bring footer down
//		labelPara = new Paragraph("\n", smallFont);
//		emptyCell.addElement(labelPara);
//		for(int i = 1; i<= 3; i++ ){
//			
//			bodyTable.addCell(emptyCell);
//			emptyCell.setColspan(1);
//			
//			bodyTable.addCell(emptyCell);
//			bodyTable.addCell(emptyCell);
//			bodyTable.addCell(emptyCell);
//			bodyTable.addCell(emptyCell);
//			
//			emptyCell.setColspan(2);
//		}
//
//		labelPara = new Paragraph("Total Quantity: "+ proformaInvoice.mQuantityInKgs +" Kgs/Ltrs", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		cell.setColspan(3);
//		bodyTable.addCell(cell);
//
//		emptyCell.setColspan(1);
//		emptyCell.enableBorderSide(Rectangle.BOTTOM);
//		bodyTable.addCell(emptyCell); // filler
//		bodyTable.addCell(emptyCell); // filler
//		bodyTable.addCell(emptyCell); // filler
////--------------------------------------------------------------------------------------------------------------------------
//		// summary table start here
//		PdfPTable summaryTable = new PdfPTable(2);
//		summaryTable.setWidthPercentage(100F);
//		
//		String afterDecimalAmount = String.valueOf(proformaInvoice.mTotalAmount);
//        afterDecimalAmount = afterDecimalAmount.substring ( afterDecimalAmount.indexOf ( "." ) + 1 );
//        
//		NumberToWords word = new NumberToWords();
//		labelPara = new Paragraph(
//				"Amount chargeable in words: TOTAL "+ proformaInvoice.mCurrency.mCurrencyWord + " " 
//		+ word.convertNumberToWords((long) proformaInvoice.mTotalAmount) + " AND " + word.convertNumberToWords(Long.parseLong(afterDecimalAmount))
//		+" "+ proformaInvoice.mCurrency.mCurrencyDecimal.toUpperCase() +" ONLY", smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		cell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//		summaryTable.addCell(cell);
//
//		labelPara = new Paragraph("Total: "+ proformaInvoice.mCurrency.mCurrencySymbol + " " + proformaInvoice.mTotalAmount, smallBoldFont);
//		labelPara.setAlignment(Rectangle.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.disableBorderSide(Rectangle.BOTTOM|Rectangle.LEFT);
//		cell.setRowspan(3);
//		cell.addElement(labelPara);
//		PdfPCell totalCell = summaryTable.addCell(cell);
//		
//		
//		if( proformaInvoice.mExpectedDeliveryDate > 0 ){
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(proformaInvoice.mExpectedDeliveryDate);
//			labelPara = new Paragraph("ETD: " + formattedDate, smallFont);
//			cell = new PdfPCell();
//			cell.addElement(labelPara);
//			cell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//			totalCell.setRowspan(4);
//			summaryTable.addCell(cell);
//		}
//		
//		
//		labelPara = new Paragraph(proformaInvoice.mLcRemarks, smallFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		cell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//		summaryTable.addCell(cell);
//		
//		double fobValue = Math.round(( proformaInvoice.mTotalAmount - proformaInvoice.mFreightAmount ) * 100D) / 100D;
//		labelPara = new Paragraph("FOB VALUE: "+ proformaInvoice.mCurrency.mCurrencySymbol + " " + fobValue + 
//					" + FREIGHT: "+ proformaInvoice.mCurrency.mCurrencySymbol + " " + proformaInvoice.mFreightAmount, smallFont); //calculate fob data.mFobAmount
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		cell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//		summaryTable.addCell(cell);  
//		
//		
//		
////		---------------------------------------------------------------------------------------------------------------------
//		// bottom part start here
//		PdfPTable footTable = new PdfPTable(3);
//		footTable.setWidthPercentage(100F);
//        
//
//		labelPara = new Paragraph("Contact Person for PSI:", smallBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(
//				proformaInvoice.mSysUser.mName + "\nMobile:" +
//				proformaInvoice.mSysUser.mMobileNumber + "\nFax:" +
//				proformaInvoice.mOrganization.mFaxNumber + "\nEmail ID:" +
//				proformaInvoice.mSysUser.mEmail,smallFont);
//		cell.addElement( labelPara );
//		cell.disableBorderSide(Rectangle.TOP);
//		cell.disableBorderSide(Rectangle.RIGHT);
//		cell.disableBorderSide(Rectangle.BOTTOM);
//		footTable.addCell(cell);
//
//		labelPara = new Paragraph("Bank Details:", smallBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		Paragraph p = ;
//		cell.addElement(new Paragraph(
//				proformaInvoice.mBankAccount.mBankName + "\nA/C NO: "+ proformaInvoice.mBankAccount.mAccountNumber + "\nIFSC CODE: "+ proformaInvoice.mBankAccount.mIfsCode +"\nSWIFT: " + proformaInvoice.mBankAccount.mSwift,
//				smallFont));
//		cell.disableBorderSide(Rectangle.TOP);
//		cell.disableBorderSide(Rectangle.LEFT);
//		cell.disableBorderSide(Rectangle.BOTTOM);
//
//		footTable.addCell(cell);
//
//		labelPara = new Paragraph("Signature:", smallBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		cell.setRowspan(2);
//		footTable.addCell(cell);
//
//		cell = new PdfPCell(new Paragraph(
//				"Declaration: We declare that this invoice shows the actual price of the goods described and that all particulars are true and correct.",
//				tinyfont));
//		cell.setColspan(2);
//		footTable.addCell(cell);
//		
//		cell = new PdfPCell(new Paragraph(
//				"TechnoMedia ERP",
//				tinyfont));
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.setColspan(3);
//		cell.setPaddingTop(5f);
//		footTable.addCell(cell);
//
//		try {
//			headerTable.setWidths( new float[] { 25f, 25f, 25f, 25f} );
//			bodyTable.setWidths(new float[] { 10f, 10f, 30f, 10f, 10f, 10f });
//			summaryTable.setWidths(new float[] { 75f, 25f});
//			document.add(headerTable);
//			document.add(bodyTable);
//			document.add(summaryTable);
//			document.add(footTable);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "UNABLE TO GENERATE";
//		}
//		document.close();
//
//		MLogger.i( MLogger.MOD_DB, "Server Path Proforma Invoice pdf :" + fileNameGenerator.mServerFileName );
//		MLogger.i( MLogger.MOD_DB, "Local Path Proforma Invoice pdf :" + fileNameGenerator.mLocalFileName);
//		
//		return fileNameGenerator.mServerFileName;
//	}
//
//*/
//	@Override
//	public String getPageTitle() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	@Override
//	public String getPageSubject() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	public String generateCommercialInvoice( CommercialInvoice commercialInvoice, ArrayList < CommercialInvoiceDetails > commercialDetailsList, String filePath ) {
//		
//		if( commercialInvoice == null )
//			return null;
//		
//		FileNameGenerator fileNameGenerator = new FileNameGenerator();
//		
//		fileNameGenerator.generateFileName( commercialInvoice.mCommercialInvoiceNumber, commercialInvoice.mOrganization.mShortName, filePath );
//		
//		if( fileNameGenerator.mLocalFileName == null && fileNameGenerator.mServerFileName == null ) {
//			
//			return null;
//		}
//
//		Document document = generatePage( fileNameGenerator.mLocalFileName, PageSize.A4, "author", "creator" );
//		document.open();
//		document.newPage();
//		
//		Paragraph heading = new Paragraph("COMMERCIAL INVOICE");
//		heading.setAlignment(Element.ALIGN_CENTER);
//		heading.setSpacingAfter(5);
//		Paragraph labelPara = null;
//		
//
////		top part
//		PdfPTable headerTable = new PdfPTable(3);
//		headerTable.setWidthPercentage(100F);
//		PdfPCell cell = null;
//
//
//		labelPara = new Paragraph("Exporter Information", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mExporter, smallBoldFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(2);
//		headerTable.addCell(cell);
//
//		String DateFormat = "dd.MM.yyyy";
//		String formattedDate = "";
//		if( commercialInvoice.mCommercialInvoiceDate > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(commercialInvoice.mCommercialInvoiceDate);
//		}
//		
//		labelPara = new Paragraph("Invoice No. & Date", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		// need PO number and date
//		labelPara = new Paragraph(commercialInvoice.mCommercialInvoiceNumber + "      DT: " + formattedDate, smallFont);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Exporter's Reference", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("IEC : " + commercialInvoice.mOrganization.mIec, smallFont);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
////		if( commercialInvoice.mProformaInvoice.mProformaInvoiceDate > 0 ) {
////			
////			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
////			formattedDate = sdf.format(commercialInvoice.mProformaInvoice.mProformaInvoiceDate);
////		}
////
////		labelPara = new Paragraph("Performa / Buyer's Ref no and Date", labelBoldFont);
////		cell = new PdfPCell();
////		cell.addElement(labelPara);
////		labelPara = new Paragraph(commercialInvoice.mProformaInvoice.mProformaInvoiceNumber + "             DT: " + formattedDate, smallFont);
////		cell.addElement(labelPara);
////		cell.setColspan(2);
////		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Consignee", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mConsigneeName, smallBoldFont);  // need name of the customer  
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("ADD: "+ commercialInvoice.mConsignee, smallFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(2);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Buyer (if other than consignee)", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mBuyer, smallFont);
//		cell.addElement(labelPara);
//		cell.setColspan(2);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Country of Origin of Goods", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mCountryOfOrigin, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Country of Final Destination", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mDestinationCountry, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Pre Carriage By", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Place of Receipt by Pre Carrier", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		if( commercialInvoice.mLetterOfCredit.mDateOfIssue > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(commercialInvoice.mLetterOfCredit.mDateOfIssue );
//		}
//		
//		labelPara = new Paragraph("Terms of Delivery and Payment", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph( "LC No. " + commercialInvoice.mLetterOfCredit.mDocumentaryCreditNumber + " DATED " + formattedDate
//					+ " OF " + commercialInvoice.mLetterOfCredit.mIssuingBankName + ", " + commercialInvoice.mLetterOfCredit.mIssuingBankAddress
//					+ "\n\n" + commercialInvoice.mLetterOfCredit.mTradeTerm, smallFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(3);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Road/Sea/Air", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mVesselFlightNo, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Port of Loading", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mLoadingPort, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Port of Discharge", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mDischargePort, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Final Destination", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mFinalDestination, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
////		body
//		PdfPTable bodyTable = new PdfPTable(6);
//		float[] columnWidths = new float[] { 10f, 10f, 30f, 10f, 10f, 10f };
//
//		bodyTable.setWidthPercentage(100F);
//
//		labelPara = new Paragraph("Marks & no/ Container No", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("No. & Kind of Pkgs", labelBoldFont);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
//		cell.addElement(labelPara);
//
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Description and Tarrif of Goods", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.LEFT);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Quantity in Kgs/Ltrs", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		//labelPara = new Paragraph("Rate\nIn " + commercialInvoice.mCurrency.mCurrencySymbol + " on "+ commercialInvoice.mProformaInvoice.mRateBasedOnLookup.mLookupName +" Basis per KGS", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Amount\n In " + commercialInvoice.mCurrency.mCurrencySymbol, labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph( commercialInvoice.mShippingMark, smallFont);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.TOP);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Standard Export Worthy Packing", smallFont);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph( commercialInvoice.mHsnDescription, smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		PdfPCell emptyCell = new PdfPCell(new Paragraph("\n")); // adding to fill place;
//		emptyCell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM);
//		bodyTable.addCell(emptyCell); // adding to fill place;
//		bodyTable.addCell(emptyCell); // adding to fill place;
//		bodyTable.addCell(emptyCell);
//
//		// products part
//		emptyCell.setColspan(2);
//		emptyCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
////		emptyCell.disableBorderSide(3);
//
//		// get list of products object and add to the table
////		ArrayList<Product> products = new ArrayList<Product>(); //passed as a parameter
//		PdfPCell infoCell = null; // using to imitate
//		for (int i = 1; i <= commercialDetailsList.size() ; i++) { // i length = number of products
//			emptyCell.setPadding(2f);
//			bodyTable.addCell(emptyCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + i + ". " + commercialDetailsList.get(i - 1).mProduct.mBillingProductName, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP | Rectangle.LEFT);
//			infoCell.setPadding(2f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + commercialDetailsList.get(i - 1).mQuantity, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//			infoCell.setPadding(2f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + commercialDetailsList.get(i - 1).mRate, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//			infoCell.setPadding(2f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(
//					new Paragraph("" + commercialDetailsList.get(i - 1).mAmount, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//			infoCell.setPadding(2f);
//			bodyTable.addCell(infoCell);
//			
//			if( i == commercialDetailsList.size() ){
//				PdfPCell localEmptyCell = new PdfPCell(new Paragraph("\n"));
//				localEmptyCell.setColspan(1);
//				localEmptyCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP );
//				for(int j = 1; j<= 15; j++ ){
//					if( j == 1 || j == 6 || j == 11)
//						bodyTable.addCell(emptyCell);
//					else
//						bodyTable.addCell(localEmptyCell);		
//				}
//			}
//		}
//
//		// Quantity and Total row
//		emptyCell.setColspan(3);
//		emptyCell.enableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//		bodyTable.addCell(emptyCell); // filler
//
//		labelPara = new Paragraph("TOTAL ", smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.setColspan(2);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph(commercialInvoice.mCurrency.mCurrencySymbol + commercialInvoice.mTotalAmount, smallBoldFont); 
//		labelPara.setAlignment(Element.ALIGN_RIGHT);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		String afterDecimalAmount = String.valueOf(commercialInvoice.mTotalAmount);
//        afterDecimalAmount = afterDecimalAmount.substring ( afterDecimalAmount.indexOf ( "." ) + 1 );
//        
//		NumberToWords words = new NumberToWords();
//		labelPara = new Paragraph(
//				"Amount chargeable (in words)   TOTAL: "+ commercialInvoice.mCurrency.mCurrencyWord
//				+ " " + words.convertNumberToWords((long) commercialInvoice.mTotalAmount)+ " AND " 
//				+ words.convertNumberToWords(Long.parseLong(afterDecimalAmount)) + " " + commercialInvoice.mCurrency.mCurrencyDecimal.toUpperCase() + " ONLY",
//				smallFont);
////		labelPara.setSpacingBefore(2f);
//		cell = new PdfPCell();
//		cell.setUseAscender(true);
//		cell.setPadding(3f);
//		cell.addElement(labelPara);
//		cell.setColspan(6);
//		bodyTable.addCell(cell);
//
////		bottom part start here
//		PdfPTable footTable = new PdfPTable(2);
//		footTable.setWidthPercentage(100F);
////						
//		double fobValue = Math.round(( commercialInvoice.mTotalAmount - commercialInvoice.mFreightAmount ) * 100D) / 100D;
//		labelPara = new Paragraph("FOB Value: "+ commercialInvoice.mCurrency.mCurrencySymbol + fobValue, smallFont); // FOB value append to it
//		cell = new PdfPCell();
//		cell.setColspan(2);
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Freight: "+ commercialInvoice.mCurrency.mCurrencySymbol + commercialInvoice.mFreightAmount +"\n\n", smallFont); // append freight value
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Net Weight: "+ commercialInvoice.mNetQuantity +" Kgs", smallFont);
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Gross Weight: "+commercialInvoice.mGrossQuantity +" Kgs", smallFont);
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Packages: " + commercialInvoice.mPackages + "\n\n", smallFont); // get
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("WE CERTIFY THAT THE GOODS ARE OF "+ commercialInvoice.mCountryOfOrigin.toUpperCase() +" ORIGIN\n\n", smallFont); // replace country
//																									// name from country
//																									// of origin;
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("LCAF NO: " + commercialInvoice.mLetterOfCredit.mLcafNo + " BIN NO: " + commercialInvoice.mLetterOfCredit.mBinNo + " TIN NO: " + commercialInvoice.mLetterOfCredit.mTin
//				+ " BEZA NO. " + commercialInvoice.mLetterOfCredit.mBezaNo + ", AND ISSUING BANK BIN NO: " + commercialInvoice.mLetterOfCredit.mBinNo + "\n\n",
//				smallFont);
//		cell.addElement(labelPara);
//
////		labelPara = new Paragraph(
////				"WE ALSO CERTIFY THAT THE DESCRIPTION OF GOODS, QUALITY, QUALITY, UNIT PRICE AND ALL OTHER SPECIFICATIONS OF THE GOODS WILL BE AS PER OUR PERFORMA INVOICE NO. "
////						+ commercialInvoice.mProformaInvoice.mProformaInvoiceNumber + " DATED " + formattedDate + "\n",
////				smallFont);
////		cell.addElement(labelPara);
//
//		cell.disableBorderSide(Rectangle.BOTTOM);
//		cell.setPaddingTop(10f);
//		cell.setPaddingBottom(10f);
//		footTable.addCell(cell);
//
//		cell = new PdfPCell();
//		labelPara = new Paragraph("GSTIN: " + commercialInvoice.mOrganization.mGstin, smallBoldFont);
//		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("LUT NO (ARN) - " + commercialInvoice.mOrganization.mLutArn +"\n\n", smallBoldFont);
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//
//		cell = new PdfPCell(new Paragraph("Signature & Date", smallBoldFont));
//		cell.setRowspan(2);
//		footTable.addCell(cell);
//
//		cell = new PdfPCell(new Paragraph(
//				"Declaration: We declare that this invoice shows the actual price of the goods described and that all particulars are true and correct.",
//				tinyfont));
//		cell.disableBorderSide(Rectangle.TOP);
//		cell.setPaddingTop(5f);
//		footTable.addCell(cell);
//		
//		cell = new PdfPCell(new Paragraph(
//				"TechnoMedia ERP",
//				tinyfont));
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.setColspan(2);
//		cell.setPaddingTop(5f);
//		footTable.addCell(cell);
//
//		try {
//			document.add(heading);
//			document.add(headerTable);
//			bodyTable.setWidths(columnWidths);
//			footTable.setWidths(new float[] { 70f, 30f });
//			document.add(bodyTable);
//			document.add(footTable);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		
//		document.close();
//		
//		MLogger.i( MLogger.MOD_DB, "Server Path Commercial Invoice pdf :" + fileNameGenerator.mServerFileName );
//
//		return fileNameGenerator.mServerFileName;
//
//	}
//	
//	
//	
//public String generatePackingList( CommercialInvoice commercialInvoice, ArrayList < CommercialInvoiceDetails > commercialDetailsList, String filePath ) {
//		
//		if( commercialInvoice == null )
//			return null;
//		
//		FileNameGenerator fileNameGenerator = new FileNameGenerator();
//		
//		fileNameGenerator.generateFileName( commercialInvoice.mCommercialInvoiceNumber + "_Packing_List", commercialInvoice.mOrganization.mShortName, filePath );
//		
//		if( fileNameGenerator.mLocalFileName == null && fileNameGenerator.mServerFileName == null ) {
//			
//			return null;
//		}
//
//		Document document = generatePage( fileNameGenerator.mLocalFileName, PageSize.A4, "author", "creator" );
//		document.open();
//		document.newPage();
//		
//		Paragraph heading = new Paragraph("PACKING LIST");
//		heading.setAlignment(Element.ALIGN_CENTER);
//		heading.setSpacingAfter(5);
//		Paragraph labelPara = null;
//		
//
////		top part
//		PdfPTable headerTable = new PdfPTable(3);
//		headerTable.setWidthPercentage(100F);
//		PdfPCell cell = null;
//
//
//		labelPara = new Paragraph("Exporter Information", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mExporter, smallBoldFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(2);
//		headerTable.addCell(cell);
//
//		String DateFormat = "dd.MM.yyyy";
//		String formattedDate = "";
//		if( commercialInvoice.mCommercialInvoiceDate > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(commercialInvoice.mCommercialInvoiceDate);
//		}
//		
//		labelPara = new Paragraph("Invoice No. & Date", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		// need PO number and date
//		labelPara = new Paragraph(commercialInvoice.mCommercialInvoiceNumber + "      DT: " + formattedDate, smallFont);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Exporter's Reference", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("IEC : " + commercialInvoice.mOrganization.mIec, smallFont);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
////		if( commercialInvoice.mProformaInvoice.mProformaInvoiceDate > 0 ) {
////			
////			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
////			formattedDate = sdf.format(commercialInvoice.mProformaInvoice.mProformaInvoiceDate);
////		}
//
//		labelPara = new Paragraph("Performa / Buyer's Ref no and Date", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		//labelPara = new Paragraph(commercialInvoice.mProformaInvoice.mProformaInvoiceNumber + "             DT: " + formattedDate, smallFont);
//		cell.addElement(labelPara);
//		cell.setColspan(2);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Consignee", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mConsigneeName, smallBoldFont);  // need name of the customer  
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("ADD: "+ commercialInvoice.mConsignee, smallFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(2);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Buyer (if other than consignee)", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mBuyer, smallFont);
//		cell.addElement(labelPara);
//		cell.setColspan(2);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Country of Origin of Goods", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mCountryOfOrigin, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Country of Final Destination", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mDestinationCountry, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Pre Carriage By", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Place of Receipt by Pre Carrier", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		if( commercialInvoice.mLetterOfCredit.mDateOfIssue > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			formattedDate = sdf.format(commercialInvoice.mLetterOfCredit.mDateOfIssue );
//		}
//		
//		labelPara = new Paragraph("Terms of Delivery and Payment", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph( "LC No. " + commercialInvoice.mLetterOfCredit.mDocumentaryCreditNumber + " DATED " + formattedDate
//					+ " OF " + commercialInvoice.mLetterOfCredit.mIssuingBankName + ", " + commercialInvoice.mLetterOfCredit.mIssuingBankAddress
//					+ "\n\n" + commercialInvoice.mLetterOfCredit.mTradeTerm, smallFont);
//		cell.addElement(labelPara);
//		cell.setRowspan(3);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Road/Sea/Air", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mVesselFlightNo, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Port of Loading", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mLoadingPort, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Port of Discharge", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mDischargePort, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
//		labelPara = new Paragraph("Final Destination", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mFinalDestination, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//
////		body
//		PdfPTable bodyTable = new PdfPTable(6);
//		float[] columnWidths = new float[] { 10f, 10f, 30f, 10f, 10f, 10f };
//
//		bodyTable.setWidthPercentage(100F);
//
//		labelPara = new Paragraph("Marks & no/ Container No", labelBoldFont);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("No. & Kind of Pkgs", labelBoldFont);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
//		cell.addElement(labelPara);
//
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Description and Tarrif of Goods", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.LEFT);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("No. Of Pkgs X/Drums", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Gross Wt In Kgs", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Net Wt In Kgs", labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph(commercialInvoice.mShippingMark, smallFont);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.TOP);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph("Standard Export Worthy Packing", smallFont);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		labelPara = new Paragraph( commercialInvoice.mHsnDescription, smallBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
////		cell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//
//		PdfPCell emptyCell = new PdfPCell(new Paragraph("\n")); // adding to fill place;
//		emptyCell.disableBorderSide(Rectangle.TOP | Rectangle.BOTTOM);
//		bodyTable.addCell(emptyCell); // adding to fill place;
//		bodyTable.addCell(emptyCell); // adding to fill place;
//		bodyTable.addCell(emptyCell);
//
//		// products part
//		emptyCell.setColspan(2);
//		emptyCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
////		emptyCell.disableBorderSide(3);
//
//		// get list of products object and add to the table
////		ArrayList<Product> products = new ArrayList<Product>(); //passed as a parameter
//		PdfPCell infoCell = null; // using to imitate
//		for (int i = 1; i <= commercialDetailsList.size() ; i++) { // i length = number of products
//			bodyTable.addCell(emptyCell);
//
//			infoCell = new PdfPCell(new Paragraph( i + ". " + commercialDetailsList.get(i - 1).mProduct.mBillingProductName, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP | Rectangle.LEFT);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + commercialDetailsList.get(i - 1).mSize, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + commercialDetailsList.get(i - 1).mGrossQuantity, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(
//					new Paragraph("" + commercialDetailsList.get(i - 1).mQuantity, smallFont));
//			infoCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//			infoCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//			infoCell.setPadding(4f);
//			bodyTable.addCell(infoCell);
//			
//			if( i == commercialDetailsList.size() ){
//				PdfPCell localEmptyCell = new PdfPCell(new Paragraph("\n"));
//				localEmptyCell.setColspan(1);
//				localEmptyCell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP );
//				for(int j = 1; j<= 15; j++ ){
//					if( j == 1 || j == 6 || j == 11)
//						bodyTable.addCell(emptyCell);
//					else
//						bodyTable.addCell(localEmptyCell);		
//				}
//			}
//		}
//
//
////		bottom part start here
//		PdfPTable footTable = new PdfPTable(2);
//		footTable.setWidthPercentage(100F);
////						
//		double fobValue = Math.round(( commercialInvoice.mTotalAmount - commercialInvoice.mFreightAmount ) * 100D) / 100D;
//		labelPara = new Paragraph("FOB Value: "+ commercialInvoice.mCurrency.mCurrencyWord +" " + fobValue, smallFont); // FOB value append to it
//		cell = new PdfPCell();
//		cell.setColspan(2);
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Freight: "+ commercialInvoice.mCurrency.mCurrencyWord +" " + commercialInvoice.mFreightAmount +"\n\n", smallFont); // append freight value
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Net Weight: "+ commercialInvoice.mNetQuantity +" Kgs", smallFont);
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Gross Weight: "+ commercialInvoice.mGrossQuantity +" Kgs", smallFont);
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("Packages: " + commercialInvoice.mPackages, smallFont); 									
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("\n\nWE CERTIFY THAT THE GOODS ARE OF "+ commercialInvoice.mCountryOfOrigin.toUpperCase() +" ORIGIN\n\n", smallFont);
//																									
//		cell.addElement(labelPara);
//
//		labelPara = new Paragraph("LCAF NO: " + commercialInvoice.mLetterOfCredit.mLcafNo + " BIN NO: " + commercialInvoice.mLetterOfCredit.mBinNo + " TIN NO: " + commercialInvoice.mLetterOfCredit.mTin
//				+ " BEZA NO. " + commercialInvoice.mLetterOfCredit.mBezaNo + ", AND ISSUING BANK BIN NO: " + commercialInvoice.mLetterOfCredit.mBinNo + "\n\n",
//				smallFont);
//		cell.addElement(labelPara);
//
////		labelPara = new Paragraph(
////				"WE ALSO CERTIFY THAT THE DESCRIPTION OF GOODS, QUALITY, QUALITY, UNIT PRICE AND ALL OTHER SPECIFICATIONS OF THE GOODS WILL BE AS PER OUR PERFORMA INVOICE NO. "
////						+ commercialInvoice.mProformaInvoice.mProformaInvoiceNumber + " DATED " + formattedDate + "\n",smallFont);
////		cell.addElement(labelPara);
//
//		cell.disableBorderSide(Rectangle.BOTTOM);
//		cell.setPaddingTop(10f);
//		cell.setPaddingBottom(10f);
//		footTable.addCell(cell);
//	
//		cell = new PdfPCell();
//		cell.disableBorderSide(Rectangle.BOTTOM | Rectangle.TOP);
//		labelPara = new Paragraph();
//		labelPara.add( new Chunk("GST ARN NO: ", smallFont) );
//		labelPara.add( new Chunk(commercialInvoice.mOrganization.mGstArn, smallBoldFont) );
//		cell.addElement(labelPara);
//		
//		labelPara = new Paragraph();
//		labelPara.add( new Chunk("GST IN NO: ", smallFont) );
//		labelPara.add( new Chunk(commercialInvoice.mOrganization.mGstin, smallBoldFont) );
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//		
//		cell = new PdfPCell(new Paragraph("Signature & Date", smallBoldFont));
//		cell.setRowspan(2);
//		footTable.addCell(cell);
//
//		cell = new PdfPCell(new Paragraph(
//				"Declaration: We declare that this invoice shows the actual price of the goods described and that all particulars are true and correct.",
//				tinyfont));
//		cell.disableBorderSide(Rectangle.TOP);
//		cell.setPaddingTop(5f);
//		footTable.addCell(cell);
//		
//		cell = new PdfPCell(new Paragraph(
//				"TechnoMedia ERP",
//				tinyfont));
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.setColspan(2);
//		cell.setPaddingTop(5f);
//		footTable.addCell(cell);
//
//		try {
//			document.add(heading);
//			document.add(headerTable);
//			bodyTable.setWidths(columnWidths);
//			footTable.setWidths(new float[] { 70f, 30f });
//			document.add(bodyTable);
//			document.add(footTable);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		
//		document.close();
//		
//		MLogger.i( MLogger.MOD_DB, "Server Path Packing List pdf :" + fileNameGenerator.mServerFileName );
//
//		return fileNameGenerator.mServerFileName;
//
//	}
//
//
//	
//
//	public String generateCertificateOfOrigin(CommercialInvoice commercialInvoice, ArrayList < CommercialInvoiceDetails > commercialDetailsList, String filePath) throws DocumentException {
//		
//		if( commercialInvoice == null )
//			return null;
//		
//		FileNameGenerator fileNameGenerator = new FileNameGenerator();
//		
//		fileNameGenerator.generateFileName( commercialInvoice.mCommercialInvoiceNumber + "_COO", commercialInvoice.mOrganization.mShortName, filePath );
//		
//		if( fileNameGenerator.mLocalFileName == null && fileNameGenerator.mServerFileName == null ) {
//			
//			return null;
//		}
//
//		Document document = generatePage( fileNameGenerator.mLocalFileName, PageSize.A4, "author", "creator" );
//		PdfWriter writer = null;
//		try {
//			writer = PdfWriter.getInstance(document, new FileOutputStream(fileNameGenerator.mLocalFileName));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		document.open();
//		document.newPage();
//		
//		
//		Paragraph labelPara = null;
//
////		top part
//		PdfPTable headerTable = new PdfPTable(2);
//		headerTable.setWidthPercentage(100F);
//		PdfPCell cell = null;
//		PdfPCell emptyCell = new PdfPCell(new Paragraph("\n"));
//		emptyCell.disableBorderSide(Rectangle.BOX);
//		
//		cell = new PdfPCell();
//		labelPara = new Paragraph("\n\n\n\n" +commercialInvoice.mOrganization.mContactPersonName, smallFont);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph(commercialInvoice.mExporter, smallFont);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("IEC: " + commercialInvoice.mOrganization.mIec + "\n\n\n", smallFont);
//		cell.addElement(labelPara);
//		cell.disableBorderSide(Rectangle.BOX);
//		headerTable.addCell(cell);
//		
//		headerTable.addCell(emptyCell);
//		labelPara = new Paragraph("\n\n\n\n" + commercialInvoice.mCustomer.mBusinessName, smallBoldFont);
//		cell = new PdfPCell();
//		labelPara.setSpacingBefore(50f);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("ADD: " + commercialInvoice.mCustomer.mAddress, smallFont);
//		labelPara.setSpacingAfter(40f);
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//		
//		headerTable.addCell(emptyCell);
//		
//		cell = new PdfPCell();
//		labelPara = new Paragraph("\n\n\n\n" + commercialInvoice.mVesselFlightNo + "          FROM " + commercialInvoice.mLoadingPort , smallFont); // no \t support
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell.addElement(labelPara);
//		labelPara = new Paragraph("\n" + commercialInvoice.mDischargePort + "          TO " + commercialInvoice.mFinalDestination, smallFont); // no \t support  needs fixing
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		labelPara.setSpacingAfter(40f);
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		headerTable.addCell(cell);
//	
//		headerTable.addCell(emptyCell);
//		
////		body
//		PdfPTable bodyTable = new PdfPTable(6);
//		bodyTable.setWidthPercentage(100F);
//		
//		emptyCell.setColspan(6);
//		bodyTable.addCell(emptyCell);
//		bodyTable.addCell(emptyCell);
//		
//		emptyCell.setColspan(1);
//		bodyTable.addCell(emptyCell); //filler
//		
//		labelPara = new Paragraph( commercialInvoice.mShippingMark, smallFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph( commercialInvoice.mHsnDescription, labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		labelPara.setSpacingAfter(10f);
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph( commercialInvoice.mCountryOfOrigin, labelBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		labelPara = new Paragraph("Grss Wt: "+ commercialInvoice.mGrossQuantity +"Kgs\nNet Wt: " + commercialInvoice.mNetQuantity, tinyBoldFont);
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		labelPara.setSpacingAfter(15f);
//		cell = new PdfPCell();
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		bodyTable.addCell(cell);
//		
//		bodyTable.addCell(emptyCell); //filler
//		
//		String proformaInvoiceDate = "", commercialInvoiceDate = "", lcDate = "";
////		
////		if(  commercialInvoice.mProformaInvoice.mProformaInvoiceDate > 0
////		   && commercialInvoice.mCommercialInvoiceDate > 0
////		   && commercialInvoice.mLetterOfCredit.mDateOfIssue > 0) {
////			
////			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
////			proformaInvoiceDate = sdf.format( commercialInvoice.mProformaInvoice.mProformaInvoiceDate );
////			commercialInvoiceDate = sdf.format( commercialInvoice.mCommercialInvoiceDate );
////			lcDate = sdf.format( commercialInvoice.mLetterOfCredit.mDateOfIssue );
////		}
//		
//		labelPara = new Paragraph(
//		"INVOICE NO\n" + 
//		commercialInvoice.mCommercialInvoiceNumber + 
//		"\nDT. " + commercialInvoiceDate + 
//		"\nL/C No. " + commercialInvoice.mLetterOfCredit.mDocumentaryCreditNumber + 
//		"\nDT: " + lcDate + 
//		"\nP.I. No."+
////		"\n" + commercialInvoice.mProformaInvoice.mProformaInvoiceNumber +
//		"\nDT. " + proformaInvoiceDate +
//		"\nLCAF NO. " + commercialInvoice.mLetterOfCredit.mLcafNo + "," +
//		"\nBIN NO. " + commercialInvoice.mLetterOfCredit.mBinNo + "," +
//		"\nTIN: " + commercialInvoice.mLetterOfCredit.mTin + "," +
//		"\nBEZA NO. " + commercialInvoice.mLetterOfCredit.mBezaNo + "," +
//		"\nAND ISSUING BANK BIN NO - " + commercialInvoice.mLetterOfCredit.mIssuingBankBinNo, tinyBoldFont);
//		cell = new PdfPCell();
//		cell.setUseAscender(true);
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		
//		
////		blankCell
//		PdfPCell largeCell = null;
//		PdfPCell infoCell = null;
//		for (int i = 1; i <= commercialDetailsList.size(); i++) { // i length = number of products
//
//			infoCell = new PdfPCell(new Paragraph(i + "", tinyBoldFont)); //name and desc
//			infoCell.disableBorderSide(Rectangle.BOX);
//			infoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			infoCell.setVerticalAlignment(Element.ALIGN_TOP);
//			infoCell.setUseAscender(true);
//			infoCell.setPaddingBottom(0);
//			bodyTable.addCell(infoCell);
//			
//			emptyCell.setUseAscender(true);
//			emptyCell.setPaddingBottom(0);
//			bodyTable.addCell(emptyCell);
//			
//			infoCell = new PdfPCell(new Paragraph( commercialDetailsList.get(i - 1).mProduct.mBillingProductName, tinyBoldFont)); //name and desc
//			infoCell.disableBorderSide(Rectangle.BOX);
//			infoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//			infoCell.setVerticalAlignment(Element.ALIGN_TOP);
//			infoCell.setUseAscender(true);
//			infoCell.setPaddingBottom(0);	
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("HS CODE " + commercialDetailsList.get(i - 1).mProduct.mHsnCode, tinyBoldFont)); //hscode of individual products
//			infoCell.disableBorderSide(Rectangle.BOX);
//			infoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			infoCell.setVerticalAlignment(Element.ALIGN_TOP);
//			infoCell.setUseAscender(true);
//			infoCell.setPaddingBottom(0);
//			bodyTable.addCell(infoCell);
//
//			infoCell = new PdfPCell(new Paragraph("" + commercialDetailsList.get(i - 1).mQuantity, tinyBoldFont)); //unrecognized numbers
//			infoCell.disableBorderSide(Rectangle.BOX);
//			infoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			infoCell.setVerticalAlignment(Element.ALIGN_TOP);
//			infoCell.setUseAscender(true);
//			infoCell.setPaddingBottom(0);
//			bodyTable.addCell(infoCell);
//			
//			if(i == 1) {
//				largeCell = bodyTable.addCell(cell);			
//			}
//			else {
//				largeCell.setRowspan(i + 1);
//			}
//				
//		}
//		
//		
//		PdfPTable footTable = new PdfPTable(2);
//		
//		footTable.addCell(emptyCell);
//		
//		labelPara = new Paragraph("\n" + commercialInvoice.mFinalDestination, FontFactory.getFont("/fonts/Verdana.ttf", 12, Font.NORMAL, BaseColor.BLACK));
//		labelPara.setAlignment(Element.ALIGN_CENTER);
//		cell = new PdfPCell();
//		cell.setMinimumHeight(120f);
//		cell.disableBorderSide(Rectangle.BOX);
//		cell.addElement(labelPara);
//		footTable.addCell(cell);
//		footTable.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));
//		footTable.setLockedWidth(true);
//
//		
//		try {
//			bodyTable.setWidths(new float[] {5f,10f, 15f, 10f, 10f,10f});
//			document.add(headerTable);
//			document.add(bodyTable);
//			footTable.writeSelectedRows(0, -1,
//				    document.left(document.leftMargin()),
//				    footTable.getTotalHeight() + document.bottom(document.bottomMargin()),
//				    writer.getDirectContent());
//			
//			
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} 
//		
//		document.close();
//		System.out.println("COO GENERATED");
//		MLogger.i( MLogger.MOD_DB, "Local Path Certificate of Origin pdf :" + fileNameGenerator.mLocalFileName );
//		MLogger.i( MLogger.MOD_DB, "Server Path Certificate of Origin pdf :" + fileNameGenerator.mServerFileName );
//		
//		return fileNameGenerator.mServerFileName;
//		
//	}
//
//}
//
