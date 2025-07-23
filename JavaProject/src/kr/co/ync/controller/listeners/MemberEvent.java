package kr.co.ync.controller.listeners;

import kr.co.ync.controller.enums.MemberEventType;

import java.util.EventObject;

public class MemberEvent<T> extends EventObject {
    private MemberEventType type;

    public MemberEvent(T source, MemberEventType type) {
        super(source);
        this.type = type;
    }

    public T getSource() {
        return (T) super.getSource();
    }

    public MemberEventType getType() {
        return type;
    }
}
