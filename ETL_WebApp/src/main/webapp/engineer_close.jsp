<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Complaint" %>
<%
    List<Complaint> complaintList = (List<Complaint>) request.getAttribute("complaintList");
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Engineer Complaints - Close</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table td { vertical-align: middle; }
        ul { padding-left: 1.2rem; }
    </style>
</head>
<body>
<div class="container mt-4">
    <h3 class="text-primary mb-4">Open Complaints Assigned</h3>

    <table class="table table-bordered table-hover">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Asset</th>
                <th>Date</th>
                <th>Department</th>
                <th>Category</th>
                <th>Description</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <% if (complaintList != null && !complaintList.isEmpty()) {
            for (Complaint c : complaintList) { %>
            <tr>
                <td><%= c.getComplaintId() %></td>
                <td><%= c.getAssetName() %></td>
                <td><%= c.getComplaintDate() %></td>
                <td><%= c.getDepartment() %></td>
                <td><%= c.getCategory() %></td>
                <td><%= c.getDescription() %></td>
                <td><%= c.getStatus() %></td>
                <td>
                    <form method="post" action="closeComplaint">
                        <input type="hidden" name="complaintId" value="<%= c.getComplaintId() %>">
                        <input type="text" name="feedback" class="form-control mb-2" placeholder="Enter feedback" required>
                        <button class="btn btn-success btn-sm" type="submit">Close</button>
                    </form>
                </td>
            </tr>
        <% }} else { %>
            <tr><td colspan="9" class="text-center text-muted">No open complaints available.</td></tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
