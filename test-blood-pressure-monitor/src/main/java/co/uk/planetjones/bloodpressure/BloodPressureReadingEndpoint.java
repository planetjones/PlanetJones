package co.uk.planetjones.bloodpressure;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.List;

@Api(name="bp",
     version = "v1",
     description = "Test API for Blood Pressure Readings")
public class BloodPressureReadingEndpoint {

    private static List<BloodPressureReading> testData = new ArrayList<>();

    static {
        testData.add(new BloodPressureReading(1, (short)100, (short)200));
        testData.add(new BloodPressureReading(2, (short)80, (short)130));
        testData.add(new BloodPressureReading(3, (short)60, (short)100));
    }

    @ApiMethod(
            name = "bpreading.list",
            path = "bpreading",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public List<BloodPressureReading> list(@Nullable @Named("limit") Integer limit) {
        System.out.println("Limit was: " + limit);
        return testData;
    }

    @ApiMethod(
            name = "bpreading.get",
            path = "bpreading/{id}",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public BloodPressureReading get(@Named("id") Integer id) {

        System.out.println("ID is " + id);

        for(BloodPressureReading reading : testData) {
            if(reading.getId().equals(id)) {
                return reading;
            }
        }

        return null;
    }

    @ApiMethod(
            name = "bpreading.add",
            path = "bpreading",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public void add(BloodPressureReading reading) {
        System.out.println("Adding: " + reading);
    }

    @ApiMethod(
            name = "bpreading.delete",
            path = "bpreading/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("id") Long id) {
        System.out.println("Deleting: " + id);
    }
}
