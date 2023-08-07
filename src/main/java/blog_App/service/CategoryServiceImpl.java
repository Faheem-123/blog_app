package blog_App.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog_App.entity.Category;
import blog_App.entity.User;
import blog_App.exception.ResourceNotFoundException;
import blog_App.payloads.CategoryDto;
import blog_App.payloads.UserDto;
import blog_App.repository.CategoryRepository;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category savedUser = this.categoryRepository.save(category);
		return this.categoryToCategoryDto(savedUser);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer CategoryId) {
		Category category = this.categoryRepository.findById(CategoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", CategoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcategory = this.categoryRepository.save(category);
		return this.categoryToCategoryDto(updatedcategory);
	}

	@Override
	public CategoryDto getCategoryById(Integer CategoryId) {
		Category category = this.categoryRepository.findById(CategoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", CategoryId));
		return this.categoryToCategoryDto(category);
	}
	@Override
	public List<CategoryDto> getAllCategorys() {
		List<Category> categorys = this.categoryRepository.findAll();
		List<CategoryDto> categoryDtos = categorys.stream().map(category -> this.categoryToCategoryDto(category))
				.collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public void deleteCategory(Integer CategoryId) {
		Category category = this.categoryRepository.findById(CategoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","Id",CategoryId));
				this.categoryRepository.delete(category);
	}

	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}

	public CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;

	}

}
