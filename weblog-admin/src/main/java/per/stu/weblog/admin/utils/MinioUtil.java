package per.stu.weblog.admin.utils;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import per.stu.weblog.admin.config.MinioProperties;

import java.util.UUID;

/**
 * @description: Minio工具类
 * @author: syl
 * @create: 2025-01-02 17:54
 **/
@Component
@Slf4j
public class MinioUtil {

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception {
        // 判断文件是否为空
        if (file.isEmpty()) {
            log.error(" =====> 上传文件异常 文件为空");
            throw new RuntimeException("文件不能为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 文件的 Content-Type
        String contentType = file.getContentType();
        // 生成文件的唯一标识符
        String key = UUID.randomUUID().toString().replace("-", "");
        // 获取文件名后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //拼接文件名
        String objectName = String.format("%s%s", key, suffix);
        log.info(" =====> 开始上传文件 objectName:{}, contentType:{}", objectName, contentType);
        // 上传文件
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(objectName)
                .contentType(contentType)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
        //返回文件路径
        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), objectName);
        log.info(" =====> 上传文件到 minio成功  访问路径:{}", url);
        return url;
    }
}
