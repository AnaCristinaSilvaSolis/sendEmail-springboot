package com.sendmail.springbootmailsend.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

  // Configuración de los datos del correo usando application.properties
  @Value("${email.sender}")
  private String emailUser;

  @Value("${email.password}")
  private String emailPass;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    mailSender.setHost("smtp.gmail.com"); // host para gmail
    mailSender.setPort(587); // puerto para gmail
    mailSender.setUsername(emailUser); // usuario de gmail desde donde se van a enviar los correos
    mailSender.setPassword(emailPass); // contraseña del usuario de gmail

    Properties properties = mailSender.getJavaMailProperties(); // obtener las propiedades del objeto mailSender
    properties.put("mail.transport.protocol", "smtp");
    // aqui estamos indicando que se va a utilizar este protocolo para enviar el
    // correo, es decir, estamos indicando cual va a ser el protocolo que vamos a
    // utilizar
    properties.put("mail.smtp.auth", "true"); // activamos la autenticación SMTP
    properties.put("mail.smtp.starttls.enable", "true"); // activamos el inicio seguro TLS (habilitamos el cifrado entre
                                                         // la
    // comunicacion de la app y el correo medinate el smtp)
    properties.put("mail.debug", "true"); // para que en la consola nos vaya imprimiendo info acerca de la app y el
                                          // correo (ACTIVAR SOLO PARA DESARROLLO, PARA PRODUCCION PONERLO EN FALSE)

    return mailSender;
  }

}
