package servlets;

import utils.DBUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/approvePMAction")
public class ApprovePMActionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String pmIdStr = request.getParameter("pmId");

        if (userId != null && pmIdStr != null) {
            try (Connection con = DBUtil.getConnection()) {
                String sql = "UPDATE pm_records SET approved_by = ? WHERE pm_id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, Integer.parseInt(pmIdStr));
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("approvePM");
    }
}
