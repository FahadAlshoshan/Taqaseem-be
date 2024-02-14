package com.app.taqaseem.repository;

import com.app.taqaseem.model.GroupMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMember, Long> {
    List<GroupMember> findByGroupId(Long groupId);
    List<GroupMember> findByUserId(Long userId);
}
