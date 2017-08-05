package id.co.blogspot.tutor93.bakingapps.data.network;

import id.co.blogspot.tutor93.bakingapps.data.network.response.BakingResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<BakingResponse> getRecipeDatas();
}


