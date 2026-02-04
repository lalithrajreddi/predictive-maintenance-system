package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

import utils.DBUtil;

@WebServlet("/addAsset")
public class AddAssetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"EIC".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        String assetNumber = request.getParameter("assetNumber");
        String boqItem = request.getParameter("boqItem");
        String itemText = request.getParameter("itemText");
        String equipmentType = request.getParameter("equipmentType");
        String section = request.getParameter("section");
        String location = request.getParameter("location");
        String department = request.getParameter("department");

        try (Connection con = DBUtil.getConnection()) {
            String sql = """
                INSERT INTO assets (asset_number, boq_item, item_text, equipment_type, section, location, department)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, assetNumber);
            ps.setString(2, boqItem);
            ps.setString(3, itemText);
            ps.setString(4, equipmentType);
            ps.setString(5, section);
            ps.setString(6, location);
            ps.setString(7, department);
            ps.executeUpdate();

            response.sendRedirect("eic_dashboard.jsp?success=asset");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("eic_dashboard.jsp?error=db");
        }
    }
}
