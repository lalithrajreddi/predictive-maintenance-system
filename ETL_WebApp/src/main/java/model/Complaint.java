package model;

import java.util.Date;

public class Complaint {
    private int complaintId;
    private String assetName;
    private Date complaintDate;
    private String department;
    private String category;
    private String description;
    private String status;
    private String feedback;

    public int getComplaintId() { return complaintId; }
    public void setComplaintId(int complaintId) { this.complaintId = complaintId; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public Date getComplaintDate() { return complaintDate; }
    public void setComplaintDate(Date complaintDate) { this.complaintDate = complaintDate; }
    
    public String getDepartment() { return department;}
    public void setDepartment(String department) { this.department = department; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
