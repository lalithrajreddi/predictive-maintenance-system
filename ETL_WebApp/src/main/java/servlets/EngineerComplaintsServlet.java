package servlets;

import model.Complaint;
import utils.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/engineerComplaints")
public class EngineerComplaintsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Complaint> list = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            String sql = """
                SELECT c.complaint_id, a.asset_number, a.item_text,
                       c.complaint_date, c.department, c.category,
                       c.description, c.status, c.feedback
                FROM complaints c
                JOIN assets a ON c.asset_id = a.asset_id
                WHERE c.status = 'Open'
                ORDER BY c.complaint_date DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complaint c = new Complaint();
                c.setComplaintId(rs.getInt("complaint_id"));
                c.setAssetName(rs.getString("asset_number") + " - " + rs.getString("item_text"));
                c.setComplaintDate(rs.getDate("complaint_date"));
                c.setDepartment(rs.getString("department"));
                c.setCategory(rs.getString("category"));
                c.setDescription(rs.getString("description"));
                c.setStatus(rs.getString("status"));
                c.setFeedback(rs.getString("feedback"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("complaintList", list);
        request.getRequestDispatcher("engineer_close.jsp").forward(request, response);
    }
}
