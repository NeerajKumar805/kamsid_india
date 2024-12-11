package com.india.kamsid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.india.kamsid.entities.FormData;
import com.india.kamsid.entities.FormData2;
import com.india.kamsid.entities.FormData3;
import com.india.kamsid.services.ProductService;

import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api/v1")
public class FormController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ProductService productService;

//	@PostMapping("/form)
//    public String handleFormSubmission(@RequestBody Map<String, String> formData) throws IOException, MessagingException {
//        // Generate Excel file
//        byte[] excelFile = generateExcelFile(formData);
//
//        // Send email with Excel attachment
//        sendEmailWithAttachment(excelFile, formData.get("email"));
//
//        return "Form submitted and email sent";
//    }
//
//    // Method to generate an Excel file
//    private byte[] generateExcelFile(Map<String, String> formData) throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Form Data");
//
//        // Add header row
//        Row header = sheet.createRow(0);
//        header.createCell(0).setCellValue("Field");
//        header.createCell(1).setCellValue("Value");
//
//        // Add form data
//        int rowNum = 1;
//        for (Map.Entry<String, String> entry : formData.entrySet()) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(entry.getKey());
//            row.createCell(1).setCellValue(entry.getValue());
//        }
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        workbook.write(bos);
//        workbook.close();
//
//        return bos.toByteArray();
//    }
//
//    // Method to send an email with the Excel file attached
//    private void sendEmailWithAttachment(byte[] excelFile, String toEmail) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();  
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(toEmail);
//        helper.setSubject("Form Submission Data");
//        helper.setText("Please find attached the form submission data in the Excel file.");
//
//        // Create the Excel attachment
//        InputStreamSource attachment = new ByteArrayResource(excelFile);
//        helper.addAttachment("form-data.xlsx", attachment);
//
//        mailSender.send(message);
//    }
	@PostMapping("/form")
	public String handleFormSubmission(@RequestBody FormData formData) {
		try {
			// Create a MimeMessage for sending HTML email
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

			// Set the "from" address as your email (Gmail SMTP restriction)
			helper.setFrom("neerajkumarroy805@gmail.com");

			// Set the "Reply-To" address as the user's email
			helper.setReplyTo(formData.getEmail());

			// Set the recipient address (your email)
			helper.setTo("neerajkumarroy805@gmail.com");

			// Set the subject
			helper.setSubject("New Contact Form Submission from " + formData.getName());

			// Create the email body with improved HTML content
			String htmlContent = "<div style='font-family: Arial, sans-serif; background-color: #f2f2f7; padding: 20px;'>"
					+ "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);'>"
					+ "<h2 style='color: #4A90E2; text-align: center; margin-bottom: 30px;'>New Contact Form Submission</h2>"
					+ "<table style='width: 100%; border-collapse: collapse;'>"
					+ "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Name:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getName() + "</td>"
					+ "</tr>" + "<tr>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Product Name:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getProductName()
					+ "</td>" + "</tr>" + "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Email:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getEmail() + "</td>"
					+ "</tr>" + "<tr>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Location:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getLocation() + "</td>"
					+ "</tr>" + "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Phone:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getPhone() + "</td>"
					+ "</tr>" + "<tr>" + "<td style='padding: 10px;'><strong>Message:</strong></td>"
					+ "<td style='padding: 10px;'>" + formData.getMessage() + "</td>" + "</tr>" + "</table>"
					+ "<div style='margin-top: 30px; text-align: center;'>" + "</div>" + "</div>"
					+ "<footer style='margin-top: 20px; text-align: center; font-size: 12px; color: #aaa;'>"
					+ "This email was sent from Kamsid India's contact form." + "</footer>" + "</div>";

			// Set the email body as HTML
			helper.setText(htmlContent, true); // 'true' enables HTML content

			// Send the email
			mailSender.send(mimeMessage);

			return "Form submitted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error submitting the form!";
		}
	}

	@PostMapping("/form2")
	public String handleFormSubmission2(@RequestBody FormData2 formData) {
		try {
			// Create a MimeMessage for sending HTML email
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

			// Set the "from" address as your email (Gmail SMTP restriction)
			helper.setFrom("neerajkumarroy805@gmail.com");

			// Set the "Reply-To" address as the user's email
			helper.setReplyTo(formData.getEmail());

			// Set the recipient address (your email)
			helper.setTo("neerajkumarroy805@gmail.com");

			// Set the subject
			helper.setSubject("New Contact Form Submission from " + formData.getName());

			// Create the email body with improved HTML content
			String htmlContent = "<div style='font-family: Arial, sans-serif; background-color: #f2f2f7; padding: 20px;'>"
					+ "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);'>"
					+ "<h2 style='color: #4A90E2; text-align: center; margin-bottom: 30px;'>New Contact Form Submission</h2>"
					+ "<table style='width: 100%; border-collapse: collapse;'>"
					+ "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Name:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getName() + "</td>"
					+ "</tr>" + "<tr>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Location:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getLocation() + "</td>"
					+ "</tr>" + "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Email:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getEmail() + "</td>"
					+ "</tr>" + "<tr>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Phone:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getPhone() + "</td>"
					+ "</tr>" + "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px;'><strong>Message:</strong></td>" + "<td style='padding: 10px;'>"
					+ formData.getMessage() + "</td>" + "</tr>" + "</table>"
					+ "<div style='margin-top: 30px; text-align: center;'>" + "</div>" + "</div>"
					+ "<footer style='margin-top: 20px; text-align: center; font-size: 12px; color: #aaa;'>"
					+ "This email was sent from Kamsid India's contact form." + "</footer>" + "</div>";

			// Set the email body as HTML
			helper.setText(htmlContent, true); // 'true' enables HTML content

			// Send the email
			mailSender.send(mimeMessage);

			return "Form submitted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error submitting the form!";
		}
	}

	@PostMapping("/form3")
	public String handleFormSubmission3(@RequestBody FormData3 formData) {
		try {
			// Create a MimeMessage for sending HTML email
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

			// Set the "from" address as your email (Gmail SMTP restriction)
			helper.setFrom("neerajkumarroy805@gmail.com");

			// Set the "Reply-To" address as the user's email
			helper.setReplyTo(formData.getEmail());

			// Set the recipient address (your email)
			helper.setTo("neerajkumarroy805@gmail.com");

			// Set the subject
			helper.setSubject("New Contact Form Submission from " + formData.getFirstName());

			// Create the email body with improved HTML content
			String htmlContent = "<div style='font-family: Arial, sans-serif; background-color: #f2f2f7; padding: 20px;'>"
					+ "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);'>"
					+ "<h2 style='color: #4A90E2; text-align: center; margin-bottom: 30px;'>New Query Submission</h2>"
					+ "<table style='width: 100%; border-collapse: collapse;'>"
					+ "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>First Name:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getFirstName()
					+ "</td>" + "</tr>" + "<tr>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Last Name:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getLastName() + "</td>"
					+ "</tr>" + "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Company Name:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getCompany() + "</td>"
					+ "</tr>" + "<tr>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Phone:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getPhone() + "</td>"
					+ "</tr>" + "<tr style='background-color: #f9f9f9;'>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'><strong>Email:</strong></td>"
					+ "<td style='padding: 10px; border-bottom: 1px solid #dddddd;'>" + formData.getEmail() + "</td>"
					+ "</tr>" + "<tr>" + "<td style='padding: 10px;'><strong>Message:</strong></td>"
					+ "<td style='padding: 10px;'>" + formData.getMessage() + "</td>" + "</tr>" + "</table>"
					+ "<div style='margin-top: 30px; text-align: center;'>" + "</div>" + "</div>"
					+ "<footer style='margin-top: 20px; text-align: center; font-size: 12px; color: #aaa;'>"
					+ "This email was sent from Kamsid India's query form." + "</footer>" + "</div>";

			// Set the email body as HTML
			helper.setText(htmlContent, true); // 'true' enables HTML content

			// Send the email
			mailSender.send(mimeMessage);

			return "Form submitted successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error submitting the form!";
		}
	}

	@PostMapping("/subscribe")
	public String subscribe(@RequestBody String email) {
		return productService.subscribe(email);
	}
	
	@GetMapping(value = "/{path:[^\\.]*}") // Match all paths except those containing a dot (static files)
    public String redirect() {
        return "forward:/index.html";
    }
}