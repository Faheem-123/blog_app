package blog_App.controller;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import blog_App.payloads.ApiResponse;
import blog_App.payloads.CategoryDto;
import blog_App.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	//Create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createUserDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	//Update category using category id
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer categoryId){
		CategoryDto updatedUserDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedUserDto);
	}
	//Delete category using id
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
	this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully","200",true),HttpStatus.OK);
	}
	//Get all categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategorys(){
		return ResponseEntity.ok(this.categoryService.getAllCategorys());
	}
	//Get category by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer categoryId){
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
	}

}
