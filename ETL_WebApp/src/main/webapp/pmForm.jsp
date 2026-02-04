<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Asset" %>
<%
    List<Asset> assets = (List<Asset>) request.getAttribute("assets");
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit Preventive Maintenance</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table td { vertical-align: middle; }
    </style>
</head>
<body>
<div class="container mt-5">
    <h3 class="mb-4 text-primary">Preventive Maintenance Form</h3>

    <form action="submitPM" method="post" class="bg-light p-4 border rounded shadow-sm">

        <!-- Asset Selection -->
        <div class="mb-3">
            <label class="form-label">Select Asset</label>
            <select name="assetId" class="form-select" required>
                <% if (assets != null) {
                    for (Asset a : assets) { %>
                        <option value="<%= a.getAssetId() %>">
                            <%= a.getAssetNumber() %> - <%= a.getItemText() %>
                        </option>
                <%  }} %>
            </select>
        </div>

        <!-- Department -->
        <div class="mb-3">
            <label class="form-label">Department</label>
            <select name="department" class="form-select" required>
                <option value="">-- Select Department --</option>
                <option value="North Zone">North Zone</option>
                <option value="South Zone">South Zone</option>
                <option value="East Zone">East Zone</option>
                <option value="West Zone">West Zone</option>
            </select>
        </div>

        <!-- Category -->
        <div class="mb-3">
            <label class="form-label">Category</label>
            <select name="category" class="form-select" required>
                <option value="">-- Select Category --</option>
                <option value="Electrical">Electrical</option>
                <option value="HVAC">HVAC</option>
                <option value="IT">IT</option>
                <option value="Printer">Printer</option>
            </select>
        </div>

        <!-- Checklist -->
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead class="table-light">
                    <tr>
                        <th>Check list for Maintenance personnel</th>
                        <th>PERFORMED</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        String[] items = {
                            "Check AC Input Voltage",
                            "Check Functioning of OS",
                            "Check Antivirus",
                            "Printer Cartridge Clean",
                            "Test Print Taken",
                            "Removal of Junk files",
                            "Check Healthiness of Monitor"
                        };

                        for (int i = 0; i < items.length; i++) {
                            String name = "check_" + i;
                    %>
                    <tr>
                        <td><%= items[i] %></td>
                        <td>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="<%= name %>" value="Yes" required>
                                <label class="form-check-label">Yes</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="<%= name %>" value="No">
                                <label class="form-check-label">No</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="<%= name %>" value="NA">
                                <label class="form-check-label">N/A</label>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <!-- Remarks -->
        <div class="mb-3">
            <label class="form-label">Remarks (Maximum 100 characters)</label>
            <input name="remarks" maxlength="100" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>

</div>
</body>
</html>
