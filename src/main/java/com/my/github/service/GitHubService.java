package com.my.github.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.github.OkHttpUtil;
import com.my.github.TransferRequest;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class GitHubService {
    private static final Set<String> unDealRepo = new HashSet<>();

    public Boolean listRepos(String username, String token) {
        // https://api.github.com/users/{username}/repos
        String URL = "https://api.github.com/users/" + username + "/repos";
        try (Response response = OkHttpUtil.request_Get(URL, token)) {
            if (response.code() == HttpStatus.OK.value()) {
                String responseStr = Objects.requireNonNull(response.body()).string();
                JsonArray repos = new Gson().fromJson(responseStr, JsonArray.class);
                for (JsonElement ele : repos) {
                    JsonObject jsonObj = new Gson().fromJson(ele, JsonObject.class);
                    String name = jsonObj.get("name").getAsString();
                    unDealRepo.add(name);
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void transfer(TransferRequest req, String token) {
        if (CollectionUtils.isEmpty(unDealRepo)) {
            for (String name : unDealRepo) {
                req.setRepo(name);
                // https://api.github.com/repos/{username}/{repo}/transfer
                String URL = "https://api.github.com/repos/" + req.getOwner() + "/" + req.getRepo() + "/transfer";
                try (Response response = OkHttpUtil.request_Post(URL, token, new Gson().toJson(req))) {
                    if (response.code() == HttpStatus.ACCEPTED.value() || response.code() == HttpStatus.OK.value()) {
                        unDealRepo.remove(name);
                    } else break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
