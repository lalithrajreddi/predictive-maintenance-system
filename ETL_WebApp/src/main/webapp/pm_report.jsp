<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.PMRecord" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    List<PMRecord> pmList = (List<PMRecord>) request.getAttribute("pmList");
    String role = (String) session.getAttribute("role");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Preventive Maintenance Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        ul { padding-left: 1.25rem; margin-bottom: 0; }
        table td { vertical-align: middle; }
        .nowrap {
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h3 class="text-primary mb-4">Preventive Maintenance Report</h3>

	<div class="d-flex justify-content-end mb-3">
        <% if ("EIC".equals(role)) { %>
            <a href="downloadPMCSV" class="btn btn-outline-primary btn-sm">
                <i class="fas fa-file-csv"></i> Download CSV
            </a>
        <% } %>
    </div>

    <table class="table table-bordered table-hover">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Asset</th>
                <th class="nowrap">Date</th>
                <th>Department</th>
                <th>Category</th>
                <th>Checklist</th>
                <th>Remarks</th>
                <th>Done By</th>
                <th class="nowrap">Verified On</th>
                <th class="nowrap">Approved On</th>
                <th>Billed</th>
            </tr>
        </thead>
        <tbody>
        <% if (pmList != null && !pmList.isEmpty()) {
            for (PMRecord r : pmList) { %>
                <tr>
                    <td><%= r.getPmId() %></td>
                    <td><%= r.getAssetNumber() %></td>
                    <td class="nowrap"><%= sdf.format(r.getPmDate()) %></td>
                    <td><%= r.getDepartment() != null ? r.getDepartment() : "—" %></td>
                    <td><%= r.getCategory() != null ? r.getCategory() : "—" %></td>
                    <td>
                        <%
                            try {
                                JSONObject checklist = new JSONObject(r.getChecklist());
                        %>
                        <ul>
                            <% for (String key : checklist.keySet()) { %>
                                <li><strong><%= key %></strong>: <%= checklist.getString(key) %></li>
                            <% } %>
                        </ul>
                        <%
                            } catch (Exception e) {
                                out.print("Invalid checklist");
                            }
                        %>
                    </td>
                    <td><%= r.getRemarks() %></td>
                    <td><%= r.getDoneBy() %></td>
                    <td class="nowrap"><%= r.getVerifiedOn() != null ? sdf.format(r.getVerifiedOn()) : "⛔" %></td>
                    <td class="nowrap"><%= r.getApprovedOn() != null ? sdf.format(r.getApprovedOn()) : "⛔" %></td>
                    <td><%= r.isBilled() ? "✔" : "❌" %></td>
                </tr>
        <% }} else { %>
            <tr><td colspan="11" class="text-center text-muted">No records found.</td></tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
