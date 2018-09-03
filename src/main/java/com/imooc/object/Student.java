package com.imooc.object;



public class Student {


    private Integer id;
    private String name;
    private Integer  result;

    public Student() {
    }

    public Student(Integer id, String name, Integer result) {
        this.id = id;
        this.name = name;
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", result=" + result +
                '}';
    }
}
