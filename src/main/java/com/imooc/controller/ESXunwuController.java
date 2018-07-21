package com.imooc.controller;

import com.imooc.vo.Query;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ESXunwuController {

    @Autowired
    private TransportClient transportClient;

    /**
     * 对某个城市、某个区域的房子数，聚合统计
     * http://localhost:8080/aggregateDistrictHouse?city=bj&region=cpq
     * @param city
     * @param region
     * @return
     */
    @RequestMapping(value ="aggregateDistrictHouse")
    @ResponseBody
    public Object aggregate(@RequestParam("city") String city,@RequestParam("region") String region) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("cityEnName","bj"))
                .filter(QueryBuilders.termQuery("regionEnName","hdq"));


        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("region").field("regionEnName");

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("xunwu")
                .setTypes("house")
                .setQuery(boolQueryBuilder)
                .addAggregation(aggregationBuilder)
                .setSize(0);

        SearchResponse response = searchRequestBuilder.get();
        if (response.status() == RestStatus.OK) {
            Terms terms = response.getAggregations().get("region");
            if (terms.getBuckets() != null && !terms.getBuckets().isEmpty()) {
                System.out.println(terms.getBuckets() + " : " + terms.getBucketByKey(region).getDocCount());

            }
        }

        return "hello,world";
    }

    /**
     * 测试多个字段的聚合
     * @return
     */
    @RequestMapping(value = "aggregateSubway")
    @ResponseBody
    public Object aggregateSubway() {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termQuery("cityEnName","bj"))
                .filter(QueryBuilders.termQuery("cityEnName","bj"));

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("subwayline").field("subwayLineName");
        AggregationBuilder aggregationBuilder2 = AggregationBuilders.terms("subwayStation").field("subwayStationName");

        SearchRequestBuilder requestBuilder = transportClient.prepareSearch("xunwu").setTypes("house")
                .setQuery(boolQueryBuilder)
                .addAggregation(aggregationBuilder)
                .addAggregation(aggregationBuilder2);

        SearchResponse response = requestBuilder.get();

        Terms terms = response.getAggregations().get("subwayline");
        for (Terms.Bucket bucket : terms.getBuckets()) {
            System.out.println(bucket.getKeyAsString() + "：" + bucket.getDocCount());
        }

        Terms terms2 = response.getAggregations().get("subwayStation");
        for (Terms.Bucket bucket : terms2.getBuckets()) {
            System.out.println(bucket.getKeyAsString() + "：" + bucket.getDocCount());
        }

        return "hello,world";

    }

    /**
     * 关键字进行搜索
     * @param key
     * @return
     */
    @RequestMapping(value="search")
    @ResponseBody
    public Object findHouseByCityDistrict(@RequestParam("key") String key) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //在多个字段查询关键字
        boolQueryBuilder.must(
                QueryBuilders.multiMatchQuery(key, "subwayLineName","title","street","district"
                ));
        SearchRequestBuilder requestBuilder = transportClient.prepareSearch("xunwu")
                .setTypes("house")
                .setQuery(boolQueryBuilder)
                .setFrom(0)
                .setSize(10);
        SearchResponse response = requestBuilder.get();
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSource());
            String.valueOf(hit.getSource().get("district"));
        }

        return "hello,world";
    }


}
