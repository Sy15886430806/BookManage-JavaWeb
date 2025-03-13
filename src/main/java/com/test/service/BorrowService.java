package com.test.service;

import com.test.entity.Book;
import com.test.entity.Borrow;
import com.test.entity.Student;
import com.test.mapper.BookMapper;
import com.test.mapper.BorrowMapper;
import com.test.mapper.StudentMapper;
import com.test.util.InputUtil;
import com.test.util.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Log
public class BorrowService {
    public static void addBorrow() {
        StudentMapper studentMapper= SqlUtil.takeMapper(StudentMapper.class);
        BookMapper bookMapper= SqlUtil.takeMapper(BookMapper.class);

        Student student = InputUtil.nextEntity("请选择借阅人：", studentMapper::selectStudentBySid);
        Book book = InputUtil.nextEntity("请选择借阅书籍：", bookMapper::selectBookByBid);

        SqlUtil.doSqlWork(BorrowMapper.class, mapper -> {
            int count = mapper.insertBorrow(student.getSid(), book.getBid());
            if(count > 0) {
                System.out.println("借阅成功：" + student.getName() + "借阅了" + book.getTitle());
                log.info("借阅信息插入操作" + student.getName() + "借阅了" + book.getTitle());
            } else {
                System.out.println("借阅失败,请重试");
            }
        });
    }

    public static void ListBorrow() {
        SqlUtil.doSqlWork(BorrowMapper.class, mapper -> {
            List<Borrow> borrows = mapper.selectAllBorrow();
            if(borrows.isEmpty()) {
                System.err.println("没有借阅信息");
            }else {
                String format = "%-5s %-5s %-5s %-20s\n";
                System.out.printf(format, "学号", "姓名", "书籍编号","书籍名称");
                borrows.forEach(borrow -> System.out.printf(format,
                        borrow.getStudent().getSid(), borrow.getStudent().getName(), borrow.getBook().getBid(), borrow.getBook().getTitle()));
           }
        });
    }
}
