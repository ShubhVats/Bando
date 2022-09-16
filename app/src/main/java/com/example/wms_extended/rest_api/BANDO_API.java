package com.example.wms_extended.rest_api;

import com.example.wms_extended.models.DispatchReq_Model;
import com.example.wms_extended.models.InventoryItem_Model;
import com.example.wms_extended.models.LocationItem_Model;
import com.example.wms_extended.models.LocationItem_Response_Model;
import com.example.wms_extended.models.Lot_Model;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface BANDO_API {

    @POST("Token/mobile-login")
    Call<JsonObject> Login(@Body RequestBody body);

    @GET
    Call<InventoryItem_Model> getInvnetory(@Url String code);

    @PUT("inventory/bando/store-item/{pallet}/{reasone2use}")
    Call<JsonObject> storeMaterial(@Path("pallet") String pallet, @Path("reasone2use") String reasone2use, @Body LocationItem_Model body);

    @GET
    Call<JsonArray> reItems(@Url String code);

    @GET
    Call<JsonArray> getInventoryList(@Url String code);

    @POST("/api/RmRequestItemes/bando/create-request")
    Call<JsonObject> createRequest(@Body RequestBody body);

    @GET
    Call<JsonObject> getJob(@Url String code);

    @PUT("rmrequest-picklist2/bando/pick-pallet/{order_number}/{pallet_number}")
    Call<JsonObject> pickMaterial(@Path("order_number") String order_number, @Path("pallet_number") String pallet_number);

    @POST("/api/rmrequest-picklist2/bando/handoverbin/{order_number}/{pallet_number}")
    Call<JsonObject> handOverBin(@Path("order_number") String order_number, @Path("pallet_number") String pallet_number, @Body String[] body);

    @POST("/api/rmrequest-picklist2/bando/quality_hold_by_production")
    Call<JsonObject> holdBin(@Body RequestBody body);

    @GET
    Call<Lot_Model> getLot(@Url String code);

    @PUT("belt_section/store_belt/{lot_number}")
    Call<JsonObject> storeBelt(@Path("lot_number") String lot_number,@Body RequestBody body);

    @PUT("belt_section/retrieve_belt/{tie_number}")
    Call<JsonObject> retriveBelt(@Path("tie_number") String tie_number,@Body RequestBody body);

    @POST("belt_section/create_dispatch_request/{userid}/{navision_barcode}")
    Call<DispatchReq_Model> dispatchCheck(@Path("userid") String userid, @Path("navision_barcode") String navision_barcode);

    @PUT("belt_section/dispatch_request_collect_box/{userid}/{request_no}/{box_barcode}")
    Call<JsonObject> checkPallet(@Path("userid") String userid,@Path("request_no") String request_no,@Path("box_barcode") String box_barcode);

    @GET
    Call<LocationItem_Response_Model> getEligible(@Url String code);


}
