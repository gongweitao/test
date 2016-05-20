package com.lianjia.dao.base;

import com.lianjia.common.datasource.DataRegion;
import com.lianjia.common.datasource.mybatis.DataRegionSqlSessionDaoSupport;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by myths on 2016/5/19.
 */
public class BaseDao<T> extends DataRegionSqlSessionDaoSupport {
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    protected SqlSession getBatchSqlSession(){
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        return batchSqlSession;
    }

    protected String obtainSqlID(String sqlId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName()).append(".").append(sqlId);
        return stringBuilder.toString();
    }

    public T queryByPk(DataRegion region, Long pk, String... includes){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("pk",pk);
        if(includes.length > 0){
            List<String> includesCols = new ArrayList<String>();
            for(String include : includes){
                includesCols.add(include);
            }
            params.put("includes",includesCols);
        }
        T result = getSqlSession().selectOne(region,obtainSqlID("queryByPk"),params);
        return result;
    }
}
