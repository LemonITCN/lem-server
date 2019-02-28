package cn.lemonit.lemoi;

import cn.lemonit.lemoi.interfaces.LemoiOperator;
import cn.lemonit.lemoi.listener.LemoiProgressListener;
import cn.lemonit.lemoi.models.LemoiLocalClientConfig;
import cn.lemonit.lemoi.models.LemoiS3ClientConfig;
import cn.lemonit.lemoi.operators.LemoiLocalOperator;
import cn.lemonit.lemoi.operators.LemoiS3Operator;

import java.io.File;
import java.util.List;

/**
 * Lemoi entrace
 *
 * @author lemonit_cn
 */
public class Lemoi {
    private static final String ACCESS_KEY = "tanyouwei";
    private static final String SECRET_KEY = "tanyouwei123456%";
    private static final String ENDPOINT = "http://oss.lemonit.cn";
    private static final String BUCKET = "lem";

    public static void main(String[] args) {
//        LemoiS3ClientConfig clientConfig = new LemoiS3ClientConfig();
//        clientConfig.setAccessKey(ACCESS_KEY)
//                .setSecretKey(SECRET_KEY)
//                .setEndpoint(ENDPOINT)
//                .setBucketName(BUCKET)
//                .setUseHttps(false)
//                .setUsePathStyle(true);
//        LemoiLocalClientConfig localClientConfig = new LemoiLocalClientConfig();
//        localClientConfig.setRootPath("C:/Users/tyw/Downloads")
//                .setBufferSize(1024 * 1024);
//        try {
//            LemoiOperator operator = LemoiS3Operator.createInstance(clientConfig);
////            LemoiOperator operator = LemoiLocalOperator.createInstance(localClientConfig);
//            List<String> pathList = operator.list("");
//            for (String path : pathList) {
//                System.out.println("*** " + path);
//            }
//            LemoiProgressListener commonListener = new LemoiProgressListener() {
//                @Override
//                public void onStart(String aimPath) {
//                    System.out.println("开始任务");
//                }
//
//                @Override
//                public void onException(String aimPath, Exception e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onComplete(String aimPath, File file) {
//                    System.out.println("任务完成 : " + file.getAbsolutePath());
//                }
//
//                @Override
//                public void onProgress(String aimPath, double progress) {
//                    System.out.println("任务进度：" + progress);
//                }
//            };
//            // 上传
////            operator.put(new File("C:/Users/tyw/Downloads/lemoi-master/lemoi-master/README.md"), "testFile2", commonListener);
//            System.out.println("CON: " + operator.contain("testFile2"));
//            // 下载
//            operator.get("testFile1.png", commonListener);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        ClientConfiguration configuration = new ClientConfiguration().withProtocol(Protocol.HTTP);
//        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(URLEncoder.encode(ACCESS_KEY), SECRET_KEY)))
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT, ENDPOINT))
//                .withClientConfiguration(configuration)
//                .build();
//        AmazonS3Client s3 = new AmazonS3Client(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY), configuration);
//        s3.setEndpoint(ENDPOINT);
//        s3.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
//        List<Bucket> bucketList = s3.listBuckets();
//        for (Bucket bucket : bucketList) {
//            System.out.println("* " + bucket.getName());
//        }
//
//        ObjectListing objectListing = s3.listObjects(BUCKET);
//        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
//            System.out.println("*** " + summary.getKey());
//        }
//        BlobStoreContext blobStoreContext = ContextBuilder.newBuilder("s3").endpoint(ENDPOINT)
//                .credentials(ACCESS_KEY, SECRET_KEY)
//                .build(BlobStoreContext.class);
//        BlobStore blobStore = null;
//        ApiMetadata apiMetadata = blobStoreContext.unwrap().getProviderMetadata().getApiMetadata();
//        blobStore = blobStoreContext.getBlobStore();
//        PageSet pageSet = blobStore.list();
//        for (Object metadata : pageSet) {
//            System.out.println(metadata.getClass().getName());
//        }

    }

}
