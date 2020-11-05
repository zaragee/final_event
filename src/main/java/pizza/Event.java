package pizza;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Event_table")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String eventStatus="Waiting";
    private String eventKind;
    private Long giftId;

    @PostPersist
    public void onPostPersist(){
        EventStarted eventStarted = new EventStarted();
        BeanUtils.copyProperties(this, eventStarted);
        eventStarted.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        pizza.external.Delivery delivery = new pizza.external.Delivery();
        delivery.setEventId(this.getId());
        delivery.setEventStatus("EventStarted");
        delivery.setDeliveryStatus("Delivered");
        delivery.setOrderId(Long.valueOf(10));
        // mappings goes here
        EventApplication.applicationContext.getBean(pizza.external.DeliveryService.class)
            .addGift(delivery);


    }

    @PostUpdate
    public void onPostUpdate(){
        StoppedEvent stoppedEvent = new StoppedEvent();
        BeanUtils.copyProperties(this, stoppedEvent);
        stoppedEvent.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
    public String getEventKind() {
        return eventKind;
    }

    public void setEventKind(String eventKind) {
        this.eventKind = eventKind;
    }
    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }




}
