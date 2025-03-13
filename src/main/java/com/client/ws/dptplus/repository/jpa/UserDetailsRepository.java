package com.client.ws.dptplus.repository.jpa;

import com.client.ws.dptplus.model.jpa.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserCredentials,Long> {

    Optional<UserCredentials> findByUsername(String username);

}
