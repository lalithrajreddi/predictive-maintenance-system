package servlets;

import model.PMRecord;
import utils.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/billingReview")
public class BillingReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PMRecord> pmList = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            String sql = """
                SELECT p.pm_id, a.asset_number, p.pm_date, p.checklist, p.remarks, 
                       u.full_name, p.eic_billed,
                       p.department, p.category,
                       p.verified_on, p.approved_on
                FROM pm_records p
                JOIN assets a ON p.asset_id = a.asset_id
                JOIN users u ON p.done_by = u.user_id
                WHERE p.eic_billed = FALSE
                ORDER BY p.pm_id DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PMRecord r = new PMRecord();
                r.setPmId(rs.getInt("pm_id"));
                r.setAssetNumber(rs.getString("asset_number"));
                r.setPmDate(rs.getDate("pm_date"));
                r.setDepartment(rs.getString("department"));
                r.setCategory(rs.getString("category"));
                r.setChecklist(rs.getString("checklist"));
                r.setRemarks(rs.getString("remarks"));
                r.setDoneBy(rs.getString("full_name"));
                r.setBilled(rs.getBoolean("eic_billed"));
                r.setVerifiedOn(rs.getDate("verified_on"));
                r.setApprovedOn(rs.getDate("approved_on"));
                pmList.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("pmList", pmList);
        request.getRequestDispatcher("billing_review.jsp").forward(request, response);
    }
}
