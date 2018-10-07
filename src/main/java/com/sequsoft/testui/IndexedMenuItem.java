package com.sequsoft.testui;

import javafx.scene.control.MenuItem;

public class IndexedMenuItem extends MenuItem {

    public IndexedMenuItem(String id) {
        super(id);
        setId(id);
    }
}
