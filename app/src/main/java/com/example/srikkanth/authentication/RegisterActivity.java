package com.example.srikkanth.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegisterActivity extends AppCompatActivity {
    private EditText SellerIdEditText,NameEditText,MobileEditText,AltMobileEditText,EmailEditText,PasswordEditText,AddressEditText,AadharEditText;
    private Button RegisterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    VolleyPost("Register");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void init(){
        SellerIdEditText=findViewById(R.id.SellerId_edit_text);
        NameEditText=findViewById(R.id.Name_edit_text);
        MobileEditText=findViewById(R.id.Mobile_edit_text);
        AltMobileEditText=findViewById(R.id.altmob_edit_text);
        EmailEditText=findViewById(R.id.Email_edit_text);
        PasswordEditText=findViewById(R.id.Password_edit_text);
        AddressEditText=findViewById(R.id.Address_edit_text);
        AadharEditText=findViewById(R.id.Aadhar_edit_text);
        RegisterButton=findViewById(R.id.registr_button);
    }
    public void VolleyPost(String action) throws JSONException {
        Log.e("Action",action);
        String ip_address=getString(R.string.Ip_address);
        String Url="http://"+ip_address+"/Email.php";
        JSONObject jsonObject=new JSONObject();
        jsonObject=populate(action);
        final String requestBody=jsonObject.toString();
        StringRequest postRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Hi","Hi");
                        Log.e("Response", response);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);

    }
    public JSONObject populate(String action) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Action",action);
        jsonObject.put("SellerId",SellerIdEditText.getText().toString());
        jsonObject.put("Name",NameEditText.getText().toString());
        jsonObject.put("Mobile",MobileEditText.getText().toString());
        jsonObject.put("Alternate Mobile",AltMobileEditText.getText().toString());
        jsonObject.put("Email",EmailEditText.getText().toString());
        jsonObject.put("Password",PasswordEditText.getText().toString());
        jsonObject.put("Address",AddressEditText.getText().toString());
        jsonObject.put("Aadhar",AadharEditText.getText().toString());
        return jsonObject;
    }
}
