//package com.technomedia.routealert.report;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.List;
//import com.itextpdf.text.ListItem;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.technomedia.logger.MLogger;
//import com.technomedia.routealert.database.CommercialInvoice;
//import com.technomedia.routealert.database.NegotiationData;
//import com.technomedia.routealert.database.utils.NumberToWords;
//import com.technomedia.routealert.report.base.AbstractPdfReport;
//import com.technomedia.routealert.server.common.DefaultServerSettings;
//
//public class NegotiationDocuments extends AbstractPdfReport {
//
//	protected class FileNameGenerator {
//
//		public String mLocalFileName = null; // file with path used by the Java
//												// program to write the pdf file
//		public String mServerFileName = null; // file with path used by the
//												// browser to access the pdf
//												// file
//
//		public void generateFileName(String documentNumber,
//				String orgShortName, String contextPath) {
//
//			String fileName = documentNumber;
//			fileName = fileName.replace( '/', '_' );
//			fileName = fileName.replace( '-', '_' );
//			fileName += "_NegotiationDocs.pdf";
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
//		};
//	}
//	private final byte typeExchange = 1;
//	private final byte typeAcceptance = 2;
//	private final byte typeCourier = 3;
//	private final byte typeInsurance = 4;
//	private final byte typeShipmentAdvice = 5;
//	
//	private final byte typeBeneficiaryFirst = 6;
//	private final byte typeBeneficiarySecond = 7;
//	private final byte typeBeneficiaryThird = 8;
//	
//	private String DateFormat = "dd.MM.yyyy";
//	private CommercialInvoice commercialInvoice;
//	private NegotiationData negotiationData;
//
//	public String pathToDocuments;
//
//	private final Font smallBoldFont = new Font( Font.FontFamily.HELVETICA, 12,
//			Font.BOLD | Font.UNDERLINE, BaseColor.BLACK );
//
//	private final Font smallFont = new Font( Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK );
//	// Constructor
//	public NegotiationDocuments( CommercialInvoice commercialInvoice, NegotiationData negotiationData ) {
//
//		this.commercialInvoice = commercialInvoice;
//		this.negotiationData = negotiationData;
//	}
//
//	public static void main(String[] args) {
//
//		long start = System.currentTimeMillis();
//
////		CommercialInvoice commercialInvoice = new CommercialInvoice();
//
////		NegotiationDocuments negotiationDocuments = new NegotiationDocuments(
////				commercialInvoice );
//
//		// NegotiationData negotiationData = new NegotiationData();
//		// String pathTo = "F:\\extra"; // directory path
//		// negotiationData.mLcCreditNo = "350218020082";
//		// negotiationData.mLcCreditDate = "22.10.2018"; // not the best
//		// approach
//		// negotiationData.mBankHeader =
//		// "MODHUMOTI BANK LTD. MOTIJHEEL BRANCH, DHAKA, BANGLADESH";
//		// negotiationData.mBankHeaderWithNl =
//		// "MODHUMOTI BANK LTD.\nMOTIJHEEL BRANCH,\nDHAKA, BANGLADESH";
//		// negotiationData.mToAddressWithNl =
//		// "MODHUMOTI BANK LTD.\nOFF-SHORE BANKING UNIT CEPZ,\nCHITTAGONG, BANGLADESH";
//		// negotiationData.mToAddress =
//		// "MODHUMOTI BANK LTD. OFF-SHORE BANKING UNIT CEPZ, CHITTAGONG, BANGLADESH";
//		// // negotiationData.mTypeOfExchange = "FIRST OF EXCAHNGE";
//		// negotiationData.mAmount = 99154F;
//		// negotiationData.mCommercialInvoiceNumber = "TE/18-19/52";
//		// negotiationData.mCommercialInvoiceDate = "29.10.2018";
//		// negotiationData.mLetterDate = "12.11.2018";
//		// negotiationData.mPayTo =
//		// "Union Bank of India, Overseas Branch, 9 India Exchange Place, Kolkata - 700001";
//		//
//		// negotiationData.mDocumentDate = "8.11.2018"; // when the document was
//		// created
//		// negotiationData.mParentCompany = "Teximco Enterprise";
//		// negotiationData.mCustomerName =
//		// "M/S THAI FOILS AND POLYMER INDUSTRIES LTD.";
//		// negotiationData.mCustomerAddressWithNl =
//		// "MEGHNA INDUSTRIES ECONOMIC ZONE,\nSONARGAON, NARAYANGONJ, BANGLADESH";
//		// negotiationData.mInsuranceCompany =
//		// "M/S. Republic Insurance Company Limited";
//		// negotiationData.mInsuranceCompanyAddressWithNl =
//		// "HATKHOLA BRANCH, 2nd Floor,\n161, MOTIJHEEL C/A,\nDHAKA-1000, BANGLADESH";
//		// negotiationData.mInsuranceCompanyEmail = "ricilhkb@gmail.com";
//		// negotiationData.mInsuranceCoverNote = "RICL/HKB/MC-0071/02/2018";
//		// negotiationData.mInsuranceCoverDate = "15.02.2018";
//		// negotiationData.mLcafNo = "007639";
//		// negotiationData.mBinNo = "000119969";
//		// negotiationData.mTinNo = "179431977769";
//		// negotiationData.mBankBinNo = "000001507";
//		// negotiationData.mHsCode.add("3215.19.10");
//		// negotiationData.mHsCode.add("3215.11.10");
//		// negotiationData.mBezaNo = "19MAR20181000195";
//		// negotiationData.mLadingNo = "SLNASACTG1810144";
//		// negotiationData.mPackingListNo = "TE/18-19/52";
//		// negotiationData.mEtdAddress = "Nhava Sheva, Mumbai, India";
//		// negotiationData.mEtdDate = "05.11.2018";
//		// negotiationData.mEtaAddress = "Chattogram Sea Port, Bangladesh";
//		// negotiationData.mEtaDate = "25.11.2018";
//		// negotiationData.mNetQuantity = 26000F;
//		// negotiationData.mType1DrumsNo = 272;
//		// negotiationData.mType2DrumsNo = 940;
//		// negotiationData.mType1DrumCap = 25.00F;
//		// negotiationData.mType2DrumCap = 20.00F;
//		// negotiationData.mTotalPallets = 50;
//
//		// Document document = negotiationDocuments.generatePage(pathTo +
//		// "\\NegotiationDocuments.pdf", PageSize.A4,
//		// "author", "creator");
//		//
//		// document.open();
//		// negotiationDocuments.generateAllDocuments(document, negotiationData);
//		// document.close();
//		System.out.println( "Finished in: "
//				+ ( System.currentTimeMillis() - start ) + "ms" );
//	}
//
//	// generate all negotiation documents
//	public String generateAllDocuments(String filePath) {
//
//		if ( this.commercialInvoice == null )
//			return null;
//
//		FileNameGenerator fileNameGenerator = new FileNameGenerator();
//
//		fileNameGenerator.generateFileName( commercialInvoice.mCommercialInvoiceNumber,
//				commercialInvoice.mOrganization.mShortName, filePath );
//
//		if ( fileNameGenerator.mLocalFileName == null
//				&& fileNameGenerator.mServerFileName == null ) {
//
//			return null;
//		}
//
//		Document document = generatePage( fileNameGenerator.mLocalFileName, PageSize.A4, "author", "creator" );
//		document.open();
//		document.newPage();
//
//		try {
//
//			// exchange documents
//			generateFirstSet( document, "FIRST OF EXCHANGE", this.typeExchange );
//			generateFirstSet( document, "SECOND OF EXCHANGE", this.typeExchange );
//			generateFirstSet( document, "FIRST OF EXCHANGE", this.typeAcceptance );
//			generateFirstSet( document, "SECOND OF EXCHANGE", this.typeAcceptance );
//
//			// beneficiary certificates
//			generateSecondSet( document, this.typeBeneficiaryFirst );
//			generateSecondSet( document, this.typeBeneficiarySecond );
//			generateSecondSet( document, this.typeBeneficiaryThird );
//
//			// courier, insurance and shipment
//			generateThirdSet( document, this.typeCourier );
//			generateThirdSet( document, this.typeInsurance );
//			generateThirdSet( document, this.typeShipmentAdvice );
//		} catch ( Exception e ) {
//			e.printStackTrace();
//		}
//		
//		document.close();
//		
//		MLogger.i( MLogger.MOD_DB, "Server Path Negotiation Documents pdf :" + fileNameGenerator.mServerFileName );
//		
//		return fileNameGenerator.mServerFileName;
//	}
//
//	/*
//	 * @param typeOfDocument need this to differentiate between documents as 3rd
//	 * param is not enough to decide
//	 */
//	public String generateFirstSet(  Document document, String typeOfExchange, byte typeOfDocument) throws DocumentException {
//
//
//		document.newPage();
//		
//		String currency = commercialInvoice.mCurrency.mCurrencyWord + " " + commercialInvoice.mCurrency.mCurrencySymbol;
//		
//		String dateOfIssue = "", invoiceDate = "";
//		
//		if( commercialInvoice.mLetterOfCredit.mDateOfIssue > 0 && commercialInvoice.mCommercialInvoiceDate > 0) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			dateOfIssue = sdf.format( commercialInvoice.mLetterOfCredit.mDateOfIssue );
//			invoiceDate = sdf.format( commercialInvoice.mCommercialInvoiceDate );
//		}
//		
//		
//		
//		// add header text
//		String headerText = "DRAWN UNDER DOCUMENTARY CREDIT NO. "
//				+ commercialInvoice.mLetterOfCredit.mDocumentaryCreditNumber + " DTD. "
//				+ dateOfIssue + " OF "
//				+ commercialInvoice.mLetterOfCredit.mIssuingBankName + ", " + commercialInvoice.mLetterOfCredit.mIssuingBankAddress + ".";
//		
//		Paragraph emptyPara = new Paragraph(" ");
//		emptyPara.setSpacingAfter(40);
//		document.add( emptyPara );
//		Paragraph headerTextPara = new Paragraph();
////		headerTextPara.setFont( normalFont );
//		headerTextPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		headerTextPara.setSpacingAfter( 50 );
//		headerTextPara.add( headerText );
//
//		document.add( headerTextPara );
//
//
//		String exchText = "Exchange for "+ currency  + commercialInvoice.mTotalAmount;
//		Paragraph exchTextPara = new Paragraph();
////		exchTextPara.setFont( normalFont );
//		exchTextPara.setAlignment( Element.ALIGN_LEFT );
//		exchTextPara.setSpacingAfter( 10 );
//		exchTextPara.add( exchText );
//		document.add( exchTextPara );
//
//	
//		// invoice date
//		String invText = "NO: " + commercialInvoice.mCommercialInvoiceNumber + "\n Dated: "
//				+ invoiceDate;
//		Paragraph invTextPara = new Paragraph();
////		invTextPara.setFont( normalFont );
//		invTextPara.setAlignment( Element.ALIGN_RIGHT );
//		invTextPara.setSpacingAfter( 50 );
//		invTextPara.add( invText );
//		document.add( invTextPara );
//
//		String start, toPay, foot;
//		NumberToWords numberToWords = new NumberToWords();
//		String amountWord = numberToWords
//				.convertNumberToWords( (long) commercialInvoice.mTotalAmount );
//		// MoneyConverters converter =
//		// MoneyConverters.ENGLISH_BANKING_MONEY_VALUE;
//		// String amountWord = converter.asWords(new BigDecimal("12345.22"));
//		switch ( typeOfDocument ) {
//
//		case 1:
//			start = "At sight";
//			toPay = commercialInvoice.mLetterOfCredit.mReceivingBankName + commercialInvoice.mLetterOfCredit.mReceivingBankAddress;
//			foot = commercialInvoice.mLetterOfCredit.mIssuingBankName + "\n" + commercialInvoice.mLetterOfCredit.mIssuingBankAddress;
//			break;
//		case 2:
//			start = "At 180 days from the date of Acceptance";
//			toPay = commercialInvoice.mLetterOfCredit.mIssuingBankName + commercialInvoice.mLetterOfCredit.mIssuingBankAddress;
//			foot = commercialInvoice.mLetterOfCredit.mIssuingBankName + "\n" + commercialInvoice.mLetterOfCredit.mIssuingBankAddress;
//			break;
//		default:
//			start = "START NOT SET";
//			toPay = "TO PAY NOT SET";
//			foot = "FOOT NOT SET";
//		}
//		
//		
//		// content
//		StringBuilder contentText = new StringBuilder();
//		contentText
//				.append( start )
//				.append( " please pay the " )
//				.append( typeOfExchange )
//				.append( " to " )
//				.append( toPay )
//				.append( ", the sum of " + commercialInvoice.mCurrency.mCurrencyWord + " ")
//				.append( amountWord )
//				.append(
//						" Only for value received as per documents attached and place the same of the A/C of our Invoice No. " )
//				.append( commercialInvoice.mCommercialInvoiceNumber ).append( " Dated " )
//				.append( invoiceDate ).append( " L/C No. " )
//				.append( commercialInvoice.mLetterOfCredit.mDocumentaryCreditNumber ).append( " Dated " )
//				.append( dateOfIssue );
//
//		Paragraph contentTextPara = new Paragraph( contentText.toString() );
////		contentTextPara.setFont( normalFont );
//		contentTextPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		contentTextPara.setSpacingBefore( 20 );
//		contentTextPara.setSpacingAfter( 20 );
//
//		document.add( contentTextPara );
//
//		String footerText = "To, \n" + foot;
//
//		Paragraph footPara = new Paragraph( footerText );
//		footPara.setAlignment( Element.ALIGN_LEFT );
//		footPara.setSpacingBefore( 50 );
////		footPara.setFont( normalFont );
//
//		document.add( footPara );
//
//		return "";
//
//	}
//
//	/*
//	 * @param ttypeOfDocument need this to differentiate between documents as 2nd
//	 */
//	public String generateSecondSet(Document document, byte typeOfDocument) throws DocumentException {
//		
//		
//		document.newPage();
//
//		String dateOfIssue = "", proformaInvoiceDate = "", ngotiationDocumentDate = "";
//		
//		if( commercialInvoice.mLetterOfCredit.mDateOfIssue > 0 
//			&& commercialInvoice.mCommercialInvoiceDate > 0
////			&& commercialInvoice.mProformaInvoice.mProformaInvoiceDate > 0
//			&& negotiationData.mNegotiationDocDate > 0 ) {
//			
//			SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//			dateOfIssue = sdf.format( commercialInvoice.mLetterOfCredit.mDateOfIssue );
////			proformaInvoiceDate = sdf.format( commercialInvoice.mProformaInvoice.mProformaInvoiceDate );
//			ngotiationDocumentDate = sdf.format( negotiationData.mNegotiationDocDate );
//		}
//		
//		
//		Paragraph emptyPara = new Paragraph(" ");
//		emptyPara.setSpacingAfter(30);
//		document.add( emptyPara );
//
//		// adding date
//		Paragraph dateTextPara = new Paragraph( "Dated: " + ngotiationDocumentDate );
//		dateTextPara.setFont( normalFont );
//		dateTextPara.setSpacingAfter( 20 );
//		document.add( dateTextPara );
//		
//		// adding title
//		Paragraph TitleTextPara = new Paragraph( "BENEFICIARY'S CERTIFICATE", this.smallBoldFont );
//		TitleTextPara.setAlignment( Element.ALIGN_CENTER );
//		TitleTextPara.setSpacingAfter( 20 );
//		document.add( TitleTextPara );
//		
//
//		StringBuilder firstPara = new StringBuilder();
//		firstPara.append(
//				"Dear Sir,\n\nThis is to intimate you that we have shipped the materials to " )
//				.append( commercialInvoice.mCustomer.mBusinessName )
//				.append( ", under Performa Invoice No. " )
////				.append( commercialInvoice.mProformaInvoice.mProformaInvoiceNumber ).append( " dated " )
//				.append( proformaInvoiceDate ).append( ", L/C No. " )
//				.append( commercialInvoice.mLetterOfCredit.mDocumentaryCreditNumber ).append( " dated " )
//				.append( dateOfIssue  ).append( " of " )
//				.append( commercialInvoice.mLetterOfCredit.mIssuingBankName + ", " + commercialInvoice.mLetterOfCredit.mIssuingBankAddress );
//
//		String secondPara = "LCAF NO. " + commercialInvoice.mLetterOfCredit.mLcafNo + " BIN NO. " + commercialInvoice.mLetterOfCredit.mBinNo
//				+ ", TIN NO. " + commercialInvoice.mLetterOfCredit.mTin + ", BEZA NO. "
//				+ commercialInvoice.mLetterOfCredit.mBezaNo + ", ISSUING BANK BIN NO - " + commercialInvoice.mLetterOfCredit.mIssuingBankBinNo
//				+ ", AND H.S.CODE: " + commercialInvoice.mHsnCodes + " AND SHIPPING MARK: " + commercialInvoice.mShippingMark;
//		
//		
//		
//		String thirdPara = "";
//		
//
//		switch ( typeOfDocument ) {
//		case 6:
//			thirdPara = "We certify that Importer's Name, Address and TIN no. have either been printed or written in indelible ink at least 2 (two) percent of the packages.";
//			break;
//		case 7:
//			thirdPara = "We certify that shipment/transshipment by any Israeli sea vessel has been prohibited.";
//			break;
//		case 8:
//			thirdPara = "We certify that the quality, quantity, classification, description, specification and weight of the goods are fully as per LC clause.";
//			break;
//		default:
//			
//		}
//
//		String footNote = "\n\n" + commercialInvoice.mOrganization.mOrganizationName + "\n\n\nAuthorized Signatory";
//
//		// Adding all to document now
//
//		Paragraph introPara = new Paragraph( firstPara.toString() );
//		Paragraph conPara = new Paragraph( secondPara );
//		Paragraph endPara = new Paragraph( thirdPara );
//		Paragraph footPara = new Paragraph( footNote );
//		introPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		introPara.setSpacingAfter( 20 );
//		conPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		conPara.setSpacingAfter( 20 );
//		endPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		endPara.setSpacingAfter( 10 );
//
//		document.add( introPara );
//		document.add( conPara );
//		document.add( endPara );
//		document.add( footPara );
//		
//		return "GENERATION SUCCESSFULL";
//	 
//	}
//
//	/*
//	 * @param path directory path where files will be generated E:\\
//	 * 
//	 * @param data Negotiation Related data
//	 */
//	public String generateThirdSet(Document document, byte typeOfDocument) throws DocumentException {
//		
//		
//		document.newPage();
//		
//		SimpleDateFormat sdf=new SimpleDateFormat( DateFormat );
//		String currency = commercialInvoice.mCurrency.mCurrencyWord + " " + commercialInvoice.mCurrency.mCurrencySymbol;
//		String dateOfIssue = "", shipmentDate = "", invoiceDate = "", proformaInvoiceDate = "", ngotiationDocumentDate = "", insuranceCoverDate = "";
//		
//		if( commercialInvoice.mLetterOfCredit.mDateOfIssue > 0 
//			&& commercialInvoice.mCommercialInvoiceDate > 0
////			&& commercialInvoice.mProformaInvoice.mProformaInvoiceDate > 0
//			&& commercialInvoice.mLetterOfCredit.mInsuranceCoverDate > 0
//			&& negotiationData.mNegotiationDocDate > 0 
//			&& negotiationData.mShipmentDate > 0) {
//			
//			dateOfIssue = sdf.format( commercialInvoice.mLetterOfCredit.mDateOfIssue );
//			invoiceDate = sdf.format( commercialInvoice.mCommercialInvoiceDate );
////			proformaInvoiceDate = sdf.format( commercialInvoice.mProformaInvoice.mProformaInvoiceDate );
//			insuranceCoverDate = sdf.format(commercialInvoice.mLetterOfCredit.mInsuranceCoverDate);
//			ngotiationDocumentDate = sdf.format( negotiationData.mNegotiationDocDate );
//			shipmentDate = sdf.format( negotiationData.mShipmentDate  );
//		}
//		
//		
//		String titleText;
//		String subjectText; 
//		String addressText;
//		switch ( typeOfDocument ) {
//		case 3:
//			titleText = "By Courier";
//			subjectText = "Ref: One set of non negotiable shipping documents";
//			addressText = "To,\n" + commercialInvoice.mCustomer.mBusinessName + "\nADD: "
//					+ commercialInvoice.mCustomer.mAddress;
//			break;
//		case 4:
//			titleText = "Shipment Advice";
//			subjectText = "Ref: Insurance Cover Note No. "
//					+ commercialInvoice.mLetterOfCredit.mInsuranceCoverNo + " Dated "     
//					+ insuranceCoverDate; //commercialInvoice.mInsuranceCoverDate
//			addressText = "To,\n" + commercialInvoice.mCustomer.mBusinessName + "\nADD: "
//					+ commercialInvoice.mCustomer.mAddress;
//			break;
//		case 5:
//			titleText = "Shipment Advice\nE-mail: "
//					+ commercialInvoice.mLetterOfCredit.mInsuranceCompany.mEmail; // commercialInvoice.mInsuranceCompanyEmail;
//			subjectText = "Ref: Insurance Cover Note No. "
//					+ commercialInvoice.mLetterOfCredit.mInsuranceCoverNo + " Dated "  //commercialInvoice.mInsuranceCoverNote
//					+ insuranceCoverDate; 			//commercialInvoice.mInsuranceCoverDate
//			addressText = "To,\n" + commercialInvoice.mLetterOfCredit.mInsuranceCompany.mName + "\nADD: "
//					+ commercialInvoice.mLetterOfCredit.mInsuranceCompany.mAddress;
//			break;
//		default:
//			titleText = "TITLE NOT SET";
//			subjectText = "SUBJECT NOT SET";
//			addressText = "ADDRESS UNKOWN";
//		}
//		// adding title
//		Paragraph TitleTextPara = new Paragraph( titleText, this.smallBoldFont );
//		TitleTextPara.setAlignment( Element.ALIGN_CENTER );
//		TitleTextPara.setSpacingAfter( 20 );
//		document.add( TitleTextPara );
//
//	
//		// adding date
//		Paragraph dateTextPara = new Paragraph( "Dated: " + ngotiationDocumentDate, smallFont);
//		dateTextPara.setSpacingAfter( 20 );
//		document.add( dateTextPara );
//
//		// adding header address
//		
//		Paragraph addressPara = new Paragraph( addressText, smallFont);
//		addressPara.setSpacingAfter( 30 );
//		document.add( addressPara );
//
//		// adding subject
//		Paragraph subjectPara = new Paragraph( subjectText, this.smallBoldFont );
//		subjectPara.setAlignment( Element.ALIGN_CENTER );
//		subjectPara.setSpacingAfter( 30 );
//		document.add( subjectPara );
//
//		StringBuilder intro = new StringBuilder();
//		intro.append(
//				"Dear Sir,\n\nThis is to intimate you that we have shipped the materials to " )
//				.append( commercialInvoice.mCustomer.mBusinessName )
//				.append( ", under Performa Invoice No. " )
////				.append( commercialInvoice.mProformaInvoice.mProformaInvoiceNumber ).append( " dated " )
//				.append( proformaInvoiceDate ).append( ", L/C No. " )
//				.append( commercialInvoice.mLetterOfCredit.mDocumentaryCreditNumber ).append( " dated " )
//				.append( dateOfIssue  ).append( " of " )
//				.append( commercialInvoice.mLetterOfCredit.mIssuingBankName + ", " + commercialInvoice.mLetterOfCredit.mIssuingBankAddress );
//
//		String con = "LCAF NO. " + commercialInvoice.mLetterOfCredit.mLcafNo + " BIN NO. " + commercialInvoice.mLetterOfCredit.mBinNo
//				+ ", TIN NO. " + commercialInvoice.mLetterOfCredit.mTin + ", BEZA NO. "
//				+ commercialInvoice.mLetterOfCredit.mBezaNo + ", ISSUING BANK BIN NO - " + commercialInvoice.mLetterOfCredit.mIssuingBankBinNo
//				+ ", AND H.S.CODE: " + commercialInvoice.mHsnCodes + " AND SHIPPING MARK: " + commercialInvoice.mShippingMark;
//		
//		// HS Code can have more than one entries.. refactor accordingly in
//		// future
//		
//		String etdDate = "";
//		String etaDate = "";
////		if( commercialInvoice.mProformaInvoice.mExpectedDeliveryDate > 0 ) {
//			
//			
//			// need dispatch date; currently making it same as delivery
////			etdDate = sdf.format( commercialInvoice.mProformaInvoice.mExpectedDeliveryDate ); 
////			etaDate = sdf.format( commercialInvoice.mProformaInvoice.mExpectedDeliveryDate );
//	//	}
//		
//		String estimate = "ETD (" + commercialInvoice.mLoadingPort + ") - " + etdDate + " and ETA (" + commercialInvoice.mDischargePort +
//				") - " + etaDate + "\nWe are enclosing the following documents as per L/C clause for your kind perusal:";
//		
//		
//		List enclosure = new List( true, 20 );
//		enclosure.add( new ListItem( "Invoice No. " + commercialInvoice.mCommercialInvoiceNumber
//				+ " Dt. " + invoiceDate + " for "+ currency + commercialInvoice.mTotalAmount, smallFont ) );
//		enclosure.add( new ListItem( "Packing List No. " +   commercialInvoice.mCommercialInvoiceNumber      // commercialInvoice.mPackingListNo
//				+ " Dt. " + invoiceDate , smallFont) ); // packing list Date
//		
//		enclosure.add( new ListItem( negotiationData.mTypeOfTransportLookup.mLookupName + " " + negotiationData.mShipmentNumber  , smallFont ) );  // commercialInvoice.mLadingNo
//
//		if( negotiationData.mTypeOfTransportLookup.mLookupName == "Bill of Lading"){
//			enclosure.add( new ListItem( "Container No: " + negotiationData.mContainerNumber, smallFont ) );
//			
//		}
//		enclosure.add( new ListItem( "Name of Transport: " + negotiationData.mTransporter, smallFont ) );
//
//		enclosure.add( new ListItem( "Certificate of Origin", smallFont ) );
//		enclosure.add( new ListItem( "Shipment Date: " + shipmentDate , smallFont) );
//		enclosure.add( new ListItem( "Port of Loading: " + commercialInvoice.mLoadingPort + " & Port of Discharge: " + commercialInvoice.mDischargePort, smallFont) );
//		enclosure.add( new ListItem( "Quantity of Goods: " + commercialInvoice.mNetQuantity + " Kgs", smallFont) );
//		String end = "\nQuantity Shipped - "+ commercialInvoice.mGrossQuantity +"Kgs. And Packing - " + commercialInvoice.mPackages +".\nKindly acknowledge the receipt";
//
//		String foot = "Thanking you,\nYours faithfully,\nFor "
//				+ commercialInvoice.mOrganization.mOrganizationName + "\n\nAuthorized Signatory";
//
//		// Adding all to document now
//
//		Paragraph introPara = new Paragraph( intro.toString(),smallFont );
//		Paragraph conPara = new Paragraph( con,smallFont);
//		Paragraph estPara = new Paragraph( estimate,smallFont );
//		Paragraph endPara = new Paragraph( end,smallFont );
//		Paragraph footPara = new Paragraph( foot,smallFont );
//		introPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		introPara.setSpacingAfter( 20 );
//		conPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		conPara.setSpacingAfter( 20 );
//		estPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		estPara.setSpacingAfter( 20 );
//		endPara.setAlignment( Element.ALIGN_JUSTIFIED );
//		endPara.setSpacingAfter( 10 );
//
//		document.add( introPara );
//		document.add( conPara );
//		document.add( estPara );
//		document.add( enclosure );
//		document.add( endPara );
//		document.add( footPara );
//		
//		return "GENERATION SUCCESSFULL";
//
//	}
//
//	@Override
//	public String getPageTitle() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getPageSubject() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
