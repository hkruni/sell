package com.imooc.controller;


import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class ElasticSearchController {

    @Autowired
    private TransportClient transportClient;

    /**
     * 根据id获取book信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/book/novel")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name ="id",defaultValue = "") String id) {
        GetResponse result = transportClient.prepareGet("book","novel",id).get();

        if(!result.isExists()) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity(result.getSource(), HttpStatus.OK);
        return new ResponseEntity(result.getSourceAsString(), HttpStatus.OK);
    }

    /**
     * 增加文档
     * @param title
     * @param author
     * @param word_count
     * @param publish_date
     * @return
     */
    @RequestMapping(value = "/add/book/novel",method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity get(@RequestParam(name ="title") String title,@RequestParam(name ="author") String author,
                              @RequestParam(name ="word_count") Integer word_count,
                              @RequestParam(name ="publish_date")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publish_date) {

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title",title)
                    .field("author",author)
                    .field("word_count",word_count)
                    .field("publish_date",publish_date.getTime())//毫秒时间
                    .endObject();
            IndexResponse result = transportClient.prepareIndex("book","novel")
                    .setSource(content)
                    .get();

            return new ResponseEntity(result.getId(),HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/book/novel")
    @ResponseBody
    public ResponseEntity delete(@RequestParam(name ="id") String id) {
        DeleteResponse result = transportClient.prepareDelete("book","novel",id).get();

        return new ResponseEntity(result.getResult().toString(),HttpStatus.OK);
    }

    @PutMapping("update/book/novel")
    @ResponseBody
    public ResponseEntity update(@RequestParam(name ="id") String id,
                                 @RequestParam(name ="title",required = false) String title,
                                 @RequestParam(name ="author",required = false) String author) {
        UpdateRequest update= new UpdateRequest("book","novel",id);
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder().startObject();
            if(title != null) {
                builder.field("title",title);
            }
            if(author != null) {
                builder.field("author",author);
            }
            builder.endObject();
            update.doc(builder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            UpdateResponse result = transportClient.update(update).get();
            return  new ResponseEntity(result.getGetResult().toString(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("query/book/novel")
    @ResponseBody
    public ResponseEntity query(@RequestParam(name = "author",required = false) String  author,
                                @RequestParam(name = "title",required = false) String  title,
                                @RequestParam(name = "gt_word_count",defaultValue = "0") Integer  gtWordCount,
                                @RequestParam(name = "lt_word_count",required = false) Integer  ltWordCount) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(author != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("author",author));
        }

        if(title != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title",title));
        }

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("word_count")
                .from(gtWordCount);
        if(ltWordCount !=null && ltWordCount > 0) {
            rangeQueryBuilder.to(rangeQueryBuilder);
        }
        boolQueryBuilder.filter(rangeQueryBuilder);
        SearchRequestBuilder builder = transportClient.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom(0)
                .setSize(10);
        System.out.println(builder);
        SearchResponse response = builder.get();
        List<Map<String ,Object>> result = new ArrayList<Map<String ,Object>>();

        for (SearchHit hit : response.getHits()) {
            result.add(hit.getSource());
        }

        return new ResponseEntity(result,HttpStatus.OK);
    }


}
