package com.example.gohike;

import java.util.List;

public class data_class_namateam {
    private String teamName;
    private List<String> teamMembers;

    // Empty constructor needed for Firestore
    public data_class_namateam() {}

    public data_class_namateam(String teamName, List<String> teamMembers) {
        this.teamName = teamName;
        this.teamMembers = teamMembers;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
