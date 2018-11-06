package com.imooc.Test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by hukai on 2018/9/23.
 */
public class ESQueryTest {

    private static TransportClient client = null;

    private static String INDEX = "book";

    private static String TYPE = "novel";
    static {
        Settings settings = Settings.builder()
                .put("cluster.name", "wali")
                .build();

        InetSocketTransportAddress master = null;
        try {
            master = new InetSocketTransportAddress(
                    InetAddress.getByName("localhost"), 9300
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(master);
    }


    public static void queryAll() {
        QueryBuilder qb = QueryBuilders.matchAllQuery();

        SearchResponse response = client.prepareSearch(INDEX).setQuery(qb).setSize(3).get();

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSource().get("author"));
        }
    }

    public static void main(String[] args) {
        queryAll();
    }


}
