package blog_App.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserCategoryResponse {
    private String title;
    private String username;
    private String categoryTitle;
}
