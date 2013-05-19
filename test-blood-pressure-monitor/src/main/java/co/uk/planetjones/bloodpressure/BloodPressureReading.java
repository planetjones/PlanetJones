package co.uk.planetjones.bloodpressure;

import java.util.Date;

/**
 * Represents a Blood Pressure reading taken at a specific time
 */
public class BloodPressureReading {

    private Integer id;
    private Date created;
    private Short systolic;
    private Short diastolic;

    private static Long currentId = 0L;

    public BloodPressureReading() {
    }

    // just for the example
    public BloodPressureReading (Integer id, Short systolic, Short diastolic) {
        this.id = id;
        this.created = new Date();
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    @Override
    public String toString() {
        return "BloodPressureReading{" +
                "id=" + id +
                ", created=" + created +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                '}';
    }



    public Integer getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }


    public Short getSystolic() {
        return systolic;
    }


    public Short getDiastolic() {
        return diastolic;
    }




}
