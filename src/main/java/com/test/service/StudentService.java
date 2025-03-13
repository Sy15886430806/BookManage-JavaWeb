package com.test.service;

import com.test.entity.Student;
import com.test.mapper.StudentMapper;
import com.test.util.InputUtil;
import com.test.util.SqlUtil;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class StudentService {

    public static void addStudent() {
        String name = InputUtil.nextLine("请输入学生姓名：");
        String gender = InputUtil.nextLine("请输入学生性别：(男/女)", "男", "女");
        int age = InputUtil.nextInt("请输入学生年龄：");

        Student student = new Student(0, name, gender, age);
        SqlUtil.doSqlWork(StudentMapper.class, mapper -> {
            int count = mapper.insertStudent(student);
            if(count > 0) {
                System.out.println("用户信息加入成功：" + student);
                log.info("用户信息插入操作" + student);
            } else {
                System.out.println("用户信息加入失败,请重试！");
            }
        });
    }

    public static void ListStudent() {
        SqlUtil.doSqlWork(StudentMapper.class, mapper -> {
            List<Student> students = mapper.selectAllStudent();
            if(students.isEmpty()) {
                System.err.println("没有学生");
            }else {
                String format = "%-5s %-6s %-5s %-5s\n";
                System.out.printf(format, "学号", "姓名", "性别", "年龄");
                students.forEach(student -> System.out.printf(format, student.getSid(),student.getName(), student.getGender(), student.getAge()));
            }
        });
    }
}
