package com.hsf302_group2.club_management_system.mail;

import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject("Xác nhận đăng ký tham gia sự kiện: " + clubEvent.getTitle());
        String content = "Xác nhận đăng ký sự kiện\n\n" +
                "Cảm ơn bạn đã đăng ký tham gia sự kiện của chúng tôi. Dưới đây là thông tin chi tiết vể sự kiện:\n\n" +
                "Tiêu đề sự kiện: " + clubEvent.getTitle() + "\n" +
                "Mô tả: " + clubEvent.getDescription() + "\n" +
                "Địa điểm: " + clubEvent.getLocation() + "\n" +
                "Diễn giả: " + clubEvent.getSpeaker() + "\n" +
                "Ngày diễn ra: " + clubEvent.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "Thời gian bắt đầu: " + clubEvent.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n" +
                "Thời gian kết thúc: " + clubEvent.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n\n" +
                "Vui lòng đến đúng giờ và liên hệ với chúng tôi nếu có bất kỳ câu hỏi nào.\n" +
                "Trân trọng,\n" +
                "Đội ngũ tổ chức sự kiện";

        message.setText(content);
        mailSender.send(message);
    }
}
