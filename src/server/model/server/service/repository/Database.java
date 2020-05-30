package server.model.server.service.repository;

import server.model.SearchInfo;
import server.model.StudentInfo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    private Map<Integer, List<StudentInfo>> infos;
    private AtomicInteger atomicInt;

    public Database() {
        infos = new ConcurrentHashMap<>();
        atomicInt = new AtomicInteger(-1);
        createEmptyList();
    }

    public Integer setInfos(List<StudentInfo> infos) {
        int id = atomicInt.incrementAndGet();
        this.infos.put(id, infos);
        return id;
    }


    public void insertInfo(Integer listId, StudentInfo info) {
        List<StudentInfo> list = infos.get(listId);
        list.add(info);
        this.infos.put(listId, list);
    }

    public Integer createEmptyList() {
        int id = atomicInt.incrementAndGet();
        this.infos.put(id, new ArrayList<StudentInfo>());
        return id;
    }

    public List<StudentInfo> findByNameAndGroup(Integer listId, SearchInfo searchInfo) throws IOException {
        List<StudentInfo> infos = this.infos.get(listId);
        List<StudentInfo> result = new ArrayList<>();
        for (StudentInfo student :
                infos) {
            if (student.getName().equals(searchInfo.name) && student.getGroup().equals(searchInfo.group)) {
                result.add(student);
            }
        }
        return getFiltered(searchInfo.limit, searchInfo.offset, result);
    }

    public List<StudentInfo> findByNameLowAndHighSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        List<StudentInfo> infos = this.infos.get(listId);
        List<StudentInfo> result = new ArrayList<>();
        for (StudentInfo student :
                infos) {
            if (student.getName().equals(searchInfo.name) && student.getSummary() < searchInfo.highAmountOfSkip &&
                    student.getSummary() > searchInfo.lowAmountOfSkip) {
                result.add(student);
            }
        }
        return getFiltered(searchInfo.limit, searchInfo.offset, result);
    }

    public List<StudentInfo> findByNameAndTypeOfSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        List<StudentInfo> infos = this.infos.get(listId);
        List<StudentInfo> result = new ArrayList<>();
        for (StudentInfo student :
                infos) {
            if (student.getName().equals(searchInfo.name) && student.getSummary() < searchInfo.highAmountOfSkip &&
                    student.getSummary() > searchInfo.lowAmountOfSkip) {
                switch (searchInfo.typeOfSkip) {
                    case "illness":
                        if (student.getByIllness() > 0) {
                            result.add(student);
                        }
                        break;
                    case "without reason":
                        if (student.getWithoutReason() > 0) {
                            result.add(student);
                        }
                        break;
                    case "another reason":
                        if (student.getAnotherReason() > 0) {
                            result.add(student);
                        }
                        break;
                }
            }
        }
        return getFiltered(searchInfo.limit, searchInfo.offset, result);
    }

    public Integer removeByNameAndGroup(Integer listId, SearchInfo searchInfo) throws IOException {
        int counter = 0;
        for (Iterator<StudentInfo> it = infos.get(listId).iterator(); it.hasNext(); ) {
            StudentInfo student = it.next();
            if (student.getName().equals(searchInfo.name) && student.getGroup().equals(searchInfo.group)) {
                it.remove();
                counter++;
            }
        }
        return counter;
    }

    public Integer removeByNameLowAndHighSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        Integer counter = 0;
        for (Iterator<StudentInfo> it = infos.get(listId).iterator(); it.hasNext(); ) {
            StudentInfo student = it.next();
            if (student.getName().equals(searchInfo.name) &&
                    student.getSummary() < searchInfo.highAmountOfSkip &&
                    student.getSummary() > searchInfo.lowAmountOfSkip) {
                it.remove();
                counter++;
            }
        }
        return counter;
    }

    public Integer removeByNameAndTypeOfSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        int counter = 0;
        for (Iterator<StudentInfo> it = infos.get(listId).iterator(); it.hasNext(); ) {
            StudentInfo student = it.next();
            if (student.getName().equals(searchInfo.name)) {
                switch (searchInfo.typeOfSkip) {
                    case "illness":
                        if (student.getByIllness() > 0) {
                            it.remove();
                            counter++;
                        }
                        break;
                    case "without reason":
                        if (student.getWithoutReason() > 0) {
                            it.remove();
                            counter++;
                        }
                        break;
                    case "another reason":
                        if (student.getAnotherReason() > 0) {
                            it.remove();
                            counter++;
                        }
                        break;
                }
            }
        }
        return counter;
    }


    public void removeInfos(Integer listId) {
        this.infos.remove(listId);
    }

    public List<StudentInfo> getInfos(Integer listId, Integer limit, Integer offset) {
        return getFiltered(limit, offset, this.infos.get(listId));
    }

    private List<StudentInfo> getFiltered(Integer limit, Integer offset, List<StudentInfo> studentInfo) {
        if ((limit + offset) < studentInfo.size()) {
            return studentInfo.subList(offset, offset + limit);
        }
        if (studentInfo.size() == 0) {
            return studentInfo;
        }
        if (offset < studentInfo.size()) {
            return studentInfo.subList(offset, studentInfo.size());
        }
        return studentInfo.subList(studentInfo.size() - 1, studentInfo.size() - 1);
    }

    public Integer getSize(Integer listId) {
        return this.infos.get(listId).size();
    }
}
