package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable {
    private Coach coach;
    private Integer counter;

    public CounterOfTrainings(Coach coach, Integer counter) {
        this.coach = coach;
        this.counter = counter;
    }

    public Coach getCoach() {
        return coach;
    }

    @Override
    public int compareTo(Object o) {
        if (this.getClass() != o.getClass()) {
            return 0;
        }
        CounterOfTrainings anotherCounter = (CounterOfTrainings) o;
        return anotherCounter.counter - this.counter;
    }
}
