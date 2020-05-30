package client.model;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    private String url;
    private HttpClient client;

    public Client() {
        this.url = "http://localhost:8001";
        client = new DefaultHttpClient();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<StudentInfo> getStudentInfo(Integer listId, int limit, int offset) throws IOException, InterruptedException {
        HttpResponse response = client.execute(new HttpGet(url + "/list/" + listId + "?limit=" + limit + "&offset=" + offset));
        return stringToArray(EntityUtils.toString(response.getEntity()), StudentInfo[].class);
    }

    public Integer getStudentInfoSize(Integer listId) throws IOException, InterruptedException {
        HttpResponse response = client.execute(new HttpGet(url + "/list/" + listId + "/size"));
        Gson gson = new Gson();
        return gson.fromJson(EntityUtils.toString(response.getEntity()), Integer.TYPE);
    }

    public Integer setStudentInfo(List<StudentInfo> infos) throws IOException {
        HttpPost post = new HttpPost(url + "/list");
        Gson gson = new Gson();
        StringEntity input = new StringEntity(gson.toJson(infos));
        post.setEntity(input);
        HttpResponse response = client.execute(post);
        return gson.fromJson(EntityUtils.toString(response.getEntity()), Integer.TYPE);
    }

    private static <T> ArrayList<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return new ArrayList<>(Arrays.asList(arr));
    }

    public void addStudentInfo(Integer listId, StudentInfo info) throws IOException {
        HttpPost post = new HttpPost(url + "/list/" + listId + "/student");
        Gson gson = new Gson();
        StringEntity input = new StringEntity(gson.toJson(info));
        post.setEntity(input);
        client.execute(post);
    }

    public Integer removeByNameAndGroup(Integer listId, String name, Integer group) throws IOException {
        return remove(url + "/list/" + listId + "?name" + name + "&group=" + group);
    }

    public Integer removeByNameAndTypeOfSkip(Integer listId, String name, String typeOfSkip) throws IOException {
        return remove(url + "/list/" + listId + "?name" + name + "&typeOfSkip=" + typeOfSkip);
    }

    public Integer removeByNameLowAndHighSkip(Integer listId, String name, Integer low, Integer high) throws IOException {
        return remove(url + "/list/" + listId + "?name" + name + "&low=" + low + "&high=" + high);
    }

    public List<StudentInfo> findByNameAndGroup(Integer listId, String name, Integer group) throws IOException {
        return find(url + "/list/" + listId + "?name" + name + "&group=" + group);
    }

    public List<StudentInfo> findByNameAndTypeOfSkip(Integer listId, String name, String typeOfSkip) throws IOException {
        return find(url + "/list/" + listId + "?name" + name + "&typeOfSkip=" + typeOfSkip);
    }

    public List<StudentInfo> findByNameLowAndHighSkip(Integer listId, String name, Integer low, Integer high) throws IOException {
        return find(url + "/list/" + listId + "?name" + name + "&low=" + low + "&high=" + high);
    }

    private Integer remove(String url) throws IOException {
        HttpDelete delete = new HttpDelete(url);
        HttpResponse response = client.execute(delete);
        Gson gson = new Gson();
        return gson.fromJson(EntityUtils.toString(response.getEntity()), Integer.TYPE);
    }

    private List<StudentInfo> find(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        Gson gson = new Gson();
        return stringToArray(EntityUtils.toString(response.getEntity()), StudentInfo[].class);
    }
}
