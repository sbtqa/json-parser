package ru.sbtqa.tag.parsers;

public class TestPullRequest {
    
    public void thrower() {
        try {
            int i = 19;
            System.out.println(i);
        } catch (Exception e) {
            System.out.println("blep");
        }
    }
}
