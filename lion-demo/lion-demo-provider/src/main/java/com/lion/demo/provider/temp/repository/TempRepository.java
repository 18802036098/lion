package com.lion.demo.provider.temp.repository;

import com.lion.demo.provider.temp.entity.TempJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TempRepository
 * TODO
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2019/04/15
 * Copyright 2019 Yanzheng. All rights reserved.
 */
@Transactional  // 在做update、delete时必须声明事物，否则会抛异常
public interface TempRepository extends JpaRepository<TempJpa, String> {

    // 占位符风格
    @Query(value = "select * from temp_jpa where status = ?1", nativeQuery = true)
    List<TempJpa> selectByCustomSql(Boolean status);

    // SPEL表达式风格
    //@Query(value = "select * from temp_jpa where status = :status", nativeQuery = true)
    //List<TempJpa> selectByCustomSql(@Param("status") Boolean status);

}
