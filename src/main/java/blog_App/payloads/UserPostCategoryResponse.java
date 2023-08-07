package blog_App.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostCategoryResponse {
    private String username;
    private String title;
    private String categoryTitle;
}
