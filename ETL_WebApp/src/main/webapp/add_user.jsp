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
    <title>Add User</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-4">
    <h3 class="text-center text-primary mb-4">Add New User</h3>

    <form action="addUser" method="post" class="border p-4 bg-white rounded shadow-sm">

        <div class="mb-3">
            <label class="form-label">Username</label>
            <input name="username" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Password</label>
            <input name="password" type="password" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Full Name</label>
            <input name="fullName" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Role</label>
            <select name="role" class="form-select" required>
                <option value="">-- Select Role --</option>
                <option value="ContractCell">Contract Cell</option>
                <option value="Coordinator">Coordinator</option>
                <option value="InCharge">InCharge</option>
                <option value="Engineer">Engineer</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Department / Zone</label>
            <input name="department" class="form-control" required />
        </div>

        <div class="d-grid">
            <button class="btn btn-success">Add User</button>
        </div>
    </form>
</div>
</body>
</html>
