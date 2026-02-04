package servlets;

import model.Complaint;
import utils.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/complaintReport")
public class ComplaintReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Complaint> complaints = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            String sql = """
                SELECT c.complaint_id, a.asset_number, c.complaint_date, 
                       c.category, c.description, c.department, c.status, c.feedback
                FROM complaints c
                JOIN assets a ON c.asset_id = a.asset_id
                ORDER BY c.complaint_id DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complaint c = new Complaint();
                c.setComplaintId(rs.getInt("complaint_id"));
                c.setAssetName(rs.getString("asset_number"));
                c.setComplaintDate(rs.getDate("complaint_date"));
                c.setCategory(rs.getString("category"));
                c.setDescription(rs.getString("description"));
                c.setDepartment(rs.getString("department"));  // ✅ added
                c.setStatus(rs.getString("status"));
                c.setFeedback(rs.getString("feedback"));
                complaints.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("complaints", complaints);
        request.getRequestDispatcher("complaint_report.jsp").forward(request, response);
    }
}
