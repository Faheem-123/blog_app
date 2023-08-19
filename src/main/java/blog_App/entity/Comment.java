package blog_App.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
