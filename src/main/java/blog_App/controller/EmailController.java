package blog_App.controller;
import blog_App.entity.EmailRequest;
import blog_App.payloads.EmailResponse;
import blog_App.service.EmailService;
import blog_App.utils.EmailUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "EmailController",description = "APIs for Email !!")
@SecurityRequirement(name="securityScheme")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailUtil emailUtil;


    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmailResponse sentEmail(@RequestBody EmailRequest emailRequest) {

        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        String body = emailRequest.getBody();


        return emailService.sentEmail(to, subject, body);
    }

    @PostMapping(value = "/email-attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmailResponse sentEmailWithAttachment(@RequestParam Map<String, String> emailRequest, @RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "", name = "cc") String CC) throws IOException {

        String to = emailRequest.get("to");
        String subject = emailRequest.get("subject");
        String body = emailRequest.get("body");


        //File file=multipartToFile(multipartFile);

        return emailService.sentEmailWithAttachment(to, subject, body, file, CC);
    }
}
