package blog_App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog_App.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
