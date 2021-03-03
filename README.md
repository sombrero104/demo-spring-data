# 스프링 데이터 JPA 
<br/>

# 예제 프로젝트 준비
<br/>

### 데이터베이스
이 예제에서는 도커를 이용한 Postgres 사용.<br/>
<br/>

#### ∙ 현재 떠있는 컨테이너 확인  
<pre>
$ docker ps
</pre>

#### ∙ 컨테이너는 존재하지만 동작하지 않고 있는(멈춰있는) 컨테이너 확인 
<pre>
$ docker ps -a 
</pre>

#### ∙ 컨테이너 실행
<pre>
$ docker start [컨테이너 이름]
</pre>

#### ∙ 컨테이너 접근 
<pre>
$ docker exec -i -t [컨테이너 이름] bash
</pre>

#### ∙ postgres 접속 
<pre>
$ su - postgres
</pre>

#### ∙ 내가 만든 'springdata' 데이터베이스 접근 
<pre>
$ psql --username rey --dbname springdata
</pre>

#### ∙ 접속한 DB Instance의 테이블 목록 확인 
<pre>
$ \dt
</pre>

#### ∙ 내가 만든 'account' 테이블 조회
<pre>
$ select * from account;
</pre>

#### ∙ postgres 유저 확인
<pre>
$ \du
</pre>

#### ∙ 기타 데이터베이스 설정 정보 
<pre>
현재 이 예제에서는..
postgres 사용자 => rey
데이터베이스 => springdata
account 테이블에 있는 사용자 데이터 => sombrero104 / pass
</pre>
<br/><br/><br/><br/>

# JPA와 하이버네이트 
JPA의 EntityManager가 내부적으로는 하이버네이트를 사용한다. <br/>
때문에 JPA 기반으로 코딩할 수도 있고, 하이버네이트 기반으로 코딩할 수도 있다. <br/>
하지만 대부분의 경우에는 JPA 또는 하이버네이트의 API를 직접적으로 사용하는 경우는 없다. <br/>
<br/>

# JPA의 핵심인 EntityManager 
HibernateJpaAutoConfiguration에서 HibernateJpaConfiguration 설정파일을 가져온다. <br/>
HibernateJpaConfiguration 설정파일의 상위 클래스인 JpaBaseConfiguration을 보면, <br/>
JPA의 핵심인 EntityManagerFactoryBuilder가 entityManagerFactoryBuilder()에서 빈으로 등록이 되는 것을 확인할 수 있다. <br/>
이외에도 entityManagerFactory()에서 EntityManagerFactory를 빈으로 등록하는 것을 확인할 수 있는데 <br/>
이것 때문에 우리는 EntityManager를 주입 받아서 사용할 수 있는 것이다. <br/>
이외에도 TransactionManager도 이곳에서 빈으로 등록하는 것을 확인할 수 있다. <br/>
<br/><br/><br/><br/>

# CASCADE
- CASCADE: 엔티티의 상태 변화를 전파 시키는 옵션.<br/>
도메인의 관계가 Parent와 Child 관계에 있을 때 사용할 수 있다. <br/>
예를 들어, Post와 Comment는 Post가 삭제되면 Comment도 삭제된다. <br/>
<br/>

### 잠깐, 엔티티의 상태란?
- Transient: JPA가 모르는 상태.
- Persistent: JPA가 관리중인 상태. (엔티티 객체의 변경사항을 JPA가 모니터링하고 있다는 의미.) <br/>
  Persistent 상태가 되면 하이버네이트가 1차 캐시, Dirty Checking, Write Behind, 등등과 같은 여러가지 일을 해준다.<br/>
  - 1차 캐시: EntityManager, Session을 Persistent Context라고 부르는데<br/>
             save가 되면 이 Persistent Context에 저장이 된다. (즉, 캐시가 된다.) <br/>
             때문에 save 이후, 트랜잭션이 끝나기 전에 조회하는 메소드를 호출하면 DB에서 가져오지 않고 캐시에 있는 것을 꺼내준다. <br/> 
             (즉, select 쿼리가 발생하지 않는다.)
  - Dirty Checking: 엔티티 객체의 변경사항을 계속 감지한다는 의미. <br/>
  - Write Behind: 엔티티 객체의 상태 변화를 DB에 최대한 늦게 (가장 필요한 시점에) 적용한다는 의미. <br/>
- Detached: JPA가 더이상 관리하지 않는 상태.<br/>
JPA에 의해 관리되었던 객체가 트랜잭션이 끝나서 Session 밖으로 나온 후 객체가 밖에서 사용이 될 때는 Detached 상태가 된다. <br/>
(예를 들어, Service단에서 Repository를 호출한 후 트랜잭션이 끝나고 객체를 반환해서 다시 Service단에서 객체를 받은 경우.)
- Removed: JPA가 관리하긴 하지만 삭제하기로 한 상태. 실제 커밋이 일어날 때 삭제가 된다. 
<br/>

<img src="./images/entity_states.png" width="75%" /><br/>
<br/><br/><br/><br/>

# Fetch
연관관계의 엔티티를 지금 가져올 것인지, 나중에 가져올 것인지..
지금(**_Eager_**)? 나중에(**_Lazy_**)?
- @OneToMany의 기본값은 Lazy
- @ManyToOne의 기본값은 Eager
<br/>

## Post 조회
### 1. Post에서 comments가 Lazy인 경우. 
@OneToMany이기 때문에 기본적으로 Lazy모드이다. 
<pre>
select
    post0_.id as id1_2_0_,
    post0_.title as title2_2_0_ 
from
    post post0_ 
where
    post0_.id=?
</pre><br/> 

### 2. Post에서 comments가 Eager인 경우. 
@OneToMany에 'fetch = FetchType.EAGER'로 바꿔주면<br/>
아래와 같이 post만 가져오는데도 comment도 같이 select하는 것을 확인할 수 있다. <br/>
<pre>
Post post = session.get(Post.class, 1l);
System.out.println("##### post.getTitle(): " + post.getTitle());
</pre>
<pre>
select
    post0_.id as id1_2_0_,
    post0_.title as title2_2_0_,
    comments1_.post_id as post_id3_1_1_,
    comments1_.id as id1_1_1_,
    comments1_.id as id1_1_2_,
    comments1_.comment as comment2_1_2_,
    comments1_.post_id as post_id3_1_2_ 
from
    post post0_ 
left outer join
    comment comments1_ 
        on post0_.id=comments1_.post_id 
where
    post0_.id=?
</pre><br/> 

## comment 조회 
### comment를 가져오는 경우
@ManyToOne은 기본적으로 Eager모드이다. <br/> 
때문에 아래와 같이 comment만 가져와도 post도 같이 가져오는 것을 확인할 수 있다. 
<pre>
Comment comment = session.get(Comment.class, 2l);
System.out.println("##### comment.getComment(): " + comment.getComment());
System.out.println("##### comment.getPost().getTitle(): " + comment.getPost().getTitle());
</pre>
<pre>
select
    comment0_.id as id1_1_0_,
    comment0_.comment as comment2_1_0_,
    comment0_.post_id as post_id3_1_0_,
    post1_.id as id1_2_1_,
    post1_.title as title2_2_1_ 
from
    comment comment0_ 
left outer join
    post post1_ 
        on comment0_.post_id=post1_.id 
where
    comment0_.id=?
</pre>
<br/><br/><br/><br/>