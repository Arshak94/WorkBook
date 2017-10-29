package com.fipsoftware.workbook.DBConnection;

import com.fipsoftware.workbook.model.WorkBook;
import com.fipsoftware.workbook.model.WorkHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DBConnectImpl implements DBConnect{
    public void save(WorkBook workBook){
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "root", "rootroot");
            String workBookQuery = "INSERT INTO workbook (id,social_security, first_name,last_name,date_of_birth,photo) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(workBookQuery);
            pstmt.setInt(1,workBook.getId());
            pstmt.setString(2,workBook.getSocialSecurity());
            pstmt.setString(3,workBook.getFirstName());
            pstmt.setString(4,workBook.getLastName());
            pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setBytes(6,workBook.getPhoto());
            pstmt.execute();
            for (WorkHistory workHistory :
                    workBook.getWorkHistory()) {
                String workHistoryQuery = "INSERT INTO work_history (id,work_book_id,company_name, job_position,salary,work_from,work_until) VALUES (?,?,?,?,?,?,?)";
                pstmt=conn.prepareStatement(workHistoryQuery);
                pstmt.setInt(1,workHistory.getId());
                pstmt.setInt(2, workBook.getId());
                pstmt.setString(3,workHistory.getCompanyName());
                pstmt.setString(4,workHistory.getPosition());
                pstmt.setBigDecimal(5,workHistory.getSalary());
                pstmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
                pstmt.setDate(7, new java.sql.Date(System.currentTimeMillis()));
                pstmt.execute();
            }


            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<WorkBook> findAll(){
        List<WorkBook> workBooks = new ArrayList<>();
        List<WorkHistory> workHistories = new ArrayList<>();
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "root", "rootroot");

            String sql = "SELECT id, work_book_id, company_name, job_position,salary,work_from,work_until FROM work_history";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                WorkHistory workHistory = new WorkHistory();
                workHistory.setId(resultSet.getInt("id"));
                workHistory.setWorkBookId(resultSet.getInt("work_book_id"));
                workHistory.setCompanyName(resultSet.getString("company_name"));
                workHistory.setPosition(resultSet.getString("job_position"));
                workHistory.setSalary(resultSet.getBigDecimal("salary"));
                workHistory.setWorkFrom(resultSet.getDate("work_from"));
                workHistory.setWorkUntil(resultSet.getDate("work_until"));
                workHistories.add(workHistory);
            }
            String getsql = "SELECT id, social_security, first_name, last_name, date_of_birth, photo FROM workbook";
            PreparedStatement pstmt = conn.prepareStatement(getsql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                WorkBook workBook = new WorkBook();
                workBook.setId(rs.getInt("id"));
                workBook.setSocialSecurity(rs.getString("social_security"));
                workBook.setFirstName(rs.getString("first_name"));
                workBook.setLastName(rs.getString("last_name"));
                workBook.setDateOfBirth(rs.getDate("date_of_birth"));
                workBook.setPhoto(rs.getBytes("photo"));
                workBooks.add(workBook);
            }

            pstmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        for (WorkBook workbook :
                workBooks) {
            List<WorkHistory> workHistoryList = new ArrayList<>();
            for (WorkHistory workhistory :
                    workHistories) {
                if (workhistory.getWorkBookId() == workbook.getId()){
                    workHistoryList.add(workhistory);
                }
            }
            workbook.setWorkHistory(workHistoryList);

        }
        return workBooks;
    }
}
