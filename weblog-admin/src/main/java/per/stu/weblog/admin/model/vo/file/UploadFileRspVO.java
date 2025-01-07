package per.stu.weblog.admin.model.vo.file;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
/**
 * @description: 上传文件响应对象
 * @author: syl
 * @create: 2025-01-02 18:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileRspVO {

    /**
     * 文件的访问链接
     */
    private String url;

}

