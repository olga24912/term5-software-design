package ru.spbau.mit;

import ru.spbau.mit.control.SwingGUIManager;

import java.io.IOException;

public class Main {
    private Main() {}

    public static void main(String[] args) throws IOException, InterruptedException {
        new SwingGUIManager();
    }
}
