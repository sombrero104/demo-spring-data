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



<br/><br/><br/><br/>


