package me.kp56.timetables.filters;

import java.io.*;
import java.util.*;

public record Filter(String person, List<Period> periods) implements Serializable {
    private static Map<String, Filter> filters = new HashMap<>();

    public Filter {
        filters.put(person, this);
    }

    public static Collection<Filter> getFilters() {
        return filters.values();
    }

    public static void deleteFilter(String name) {
        filters.remove(name);
    }

    public static void saveFilters() {
        try {
            FileOutputStream fos = new FileOutputStream("filters");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(filters.size());
            for (Filter filter : filters.values()) {
                filter.saveFilter(oos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFilter(ObjectOutputStream oos) throws IOException {
        for (Period p : periods) {
            p.savePeriod(oos);
        }
    }

    public static Filter byName(String name) {
        return filters.get(name);
    }

    @Override
    public String toString() {
        return person;
    }
}
