package com.pt2121.dbpop.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pt2121 on 7/28/14.
 * Generated by http://www.jsonschema2pojo.org
 */
public class Program {

    @SerializedName("ProgramId")
    @Expose
    private Integer programId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("MapCenter")
    @Expose
    private MapCenter mapCenter;

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MapCenter getMapCenter() {
        return mapCenter;
    }

    public void setMapCenter(MapCenter mapCenter) {
        this.mapCenter = mapCenter;
    }

}