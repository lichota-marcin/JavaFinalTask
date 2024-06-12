import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CalculatorTest {

    HoursCalculator calculator = new HoursCalculator();
    final LocalDateTime timeStart = LocalDateTime.of(LocalDate.of(2024, 6, 3), LocalTime.of(12, 0, 0));
    final LocalDateTime timeEnd = LocalDateTime.of(LocalDate.of(2024, 6, 3), LocalTime.of(18, 0, 0));
    final LocalDateTime timeStartWeekend = LocalDateTime.of(LocalDate.of(2024, 6, 7), LocalTime.of(8, 0, 0));
    final LocalDateTime timeEndWeekend = LocalDateTime.of(LocalDate.of(2024, 6, 10), LocalTime.of(22, 0, 0));

    @Test
    public void workingHoursEarlierToLater() {
        int hours = calculator.getWorkingHours(timeStart, timeEnd);
        Assert.assertEquals(hours, 6);
    }

    @Test
    public void workingHoursEarlierToLaterWeekendIncluded() {
        int hours = calculator.getWorkingHours(timeStartWeekend, timeEndWeekend);
        Assert.assertEquals(hours, 16);
    }

    @Test
    public void workingHoursLaterToEarlier() {
        int hours = calculator.getWorkingHours(timeEnd, timeStart);
        Assert.assertEquals(hours, 6);
    }

    @Test
    public void workingHoursLaterToEarlierWeekendIncluded() {
        int hours = calculator.getWorkingHours(timeEndWeekend, timeStartWeekend);
        Assert.assertEquals(hours, 16);
    }

    @Test
    public void endDateRandom() {
        LocalDateTime actual = calculator.endDate(timeStart, 20);
        final LocalDateTime expected = LocalDateTime.of(LocalDate.of(2024, 6, 5), LocalTime.of(16, 0, 0));
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void endDateEndAt18() {
        LocalDateTime actual = calculator.endDate(timeStartWeekend, 8);
        final LocalDateTime expected = LocalDateTime.of(LocalDate.of(2024, 6, 8), LocalTime.of(18, 0, 0));
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void endDateWeekendIncluded() {
        LocalDateTime actual = calculator.endDate(timeStartWeekend, 17);
        final LocalDateTime expected = LocalDateTime.of(LocalDate.of(2024, 6, 11), LocalTime.of(11, 0, 0));
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void completionTimeOnlyHours() {
        String actual = calculator.whenCompletion(7);
        Assert.assertEquals(actual, "Training is not finished. 7 h are left until the end.");
    }

    @Test
    public void completionTime() {
        String actual = calculator.whenCompletion(11);
        Assert.assertEquals(actual, "Training is not finished. 1 d 3 h are left until the end.");
    }

    @Test
    public void completionTimeTrainingFinishedOnlyHours() {
        String actual = calculator.whenCompletion(-5);
        Assert.assertEquals(actual, "Training completed. 5 h have passed since the end.");
    }

    @Test
    public void completionTimeTrainingFinished() {
        String actual = calculator.whenCompletion(-23);
        Assert.assertEquals(actual, "Training completed. 2 d 7 h have passed since the end.");
    }
}
