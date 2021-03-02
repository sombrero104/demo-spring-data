package me.sombrero.demospringdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 엔티티의 관계는 항상 2개의 엔티티의 관계이다.
 */
@Entity
public class Study {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /**
     * [ 관계의 주인이 Study인 경우 ] => @ManyToOne : 기본값은 외래키 생성.
     * 실행하면 Study 테이블에는 Account의 외래키인 owner_id가 만들어진다.
     * Study 엔티티에서 Account 엔티티와의 관계를 설정했으므로
     * Study가 이 관계에 대한 주인이며, Study가 이 관계를 관리한다.
     */ 
    @ManyToOne // 한 사람이 여러개의 스터디를 만들 수 있다.
    private Account owner; // 스터디를 만든 사람.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

}
