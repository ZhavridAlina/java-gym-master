package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании

        //Получаем расписание на день
        TreeMap<TimeOfDay, List<TrainingSession>> timetableOfDay = timetable.get(trainingSession.getDayOfWeek());
        if (timetableOfDay == null) {
            timetableOfDay = new TreeMap<>();
            timetable.put(trainingSession.getDayOfWeek(), timetableOfDay);
        }

        //Получаем расписание тренировок в одно время
        List<TrainingSession> allTrainingsInTime = timetableOfDay.get(trainingSession.getTimeOfDay());
        if (allTrainingsInTime == null) {
            allTrainingsInTime = new ArrayList<>();
            timetableOfDay.put(trainingSession.getTimeOfDay(), allTrainingsInTime);
        }

        allTrainingsInTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> timetableOfDay = timetable.get(dayOfWeek);
        if (timetableOfDay == null)
            return Collections.emptyList();

        List<TrainingSession> allTrainingsInTime = timetableOfDay.get(timeOfDay);
        if (allTrainingsInTime == null) {
            return Collections.emptyList();
        }

        return allTrainingsInTime;

    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> countByCoaches = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable : timetable.values()) {
            for (List<TrainingSession> sessions : dayTimetable.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    if (countByCoaches.containsKey(coach)) {
                        Integer currentCount = countByCoaches.get(coach);
                        countByCoaches.put(coach, currentCount + 1);
                    } else {
                        countByCoaches.put(coach, 1);
                    }
                }
            }
        }

        ArrayList<CounterOfTrainings> countersOfSessions = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : countByCoaches.entrySet()) {
            CounterOfTrainings newCounter = new CounterOfTrainings(entry.getKey(), entry.getValue());
            countersOfSessions.add(newCounter);
        }
        Collections.sort(countersOfSessions);

        return countersOfSessions;
    }
}
