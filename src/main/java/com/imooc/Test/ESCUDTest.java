package com.imooc.Test;

import com.google.common.collect.Lists;
import com.imooc.dataobject.Book;
import com.imooc.util.JsonUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by hukai on 2018/9/23.
 */
public class ESCUDTest {

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


    /**
     * 根据ID查询单个文档
     * @param id
     */
    public static void getDocById(String id) {
        GetResponse response = client.prepareGet(INDEX, TYPE, id).execute().actionGet();
        System.out.println(response.getSourceAsString());
        System.out.println("-----------map-----------");
        for (Map.Entry<String, Object> entry : response.getSource().entrySet()) {
            System.out.println(entry.getKey() + "::" + entry.getValue());
        }
    }

    /**
     * 添加单个文档
     * @param
     */
    public static void addDoc(Book book) {

        String s = JsonUtil.obj2String(book);
        IndexResponse response = client.prepareIndex(INDEX, TYPE,book.getId()).setSource(s, XContentType.JSON).get();

        if (response.status() == RestStatus.CREATED) {
            System.out.println("创建成功");
        }

    }

    /**
     * 删除单个文档
     * @param
     */
    public static void deleteDoc(String id) {

        DeleteResponse response = client.prepareDelete(INDEX,TYPE,id).get();

        if (response.status() == RestStatus.OK) {
            System.out.println("删除成功");
        }

    }

    /**
     * 部分更新
     */
    public static void updatePost() throws IOException, ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(INDEX)
                .type(TYPE)
                .id("10")
                .doc(XContentFactory.jsonBuilder().startObject()
                        .field("author","姚明")
                        .endObject());
        UpdateResponse response = client.update(updateRequest).get();

        if(response.status() == RestStatus.OK) {
            System.out.println("更新成功");
        }

    }




    /**
     * 根据ID查询单个文档
     */
    public static void getDocByIds() {

        MultiGetResponse response = client.prepareMultiGet()
                .add(INDEX, TYPE, Lists.newArrayList("1", "2", "3"))
                .get();

        for (MultiGetItemResponse item : response) {
            GetResponse response1 = item.getResponse();
            for (Map.Entry<String, Object> entry : response1.getSourceAsMap().entrySet()) {
                System.out.println(entry.getKey() + ":" +entry.getValue());
            }
        }

    }


    private static void bulkAddDoc() {

        Book book1 = new Book("33",3000,"小米","大数据分析",new Date());
        Book book2 = new Book("34",2000,"张平","微服务分析",new Date());

        BulkRequestBuilder builder = client.prepareBulk();
        builder.add(client.prepareIndex(INDEX,TYPE, book1.getId())
                .setSource(JsonUtil.obj2String(book1),XContentType.JSON));

        builder.add(client.prepareIndex(INDEX,TYPE, book2.getId())
                .setSource(JsonUtil.obj2String(book2),XContentType.JSON));

        BulkResponse responses = builder.get();
        if(responses.status() == RestStatus.OK) {
            System.out.println("批量添加成功");
        }

    }


    public static void  queryDelete() {

        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.termQuery("author", "狠狠"))
                .source(INDEX);

        BulkByScrollResponse response = builder.get();

        System.out.println("删除了" + response.getDeleted() + "条数据");

    }




    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
//        getDocById("1");
///
//        Book book = new Book("32",3000,"张三","大话西游",new Date());
//        addDoc(book);
        //deleteDoc("AWYEMjuOORtir5JfQNIT");

//        updatePost();

//        getDocByIds();

        //bulkAddDoc();
        queryDelete();

    }














}
