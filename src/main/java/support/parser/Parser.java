package support.parser;

import data.*;
import com.opencsv.RFC4180Parser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Parser {
    public static StudyGroup parseArrayToStudyGroup(String[] x) throws InvalidDataException{//without id
        if (x.length != 9) {
            throw new InvalidDataException("StudyGroup can only contain 9 elements");
        }
        String name = Parser.parseName(x[0]);
        Coordinates coordinates = Coordinates.builder().
                x(Parser.parseCoordinatesX(x[1])).
                y(Parser.parseCoordinatesY(x[2])).
                build();
        Date creationDate = new Date();
        long studentsCount = Parser.parseStudentsCount(x[3]);
        FormOfEducation formOfEducation = Parser.parseFormOfEducation(x[4]);
        if (formOfEducation.equals(FormOfEducation.NULL)) {
            formOfEducation = null;
        }
        Semester semesterEnum = Parser.parseSemesterEnum(x[5]);
        Person groupAdmin = null;
        String groupAdminName = Parser.parseGroupAdminName(x[6]);
        if (!groupAdminName.equals("")) {
            groupAdmin = Person.builder().
                    name(groupAdminName).
                    birthday(Parser.parseGroupAdminBirthday(x[7])).
                    weight(Parser.parseGroupAdminWeight(x[8])).
                    build();
        }
        return StudyGroup.builder().
                id(0).
                name(name).
                coordinates(coordinates).
                creationDate(creationDate).
                studentsCount(studentsCount).
                formOfEducation(formOfEducation).
                semesterEnum(semesterEnum).
                groupAdmin(groupAdmin).
                build();
    }



    public static String parseEntryToCSV(Map.Entry<Long, StudyGroup> x) {
        ArrayList<String> toCSV = new ArrayList<>();
        toCSV.add(x.getKey().toString());
        StudyGroup studyGroup = x.getValue();
        toCSV.add(studyGroup.getName());
        toCSV.add(studyGroup.getCoordinates().getX().toString());
        toCSV.add(Double.valueOf(studyGroup.getCoordinates().getY()).toString());
        toCSV.add(Long.valueOf(studyGroup.getStudentsCount()).toString());
        if (studyGroup.getFormOfEducation() == null) {
            toCSV.add("");
        } else {
            toCSV.add(studyGroup.getFormOfEducation().toString());
        }
        toCSV.add(studyGroup.getSemesterEnum().toString());
        if (studyGroup.getGroupAdmin() == null) {
            toCSV.add("");
            toCSV.add("");
            toCSV.add("");
        } else {
            toCSV.add(studyGroup.getGroupAdmin().getName());
            ZonedDateTime birthday = studyGroup.getGroupAdmin().getBirthday();
            String zone = birthday.getZone().toString();
            for (Map.Entry<String,String> entry : ZoneId.SHORT_IDS.entrySet()) {
                if (zone.equals(entry.getValue())) {
                    zone = entry.getKey();
                    break;
                }
            }
            toCSV.add(birthday.toLocalDateTime() + zone);
            toCSV.add(Integer.valueOf(studyGroup.getGroupAdmin().getWeight()).toString());
        }
        RFC4180Parser rfc4180Parser = new RFC4180Parser();
        return rfc4180Parser.parseToLine(toCSV.toArray(new String[0]), false);
    }

    public static String[] parseCSVToArray(String x) {
        Reader in = new StringReader(x);
        try {
            CSVParser parser = new CSVParser(in, CSVFormat.RFC4180);
            return parser.getRecords().get(0).toList().toArray(new String[0]);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    public static long parseId(String x) throws InvalidDataException {
        try {
            long result = Long.parseLong(x);
            if (result <= 0) {
                throw (new InvalidDataException("Id can only be higher than 0"));
            }
            return result;
        } catch (NumberFormatException e) {
            throw (new InvalidDataException("Id can only be long"));
        }
    }

    public static Long parseKey(String x) throws InvalidDataException {
        try {
            Long result = Long.parseLong(x);
            return result;
        } catch (NumberFormatException e) {
            throw (new InvalidDataException("Key can only be long"));
        }
    }

    public static String parseName(String x) throws InvalidDataException {
        if (x.equals("")) {
            throw (new InvalidDataException("Name can not be empty"));
        }
        return x;
    }

    public static Double parseCoordinatesX(String x) throws InvalidDataException {
        try {
            Double result = Double.parseDouble(x);
            if (result <= -682) {
                throw (new InvalidDataException("CoordinatesX can only be higher than -682"));
            }
            return result;
        } catch (NumberFormatException e) {
            throw (new InvalidDataException("CoordinatesX can only be a double"));
        }
    }

    public static Double parseCoordinatesY(String x) throws InvalidDataException {
        try {
            return Double.parseDouble(x);
        } catch (NumberFormatException e) {
            throw (new InvalidDataException("CoordinatesY can only be a double"));
        }
    }

    public static Long parseStudentsCount(String x) throws InvalidDataException {
        try {
            Long result = Long.parseLong(x);
            if (result <= 0) {
                throw (new InvalidDataException("StudentsCount can only be higher than 0"));
            }
            return result;
        } catch (NumberFormatException e) {
            throw (new InvalidDataException("StudentsCount can only be long"));
        }
    }

    public static FormOfEducation parseFormOfEducation(String x) throws InvalidDataException {
        try {
            if (x.equals("")) {
                return FormOfEducation.NULL;
            }
            return FormOfEducation.valueOf(x);
        } catch (IllegalArgumentException e) {
            throw (new InvalidDataException("FormOfEducation can only be DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES or empty"));
        }
    }

    public static Semester parseSemesterEnum(String x) throws InvalidDataException {
        try {
            return Semester.valueOf(x);
        } catch (IllegalArgumentException e) {
            throw (new InvalidDataException("SemesterEnum can only be FIRST, SECOND, FOURTH, SIXTH or EIGHTH"));
        }
    }

    public static String parseGroupAdminName(String x) throws InvalidDataException {
        return x;//if this field is empty, groupAdmin will be null
    }

    public static ZonedDateTime parseGroupAdminBirthday(String x) throws InvalidDataException {
        try {
            int year = Integer.parseInt(x.substring(0, 4));
            int month = Integer.parseInt(x.substring(5, 7));
            int day = Integer.parseInt(x.substring(8, 10));
            int hour = Integer.parseInt(x.substring(11, 13));
            int minute = Integer.parseInt(x.substring(14, 16));
            ZoneId zoneId = ZoneId.of(x.substring(16));
            LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);
            ZonedDateTime result = ZonedDateTime.of(localDateTime, zoneId);
            return result;
        } catch (Exception e) {
            throw (new InvalidDataException("Format: yyyy-MM-ddThh:mm + ZoneId.SHORT_IDS key"));
        }
    }

    public static Integer parseGroupAdminWeight(String x) throws InvalidDataException {
        try {
            Integer result = Integer.parseInt(x);
            if (result <= 0) {
                throw (new InvalidDataException("GroupAdminWeight can only be higher than 0"));
            }
            return result;
        } catch (NumberFormatException e) {
            throw (new InvalidDataException("GroupAdminWeight can only be a integer"));
        }
    }
}