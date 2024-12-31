package per.stu.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.stu.weblog.admin.model.vo.tag.*;
import per.stu.weblog.admin.service.AdminTagService;
import per.stu.weblog.common.domain.dos.TagDO;
import per.stu.weblog.common.domain.mapper.TagMapper;
import per.stu.weblog.common.enums.ResponseCodeEnum;
import per.stu.weblog.common.model.vo.SelectResVO;
import per.stu.weblog.common.utils.PageResponse;
import per.stu.weblog.common.utils.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response addTagList(AddTagReqVO addTagReqVO) {
        try {
            // vo 转 do
            List<TagDO> collect = addTagReqVO.getTagNames().stream().map(tagName ->
                    TagDO.builder().name(tagName).createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).isDeleted(false).build()
            ).collect(Collectors.toList());
            // 批量插入
            saveBatch(collect);
        }catch (Exception e){
            log.warn("add tag list error, {}", e.getMessage());
        }
        return Response.success();
    }

    @Override
    public PageResponse findTagList(FindTagPageListReqVO findTagPageListReqVO) {

        // 获取分页参数
        Long  pageNum = findTagPageListReqVO.getCurrent();
        Long  pageSize = findTagPageListReqVO.getSize();
        String name = findTagPageListReqVO.getName();
        LocalDate startDate = findTagPageListReqVO.getStartDate();
        LocalDate endDate = findTagPageListReqVO.getEndDate();
        Page<TagDO> tagList = tagMapper.findTagList(pageNum, pageSize, name, startDate, endDate);
        List<TagDO> records = tagList.getRecords();

        //DO转VO
        List<FindTagPageListResVO> vos = null;
        if (CollectionUtils.isNotEmpty(records)){
            vos = records.stream().map(record ->
                    FindTagPageListResVO.builder()
                            .id(record.getId())
                            .name(record.getName())
                            .createTime(record.getCreateTime())
                            .build()
            ).collect(Collectors.toList());
        }
        return PageResponse.success(tagList,vos);
    }

    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
        // 分类 ID
        Long categoryId = deleteTagReqVO.getId();

        // 删除分类
        int count = tagMapper.deleteById(categoryId);

        return count > 0 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_IS_NOT_EXIST);
    }

    @Override
    public Response searchTagList(FindTagReqVO findTagReqVO) {
        String key = findTagReqVO.getKey();
        List<TagDO> tagList = tagMapper.searchTagList(key);
        // DO 转 VO
        List<SelectResVO> vos = null;
        if (CollectionUtils.isNotEmpty(tagList)){
            vos = tagList.stream().map(tag ->
                    SelectResVO.builder()
                            .label(tag.getName())
                            .value(tag.getId())
                            .build()
            ).collect(Collectors.toList());
        }
        return Response.success(vos);
    }


}
