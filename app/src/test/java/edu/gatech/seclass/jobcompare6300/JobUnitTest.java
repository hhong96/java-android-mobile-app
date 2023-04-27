package edu.gatech.seclass.jobcompare6300;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.gatech.seclass.jobcompare6300.Entities.Job;

public class JobUnitTest {
    Job testJob = new Job();

    @Test
    public void setJobTitle_Success() {
        String testTitle = "Software Engineer";
        testJob.setTitle(testTitle);

        assertEquals(0, testTitle.compareTo(testJob.getTitle()));
    }

    @Test
    public void setJobTitle_Failure() {
        assertThrows(IllegalArgumentException.class, () -> testJob.setTitle(""));
    }

    @Test
    public void setCompanyName_Success() {
        String testCompany = "Microsoft";
        testJob.setCompany(testCompany);

        assertEquals(0, testCompany.compareTo(testJob.getCompany()));
    }

    @Test
    public void setCompanyName_Failure() {
        assertThrows(IllegalArgumentException.class, () -> testJob.setCompany(""));
    }

    @Test
    public void setRelocationStipend_Success() {
        Integer testStipend = 15000;
        testJob.setRelocationStipend(testStipend);

        assertEquals(testStipend, testJob.getRelocationStipend());
    }

    @Test
    public void setRelocationStipend_Failure() {
        assertThrows(IllegalArgumentException.class, () -> testJob.setRelocationStipend(50000));
    }

    @Test
    public void setPersonalChoiceHolidays_Success() {
        Integer testPTO = 15;
        testJob.setPersonalChoiceHolidays(testPTO);

        assertEquals(testPTO, testJob.getPersonalChoiceHolidays());
    }

    @Test
    public void setPersonalChoiceHolidays_Failure() {
        assertThrows(IllegalArgumentException.class, () -> testJob.setPersonalChoiceHolidays(40));
    }

    @Test
    public void setCostofLiving_Success() {
        Integer testCOL = 10;
        testJob.setCostOfLiving(testCOL);

        assertEquals(testCOL, testJob.getCostOfLiving());
    }

    @Test
    public void setCostofLiving_Failure() {
        assertThrows(IllegalArgumentException.class, () -> testJob.setCostOfLiving(0));
    }
}
