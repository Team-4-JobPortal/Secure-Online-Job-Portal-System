package com.example.enums;

public enum ExperienceLevel {
    JUNIOR("0-5"),   // 0-5 years
    SENIOR("5-10"),  // 5-10 years
    LEAD("10+");     // 10+ years
     
    private String range;

    ExperienceLevel(String range) {
        this.range = range;
    }

    public String getRange() {
        return range;
    }
}
