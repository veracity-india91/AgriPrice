package AgriPrice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import AgriPrice.entity.User;

public interface UserRepository extends JpaRepository<User , Long> {
	User findByEmail(String email);

}
