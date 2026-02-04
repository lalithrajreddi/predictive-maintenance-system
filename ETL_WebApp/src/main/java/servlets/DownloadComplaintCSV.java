package servlets;

import utils.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.time.LocalDate;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/downloadComplaintCSV")
public class DownloadComplaintCSV extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get today's date in yyyy-MM-dd format
        String today = LocalDate.now().toString();
        String fileName = "complaint_report_" + today + ".csv";

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement("""
                SELECT c.complaint_id, a.asset_number, c.complaint_date, 
                       c.category, c.description, c.department, c.status, c.feedback
                FROM complaints c
                JOIN assets a ON c.asset_id = a.asset_id
                ORDER BY c.complaint_id DESC
            """);
            ResultSet rs = ps.executeQuery();
            PrintWriter out = response.getWriter();
        ) {
            // Header row
            out.println("Complaint ID,Asset Number,Date,Category,Description,Department,Status,Feedback");

            // Data rows
            while (rs.next()) {
                out.printf("%d,%s,%s,%s,\"%s\",%s,%s,%s%n",
                        rs.getInt("complaint_id"),
                        rs.getString("asset_number"),
                        rs.getDate("complaint_date"),
                        rs.getString("category"),
                        rs.getString("description").replace("\"", "'"),
                        rs.getString("department"),
                        rs.getString("status"),
                        rs.getString("feedback") != null ? rs.getString("feedback").replace("\"", "'") : ""
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
