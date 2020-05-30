package server.model.server.service;

import server.model.SearchInfo;
import server.model.StudentInfo;
import server.model.server.service.repository.Repository;

import java.io.IOException;
import java.util.List;

public class Service {
    private Repository repository;

    public Service() {
        repository = new Repository();
    }

    public List<StudentInfo> getStudentInfoList(Integer listId, SearchInfo searchInfo) throws IOException {
        if (searchInfo.group != null) {
            return repository.findByNameAndGroup(listId, searchInfo);
        }
        if (searchInfo.lowAmountOfSkip != null && searchInfo.highAmountOfSkip != null) {
            return repository.findByNameLowAndHighSkip(listId, searchInfo);
        }
        if (searchInfo.typeOfSkip != null) {
            return repository.findByNameAndTypeOfSkip(listId, searchInfo);
        }
        return repository.getStudentInfoList(listId, searchInfo.limit, searchInfo.offset);
    }

    public Integer removeStudentInfo(Integer listId, SearchInfo searchInfo) throws IOException {
        if (searchInfo.group != null) {
            return repository.removeByNameAndGroup(listId, searchInfo);
        }
        if (searchInfo.lowAmountOfSkip != null && searchInfo.highAmountOfSkip != null) {
            return repository.removeByNameAndTypeOfSkip(listId, searchInfo);
        }
        if (searchInfo.typeOfSkip != null) {
            return repository.removeByNameLowAndHighSkip(listId, searchInfo);
        }
        return 0;
    }


    public Integer setInfos(List<StudentInfo> infos) {
        return repository.setInfos(infos);
    }


    public void insertInfo(Integer listId, StudentInfo info) {
        repository.insertInfo(listId, info);
    }

    public Integer getSize(Integer listId) {
        return this.repository.getSize(listId);
    }


    public void removeInfos(Integer listId) {
        repository.removeInfos(listId);
    }

    public Integer createEmptyList() {
        return repository.createEmptyList();
    }
}
