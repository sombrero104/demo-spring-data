package me.sombrero.demospringdata;

/**
 * [ 프로그래밍으로 빈 등록하기 ]
 * SombreroRegistrar에 이 클래스를 빈으로 등록하는 프로그래밍이 되어있다.
 */
public class Sombrero {

    private String name = "sombrero104 :D";

    private String desc = "hi :D";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
