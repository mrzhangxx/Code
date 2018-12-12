package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitMQConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author shuaizhang
 * @mail zs951027@126.com
 * @date 18/11/5 下午2:57
 */
@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 根据条件模糊查询
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        //条件匹配器
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        //2.创建查询条件实体对象
        CmsPage cmsPage = new CmsPage();
        //站点id
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        Example<CmsPage> example = Example.of(cmsPage, matcher);
        //页码
        page = page - 1;
        //分页对象
        Pageable pageable = new PageRequest(page, size);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> pageQueryResult = new QueryResult<CmsPage>();
        pageQueryResult.setList(cmsPages.getContent());
        pageQueryResult.setTotal(cmsPages.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS, pageQueryResult);
    }

    /**
     * cmsPage添加方法
     *
     * @param cmsPage 需要添加的cmsPage数据
     * @return CmsPageResult Api接口规范的响应结果对象
     */
    public CmsPageResult add(CmsPage cmsPage) {
        //1.如果传来的cmsPage数据为空，返回失败结果
        if (ObjectUtils.isEmpty(cmsPage)) {
            //返回非法参数自定义异常信息
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //2.根据页面名称、站点Id、页面访问路径三个值的唯一性判断cmsPage是否存在
        CmsPage one = cmsPageRepository.findByPageNameAndPageWebPathAndSiteId(cmsPage.getPageName(), cmsPage.getPageWebPath(),
                cmsPage.getSiteId());

        if (!ObjectUtils.isEmpty(one)) {

            /*********返回页面存在的自定义异常信息**************/
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);

        }

        //3.如果mongodb中没有对应的数据，再保存cmsPage数据
        //为了确保数据的新增，可以设置cmsPage的Id为空
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 根据Id查询CmsPage数据
     *
     * @param id
     * @return
     */
    public CmsPageResult findById(String id) {
        //1.判断Id是否为空
        if (StringUtils.isBlank(id)) return null;
        //2.根据Id进行查询
        Optional<CmsPage> optional = cmsPageRepository.findById(id);

        //3.返回结果数据
        //  CmsPage cmsPage = optional.isPresent() ? optional.get() : null ;

        //4.根据查询后数据返回Cms结果
        if (optional.isPresent()) {
            return new CmsPageResult(CommonCode.SUCCESS, optional.get());
        } else {
            return new CmsPageResult(CommonCode.FAIL, null);
        }
    }

    /**
     * 根据CmsPage的Id值修改CmsPage数据
     *
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        //1.判断Id和cmsPage修改数据的合法性
        if (!StringUtils.isBlank(id) && (!ObjectUtils.isEmpty(cmsPage))) {
            //2.根据Id查询CmsPage数据
            CmsPageResult result = this.findById(id);
            //3.判断根据Id查询后的数据
            if (result.isSuccess()) {
                //获得CmsPage数据
                CmsPage one = result.getCmsPage();
                //站点id
                one.setSiteId(cmsPage.getSiteId());
                //别名
                one.setPageAliase(cmsPage.getPageAliase());
                //更新模板id
                one.setTemplateId(cmsPage.getTemplateId());
                //更新访问路径
                one.setPageWebPath(cmsPage.getPageWebPath());
                //更新物理路径
                one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
                //更新页面名称
                one.setPageName(cmsPage.getPageName());
                //更新dataUrl
                one.setDataUrl(cmsPage.getDataUrl());
                //4.执行更新
                CmsPage save = cmsPageRepository.save(one);
                //5.判断更新后的数据是否正确
                if (save != null) {
                    CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                    return cmsPageResult;
                }
            }
        }
        //7.返回修改失败的信息
        return new CmsPageResult(CommonCode.FAIL, cmsPage);
    }

    public ResponseResult delete(String id) {
        //1.判断id是否合法
        if (!StringUtils.isBlank(id)) {
            // 2.根据id删除数据
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        // 5.返回失败结果
        return new ResponseResult(CommonCode.FAIL);

    }

    /**
     * 获取dataURL地址
     *
     * @param id
     * @return
     */
    public CmsConfig findByConfigId(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if (optional.isPresent()) {
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        return null;
    }

    //执行页面静态化
    public String getPageHtml(String pageId) {
        //获取页面模型数据
        Map modelByPageId = this.getModelByPageId(pageId);
        if (modelByPageId == null) {
            //获取数据模型为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        //获取页面模板
        String template = getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(template)) {
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        //执行静态化
        String html = generateHtml(template, modelByPageId);
        if (StringUtils.isEmpty(html)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);

        }
        return html;

    }

    //生成页面静态化
    public String generateHtml(String template, Map model) {
        //生成配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板加载器
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("template", template);
        //配置模板加载器
        configuration.setTemplateLoader(templateLoader);
        //获取模板
        try {
            Template template1 = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取页面模板
    public String getTemplateByPageId(String pageId) {
        //查询页面信息
        CmsPageResult cmsPageResult = this.findById(pageId);
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        //获取到模板
        String templateId = cmsPage.getTemplateId();
        if (templateId == null) {
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            //获取到文件id
            String fileId = cmsTemplate.getTemplateFileId();
            //取出模板文件内容进行匹配
            GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
            //打开下载流对象
            GridFSDownloadStream stream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource
            GridFsResource resource = new GridFsResource(gridFSFile, stream);
            try {
                String content = IOUtils.toString(resource.getInputStream());
                return content;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //获取页面模型数据
    public Map getModelByPageId(String pageId) {
        //查询页面信息
        CmsPageResult cmsPageResult = this.findById(pageId);
        if (cmsPageResult == null) {
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //取出dataURL
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            //地址为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }

        ResponseEntity<Map> template = restTemplate.getForEntity(dataUrl, Map.class);
        //获取到页面数据
        Map body = template.getBody();
        return body;
    }

    //页面发布
    public ResponseResult post(String pageId){
        //执行页面静态化
        String pageHtml = this.getPageHtml(pageId);
        //将页面静态化文件存储到GridFs中
        saveHtml(pageId, pageHtml);
        //向MQ发消息
        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //向mq 发送消息
    private void sendPostPage(String pageId){
        //得到页面信息

        CmsPageResult cmsPageResult = findById(pageId);
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        if(cmsPage == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //创建消息对象
        Map<String,String> msg = new HashMap<>();
        msg.put("pageId",pageId);
        //转成json串
        String jsonString = JSON.toJSONString(msg);
        //发送给mq
        //站点id
        String siteId = cmsPage.getSiteId();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EX_ROUTING_CMS_POSTPAGE,siteId,jsonString);
    }
    //保存html到GridFS
    private CmsPage saveHtml(String pageId,String htmlContent){
        //先得到页面信息
        CmsPageResult cmsPageResult = this.findById(pageId);
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        if(cmsPage == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ObjectId objectId = null;
        try {
            //将htmlContent内容转成输入流
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            //将html文件内容保存到GridFS
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将html文件id更新到cmsPage中
        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }
}
