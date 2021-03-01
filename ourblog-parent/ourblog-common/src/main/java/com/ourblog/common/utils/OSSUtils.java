package com.ourblog.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.ourblog.common.model.response.Result;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OSSUtils {

    /**
     * OSS简单上传 - 字符串上传(默认生成.txt文件，生成格式 /年-月-日/randId.txt)
     *
     * @param content
     * @return: com.cx.jx.bean.ResultMsg
     **/
    public static Result simpleUpload(String content) {
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI4GKLPvgNBQxU3DAzMnKP";
        String accessKeySecret = "gTObKawEChj1tBR4y2pWl9UFbv2eqF";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String objectName = formatter.format(date) + "/" + new IdWorker(0, 0, 0).nextId() + ".txt";
        PutObjectRequest putObjectRequest = new PutObjectRequest("yudachi", objectName, new ByteArrayInputStream(content.getBytes()));

        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();

        return new Result(objectName);
    }

    /**
     * OSS简单上传 - 字节数组上传(生成格式 /年-月-日/randId.ext)
     *
     * @param content 上传内容
     * @param ext     扩展名(例如.jpg 等)
     * @return: com.cx.jx.bean.ResultMsg
     **/
    public static Result simpleUpload(byte[] content, String ext) {
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI4GKLPvgNBQxU3DAzMnKP";
        String accessKeySecret = "gTObKawEChj1tBR4y2pWl9UFbv2eqF";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String objectName = formatter.format(date) + "/" + new IdWorker(0, 0, 0).nextId() + ext;
        PutObjectRequest putObjectRequest = new PutObjectRequest("yudachi", objectName, new ByteArrayInputStream(content));
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return new Result(objectName);
    }

    /**
     * OSS简单上传 - 网络流上传(生成格式 /年-月-日/randId.ext)
     *
     * @param inputStream 上传内容
     * @param ext         扩展名(例如.jpg 等)
     * @return: com.cx.jx.bean.ResultMsg
     **/
    public static Result simpleUpload(InputStream inputStream, String ext) {
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI4GKLPvgNBQxU3DAzMnKP";
        String accessKeySecret = "gTObKawEChj1tBR4y2pWl9UFbv2eqF";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String objectName = formatter.format(date) + "/" + new IdWorker(0, 0, 0).nextId() + ext;
        PutObjectRequest putObjectRequest = new PutObjectRequest("yudachi", objectName, inputStream);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return new Result(objectName);
    }
    /*

     *//**
     * OSS简单上传 - 本地文件上传(生成格式 /年-月-日/randId.ext)
     *
     * @return: com.cx.jx.bean.ResultMsg
     **//*
    public static Result simpleUpload() {
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI4GKLPvgNBQxU3DAzMnKP";
        String accessKeySecret = "gTObKawEChj1tBR4y2pWl9UFbv2eqF";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String objectName = formatter.format(date) + "/" + new IdWorker(0, 0, 0).nextId() + getExt(file.getName());
        PutObjectRequest putObjectRequest = new PutObjectRequest("yudachi", objectName , file);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return new Result( objectName);
    }*/

    /**
     * OSS断点续传
     *
     * @param file 上传内容
     * @return: com.cx.jx.bean.ResultMsg
     **/
    public static Result checkpointUpload(File file) {
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI4GKLPvgNBQxU3DAzMnKP";
        String accessKeySecret = "gTObKawEChj1tBR4y2pWl9UFbv2eqF";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(getContentType(file.getName()));
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String bucketName = "yudachi";
        String objectName = formatter.format(date) + "/" + new IdWorker(0, 0, 0).nextId() + getExt(file.getName());
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, objectName);
        uploadFileRequest.setUploadFile(file.getName());
        // 指定上传并发线程数，默认为1。
        uploadFileRequest.setTaskNum(5);
        // 指定上传的分片大小。
        uploadFileRequest.setPartSize(1 * 1024 * 1024);
        // 开启断点续传，默认关闭。
        uploadFileRequest.setEnableCheckpoint(true);
        // 记录本地分片上传结果的文件。
        uploadFileRequest.setCheckpointFile("uploadFile.ucp");
        // 文件的元数据。
        uploadFileRequest.setObjectMetadata(meta);

        // 断点续传上传。
        try {
            ossClient.uploadFile(uploadFileRequest);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return new Result("上传成功");
    }

    /**
     * 流式获取文件，获取的值是BufferedReader读出来的值
     *
     * @param path 文件路径
     * @return: com.cx.jx.bean.ResultMsg
     **/
    public static Result getStream(String path) {
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI4GKLPvgNBQxU3DAzMnKP";
        String accessKeySecret = "gTObKawEChj1tBR4y2pWl9UFbv2eqF";
        String bucketName = "yudachi";
        String objectName = path;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        String line = null;
        while (true) {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return new Result(line);
    }

    /**
     * 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
     *
     * @param path 文件路径
     * @param file 本地文件
     * @return: com.cx.jx.bean.ResultMsg
     **/
    public static Result getLocalFile(String path, File file) {
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI4GKLPvgNBQxU3DAzMnKP";
        String accessKeySecret = "gTObKawEChj1tBR4y2pWl9UFbv2eqF";
        String bucketName = "yudachi";
        String objectName = path;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), file);

        // 关闭OSSClient。
        ossClient.shutdown();
        return new Result("获取成功");
    }

    public static final String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static final String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".jpg".equalsIgnoreCase(fileExtension)) {
            return "image/jpg";
        }
        if (".png".equalsIgnoreCase(fileExtension)) {
            return "image/png";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        return "text/html";
    }
}
