package com.sid.demoapp.github;

import com.sid.demoapp.github.data.RepoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Okis on 2016.05.24 @ 14:25.
 */
public class GitHubService {
    private final String API_URL = "https://api.github.com/";
    private final String REPO_NAME = "rimasg";
    private final Callback<List<RepoData>> reposListCallback;

    public GitHubService(Callback<List<RepoData>> reposListCallback) {
        try {
            this.reposListCallback = reposListCallback;
        } catch (ClassCastException e) {
            throw new ClassCastException(reposListCallback.toString() + " must implement Callback listener");
        }
        getRepos();
    }

    public void getRepos() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GitHubAPI gitHubAPI = retrofit.create(GitHubAPI.class);
        final Call<List<RepoData>> call = gitHubAPI.listRepos(REPO_NAME);
        call.enqueue(reposListCallback);
    }

    public interface GitHubAPI {
        @GET("users/{user}/repos")
        Call<List<RepoData>> listRepos(@Path("user") String user);
    }
}
