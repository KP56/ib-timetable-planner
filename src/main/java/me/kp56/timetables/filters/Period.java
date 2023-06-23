package me.kp56.timetables.filters;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public record Period(int fromDay, int fromLesson, int toDay, int toLesson) implements Serializable {
    public boolean isWithin(int day, int lesson) {
        if (day < 0 || lesson < 0) return false;

        return day > fromDay && day < toDay || (fromDay != toDay && ((day == fromDay && lesson >= fromLesson) || (day == toDay && lesson <= toLesson)))
                || (fromDay == toDay && day == fromDay && lesson >= fromLesson && lesson <= toLesson);
    }

    public boolean intersect(Period period) {
        return (fromDay > period.fromDay || toDay >= period.fromDay) && (fromDay <= period.toDay || toDay < period.toDay) && (fromDay > period.fromDay || toDay != period.fromDay || toLesson >= period.fromLesson) && (fromDay != period.toDay || toDay < period.toDay || fromLesson <= period.toLesson);
    }
    void savePeriod(ObjectOutputStream oos) throws IOException {
        oos.writeObject(this);
    }
}
