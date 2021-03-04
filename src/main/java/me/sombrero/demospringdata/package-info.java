/**
 * package-info.java 파일을 만들고
 * 이 파일의 패키지 위에 @NonNullApi를 붙이면
 * 이 패키지 안의 모든 메서드의 리턴타입, 파라미터들이 다 @NonNull이 붙는 것과 마찬가지이다.
 * 이걸 만든 후 예외일 경우에는 그곳에다가 @Nullable을 붙인다.
 * (하지만 잘 안쓰일 것 같다.)
 */
// @org.springframework.lang.NonNullApi
package me.sombrero.demospringdata;
