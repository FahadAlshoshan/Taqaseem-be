package com.app.taqaseem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.app.taqaseem.model.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> findByGroupName(String groupName);
}
