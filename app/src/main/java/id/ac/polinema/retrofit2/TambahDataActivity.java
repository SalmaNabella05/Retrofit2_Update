package id.ac.polinema.retrofit2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahDataActivity extends AppCompatActivity {
    private EditText inputNama, inputAlamat, inputTelp;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahdata);

        //button tambah
        Button btn = findViewById(R.id.btn_tambah);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNama = findViewById(R.id.edt_nama);
                inputAlamat = findViewById(R.id.edt_alamat);
                inputTelp = findViewById(R.id.edt_telp);

                //ini berfungsi untuk mengambil data yang telah diinput tadi
                String nama = inputNama.getText().toString();
                String alamat = inputAlamat.getText().toString();
                radioGroup = findViewById(R.id.group_jk);
                RadioButton selected = findViewById(radioGroup.getCheckedRadioButtonId());
                String jenis_kelamin = selected.getText().toString();
                String no_telp = inputTelp.getText().toString();

                //ini merupakan error handling jika ada salah satu yang kosong
                if (TextUtils.isEmpty(nama) && TextUtils.isEmpty(alamat) && TextUtils.isEmpty(jenis_kelamin) && TextUtils.isEmpty(no_telp)) {
                    Toast.makeText(getApplicationContext(), "Fill the field", Toast.LENGTH_SHORT).show();
                } else {
                    //jika tidak ada yang kosong, maka akan dilakukan instansiasi retrofit ke base url rest server
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://nursalmanabella.000webhostapp.com/index.php/Mahasiswa/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    //mengisi parameter data yang telah diGetText tadi ke setPost
                    Call<ResponseBody> call = jsonPlaceHolderApi.setPost(
                            nama,
                            alamat,
                            jenis_kelamin,
                            no_telp
                    );

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        //jika android dapat berkomunikasi dengan rest server dan akan kembali ke mainActivity
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        //jika android gagal berkomunikasi dengan server
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
