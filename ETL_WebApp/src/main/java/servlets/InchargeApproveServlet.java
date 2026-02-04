package servlets;

import utils.DBUtil;
import model.PMRecord;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/approvePM")
public class InchargeApproveServlet extends HttpServlet {

    // Show verified PMs (not yet approved)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PMRecord> list = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            String sql = """
                SELECT p.pm_id, a.asset_number, p.pm_date, p.checklist, p.remarks,
                       u.full_name AS done_by, p.eic_billed,
                       p.department, p.category,
                       p.verified_on, p.approved_on
                FROM pm_records p
                JOIN assets a ON p.asset_id = a.asset_id
                JOIN users u ON p.done_by = u.user_id
                WHERE p.verified_by IS NOT NULL AND p.approved_by IS NULL
                ORDER BY p.pm_id DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PMRecord r = new PMRecord();
                r.setPmId(rs.getInt("pm_id"));
                r.setAssetNumber(rs.getString("asset_number"));
                r.setPmDate(rs.getDate("pm_date"));
                r.setChecklist(rs.getString("checklist"));
                r.setRemarks(rs.getString("remarks"));
                r.setDoneBy(rs.getString("done_by"));
                r.setBilled(rs.getBoolean("eic_billed"));
                r.setDepartment(rs.getString("department"));
                r.setCategory(rs.getString("category"));
                r.setVerifiedOn(rs.getDate("verified_on"));
                r.setApprovedOn(rs.getDate("approved_on"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("pmList", list);
        request.getRequestDispatcher("approve_pm.jsp").forward(request, response);
    }

    // Approve the selected PM
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pmId = Integer.parseInt(request.getParameter("pmId"));
        int userId = (int) request.getSession().getAttribute("userId");

        try (Connection con = DBUtil.getConnection()) {
            String sql = "UPDATE pm_records SET approved_by = ?, approved_on = CURDATE() WHERE pm_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, pmId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect back to the page
        response.sendRedirect("approvePM");
    }
}
