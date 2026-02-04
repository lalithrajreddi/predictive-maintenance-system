<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Complaint" %>
<%
    List<Complaint> list = (List<Complaint>) request.getAttribute("complaints");
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Complaint Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
    <h3 class="mb-3 text-primary">Complaint Report</h3>

	<div class="d-flex justify-content-end mb-3">
        <% if ("EIC".equals(role)) { %>
            <a href="downloadComplaintCSV" class="btn btn-outline-primary btn-sm">
                <i class="fas fa-file-csv"></i> Download CSV
            </a>
        <% } %>
    </div>

    <table class="table table-bordered">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Asset</th>
                <th>Date</th>
                <th>Department</th>
                <th>Category</th>
                <th>Description</th>
                <th>Status</th>
                <th>Feedback</th>
            </tr>
        </thead>
        <tbody>
        <% for (Complaint c : list) { %>
            <tr>
                <td><%= c.getComplaintId() %></td>
                <td><%= c.getAssetName() %></td>
                <td><%= c.getComplaintDate() %></td>
                <td><%= c.getDepartment() %></td>
                <td><%= c.getCategory() %></td>
                <td><%= c.getDescription() %></td>
                <td><%= c.getStatus() %></td>
                <td><%= c.getFeedback() != null ? c.getFeedback() : "-" %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
</body>
</html>
