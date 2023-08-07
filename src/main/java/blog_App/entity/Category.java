package blog_App.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
	@Column(name="title")
    private String categoryTitle;
	@Column(name="description")
    private String categoryDescription;
	@OneToMany(mappedBy="category", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private List<Post> posts=new ArrayList<Post>();

}
