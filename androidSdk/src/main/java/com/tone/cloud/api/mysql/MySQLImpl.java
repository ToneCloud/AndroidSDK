package com.tone.cloud.api.mysql;

import com.tone.cloud.ToneCloud;
import com.tone.cloud.ToneCloudCallback;
import com.tone.cloud.ToneCloudListener;
import com.tone.cloud.net.ToneHttpClient;
import com.tone.cloud.utils.RC4Util;
import com.tone.cloud.utils.ToneCloudUtils;

public class MySQLImpl implements MySQL {

    private static final String baseUrl = "http://mysql.t1.huayi-w.cn/tmysql.sql";

    private static final MySQL instance = new MySQLImpl();

    public static MySQL getInstance() {
        return instance;
    }

    private MySQLImpl() {
    }

    @Override
    public void insert(String table, String column, String value, ToneCloudListener listener) {
        ToneHttpClient.get(insertUrl(table, column, value),listener);
    }

    @Override
    public void update(String table, String set, String where, ToneCloudListener listener) {
        ToneHttpClient.get(updateUrl(table, set, where),listener);
    }

    @Override
    public void select(String table, String column, String where, ToneCloudListener listener) {
        ToneHttpClient.get(selectUrl(table, column, where),listener);
    }

    @Override
    public void delete(String table, String where, ToneCloudListener listener) {
        ToneHttpClient.get(deleteUrl(table,where),listener);
    }

    @Override
    public void insert(String table, String column, String value, ToneCloudCallback callback) {
        ToneHttpClient.get(insertUrl(table, column, value),callback);
    }

    @Override
    public void update(String table, String set, String where, ToneCloudCallback callback) {
        ToneHttpClient.get(updateUrl(table, set, where),callback);
    }

    @Override
    public void select(String table, String column, String where, ToneCloudCallback callback) {
        ToneHttpClient.get(selectUrl(table, column, where),callback);
    }

    @Override
    public void delete(String table, String where, ToneCloudCallback callback) {
        ToneHttpClient.get(deleteUrl(table, where),callback);
    }

    private String insertUrl(String table, String column, String value) {
        StringBuilder sb = new StringBuilder();
        String type = "insert";
        String rTable = RC4Util.encryRC4String(table);
        String rColumn = RC4Util.encryRC4String(column);
        String rValue = RC4Util.encryRC4String(value);
        String signature = ToneCloudUtils.md5WithKeys(type + table + column + value);
        sb.append(baseUrl)
                .append("?type=").append(type)
                .append("&key=").append(ToneCloud.getPublicKey())
                .append("&table=").append(rTable)
                .append("&column=").append(rColumn)
                .append("&value=").append(rValue)
                .append("&signature=").append(signature);

        return sb.toString();
    }

    private String updateUrl(String table, String set, String where) {
        StringBuilder sb = new StringBuilder();
        String type = "update";
        String rTable = RC4Util.encryRC4String(table);
        String rSet = RC4Util.encryRC4String(set);
        String rWhere = RC4Util.encryRC4String(where);
        String signature = ToneCloudUtils.md5WithKeys(type + table + set+ where);
        sb.append(baseUrl)
                .append("?type=").append(type)
                .append("&key=").append(ToneCloud.getPublicKey())
                .append("&table=").append(rTable)
                .append("&set=").append(rSet)
                .append("&where=").append(rWhere)
                .append("&signature=").append(signature);
        return sb.toString();
    }

    private String selectUrl(String table, String column, String where) {
        StringBuilder sb = new StringBuilder();
        String type = "select";
        String rTable = RC4Util.encryRC4String(table);
        String rColumn = RC4Util.encryRC4String(column);
        String rWhere = RC4Util.encryRC4String(where);
        String signature = ToneCloudUtils.md5WithKeys(type + table + column + where);
        sb.append(baseUrl)
                .append("?type=").append(type)
                .append("&key=").append(ToneCloud.getPublicKey())
                .append("&table=").append(rTable)
                .append("&column=").append(rColumn)
                .append("&where=").append(rWhere)
                .append("&signature=").append(signature);
        return sb.toString();
    }

    private String deleteUrl(String table, String where) {
        StringBuilder sb = new StringBuilder();
        String type = "delete";
        String rTable = RC4Util.encryRC4String(table);
        String rWhere = RC4Util.encryRC4String(where);
        String signature = ToneCloudUtils.md5WithKeys(type + table+ where);
        sb.append(baseUrl)
                .append("?type=").append(type)
                .append("&key=").append(ToneCloud.getPublicKey())
                .append("&table=").append(rTable)
                .append("&where=").append(rWhere)
                .append("&signature=").append(signature);
        return sb.toString();
    }
}
