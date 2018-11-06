package com.imooc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.dataobject.Book;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by hukai on 2018/9/23.
 */

@Controller
public class ESController {


    @Autowired
    private TransportClient transportClient;


    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/add/book/novel2",method = RequestMethod.POST)
    @ResponseBody
    public String get(Book book) {
        try {
            IndexResponse indexResponse = this.transportClient.prepareIndex("book", "novel")
                    .setSource(objectMapper.writeValueAsBytes(book), XContentType.JSON)
                    .get();

            if (RestStatus.CREATED == indexResponse.status()) {
                return "创建成功";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "创建失败";
    }

    /**
     * 条件删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/book/novel2",method = RequestMethod.POST)
    @ResponseBody
    public String delete(Integer id) {
        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(transportClient)
                .filter(QueryBuilders.termQuery("id", id))
                .source("book");

        BulkByScrollResponse bulkByScrollResponse = builder.get();
        System.out.println(bulkByScrollResponse.getDeleted());

        return "删除成功";
    }



}
