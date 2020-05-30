package client.model;

import java.io.IOException;
import java.util.*;

public class Model {
    private Client client;
    private Map<Integer, String> listOfStudents;
    private Integer curList;

    public Model() {
        client = new Client();
        listOfStudents = new HashMap<>();
        listOfStudents.put(0, "Default");
        curList = 0;
    }

    public void addStudentInfo(StudentInfo info) throws IOException {
        client.addStudentInfo(curList, info);
    }

    public Integer getStudentInfoViewsSize() throws IOException, InterruptedException {
        return client.getStudentInfoSize(curList);
    }

    public List<StudentInfo> getStudentInfos(int limit, int offset) throws IOException, InterruptedException {
        return client.getStudentInfo(curList, limit, offset);
    }

    public void setStudentInfoViews(ArrayList<StudentInfo> studentInfoViews, String name) throws IOException {
        Integer id = client.setStudentInfo(studentInfoViews);
        listOfStudents.put(id, name);
        curList = id;
    }

    public Integer removeByNameAndGroup(String name, Integer group) throws IOException {
        return client.removeByNameAndGroup(curList, name, group);
    }

    public Integer removeByNameAndTypeOfSkip(String name, String typeOfSkip) throws IOException {
        return client.removeByNameAndTypeOfSkip(curList, name, typeOfSkip);

    }

    public Integer removeByNameLowAndHighSkip(String name, Integer low, Integer high) throws IOException {
        return client.removeByNameLowAndHighSkip(curList, name, low, high);

    }

    public List<StudentInfo> searchByNameHighAndLowAmountOfSkip(String name, Integer low, Integer high) throws IOException {
        return client.findByNameLowAndHighSkip(curList, name, low, high);
    }

    public List<StudentInfo> searchByNameAndGroup(String name, Integer group) throws IOException {
        return client.findByNameAndGroup(curList, name, group);
    }

    public List<StudentInfo> searchByNameAndTypeOfSkip(String name, String typeOfSkip) throws IOException {
        return client.findByNameAndTypeOfSkip(curList, name, typeOfSkip);
    }
}

