package blog_App.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {
	 private int categoryId;
	 @NotEmpty
	 @Size(min=4,message="Title must be min of 4 character !!")
	 private String categoryTitle;
	 @NotEmpty
	 @Size(min=4,message="Description must be min of 4 character !!")
	 private String categoryDescription;
}
