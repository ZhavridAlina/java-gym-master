package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySessions);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        assertEquals(new TimeOfDay(13, 0), thursdaySessions.firstEntry().getKey());

        List<TimeOfDay> keys = new ArrayList<>(thursdaySessions.keySet());
        assertEquals(new TimeOfDay(20, 0), keys.get(1));

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertNull(tuesdaySessions);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> mondaySessions1 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, mondaySessions1.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> mondaySessions2 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertEquals(0, mondaySessions2.size());
    }

    @Test
    void testGetOneCoachRanking() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Петров", "Петр", "Петрович");

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);

        ArrayList<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(1, result.size());
        assertEquals(coach1.toString(), result.get(0).getCoach().toString());
    }

    @Test
    void testGetCoachesRanking() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Петров", "Петр", "Петрович");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach3 = new Coach("Васильев", "Василий", "Васильевич");

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession tuedayTrainingSession = new TrainingSession(group2, coach3,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession thursdayTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(12, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(group1, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(tuedayTrainingSession);
        timetable.addNewTrainingSession(thursdayTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        ArrayList<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(3, result.size());
        assertEquals(coach2.toString(), result.get(0).getCoach().toString());
    }

    @Test
    void testGetNoneCoachRanking() {
        Timetable timetable = new Timetable();
        ArrayList<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals(0, result.size());
        assertNotNull(result);
    }
}
