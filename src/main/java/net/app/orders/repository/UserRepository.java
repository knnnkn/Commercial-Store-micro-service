package net.app.orders.repository;

import net.app.orders.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);
    User findByFirstName(String firstName);
}
