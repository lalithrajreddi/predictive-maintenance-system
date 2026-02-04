package servlets;

import model.Asset;
import utils.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/complaintForm")
public class ComplaintFormServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Asset> assets = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT asset_id, asset_number, item_text FROM assets"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Asset asset = new Asset();
                asset.setAssetId(rs.getInt("asset_id"));
                asset.setAssetNumber(rs.getString("asset_number"));
                asset.setItemText(rs.getString("item_text"));
                assets.add(asset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("assets", assets);
        request.getRequestDispatcher("complaintForm.jsp").forward(request, response);
    }
}
