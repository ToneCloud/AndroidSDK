package com.tone.cloud.api.mysql;

import com.tone.cloud.ToneCloud;
import com.tone.cloud.ToneCloudCallback;
import com.tone.cloud.utils.net.ToneHttp;

public class MySql {

    // http://mysql.t1.huayi-w.cn/tmysql.sql
    private static final String baseUrl = "http://v1.api.t1y.net/mysql.sql";

    private static final MySql instance = new MySql();

    public static MySql getInstance() {
        return instance;
    }

    private MySql() {}

    public void select(String table, String column, String where, String sort, String sort_column,
                       ToneCloudCallback callback) {
        MysqlJsonBuilder jsonBuilder = new MysqlJsonBuilder();
        jsonBuilder.type(MySqlType.SELECT);
        jsonBuilder.table(table);
        jsonBuilder.column(column);
        jsonBuilder.where(where);
        jsonBuilder.sort(sort);
        jsonBuilder.sortColumn(sort_column);
        String data = jsonBuilder.build();
        ToneHttp.post(getUrl(data),callback);
    }

    public void insert(String table, String column, String value, ToneCloudCallback callback) {
        MysqlJsonBuilder jsonBuilder = new MysqlJsonBuilder();
        jsonBuilder.type(MySqlType.INERT);
        jsonBuilder.table(table);
        jsonBuilder.column(column);
        jsonBuilder.value(value);
        ToneHttp.post(getUrl(jsonBuilder.build()),callback);
    }

    public void delete(String table, String where, ToneCloudCallback callback) {
        MysqlJsonBuilder jsonBuilder = new MysqlJsonBuilder();
        jsonBuilder.type(MySqlType.DELETE);
        jsonBuilder.table(table);
        jsonBuilder.where(where);
        ToneHttp.post(getUrl(jsonBuilder.build()),callback);
    }

    public void update(String table, String set, String where, ToneCloudCallback callback) {
        MysqlJsonBuilder jsonBuilder = new MysqlJsonBuilder();
        jsonBuilder.type(MySqlType.UPDATE);
        jsonBuilder.table(table);
        jsonBuilder.set(set);
        jsonBuilder.where(where);
        ToneHttp.post(getUrl(jsonBuilder.build()),callback);
    }

    public void command(String cmd, ToneCloudCallback callback) {
        MysqlJsonBuilder jsonBuilder = new MysqlJsonBuilder();
        jsonBuilder.type(MySqlType.CMD);
        jsonBuilder.command(cmd);
        ToneHttp.post(getUrl(jsonBuilder.build()),callback);
    }

    private static String getUrl(String data) {
        return baseUrl + "?key=" + ToneCloud.getPublicKey() + "&data=" + data;
    }

    public void post(MysqlJsonBuilder builder, ToneCloudCallback callback) {
        ToneHttp.post(getUrl(builder.build()),callback);
    }

}
