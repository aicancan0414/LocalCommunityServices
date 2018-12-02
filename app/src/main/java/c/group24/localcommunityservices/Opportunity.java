package c.group24.localcommunityservices;

import java.util.Map;

public class Opportunity {
    public String contact;
    public String description;
    public Map<String,String> students;

    Opportunity(){}

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getStudents() {
        return students;
    }

    public void setStudents(Map<String, String> students) {
        this.students = students;
    }
}
