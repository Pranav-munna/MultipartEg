package com.twixttechnologies.multiparteg.Controller

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Pranav Ashok on 11/06/18.
 */
interface MultipartEgInterphase {

    /* @GET("aps_api/get_companydetails_arabic/{id}")
    fun getDetailsarab(@Path("id") id: String): Call<DetailsResponse>

    @FormUrlEncoded
    @POST("api/categories/7")
    fun getCategories(@Field("shopId") shopId: String,
                      @Query("page") page: Int): Call<Array<ResponceCategories>>
*/

    @Multipart
    @POST("machine_test/test/webservices/insert_documents.php")
    fun getstatus(@Part("title") shopId: String,
                  @Part("name") name: String,
                  @Part("type") type: String,
                  @Part imagePart: MultipartBody.Part): Call<Responce>


}