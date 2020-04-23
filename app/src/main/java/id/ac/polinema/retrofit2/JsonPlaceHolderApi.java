package id.ac.polinema.retrofit2;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET ("mahasiswa")
    Call<List<Post>> getPost();

    @FormUrlEncoded
    @POST("mahasiswa")
    Call<ResponseBody> setPost(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("no_telp") String no_telp
    );

    //untuk mengambil data berdasarkan id
//    @GET ("mahasiswa")
//    Call<List<Post>> getPostById(
//            @Query("id_siswa") String id_siswa
//    );
}
