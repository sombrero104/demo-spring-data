package me.sombrero.demospringdata;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    /**
     * cascade = CascadeType.PERSIST
     *      => Post를 저장할 때 comment도 같이 저장하도록 전파.
     *      Post 엔티티가 Transient에서 Persistent 상태로 넘어갈 때
     *      Child에 해당하는 참조하고 있던 다른 객체들(연관관계에 있던 다른 객체들)도
     *      같이 Persistent 상태로 되어서 같이 저장이 된다.
     *
     * cascade = CascadeType.ALL
     *      => 보통은 모든 상태를 전파.
     */
    // @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST) // post를 저장할 때 comment도 같이 저장하도록 전파.
    // @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) // post를 저장,삭제할 때 comment도 같이 저장,삭제하도록 전파.
    // @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // post의 모든 상태를 comment도 상태가 바뀌도록 전파.
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    /**
     * [ 컨비니언트 메소드 ]
     */
    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.setPost(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
