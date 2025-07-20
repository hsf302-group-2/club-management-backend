package com.hsf302_group2.club_management_system.mail;

import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailService {
    JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String link){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your email");
        message.setText("Please click the following link to verify your email: \n " + link);
        mailSender.send(message);

    }

    public void sendRegistrationClubEventEmail(String toMail, ClubEvent clubEvent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toMail);
            helper.setSubject("Xác nhận đăng ký sự kiện: " + clubEvent.getTitle());

            String htmlContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                    "<h2>Xác nhận đăng ký sự kiện</h2>" +
                    "<p>Cảm ơn bạn đã đăng ký tham gia sự kiện của chúng tôi. Dưới đây là thông tin chi tiết:</p>" +
                    "<table style='border-collapse: collapse; width: 100%; max-width: 600px;'>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Tiêu đề sự kiện</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubEvent.getTitle() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Mô tả</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubEvent.getDescription() + "</td>" +
                    "</tr>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Địa điểm</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubEvent.getLocation() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Diễn giả</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubEvent.getSpeaker() + "</td>" +
                    "</tr>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Ngày diễn ra</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubEvent.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Thời gian bắt đầu</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubEvent.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "</td>" +
                    "</tr>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Thời gian kết thúc</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubEvent.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<p style='margin-top: 20px;'>Vui lòng đến đúng giờ và liên hệ với chúng tôi nếu có bất kỳ câu hỏi nào.</p>" +
                    "<p>Trân trọng,<br>Đội ngũ tổ chức sự kiện</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.MAIL_SEND_FAILED);
        }
    }

    
}
