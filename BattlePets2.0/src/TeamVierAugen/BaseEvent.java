/**
 * This is the parent class for all event classes. Each event class
 * has an associated event type.
 */

package TeamVierAugen;

public abstract class BaseEvent {
    private EventTypes eventType;

    /**
     * This is a constructor that sets the event type.
     * @param eventType type of event
     */
    protected BaseEvent(EventTypes eventType)
    {
        this.eventType = eventType;
    }

    /**
     * This method returns the event type.
     * @return type of event
     */
    public EventTypes getEventType() {
        return eventType;
    }

    /**
     * This method checks if the event is equal to another object.
     * @param object object to compare to
     * @return true if objects are equal, false otherwise
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        BaseEvent baseEvent = (BaseEvent) object;
        return eventType == baseEvent.eventType;
    }

    /**
     * This method returns the unique hashcode of the event.
     * @return hashcode
     */
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), eventType);
    }

    /**
     * This method returns information about the event in String form.
     * @return event in String form
     */
    @java.lang.Override
    public java.lang.String toString() {
        return "BaseEvent{" +
                "eventType=" + eventType +
                '}';
    }
}
