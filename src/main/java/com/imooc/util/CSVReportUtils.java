package com.imooc.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author hukai
 *
 */
public class CSVReportUtils {
    public static String CSV_QUOTE = "\"";
    public static String CSV_QUOTE_ESCAPE = "\"\"";
    public static String CSV_DELIMITER = ",";
    public static char   CSV_DELIMITER_CHAR = ',';
    public static String CSV_NEWLINE = "\r\n";

    /**
     * 生成CSV格式的报表字符串
     *
     * @param titles List<String> 报表标题
     * @param data List<String[]> 报表数据
     * @return CSV格式的报表字符串
     */
    public static String genCSVReport(List<String> titles, List<List<String>> data){
        StringBuffer sb = new StringBuffer();

        if(titles!=null){
            for(String title: titles) {
                if(title==null){
                    title = "";
                }else{
                    title = title.replaceAll(CSV_QUOTE, CSV_QUOTE_ESCAPE);
                }
                sb.append(CSV_QUOTE).append(title).append(CSV_QUOTE).append(CSV_DELIMITER);
            }
            if (sb.charAt(sb.length() - 1) == CSV_DELIMITER_CHAR){
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(CSV_NEWLINE);
        }

        if(data!=null){
            for(List<String> items: data) {
                for(String item: items) {
                    if(item==null){
                        item = "";
                    }else{
                        item = item.replaceAll(CSV_QUOTE, CSV_QUOTE_ESCAPE);
                    }
                    sb.append(CSV_QUOTE).append(item).append(CSV_QUOTE).append(CSV_DELIMITER);
                }
                if (sb.charAt(sb.length() - 1) == CSV_DELIMITER_CHAR){
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(CSV_NEWLINE);
            }
        }

        return sb.toString();
    }

    public static void writeCsvData(String content,File file) throws IOException{
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "gbk");
        fw.write(content);
        fw.flush();
        fw.close();
    }

}
