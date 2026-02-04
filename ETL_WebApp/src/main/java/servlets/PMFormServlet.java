package servlets;

import model.Asset;
import utils.DBUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/pmForm")
public class PMFormServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Asset> assets = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT asset_id, asset_number, item_text FROM assets"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Asset a = new Asset();
                a.setAssetId(rs.getInt("asset_id"));
                a.setAssetNumber(rs.getString("asset_number"));
                a.setItemText(rs.getString("item_text"));
                assets.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("assets", assets);
        request.getRequestDispatcher("pmForm.jsp").forward(request, response);
    }
}
