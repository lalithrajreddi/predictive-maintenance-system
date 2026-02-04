<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.PMRecord, java.util.List" %>
<%@ page import="org.json.JSONObject" %>
<%
    List<PMRecord> pmList = (List<PMRecord>) request.getAttribute("pmList");
    String role = (String) session.getAttribute("role");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Approve Preventive Maintenance</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        ul { padding-left: 1.25rem; margin-bottom: 0; }
        table td { vertical-align: middle; }
    </style>
</head>
<body>
<div class="container mt-4">
    <h3 class="text-primary mb-4">PMs Awaiting Approval</h3>

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
            <th>Action</th>
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
                    <td>
                        <form action="approvePM" method="post">
                            <input type="hidden" name="pmId" value="<%= r.getPmId() %>">
                            <button type="submit" class="btn btn-success btn-sm">✔ Approve</button>
                        </form>
                    </td>
                </tr>
        <% }} else { %>
            <tr><td colspan="11" class="text-center text-muted">No PMs pending approval.</td></tr>
        <% } %>
        </tbody>
    </table>

</div>
</body>
</html>
