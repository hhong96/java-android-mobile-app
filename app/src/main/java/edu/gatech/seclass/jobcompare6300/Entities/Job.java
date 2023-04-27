package edu.gatech.seclass.jobcompare6300.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Citations:
//      https://developer.android.com/training/data-storage/room/defining-data
@Entity(tableName = "jobs")
public class Job implements Parcelable {
    @PrimaryKey(autoGenerate=true)
    private Integer rowId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "company")
    private String company;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "col")
    private Integer costOfLiving;

    @ColumnInfo(name = "salary")
    private Integer yearlySalary;

    @ColumnInfo(name = "bonus")
    private Integer yearlyBonus;

    @ColumnInfo(name = "rsu")
    private Integer restrictedStockUnitAward;

    @ColumnInfo(name = "reloc")
    private Integer relocationStipend;

    @ColumnInfo(name = "pto")
    private Integer personalChoiceHolidays;

    @ColumnInfo(name = "is_curr_job")
    private boolean isCurrJob = false;
    private boolean isSelected = false;
    private double jobScore;

    public Job() {
        // default constructor; nothing set
    }

    public Job(String title, String company, String city, String state, Integer costOfLiving,
               Integer yearlySalary, Integer yearlyBonus, Integer restrictedStockUnitAward,
               Integer relocationStipend, Integer personalChoiceHolidays) {
        setTitle(title);
        setCompany(company);
        setCity(city);
        setState(state);
        setCostOfLiving(costOfLiving);
        setYearlySalary(yearlySalary);
        setYearlyBonus(yearlyBonus);
        setRestrictedStockUnitAward(restrictedStockUnitAward);
        setRelocationStipend(relocationStipend);
        setPersonalChoiceHolidays(personalChoiceHolidays);
    }

    protected Job(Parcel in) {
        title = in.readString();
        company = in.readString();
        city = in.readString();
        state = in.readString();
        costOfLiving = in.readInt();
        yearlySalary = in.readInt();
        yearlyBonus = in.readInt();
        restrictedStockUnitAward = in.readInt();
        relocationStipend = in.readInt();
        personalChoiceHolidays = in.readInt();
        isCurrJob = in.readByte() != 0;
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public void updateJob(Job newJob) {
        setTitle(newJob.getTitle());
        setCompany(newJob.getCompany());
        setCity(newJob.getCity());
        setState(newJob.getState());
        setCostOfLiving(newJob.getCostOfLiving());
        setYearlySalary(newJob.getYearlySalary());
        setYearlyBonus(newJob.getYearlyBonus());
        setRestrictedStockUnitAward(newJob.getRestrictedStockUnitAward());
        setRelocationStipend(newJob.getRelocationStipend());
        setPersonalChoiceHolidays(newJob.getPersonalChoiceHolidays());
    }

    //Getter for rowId
    public Integer getRowId() {
        return rowId;
    }

    // Setter for rowId
    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public void setTitle(String title) {
        if(title.isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be empty!");
        } else {
            this.title = title;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setCompany(String company) {
        if(company.isEmpty()) {
            throw new IllegalArgumentException(("Company name cannot be empty!"));
        } else {
            this.company = company;
        }
    }

    public String getCompany() {
        return this.company;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setCostOfLiving(int costOfLiving) {
        if(costOfLiving <= 0) {
            throw new IllegalArgumentException("Cost of living should be an integer greater than 0!");
        } else {
            this.costOfLiving = costOfLiving;
        }
    }

    public Integer getCostOfLiving() {
        return this.costOfLiving;
    }

    public void setYearlySalary(int yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public Integer getYearlySalary() {
        return this.yearlySalary;
    }

    public void setYearlyBonus(int yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public Integer getYearlyBonus() {
        return this.yearlyBonus;
    }

    public void setRestrictedStockUnitAward(int rsu) {
        this.restrictedStockUnitAward = rsu;
    }

    public Integer getRestrictedStockUnitAward() {
        return this.restrictedStockUnitAward;
    }

    public void setRelocationStipend(int relocStipend) {
        if(!(relocStipend >= 0 && relocStipend <= 25000)) {
            throw new IllegalArgumentException("Relocation stipend must be between $0 and $25,000!");
        } else {
            this.relocationStipend = relocStipend;
        }
    }

    public Integer getRelocationStipend() {
        return this.relocationStipend;
    }

    public void setPersonalChoiceHolidays(int pto) {
        if(!(pto >= 0 && pto <= 20)) {
            throw new IllegalArgumentException("Personal choice holidays should be between 0 and 20 days!");
        } else {
            this.personalChoiceHolidays = pto;
        }
    }

    public Integer getPersonalChoiceHolidays() {
        return this.personalChoiceHolidays;
    }

    public void setIsCurrJob(boolean isCurrJob) {
        this.isCurrJob = isCurrJob;
    }

    public boolean getIsCurrJob() {
        return this.isCurrJob;
    }
    
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setJobScore(double score) {
        this.jobScore = score;
    }

    public double getJobScore() {
        return this.jobScore;
    }

    @Override
    public String toString() {
        return getTitle() + ", " + getCompany();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(company);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeInt(costOfLiving);
        parcel.writeInt(yearlySalary);
        parcel.writeInt(yearlyBonus);
        parcel.writeInt(restrictedStockUnitAward);
        parcel.writeInt(relocationStipend);
        parcel.writeInt(personalChoiceHolidays);
        parcel.writeByte((byte) (isCurrJob ? 1 : 0));
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
