package cn.lemonit.lemserver.utils;

import cn.lemonit.lemoi.exceptions.ConfigInvalidException;
import cn.lemonit.lemoi.exceptions.TargetNotExistsException;
import cn.lemonit.lemoi.interfaces.LemoiOperator;
import cn.lemonit.lemoi.listener.LemoiProgressListener;
import cn.lemonit.lemoi.models.LemoiS3ClientConfig;
import cn.lemonit.lemoi.operators.LemoiS3Operator;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class LemoiUtil {
    private static final String ACCESS_KEY = "tanyouwei";
    private static final String SECRET_KEY = "tanyouwei123456%";
    private static final String ENDPOINT = "http://oss.lemonit.cn";
    private static final String BUCKET = "lem";

    private static String uploadtempdir = System.getProperty("java.io.tmpdir");

    public static LemoiOperator connectoss() throws ConfigInvalidException {
        //设置参数
        LemoiS3ClientConfig clientConfig = new LemoiS3ClientConfig();
        clientConfig.setAccessKey(ACCESS_KEY)
                .setSecretKey(SECRET_KEY)
                .setEndpoint(ENDPOINT)
                .setBucketName(BUCKET)
                .setUseHttps(false)
                .setUsePathStyle(true);
        LemoiOperator operator = LemoiS3Operator.createInstance(clientConfig);
//        List<String> pathList = operator.list("");
//                for (String osspath : pathList) {
//                    System.out.println("*** " + osspath);
//                }
        return operator;
    }

    public static LemoiProgressListener commonListener = new LemoiProgressListener() {
        @Override
        public void onStart(String aimPath) {
            System.out.println("开始任务");
        }

        @Override
        public void onException(String aimPath, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete(String aimPath, File file) {
            System.out.println("任务完成 : " + file.getAbsolutePath());
        }

        @Override
        public void onProgress(String aimPath, double progress) {
//            System.out.println("任务进度：" + progress);
        }
    };

    public static void upload(MultipartFile file, String filename) throws ConfigInvalidException, IOException {
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.print("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.print("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
//            String filePath = "C:/Users/tyw/Downloads/lemoi-master/";
            String path = uploadtempdir + filename + suffixName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            System.out.print("存储成功：" + path);

            LemoiOperator operator = (LemoiOperator) connectoss();
            // 上传
            operator.put(new File(uploadtempdir+filename+suffixName), filename+suffixName, commonListener);
            System.out.println("CON: " + operator.contain("filename"));

        } catch (ConfigInvalidException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void download(String path){
        if(path==null){
            throw new BaseBusinessException(ErrorMsg.resource_does_not_exit.toString());
        }
        try {
            LemoiOperator operator = (LemoiOperator) connectoss();
            if(!operator.contain(path)){
                throw new BaseBusinessException(ErrorMsg.resource_does_not_exit.toString());
            };
            operator.get(path, commonListener);
            System.out.print("下载成功：" + path);

        } catch (TargetNotExistsException e) {
            e.printStackTrace();
        } catch (ConfigInvalidException e) {
            e.printStackTrace();
        }
    }

    public static OutputStream resizeImage(InputStream is, OutputStream os, int size) throws IOException {
        BufferedImage prevImage = ImageIO.read(is);
        double width = prevImage.getWidth();
        double height = prevImage.getHeight();
        double percent = size/width;
        int newWidth = (int)(width * percent);
        int newHeight = (int)(height * percent);
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
        ImageIO.write(image, "jpg", os);
        os.flush();
        is.close();
        os.close();

//        ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
//        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return os;

    }
}
