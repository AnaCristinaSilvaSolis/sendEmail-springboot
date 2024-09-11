package com.sendmail.springbootmailsend.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sendmail.springbootmailsend.domain.EmailDTO;
import com.sendmail.springbootmailsend.domain.EmailFileDTO;
import com.sendmail.springbootmailsend.services.IEmailService;

@RestController
@RequestMapping("/v1") // ruta raiz
public class MainController {
  @Autowired
  private IEmailService emailService;

  @PostMapping("/sendMessage")
  // <?> significa que retornaremos cualquier tipo de datos
  // debemos convertir el json a objeto Java con @RequestBody
  public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO) {
    System.out.println("Mensaje recibido " + emailDTO);

    emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

    // para las repuestas al front
    Map<String, String> response = new HashMap<>();
    response.put("estado", "Enviado");

    return ResponseEntity.ok(response);
  }

  @PostMapping("/sendMessageFile")
  // los archivos no son compatibles con json
  // para eso usamos formData
  // con @ModelAttribute mapeamos el formData con nuestro DTO
  public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO) {

    try {
      // recibimos
      String fileName = emailFileDTO.getFile().getOriginalFilename(); // getOriginalFilename() nos trae la extension del
                                                                      // archivo
      // guardamos
      Path path = Paths.get("src/main/resources/files", fileName);
      // refereciamos la ruta en donde se guardara

      // creamos el directorio si por algun motivo no existe
      Files.createDirectories(path.getParent());

      // copiamos los archivos y si ya existe los reemplazaria para que no hayan
      // archivos repetidos
      Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

      // enviar el corre
      File file = path.toFile();

      emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(),
          file);

      // para las repuestas al front
      Map<String, String> response = new HashMap<>();
      response.put("estado", "Enviado");
      response.put("archivo", fileName);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el email con el archivo " + e.getMessage());
    }

  }

}
