package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import utils.DBUtil;

import java.io.IOException;
import java.sql.*;

@WebServlet("/closeComplaint")
public class CloseComplaintServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int complaintId = Integer.parseInt(request.getParameter("complaintId"));
        String feedback = request.getParameter("feedback");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try (Connection con = DBUtil.getConnection()) {
            String sql = "UPDATE complaints SET status = 'Closed', closed_by = ?, feedback = ? WHERE complaint_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, feedback);
            ps.setInt(3, complaintId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("engineerComplaints");
    }
}
