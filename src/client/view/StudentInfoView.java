package client.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentInfoView {
    private SimpleStringProperty name;
    private SimpleIntegerProperty group;
    private SimpleIntegerProperty byIllness;
    private SimpleIntegerProperty anotherReason;
    private SimpleIntegerProperty withoutReason;
    private SimpleIntegerProperty summary;

    public StudentInfoView(String name,
                           Integer group,
                           Integer byIllness,
                           Integer anotherReason,
                           Integer withoutReason) {
        this.name = new SimpleStringProperty(name);
        this.group = new SimpleIntegerProperty(group);
        this.byIllness = new SimpleIntegerProperty(byIllness);
        this.anotherReason = new SimpleIntegerProperty(anotherReason);
        this.withoutReason = new SimpleIntegerProperty(withoutReason);
        this.summary = new SimpleIntegerProperty(byIllness+anotherReason+withoutReason);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Integer getAnotherReason() {
        return anotherReason.get();
    }

    public Integer getByIllness() {
        return byIllness.get();
    }

    public Integer getGroup() {
        return group.get();
    }

    public Integer getWithoutReason() {
        return withoutReason.get();
    }

    public void setAnotherReason(int anotherReason) {
        this.anotherReason.set(anotherReason);
    }

    public void setByIllness(int byIllness) {
        this.byIllness.set(byIllness);
    }

    public void setGroup(int group) {
        this.group.set(group);
    }

    public void setWithoutReason(int withoutReason) {
        this.withoutReason.set(withoutReason);
    }

    public void setSummary(int summary) {
        this.summary.set(summary);
    }

    public Integer getSummary() {
        return getByIllness() + getWithoutReason() + getAnotherReason();
    }
}
