package org.zackratos.kanebo.eventBusHandle;


// EventBus通用通信信息
public class EventBusHandle {

    public final String message;

    public static EventBusHandle getInstance(String message) {
        return new EventBusHandle(message);
    }

    private EventBusHandle(String message) {
        this.message = message;
    }
}
