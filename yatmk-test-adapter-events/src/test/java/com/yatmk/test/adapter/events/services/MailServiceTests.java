// package com.yatmk.test.adapter.events.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import com.icegreen.greenmail.configuration.GreenMailConfiguration;
// import com.icegreen.greenmail.junit5.GreenMailExtension;
// import com.icegreen.greenmail.util.ServerSetupTest;
// import com.yatmk.test.adapter.events.events.MailEvent;
// import com.yatmk.test.adapter.events.events.MailEvent.MailFile;
// import com.yatmk.test.ports.domain.exception.ServerSideException;
// import java.util.Arrays;
// import java.util.Collections;
// import javax.activation.DataSource;
// import javax.mail.internet.MimeMessage;
// import javax.mail.util.ByteArrayDataSource;
// import org.junit.Test;
// import org.junit.jupiter.api.extension.RegisterExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;

// @SpringBootTest
// @ActiveProfiles("test")
// public class MailServiceTests {

// public static final GreenMailConfiguration CONFIG = GreenMailConfiguration
// .aConfig()
// .withUser("test@example.com", "password");

// @RegisterExtension
// public static final GreenMailExtension GREEN_MAIL = new
// GreenMailExtension(ServerSetupTest.SMTP)
// .withConfiguration(CONFIG)
// .withPerMethodLifecycle(true);

// @Autowired
// private MailService mailService;

// @Test
// public void shouldSendEmailCorrectly() throws Exception {
// // Arrange
// String recipient = "customer@domain.com";
// String subject = "Invoice #123";
// String htmlBody = "<h1>Hello!</h1>";

// MailEvent mailEvent = new MailEvent(
// null,
// Arrays.asList(recipient),
// Collections.emptyList(),
// subject,
// htmlBody,
// Collections.emptyList()
// );

// // Act
// mailService.send(mailEvent);

// // Assert
// // Wait for the email to arrive (default 1s)
// assertTrue(GREEN_MAIL.waitForIncomingEmail(1));

// MimeMessage[] receivedMessages = GREEN_MAIL.getReceivedMessages();
// assertEquals(1, receivedMessages.length);

// MimeMessage message = receivedMessages[0];
// assertEquals(subject, message.getSubject());
// assertEquals(recipient, message.getAllRecipients()[0].toString());
// assertTrue(message.getContentType().contains("multipart"));
// }

// @Test
// public void shouldSendEmailWithAttachment() throws Exception {
// // Arrange
// DataSource ds = new ByteArrayDataSource("dummy content".getBytes(),
// "application/pdf");
// MailFile file =
// MailFile.builder().fileName("report.pdf").fileSize(100L).dataSource(ds).build();
// // Arrange
// String recipient = "customer@domain.com";
// String subject = "Invoice #123";
// String htmlBody = "<h1>Hello!</h1>";

// MailEvent mailEvent = new MailEvent(
// null,
// Arrays.asList(recipient),
// Collections.emptyList(),
// subject,
// htmlBody,
// Arrays.asList(file)
// );

// // Act
// mailService.send(mailEvent);

// // Assert
// MimeMessage[] receivedMessages = GREEN_MAIL.getReceivedMessages();
// assertEquals(1, receivedMessages.length);

// // GreenMail allows you to check if the body contains specific strings
// String content = receivedMessages[0].getContent().toString();
// assertTrue(content.contains("report.pdf"));
// }

// @Test
// public void shouldThrowExceptionWhenSizeExceedsLimit() {
// // Arrange
// MailFile bigFile = MailFile.builder().fileSize(MailService.EMAIL_LIMIT_SIZE +
// 1024).build();

// // Arrange
// String recipient = "customer@domain.com";
// String subject = "Invoice #123";
// String htmlBody = "<h1>Hello!</h1>";

// MailEvent mailEvent = new MailEvent(
// null,
// Arrays.asList(recipient),
// Collections.emptyList(),
// subject,
// htmlBody,
// Arrays.asList(bigFile)
// );

// // Act & Assert
// assertThrows(ServerSideException.class, () -> mailService.send(mailEvent));
// }
// }
