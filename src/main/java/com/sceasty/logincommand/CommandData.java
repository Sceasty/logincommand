package com.sceasty.logincommand;

public class CommandData {
    private String command;
    private boolean consoleCommand;
    private int delay;
    
    public CommandData(String command, boolean consoleCommand, int delay) {
        this.command = command;
        this.consoleCommand = consoleCommand;
        this.delay = delay;
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public boolean isConsoleCommand() {
        return consoleCommand;
    }
    
    public void setConsoleCommand(boolean consoleCommand) {
        this.consoleCommand = consoleCommand;
    }
    
    public int getDelay() {
        return delay;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    @Override
    public String toString() {
        return "CommandData{" +
                "command='" + command + '\'' +
                ", consoleCommand=" + consoleCommand +
                ", delay=" + delay +
                '}';
    }
}