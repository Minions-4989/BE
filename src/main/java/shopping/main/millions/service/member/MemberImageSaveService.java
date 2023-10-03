package shopping.main.millions.service.member;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberImageSaveService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3Client amazonS3Client;

    @Transactional
    public String imageSave(MultipartFile profileImage) {
        String originalName = profileImage.getOriginalFilename();
        String filename = getFileName(originalName);
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(profileImage.getContentType());
            objectMetadata.setContentLength(profileImage.getInputStream().available());
            amazonS3Client.putObject(bucketName, filename, profileImage.getInputStream(), objectMetadata);
            return amazonS3Client.getUrl(bucketName, filename).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractExtension(String originName) {
        int index = originName.lastIndexOf('.');
        return originName.substring(index, originName.length());
    }

    public String getFileName(String originName) {
        return UUID.randomUUID() + "." + extractExtension(originName);
    }

}
