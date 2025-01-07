package per.stu.weblog.admin.service;


import org.springframework.web.multipart.MultipartFile;
import per.stu.weblog.common.utils.Response;

/**
 * @description: 文件管理服务
 * @author: syl
 * @create: 2025-01-02 18:03
 **/
public interface AdminFileService {

    /**
     * 上传文件
     * @param file 文件
     * @return Response
     */
    Response uploadFile(MultipartFile file);
}
