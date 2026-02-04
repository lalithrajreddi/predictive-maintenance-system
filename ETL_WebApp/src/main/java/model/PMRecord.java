package model;

import java.sql.Date;

public class PMRecord {
    private int pmId;
    private String assetNumber;
    private Date pmDate;
    private String checklist;
    private String remarks;
    private String doneBy;
    private String department;
    private String category;
    private boolean billed;
    private Date verifiedOn;
    private Date approvedOn;

    public int getPmId() { return pmId; }
    public void setPmId(int pmId) { this.pmId = pmId; }

    public String getAssetNumber() { return assetNumber; }
    public void setAssetNumber(String assetNumber) { this.assetNumber = assetNumber; }

    public Date getPmDate() { return pmDate; }
    public void setPmDate(Date pmDate) { this.pmDate = pmDate; }

    public String getChecklist() { return checklist; }
    public void setChecklist(String checklist) { this.checklist = checklist; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getDoneBy() { return doneBy; }
    public void setDoneBy(String doneBy) { this.doneBy = doneBy; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isBilled() { return billed; }
    public void setBilled(boolean billed) { this.billed = billed; }

    public Date getVerifiedOn() { return verifiedOn; }
    public void setVerifiedOn(Date verifiedOn) { this.verifiedOn = verifiedOn; }

    public Date getApprovedOn() { return approvedOn; }
    public void setApprovedOn(Date approvedOn) { this.approvedOn = approvedOn; }
}
