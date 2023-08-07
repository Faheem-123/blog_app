package blog_App.service;

import java.util.List;

import blog_App.payloads.CategoryDto;
import blog_App.payloads.UserDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto, Integer CategoryId);
	CategoryDto getCategoryById(Integer CategoryId);
	List<CategoryDto> getAllCategorys();
	void deleteCategory(Integer CategoryId);

}
