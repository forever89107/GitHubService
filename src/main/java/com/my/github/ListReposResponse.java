package com.my.github;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListReposResponse {
    private long id;
    private String node_id;
    private String name;
    private String full_name;
}
