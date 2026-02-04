package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import utils.DBUtil;

@WebServlet("/downloadPMCSV")
public class DownloadPMCSV extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Get today's date in yyyy-MM-dd format
        String today = LocalDate.now().toString();
        String fileName = "pm_report_" + today + ".csv";

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try (Connection con = DBUtil.getConnection();
             PrintWriter out = response.getWriter()) {

            String sql = """
                SELECT p.pm_id, a.asset_number, p.pm_date, p.checklist, p.remarks,
                       u.full_name AS done_by, a.department, p.category,
                       p.verified_on, p.approved_on, p.eic_billed
                FROM pm_records p
                JOIN assets a ON p.asset_id = a.asset_id
                JOIN users u ON p.done_by = u.user_id
                ORDER BY p.pm_id DESC
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // CSV header
            out.println("PM ID,Asset Number,Date,Checklist,Remarks,Done By,Department,Category,Verified On,Approved On,Billed");

            while (rs.next()) {
                int pmId = rs.getInt("pm_id");
                String assetNumber = rs.getString("asset_number");
                String date = rs.getDate("pm_date").toString();
                String checklist = escapeCSV(rs.getString("checklist"));
                String remarks = escapeCSV(rs.getString("remarks"));
                String doneBy = rs.getString("done_by");
                String dept = rs.getString("department");
                String category = rs.getString("category");
                String verifiedOn = rs.getString("verified_on");
                String approvedOn = rs.getString("approved_on");
                boolean billed = rs.getBoolean("eic_billed");

                out.printf("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        pmId, assetNumber, date, checklist, remarks,
                        doneBy, dept, category,
                        (verifiedOn != null ? verifiedOn : ""),
                        (approvedOn != null ? approvedOn : ""),
                        billed ? "Yes" : "No"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String escapeCSV(String input) {
        if (input == null) return "";
        input = input.replace("\"", "'"); // escape internal quotes
        if (input.contains(",") || input.contains("\n")) {
            return "\"" + input + "\"";
        }
        return input;
    }
}
