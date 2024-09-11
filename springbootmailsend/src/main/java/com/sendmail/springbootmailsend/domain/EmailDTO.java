package com.sendmail.springbootmailsend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
//los DTO son objetos inmutables, es decir que no se pueden modificar, por eso no se le ponen Setter
// los DTO se usan para representar los json que nos llegan y convertirlos a
// objetos, aunque tiene mas usos
// public record EmailDTO(
//     String[] toUser,
//     String subject,
//     String message) {
// }
import lombok.ToString;

//_ahora usando lombok 
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO {
  private String[] toUser;
  private String subject;
  private String message;
}