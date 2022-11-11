import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.github.OkHttpUtil;
import okhttp3.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.*;

public class Test1 {


    @Test
    public void tttt() {
        Set<String> unDealRepo = new HashSet<>();
        Set<String> doneRepo = new LinkedHashSet<>();
        String username = "";
        String token = "";

        // https://api.github.com/users/{username}/repos
        String URL = "https://api.github.com/users/" + username + "/repos";
        try (Response response = OkHttpUtil.request_Get(URL, token)) {
            if (response.code() == HttpStatus.OK.value()) {
                String responseStr = Objects.requireNonNull(response.body()).string();
                JsonArray repos = new Gson().fromJson(responseStr, JsonArray.class);
                for (JsonElement ele : repos) {
                    JsonObject jsonObj = new Gson().fromJson(ele, JsonObject.class);
                    String name = jsonObj.get("name").getAsString();
                    boolean visibility = jsonObj.get("private").getAsBoolean();
                    if (!visibility)
                        unDealRepo.add(name);
                    if (unDealRepo.size() >= 5) break;

                }
                System.out.println("Repo：" + unDealRepo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
