package blog_App.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String content;
    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;
    @ManyToOne
    private User user;
}
