package ai.ia.agh.edu.pl.workshop.incprofs.sensors;

import android.net.Uri;

/**
 * Created by Rael on 21.01.2016.
 */
public class SensorQuery {

    //Pola
    private Uri sensorURI;
    private String[] tableColumns;
    private String whereCondition;
    private String[] whereArguments;
    private String orderBy;

    //Metody

    public SensorQuery(Uri sensorURI, String[] tableColumns, String whereCondition, String[] whereArguments, String orderBy) {
        this.sensorURI = sensorURI;
        this.tableColumns = tableColumns;
        this.whereCondition = whereCondition;
        this.whereArguments = whereArguments;
        this.orderBy = orderBy;
    }

    public SensorQuery() {
        this.sensorURI = null;
        this.tableColumns = null;
        this.whereCondition = null;
        this.whereArguments = null;
        this.orderBy = null;
    }

    public Uri getSensorURI() {
        return sensorURI;
    }

    public void setSensorURI(Uri sensorURI) {
        this.sensorURI = sensorURI;
    }

    public String[] getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(String[] tableColumns) {
        this.tableColumns = tableColumns;
    }

    public String getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(String whereCondition) {
        this.whereCondition = whereCondition;
    }

    public String[] getWhereArguments() {
        return whereArguments;
    }

    public void setWhereArguments(String[] whereArguments) {
        this.whereArguments = whereArguments;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


}
