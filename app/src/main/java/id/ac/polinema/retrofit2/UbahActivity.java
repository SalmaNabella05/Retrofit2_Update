package id.ac.polinema.retrofit2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UbahActivity extends AppCompatActivity {
    //Deklarasikan komponen yang ada pada layout activity edit
    private TextView TextId;
    private EditText EditNama, EditAlamat, EditTelp;
    private RadioGroup GroupJk;
    private RadioButton perempuan, laki;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //dilakukan proses findViewById untuk form pada activity_edit
        TextId = findViewById(R.id.txt_id);
        EditNama = findViewById(R.id.edt_nama_1);
        EditAlamat = findViewById(R.id.edt_alamat_1);
        GroupJk = findViewById(R.id.group_jk_1);
        perempuan = findViewById(R.id.btn_perempuan_1);
        laki = findViewById(R.id.btn_laki_1);
        EditTelp = findViewById(R.id.edt_telp_1);

        //mengambil data yang didapatkan dari intent
        TextId.setText(getIntent().getStringExtra("id_siswa"));
        EditNama.setText(getIntent().getStringExtra("nama"));
        EditAlamat.setText(getIntent().getStringExtra("alamat"));
        if (getIntent().getStringExtra("jenis_kelamin").equals("Perempuan")) {
            perempuan.setChecked(true);
        } else {
            laki.setChecked(true);
        }
        EditTelp.setText(getIntent().getStringExtra("no_telp"));

        //kemudian terdapat onclicklistener jika button edit ditekan
        edit = findViewById(R.id.btn_edit_data);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mendapatkan data yang diisi lewat form
                String id = TextId.getText().toString();
                String nama = EditNama.getText().toString();
                String alamat = EditAlamat.getText().toString();
                RadioButton selected = findViewById(GroupJk.getCheckedRadioButtonId());
                String jenis_kelamin = "";
                if (selected != null) {
                    jenis_kelamin = selected.getText().toString();
                }
                String no_telp = EditTelp.getText().toString();

                //error handling, jika data yang diisi kosong
                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(alamat) || TextUtils.isEmpty(jenis_kelamin) || TextUtils.isEmpty(no_telp)) {
                    Toast.makeText(getApplicationContext(), "Pastikan telah diisi semua", Toast.LENGTH_SHORT).show();
                } else {
                    //instansiasi object dari konstruktor class Post yang tadi pada jsonplaceholderapi
                    Post post = new Post(id, nama, alamat, jenis_kelamin, no_telp);

                    //jika tidak kosong, maka retrofit akan diinstansiasi ke base url
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.100.244/server_mobile/index.php/mahasiswa/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    //kemudian memanggil method editPost dengan parameter objek
                    Call<ResponseBody> call = jsonPlaceHolderApi.editPost(post);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        // jika proses update berhasil
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Berhasil dong", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        //jika proses update gagal
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Yah Gagal :(", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
