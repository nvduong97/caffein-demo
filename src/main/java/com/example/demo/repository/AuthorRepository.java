package com.example.demo.repository;

import com.example.demo.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {
    @Query("SELECT a FROM Author a " +
            "WHERE (a.name like %:searchValue%  " +
            "OR a.address like %:searchValue% " +
            "OR a.email LIKE %:searchValue%)")
    Page<Author> findAllAuthor(@Param("searchValue") String searchValue, Pageable pageable);

//    @Query(value = "select  a.* from " +
//            " author a " +
//            "where (:id is null or a.id = :authorId) " +
//            " and (:address is null  or a.address = :address) " +
//            " and (:email is null  or a.email = :email) " +
//            " and (:name is null  or a.name = :name) ", nativeQuery = true)
//    Optional<List<Author>> searchAuthor(@Param("authorId") Integer authorId,
//                                        @Param("address") Integer address,
//                                        @Param("email") Integer email,
//                                        @Param("name") Integer name);
}
