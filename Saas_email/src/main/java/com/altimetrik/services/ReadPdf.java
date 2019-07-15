package com.altimetrik.services;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.altiometrik.databaseconfig.DataBaseConnection;

import java.io.IOException;
import java.io.InputStream;

public class ReadPdf {

	public void pdfExtractor(InputStream pdfStream) throws IOException {

		try (PDDocument document = PDDocument.load(pdfStream)) {
			document.getClass();

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);
				// System.out.println("Text:" + st);
				// System.out.println(pdfFileInText);
				// split by whitespace
				String lines[] = pdfFileInText.split("\n");
				String invoiceNo = "", invoiceDate = "", customerPO = "", address = "", amount = "";
				for (int i = 0; i < lines.length; i++) {
					if (lines[i].trim().equals("Invoice No")) {
						invoiceNo = lines[i + 1].trim();
					}
					if (lines[i].trim().equals("Invoice Date"))
						invoiceDate = lines[i + 1].trim();
					if (lines[i].trim().equals("Customer P.O."))
						customerPO = lines[i + 1].trim();
					if (lines[i].trim().equals("Sold To")) {
						while (!lines[++i].trim().startsWith("Ship To"))
							address += lines[i] + " ";
					}
				}
				outer: for (int i = 0; i < lines.length; i++) {
					if (lines[i].trim().equals("Total Invoice")) {
						for (int j = i + 1; j < lines.length; j++) {
							if (!lines[j + 1].trim().startsWith("$")) {
								amount = lines[j].trim();
								amount = amount.replace(",", "");
								amount = amount.replace("$", "");
								break outer;
							}
						}
					}
				}
				System.out.println("Invoice No :" + invoiceNo);
				System.out.println("Invoice Date :" + invoiceDate);
				System.out.println("Cust PO :" + customerPO);
				System.out.println("Addess :" + address);
				System.out.println("Total Invoice " + amount);
				if (invoiceNo != null) {
					JavaEmail.invno(invoiceNo);

					DataBaseConnection.saveData(invoiceNo, invoiceDate, customerPO, address, amount);

				}

			}

		}
	}
}
