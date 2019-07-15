package com.altimetrik;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.annotations.Test;

import com.altimetrik.services.JavaEmail;
import com.altimetrik.services.ReadPdf;
import com.altimetrik.services.ReceiveEmailWithAttachment;
import com.altiometrik.databaseconfig.DataBaseConnection;

public class App {
	
@Test
	public void main1() throws IOException, AddressException, MessagingException  {
		Scanner sc = new Scanner(System.in);
		boolean askAgain = true;
		while (askAgain) {

			System.out.print(
					" 1-> Press 1 to get newInvoice from mail \n 2-> Press 2 to validate newInvoice \n 3-> Press 3 to get all Invoice present \n 4-> Press 4 to exit");
			System.out.println("\n____________________________");
			System.out.println("Please enter the choice>>");

			int option = sc.nextInt();

			switch (option) {
			case 1:
				ReceiveEmailWithAttachment receiver = new ReceiveEmailWithAttachment();
				InputStream pdfStream = null;
				pdfStream = receiver.downloadEmailAttachments();
				try {
					ReadPdf pdfText = new ReadPdf();

					pdfText.pdfExtractor(pdfStream);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("No new mail is Present");
					System.out.println("____________________________");
				}

				finally {
					if (pdfStream != null)
						pdfStream.close();

				}
				break;

			case 2:
				JavaEmail.Sendmail();

				break;

			case 3:
				DataBaseConnection.Invoices();
				break;

			case 4:

				askAgain = false;

				break;

			default:
				System.out.println(" Sorry wrong choice");
				System.out.println("____________________________");

				break;

			}
		}
		sc.close();
	}
}
