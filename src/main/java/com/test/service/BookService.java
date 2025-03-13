package com.test.service;

import com.test.entity.Book;
import com.test.entity.Student;
import com.test.mapper.BookMapper;
import com.test.mapper.StudentMapper;
import com.test.util.InputUtil;
import com.test.util.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Log
public class BookService {

    public static void addBook() {
        String title = InputUtil.nextLine("请输入书籍名称：");
        String description = InputUtil.nextLine("请输入书籍描述：");
        Book book = new Book(0, title, description);

        SqlUtil.doSqlWork(BookMapper.class, mapper -> {
            int count = mapper.insertBook(book);
            if(count > 0) {
                System.out.println("图书添加成功" + book);
                log.info("书籍信息插入操作" + book);
            } else {
                System.out.println("图书添加失败,请重试！");
            }
        });
    }

    public static void ListBook() {
        SqlUtil.doSqlWork(BookMapper.class, mapper -> {
            List<Book> books = mapper.selectAllBook();
            if(books.isEmpty()) {
                System.err.println("没有图书");
            }else {
                String format = "%-5s %-10s %-20s\n";
                System.out.printf(format, "编号","书籍名", "简介");
                books.forEach(book -> System.out.printf(format, book.getBid(),book.getTitle(),book.getDescription()));
            }
        });
    }
}
