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
- CASCADE: 엔티티의 상태 변화를 전파 시키는 옵션.
<br/>

### 잠깐, 엔티티의 상태란?
- Transient: JPA가 모르는 상태.
- Persistent: JPA가 관리중인 상태. <br/>
  Persistent 상태가 되면 하이버네이트가 1차 캐시, Dirty Checking, Write Behind, 등등과 같은 여러가지 일을 해준다.<br/>
  EntityManager, Session을 Persistent Context라고 부르는데<br/>
  save가 되면 이 Persistent Context에 저장이 된다. (즉, 캐시가 된다.) <br/>
  때문에 트랜잭션이 끝나기 전에 조회하는 메소드를 호출하면 캐시에 있는 것을 꺼내준다. 
- Detached: JPA가 더이상 관리하지 않는 상태.
- Removed: JPA가 관리하긴 하지만 삭제하기로 한 상태.
<br/>

<img src="./images/entity_states.png" width="75%" /><br/>
<br/>

<br/><br/><br/><br/>


