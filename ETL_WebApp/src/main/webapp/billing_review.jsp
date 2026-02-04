<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.PMRecord" %>
<%@ page import="org.json.JSONObject" %>
<%
    List<PMRecord> pmList = (List<PMRecord>) request.getAttribute("pmList");
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PM Billing Review</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        ul { padding-left: 1.25rem; margin-bottom: 0; }
        table td { vertical-align: middle; }
    </style>
</head>
<body>
<div class="container mt-4">
    <h3 class="text-primary mb-4">Preventive Maintenance Billing Review</h3>

    <table class="table table-bordered table-hover">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Asset</th>
                <th>Date</th>
                <th>Department</th>
                <th>Category</th>
                <th>Checklist</th>
                <th>Remarks</th>
                <th>Done By</th>
                <th>Verified On</th>
                <th>Approved On</th>
                <th>Billed</th>
                <% if ("EIC".equals(role)) { %><th>Action</th><% } %>
            </tr>
        </thead>
        <tbody>
        <% if (pmList != null && !pmList.isEmpty()) {
            for (PMRecord r : pmList) { %>
                <tr>
                    <td><%= r.getPmId() %></td>
                    <td><%= r.getAssetNumber() %></td>
                    <td><%= r.getPmDate() %></td>
                    <td><%= r.getDepartment() %></td>
                    <td><%= r.getCategory() %></td>
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
                    <td><%= r.getVerifiedOn() != null ? r.getVerifiedOn() : "⛔" %></td>
                    <td><%= r.getApprovedOn() != null ? r.getApprovedOn() : "⛔" %></td>
                    <td><%= r.isBilled() ? "✔" : "❌" %></td>
                    <% if ("EIC".equals(role)) { %>
                        <td>
                            <% if (!r.isBilled()) { %>
                                <form method="post" action="markBilled">
                                    <input type="hidden" name="pmId" value="<%= r.getPmId() %>">
                                    <button class="btn btn-success btn-sm">Mark</button>
                                </form>
                            <% } else { %>
                                <span class="text-muted">Already Billed</span>
                            <% } %>
                        </td>
                    <% } %>
                </tr>
        <% }} else { %>
            <tr><td colspan="12" class="text-center text-muted">No records found.</td></tr>
        <% } %>
        </tbody>
    </table>

</div>

<!-- Font Awesome (for icons) -->
<script src="https://kit.fontawesome.com/a2e0e6a97d.js" crossorigin="anonymous"></script>
</body>
</html>
