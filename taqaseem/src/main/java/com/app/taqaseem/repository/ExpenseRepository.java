package com.app.taqaseem.repository;

import com.app.taqaseem.model.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findByPaidByUserId(Long paidByUserId);
    List<Expense> findByExpenseThreadId(Long expenseThreadId);
    List<Expense> findByGroupId(Long groupId);
}

