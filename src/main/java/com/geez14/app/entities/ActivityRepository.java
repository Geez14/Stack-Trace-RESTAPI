package com.geez14.app.entities;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ActivityRepository extends CrudRepository<Activity, Long>, PagingAndSortingRepository<Activity, Long> {
    @Query("SELECT * FROM ACTIVITY WHERE ID=:requestId AND OWNER=:owner")
    Optional<Activity> findByIdAndOwner(Long requestId, String owner);

    Page<Activity> findAllByOwnerIgnoreCase(String owner, PageRequest pageRequest);

    boolean existsByIdAndOwner(Long requestId, String owner);

    @Modifying
    @Query("DELETE ACTIVITY WHERE OWNER=:owner")
    void deleteAllByOwner(String owner);
}
