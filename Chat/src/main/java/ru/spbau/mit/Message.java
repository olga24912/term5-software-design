package ru.spbau.mit;

public class Message {
  private String name = "";
  private String msg = "";

  public Message(String name, String msg) {
    this.name = name;
    this.msg = msg;
  }

  public String getName() {
    return name;
  }

  public String getMsg() {
    return msg;
  }
}
