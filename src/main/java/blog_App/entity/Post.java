package blog_App.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;
    private String title;
    private String content;
    private String imageName;
    private Date date;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private List<Comment> comments=new ArrayList<Comment>();


}
