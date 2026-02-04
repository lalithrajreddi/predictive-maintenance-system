<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%
    String role = (String) session.getAttribute("role");
    if (!"EIC".equals(role)) {
        response.sendRedirect("unauthorized.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Asset</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h3 class="text-success mb-4">Add New Asset</h3>
    <form action="addAsset" method="post" class="border p-4 bg-white rounded shadow-sm">
        <div class="mb-3">
            <label>Asset Number</label>
            <input name="assetNumber" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>BOQ Item</label>
            <input name="boqItem" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>Item Text</label>
            <input name="itemText" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>Equipment Type</label>
            <input name="equipmentType" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>Section</label>
            <input name="section" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>Location</label>
            <input name="location" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>Department</label>
            <input name="department" class="form-control" required />
        </div>
        <button class="btn btn-success">Add Asset</button>
    </form>
</div>
</body>
</html>
