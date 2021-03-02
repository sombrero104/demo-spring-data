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
     * Study 테이블에는 Account의 외래키인 owner_id가 만들어진다.
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
