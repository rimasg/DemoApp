package com.sid.demoapp.github;

import com.sid.demoapp.github.data.RepoData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Okis on 2016.05.24 @ 14:25.
 */
public class GitHubService {
    private final String API_URL = "https://api.github.com/";
    private final String REPO_NAME = "rimasg";

    @Inject
    public GitHubService() { }

    public Observable<List<RepoData>> getRepos() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        final GitHubAPI gitHubAPI = retrofit.create(GitHubAPI.class);
        return gitHubAPI.listRepos(REPO_NAME)
                .retry(2)
                .timeout(5, TimeUnit.SECONDS)
                .cache();
    }

    public interface GitHubAPI {
        @GET("users/{user}/repos")
        Observable<List<RepoData>> listRepos(@Path("user") String user);
    }
}
