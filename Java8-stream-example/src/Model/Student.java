package Model;

public class Student{
    int stuId;
    int stuAge;
    String stuName;
    public Student(int id, int age, String name){
        this.stuId = id;
        this.stuAge = age;
        this.stuName = name;
    }
    public int getStuId() {
        return stuId;
    }
    public int getStuAge() {
        return stuAge;
    }
    public String getStuName() {
        return stuName;
    }
}
