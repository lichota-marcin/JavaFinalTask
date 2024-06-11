import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;

public class HoursCalculator {
    private static final int WORK_HOUR_START = 10;
    private static final int WORK_HOUR_END = 18;
    private static final long WORKING_HOURS_PER_DAY = WORK_HOUR_END - WORK_HOUR_START;

    public int getWorkingHours(LocalDateTime startTime, LocalDateTime endTime) {
        if (null == startTime || null == endTime) {
            throw new IllegalStateException();
        }
        if (endTime.isBefore(startTime)) {
            LocalDateTime temp;
            temp = endTime;
            endTime = startTime;
            startTime = temp;

        }
        LocalDate fromDay = startTime.toLocalDate();
        LocalDate toDay = endTime.toLocalDate();

        int allDaysBetween = (int) (ChronoUnit.DAYS.between(fromDay, toDay) + 1);
        LocalDateTime finalStartTime = startTime;
        long allWorkingHours = IntStream.range(0, allDaysBetween)
                .filter(i -> isWorkingDay(finalStartTime.plusDays(i)))
                .count() * WORKING_HOURS_PER_DAY;

        long extraHoursBeforeStart = 0;
        if (isWorkingDay(startTime)) {
            if (isWorkingHours(startTime)) {
                extraHoursBeforeStart = Duration.between(fromDay.atTime(WORK_HOUR_START, 0), startTime).toHours();
            } else if (startTime.getHour() > WORK_HOUR_END) {
                extraHoursBeforeStart = WORKING_HOURS_PER_DAY;
            }
        }

        long extraHoursAfterEnd = 0;
        if (isWorkingDay(endTime)) {
            if (isWorkingHours(endTime)) {
                extraHoursAfterEnd = Duration.between(endTime, toDay.atTime(WORK_HOUR_END, 0)).toHours();
            } else if (endTime.getHour() < WORK_HOUR_START) {
                extraHoursAfterEnd = WORKING_HOURS_PER_DAY;
            }
        }
        return (int) (allWorkingHours - extraHoursBeforeStart - extraHoursAfterEnd);
    }

    private boolean isWorkingDay(final LocalDateTime time) {
        return time.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
    }

    private boolean isWorkingHours(final LocalDateTime time) {
        int hour = time.getHour();
        return WORK_HOUR_START <= hour && hour <= WORK_HOUR_END;
    }

    public LocalDateTime endDate(LocalDateTime startTime, int duration) {
        int numberOfDays = duration / 8;
        int remainingHours = duration % 8;
        if (startTime.getHour() < WORK_HOUR_START) {
            startTime = startTime.withHour(WORK_HOUR_START);
        }
        while (numberOfDays > 0) {
            if (startTime.getHour() == WORK_HOUR_START) {
                startTime = startTime.plusHours(8);
                numberOfDays = numberOfDays - 1;
            }
            startTime = startTime.plusDays(1);
            if (startTime.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue()) {
                numberOfDays = numberOfDays - 1;
            }
        }
        int hour = startTime.getHour();
        int sumOfHours = hour + remainingHours;
        if (sumOfHours > WORK_HOUR_END) {
            int overflowingHours = sumOfHours - WORK_HOUR_END;
            startTime = startTime.plusDays(1);
            if (startTime.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue()) {
                startTime = startTime.withHour(WORK_HOUR_START + overflowingHours);
            } else if (startTime.getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue()) {
                startTime = startTime.plusDays(2).withHour(WORK_HOUR_START + overflowingHours);
            }
        } else {
            startTime = startTime.plusHours(remainingHours);
        }

        return startTime;
    }

    public int hoursCompletion(LocalDateTime timeStart, int duration) {
        LocalDateTime endDate = endDate(timeStart, duration);
        LocalDateTime now = LocalDateTime.now();
        int hours = getWorkingHours(now, endDate);
        if (endDate.isBefore(now))
            return -hours;
        else
            return hours;
    }

    public String whenCompletion(int hoursCompletion) {
        int numberOfDays = hoursCompletion / 8;
        int remainingHours = hoursCompletion % 8;
        String completion = "";
        if (hoursCompletion > 7) {
            completion = "Training is not finished. " + numberOfDays + " d" + " " + remainingHours + " h" + " are left until the end.";
        } else if (hoursCompletion > 0 && hoursCompletion <= 7) {
            completion = "Training is not finished. " + remainingHours + " h" + " are left until the end.";
        } else if (hoursCompletion == 0) {
            completion = "Training is finished right at this very hour.";
        } else if (hoursCompletion < 0 && hoursCompletion >= -7) {
            completion = "Training completed. " + Math.abs(remainingHours) + " h" + " have passed since the end.";
        } else if (hoursCompletion < -7) {
            completion = "Training completed. " + Math.abs(numberOfDays) + " d" + " " + Math.abs(remainingHours) + " h" + " have passed since the end.";
        }
        return completion;
    }
}