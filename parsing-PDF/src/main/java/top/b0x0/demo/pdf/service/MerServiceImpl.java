package top.b0x0.demo.pdf.service;

import cn.hutool.extra.spring.SpringUtil;
import top.b0x0.demo.pdf.config.ExecutorConfig;
import top.b0x0.demo.pdf.entity.MerchantsDetail;
import top.b0x0.demo.pdf.entity.TemplateDataModel;
import top.b0x0.demo.pdf.util.MyBeanUtils;
import top.b0x0.demo.pdf.util.PdfTemplateDataProperties;
import top.b0x0.demo.pdf.util.PdfTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author TANG
 * @date 2021-02-20
 */
@Service
public class MerServiceImpl implements IMerService {

    @Autowired
    ExecutorConfig executorConfig;

    public MerchantsDetail findByMerId(String id) {
        MerchantsDetail list = new MerchantsDetail();
        if (id != null) {
            return MerchantsDetail.builder().
                    authorizer("李四").
                    borrower("李斯斯").
                    // 1 父母 2 兄弟姐妹 3 朋友
                            relationship("2").
                            urlAuthorizerSign("http://dev-bfp-image.oss-cn-beijing.aliyuncs.com/be43ea3360414bcab01654c0b4992d52_autograph1612507642793.png").
                            urlUsedNameSign("http://dev-bfp-image.oss-cn-beijing.aliyuncs.com/be43ea3360414bcab01654c0b4992d52_autograph1612507642793.png").
                            idCardNumber("450365598888888888").
                            otherPapers("港澳通行证").build();
        }
        return list;
    }

    @Override
    public List<String> activePdfUpload(String merId) {
        List<TemplateDataModel> list = new ArrayList<>();
        PdfTemplateDataProperties pdfTemplateDataProperties = SpringUtil.getBean(PdfTemplateDataProperties.class);
        // 模拟数据查询
        MerchantsDetail detail = this.findByMerId(merId);
        // 获取模板初始list
        List<TemplateDataModel> activeList = pdfTemplateDataProperties.getBsActiveList();
        for (TemplateDataModel model : activeList) {
            MyBeanUtils.copyPropertiesIgnoreNull(detail, model);
            model.setMerId(merId);
            list.add(model);
        }
        return PdfTemplateUtils.fileUpload(list, executorConfig.taskThreadPoolExecutor());
    }

    @Override
    public List<String> entryPdfUpload(String merId) {
        Set<String> set = new HashSet<>();
        List<TemplateDataModel> list = new ArrayList<>();

        PdfTemplateDataProperties pdfTemplateDataProperties = SpringUtil.getBean(PdfTemplateDataProperties.class);
        set.add(System.identityHashCode(pdfTemplateDataProperties) + "");

        // 模拟数据查询
        MerchantsDetail detail = this.findByMerId(merId);
        // 获取模板初始list
        List<TemplateDataModel> bsEntryList = pdfTemplateDataProperties.getBsEntryList();
        for (TemplateDataModel model : bsEntryList) {
            MyBeanUtils.copyPropertiesIgnoreNull(detail, model);
            model.setMerId(merId);
            list.add(model);
        }
        return PdfTemplateUtils.fileUpload(list, executorConfig.taskThreadPoolExecutor());
    }


    @Override
    public List<String> entryPdfUpload2(String merId, PdfTemplateDataProperties pdfTemplateDataProperties) {
        List<TemplateDataModel> list = new ArrayList<>();
        // 模拟数据查询
        MerchantsDetail detail = this.findByMerId(merId);
        // 获取模板初始list
        List<TemplateDataModel> bsEntryList = pdfTemplateDataProperties.getBsEntryList();
        for (TemplateDataModel model : bsEntryList) {
            MyBeanUtils.copyPropertiesIgnoreNull(detail, model);
            list.add(model);
        }
        return PdfTemplateUtils.fileUpload(list, executorConfig.taskThreadPoolExecutor());
    }

    @Override
    public void threadPoolTest() {
        ThreadPoolExecutor threadPoolExecutor = executorConfig.taskThreadPoolExecutor();
        System.out.println(Thread.currentThread().getName() + "- threadPoolExecutor = " + threadPoolExecutor);
    }
}
