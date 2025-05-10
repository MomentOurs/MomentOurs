//package beyond.momentours.util;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import jakarta.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//@Slf4j
//public class FileUploader {
//
//    @Value("${cloud.aws.credentials.access-key}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secret-key}")
//    private String secretKey;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private AmazonS3 s3Client;
//
//    @PostConstruct
//    public void initialize() {
//        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//
//        this.s3Client = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(region)
//                .build();
//    }
//
//    public String upload(MultipartFile file, String folder) {
//        String originalFilename = file.getOriginalFilename();
//        String fileExtension = getFileExtension(originalFilename);
//        String uniqueFileName = UUID.randomUUID() + fileExtension;
//        String s3Key = folder + "/" + uniqueFileName;
//
//        try {
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//            metadata.setContentType(file.getContentType());
//
//            s3Client.putObject(bucket, s3Key, file.getInputStream(), metadata);
//            return s3Client.getUrl(bucket, s3Key).toString();
//        } catch (IOException e) {
//            log.error("파일 업로드 실패", e);
//            throw new RuntimeException("S3 파일 업로드 실패", e);
//        }
//    }
//
//    private String getFileExtension(String filename) {
//        if (filename == null || !filename.contains(".")) {
//            return "";
//        }
//        return filename.substring(filename.lastIndexOf("."));
//    }
//}
