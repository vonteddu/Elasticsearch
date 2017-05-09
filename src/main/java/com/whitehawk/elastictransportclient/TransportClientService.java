package com.whitehawk.elastictransportclient;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.json.JSONObject;

import java.util.List;

/**
 * This interface provides services for the elasticsearch transport client
 */
public interface TransportClientService {

    //method for getting cluster health
    public ClusterHealthResponse getHealth();

    /**
     *   indexes the Json data to the salesforce
     *  @param index index of the elastic search
     *  @param type type(table in RDMS)
     *  @param  jsonObject jsonObject to be index
     */
    public void indexJsonData(String index, String type, JSONObject jsonObject);

    /**
     *   checks if a field value or column data presents or not
     *   returns boolean value by matching the field value to the value in elastic search
     *  @param index index of the elastic search
     *  @param type type(table in RDMS)
     *  @param  field field(table) of the data
     *  @param fieldValue field value (column data) to search for
     */
    public boolean isFieldValueExists(String index, String type, String field, String fieldValue);

    /**
     *   gets all data by mapping by field
     *   returns list of Json objects or documents matched by field name
     *  @param index index of the elastic search
     *  @param type the type or table name of the document
     *  @param field the column name or field name of the document
     */
    public List<JSONObject> getAllData(String index, String type, String field);

    /**
     *   gets the documents matched by the full text
     *   returns the list of Json objects matched by a string in a type
     *  @param index index of the elastic search
     *  @param type the type or table name of the document
     *  @param searchString the full text that is to be search
     */
    public List<JSONObject> fullTextSearch(String index, String type, String searchString);


    /**
     *   gets the documents matched by the full text of any types
     *    returns the list of Json objects matched by a string in a index in all types
     *  @param index index of the elastic search
     *  @param searchString the full text that is to be search
     */
    public List<JSONObject> fullTextSearch(String index, String searchString);

    /**
     *  fetches all data for a particluar type of field
     *  @param index index of the elastic search
     *  @param type the type or table name of the document
     *  @param fieldValue field name or column name
     */
    public List<JSONObject> getMatchSearchByFieldValue(String index, String type, String field, String fieldValue);

    /**
     *   fetches all the ids for a particular field
     *  @param index index of the elastic search
     *  @param type the type or table name of the document
     *  @param field field name or column name
     */
    public List<String> getAllIds(String index, String type, String field);

    /**
     *   deletes the document (records) by id
     *  @param index index of the elastic search
     *  @param type the type or table name of the document
     *  @param id id of the document or record
     */
    public void deleteDataById(String index, String type, String id);

    /**
     *   gets the document (records) by id
     *  @param index index of the elastic search
     *  @param type the type or table name of the document
     *  @param id id of the document or record
     */
    public List<JSONObject> getById(String index, String type, String id);

}
