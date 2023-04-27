package edu.gatech.seclass.jobcompare6300.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


// Citations:
//      https://developer.android.com/training/data-storage/room/defining-data
@Entity(tableName = "comparison_setting")
public class ComparisonSetting {
    //Primary Key
    @PrimaryKey()
    private int rowId;

    @ColumnInfo(name = "salary_wght")
    private int salaryWght;

    @ColumnInfo(name = "bonus_wght")
    private int bonusWght;

    @ColumnInfo(name = "rsu_wght")
    private int rsuWght;

    @ColumnInfo(name = "relo_wght")
    private int reloWght;

    @ColumnInfo(name = "pto_wght")
    private int ptoWght;

    // Might be able to get rid of this
    public ComparisonSetting() {
    }

    public ComparisonSetting(int rowID, int salaryWght, int bonusWght, int rsuWght, int reloWght, int ptoWght) {
        this.rowId = rowID;
        this.salaryWght = salaryWght;
        this.bonusWght = bonusWght;
        this.rsuWght = rsuWght;
        this.reloWght = reloWght;
        this.ptoWght = ptoWght;
    }

    //Getter for rowId
    public int getRowId() {
        return rowId;
    }

    // Setter for rowId
    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    // Getter for salaryWght
    public int getSalaryWght() {
        return salaryWght;
    }

    // Setter for salaryWght
    public void setSalaryWght(int salaryWght) {
        this.salaryWght = salaryWght;
    }

    // Getter for bonusWght
    public int getBonusWght() {
        return bonusWght;
    }

    // Setter for bonusWght
    public void setBonusWght(int bonusWght) {
        this.bonusWght = bonusWght;
    }

    // Getter for rsuWght
    public int getRsuWght() {
        return rsuWght;
    }

    // Setter for rsuWght
    public void setRsuWght(int rsuWght) {
        this.rsuWght = rsuWght;
    }

    // Getter for reloWght
    public int getReloWght() {
        return reloWght;
    }

    // Setter for reloWght
    public void setReloWght(int reloWght) {
        this.reloWght = reloWght;
    }

    // Getter for ptoWght
    public int getPtoWght() {
        return ptoWght;
    }

    // Setter for ptoWght
    public void setPtoWght(int ptoWght) {
        this.ptoWght = ptoWght;
    }

    public int getWeightSum() {
        return getSalaryWght() + getBonusWght() + getRsuWght() + getReloWght() + getPtoWght();
    }
}
