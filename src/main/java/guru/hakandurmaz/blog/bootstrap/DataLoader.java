package guru.hakandurmaz.blog.bootstrap;

import guru.hakandurmaz.blog.entity.Role;
import guru.hakandurmaz.blog.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final RoleRepository roleRepository;

  public DataLoader(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public void run(String... args) {
    if (roleRepository.findAll().isEmpty()) {
      Role adminRole = new Role();
      adminRole.setName("ROLE_ADMIN");
      roleRepository.save(adminRole);
      Role userRole = new Role();
      userRole.setName("ROLE_USER");
      roleRepository.save(userRole);
    }
  }
}
