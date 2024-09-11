package com.sendmail.springbootmailsend.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//el formData si necesita los setters
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailFileDTO {
  private String[] toUser;
  private String subject;
  private String message;
  private MultipartFile file; // mecanismo con el que spring nos facilita el manejo de archivos
}
