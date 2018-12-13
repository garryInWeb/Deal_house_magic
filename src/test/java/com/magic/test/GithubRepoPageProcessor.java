package com.magic.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by zhengtengfei on 2018/12/13.
 */
public class GithubRepoPageProcessor implements PageProcessor {
    private Site site = Site.me().setDomain(".github.com");

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().css("div.pagination").links().regex(".*/search\\?l=java.*").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//a[@class='v-align-middle']/text()").all());
        page.putField("starNum", page.getHtml().xpath("//div[@class='pl-2']//a[@class='muted-link']/text()").all());

//        page.putField("content", page.getHtml().$("div.content").toString());
//        page.putField("tags",page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/search?l=Java&p=1&q=stars%3A%3E1&s=stars&type=Repositories")
                .addPipeline(new ConsolePipeline()).thread(5).run();
    }
}
