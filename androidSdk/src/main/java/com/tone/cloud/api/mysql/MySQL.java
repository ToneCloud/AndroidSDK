package com.tone.cloud.api.mysql;

import com.tone.cloud.ToneCloudCallback;
import com.tone.cloud.ToneCloudListener;
import com.tone.cloud.api.ToneAPI;

public interface MySQL extends ToneAPI {

    /**
     * 添加数据
     * @param table 数据表名
     * @param column 字段名
     * @param value 字段对应的值
     * @param listener 回调
     */
    @Deprecated
    void insert(String table,String column,String value,ToneCloudListener listener);


    /**
     * 更新数据
     * @param table 数据表名
     * @param set 更新的值
     * @param where 更新条件
     * @param listener 回调
     */
    @Deprecated
    void update(String table,String set,String where,ToneCloudListener listener);

    /**
     * 查询数据
     * @param table 数据表名
     * @param column 查询字段
     * @param where 查询条件
     * @param listener 回调
     */
    @Deprecated
    void select(String table,String column,String where,ToneCloudListener listener);

    /**
     * 删除数据
     * @param table 数据表名
     * @param where 删除条件
     * @param listener 回调
     */
    @Deprecated
    void delete(String table,String where,ToneCloudListener listener);

    void insert(String table, String column, String value, ToneCloudCallback callback);

    void update(String table,String set,String where,ToneCloudCallback callback);

    void select(String table,String column,String where,ToneCloudCallback callback);

    void delete(String table,String where,ToneCloudCallback callback);

}
