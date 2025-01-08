package per.stu.weblog.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import per.stu.weblog.admin.model.vo.blogsettings.FindBlogSettingsRspVO;
import per.stu.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import per.stu.weblog.common.domain.dos.BlogSettingsDO;

/**
 * @description:  BlogSettingsConvert
 * @author: syl
 * @create: 2025-01-08 10:05
 **/
@Mapper
public interface BlogSettingsConvert {
    /**
     * 初始化 convert 实例
     */
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 将 VO 转化为 DO
     * @param bean
     * @return
     */
    BlogSettingsDO convertVO2DO(UpdateBlogSettingsReqVO bean);

    /**
     * 将 DO 转化为 VO
     * @param bean
     * @return
     */
    FindBlogSettingsRspVO convertDO2VO(BlogSettingsDO bean);

}

