package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 6/6/16.
 */
public class SomeLogik {

    View view;

    public SomeLogik(View view) {
        this.view = view;
    }

    public void fire() {
        view.write("someAction");
        view.write("введите:\n1 - для фейерверка\n2 - для душа\n3 - для выхода");
    }
}
