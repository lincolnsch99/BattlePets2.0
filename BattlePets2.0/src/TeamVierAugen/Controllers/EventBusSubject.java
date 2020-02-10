/**
 * This is the subject class for event bus. Each player
 * It has reference to all player.
 */
package TeamVierAugen.Controllers;

import TeamVierAugen.BaseEvent;
import TeamVierAugen.Playables.Observer;

import java.util.ArrayList;
import java.util.List;

public class EventBusSubject {
    private BaseEvent event;

    private List<Observer> observerList;

    /**
     * Constructs EventBusSubject class.
     */
    public EventBusSubject(){
        observerList=new ArrayList<Observer>();
    }

    /**
     * Add the observer into the list.
     * @param observer
     */
    public void addObserver(Observer observer){

        this.observerList.add(observer);
    }

    /**
     * Remove the observer from the list.
     * @param observer
     */
    public void removeObserver(Observer observer){

        this.observerList.remove(observer);
    }

    /**
     * Notify all observers to update their event state.
     */
    public void notifyObservers()
    {
        for(int i=0; i<observerList.size();i++)
            observerList.get(i).update(event);
    }

    /**
     * Sets the event state
     * @param event the new event
     */
    public void setEvent(BaseEvent event) {
        this.event = event;
        notifyObservers();
    }
}
