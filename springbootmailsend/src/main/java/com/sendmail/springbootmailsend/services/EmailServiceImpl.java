package com.sendmail.springbootmailsend.services;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {
  // Configuración de los datos del correo usando application.properties
  @Value("${email.sender}")
  private String emailUser;

  // inyectamos nuestro objeto que envia los mails
  @Autowired
  private JavaMailSender mailSender;

  @Override
  public void sendEmail(String[] toUser, String subject, String message) {
    SimpleMailMessage mailMessage = new SimpleMailMessage(); // ayudara a construir nuestro mensaje

    mailMessage.setFrom(emailUser); // la cuenta de la cual va a salir el correo
    mailMessage.setTo(toUser); // el destinatario del correo, en este caso serian varios ya que usamos un array
    mailMessage.setSubject(subject); // el asunto del correo
    mailMessage.setText(message); // el mensaje del correo

    mailSender.send(mailMessage); // enviamos
  }

  @Override
  public void sendEmailWithFile(String[] toUser, String subject, String message, File file) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
      // orden: mimeMessage, si vamos a enviar un archivo, la codificacion de ese
      // archivo

      mimeMessageHelper.setFrom(emailUser);
      mimeMessageHelper.setTo(toUser);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(message);

      // setear el archivo
      mimeMessageHelper.addAttachment(file.getName(), file);

      mailSender.send(mimeMessage);

    } catch (MessagingException e) {
      throw new IllegalStateException(e);
    }

  }

}
