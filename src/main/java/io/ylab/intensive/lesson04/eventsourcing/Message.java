package io.ylab.intensive.lesson04.eventsourcing;

public class Message {
    private Person person;
    private Event event;

    public Message(Person person, Event event) {
        this.person = person;
        this.event = event;
    }

    public Message() {
    }

    public Person getPerson() {
        return person;
    }

    public Event getEvent() {
        return event;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}