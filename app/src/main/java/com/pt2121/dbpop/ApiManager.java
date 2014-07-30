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
        final com.pt2121.dbpop.model.Program bikeChattanooga =
                new com.pt2121.dbpop.model.Program(0, "Bike Chattanooga",
                        Double.valueOf(35.0982955).floatValue(), Double.valueOf(-85.238691).floatValue(), -1,
                        "http://bikechattanooga.com/stations/json", null);

        final com.pt2121.dbpop.model.Program citiBike =
                new com.pt2121.dbpop.model.Program(0, "Citi Bike",
                        Double.valueOf(40.7056308).floatValue(), Double.valueOf(-73.9780035).floatValue(), -1,
                        "http://citibikenyc.com/stations/json", null);

        final com.pt2121.dbpop.model.Program divvy =
                new com.pt2121.dbpop.model.Program(0, "Divvy",
                        Double.valueOf(41.8337329).floatValue(), Double.valueOf(-87.7321555).floatValue(), -1,
                        "http://www.divvybikes.com/stations/json", null);

        final com.pt2121.dbpop.model.Program cogoBikeShare =
                new com.pt2121.dbpop.model.Program(0, "CoGo Bike Share",
                        Double.valueOf(39.9829515).floatValue(), Double.valueOf(-82.990829).floatValue(), -1,
                        "http://cogobikeshare.com/stations/json", null);

        final com.pt2121.dbpop.model.Program bayAreaBikeShare =
                new com.pt2121.dbpop.model.Program(0, "Bay Area Bike Share",
                        Double.valueOf(37.7577).floatValue(), Double.valueOf(-122.4376).floatValue(), -1,
                        "http://bayareabikeshare.com/stations/json", null);

        service.listPrograms()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Program>>() {
                    @Override
                    public void call(List<Program> programs) {

                        final DbAdapter dbAdapter = new DbAdapter(context);
                        dbAdapter.open();
                        Log.v(TAG, "opened");
                        dbAdapter.insert(citiBike);
                        dbAdapter.insert(bayAreaBikeShare);
                        dbAdapter.insert(divvy);
                        dbAdapter.insert(bikeChattanooga);
                        dbAdapter.insert(cogoBikeShare);

                        for (Program p : programs) {
                            dbAdapter.insert(
                                    new com.pt2121.dbpop.model.Program(0,
                                            p.getName(),
                                            p.getMapCenter().getLatitude().floatValue(),
                                            p.getMapCenter().getLongitude().floatValue(),
                                            p.getProgramId(),
                                            "https://publicapi.bcycle.com/api/1.0/ListProgramKiosks/" + p.getProgramId(),
                                            new Pair<String, String>("", ""))
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
