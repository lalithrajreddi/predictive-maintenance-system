package servlets;

import utils.DBUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT user_id, role, full_name FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                String fullName = rs.getString("full_name");

                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("role", role);
                session.setAttribute("fullName", fullName);

                // Redirect to role-based dashboard
                response.sendRedirect(role.toLowerCase() + "_dashboard.jsp");

            } else {
                // Invalid login
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
