package com.androfast.server.appgpsubicacion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androfast.server.appgpsubicacion.datos.Http;
import com.androfast.server.appgpsubicacion.negocio.Usuario;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IniciarSesion extends AppCompatActivity {

    TextView email, clave, errado,nuevoUsuario;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //Hooking the UI views for usage
        email = (TextView) findViewById(R.id.txtEmail);
        clave = (TextView) findViewById(R.id.txtClave);
        errado = (TextView) findViewById(R.id.txtError);
        login = (Button) findViewById(R.id.btnIniciarSesion);
       // nuevoUsuario= (TextView)findViewById(R.id.txtNuevoUsuario) ;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }

        });
       // minimizeApp();

    }
    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
    public void iniciarSesion() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(IniciarSesion.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Http.URL_WEB_SERVICE + "iniciar-sesion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Usuario user = new Usuario();
                        //JSONObject jsonObject = null;
                        try {
                            JSONObject objResultado = new JSONObject(response);

                            String estadox = objResultado.get("estado").toString();
                            if(!estadox.equalsIgnoreCase("exito")){
                                Toast.makeText(IniciarSesion.this, "Invalido username o password", Toast.LENGTH_LONG).show();

                            }else{

                                user.setCodigo(objResultado.getJSONObject("datos").optString("codigo"));
                                user.setEmail(objResultado.getJSONObject("datos").optString("email"));
                                user.setNombre(objResultado.getJSONObject("datos").optString("nombre"));
                                user.setTelefono(objResultado.getJSONObject("datos").optString("movil"));



                                Intent intent = new Intent(IniciarSesion.this, RegistrarGps.class);
                                intent.putExtra("DATA_USER",user);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errado.setText("Inténtalo de nuevo");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("clave", clave.getText().toString());
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir")
                    .setMessage("Estás seguro?")
                    .setNegativeButton(android.R.string.cancel, null)// sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
// Salir
                            IniciarSesion.this.finish();
                        }
                    })
                    .show();

// Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
// para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }
}
