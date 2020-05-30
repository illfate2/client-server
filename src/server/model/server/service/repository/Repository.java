package server.model.server.service.repository;

import server.model.SearchInfo;
import server.model.StudentInfo;

import java.io.IOException;
import java.util.List;

public class Repository {
    private Database db;

    public Repository() {
        db = new Database();
    }

    public List<StudentInfo> getStudentInfoList(Integer listId, Integer limit, Integer offset) {
        return db.getInfos(listId, limit, offset);
    }

    public Integer setInfos(List<StudentInfo> infos) {
        return db.setInfos(infos);
    }


    public Integer createEmptyList() {
        return db.createEmptyList();
    }

    public void insertInfo(Integer listId, StudentInfo info) {
        db.insertInfo(listId, info);
    }

    public Integer getSize(Integer listId) {
        return this.db.getSize(listId);
    }


    public List<StudentInfo> findByNameAndGroup(Integer listId, SearchInfo searchInfo) throws IOException {
        return db.findByNameAndGroup(listId, searchInfo);
    }

    public List<StudentInfo> findByNameLowAndHighSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        return db.findByNameLowAndHighSkip(listId, searchInfo);
    }

    public List<StudentInfo> findByNameAndTypeOfSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        return db.findByNameAndTypeOfSkip(listId, searchInfo);
    }

    public Integer removeByNameAndGroup(Integer listId, SearchInfo searchInfo) throws IOException {
        return db.removeByNameAndGroup(listId, searchInfo);
    }

    public Integer removeByNameLowAndHighSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        return db.removeByNameLowAndHighSkip(listId, searchInfo);
    }

    public Integer removeByNameAndTypeOfSkip(Integer listId, SearchInfo searchInfo) throws IOException {
        return db.removeByNameAndTypeOfSkip(listId, searchInfo);
    }

    public void removeInfos(Integer listId) {
        db.removeInfos(listId);
    }
}
