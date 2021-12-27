package ru.javamaster.javamaster.dao.abstr.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface PaginationDao<K extends Serializable, T> extends ReadWriteDao<K, T>{
    List<T> getDataByNumberPage(int pageSize, int pageNumber);
    List<T> getDataByNumberPageAndFilterPattern(int pageSize, int pageNumber, String filterPattern);
    List<T> getDataByNumberPageAndFilterPatternAndParametersMap(int pageSize, int pageNumber, String filterPattern, Map<String, Object> parametersMap);
    Long getCount();
    Long getCountByFilterPattern(String filteringPattern);
    Long getCountByFilterPatternAndParametersMap(String filterPattern, Map<String, Object> parametersMap);
}
