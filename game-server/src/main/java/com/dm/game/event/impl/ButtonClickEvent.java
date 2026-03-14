package com.dm.game.event.impl;

import com.dm.game.event.Event;

public class ButtonClickEvent implements Event {

    private final int button;

    public ButtonClickEvent(int button) {
        this.button = button;
    }

    public int getButton() {
        return button;
    }

}
