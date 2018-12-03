package c.group24.localcommunityservices;

import java.util.Map;

public class Opportunity {
    public String title;
    public String contact;
    public String date;
    public String location;
    public String description;
    public String requirements;
    public Map<String, String> students;

    Opportunity(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Map<String, String> getStudents() {
        return students;
    }

    /*public void addStudents(Student students) {
        this.students.add(students);
    }*/
}
