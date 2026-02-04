package servlets;

import utils.DBUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/markBilled")
public class MarkBilledServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pmIdParam = request.getParameter("pmId");

        try (Connection con = DBUtil.getConnection()) {
            String sql = "UPDATE pm_records SET eic_billed = TRUE WHERE pm_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(pmIdParam));
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("billingReview");
    }
}
