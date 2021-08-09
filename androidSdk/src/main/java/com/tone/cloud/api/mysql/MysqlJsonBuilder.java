package com.tone.cloud.api.mysql;


import com.tone.cloud.ToneCloud;
import com.tone.cloud.utils.ToneCloudUtils;

import java.util.HashMap;
import java.util.Map;

public class MysqlJsonBuilder {

    private final Map<String,String> params = new HashMap<>();
    private MySqlType mySqlType = null;

    public String build() {
        checkParams();
        switch (mySqlType) {
            case INERT: {
                return buildInsert();
            }
            case DELETE: {
                return buildDelete();
            }
            case SELECT: {
                return buildSelect();
            }
            case UPDATE: {
                return buildUpdate();
            }
            case CMD: {
                return buildCmd();
            }
        }
        throw new IllegalStateException("MySQL type 为空");
    }

    public void append(String key,String value) {
        params.put(key,value);
    }

    public void type(MySqlType type) {
        mySqlType = type;
    }

    public void table(String value) {
        append("table",value);
    }

    public void column(String value) {
        append("column",value);
    }

    public void set(String value) {
        append("set",value);
    }

    public void where(String value) {
        append("where",value);
    }

    public void value(String value) {
        append("value",value);
    }

    public void sort(String value) {
        append("sort",value);
    }

    public void sortColumn(String value) {
        append("sort_column",value);
    }

    public void command(String cmd) {
        append("command",cmd);
    }

    private void checkParams() {

    }

    private String buildSelect() {
        String table = params.get("table");
        if (table == null) table = "";
        String column = params.get("column");
        if (column == null) column = "";
        String where = params.get("where");
        if (where == null) where = "";
        String sort = params.get("sort");
        if (sort == null) sort = "";
        String sort_column = params.get("sort_column");
        if (sort_column == null) sort_column = "";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(pair("type","select")).append(",");
        sb.append(pair("table",table)).append(",");
        sb.append(pair("column",column)).append(",");
        sb.append(pair("where",where)).append(",");
        sb.append(pair("sort",sort)).append(",");
        sb.append(pair("sort_column",sort_column)).append(",");
        String md5 = ToneCloudUtils.md5(ToneCloud.getPublicKey() + "select" + table + column
                + where + sort + sort_column + ToneCloud.getPrivateKey());
        sign(sb,md5);
        // sb.append(pair("signature",md5)).append(",");
        // sb.append(pair("timestamp",System.currentTimeMillis() / 1000));
        sb.append("}");
        return ToneCloudUtils.encryptRC4(sb.toString());
    }

    private String buildUpdate() {
        String table = params.get("table");
        if (table == null) table = "";
        String set = params.get("set");
        if (set == null) set = "";
        String where = params.get("where");
        if (where == null) where = "";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(pair("type","update")).append(",");
        sb.append(pair("table",table)).append(",");
        sb.append(pair("set",set)).append(",");
        sb.append(pair("where",where)).append(",");
        String md5 = ToneCloudUtils.md5(ToneCloud.getPublicKey() + "update" + table + set
                + where + ToneCloud.getPrivateKey());
        sign(sb,md5);
        sb.append("}");
        return ToneCloudUtils.encryptRC4(sb.toString());
    }

    private String buildDelete() {
        String table = params.get("table");
        if (table == null) table = "";
        String where = params.get("where");
        if (where == null) where = "";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(pair("type","delete")).append(",");
        sb.append(pair("table",table)).append(",");
        sb.append(pair("where",where)).append(",");
        String md5 = ToneCloudUtils.md5(ToneCloud.getPublicKey() + "delete" + table
                + where + ToneCloud.getPrivateKey());
        sign(sb,md5);
        sb.append("}");
        return ToneCloudUtils.encryptRC4(sb.toString());
    }

    private String buildInsert() {
        String table = params.get("table");
        if (table == null) table = "";
        String column = params.get("column");
        if (column == null) column = "";
        String value = params.get("value");
        if (value == null) value = "";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(pair("type","insert")).append(",");
        sb.append(pair("table",table)).append(",");
        sb.append(pair("column",column)).append(",");
        sb.append(pair("value",value)).append(",");
        String md5 = ToneCloudUtils.md5(ToneCloud.getPublicKey() + "insert" + table
                + column + value + ToneCloud.getPrivateKey());
        sign(sb,md5);
        sb.append("}");
        return ToneCloudUtils.encryptRC4(sb.toString());
    }

    public String buildCmd() {
        String cmd = params.get("command");
        if (cmd == null) cmd = "";
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(pair("type","sql")).append(",");
        sb.append(pair("command",cmd)).append(",");
        String md5 = ToneCloudUtils.md5(ToneCloud.getPublicKey() + "sql" + cmd
                + ToneCloud.getPrivateKey());
        sign(sb,md5);
        sb.append("}");
        return ToneCloudUtils.encryptRC4(sb.toString());
    }

    private static void sign(StringBuilder sb,String signature) {
        sb.append(pair("signature",signature)).append(",");
        sb.append(pair("timestamp",System.currentTimeMillis() / 1000));
    }
    private static String pair(String key,String value) {
        return sr(key) + ":" + sr(value);
    }

    private static String pair(String key,long value) {
        return sr(key) + ":" + value;
    }

    private static String sr(String value) {
        return "\"" + value + "\"";
    }

}
