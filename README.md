# 스프링 데이터 JPA 
<br/>

### 데이터베이스
도커를 이용한 Postgres 사용.
<br/>

#### ∙ 현재 떠있는 컨테이너 확인  
$ docker ps
<br/>

#### ∙ 컨테이너는 존재하지만 동작하지 않고 있는(멈춰있는) 컨테이너 확인 
$ docker ps -a 
<br/>

#### ∙ 컨테이너 실행
$ docker start [컨테이너 이름]
<br/>

#### ∙ 컨테이너 접근 
$ docker exec -i -t [컨테이너 이름] bash
<br/>

#### ∙ postgres 접속 
$ su - postgres
<br/>

#### ∙ 내가 만든 'springdata' 데이터베이스 접근 
$ psql --username rey --dbname springdata
<br/>

#### ∙ 접속한 DB Instance의 테이블 목록 확인 
$ \dt
<br/>

#### ∙ 내가 만든 'account' 테이블 조회
$ select * from account;
<br/>

<pre>
현재 이 예제에서는..
postgres 사용자 => rey
데이터베이스 => springdata
account 테이블에 있는 사용자 데이터 => sombrero104 / pass
</pre>
<br/>

JPA의 EntityManager가 내부적으로는 하이버네이트를 사용한다. <br/>
때문에 JPA 기반으로 코딩할 수도 있고, 하이버네이트 기반으로 코딩할 수도 있다. <br/>

<br/><br/><br/><br/>


