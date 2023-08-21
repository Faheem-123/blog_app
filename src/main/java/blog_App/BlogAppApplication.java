package blog_App;

import java.util.List;

import blog_App.entity.Role;
import blog_App.repository.RoleRepository;
import blog_App.service.FilesStorageService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import blog_App.utils.AppConstants;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{
	@Autowired
	private RoleRepository roleRepository;
	@Resource
	FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
		try {
			Role adminRole= new Role();
			adminRole.setId(AppConstants.ADMIN_ROLE);
			adminRole.setName("ROLE_ADMIN");
			Role normalRole= new Role();
			normalRole.setId(AppConstants.NORMAL_ROLE);
			normalRole.setName("ROLE_NORMAL");
			List<Role> roles = List.of(adminRole,normalRole);
			roleRepository.saveAll(roles);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
