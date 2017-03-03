package entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by maomao on 15/7/10.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
}
