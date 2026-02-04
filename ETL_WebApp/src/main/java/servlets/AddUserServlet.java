package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

import utils.DBUtil;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"EIC".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String fullName = request.getParameter("fullName");
        String department = request.getParameter("department");

        try (Connection con = DBUtil.getConnection()) {
            String sql = """
                INSERT INTO users (username, password, role, full_name, department)
                VALUES (?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.setString(4, fullName);
            ps.setString(5, department);
            ps.executeUpdate();

            response.sendRedirect("eic_dashboard.jsp?success=user");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("eic_dashboard.jsp?error=db");
        }
    }
}
