package per.stu.weblog.admin.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import per.stu.weblog.admin.model.vo.file.UploadFileRspVO;
import per.stu.weblog.admin.service.AdminFileService;
import per.stu.weblog.admin.utils.MinioUtil;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.excption.BizException;
import per.stu.weblog.common.utils.Response;

/**
 * @description:  文件管理服务实现类
 * @author: syl
 * @create: 2025-01-02 18:04
 **/
@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {

    @Autowired
    private MinioUtil minioUtil;

    @Override
    public Response uploadFile(MultipartFile file) {
        try {
            // 上传文件
            String Url = minioUtil.uploadFile(file);
            return Response.success(UploadFileRspVO.builder().url(Url).build());
        }catch (Exception e) {
            log.error("==> 上传文件至 Minio 错误: ", e);
            // 手动抛出业务异常，提示 “文件上传失败”
            throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
        }
    }
}
