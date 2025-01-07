package per.stu.weblog.admin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import per.stu.weblog.admin.service.AdminFileService;
import per.stu.weblog.common.aspect.ApiOperationLog;
import per.stu.weblog.common.utils.Response;

/**
 * @description:  文件管理
 * @author: syl
 * @create: 2025-01-02 18:08
 **/
@RestController
@RequestMapping("/interface/admin/file")
@Api(tags = "Admin 文件模块")
public class AdminFileController {

    @Autowired
    private AdminFileService fileService;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    @ApiOperationLog(description = "文件上传")
    public Response uploadFile(@RequestParam MultipartFile file) {
        return fileService.uploadFile(file);
    }

}

