/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MailTest.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mail;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.paascloud.provider.PaasCloudOmcApplicationTests;
import com.paascloud.provider.service.OptFreeMarkerService;
import com.paascloud.provider.service.OptSendMailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
@Slf4j
public class MailTest extends PaasCloudOmcApplicationTests{
    @Resource
    private OptSendMailService optSendMailService;
    @Resource
    private OptFreeMarkerService optVelocityService;
    @Test
    @Ignore
    public void sendSimpleMailText() throws Exception {
        String subject = "测试sendSimpleMail邮件";
        String text = "<html><body><img src=\"cid:weixin\" >123456</body></html>";
        Set<String> to = Sets.newHashSet();
        to.add("xxxx@163.com");
        optSendMailService.sendSimpleMail(subject, text, to);
        Thread.sleep(20000);
    }

    @Test
    @Ignore
    public void getTemplateText() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "paascloud");
        map.put("url", "http://www.beian.gov.cn/uac/user/activeEmail?token=04fd7024ba324d6d841614a7d44507cd");
        map.put("dateTime", "2017-01-01 22:22:22");
        String templateLocation = "mail/sendRegisterSuccessTemplate.ftl";
        String template = optVelocityService.getTemplate(map, templateLocation);
        log.info(template);
    }

    @Test
    public void sendTemplateMailTest() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "paascloud");
        map.put("url", "http://www.beian.gov.cn/uac/user/activeEmail?token=04fd7024ba324d6d841614a7d44507cd");
        map.put("dateTime", "2017-01-01 22:22:22");

        String templateLocation = "mail/sendRegisterSuccessTemplate.ftl";
        String text = optVelocityService.getTemplate(map, templateLocation);

        String subject = "测试sendSimpleMail邮件";
        Set<String> to = Sets.newHashSet();
        to.add("xxxxx@163.com");

        int sendTemplateMail = optSendMailService.sendTemplateMail(subject, text, to);

        log.info("sendTemplateMailTest={}", sendTemplateMail);
    }

    @Test
    public void sendTemplateMailTest2() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "paascloud-sendTemplateMailTest2");
        map.put("url", "http://www.beian.gov.cn/uac/user/activeEmail?token=04fd7024ba324d6d841614a7d44507cd");
        map.put("dateTime", "2017-01-01 22:22:22");
        String templateLocation = "mail/sendRegisterSuccessTemplate.ftl";

        String subject = "测试sendSimpleMail邮件";
        Set<String> to = Sets.newHashSet();
        to.add("xxxx@qq.com");

        int sendTemplateMail = optSendMailService.sendTemplateMail(map, templateLocation, subject, to);

        log.info("sendTemplateMailTest2={}", sendTemplateMail);
    }
}
