package blog_App.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
