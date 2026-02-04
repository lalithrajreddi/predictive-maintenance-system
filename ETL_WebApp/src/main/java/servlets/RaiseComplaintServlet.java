package servlets;

import utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/raiseComplaint")
public class RaiseComplaintServlet extends HttpServlet {

    // ✅ If someone types or links to /raiseComplaint
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("complaintForm"); // Show form if visited by GET
    }

    // ✅ Handles form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int assetId = Integer.parseInt(request.getParameter("assetId"));
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String department = request.getParameter("department");

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            String sql = """
                INSERT INTO complaints (user_id, asset_id, complaint_date, category, description, department, status)
                VALUES (?, ?, CURDATE(), ?, ?, ?, 'Open')
            """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, assetId);
            ps.setString(3, category);
            ps.setString(4, description);
            ps.setString(5, department);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String role = (String) request.getSession().getAttribute("role");
        response.sendRedirect(role != null ? role.toLowerCase() + "_dashboard.jsp" : "login.jsp");
    }
}
