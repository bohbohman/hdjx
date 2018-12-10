package com.spring.bohbohman.repository;

import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @Auther: xueyb
 * @Date: 18/12/10 16:09
 * @Description:
 */
@NoRepositoryBean
public class BaseRepository {

    @PersistenceContext
    protected EntityManager entityManager;
}
