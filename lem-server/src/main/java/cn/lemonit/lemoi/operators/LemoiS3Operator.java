package cn.lemonit.lemoi.operators;

import cn.lemonit.lemoi.exceptions.ConfigInvalidException;
import cn.lemonit.lemoi.exceptions.TargetNotExistsException;
import cn.lemonit.lemoi.interfaces.LemoiClientConfig;
import cn.lemonit.lemoi.interfaces.LemoiOperator;
import cn.lemonit.lemoi.listener.LemoiProgressListener;
import cn.lemonit.lemoi.models.LemoiS3ClientConfig;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferProgress;
import com.amazonaws.services.s3.transfer.internal.CopyImpl;
import com.amazonaws.services.s3.transfer.internal.DownloadImpl;
import com.amazonaws.services.s3.transfer.internal.UploadImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 文件操作者 - 基于OSS-S3的实现
 *
 * @author lemonit_cn
 */
public class LemoiS3Operator implements LemoiOperator {

    public static LemoiS3Operator createInstance(LemoiClientConfig clientConfig) throws ConfigInvalidException {
        return new LemoiS3Operator(clientConfig);
    }

    private AmazonS3 s3;
    private LemoiS3ClientConfig clientConfig;

    private LemoiS3Operator() {
    }

    public LemoiS3Operator(LemoiClientConfig clientConfig) throws ConfigInvalidException {
        if (!(clientConfig instanceof LemoiS3ClientConfig)) {
            throw new ConfigInvalidException();
        } else {
            this.clientConfig = (LemoiS3ClientConfig) clientConfig;
            ClientConfiguration configuration = new ClientConfiguration()
                    .withProtocol(this.clientConfig.isUseHttps() ? Protocol.HTTPS : Protocol.HTTP);
            s3 = new AmazonS3Client(
                    new BasicAWSCredentials(
                            this.clientConfig.getAccessKey(),
                            this.clientConfig.getSecretKey()),
                    configuration
            );
            s3.setEndpoint(this.clientConfig.getEndpoint());
            s3.setS3ClientOptions(
                    S3ClientOptions.builder().setPathStyleAccess(
                            this.clientConfig.isUsePathStyle()
                    ).build()
            );
            // 以下客户端创建代码为最新的S3 SDK中使用，暂时注释，因为为了提高兼容性，目前使用旧版本的S3SDK
//            AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.clientConfig.getAccessKey(), this.clientConfig.getSecretKey())))
//                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.clientConfig.getEndpoint(), this.clientConfig.getEndpoint()))
//                    .withClientConfiguration(configuration);
//            builder.setPathStyleAccessEnabled(this.clientConfig.isUsePathStyle());
//            this.s3 = builder.build();
        }
    }

    @Override
    public LemoiClientConfig getClientConfig() {
        return this.clientConfig;
    }

    @Override
    public void put(File file, String path, LemoiProgressListener progressListener) {
        try {
            put(file, new FileInputStream(file), file.length(), path, progressListener);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(InputStream inputStream, Long contentLength, String path, LemoiProgressListener progressListener) {
        put(null, inputStream, contentLength, path, progressListener);
    }

    private void put(File file, InputStream inputStream, Long contentLength, String path, LemoiProgressListener progressListener) {
        TransferManager manager = new TransferManager(s3);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader(Headers.CONTENT_LENGTH, contentLength);
        UploadImpl upload = (UploadImpl) manager.upload(clientConfig.getBucketName(), path, inputStream, metadata);
        // 处理上传进度回调
        dealProgress(path, progressListener, manager, upload.getMonitor().getFuture(), upload.getProgress(), file);
    }

    @Override
    public void delete(String path) {
        this.s3.deleteObject(this.clientConfig.getBucketName(), path);
    }

    @Override
    public void get(String path, LemoiProgressListener progressListener) throws TargetNotExistsException {
        if (!contain(path)) {
            throw new TargetNotExistsException(path);
        }
        File file = generateDownloadFile(path, this.clientConfig.getTempDirPath());
        TransferManager manager = new TransferManager(this.s3);
        DownloadImpl download = (DownloadImpl) manager.download(this.clientConfig.getBucketName(), path, file);
        // 处理下载进度回调
        dealProgress(path, progressListener, manager, download.getMonitor().getFuture(), download.getProgress(), file);
    }

    @Override
    public void copy(String sourcePath, String aimPath, LemoiProgressListener progressListener) throws TargetNotExistsException {
        if (!contain(sourcePath)) {
            throw new TargetNotExistsException(sourcePath);
        }
        TransferManager manager = new TransferManager(this.s3);
        CopyImpl copy = (CopyImpl) manager.copy(this.clientConfig.getBucketName(), sourcePath, this.clientConfig.getBucketName(), aimPath);
        dealProgress(aimPath, progressListener, manager, copy.getMonitor().getFuture(), copy.getProgress(), null);
    }

    @Override
    public List<String> list(String parentPath) {
        ListObjectsV2Result objectListing = s3.listObjectsV2(this.clientConfig.getBucketName(), parentPath);
        List<String> pathList = new ArrayList<>();
        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            String fileName = summary.getKey().substring(parentPath.length()).split("/")[0];
            if (!pathList.contains(fileName)) {
                pathList.add(fileName);
            }
        }
        return pathList;
    }

    @Override
    public boolean contain(String path) {
        return s3.listObjects(this.clientConfig.getBucketName(), path).getObjectSummaries().size() > 0;
    }

    private File generateDownloadFile(String objPath, String tmpDir) {
        String[] items = objPath.split("/");
        return new File(tmpDir + File.separator + items[items.length - 1]);
    }

    private void dealProgress(String aimPath, LemoiProgressListener progressListener, TransferManager manager, Future future, TransferProgress dealProgress, File file) {
        progressListener.onStart(aimPath);
        double progress = 0;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            if (progress != dealProgress.getPercentTransferred()) {
                // 进度变化了,通知监听器
                progress = dealProgress.getPercentTransferred() / 100;
                progressListener.onProgress(aimPath, progress);
            }
        } while (!future.isDone());
        progressListener.onComplete(aimPath, file);
    }

}
