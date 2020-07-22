package cn.lemonit.lemserver.utils;

import cn.lemonit.lemserver.security.OssConfig;
import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public abstract class S3Util {
    private static final Map<String, AmazonS3> clients = new ConcurrentHashMap<>();

    public static AmazonS3 getClient(OssConfig config) {
        if (!clients.containsKey(config.getServerPath())) {
                if (!clients.containsKey(config.getServerPath())) {
                    AWSCredentials credentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
                    ClientConfiguration clientConfiguration = new ClientConfiguration();
                    clientConfiguration.setSignerOverride("AWSS3V4SignerType");
                    AmazonS3 s3 = AmazonS3ClientBuilder
                            .standard()
                            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(config.getServerPath(), Regions.CN_NORTH_1.name()))
                            .withPathStyleAccessEnabled(true)
                            .withClientConfiguration(clientConfiguration)
                            .withCredentials(new AWSStaticCredentialsProvider(credentials))
                            .build();

                    clients.put(config.getServerPath(), s3);
                }
        }
        return clients.get(config.getServerPath());
    }

    public static boolean uploadFile(AmazonS3 client, String bucket, String fileId, InputStream stream, long contentLength) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            client.putObject(new PutObjectRequest(bucket, fileId, stream, objectMetadata));
            return true;
        } catch (AmazonClientException ase) {
            log.error("上传文件失败。错误信息：" + ase.getMessage(), ase);
            return false;
        }
    }

    public static String downloadFile(AmazonS3 client, String bucket, String fileId) {
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucket, fileId);
        URL url = client.generatePresignedUrl(urlRequest);
        return url.toString();
    }
}
