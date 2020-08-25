package com.ihrm.common.service;

import org.springframework.cglib.core.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * com.ihrm.common.service
 *
 * @author zhaopj
 * 2020/7/28
 */
public class BaseService<T> {
    protected Specification<T> getSpec(String companyId) {


        return (Specification<T>)
        (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
    }
}
