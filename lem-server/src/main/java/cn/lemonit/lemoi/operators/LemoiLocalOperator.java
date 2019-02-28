package cn.lemonit.lemoi.operators;

import cn.lemonit.lemoi.exceptions.ConfigInvalidException;
import cn.lemonit.lemoi.exceptions.TargetNotExistsException;
import cn.lemonit.lemoi.interfaces.LemoiClientConfig;
import cn.lemonit.lemoi.interfaces.LemoiOperator;
import cn.lemonit.lemoi.listener.LemoiProgressListener;
import cn.lemonit.lemoi.models.LemoiLocalClientConfig;
import cn.lemonit.lemoi.models.LemoiS3ClientConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作者 - 基于本地文件的实现
 *
 * @author lemonit_cn
 */
public class LemoiLocalOperator implements LemoiOperator {

    public static LemoiLocalOperator createInstance(LemoiClientConfig clientConfig) throws ConfigInvalidException {
        return new LemoiLocalOperator(clientConfig);
    }

    private LemoiLocalClientConfig clientConfig;

    private LemoiLocalOperator() {
    }

    public LemoiLocalOperator(LemoiClientConfig clientConfig) throws ConfigInvalidException {
        if (!(clientConfig instanceof LemoiLocalClientConfig)) {
            throw new ConfigInvalidException();
        } else {
            this.clientConfig = (LemoiLocalClientConfig) clientConfig;
        }
    }

    @Override
    public LemoiClientConfig getClientConfig() {
        return null;
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
        File aimFile = getFile(path);
        try {
            OutputStream outputStream = new FileOutputStream(aimFile);
            byte[] bytes = new byte[this.clientConfig.getBufferSize()];
            long index = 0;
            int result;
            int len = Math.min(Math.toIntExact(contentLength), this.clientConfig.getBufferSize());
            while (((result = inputStream.read(bytes, 0, len)) != -1) && index < contentLength) {
                index += result;
                outputStream.write(bytes, 0, result);
                progressListener.onProgress(path, index * 1.0 / contentLength);
                len = Math.min(Math.toIntExact(contentLength - index), len);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            progressListener.onComplete(path, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String path) {
        File file = getFile(path);
        file.delete();
    }

    @Override
    public void get(String path, LemoiProgressListener progressListener) throws TargetNotExistsException {
        File getFile = getFile(path);
        if (!getFile.exists()) {
            throw new TargetNotExistsException(path);
        }
        progressListener.onStart(path);
        progressListener.onProgress(path, 1);
        progressListener.onComplete(path, getFile);
    }

    @Override
    public void copy(String sourcePath, String aimPath, LemoiProgressListener progressListener) throws TargetNotExistsException {
        File sourceFile = getFile(sourcePath);
        if (!sourceFile.exists()) {
            throw new TargetNotExistsException(sourcePath);
        }
        put(sourceFile, aimPath, progressListener);
    }

    @Override
    public List<String> list(String parentPath) {
        File parent = getFile(parentPath);
        List<String> result = new ArrayList<>();
        if (parent.exists() && parent.isDirectory()) {
            for (String fileName : parent.list()) {
                result.add(fileName.replaceAll(this.clientConfig.getRootPath() + File.separator, ""));
            }
        }
        return result;
    }

    @Override
    public boolean contain(String path) {
        return getFile(path).exists();
    }

    private File getFile(String path) {
        return new File(this.clientConfig.getRootPath() + File.separator + path);
    }
}
