package beyond.momentours.member.command.application.service;

import jakarta.mail.internet.MimeMessage;

public interface MailService {

    String sendMail(String email);
}
