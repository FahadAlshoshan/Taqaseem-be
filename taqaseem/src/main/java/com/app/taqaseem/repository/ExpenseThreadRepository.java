package com.app.taqaseem.repository;

import com.app.taqaseem.model.ExpenseThread;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseThreadRepository extends CrudRepository<ExpenseThread, Long> {
    Iterable<ExpenseThread> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}
