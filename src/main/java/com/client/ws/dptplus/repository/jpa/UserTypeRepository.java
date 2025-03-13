package com.client.ws.dptplus.repository.jpa;

import com.client.ws.dptplus.model.jpa.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType,Long> {
}
