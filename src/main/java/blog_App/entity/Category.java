package blog_App.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
