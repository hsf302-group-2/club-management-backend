package com.hsf302_group2.club_management_system.mail;

import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
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

    public void sendClubActivityRegistrationEmail(String toMail, ClubActivity clubActivity) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toMail);
            helper.setSubject("Xác nhận tham gia hoạt động: " + clubActivity.getTitle());

            String htmlContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                    "<h2>Xác nhận tham gia hoạt động câu lạc bộ</h2>" +
                    "<p>Cảm ơn bạn đã đăng ký tham gia hoạt động của câu lạc bộ chúng tôi. Dưới đây là thông tin chi tiết về hoạt động:</p>" +
                    "<table style='border-collapse: collapse; width: 100%; max-width: 600px;'>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Tiêu đề hoạt động</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubActivity.getTitle() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Mô tả</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubActivity.getDescription() + "</td>" +
                    "</tr>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Địa điểm</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubActivity.getLocation() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Loại hoạt động</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubActivity.getType() + "</td>" +
                    "</tr>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Thời gian bắt đầu</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" +
                    clubActivity.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Thời gian kết thúc</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" +
                    clubActivity.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<p style='margin-top: 20px;'>Vui lòng đến đúng giờ và liên hệ với chúng tôi nếu có bất kỳ câu hỏi nào.</p>" +
                    "<p>Trân trọng,<br>Đội ngũ câu lạc bộ</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.MAIL_SEND_FAILED);
        }
    }

    public void sendWelcomeClubMemberEmail(String toMail, ClubMember clubMember) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toMail);
            helper.setSubject("Chào mừng bạn trở thành thành viên Spring Tech Club!");

            String htmlContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                    "<h2>Chào mừng đến với Spring Tech Club!</h2>" +
                    "<p>Chúng tôi rất vui mừng chào đón bạn trở thành thành viên chính thức của <strong>Spring Tech Club</strong>! " +
                    "Câu lạc bộ của chúng tôi là nơi kết nối những người đam mê công nghệ, đặc biệt là Spring Framework, " +
                    "và chúng tôi rất mong chờ được đồng hành cùng bạn trong hành trình khám phá và phát triển.</p>" +
                    "<table style='border-collapse: collapse; width: 100%; max-width: 600px;'>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Họ và tên</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubMember.getPreMember().getUser().getFullName() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Email</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + clubMember.getPreMember().getUser().getEmail() + "</td>" +
                    "</tr>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Ngày tham gia</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" +
                    clubMember.getJoinedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<p style='margin-top: 20px;'>Hãy tham gia các hoạt động sắp tới của chúng tôi để kết nối, học hỏi và chia sẻ kinh nghiệm. " +
                    "Nếu bạn có bất kỳ câu hỏi nào, đừng ngần ngại liên hệ với đội ngũ quản lý câu lạc bộ.</p>" +
                    "<p>Chào mừng bạn một lần nữa và hy vọng bạn sẽ có những trải nghiệm tuyệt vời cùng Spring Tech Club!</p>" +
                    "<p>Trân trọng,<br>Đội ngũ Spring Tech Club</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.MAIL_SEND_FAILED);
        }
    }

    public void sendRejectionClubMemberEmail(String toMail, PreMember member) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toMail);
            helper.setSubject("Thông báo từ chối tham gia Spring Tech Club");

            String htmlContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                    "<h2>Thông báo từ chối tham gia</h2>" +
                    "<p>Xin chào bạn " + member.getUser().getFullName() + ",</p>" +
                    "<p>Chúng tôi rất tiếc phải thông báo rằng yêu cầu tham gia của bạn vào <strong>Spring Tech Club</strong> không được chấp nhận.</p>" +
                    "<table style='border-collapse: collapse; width: 100%; max-width: 600px;'>" +
                    "<tr style='background-color: #f8f8f8;'>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Họ và tên</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + member.getUser().getFullName() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'><strong>Email</strong></td>" +
                    "<td style='padding: 10px; border: 1px solid #ddd;'>" + member.getUser().getEmail() + "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<p style='margin-top: 20px;'>Nếu bạn có thắc mắc hoặc cần thêm thông tin, hãy liên hệ với đội ngũ quản lý câu lạc bộ.</p>" +
                    "<p>Trân trọng,<br>Đội ngũ Spring Tech Club</p>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.MAIL_SEND_FAILED);
        }
    }



    
}
