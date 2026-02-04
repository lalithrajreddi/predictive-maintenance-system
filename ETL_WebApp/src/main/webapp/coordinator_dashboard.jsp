<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%
    String role = (String) session.getAttribute("role");
    String fullName = (String) session.getAttribute("fullName");
    if (!"Coordinator".equals(role)) {
        response.sendRedirect("unauthorized.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Coordinator Dashboard</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0fc6c69.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4 text-primary">Welcome, <%= fullName %> (Coordinator)</h2>

    <div class="row g-3">
        <div class="col-md-4">
            <a href="complaintForm" class="btn btn-outline-danger w-100">
                <i class="fas fa-exclamation-circle me-2"></i>Raise Complaint
            </a>
        </div>
        <div class="col-md-4">
            <a href="verifyCoordinatorPM" class="btn btn-outline-primary w-100">
                <i class="fas fa-check-double me-2"></i>Verify PMs
            </a>
        </div>
        <div class="col-md-4">
            <a href="complaintReport" class="btn btn-outline-dark w-100">
                <i class="fas fa-file-alt me-2"></i>View Complaints
            </a>
        </div>
        <div class="col-md-4">
            <a href="pmReport" class="btn btn-outline-info w-100">
                <i class="fas fa-clipboard-check me-2"></i>PM Report
            </a>
        </div>
    </div>

    <div class="mt-4">
        <a href="logout.jsp" class="btn btn-secondary">
            <i class="fas fa-sign-out-alt me-1"></i>Logout
        </a>
    </div>
</div>
</body>
</html>
