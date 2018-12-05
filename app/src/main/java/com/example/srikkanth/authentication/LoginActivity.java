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

public class LoginActivity extends AppCompatActivity {
    private EditText EmailEditText,PasswordEditText;
    private Button LoginButton;
    private String Email,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EmailEditText=findViewById(R.id.emaillogin_edit_text);
        PasswordEditText=findViewById(R.id.passwordlogin_edit_text);
        LoginButton=findViewById(R.id.login_button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=EmailEditText.getText().toString();
                Password=PasswordEditText.getText().toString();
                try {
                    VolleyPost("Login");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        Log.e("Response", response);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getCause().toString(),Toast.LENGTH_LONG).show();
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
        jsonObject.put("Email",Email);
        jsonObject.put("Password",Password);
        return jsonObject;
    }
}
