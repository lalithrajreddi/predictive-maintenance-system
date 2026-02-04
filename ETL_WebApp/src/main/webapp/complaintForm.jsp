<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Asset" %>
<%
    List<Asset> assets = (List<Asset>) request.getAttribute("assets");
    String role = (String) session.getAttribute("role");
    String fullName = (String) session.getAttribute("fullName");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Raise Complaint</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h3 class="mb-4 text-danger">Raise Complaint</h3>

    <form action="raiseComplaint" method="post" class="border p-4 bg-light rounded shadow-sm">

        <!-- Asset Dropdown -->
        <div class="mb-3">
            <label class="form-label">Select Asset</label>
            <select name="assetId" class="form-select" required>
                <% if (assets != null && !assets.isEmpty()) {
                       for (Asset a : assets) { %>
                    <option value="<%= a.getAssetId() %>">
                        <%= a.getAssetNumber() %> - <%= a.getItemText() %>
                    </option>
                <%   }
                   } else { %>
                    <option disabled>No assets available</option>
                <% } %>
            </select>
        </div>

        <!-- Department Dropdown -->
        <div class="mb-3">
            <label class="form-label">Select Department</label>
            <select name="department" class="form-select" required>
                <option value="">-- Select Department --</option>
                <option value="North Zone">North Zone</option>
                <option value="South Zone">South Zone</option>
                <option value="East Zone">East Zone</option>
                <option value="West Zone">West Zone</option>
            </select>
        </div>

        <!-- Category Dropdown -->
        <div class="mb-3">
            <label class="form-label">Select Category</label>
            <select name="category" class="form-select" required>
                <option value="">-- Select Category --</option>
                <option value="Electrical">Electrical</option>
                <option value="HVAC">HVAC</option>
                <option value="Plumbing">Plumbing</option>
                <option value="Civil">Civil</option>
                <option value="IT">IT</option>
            </select>
        </div>

        <!-- Description -->
        <div class="mb-3">
            <label class="form-label">Complaint Description</label>
            <textarea name="description" class="form-control" rows="4" required></textarea>
        </div>

        <button class="btn btn-danger">Submit Complaint</button>
    </form>

</div>
</body>
</html>
