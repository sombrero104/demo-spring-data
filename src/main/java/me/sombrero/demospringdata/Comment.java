package me.sombrero.demospringdata;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String comment;

    private int likeCount;

    @Temporal(TemporalType.DATE)
    private Date created = new Date();

    @ManyToOne
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
