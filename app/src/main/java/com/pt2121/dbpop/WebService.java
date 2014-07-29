package com.pt2121.dbpop;

import com.pt2121.dbpop.model.json.Program;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by pt2121 on 7/28/14.
 */
public interface WebService {
    @Headers("ApiKey: ") // TODO add key
    @GET("/ListPrograms")
    Observable<List<Program>> listPrograms();

    @Headers("ApiKey: ") // TODO add key
    @GET("/ListPrograms")
    List<Program> programs();
}
