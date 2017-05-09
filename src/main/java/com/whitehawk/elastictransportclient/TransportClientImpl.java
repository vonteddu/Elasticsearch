package com.whitehawk.elastictransportclient;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides implementation for the elastic search transport client services
 */
public class TransportClientImpl extends com.whitehawk.elastictransportclient.TransportClient implements TransportClientService{

    private static Logger logger = Logger.getLogger(TransportClientImpl.class);

    public TransportClientImpl() throws IOException {

        super();

    }

    //method for getting cluster health
    public ClusterHealthResponse getHealth() {

        return this.client.admin().cluster().prepareHealth().get();
    }

    public void indexJsonData(String index, String type, JSONObject jsonObject) {

        this.client.prepareIndex(index, type).setSource(String.valueOf(jsonObject)).get();

    }

    public boolean isFieldValueExists(String index, String type, String field, String field_value) {

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery(field, field_value+".*"))
                .get();

        SearchHit[] str = response.getHits().getHits();

        if (str.length > 0)
            return true;

        return false;
    }

    //get all data by mapping by field
    public List<JSONObject> getAllData(String index, String type, String field) {

        List<JSONObject> list = new ArrayList<JSONObject>();

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.existsQuery(field))
                .setFrom(0).setSize(50).setExplain(true)
                .get();

        SearchHit[] str = response.getHits().getHits();

        for(SearchHit hit : str){

            JSONObject jsonObject = new JSONObject(hit.getSource());
            list.add(jsonObject);

        }

        return list;
    }

    //this method returns all ids by mapping through the field name
    public List<String> getAllIds(String index, String type, String field){

        List<String> list = new ArrayList();

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.existsQuery(field))
                .setFrom(0).setSize(50).setExplain(true)
                .get();

        SearchHit[] searchHits = response.getHits().getHits();

        for (SearchHit hit : searchHits){

            list.add(hit.getId());

        }

        return list;

    }

    //this method returns the possible matches by the field value, it returns the possible matches as a list of json objects
    public List<JSONObject> getMatchSearchByFieldValue(String index, String type, String field, String field_value) {

        List<JSONObject> list = new ArrayList<JSONObject>();

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery(field, field_value))
                .setFrom(0).setSize(50).setExplain(true)
                .get();

        SearchHit[] str = response.getHits().getHits();

        for(SearchHit hit : str){
            JSONObject jsonObject = new JSONObject(hit.getSource());
            list.add(jsonObject);

        }
        return list;
    }

    public void deleteDataById(String index, String type, String id) {

        client.prepareDelete(index, type, id).get();

    }

    public List<JSONObject> getById(String index, String type, String id) {

        List<JSONObject> jsonList = new ArrayList<JSONObject>();

        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add(index, type, id)
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                JSONObject object = new JSONObject(json);
                jsonList.add(object);
            }
        }
        return jsonList;
    }

    public List<JSONObject> fullTextSearch(String index, String type, String searchString) {
        return null;
    }

    public List<JSONObject> fullTextSearch(String index, String searchString) {
        return null;
    }
}
