package blog_App.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import blog_App.entity.Category;
//import blog_App.entity.Comment;
import blog_App.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class PostDto {
	private int postId;
    @NotEmpty
    @Size(min=4,message="Title must be min of 4 character !!")
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String imageName;
    private Date date;
    private CategoryDto category;
    private UserDto user;
    private List<CommentDto> comments=new ArrayList<CommentDto>();
}
