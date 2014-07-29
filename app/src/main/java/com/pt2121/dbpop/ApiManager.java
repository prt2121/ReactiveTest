package com.pt2121.dbpop;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.pt2121.dbpop.db.DbAdapter;
import com.pt2121.dbpop.model.json.Program;

import java.util.List;

import retrofit.RestAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pt2121 on 7/28/14.
 */
public class ApiManager {
    private static final String TAG = ApiManager.class.getSimpleName();
    private final RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("https://publicapi.bcycle.com/api/1.0/ListPrograms")
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();
    private final WebService service = restAdapter.create(WebService.class);

    public void getAllPrograms(final Context context) {
        service.listPrograms()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Program>>() {
                    @Override
                    public void call(List<Program> programs) {

                        final DbAdapter dbAdapter = new DbAdapter(context);
                        dbAdapter.open();
                        Log.v(TAG, "opened");
                        for (Program p : programs) {
                            dbAdapter.insert(
                                    new com.pt2121.dbpop.model.Program(0,
                                            p.getName(),
                                            p.getMapCenter().getLatitude().floatValue(),
                                            p.getMapCenter().getLongitude().floatValue(),
                                            p.getProgramId(),
                                            "https://publicapi.bcycle.com/api/1.0/ListProgramKiosks/" + p.getProgramId(),
                                            new Pair<String, String>("ApiKey", "")) // TODO add key
                            );
                        }
                        dbAdapter.close();
                        Log.v(TAG, "closed");
                    }
                });

                /*
                // TODO
                service.listPrograms()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(func)
                .subscribe(new Action1<MapCenter>() {
                    @Override
                    public void call(MapCenter mapCenter) {
                        Log.v(TAG, "lat : " + mapCenter.getLatitude());
                        Log.v(TAG, "lng : " + mapCenter.getLongitude());
                    }
                });
                */
    }

    /*
    private Func1<List<Program>, Observable<MapCenter>> func = new Func1<List<Program>, Observable<MapCenter>>() {
        @Override
        public Observable<MapCenter> call(List<Program> programs) {
            for(Program program : programs)
                Log.v(TAG, program.getName());
            return Observable.from(programs.get(0).getMapCenter());
        }
    };
    */


}
