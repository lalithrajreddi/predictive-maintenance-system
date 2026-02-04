package servlets;

import utils.DBUtil;
import org.json.JSONObject;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/submitPM")
public class SubmitPMServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check for logged-in user
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 1. Read input values
            int assetId = Integer.parseInt(request.getParameter("assetId"));
            String department = request.getParameter("department");
            String category = request.getParameter("category");
            String remarks = request.getParameter("remarks");

            // 2. Build checklist from radio inputs
            String[] labels = {
                "Check AC Input Voltage",
                "Check Functioning of OS",
                "Check Antivirus",
                "Printer Cartridge Clean",
                "Test Print Taken",
                "Removal of Junk files",
                "Check Healthiness of Monitor"
            };

            Map<String, String> checklistMap = new HashMap<>();
            for (int i = 0; i < labels.length; i++) {
                String key = "check_" + i;
                String value = request.getParameter(key);
                checklistMap.put(labels[i], value != null ? value : "N/A");
            }

            String checklistJson = new JSONObject(checklistMap).toString();

            // 3. Insert into database
            try (Connection con = DBUtil.getConnection()) {
                String sql = """
                    INSERT INTO pm_records (asset_id, department, category, pm_date, done_by, checklist, remarks, eic_billed)
                    VALUES (?, ?, ?, CURDATE(), ?, ?, ?, FALSE)
                """;
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, assetId);
                ps.setString(2, department);
                ps.setString(3, category);
                ps.setInt(4, userId);
                ps.setString(5, checklistJson);
                ps.setString(6, remarks);

                int rows = ps.executeUpdate();
                System.out.println("PM inserted. Rows affected: " + rows);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Submission failed: " + e.getMessage());
            request.getRequestDispatcher("pmForm.jsp").forward(request, response);
            return;
        }

        // 4. Redirect back to role dashboard
        String role = (String) request.getSession().getAttribute("role");
        response.sendRedirect(role != null ? role.toLowerCase() + "_dashboard.jsp" : "login.jsp");
    }
}
