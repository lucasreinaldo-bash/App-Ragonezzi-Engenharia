package vostore.orcamento.ragonezi.app.Orcamento;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.multidex.MultiDex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ncapdevi.fragnav.FragNavController;
import com.nmaltais.calcdialog.CalcDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import vostore.orcamento.ragonezi.app.CastLayout;
import vostore.orcamento.ragonezi.app.CriarPdf;
import vostore.orcamento.ragonezi.app.Firebase.ConfiguracaoFirebase;
import vostore.orcamento.ragonezi.app.Firebase.ValoresCompartilhados;
import vostore.orcamento.ragonezi.app.PermissionsChecker;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import vostore.apporcamentoragonezi.R;



public class Main2Activity extends CastLayout implements GoogleApiClient.OnConnectionFailedListener {


    private Button btnAjustar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demolicao);
        MultiDex.install(this);

        CastLayout castLayout = new CastLayout();
        castLayout.casts();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance();

        value = null;

        // Create the dialog with a request code to identify it in callback
        final CalcDialog calcDialog = CalcDialog.newInstance(CALC_REQUEST_CODE);
        //Botons do "Bottom"

        btnART = findViewById(R.id.btnART);
        btnDemolicao = findViewById(R.id.btnDemolicao);
        btnRevestimento = findViewById(R.id.btnRevestimento);
        btnHidraulica = findViewById(R.id.btnHidraulica);
        btnCalculadora = findViewById(R.id.btnCalculadora);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        btnPintura = findViewById(R.id.btnPintura);
        btnCam = findViewById(R.id.button7);
        btnAjustar = findViewById(R.id.btnAjustar);

        //Casts nos Lineares
        linearART = findViewById(R.id.linearART);
        linearDemolicao = findViewById(R.id.linearDemolition);
        linearRevestimento = findViewById(R.id.linearRevestimento);
        linearHidraulica = findViewById(R.id.linearHidraulica);
        linearPintura = findViewById(R.id.linearPintura);
        exibirNota = findViewById(R.id.exibirNota);
        exibirValorNota = findViewById(R.id.textView91);

        //vostore.orcamento.ragonezi.Permissoes.Firebase


        // use Shared preferences to save the best score
        //SharedPreferences mypref = getPreferences(MODE_PRIVATE);
        //SharedPreferences mypref2 = getPreferences(MODE_PRIVATE);
        isAccountValid();
        carregarValores();



        btnAjustar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Ajustes.class);
                startActivity(intent);
                finish();
            }
        });



        mContext = getApplicationContext();


        checker = new PermissionsChecker(this);



        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, camera.class);
                startActivity(intent);
                onPause();

            }
        });

        btnDemolicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearDemolicao();
                valorParcial();
            }
        });
        btnPintura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearPintura();
                valorParcial();
            }
        });

        btnART.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearART();
                valorParcial();
            }
        });
        btnRevestimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearRevestimento();
                valorParcial();
            }
        });

        btnHidraulica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearHidraulica();
                valorParcial();
            }
        });


        btnCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valorParcial();
                calcDialog.setValue(value);
                calcDialog.show(getSupportFragmentManager(), "calc_dialog");

            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main2Activity.this, "Botao Finalizar", Toast.LENGTH_SHORT).show();
            }
        });

        //Exibir linear de acordo com a seleção do checkbox


        checkBoxCozinha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout1.setVisibility(View.VISIBLE);
                    checkBoxCozinha.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxCozinha.setTextColor(Color.parseColor("#ffffff"));

                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout1.setVisibility(View.GONE);
                    checkBoxCozinha.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxBanheiroSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout2.setVisibility(View.VISIBLE);
                    checkBoxBanheiroSocial.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxBanheiroSocial.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxAreaServico.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout2.setVisibility(View.GONE);
                    checkBoxBanheiroSocial.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxAreaServico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout3.setVisibility(View.VISIBLE);
                    checkBoxAreaServico.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxAreaServico.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout3.setVisibility(View.GONE);
                    checkBoxAreaServico.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxBanheiroSuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout4.setVisibility(View.VISIBLE);
                    checkBoxBanheiroSuite.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxBanheiroSuite.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout4.setVisibility(View.GONE);
                    checkBoxBanheiroSuite.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxLavabo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout5.setVisibility(View.VISIBLE);
                    checkBoxLavabo.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxLavabo.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout5.setVisibility(View.GONE);
                    checkBoxLavabo.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxSacadaVaranda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout6.setVisibility(View.VISIBLE);
                    checkBoxSacadaVaranda.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxSacadaVaranda.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout6.setVisibility(View.GONE);
                    checkBoxSacadaVaranda.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxSalaJantar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout7.setVisibility(View.VISIBLE);
                    checkBoxSalaJantar.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxSalaJantar.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout7.setVisibility(View.GONE);
                    checkBoxSalaJantar.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxSalaEstar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout8.setVisibility(View.VISIBLE);
                    checkBoxSalaEstar.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxSalaEstar.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout8.setVisibility(View.GONE);
                    checkBoxSalaEstar.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxQuartoSuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout9.setVisibility(View.VISIBLE);
                    checkBoxQuartoSuite.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxQuartoSuite.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout9.setVisibility(View.GONE);
                    checkBoxQuartoSuite.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxQuarto1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout10.setVisibility(View.VISIBLE);
                    checkBoxQuarto1.setBackgroundColor(Color.parseColor("#1d1d1d"));

                    checkBoxQuarto1.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxQuarto2.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout10.setVisibility(View.GONE);
                    checkBoxQuarto1.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxQuarto2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout11.setVisibility(View.VISIBLE);
                    checkBoxQuarto2.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxQuarto2.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxBanheiroSocial.setChecked(false);
                    checkBoxBanheiroSuite.setChecked(false);
                    checkBoxAreaServico.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                    checkBoxQuarto1.setChecked(false);
                    checkBoxSalaEstar.setChecked(false);
                    checkBoxLavabo.setChecked(false);
                    checkBoxSalaJantar.setChecked(false);
                    checkBoxSacadaVaranda.setChecked(false);
                    checkBoxCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayout11.setVisibility(View.GONE);
                    checkBoxQuarto2.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxRevestimentoCozinha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento1.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoCozinha.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoCozinha.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento1.setVisibility(View.GONE);
                    checkBoxRevestimentoCozinha.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxRevestimentoBanheiroSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento2.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoBanheiroSocial.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoBanheiroSocial.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento2.setVisibility(View.GONE);
                    checkBoxRevestimentoBanheiroSocial.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxRevestimentoAreaServico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento3.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoAreaServico.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoAreaServico.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento3.setVisibility(View.GONE);
                    checkBoxRevestimentoAreaServico.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxRevestimentoBanheiroSuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento4.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoBanheiroSuite.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoBanheiroSuite.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento4.setVisibility(View.GONE);
                    checkBoxRevestimentoBanheiroSuite.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxRevestimentoLavabo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento5.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoLavabo.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoLavabo.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento5.setVisibility(View.GONE);
                    checkBoxRevestimentoLavabo.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxRevestimentoSacadaVaranda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento6.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoSacadaVaranda.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoSacadaVaranda.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento6.setVisibility(View.GONE);
                    checkBoxRevestimentoSacadaVaranda.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxRevestimentoSalaJantar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento7.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoSalaJantar.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoSalaJantar.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento7.setVisibility(View.GONE);
                    checkBoxRevestimentoSalaJantar.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxRevestimentoSalaEstar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento8.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoSalaEstar.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoSalaEstar.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento8.setVisibility(View.GONE);
                    checkBoxRevestimentoSalaEstar.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxRevestimentoQuarto1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento9.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoQuarto1.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoQuarto1.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento9.setVisibility(View.GONE);
                    checkBoxRevestimentoQuarto1.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxRevestimentoQuarto2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento10.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoQuarto2.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoQuarto2.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuartoSuite.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento10.setVisibility(View.GONE);
                    checkBoxRevestimentoQuarto2.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxRevestimentoQuartoSuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutRevestimento11.setVisibility(View.VISIBLE);
                    checkBoxRevestimentoQuartoSuite.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxRevestimentoQuartoSuite.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxRevestimentoAreaServico.setChecked(false);
                    checkBoxRevestimentoBanheiroSocial.setChecked(false);
                    checkBoxRevestimentoQuarto2.setChecked(false);
                    checkBoxRevestimentoSalaJantar.setChecked(false);
                    checkBoxRevestimentoSalaEstar.setChecked(false);
                    checkBoxRevestimentoBanheiroSuite.setChecked(false);
                    checkBoxRevestimentoSacadaVaranda.setChecked(false);
                    checkBoxRevestimentoLavabo.setChecked(false);
                    checkBoxRevestimentoQuarto1.setChecked(false);
                    checkBoxRevestimentoCozinha.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutRevestimento11.setVisibility(View.GONE);
                    checkBoxRevestimentoQuartoSuite.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });


        checkBoxHidraulicaCozinha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutHidraulica1.setVisibility(View.VISIBLE);
                    checkBoxHidraulicaCozinha.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxHidraulicaCozinha.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxHidraulicaAreaServico.setChecked(false);
                    checkBoxHidraulicaBanheiroSocial.setChecked(false);

                    checkBoxHidraulicaBanheiroSuite.setChecked(false);
                    checkBoxHidraulicaSacadaVaranda.setChecked(false);
                    checkBoxHidraulicaLavabo.setChecked(false);

                } else if (isChecked == false) {
                    linearLayoutHidraulica1.setVisibility(View.GONE);
                    checkBoxHidraulicaCozinha.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxHidraulicaBanheiroSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutHidraulica2.setVisibility(View.VISIBLE);
                    checkBoxHidraulicaBanheiroSocial.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxHidraulicaBanheiroSocial.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxHidraulicaAreaServico.setChecked(false);
                    checkBoxHidraulicaCozinha.setChecked(false);

                    checkBoxHidraulicaBanheiroSuite.setChecked(false);
                    checkBoxHidraulicaSacadaVaranda.setChecked(false);
                    checkBoxHidraulicaLavabo.setChecked(false);

                } else if (isChecked == false) {
                    linearLayoutHidraulica2.setVisibility(View.GONE);
                    checkBoxHidraulicaBanheiroSocial.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxHidraulicaAreaServico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutHidraulica3.setVisibility(View.VISIBLE);
                    checkBoxHidraulicaAreaServico.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxHidraulicaAreaServico.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxHidraulicaBanheiroSocial.setChecked(false);
                    checkBoxHidraulicaCozinha.setChecked(false);

                    checkBoxHidraulicaBanheiroSuite.setChecked(false);
                    checkBoxHidraulicaSacadaVaranda.setChecked(false);
                    checkBoxHidraulicaLavabo.setChecked(false);

                } else if (isChecked == false) {
                    linearLayoutHidraulica3.setVisibility(View.GONE);
                    checkBoxHidraulicaAreaServico.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxHidraulicaBanheiroSuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutHidraulica4.setVisibility(View.VISIBLE);
                    checkBoxHidraulicaBanheiroSuite.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxHidraulicaBanheiroSuite.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxHidraulicaBanheiroSocial.setChecked(false);
                    checkBoxHidraulicaCozinha.setChecked(false);

                    checkBoxHidraulicaAreaServico.setChecked(false);
                    checkBoxHidraulicaSacadaVaranda.setChecked(false);
                    checkBoxHidraulicaLavabo.setChecked(false);

                } else if (isChecked == false) {
                    linearLayoutHidraulica4.setVisibility(View.GONE);
                    checkBoxHidraulicaBanheiroSuite.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxHidraulicaLavabo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutHidraulica5.setVisibility(View.VISIBLE);
                    checkBoxHidraulicaLavabo.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxHidraulicaLavabo.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxHidraulicaBanheiroSocial.setChecked(false);
                    checkBoxHidraulicaCozinha.setChecked(false);

                    checkBoxHidraulicaAreaServico.setChecked(false);
                    checkBoxHidraulicaSacadaVaranda.setChecked(false);
                    checkBoxHidraulicaBanheiroSuite.setChecked(false);

                } else if (isChecked == false) {
                    linearLayoutHidraulica5.setVisibility(View.GONE);
                    checkBoxHidraulicaLavabo.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxHidraulicaSacadaVaranda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutHidraulica6.setVisibility(View.VISIBLE);
                    checkBoxHidraulicaSacadaVaranda.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxHidraulicaSacadaVaranda.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxHidraulicaBanheiroSocial.setChecked(false);
                    checkBoxHidraulicaCozinha.setChecked(false);

                    checkBoxHidraulicaAreaServico.setChecked(false);
                    checkBoxHidraulicaLavabo.setChecked(false);
                    checkBoxHidraulicaBanheiroSuite.setChecked(false);

                } else if (isChecked == false) {
                    linearLayoutHidraulica6.setVisibility(View.GONE);
                    checkBoxHidraulicaSacadaVaranda.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxPinturaSacadaVaranda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutHidraulica6.setVisibility(View.VISIBLE);
                    checkBoxHidraulicaSacadaVaranda.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxHidraulicaSacadaVaranda.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxHidraulicaBanheiroSocial.setChecked(false);
                    checkBoxHidraulicaCozinha.setChecked(false);

                    checkBoxHidraulicaAreaServico.setChecked(false);
                    checkBoxHidraulicaLavabo.setChecked(false);
                    checkBoxHidraulicaBanheiroSuite.setChecked(false);

                } else if (isChecked == false) {
                    linearLayoutHidraulica6.setVisibility(View.GONE);
                    checkBoxHidraulicaSacadaVaranda.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxPinturaCozinha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura1.setVisibility(View.VISIBLE);
                    checkBoxPinturaCozinha.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaCozinha.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura1.setVisibility(View.GONE);
                    checkBoxPinturaCozinha.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxPinturaBanheiroSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura2.setVisibility(View.VISIBLE);
                    checkBoxPinturaBanheiroSocial.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaBanheiroSocial.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura2.setVisibility(View.GONE);
                    checkBoxPinturaBanheiroSocial.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxPinturaAreaServico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura3.setVisibility(View.VISIBLE);
                    checkBoxPinturaAreaServico.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaAreaServico.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura3.setVisibility(View.GONE);
                    checkBoxPinturaAreaServico.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxPinturaBanheiroSuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura4.setVisibility(View.VISIBLE);
                    checkBoxPinturaBanheiroSuite.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaBanheiroSuite.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura4.setVisibility(View.GONE);
                    checkBoxPinturaBanheiroSuite.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxPinturaLavabo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura5.setVisibility(View.VISIBLE);
                    checkBoxPinturaLavabo.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaLavabo.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura5.setVisibility(View.GONE);
                    checkBoxPinturaLavabo.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxPinturaSacadaVaranda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura6.setVisibility(View.VISIBLE);
                    checkBoxPinturaSacadaVaranda.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaSacadaVaranda.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox

                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura6.setVisibility(View.GONE);
                    checkBoxPinturaSacadaVaranda.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxPinturaSalaJantar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura7.setVisibility(View.VISIBLE);
                    checkBoxPinturaSalaJantar.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaSalaJantar.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura7.setVisibility(View.GONE);
                    checkBoxPinturaSalaJantar.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxPinturaSalaEstar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura8.setVisibility(View.VISIBLE);
                    checkBoxPinturaSalaEstar.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaSalaEstar.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura8.setVisibility(View.GONE);
                    checkBoxPinturaSalaEstar.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxPinturaQuarto1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura9.setVisibility(View.VISIBLE);
                    checkBoxPinturaQuarto1.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaQuarto1.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura9.setVisibility(View.GONE);
                    checkBoxPinturaQuarto1.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxPinturaQuarto2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura10.setVisibility(View.VISIBLE);
                    checkBoxPinturaQuarto2.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaQuarto2.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaQuartoSuite.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura10.setVisibility(View.GONE);
                    checkBoxPinturaQuarto2.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        checkBoxPinturaQuartoSuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura11.setVisibility(View.VISIBLE);
                    checkBoxPinturaQuartoSuite.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaQuartoSuite.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxPinturaM2.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura11.setVisibility(View.GONE);
                    checkBoxPinturaQuartoSuite.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        checkBoxPinturaM2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayoutPintura12.setVisibility(View.VISIBLE);
                    checkBoxPinturaM2.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    checkBoxPinturaM2.setTextColor(Color.parseColor("#ffffff"));


                    //Desativando os outros checkBox
                    checkBoxPinturaAreaServico.setChecked(false);
                    checkBoxPinturaCozinha.setChecked(false);
                    checkBoxPinturaSacadaVaranda.setChecked(false);
                    checkBoxPinturaQuarto1.setChecked(false);
                    checkBoxPinturaLavabo.setChecked(false);
                    checkBoxPinturaSalaJantar.setChecked(false);
                    checkBoxPinturaBanheiroSocial.setChecked(false);
                    checkBoxPinturaBanheiroSuite.setChecked(false);
                    checkBoxPinturaSalaEstar.setChecked(false);
                    checkBoxPinturaQuarto2.setChecked(false);
                    checkBoxQuartoSuite.setChecked(false);
                } else if (isChecked == false) {
                    linearLayoutPintura12.setVisibility(View.GONE);
                    checkBoxPinturaM2.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        BtncheckBoxArtArCondicionado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckBoxArtArCondicionado.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckBoxArtArCondicionado.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckBoxArtArCondicionado.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtEnvidracamento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtEnvidracamento.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtEnvidracamento.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtEnvidracamento.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtPedrasMarmore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtPedrasMarmore.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtPedrasMarmore.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtPedrasMarmore.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });

        BtncheckboxArtNovosRevestimentos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtNovosRevestimentos.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtNovosRevestimentos.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtNovosRevestimentos.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtEletrica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtEletrica.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtEletrica.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtEletrica.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtHidraulica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtHidraulica.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtHidraulica.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtHidraulica.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtGesso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtGesso.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtGesso.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtGesso.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtDemolicao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtDemolicao.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtDemolicao.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtDemolicao.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtMoveisPlanejados.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtMoveisPlanejados.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtMoveisPlanejados.setTextColor(Color.parseColor("#ffffff"));

                } else if (isChecked == false) {
                    BtncheckboxArtMoveisPlanejados.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtDeslocamento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtDeslocamento.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtDeslocamento.setTextColor(Color.parseColor("#ffffff"));
                    valorParcial();
                } else if (isChecked == false) {
                    BtncheckboxArtDeslocamento.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });
        BtncheckboxArtBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    BtncheckboxArtBox.setBackgroundColor(Color.parseColor("#1d1d1d"));
                    BtncheckboxArtBox.setTextColor(Color.parseColor("#ffffff"));
                    valorParcial();
                } else if (isChecked == false) {
                    BtncheckboxArtBox.setBackgroundResource(R.drawable.btn_servico1);

                }
            }
        });


        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// Criando PDF
                valorParcial();

                //Preparando o local

                //Calculando Valores Cozinha

                varRemoverRevestimentoParede = Double.parseDouble(valorRevestimentoParede1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede1 = Double.parseDouble(valorRevestimentoParede1_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso = Double.parseDouble(valorRemocaoPiso1.getText().toString()) * precoRemoverPiso;
                varRemoverPiso1 = Double.parseDouble(valorRemocaoPiso1_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia = Double.parseDouble(valorRemocaoPia1.getText().toString()) * precoRemoverPia;
                varRemoverPia1 = Double.parseDouble(valorRemocaoPia1_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria = Double.parseDouble(valorRemocacAlvenaria1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria1 = Double.parseDouble(valorRemocacAlvenaria1_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque = Double.parseDouble(valorRemocaoTanque1.getText().toString()) * precoRemoverTanque;
                varRemoverTanque1 = Double.parseDouble(valorRemocaoTanque1_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2 = Double.parseDouble(valorRasgarCaixinha4x2_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_1 = Double.parseDouble(valorRasgarCaixinha4x2_1_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4 = Double.parseDouble(valorRasgarCaixinha4x4_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_1 = Double.parseDouble(valorRasgarCaixinha4x4_1_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica = Double.parseDouble(valorRasgarHidraulica1.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica1 = Double.parseDouble(valorRasgarHidraulica1_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso = Double.parseDouble(valorRemoverGesso1.getText().toString()) * precoRemoverGesso;
                varRemoverGesso1 = Double.parseDouble(valorRemoverGesso1_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario = Double.parseDouble(valorRemoverVaso1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario1 = Double.parseDouble(valorRemoverVaso1_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao = Double.parseDouble(valorRemoverVao1.getText().toString()) * precoRemoverVao;
                varRemoverVao1 = Double.parseDouble(valorRemoverVao1_1.getText().toString()) * precoRemoverVao;

                valorTotalCozinha = varRemoverRevestimentoParede + varRemoverRevestimentoParede1 + varRemoverPiso + varRemoverPiso1 + varRemoverPia + varRemoverPia1 + varRemoverAlvenaria + varRemoverAlvenaria1 + varRemoverTanque + varRemoverTanque1 + varRemoverCaixinha4x2 + varRemoverCaixinha4x2_1 + varRemoverCaixinha4x4 + varRemoverCaixinha4x4_1 + varRemoverHidraulica + varRemoverHidraulica1 + varRemoverGesso + varRemoverGesso1 + varRemoverVasoSanitario + varRemoverVasoSanitario1 + varRemoverVao + varRemoverVao1;

                //Calculando Valores Banheiro 1

                varRemoverRevestimentoParede2 = Double.parseDouble(valorRevestimentoParede2.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede2_1 = Double.parseDouble(valorRevestimentoParede2_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso2 = Double.parseDouble(valorRemocaoPiso2.getText().toString()) * precoRemoverPiso;
                varRemoverPiso2_1 = Double.parseDouble(valorRemocaoPiso2_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia2 = Double.parseDouble(valorRemocaoPia2.getText().toString()) * precoRemoverPia;
                varRemoverPia2_1 = Double.parseDouble(valorRemocaoPia2_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria2 = Double.parseDouble(valorRemocacAlvenaria2.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria2_1 = Double.parseDouble(valorRemocacAlvenaria2_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque2 = Double.parseDouble(valorRemocaoTanque2.getText().toString()) * precoRemoverTanque;
                varRemoverTanque2_1 = Double.parseDouble(valorRemocaoTanque2_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_2 = Double.parseDouble(valorRasgarCaixinha4x2_2.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_2_1 = Double.parseDouble(valorRasgarCaixinha4x2_2_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_2 = Double.parseDouble(valorRasgarCaixinha4x4_2.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_2_1 = Double.parseDouble(valorRasgarCaixinha4x4_2_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica2 = Double.parseDouble(valorRasgarHidraulica2.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica2_1 = Double.parseDouble(valorRasgarHidraulica2_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso2 = Double.parseDouble(valorRemoverGesso2.getText().toString()) * precoRemoverGesso;
                varRemoverGesso2_1 = Double.parseDouble(valorRemoverGesso2_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario2 = Double.parseDouble(valorRemoverVaso2.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario2_1 = Double.parseDouble(valorRemoverVaso2_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao2 = Double.parseDouble(valorRemoverVao2.getText().toString()) * precoRemoverVao;
                varRemoverVao2_1 = Double.parseDouble(valorRemoverVao2_1.getText().toString()) * precoRemoverVao;

                valorTotalBanheiro1 = varRemoverRevestimentoParede2 + varRemoverRevestimentoParede2_1 + varRemoverPiso2 + varRemoverPiso2_1 + varRemoverPia2 + varRemoverPia2_1 + varRemoverAlvenaria2 + varRemoverAlvenaria2_1 + varRemoverTanque2 + varRemoverTanque2 + varRemoverCaixinha4x2_2 + varRemoverCaixinha4x2_2_1 + varRemoverCaixinha4x2_2 + varRemoverCaixinha4x4_2_1 + varRemoverHidraulica2 + varRemoverHidraulica2_1 + varRemoverGesso2 + varRemoverGesso2_1 + varRemoverVasoSanitario2 + varRemoverVasoSanitario2_1 + varRemoverVao2 + varRemoverVao2_1;


                //Calculando Valores Aréa de Serviço

                varRemoverRevestimentoParede3 = Double.parseDouble(valorRevestimentoParede3.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede3_1 = Double.parseDouble(valorRevestimentoParede3_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso3 = Double.parseDouble(valorRemocaoPiso3.getText().toString()) * precoRemoverPiso;
                varRemoverPiso3_1 = Double.parseDouble(valorRemocaoPiso3_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia3 = Double.parseDouble(valorRemocaoPia3.getText().toString()) * precoRemoverPia;
                varRemoverPia3_1 = Double.parseDouble(valorRemocaoPia3_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria3 = Double.parseDouble(valorRemocacAlvenaria3.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria3_1 = Double.parseDouble(valorRemocacAlvenaria3_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque3 = Double.parseDouble(valorRemocaoTanque3.getText().toString()) * precoRemoverTanque;
                varRemoverTanque3_1 = Double.parseDouble(valorRemocaoTanque3_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_3 = Double.parseDouble(valorRasgarCaixinha4x2_3.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_3_1 = Double.parseDouble(valorRasgarCaixinha4x2_3_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_3 = Double.parseDouble(valorRasgarCaixinha4x4_3.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_3_1 = Double.parseDouble(valorRasgarCaixinha4x4_3_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica3 = Double.parseDouble(valorRasgarHidraulica3.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica3_1 = Double.parseDouble(valorRasgarHidraulica3_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso3 = Double.parseDouble(valorRemoverGesso3.getText().toString()) * precoRemoverGesso;
                varRemoverGesso3_1 = Double.parseDouble(valorRemoverGesso3_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario3 = Double.parseDouble(valorRemoverVaso3.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario3_1 = Double.parseDouble(valorRemoverVaso3_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao3 = Double.parseDouble(valorRemoverVao3.getText().toString()) * precoRemoverVao;
                varRemoverVao3_1 = Double.parseDouble(valorRemoverVao3_1.getText().toString()) * precoRemoverVao;


                valorTotalAreaServico = varRemoverRevestimentoParede3 + varRemoverRevestimentoParede3_1 + varRemoverPiso3 + varRemoverPiso3_1 + varRemoverPia3 + varRemoverPia3_1 + varRemoverAlvenaria3 + varRemoverAlvenaria3_1 + varRemoverTanque3 + varRemoverTanque3_1 + varRemoverCaixinha4x2_3 + varRemoverCaixinha4x2_3_1 + varRemoverCaixinha4x4_3 + varRemoverCaixinha4x4_3_1 + varRemoverHidraulica3 + varRemoverHidraulica3_1 + varRemoverGesso3 + varRemoverGesso3_1 + varRemoverVasoSanitario3 + varRemoverVasoSanitario3_1 + varRemoverVao3 + varRemoverVao3_1;


                //Calculando Valores Banheiro 2

                varRemoverRevestimentoParede4 = Double.parseDouble(valorRevestimentoParede4.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede4_1 = Double.parseDouble(valorRevestimentoParede4_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso4 = Double.parseDouble(valorRemocaoPiso4.getText().toString()) * precoRemoverPiso;
                varRemoverPiso4_1 = Double.parseDouble(valorRemocaoPiso4_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia4 = Double.parseDouble(valorRemocaoPia4.getText().toString()) * precoRemoverPia;
                varRemoverPia4_1 = Double.parseDouble(valorRemocaoPia4_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria4 = Double.parseDouble(valorRemocacAlvenaria4.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria4_1 = Double.parseDouble(valorRemocacAlvenaria4_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque4 = Double.parseDouble(valorRemocaoTanque4.getText().toString()) * precoRemoverTanque;
                varRemoverTanque4_1 = Double.parseDouble(valorRemocaoTanque4_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_4 = Double.parseDouble(valorRasgarCaixinha4x2_4.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_4_1 = Double.parseDouble(valorRasgarCaixinha4x2_4_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_4 = Double.parseDouble(valorRasgarCaixinha4x4_4.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_4_1 = Double.parseDouble(valorRasgarCaixinha4x4_4_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica4 = Double.parseDouble(valorRasgarHidraulica4.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica4_1 = Double.parseDouble(valorRasgarHidraulica4_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso4 = Double.parseDouble(valorRemoverGesso4.getText().toString()) * precoRemoverGesso;
                varRemoverGesso4_1 = Double.parseDouble(valorRemoverGesso4_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario4 = Double.parseDouble(valorRemoverVaso4.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario4_1 = Double.parseDouble(valorRemoverVaso4_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao4 = Double.parseDouble(valorRemoverVao4.getText().toString()) * precoRemoverVao;
                varRemoverVao4_1 = Double.parseDouble(valorRemoverVao4_1.getText().toString()) * precoRemoverVao;


                valorTotalBanheiro2 = varRemoverRevestimentoParede4 + varRemoverRevestimentoParede4_1 + varRemoverPiso4 + varRemoverPiso4_1 + varRemoverPia4 + varRemoverPia4_1 + varRemoverAlvenaria4 + varRemoverAlvenaria4_1 + varRemoverTanque4 + varRemoverTanque4_1 + varRemoverCaixinha4x2_4 + varRemoverCaixinha4x2_4_1 + varRemoverCaixinha4x4_4 + varRemoverCaixinha4x4_4_1 + varRemoverHidraulica4 + varRemoverHidraulica4_1 + varRemoverGesso4 + varRemoverGesso4_1 + varRemoverVasoSanitario4 + varRemoverVasoSanitario4_1 + varRemoverVao4 + varRemoverVao4_1;


                //Calculando Valores Lavabo

                varRemoverRevestimentoParede5 = Double.parseDouble(valorRevestimentoParede5.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede5_1 = Double.parseDouble(valorRevestimentoParede5_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso5 = Double.parseDouble(valorRemocaoPiso5.getText().toString()) * precoRemoverPiso;
                varRemoverPiso5_1 = Double.parseDouble(valorRemocaoPiso5_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia5 = Double.parseDouble(valorRemocaoPia5.getText().toString()) * precoRemoverPia;
                varRemoverPia5_1 = Double.parseDouble(valorRemocaoPia5_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria5 = Double.parseDouble(valorRemocacAlvenaria5.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria5_1 = Double.parseDouble(valorRemocacAlvenaria5_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque5 = Double.parseDouble(valorRemocaoTanque5.getText().toString()) * precoRemoverTanque;
                varRemoverTanque5_1 = Double.parseDouble(valorRemocaoTanque5_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_5 = Double.parseDouble(valorRasgarCaixinha4x2_5.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_5_1 = Double.parseDouble(valorRasgarCaixinha4x2_5_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_5 = Double.parseDouble(valorRasgarCaixinha4x4_5.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_5_1 = Double.parseDouble(valorRasgarCaixinha4x4_5_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica5 = Double.parseDouble(valorRasgarHidraulica5.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica5_1 = Double.parseDouble(valorRasgarHidraulica5_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso5 = Double.parseDouble(valorRemoverGesso5.getText().toString()) * precoRemoverGesso;
                varRemoverGesso5_1 = Double.parseDouble(valorRemoverGesso5_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario5 = Double.parseDouble(valorRemoverVaso5.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario5_1 = Double.parseDouble(valorRemoverVaso5_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao5 = Double.parseDouble(valorRemoverVao5.getText().toString()) * precoRemoverVao;
                varRemoverVao5_1 = Double.parseDouble(valorRemoverVao5_1.getText().toString()) * precoRemoverVao;


                valorTotalLavabo = varRemoverRevestimentoParede5 + varRemoverRevestimentoParede5_1 + varRemoverPiso5 + varRemoverPiso5_1 + varRemoverPia5 + varRemoverPia5_1 + varRemoverAlvenaria5 + varRemoverAlvenaria5_1 + varRemoverTanque5 + varRemoverTanque5_1 + varRemoverCaixinha4x2_5 + varRemoverCaixinha4x2_5_1 + varRemoverCaixinha4x4_5 + varRemoverCaixinha4x4_5_1 + varRemoverHidraulica5 + varRemoverHidraulica5_1 + varRemoverGesso5 + varRemoverGesso5_1 + varRemoverVasoSanitario5 + varRemoverVasoSanitario5_1 + varRemoverVao5 + varRemoverVao5_1;

                //Calculando Valores Sacada Varanda

                varRemoverRevestimentoParede6 = Double.parseDouble(valorRevestimentoParede6.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede6_1 = Double.parseDouble(valorRevestimentoParede6_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso6 = Double.parseDouble(valorRemocaoPiso6.getText().toString()) * precoRemoverPiso;
                varRemoverPiso6_1 = Double.parseDouble(valorRemocaoPiso6_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia6 = Double.parseDouble(valorRemocaoPia6.getText().toString()) * precoRemoverPia;
                varRemoverPia6_1 = Double.parseDouble(valorRemocaoPia6_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria6 = Double.parseDouble(valorRemocacAlvenaria6.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria6_1 = Double.parseDouble(valorRemocacAlvenaria6_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque6 = Double.parseDouble(valorRemocaoTanque6.getText().toString()) * precoRemoverTanque;
                varRemoverTanque6_1 = Double.parseDouble(valorRemocaoTanque6_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_6 = Double.parseDouble(valorRasgarCaixinha4x2_6.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_6_1 = Double.parseDouble(valorRasgarCaixinha4x2_6_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_6 = Double.parseDouble(valorRasgarCaixinha4x4_6.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_6_1 = Double.parseDouble(valorRasgarCaixinha4x4_6_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica6 = Double.parseDouble(valorRasgarHidraulica6.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica6_1 = Double.parseDouble(valorRasgarHidraulica6_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso6 = Double.parseDouble(valorRemoverGesso6.getText().toString()) * precoRemoverGesso;
                varRemoverGesso6_1 = Double.parseDouble(valorRemoverGesso6_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario6 = Double.parseDouble(valorRemoverVaso6.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario6_1 = Double.parseDouble(valorRemoverVaso6_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao6 = Double.parseDouble(valorRemoverVao6.getText().toString()) * precoRemoverVao;
                varRemoverVao6_1 = Double.parseDouble(valorRemoverVao6_1.getText().toString()) * precoRemoverVao;


                valorTotalSacadaVaranda = varRemoverRevestimentoParede6 + varRemoverRevestimentoParede6_1 + varRemoverPiso6 + varRemoverPiso6_1 + varRemoverPia6 + varRemoverPia6_1 + varRemoverAlvenaria6 + varRemoverAlvenaria6_1 + varRemoverTanque6 + varRemoverTanque6_1 + varRemoverCaixinha4x2_6 + varRemoverCaixinha4x2_6_1 + varRemoverCaixinha4x4_6 + varRemoverCaixinha4x4_6_1 + varRemoverHidraulica6 + varRemoverHidraulica6_1 + varRemoverGesso6 + varRemoverGesso6_1 + varRemoverVasoSanitario6 + varRemoverVasoSanitario6_1 + varRemoverVao6 + varRemoverVao6_1;

                //Calculando Valores Sala de Jantar

                varRemoverRevestimentoParede7 = Double.parseDouble(valorRevestimentoParede7.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede7_1 = Double.parseDouble(valorRevestimentoParede7_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso7 = Double.parseDouble(valorRemocaoPiso7.getText().toString()) * precoRemoverPiso;
                varRemoverPiso7_1 = Double.parseDouble(valorRemocaoPiso7_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia7 = Double.parseDouble(valorRemocaoPia7.getText().toString()) * precoRemoverPia;
                varRemoverPia7_1 = Double.parseDouble(valorRemocaoPia7_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria7 = Double.parseDouble(valorRemocacAlvenaria7.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria7_1 = Double.parseDouble(valorRemocacAlvenaria7_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque7 = Double.parseDouble(valorRemocaoTanque7.getText().toString()) * precoRemoverTanque;
                varRemoverTanque7_1 = Double.parseDouble(valorRemocaoTanque7_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_7 = Double.parseDouble(valorRasgarCaixinha4x2_7.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_7_1 = Double.parseDouble(valorRasgarCaixinha4x2_7_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_7 = Double.parseDouble(valorRasgarCaixinha4x4_7.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_7_1 = Double.parseDouble(valorRasgarCaixinha4x4_7_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica7 = Double.parseDouble(valorRasgarHidraulica7.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica7_1 = Double.parseDouble(valorRasgarHidraulica7_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso7 = Double.parseDouble(valorRemoverGesso7.getText().toString()) * precoRemoverGesso;
                varRemoverGesso7_1 = Double.parseDouble(valorRemoverGesso7_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario7 = Double.parseDouble(valorRemoverVaso7.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario7_1 = Double.parseDouble(valorRemoverVaso7_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao7 = Double.parseDouble(valorRemoverVao7.getText().toString()) * precoRemoverVao;
                varRemoverVao7_1 = Double.parseDouble(valorRemoverVao7_1.getText().toString()) * precoRemoverVao;


                valorTotalSalaJantar = varRemoverRevestimentoParede7 + varRemoverRevestimentoParede7_1 + varRemoverPiso7 + varRemoverPiso7_1 + varRemoverPia7 + varRemoverPia7_1 + varRemoverAlvenaria7 + varRemoverAlvenaria7_1 + varRemoverTanque7 + varRemoverTanque7_1 + varRemoverCaixinha4x2_7 + varRemoverCaixinha4x2_7_1 + varRemoverCaixinha4x4_7 + varRemoverCaixinha4x4_7_1 + varRemoverHidraulica7 + varRemoverHidraulica7_1 + varRemoverGesso7 + varRemoverGesso7_1 + varRemoverVasoSanitario7 + varRemoverVasoSanitario7_1 + varRemoverVao7 + varRemoverVao7_1;

                //Calculando Valores Sala de Estar

                varRemoverRevestimentoParede8 = Double.parseDouble(valorRevestimentoParede8.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede8_1 = Double.parseDouble(valorRevestimentoParede8_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso8 = Double.parseDouble(valorRemocaoPiso8.getText().toString()) * precoRemoverPiso;
                varRemoverPiso8_1 = Double.parseDouble(valorRemocaoPiso8_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia8 = Double.parseDouble(valorRemocaoPia8.getText().toString()) * precoRemoverPia;
                varRemoverPia8_1 = Double.parseDouble(valorRemocaoPia8_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria8 = Double.parseDouble(valorRemocacAlvenaria8.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria8_1 = Double.parseDouble(valorRemocacAlvenaria8_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque8 = Double.parseDouble(valorRemocaoTanque8.getText().toString()) * precoRemoverTanque;
                varRemoverTanque8_1 = Double.parseDouble(valorRemocaoTanque8_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_8 = Double.parseDouble(valorRasgarCaixinha4x2_8.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_8_1 = Double.parseDouble(valorRasgarCaixinha4x2_8_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_8 = Double.parseDouble(valorRasgarCaixinha4x4_8.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_8_1 = Double.parseDouble(valorRasgarCaixinha4x4_8_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica8 = Double.parseDouble(valorRasgarHidraulica8.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica8_1 = Double.parseDouble(valorRasgarHidraulica8_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso8 = Double.parseDouble(valorRemoverGesso8.getText().toString()) * precoRemoverGesso;
                varRemoverGesso8_1 = Double.parseDouble(valorRemoverGesso8_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario8 = Double.parseDouble(valorRemoverVaso8.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario8_1 = Double.parseDouble(valorRemoverVaso8_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao8 = Double.parseDouble(valorRemoverVao8.getText().toString()) * precoRemoverVao;
                varRemoverVao8_1 = Double.parseDouble(valorRemoverVao8_1.getText().toString()) * precoRemoverVao;


                valorTotalSalaEstar = varRemoverRevestimentoParede8 + varRemoverRevestimentoParede8_1 + varRemoverPiso8 + varRemoverPiso8_1 + varRemoverPia8 + varRemoverPia8_1 + varRemoverAlvenaria8 + varRemoverAlvenaria8_1 + varRemoverTanque8 + varRemoverTanque8_1 + varRemoverCaixinha4x2_8 + varRemoverCaixinha4x2_8_1 + varRemoverCaixinha4x4_8 + varRemoverCaixinha4x4_8_1 + varRemoverHidraulica8 + varRemoverHidraulica8_1 + varRemoverGesso8 + varRemoverGesso8_1 + varRemoverVasoSanitario8 + varRemoverVasoSanitario8_1 + varRemoverVao8 + varRemoverVao8_1;

                //Calculando Valores Quarto1

                varRemoverRevestimentoParede9 = Double.parseDouble(valorRevestimentoParede9.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede9_1 = Double.parseDouble(valorRevestimentoParede9_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso9 = Double.parseDouble(valorRemocaoPiso9.getText().toString()) * precoRemoverPiso;
                varRemoverPiso9_1 = Double.parseDouble(valorRemocaoPiso9_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia9 = Double.parseDouble(valorRemocaoPia9.getText().toString()) * precoRemoverPia;
                varRemoverPia9_1 = Double.parseDouble(valorRemocaoPia9_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria9 = Double.parseDouble(valorRemocacAlvenaria9.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria9_1 = Double.parseDouble(valorRemocacAlvenaria9_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque9 = Double.parseDouble(valorRemocaoTanque9.getText().toString()) * precoRemoverTanque;
                varRemoverTanque9_1 = Double.parseDouble(valorRemocaoTanque9_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_9 = Double.parseDouble(valorRasgarCaixinha4x2_9.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_9_1 = Double.parseDouble(valorRasgarCaixinha4x2_9_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_9 = Double.parseDouble(valorRasgarCaixinha4x4_9.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_9_1 = Double.parseDouble(valorRasgarCaixinha4x4_9_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica9 = Double.parseDouble(valorRasgarHidraulica9.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica9_1 = Double.parseDouble(valorRasgarHidraulica9_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso9 = Double.parseDouble(valorRemoverGesso9.getText().toString()) * precoRemoverGesso;
                varRemoverGesso9_1 = Double.parseDouble(valorRemoverGesso9_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario9 = Double.parseDouble(valorRemoverVaso9.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario9_1 = Double.parseDouble(valorRemoverVaso9_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao9 = Double.parseDouble(valorRemoverVao9.getText().toString()) * precoRemoverVao;
                varRemoverVao9_1 = Double.parseDouble(valorRemoverVao9_1.getText().toString()) * precoRemoverVao;


                valorTotalQuarto1 = varRemoverRevestimentoParede9 + varRemoverRevestimentoParede9_1 + varRemoverPiso9 + varRemoverPiso9_1 + varRemoverPia9 + varRemoverPia9_1 + varRemoverAlvenaria9 + varRemoverAlvenaria9_1 + varRemoverTanque9 + varRemoverTanque9_1 + varRemoverCaixinha4x2_9 + varRemoverCaixinha4x2_9_1 + varRemoverCaixinha4x4_9 + varRemoverCaixinha4x4_9_1 + varRemoverHidraulica9 + varRemoverHidraulica9_1 + varRemoverGesso9 + varRemoverGesso9_1 + varRemoverVasoSanitario9 + varRemoverVasoSanitario9_1 + varRemoverVao9 + varRemoverVao9_1;

                //Calculando Valores Quarto2

                varRemoverRevestimentoParede10 = Double.parseDouble(valorRevestimentoParede10.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede10_1 = Double.parseDouble(valorRevestimentoParede10_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso10 = Double.parseDouble(valorRemocaoPiso10.getText().toString()) * precoRemoverPiso;
                varRemoverPiso10_1 = Double.parseDouble(valorRemocaoPiso10_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia10 = Double.parseDouble(valorRemocaoPia10.getText().toString()) * precoRemoverPia;
                varRemoverPia10_1 = Double.parseDouble(valorRemocaoPia10_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria10 = Double.parseDouble(valorRemocacAlvenaria10.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria10_1 = Double.parseDouble(valorRemocacAlvenaria10_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque10 = Double.parseDouble(valorRemocaoTanque10.getText().toString()) * precoRemoverTanque;
                varRemoverTanque10_1 = Double.parseDouble(valorRemocaoTanque10_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_10 = Double.parseDouble(valorRasgarCaixinha4x2_10.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_10_1 = Double.parseDouble(valorRasgarCaixinha4x2_10_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_10 = Double.parseDouble(valorRasgarCaixinha4x4_10.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_10_1 = Double.parseDouble(valorRasgarCaixinha4x4_10_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica10 = Double.parseDouble(valorRasgarHidraulica10.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica10_1 = Double.parseDouble(valorRasgarHidraulica10_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso10 = Double.parseDouble(valorRemoverGesso10.getText().toString()) * precoRemoverGesso;
                varRemoverGesso10_1 = Double.parseDouble(valorRemoverGesso10_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario10 = Double.parseDouble(valorRemoverVaso10.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario10_1 = Double.parseDouble(valorRemoverVaso10_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao10 = Double.parseDouble(valorRemoverVao10.getText().toString()) * precoRemoverVao;
                varRemoverVao10_1 = Double.parseDouble(valorRemoverVao10_1.getText().toString()) * precoRemoverVao;


                valorTotalQuarto2 = varRemoverRevestimentoParede10 + varRemoverRevestimentoParede10_1 + varRemoverPiso10 + varRemoverPiso10_1 + varRemoverPia10 + varRemoverPia10_1 + varRemoverAlvenaria10 + varRemoverAlvenaria10_1 + varRemoverTanque10 + varRemoverTanque10_1 + varRemoverCaixinha4x2_10 + varRemoverCaixinha4x2_10_1 + varRemoverCaixinha4x4_10 + varRemoverCaixinha4x4_10_1 + varRemoverHidraulica10 + varRemoverHidraulica10_1 + varRemoverGesso10 + varRemoverGesso10_1 + varRemoverVasoSanitario10 + varRemoverVasoSanitario10_1 + varRemoverVao10 + varRemoverVao10_1;

                //Calculando Valores Quarto3

                varRemoverRevestimentoParede11 = Double.parseDouble(valorRevestimentoParede11.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverRevestimentoParede11_1 = Double.parseDouble(valorRevestimentoParede11_1.getText().toString()) * precoRemoverRevestimentoParede;
                varRemoverPiso11 = Double.parseDouble(valorRemocaoPiso11.getText().toString()) * precoRemoverPiso;
                varRemoverPiso11_1 = Double.parseDouble(valorRemocaoPiso11_1.getText().toString()) * precoRemoverPiso;
                varRemoverPia11 = Double.parseDouble(valorRemocaoPia11.getText().toString()) * precoRemoverPia;
                varRemoverPia11_1 = Double.parseDouble(valorRemocaoPia11_1.getText().toString()) * precoRemoverPia;
                varRemoverAlvenaria11 = Double.parseDouble(valorRemocacAlvenaria11.getText().toString()) * precoRemoverAlvenaria;
                varRemoverAlvenaria11_1 = Double.parseDouble(valorRemocacAlvenaria11_1.getText().toString()) * precoRemoverAlvenaria;
                varRemoverTanque11 = Double.parseDouble(valorRemocaoTanque11.getText().toString()) * precoRemoverTanque;
                varRemoverTanque11_1 = Double.parseDouble(valorRemocaoTanque11_1.getText().toString()) * precoRemoverTanque;
                varRemoverCaixinha4x2_11 = Double.parseDouble(valorRasgarCaixinha4x2_11.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x2_11_1 = Double.parseDouble(valorRasgarCaixinha4x2_11_1.getText().toString()) * precoRasgarCaixinha4x2;
                varRemoverCaixinha4x4_11 = Double.parseDouble(valorRasgarCaixinha4x4_11.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverCaixinha4x4_11_1 = Double.parseDouble(valorRasgarCaixinha4x4_11_1.getText().toString()) * precoRasgarCaixinha4x4;
                varRemoverHidraulica11 = Double.parseDouble(valorRasgarHidraulica11.getText().toString()) * precoRemoverHidraulica;
                varRemoverHidraulica11_1 = Double.parseDouble(valorRasgarHidraulica11_1.getText().toString()) * precoRemoverHidraulica;
                varRemoverGesso11 = Double.parseDouble(valorRemoverGesso11.getText().toString()) * precoRemoverGesso;
                varRemoverGesso11_1 = Double.parseDouble(valorRemoverGesso11_1.getText().toString()) * precoRemoverGesso;
                varRemoverVasoSanitario11 = Double.parseDouble(valorRemoverVaso11.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVasoSanitario11_1 = Double.parseDouble(valorRemoverVaso11_1.getText().toString()) * precoRemoverVasoSanitario;
                varRemoverVao11 = Double.parseDouble(valorRemoverVao11.getText().toString()) * precoRemoverVao;
                varRemoverVao11_1 = Double.parseDouble(valorRemoverVao11_1.getText().toString()) * precoRemoverVao;

                valorTotalQuarto3 = varRemoverRevestimentoParede11 + varRemoverRevestimentoParede11_1 + varRemoverPiso11 + varRemoverPiso11_1 + varRemoverPia11 + varRemoverPia11_1 + varRemoverAlvenaria11 + varRemoverAlvenaria11_1 + varRemoverTanque11 + varRemoverTanque11_1 + varRemoverCaixinha4x2_11 + varRemoverCaixinha4x2_11_1 + varRemoverCaixinha4x4_11 + varRemoverCaixinha4x4_11_1 + varRemoverHidraulica11 + varRemoverHidraulica11_1 + varRemoverGesso11 + varRemoverGesso11_1 + varRemoverVasoSanitario11 + varRemoverVasoSanitario11_1 + varRemoverVao11 + varRemoverVao11_1;


                totalDemolicao = valorTotalCozinha + valorTotalAreaServico + valorTotalBanheiro1 + valorTotalBanheiro2 + valorTotalBanheiro2 + valorTotalLavabo + valorTotalSacadaVaranda + valorTotalSalaEstar + valorTotalSalaJantar + valorTotalQuarto1 + valorTotalQuarto2 + valorTotalQuarto3;

                //Valores Revestimento Cozinha


                varAdicionarAlvenaria = Double.parseDouble(valorRevestimentoAlvenariaBase1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria1 = Double.parseDouble(valorRevestimentoAlvenariaBase1_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso = Double.parseDouble(valorRevestimentoContraPiso1.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso1 = Double.parseDouble(valorRevestimentoContraPiso1_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante = Double.parseDouble(valorRevestimentoImpermeabilidade1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante1 = Double.parseDouble(valorRevestimentoImpermeabilidade1_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior = Double.parseDouble(valorRevestimentoPorcelanatoAcima1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima1_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor = Double.parseDouble(valorRevestimentoPorcelanatoMenor1.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor1_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro = Double.parseDouble(valorRevestimentoPastilhaVidro1.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_1 = Double.parseDouble(valorRevestimentoPastilhaVidro1_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D = Double.parseDouble(valorRevestimento3D1.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_1 = Double.parseDouble(valorRevestimento3D1_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoCozinha = varAdicionarAlvenaria + varAdicionarAlvenaria1 + varAdicionarContraPiso + varAdicionarContraPiso1 + varAplicacaoImpermeabilizante + varAplicacaoImpermeabilizante1 + varAplicarPorcelanatoMaior + varAplicarPorcelanatoMaior1 + varAplicarPorcelanatoMenor + varAplicarPorcelanatoMenor1 + varPastilhaVidro + varPastilhaVidro_1 + varRevestimento3D + varRevestimento3D_1;

                //Valores Revestimento Banheiro Social
                varAdicionarAlvenaria2 = Double.parseDouble(valorRevestimentoAlvenariaBase2.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria2_1 = Double.parseDouble(valorRevestimentoAlvenariaBase2_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso2 = Double.parseDouble(valorRevestimentoContraPiso2.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso2_1 = Double.parseDouble(valorRevestimentoContraPiso2_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante2 = Double.parseDouble(valorRevestimentoImpermeabilidade2.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante2_1 = Double.parseDouble(valorRevestimentoImpermeabilidade2_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior2 = Double.parseDouble(valorRevestimentoPorcelanatoAcima2.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior2_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima2_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor2 = Double.parseDouble(valorRevestimentoPorcelanatoMenor2.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor2_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor2_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_2 = Double.parseDouble(valorRevestimentoPastilhaVidro2.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_2_1 = Double.parseDouble(valorRevestimentoPastilhaVidro2_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_2 = Double.parseDouble(valorRevestimento3D2.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_2_1 = Double.parseDouble(valorRevestimento3D2_1.getText().toString()) * precoRevestimento3D;

                valorTotalRevestimentoBanheiroSocial = varAdicionarAlvenaria2 + varAdicionarAlvenaria2_1 + varAdicionarContraPiso2 + varAdicionarContraPiso2_1 + varAplicacaoImpermeabilizante2 + varAplicacaoImpermeabilizante2_1 + varAplicarPorcelanatoMaior2 + varAplicarPorcelanatoMaior2_1 + varAplicarPorcelanatoMenor2 + varAplicarPorcelanatoMenor2_1 + varPastilhaVidro_2 + varPastilhaVidro_2_1 + varRevestimento3D_2 + varRevestimento3D_2_1;


                // Valores Revestimento Area de Servico
                varAdicionarAlvenaria3 = Double.parseDouble(valorRevestimentoAlvenariaBase3.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria3_1 = Double.parseDouble(valorRevestimentoAlvenariaBase3_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso3 = Double.parseDouble(valorRevestimentoContraPiso3.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso3_1 = Double.parseDouble(valorRevestimentoContraPiso3_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante3 = Double.parseDouble(valorRevestimentoImpermeabilidade3.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante3_1 = Double.parseDouble(valorRevestimentoImpermeabilidade3_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior3 = Double.parseDouble(valorRevestimentoPorcelanatoAcima3.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior3_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima3_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor3 = Double.parseDouble(valorRevestimentoPorcelanatoMenor3.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor3_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor3_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_3 = Double.parseDouble(valorRevestimentoPastilhaVidro3.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_3_1 = Double.parseDouble(valorRevestimentoPastilhaVidro3_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_3 = Double.parseDouble(valorRevestimento3D3.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_3_1 = Double.parseDouble(valorRevestimento3D3_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoAreaServico = varAdicionarAlvenaria3 + varAdicionarAlvenaria3_1 + varAdicionarContraPiso3 + varAdicionarContraPiso3_1 + varAplicacaoImpermeabilizante3 + varAplicacaoImpermeabilizante3_1 + varAplicarPorcelanatoMaior3 + varAplicarPorcelanatoMaior3_1 + varAplicarPorcelanatoMenor3 + varAplicarPorcelanatoMenor3_1 + varPastilhaVidro_3 + varPastilhaVidro_3_1 + varRevestimento3D_3 + varRevestimento3D_3_1;


                //Valores Revestimento Banheiro Suite
                varAdicionarAlvenaria4 = Double.parseDouble(valorRevestimentoAlvenariaBase4.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria4_1 = Double.parseDouble(valorRevestimentoAlvenariaBase4_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso4 = Double.parseDouble(valorRevestimentoContraPiso4.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso4_1 = Double.parseDouble(valorRevestimentoContraPiso4_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante4 = Double.parseDouble(valorRevestimentoImpermeabilidade4.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante4_1 = Double.parseDouble(valorRevestimentoImpermeabilidade4_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior4 = Double.parseDouble(valorRevestimentoPorcelanatoAcima4.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior4_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima4_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor4 = Double.parseDouble(valorRevestimentoPorcelanatoMenor4.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor4_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor4_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_4 = Double.parseDouble(valorRevestimentoPastilhaVidro4.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_4_1 = Double.parseDouble(valorRevestimentoPastilhaVidro4_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_4 = Double.parseDouble(valorRevestimento3D4.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_4_1 = Double.parseDouble(valorRevestimento3D4_1.getText().toString()) * precoRevestimento3D;

                valorTotalRevestimentoBanheiroSuite = varRevestimento3D_4_1 + varRevestimento3D_4 + varPastilhaVidro_4_1 + varPastilhaVidro_4 + varAplicarPorcelanatoMenor4 + varAplicarPorcelanatoMaior4_1 + varAplicarPorcelanatoMaior4 + varAdicionarAlvenaria4 + varAdicionarAlvenaria4_1 + varAdicionarContraPiso4 + varAdicionarContraPiso4_1 + varAplicacaoImpermeabilizante4 + varAplicacaoImpermeabilizante4_1;


                //Valores Revestimento Lavabo
                varAdicionarAlvenaria5 = Double.parseDouble(valorRevestimentoAlvenariaBase5.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria5_1 = Double.parseDouble(valorRevestimentoAlvenariaBase5_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso5 = Double.parseDouble(valorRevestimentoContraPiso5.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso5_1 = Double.parseDouble(valorRevestimentoContraPiso5_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante5 = Double.parseDouble(valorRevestimentoImpermeabilidade5.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante5_1 = Double.parseDouble(valorRevestimentoImpermeabilidade5_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior5 = Double.parseDouble(valorRevestimentoPorcelanatoAcima5.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior5_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima5_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor5 = Double.parseDouble(valorRevestimentoPorcelanatoMenor5.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor5_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor5_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_5 = Double.parseDouble(valorRevestimentoPastilhaVidro5.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_5_1 = Double.parseDouble(valorRevestimentoPastilhaVidro5_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_5 = Double.parseDouble(valorRevestimento3D5.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_5_1 = Double.parseDouble(valorRevestimento3D5_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoLavabo = varAdicionarAlvenaria5 + varAdicionarAlvenaria5_1 + varAdicionarContraPiso5 + varAdicionarContraPiso5_1 + varAplicacaoImpermeabilizante5 + varAplicacaoImpermeabilizante5_1 + varAplicarPorcelanatoMaior5 + varAplicarPorcelanatoMaior5_1 + varAplicarPorcelanatoMenor5 + varAplicarPorcelanatoMenor5_1 + varPastilhaVidro_5 + varPastilhaVidro_5_1 + varRevestimento3D_5 + varRevestimento3D_5_1;

                //Valores Revestimento Sacada

                varAdicionarAlvenaria6 = Double.parseDouble(valorRevestimentoAlvenariaBase6.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria6_1 = Double.parseDouble(valorRevestimentoAlvenariaBase6_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso6 = Double.parseDouble(valorRevestimentoContraPiso6.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso6_1 = Double.parseDouble(valorRevestimentoContraPiso6_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante6 = Double.parseDouble(valorRevestimentoImpermeabilidade6.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante6_1 = Double.parseDouble(valorRevestimentoImpermeabilidade6_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior6 = Double.parseDouble(valorRevestimentoPorcelanatoAcima6.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior6_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima6_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor6 = Double.parseDouble(valorRevestimentoPorcelanatoMenor6.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor6_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor6_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_6 = Double.parseDouble(valorRevestimentoPastilhaVidro6.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_6_1 = Double.parseDouble(valorRevestimentoPastilhaVidro6_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_6 = Double.parseDouble(valorRevestimento3D6.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_6_1 = Double.parseDouble(valorRevestimento3D6_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoSacada = varAdicionarAlvenaria6 + varAdicionarAlvenaria6_1 + varAdicionarContraPiso6 + varAdicionarContraPiso6_1 + varAplicacaoImpermeabilizante6 + varAplicacaoImpermeabilizante6_1 + varAplicarPorcelanatoMaior6 + varAplicarPorcelanatoMaior6_1 + varAplicarPorcelanatoMenor6 + varAplicarPorcelanatoMenor6_1 + varPastilhaVidro_6 + varPastilhaVidro_6_1 + varRevestimento3D_6 + varRevestimento3D_6_1;

                //Revestimento de Sala de Jantar
                varAdicionarAlvenaria7 = Double.parseDouble(valorRevestimentoAlvenariaBase7.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria7_1 = Double.parseDouble(valorRevestimentoAlvenariaBase7_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso7 = Double.parseDouble(valorRevestimentoContraPiso7.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso7_1 = Double.parseDouble(valorRevestimentoContraPiso7_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante7 = Double.parseDouble(valorRevestimentoImpermeabilidade7.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante7_1 = Double.parseDouble(valorRevestimentoImpermeabilidade7_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior7 = Double.parseDouble(valorRevestimentoPorcelanatoAcima7.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior7_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima7_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor7 = Double.parseDouble(valorRevestimentoPorcelanatoMenor7.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor7_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor7_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_7 = Double.parseDouble(valorRevestimentoPastilhaVidro7.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_7_1 = Double.parseDouble(valorRevestimentoPastilhaVidro7_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_7 = Double.parseDouble(valorRevestimento3D7.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_7_1 = Double.parseDouble(valorRevestimento3D7_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoSalaJantar = varAdicionarAlvenaria7 + varAdicionarAlvenaria7_1 + varAdicionarContraPiso7 + varAdicionarContraPiso7_1 + varAplicacaoImpermeabilizante7 + varAplicacaoImpermeabilizante7_1 + varAplicarPorcelanatoMaior7 + varAplicarPorcelanatoMaior7_1 + varAplicarPorcelanatoMenor7 + varAplicarPorcelanatoMenor7_1 + varPastilhaVidro_7 + varPastilhaVidro_7_1 + varRevestimento3D_7 + varRevestimento3D_7_1;


                //Valores Revestimento Sala Estar
                varAdicionarAlvenaria8 = Double.parseDouble(valorRevestimentoAlvenariaBase8.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria8_1 = Double.parseDouble(valorRevestimentoAlvenariaBase8_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso8 = Double.parseDouble(valorRevestimentoContraPiso8.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso8_1 = Double.parseDouble(valorRevestimentoContraPiso8_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante8 = Double.parseDouble(valorRevestimentoImpermeabilidade8.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante8_1 = Double.parseDouble(valorRevestimentoImpermeabilidade8_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior8 = Double.parseDouble(valorRevestimentoPorcelanatoAcima8.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior8_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima8_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor8 = Double.parseDouble(valorRevestimentoPorcelanatoMenor8.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor8_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor8_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_8 = Double.parseDouble(valorRevestimentoPastilhaVidro8.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_8_1 = Double.parseDouble(valorRevestimentoPastilhaVidro8_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_8 = Double.parseDouble(valorRevestimento3D8.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_8_1 = Double.parseDouble(valorRevestimento3D8_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoSalaEstar = varAdicionarAlvenaria8 + varAdicionarAlvenaria8_1 + varAdicionarContraPiso8 + varAdicionarContraPiso8_1 + varAplicacaoImpermeabilizante8 + varAplicacaoImpermeabilizante8_1 + varAplicarPorcelanatoMaior8 + varAplicarPorcelanatoMaior8_1 + varAplicarPorcelanatoMenor8 + varAplicarPorcelanatoMenor8_1 + varPastilhaVidro_8 + varPastilhaVidro_8_1 + varRevestimento3D_8 + varRevestimento3D_8_1;


                //Valores Revestimento Quarto Suite
                varAdicionarAlvenaria9 = Double.parseDouble(valorRevestimentoAlvenariaBase9.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria9_1 = Double.parseDouble(valorRevestimentoAlvenariaBase9_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso9 = Double.parseDouble(valorRevestimentoContraPiso9.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso9_1 = Double.parseDouble(valorRevestimentoContraPiso9_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante9 = Double.parseDouble(valorRevestimentoImpermeabilidade9.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante9_1 = Double.parseDouble(valorRevestimentoImpermeabilidade9_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior9 = Double.parseDouble(valorRevestimentoPorcelanatoAcima9.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior9_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima9_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor9 = Double.parseDouble(valorRevestimentoPorcelanatoMenor9.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor9_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor9_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_9 = Double.parseDouble(valorRevestimentoPastilhaVidro9.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_9_1 = Double.parseDouble(valorRevestimentoPastilhaVidro9_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_9 = Double.parseDouble(valorRevestimento3D9.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_9_1 = Double.parseDouble(valorRevestimento3D9_1.getText().toString()) * precoRevestimento3D;

                valorTotalRevestimentoQuartoSuite = varAdicionarAlvenaria9 + varAdicionarAlvenaria9_1 + varAdicionarContraPiso9 + varAdicionarContraPiso9_1 + varAplicacaoImpermeabilizante9 + varAplicacaoImpermeabilizante9_1 + varAplicarPorcelanatoMaior9 + varAplicarPorcelanatoMaior9_1 + varAplicarPorcelanatoMenor9 + varAplicarPorcelanatoMenor9_1 + varPastilhaVidro_9 + varPastilhaVidro_9_1 + varRevestimento3D_9 + varRevestimento3D_9_1;

                //Valores Revstimento Quarto 1
                varAdicionarAlvenaria10 = Double.parseDouble(valorRevestimentoAlvenariaBase10.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria10_1 = Double.parseDouble(valorRevestimentoAlvenariaBase10_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso10 = Double.parseDouble(valorRevestimentoContraPiso10.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso10_1 = Double.parseDouble(valorRevestimentoContraPiso10_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante10 = Double.parseDouble(valorRevestimentoImpermeabilidade10.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante10_1 = Double.parseDouble(valorRevestimentoImpermeabilidade10_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior10 = Double.parseDouble(valorRevestimentoPorcelanatoAcima10.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior10_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima10_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor10 = Double.parseDouble(valorRevestimentoPorcelanatoMenor10.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor10_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor10_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_10 = Double.parseDouble(valorRevestimentoPastilhaVidro10.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_10_1 = Double.parseDouble(valorRevestimentoPastilhaVidro10_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_10 = Double.parseDouble(valorRevestimento3D10.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_10_1 = Double.parseDouble(valorRevestimento3D10_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoQuarto1 = varAdicionarAlvenaria10 + varAdicionarAlvenaria10_1 + varAdicionarContraPiso10 + varAdicionarContraPiso10_1 + varAplicacaoImpermeabilizante10 + varAplicacaoImpermeabilizante10_1 + varAplicarPorcelanatoMaior10 + varAplicarPorcelanatoMaior10_1 + varAplicarPorcelanatoMenor10 + varAplicarPorcelanatoMenor10_1 + varPastilhaVidro_10 + varPastilhaVidro_10_1 + varRevestimento3D_10 + varRevestimento3D_10_1;


                //Valores Revestimento Quarto 2
                varAdicionarAlvenaria11 = Double.parseDouble(valorRevestimentoAlvenariaBase11.getText().toString()) * precoCriarAlvenaria;
                varAdicionarAlvenaria11_1 = Double.parseDouble(valorRevestimentoAlvenariaBase11_1.getText().toString()) * precoCriarAlvenaria;
                varAdicionarContraPiso11 = Double.parseDouble(valorRevestimentoContraPiso11.getText().toString()) * precoCriarContraPiso;
                varAdicionarContraPiso11_1 = Double.parseDouble(valorRevestimentoContraPiso11_1.getText().toString()) * precoCriarContraPiso;
                varAplicacaoImpermeabilizante11 = Double.parseDouble(valorRevestimentoImpermeabilidade11.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicacaoImpermeabilizante11_1 = Double.parseDouble(valorRevestimentoImpermeabilidade11_1.getText().toString()) * precoAplicarImpermeabilizante;
                varAplicarPorcelanatoMaior11 = Double.parseDouble(valorRevestimentoPorcelanatoAcima11.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMaior11_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima11_1.getText().toString()) * precoPorcelanatoMaior;
                varAplicarPorcelanatoMenor11 = Double.parseDouble(valorRevestimentoPorcelanatoMenor11.getText().toString()) * precoPorcelanatoMenor;
                varAplicarPorcelanatoMenor11_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor11_1.getText().toString()) * precoPorcelanatoMenor;
                varPastilhaVidro_11 = Double.parseDouble(valorRevestimentoPastilhaVidro11.getText().toString()) * precoPastilhaVidro;
                varPastilhaVidro_11_1 = Double.parseDouble(valorRevestimentoPastilhaVidro11_1.getText().toString()) * precoPastilhaVidro;
                varRevestimento3D_11 = Double.parseDouble(valorRevestimento3D11.getText().toString()) * precoRevestimento3D;
                varRevestimento3D_11_1 = Double.parseDouble(valorRevestimento3D11_1.getText().toString()) * precoRevestimento3D;


                valorTotalRevestimentoQuarto2 = varAdicionarAlvenaria11 + varAdicionarAlvenaria11_1 + varAdicionarContraPiso11 + varAdicionarContraPiso11_1 + varAplicacaoImpermeabilizante11 + varAplicacaoImpermeabilizante11_1 + varAplicarPorcelanatoMaior11 + varAplicarPorcelanatoMaior11_1 + varAplicarPorcelanatoMenor11 + varAplicarPorcelanatoMenor11_1 + varPastilhaVidro_11 + varPastilhaVidro_11_1 + varRevestimento3D_11 + varRevestimento3D_11_1;

                valorTotalCategoriaRevestimento = valorTotalRevestimentoAreaServico + valorTotalRevestimentoQuarto2 + valorTotalRevestimentoQuarto1 + valorTotalRevestimentoQuartoSuite + valorTotalRevestimentoSalaEstar + valorTotalRevestimentoSalaJantar + valorTotalRevestimentoSacada + valorTotalRevestimentoLavabo + valorTotalRevestimentoAreaServico + valorTotalRevestimentoBanheiroSocial + valorTotalRevestimentoCozinha;


                //Valores Hidraulica
                varAdicionarTorneiraEletrica = Double.parseDouble(valorHidraulicaTorneiraEletrica1.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraEletrica1 = Double.parseDouble(valorHidraulicaTorneiraEletrica1_1.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraMonocomando = Double.parseDouble(valorHidraulicaTorneiraMonocomando1.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraMonocomando1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando1_1.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraSimples = Double.parseDouble(valorHidraulicaTorneiraSimples1.getText().toString()) * precoTorneiraSimples;
                varAdicionarTorneiraSimples1 = Double.parseDouble(valorHidraulicaTorneiraSimples1_1.getText().toString()) * precoTorneiraSimples;
                varAdicionarValvulaSifao = Double.parseDouble(valorHidraulicaValvulaSifao1.getText().toString()) * precoValvulaSifao;
                varAdicionarValvulaSifao1 = Double.parseDouble(valorHidraulicaValvulaSifao1_1.getText().toString()) * precoValvulaSifao;
                varAdicionarRegistroAcabamento = Double.parseDouble(valorHidraulicaRegistroAcabamento1.getText().toString()) * precoRegistroAcabamento;
                varAdicionarRegistroAcabamento1 = Double.parseDouble(valorHidraulicaRegistroAcabamento1_1.getText().toString()) * precoRegistroAcabamento;
                varAdicionarPontoAgua = Double.parseDouble(valorHidraulicaCriacaoAgua1.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua1_1.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoEsgoto = Double.parseDouble(valorHidraulicaCriacaoEsgoto1.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto1_1.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarRalo10cm = Double.parseDouble(valorHidraulicaRalo10cm1.getText().toString()) * precoRalo10cm;
                varAdicionarRalo10cm1 = Double.parseDouble(valorHidraulicaRalo10cm1_1.getText().toString()) * precoRalo10cm;
                varAdicionarRalo15cm = Double.parseDouble(valorHidraulicaRalo15cm1.getText().toString()) * precoRalo15cm;
                varAdicionarRalo15cm1 = Double.parseDouble(valorHidraulicaRalo15cm1_1.getText().toString()) * precoRalo15cm;

                valorTotalHidraulicaCozinha = varAdicionarTorneiraEletrica + varAdicionarTorneiraEletrica1 + varAdicionarTorneiraMonocomando + varAdicionarTorneiraMonocomando1 + varAdicionarTorneiraSimples + varAdicionarTorneiraSimples1 + varAdicionarValvulaSifao + varAdicionarValvulaSifao1 + varAdicionarRegistroAcabamento + varAdicionarRegistroAcabamento1 + varAdicionarPontoAgua + varAdicionarPontoAgua_1 + varAdicionarPontoEsgoto + varAdicionarPontoEsgoto_1 + varAdicionarRalo10cm + varAdicionarRalo10cm1 + varAdicionarRalo15cm + varAdicionarRalo15cm1;


                //Valores Hidraulica
                varAdicionarTorneiraEletrica2 = Double.parseDouble(valorHidraulicaTorneiraEletrica2.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraEletrica2 = Double.parseDouble(valorHidraulicaTorneiraEletrica2_1.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraMonocomando2 = Double.parseDouble(valorHidraulicaTorneiraMonocomando2.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraMonocomando2_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando2_1.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraSimples2 = Double.parseDouble(valorHidraulicaTorneiraSimples2.getText().toString()) * precoTorneiraSimples;
                varAdicionarTorneiraSimples2_1 = Double.parseDouble(valorHidraulicaTorneiraSimples2_1.getText().toString()) * precoTorneiraSimples;
                varAdicionarValvulaSifao2 = Double.parseDouble(valorHidraulicaValvulaSifao2.getText().toString()) * precoValvulaSifao;
                varAdicionarValvulaSifao2_1 = Double.parseDouble(valorHidraulicaValvulaSifao2_1.getText().toString()) * precoValvulaSifao;
                varAdicionarRegistroAcabamento2 = Double.parseDouble(valorHidraulicaRegistroAcabamento2.getText().toString()) * precoRegistroAcabamento;
                varAdicionarRegistroAcabamento = Double.parseDouble(valorHidraulicaRegistroAcabamento2_1.getText().toString()) * precoRegistroAcabamento;
                varAdicionarPontoAgua_2 = Double.parseDouble(valorHidraulicaCriacaoAgua2.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua2_1.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoEsgoto_2 = Double.parseDouble(valorHidraulicaCriacaoEsgoto2.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto2_1.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarRalo10cm2 = Double.parseDouble(valorHidraulicaRalo10cm2.getText().toString()) * precoRalo10cm;
                varAdicionarRalo10cm2_1 = Double.parseDouble(valorHidraulicaRalo10cm2_1.getText().toString()) * precoRalo10cm;
                varAdicionarRalo15cm2 = Double.parseDouble(valorHidraulicaRalo15cm2.getText().toString()) * precoRalo15cm;
                varAdicionarRalo15cm2_1 = Double.parseDouble(valorHidraulicaRalo15cm2_1.getText().toString()) * precoRalo15cm;
                varAdicionarChuveiro2 = Double.parseDouble(valorHidraulicaChuveiro2.getText().toString()) * precoChuveiro;
                varAdicionarChuveiro2_1 = Double.parseDouble(valorHidraulicaChuveiro2_1.getText().toString()) * precoChuveiro;
                varAdicionarVasoSanitario2 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario2.getText().toString()) * precoInstalarVasoSanitario;
                varAdicionarVasoSanitario2_1 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario2_1.getText().toString()) * precoInstalarVasoSanitario;
                varAdicionarRaloLinear2 = Double.parseDouble(valorHidraulicaRaloLinear2.getText().toString()) * precoRaloLinear;
                varAdicionarRaloLinear2_1 = Double.parseDouble(valorHidraulicaRaloLinear2_1.getText().toString()) * precoRaloLinear;

                valorTotalHidraulicaBanheiroSocial = varAdicionarChuveiro2 + varAdicionarChuveiro2_1 + varAdicionarVasoSanitario2 + varAdicionarVasoSanitario2_1 + varAdicionarRaloLinear2 + varAdicionarRaloLinear2_1 + varAdicionarTorneiraEletrica2 + varAdicionarTorneiraEletrica2_1 + varAdicionarTorneiraMonocomando2 + varAdicionarTorneiraMonocomando2_1 + varAdicionarTorneiraSimples2 + varAdicionarTorneiraSimples2_1 + varAdicionarValvulaSifao2 + varAdicionarValvulaSifao2_1 + varAdicionarRegistroAcabamento2 + varAdicionarRegistroAcabamento2_1 + varAdicionarPontoAgua_2 + varAdicionarPontoAgua_2_1 + varAdicionarPontoEsgoto_2 + varAdicionarPontoEsgoto_2_1 + varAdicionarRalo10cm2 + varAdicionarRalo10cm2_1 + varAdicionarRalo15cm2 + varAdicionarRalo15cm2_1;


                varAdicionarTorneiraEletrica3 = Double.parseDouble(valorHidraulicaTorneiraEletrica3.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraEletrica3 = Double.parseDouble(valorHidraulicaTorneiraEletrica3_1.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraMonocomando3 = Double.parseDouble(valorHidraulicaTorneiraMonocomando3.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraMonocomando3_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando3_1.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraSimples3 = Double.parseDouble(valorHidraulicaTorneiraSimples3.getText().toString()) * precoTorneiraSimples;
                varAdicionarTorneiraSimples3_1 = Double.parseDouble(valorHidraulicaTorneiraSimples3_1.getText().toString()) * precoTorneiraSimples;
                varAdicionarValvulaSifao3 = Double.parseDouble(valorHidraulicaValvulaSifao3.getText().toString()) * precoValvulaSifao;
                varAdicionarValvulaSifao3_1 = Double.parseDouble(valorHidraulicaValvulaSifao3_1.getText().toString()) * precoValvulaSifao;
                varAdicionarRegistroAcabamento3 = Double.parseDouble(valorHidraulicaRegistroAcabamento3.getText().toString()) * precoRegistroAcabamento;
                varAdicionarRegistroAcabamento3_1 = Double.parseDouble(valorHidraulicaRegistroAcabamento3_1.getText().toString()) * precoRegistroAcabamento;
                varAdicionarPontoAgua_3 = Double.parseDouble(valorHidraulicaCriacaoAgua3.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoAgua_3_1 = Double.parseDouble(valorHidraulicaCriacaoAgua3_1.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoEsgoto_3 = Double.parseDouble(valorHidraulicaCriacaoEsgoto3.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarPontoEsgoto_3_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto3_1.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarRalo10cm3 = Double.parseDouble(valorHidraulicaRalo10cm3.getText().toString()) * precoRalo10cm;
                varAdicionarRalo10cm3_1 = Double.parseDouble(valorHidraulicaRalo10cm3_1.getText().toString()) * precoRalo10cm;
                varAdicionarRalo15cm3 = Double.parseDouble(valorHidraulicaRalo15cm3.getText().toString()) * precoRalo15cm;
                varAdicionarRalo15cm3_1 = Double.parseDouble(valorHidraulicaRalo15cm3_1.getText().toString()) * precoRalo15cm;


                valorTotalHidraulicaAreaServico = varAdicionarTorneiraEletrica3 + varAdicionarTorneiraEletrica3_1 + varAdicionarTorneiraMonocomando3 + varAdicionarTorneiraMonocomando3_1 + varAdicionarTorneiraSimples3 + varAdicionarTorneiraSimples3_1 + varAdicionarValvulaSifao3 + varAdicionarValvulaSifao3_1 + varAdicionarRegistroAcabamento3 + varAdicionarRegistroAcabamento3_1 + varAdicionarPontoAgua_3 + varAdicionarPontoAgua_3_1 + varAdicionarPontoEsgoto_3 + varAdicionarPontoEsgoto_3_1 + varAdicionarRalo10cm3 + varAdicionarRalo10cm3_1 + varAdicionarRalo15cm3 + varAdicionarRalo15cm3_1;

                varAdicionarTorneiraEletrica4 = Double.parseDouble(valorHidraulicaTorneiraEletrica4.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraEletrica4 = Double.parseDouble(valorHidraulicaTorneiraEletrica4_1.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraMonocomando4 = Double.parseDouble(valorHidraulicaTorneiraMonocomando4.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraMonocomando4_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando4_1.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraSimples4 = Double.parseDouble(valorHidraulicaTorneiraSimples4.getText().toString()) * precoTorneiraSimples;
                varAdicionarTorneiraSimples4_1 = Double.parseDouble(valorHidraulicaTorneiraSimples4_1.getText().toString()) * precoTorneiraSimples;
                varAdicionarValvulaSifao4 = Double.parseDouble(valorHidraulicaValvulaSifao4.getText().toString()) * precoValvulaSifao;
                varAdicionarValvulaSifao4_1 = Double.parseDouble(valorHidraulicaValvulaSifao4_1.getText().toString()) * precoValvulaSifao;
                varAdicionarRegistroAcabamento4 = Double.parseDouble(valorHidraulicaRegistroAcabamento4.getText().toString()) * precoRegistroAcabamento;
                varAdicionarRegistroAcabamento4_1 = Double.parseDouble(valorHidraulicaRegistroAcabamento4_1.getText().toString()) * precoRegistroAcabamento;
                varAdicionarPontoAgua_4 = Double.parseDouble(valorHidraulicaCriacaoAgua4.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoAgua_4_1 = Double.parseDouble(valorHidraulicaCriacaoAgua4_1.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoEsgoto_4 = Double.parseDouble(valorHidraulicaCriacaoEsgoto4.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarPontoEsgoto_4_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto4_1.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarRalo10cm4 = Double.parseDouble(valorHidraulicaRalo10cm4.getText().toString()) * precoRalo10cm;
                varAdicionarRalo10cm4_1 = Double.parseDouble(valorHidraulicaRalo10cm4_1.getText().toString()) * precoRalo10cm;
                varAdicionarRalo15cm4 = Double.parseDouble(valorHidraulicaRalo15cm4.getText().toString()) * precoRalo15cm;
                varAdicionarRalo15cm4_1 = Double.parseDouble(valorHidraulicaRalo15cm4_1.getText().toString()) * precoRalo15cm;
                varAdicionarChuveiro4 = Double.parseDouble(valorHidraulicaChuveiro4.getText().toString()) * precoChuveiro;
                varAdicionarChuveiro4_1 = Double.parseDouble(valorHidraulicaChuveiro4_1.getText().toString()) * precoChuveiro;
                varAdicionarVasoSanitario4 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario4.getText().toString()) * precoInstalarVasoSanitario;
                varAdicionarVasoSanitario4_1 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario4_1.getText().toString()) * precoInstalarVasoSanitario;
                varAdicionarRaloLinear4 = Double.parseDouble(valorHidraulicaRaloLinear4.getText().toString()) * precoRaloLinear;
                varAdicionarRaloLinear4_1 = Double.parseDouble(valorHidraulicaRaloLinear4_1.getText().toString()) * precoRaloLinear;

                valorTotalHidraulicaBanheiroSuite = varAdicionarChuveiro4 + varAdicionarChuveiro4_1 + varAdicionarVasoSanitario4 + varAdicionarVasoSanitario4_1 + varAdicionarRaloLinear4 + varAdicionarRaloLinear4_1 + varAdicionarTorneiraEletrica4 + varAdicionarTorneiraEletrica4_1 + varAdicionarTorneiraMonocomando4 + varAdicionarTorneiraMonocomando4_1 + varAdicionarTorneiraSimples4 + varAdicionarTorneiraSimples4_1 + varAdicionarValvulaSifao4 + varAdicionarValvulaSifao4_1 + varAdicionarRegistroAcabamento4 + varAdicionarRegistroAcabamento4_1 + varAdicionarPontoAgua_4 + varAdicionarPontoAgua_4_1 + varAdicionarPontoEsgoto_4 + varAdicionarPontoEsgoto_4_1 + varAdicionarRalo10cm4 + varAdicionarRalo10cm4_1 + varAdicionarRalo15cm4 + varAdicionarRalo15cm4_1;


                varAdicionarTorneiraEletrica5 = Double.parseDouble(valorHidraulicaTorneiraEletrica5.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraEletrica5 = Double.parseDouble(valorHidraulicaTorneiraEletrica5_1.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraMonocomando5 = Double.parseDouble(valorHidraulicaTorneiraMonocomando5.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraMonocomando5_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando5_1.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraSimples5 = Double.parseDouble(valorHidraulicaTorneiraSimples5.getText().toString()) * precoTorneiraSimples;
                varAdicionarTorneiraSimples5_1 = Double.parseDouble(valorHidraulicaTorneiraSimples5_1.getText().toString()) * precoTorneiraSimples;
                varAdicionarValvulaSifao5 = Double.parseDouble(valorHidraulicaValvulaSifao5.getText().toString()) * precoValvulaSifao;
                varAdicionarValvulaSifao5_1 = Double.parseDouble(valorHidraulicaValvulaSifao5_1.getText().toString()) * precoValvulaSifao;
                varAdicionarRegistroAcabamento5 = Double.parseDouble(valorHidraulicaRegistroAcabamento5.getText().toString()) * precoRegistroAcabamento;
                varAdicionarRegistroAcabamento5_1 = Double.parseDouble(valorHidraulicaRegistroAcabamento5_1.getText().toString()) * precoRegistroAcabamento;
                varAdicionarPontoAgua_5 = Double.parseDouble(valorHidraulicaCriacaoAgua5.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua5_1.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoEsgoto_5 = Double.parseDouble(valorHidraulicaCriacaoEsgoto5.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto5_1.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarRalo10cm5 = Double.parseDouble(valorHidraulicaRalo10cm5.getText().toString()) * precoRalo10cm;
                varAdicionarRalo10cm5_1 = Double.parseDouble(valorHidraulicaRalo10cm5_1.getText().toString()) * precoRalo10cm;
                varAdicionarRalo15cm5 = Double.parseDouble(valorHidraulicaRalo15cm5.getText().toString()) * precoRalo15cm;
                varAdicionarRalo15cm5_1 = Double.parseDouble(valorHidraulicaRalo15cm5_1.getText().toString()) * precoRalo15cm;
                varAdicionarVasoSanitario5 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario5.getText().toString()) * precoInstalarVasoSanitario;
                varAdicionarVasoSanitario5_1 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario5_1.getText().toString()) * precoInstalarVasoSanitario;
                varAdicionarRaloLinear5 = Double.parseDouble(valorHidraulicaRaloLinear5.getText().toString()) * precoRaloLinear;
                varAdicionarRaloLinear5_1 = Double.parseDouble(valorHidraulicaRaloLinear5_1.getText().toString()) * precoRaloLinear;
                varAdicionarRaloLinear5_1 = Double.parseDouble(valorHidraulicaRaloLinear5_1.getText().toString()) * precoRaloLinear;


                valorTotalHidraulicaLavabo = +varAdicionarVasoSanitario5 + varAdicionarVasoSanitario5_1 + varAdicionarRaloLinear5 + varAdicionarRaloLinear5_1 + varAdicionarTorneiraEletrica5 + varAdicionarTorneiraEletrica5_1 + varAdicionarTorneiraMonocomando5 + varAdicionarTorneiraMonocomando5_1 + varAdicionarTorneiraSimples5 + varAdicionarTorneiraSimples5_1 + varAdicionarValvulaSifao5 + varAdicionarValvulaSifao5_1 + varAdicionarRegistroAcabamento5 + varAdicionarRegistroAcabamento5_1 + varAdicionarPontoAgua_5 + varAdicionarPontoAgua_5_1 + varAdicionarPontoEsgoto_5 + varAdicionarPontoEsgoto_5_1 + varAdicionarRalo10cm5 + varAdicionarRalo10cm5_1 + varAdicionarRalo15cm5 + varAdicionarRalo15cm5_1;

                varAdicionarTorneiraEletrica6 = Double.parseDouble(valorHidraulicaTorneiraEletrica6.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraEletrica6 = Double.parseDouble(valorHidraulicaTorneiraEletrica6_1.getText().toString()) * precoTorneiraEletrica;
                varAdicionarTorneiraMonocomando6 = Double.parseDouble(valorHidraulicaTorneiraMonocomando6.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraMonocomando6_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando6_1.getText().toString()) * precoTorneiraMonocomando;
                varAdicionarTorneiraSimples6 = Double.parseDouble(valorHidraulicaTorneiraSimples6.getText().toString()) * precoTorneiraSimples;
                varAdicionarTorneiraSimples6_1 = Double.parseDouble(valorHidraulicaTorneiraSimples6_1.getText().toString()) * precoTorneiraSimples;
                varAdicionarValvulaSifao6 = Double.parseDouble(valorHidraulicaValvulaSifao6.getText().toString()) * precoValvulaSifao;
                varAdicionarValvulaSifao6_1 = Double.parseDouble(valorHidraulicaValvulaSifao6_1.getText().toString()) * precoValvulaSifao;
                varAdicionarRegistroAcabamento6 = Double.parseDouble(valorHidraulicaRegistroAcabamento6.getText().toString()) * precoRegistroAcabamento;
                varAdicionarRegistroAcabamento = Double.parseDouble(valorHidraulicaRegistroAcabamento6_1.getText().toString()) * precoRegistroAcabamento;
                varAdicionarPontoAgua_6 = Double.parseDouble(valorHidraulicaCriacaoAgua6.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua6_1.getText().toString()) * precoCriacaoAgua;
                varAdicionarPontoEsgoto_6 = Double.parseDouble(valorHidraulicaCriacaoEsgoto6.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto6_1.getText().toString()) * precoCriacaoEsgoto;
                varAdicionarRalo10cm6 = Double.parseDouble(valorHidraulicaRalo10cm6.getText().toString()) * precoRalo10cm;
                varAdicionarRalo10cm6_1 = Double.parseDouble(valorHidraulicaRalo10cm6_1.getText().toString()) * precoRalo10cm;
                varAdicionarRalo15cm6 = Double.parseDouble(valorHidraulicaRalo15cm6.getText().toString()) * precoRalo15cm;
                varAdicionarRalo15cm6_1 = Double.parseDouble(valorHidraulicaRalo15cm6_1.getText().toString()) * precoRalo15cm;


                valorTotalHidraulicaSacada = varAdicionarTorneiraEletrica6 + varAdicionarTorneiraEletrica6_1 + varAdicionarTorneiraMonocomando6 + varAdicionarTorneiraMonocomando6_1 + varAdicionarTorneiraSimples6 + varAdicionarTorneiraSimples6_1 + varAdicionarValvulaSifao6 + varAdicionarValvulaSifao6_1 + varAdicionarRegistroAcabamento6 + varAdicionarRegistroAcabamento6_1 + varAdicionarPontoAgua_6 + varAdicionarPontoAgua_6_1 + varAdicionarPontoEsgoto_6 + varAdicionarPontoEsgoto_6_1 + varAdicionarRalo10cm6 + varAdicionarRalo10cm6_1 + varAdicionarRalo15cm6 + varAdicionarRalo15cm6_1;


                valorTotalCategoriaHidraulica = valorTotalHidraulicaSacada + valorTotalHidraulicaLavabo + valorTotalHidraulicaBanheiroSuite + valorTotalHidraulicaAreaServico + valorTotalHidraulicaBanheiroSocial + valorTotalHidraulicaCozinha;

                //Pintura
                varPorta = Double.parseDouble(valorPinturaPorta1.getText().toString()) * precoPinturaPorta;
                varPorta1 = Double.parseDouble(valorPinturaPorta1_1.getText().toString()) * precoPinturaPorta;
                varJanela = Double.parseDouble(valorPinturaJanela1.getText().toString()) * precoPinturaJanela;
                varJanela1 = Double.parseDouble(valorPinturaJanela1_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo = Double.parseDouble(valorPinturaEfeitoDecorativo1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo1 = Double.parseDouble(valorPinturaEfeitoDecorativo1_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso = Double.parseDouble(valorPinturaReparoGesso1.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso1 = Double.parseDouble(valorPinturaReparoGesso1_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaCozinha = varPorta + varPorta1 + varJanela + varJanela1 + varEfeitoDecorativo + varEfeitoDecorativo1 + varReparoGesso + varReparoGesso1;


                varPorta2 = Double.parseDouble(valorPinturaPorta2.getText().toString()) * precoPinturaPorta;
                varPorta2_1 = Double.parseDouble(valorPinturaPorta2_1.getText().toString()) * precoPinturaPorta;
                varJanela2 = Double.parseDouble(valorPinturaJanela2.getText().toString()) * precoPinturaJanela;
                varJanela2_1 = Double.parseDouble(valorPinturaJanela2_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo2 = Double.parseDouble(valorPinturaEfeitoDecorativo2.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo2_1 = Double.parseDouble(valorPinturaEfeitoDecorativo2_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso2 = Double.parseDouble(valorPinturaReparoGesso2.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso2_1 = Double.parseDouble(valorPinturaReparoGesso2_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaBanheiroSocial = varPorta2 + varPorta2_1 + varJanela2 + varJanela2_1 + varEfeitoDecorativo2 + varEfeitoDecorativo2_1 + varReparoGesso2 + varReparoGesso2_1;


                varPorta3 = Double.parseDouble(valorPinturaPorta3.getText().toString()) * precoPinturaPorta;
                varPorta3_1 = Double.parseDouble(valorPinturaPorta3_1.getText().toString()) * precoPinturaPorta;
                varJanela3 = Double.parseDouble(valorPinturaJanela3.getText().toString()) * precoPinturaJanela;
                varJanela3_1 = Double.parseDouble(valorPinturaJanela3_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo3 = Double.parseDouble(valorPinturaEfeitoDecorativo3.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo3_1 = Double.parseDouble(valorPinturaEfeitoDecorativo3_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso3 = Double.parseDouble(valorPinturaReparoGesso3.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso3_1 = Double.parseDouble(valorPinturaReparoGesso3_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaAreaServico = varPorta3 + varPorta3_1 + varJanela3 + varJanela3_1 + varEfeitoDecorativo3 + varEfeitoDecorativo3_1 + varReparoGesso3 + varReparoGesso3_1;


                varPorta4 = Double.parseDouble(valorPinturaPorta4.getText().toString()) * precoPinturaPorta;
                varPorta4_1 = Double.parseDouble(valorPinturaPorta4_1.getText().toString()) * precoPinturaPorta;
                varJanela4 = Double.parseDouble(valorPinturaJanela4.getText().toString()) * precoPinturaJanela;
                varJanela4_1 = Double.parseDouble(valorPinturaJanela4_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo4 = Double.parseDouble(valorPinturaEfeitoDecorativo4.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo4_1 = Double.parseDouble(valorPinturaEfeitoDecorativo4_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso4 = Double.parseDouble(valorPinturaReparoGesso4.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso4_1 = Double.parseDouble(valorPinturaReparoGesso4_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaBanheiroSuite = varPorta4 + varPorta4_1 + varJanela4 + varJanela4_1 + varEfeitoDecorativo4 + varEfeitoDecorativo4_1 + varReparoGesso4 + varReparoGesso4_1;


                varPorta5 = Double.parseDouble(valorPinturaPorta5.getText().toString()) * precoPinturaPorta;
                varPorta5_1 = Double.parseDouble(valorPinturaPorta5_1.getText().toString()) * precoPinturaPorta;
                varJanela5 = Double.parseDouble(valorPinturaJanela5.getText().toString()) * precoPinturaJanela;
                varJanela5_1 = Double.parseDouble(valorPinturaJanela5_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo5 = Double.parseDouble(valorPinturaEfeitoDecorativo5.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo5_1 = Double.parseDouble(valorPinturaEfeitoDecorativo5_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso5 = Double.parseDouble(valorPinturaReparoGesso5.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso5_1 = Double.parseDouble(valorPinturaReparoGesso5_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaLavabo = varPorta5 + varPorta5_1 + varJanela5 + varJanela5_1 + varEfeitoDecorativo5 + varEfeitoDecorativo5_1 + varReparoGesso5 + varReparoGesso5_1;


                varPorta6 = Double.parseDouble(valorPinturaPorta6.getText().toString()) * precoPinturaPorta;
                varPorta6_1 = Double.parseDouble(valorPinturaPorta6_1.getText().toString()) * precoPinturaPorta;
                varJanela6 = Double.parseDouble(valorPinturaJanela6.getText().toString()) * precoPinturaJanela;
                varJanela6_1 = Double.parseDouble(valorPinturaJanela6_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo6 = Double.parseDouble(valorPinturaEfeitoDecorativo6.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo6_1 = Double.parseDouble(valorPinturaEfeitoDecorativo6_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso6 = Double.parseDouble(valorPinturaReparoGesso6.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso6_1 = Double.parseDouble(valorPinturaReparoGesso6_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaSacada = varPorta6 + varPorta6_1 + varJanela6 + varJanela6_1 + varEfeitoDecorativo6 + varEfeitoDecorativo6_1 + varReparoGesso6 + varReparoGesso6_1;


                varPorta7 = Double.parseDouble(valorPinturaPorta7.getText().toString()) * precoPinturaPorta;
                varPorta7_1 = Double.parseDouble(valorPinturaPorta7_1.getText().toString()) * precoPinturaPorta;
                varJanela7 = Double.parseDouble(valorPinturaJanela7.getText().toString()) * precoPinturaJanela;
                varJanela7_1 = Double.parseDouble(valorPinturaJanela7_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo7 = Double.parseDouble(valorPinturaEfeitoDecorativo7.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo7_1 = Double.parseDouble(valorPinturaEfeitoDecorativo7_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso7 = Double.parseDouble(valorPinturaReparoGesso7.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso7_1 = Double.parseDouble(valorPinturaReparoGesso7_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaSalaJantar = varPorta7 + varPorta7_1 + varJanela7 + varJanela7_1 + varEfeitoDecorativo7 + varEfeitoDecorativo7_1 + varReparoGesso7 + varReparoGesso7_1;


                varPorta8 = Double.parseDouble(valorPinturaPorta8.getText().toString()) * precoPinturaPorta;
                varPorta8_1 = Double.parseDouble(valorPinturaPorta8_1.getText().toString()) * precoPinturaPorta;
                varJanela8 = Double.parseDouble(valorPinturaJanela8.getText().toString()) * precoPinturaJanela;
                varJanela8_1 = Double.parseDouble(valorPinturaJanela8_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo8 = Double.parseDouble(valorPinturaEfeitoDecorativo8.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo8_1 = Double.parseDouble(valorPinturaEfeitoDecorativo8_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso8 = Double.parseDouble(valorPinturaReparoGesso8.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso8_1 = Double.parseDouble(valorPinturaReparoGesso8_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaSalaEstar = varPorta8 + varPorta8_1 + varJanela8 + varJanela8_1 + varEfeitoDecorativo8 + varEfeitoDecorativo8_1 + varReparoGesso8 + varReparoGesso8_1;


                varPorta9 = Double.parseDouble(valorPinturaPorta9.getText().toString()) * precoPinturaPorta;
                varPorta9_1 = Double.parseDouble(valorPinturaPorta9_1.getText().toString()) * precoPinturaPorta;
                varJanela9 = Double.parseDouble(valorPinturaJanela9.getText().toString()) * precoPinturaJanela;
                varJanela9_1 = Double.parseDouble(valorPinturaJanela9_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo9 = Double.parseDouble(valorPinturaEfeitoDecorativo9.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo9_1 = Double.parseDouble(valorPinturaEfeitoDecorativo9_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso9 = Double.parseDouble(valorPinturaReparoGesso9.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso9_1 = Double.parseDouble(valorPinturaReparoGesso9_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaQuarto1 = varPorta9 + varPorta9_1 + varJanela9 + varJanela9_1 + varEfeitoDecorativo9 + varEfeitoDecorativo9_1 + varReparoGesso9 + varReparoGesso9_1;


                varPorta10 = Double.parseDouble(valorPinturaPorta10.getText().toString()) * precoPinturaPorta;
                varPorta10_1 = Double.parseDouble(valorPinturaPorta10_1.getText().toString()) * precoPinturaPorta;
                varJanela10 = Double.parseDouble(valorPinturaJanela10.getText().toString()) * precoPinturaJanela;
                varJanela10_1 = Double.parseDouble(valorPinturaJanela10_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo10 = Double.parseDouble(valorPinturaEfeitoDecorativo10.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo10_1 = Double.parseDouble(valorPinturaEfeitoDecorativo10_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso10 = Double.parseDouble(valorPinturaReparoGesso10.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso10_1 = Double.parseDouble(valorPinturaReparoGesso10_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaQuarto2 = varPorta10 + varPorta10_1 + varJanela10 + varJanela10_1 + varEfeitoDecorativo10 + varEfeitoDecorativo10_1 + varReparoGesso10 + varReparoGesso10_1;


                varPorta11 = Double.parseDouble(valorPinturaPorta11.getText().toString()) * precoPinturaPorta;
                varPorta11_1 = Double.parseDouble(valorPinturaPorta11_1.getText().toString()) * precoPinturaPorta;
                varJanela11 = Double.parseDouble(valorPinturaJanela11.getText().toString()) * precoPinturaJanela;
                varJanela11_1 = Double.parseDouble(valorPinturaJanela11_1.getText().toString()) * precoPinturaJanela;
                varEfeitoDecorativo11 = Double.parseDouble(valorPinturaEfeitoDecorativo11.getText().toString()) * precoPinturaEfeitoDecorativo;
                varEfeitoDecorativo11_1 = Double.parseDouble(valorPinturaEfeitoDecorativo11_1.getText().toString()) * precoPinturaEfeitoDecorativo;
                varReparoGesso11 = Double.parseDouble(valorPinturaReparoGesso11.getText().toString()) * precoPinturaReparoGesso;
                varReparoGesso11_1 = Double.parseDouble(valorPinturaReparoGesso11_1.getText().toString()) * precoPinturaReparoGesso;

                valorTotalPinturaQuartoSuite = varPorta11 + varPorta11_1 + varJanela11 + varJanela11_1 + varEfeitoDecorativo11 + varEfeitoDecorativo11_1 + varReparoGesso11 + varReparoGesso11_1;


                varM2 = Double.parseDouble(valorPinturaApartamento1.getText().toString()) * precoPinturaApartament0;
                varM2_1 = Double.parseDouble(valorPinturaApartamento1_1.getText().toString()) * precoPinturaApartament0;

                valorTotalPinturaApartamento = varM2 + varM2_1;

                valorTotalCategoriaPintura = valorTotalPinturaQuarto2 + valorTotalPinturaApartamento +  valorTotalPinturaQuarto1 + valorTotalPinturaQuartoSuite + valorTotalPinturaSalaEstar + valorTotalPinturaSalaJantar + valorTotalPinturaSacada + valorTotalPinturaLavabo + valorTotalPinturaBanheiroSocial + valorTotalPinturaBanheiroSuite + valorTotalPinturaCozinha + valorTotalPinturaAreaServico;




                View view = getLayoutInflater().inflate(R.layout.layout_edt, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(Main2Activity.this);
                alert.setTitle("Nome do Cliente");
                final EditText input = new EditText(Main2Activity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alert.setView(input);


                alert.setPositiveButton("Prosseguir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog

                                nomeCliente = input.getText().toString();

                                try {
                                    createPdfWrapper();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                alert.create();
                alert.show();

            }
        });

    }

    private void    isAccountValid() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("ValoresCompartilhados");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("numeroNota").getValue() == null)
                    Toast.makeText(Main2Activity.this,     "Voce esta sem internet", Toast.LENGTH_SHORT).show();
                else {
                    numNota = Integer.parseInt(dataSnapshot.child("numeroNota").getValue().toString());
                    valorNota = dataSnapshot.child("valorTotal").getValue().toString();
                    exibirNota.setText("000" + Integer.toString(numNota));
                    exibirValorNota.setText(valorNota);
                    if (valorNota == null) {
                        Toast.makeText(mContext, "Você está sem internet!", Toast.LENGTH_SHORT).show();
                    } else {



                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void carregarValores(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("ValorServico");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("demolicao").child("RemoverRevestimentoParede").getValue() == null)
                    Toast.makeText(Main2Activity.this,     "Voce esta sem internet", Toast.LENGTH_SHORT).show();
                else {

                    //Carregando valores Demolicao
                    precoRemoverRevestimentoParede = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverRevestimentoParede").getValue().toString());
                    precoRemoverPiso = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverPiso").getValue().toString());
                    precoRemoverAlvenaria = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverAlvenaria").getValue().toString());
                    precoRemoverPia = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverPia").getValue().toString());
                    precoRemoverTanque = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverTanque").getValue().toString());
                    precoRasgarCaixinha4x2 = Double.parseDouble(dataSnapshot.child("demolicao").child("RasgarCaixinha4x2").getValue().toString());;
                    precoRasgarCaixinha4x4 = Double.parseDouble(dataSnapshot.child("demolicao").child("RasgarCaixinha4x4").getValue().toString());
                    precoRemoverVasoSanitario = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverVasoSanitario").getValue().toString());
                    precoRemoverVao = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverVao").getValue().toString());
                    precoRemoverHidraulica = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverHidraulica").getValue().toString());
                    precoRemoverGesso = Double.parseDouble(dataSnapshot.child("demolicao").child("RemoverGesso").getValue().toString());

                    //Hidraulica
                    precoTorneiraEletrica = Double.parseDouble(dataSnapshot.child("hidraulica").child("TorneiraEletrica").getValue().toString());
                    precoTorneiraMonocomando = Double.parseDouble(dataSnapshot.child("hidraulica").child("TorneiraMonocomando").getValue().toString());
                    precoTorneiraSimples = Double.parseDouble(dataSnapshot.child("hidraulica").child("TorneiraSimples").getValue().toString());
                    precoValvulaSifao = Double.parseDouble(dataSnapshot.child("hidraulica").child("TorneiraEletrica").getValue().toString());
                    precoRegistroAcabamento = Double.parseDouble(dataSnapshot.child("hidraulica").child("RegistroAcabamento").getValue().toString());
                    precoCriacaoAgua = Double.parseDouble(dataSnapshot.child("hidraulica").child("CriacaoAgua").getValue().toString());
                    precoCriacaoEsgoto = Double.parseDouble(dataSnapshot.child("hidraulica").child("CriacaoEsgoto").getValue().toString());
                    precoRalo10cm = Double.parseDouble(dataSnapshot.child("hidraulica").child("Ralo10cm").getValue().toString());
                    precoRalo15cm = Double.parseDouble(dataSnapshot.child("hidraulica").child("Ralo15cm").getValue().toString());
                    precoChuveiro = Double.parseDouble(dataSnapshot.child("hidraulica").child("Chuveiro").getValue().toString());
                    precoRaloLinear = Double.parseDouble(dataSnapshot.child("hidraulica").child("RaloLinear").getValue().toString());
                    precoInstalarVasoSanitario = Double.parseDouble(dataSnapshot.child("hidraulica").child("InstalarVasoSanitario").getValue().toString());

                    //Revestimento
                    precoCriarAlvenaria = Double.parseDouble(dataSnapshot.child("revestimento").child("CriarAlvenaria").getValue().toString());
                    precoCriarContraPiso = Double.parseDouble(dataSnapshot.child("revestimento").child("CriarContraPiso").getValue().toString());
                    precoAplicarImpermeabilizante = Double.parseDouble(dataSnapshot.child("revestimento").child("AplicarImpermeabilizante").getValue().toString());
                    precoPorcelanatoMenor = Double.parseDouble(dataSnapshot.child("revestimento").child("PorcelanatoMenor").getValue().toString());
                    precoPorcelanatoMaior = Double.parseDouble(dataSnapshot.child("revestimento").child("PorcelanatoMaior").getValue().toString());
                    precoPastilhaVidro = Double.parseDouble(dataSnapshot.child("revestimento").child("PastilhaVidro").getValue().toString());
                    precoRevestimento3D = Double.parseDouble(dataSnapshot.child("revestimento").child("Revestimento3D").getValue().toString());

                    //Art
                    valorTotalArtArcondicionado = Double.parseDouble(dataSnapshot.child("art").child("ArtArcondicionado").getValue().toString());
                    valorTotalArtEnvidracamento = Double.parseDouble(dataSnapshot.child("art").child("ArtEnvidracamento").getValue().toString());
                    valorTotalArtPedrasMarmore = Double.parseDouble(dataSnapshot.child("art").child("ArtPedrasMarmore").getValue().toString());
                    valorTotalArtNovosRevestimentos = Double.parseDouble(dataSnapshot.child("art").child("ArtNovosRevestimentos").getValue().toString());
                    valorTotalArtEletrica = Double.parseDouble(dataSnapshot.child("art").child("ArtEletrica").getValue().toString());
                    valorTotalArtHidraulica = Double.parseDouble(dataSnapshot.child("art").child("ArtHidraulica").getValue().toString());
                    valorTotalArtBox = Double.parseDouble(dataSnapshot.child("art").child("ArtBox").getValue().toString());
                    valorTotalArtGesso = Double.parseDouble(dataSnapshot.child("art").child("ArtGesso").getValue().toString());
                    valorTotalArtDemolicaoParedeNaoEstrutural = Double.parseDouble(dataSnapshot.child("art").child("ArtDemolicaoParedeNaoEstrutural").getValue().toString());
                    valorTotalArtMoveisPlanejados = Double.parseDouble(dataSnapshot.child("art").child("ArtMoveisPlanejados").getValue().toString());
                    valorTotalArtDeslocamentoPontoGas = Double.parseDouble(dataSnapshot.child("art").child("ArtDeslocamentoPontoGas").getValue().toString());
                    valorTotalArtTaxa = Double.parseDouble(dataSnapshot.child("art").child("ArtTaxa").getValue().toString());




                    if (precoRemoverRevestimentoParede == 0) {
                        Toast.makeText(mContext, "Você está sem internet!", Toast.LENGTH_SHORT).show();
                    } else {



                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void previewPdf() {


        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("*application/pdf*");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Toast.makeText(this, "Chegou até aqui", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            //intent.setAction(Intent.ACTION_VIEW);
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RelatóriosRagonezi/Orcamentos");
            pdfFile = new File(docsFolder.getAbsolutePath(), "vostore/orcamento/ragonezi/app/Orcamento" + "000" + alterarNumeroNota + ".pdf");
            Uri uri = FileProvider.getUriForFile(this, "vostore.apporcamentoragonezi", pdfFile);
//            FileProvider.getUriForFile(this,"your_package.fileprovider",pdfFile);
            intent.setData(uri);
            intent.setAction(android.content.Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setData(uri);
            startActivity(intent);

            startActivity(intent);
            onStop();
            //finish();
        } else {
            Toast.makeText(this, "Você precisa fazer o download de algum leitor de PDF !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("numeroNotta", alterarNumeroNota);
        startActivity(intent);
        finish();
    }

    public void gerar() {


        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        String pathPdf = Environment.getExternalStorageDirectory()
                + "/Teste PDF/Notificações Teste/";

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            File dir = new File(pathPdf);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //File file = new File(dir, "Teste.pdf");

            String file = "arquivo";
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now;
            FileOutputStream fOut = new FileOutputStream(mPath);
            PdfWriter.getInstance(document, fOut);
            document.open();
            Font bold = FontFactory.getFont("Times-Roman, Bold", 12, Font.BOLD);
            Paragraph p00 = new Paragraph("Teste Bold", bold);
            Paragraph p01 = new Paragraph("Teste sem bold");
            p00.setAlignment(Paragraph.ALIGN_LEFT);
            p01.setAlignment(Paragraph.ALIGN_LEFT);

            document.add(p00);
            document.add(Chunk.NEWLINE);
            document.add(p01);
            document.add(Chunk.NEWLINE);
            document.close();
            Log.i("PDF", pathPdf);
        } catch (Exception e) {
            Log.e("Erro PDF", e.toString());
        }
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_ASK_PERMISSIONS);

                    }

                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            kk();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private static void PularLinha(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(""));
        }
    }

    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        return cell;
    }

    public static PdfPCell createTextCell(String text) throws DocumentException, IOException {
        double tamanhoFonte = 9.5;
        Font fonteEndereco = FontFactory.getFont("Times-Roman", (float) tamanhoFonte, Font.NORMAL);
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text, fonteEndereco);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public void linearHidraulica() {
        if (linearHidraulica.getVisibility() == View.VISIBLE)
            linearHidraulica.setVisibility(View.GONE);
        else {
            linearHidraulica.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearART.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
        }
    }

    public void linearRevestimento() {
        if (linearRevestimento.getVisibility() == View.VISIBLE)
            linearRevestimento.setVisibility(View.GONE);
        else {
            linearRevestimento.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearART.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
        }
    }

    public void linearART() {
        if (linearART.getVisibility() == View.VISIBLE)
            linearART.setVisibility(View.GONE);
        else {
            linearART.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);

        }
    }

    public void linearDemolicao() {
        if (linearDemolicao.getVisibility() == View.VISIBLE)
            linearDemolicao.setVisibility(View.GONE);
        else {
            linearDemolicao.setVisibility(View.VISIBLE);
            linearART.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
        }
    }

    public void linearPintura() {
        if (linearPintura.getVisibility() == View.VISIBLE)
            linearPintura.setVisibility(View.GONE);
        else {
            linearPintura.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearART.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPostResume() {
        onRestart();
        super.onPostResume();
    }
    private void signOut() {
        // vostore.orcamento.ragonezi.Permissoes.Firebase sign out

        mAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        mAuth.signOut();
        Intent intent = new Intent(Main2Activity.this, login.class);
        startActivity(intent);

        // Google sign out




    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void valorParcial(){

        //Preparando o local

        //Calculando Valores Cozinha
        DecimalFormat df = new DecimalFormat("###,##0.00");


        varRemoverRevestimentoParede = Double.parseDouble(valorRevestimentoParede1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede1 = Double.parseDouble(valorRevestimentoParede1_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso = Double.parseDouble(valorRemocaoPiso1.getText().toString()) * precoRemoverPiso;
        varRemoverPiso1 = Double.parseDouble(valorRemocaoPiso1_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia = Double.parseDouble(valorRemocaoPia1.getText().toString()) * precoRemoverPia;
        varRemoverPia1 = Double.parseDouble(valorRemocaoPia1_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria = Double.parseDouble(valorRemocacAlvenaria1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria1 = Double.parseDouble(valorRemocacAlvenaria1_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque = Double.parseDouble(valorRemocaoTanque1.getText().toString()) * precoRemoverTanque;
        varRemoverTanque1 = Double.parseDouble(valorRemocaoTanque1_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2 = Double.parseDouble(valorRasgarCaixinha4x2_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_1 = Double.parseDouble(valorRasgarCaixinha4x2_1_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4 = Double.parseDouble(valorRasgarCaixinha4x4_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_1 = Double.parseDouble(valorRasgarCaixinha4x4_1_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica = Double.parseDouble(valorRasgarHidraulica1.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica1 = Double.parseDouble(valorRasgarHidraulica1_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso = Double.parseDouble(valorRemoverGesso1.getText().toString()) * precoRemoverGesso;
        varRemoverGesso1 = Double.parseDouble(valorRemoverGesso1_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario = Double.parseDouble(valorRemoverVaso1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario1 = Double.parseDouble(valorRemoverVaso1_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao = Double.parseDouble(valorRemoverVao1.getText().toString()) * precoRemoverVao;
        varRemoverVao1 = Double.parseDouble(valorRemoverVao1_1.getText().toString()) * precoRemoverVao;

        valorTotalCozinha = varRemoverRevestimentoParede + varRemoverRevestimentoParede1 + varRemoverPiso + varRemoverPiso1 + varRemoverPia + varRemoverPia1 + varRemoverAlvenaria + varRemoverAlvenaria1 + varRemoverTanque + varRemoverTanque1 + varRemoverCaixinha4x2 + varRemoverCaixinha4x2_1 + varRemoverCaixinha4x4 + varRemoverCaixinha4x4_1 + varRemoverHidraulica + varRemoverHidraulica1 + varRemoverGesso + varRemoverGesso1 + varRemoverVasoSanitario + varRemoverVasoSanitario1 + varRemoverVao + varRemoverVao1;

        //Calculando Valores Banheiro 1

        varRemoverRevestimentoParede2 = Double.parseDouble(valorRevestimentoParede2.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede2_1 = Double.parseDouble(valorRevestimentoParede2_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso2 = Double.parseDouble(valorRemocaoPiso2.getText().toString()) * precoRemoverPiso;
        varRemoverPiso2_1 = Double.parseDouble(valorRemocaoPiso2_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia2 = Double.parseDouble(valorRemocaoPia2.getText().toString()) * precoRemoverPia;
        varRemoverPia2_1 = Double.parseDouble(valorRemocaoPia2_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria2 = Double.parseDouble(valorRemocacAlvenaria2.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria2_1 = Double.parseDouble(valorRemocacAlvenaria2_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque2 = Double.parseDouble(valorRemocaoTanque2.getText().toString()) * precoRemoverTanque;
        varRemoverTanque2_1 = Double.parseDouble(valorRemocaoTanque2_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_2 = Double.parseDouble(valorRasgarCaixinha4x2_2.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_2_1 = Double.parseDouble(valorRasgarCaixinha4x2_2_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_2 = Double.parseDouble(valorRasgarCaixinha4x4_2.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_2_1 = Double.parseDouble(valorRasgarCaixinha4x4_2_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica2 = Double.parseDouble(valorRasgarHidraulica2.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica2_1 = Double.parseDouble(valorRasgarHidraulica2_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso2 = Double.parseDouble(valorRemoverGesso2.getText().toString()) * precoRemoverGesso;
        varRemoverGesso2_1 = Double.parseDouble(valorRemoverGesso2_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario2 = Double.parseDouble(valorRemoverVaso2.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario2_1 = Double.parseDouble(valorRemoverVaso2_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao2 = Double.parseDouble(valorRemoverVao2.getText().toString()) * precoRemoverVao;
        varRemoverVao2_1 = Double.parseDouble(valorRemoverVao2_1.getText().toString()) * precoRemoverVao;

        valorTotalBanheiro1 = varRemoverRevestimentoParede2 + varRemoverRevestimentoParede2_1 + varRemoverPiso2 + varRemoverPiso2_1 + varRemoverPia2 + varRemoverPia2_1 + varRemoverAlvenaria2 + varRemoverAlvenaria2_1 + varRemoverTanque2 + varRemoverTanque2 + varRemoverCaixinha4x2_2 + varRemoverCaixinha4x2_2_1 + varRemoverCaixinha4x2_2 + varRemoverCaixinha4x4_2_1 + varRemoverHidraulica2 + varRemoverHidraulica2_1 + varRemoverGesso2 + varRemoverGesso2_1 + varRemoverVasoSanitario2 + varRemoverVasoSanitario2_1 + varRemoverVao2 + varRemoverVao2_1;


        //Calculando Valores Aréa de Serviço

        varRemoverRevestimentoParede3 = Double.parseDouble(valorRevestimentoParede3.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede3_1 = Double.parseDouble(valorRevestimentoParede3_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso3 = Double.parseDouble(valorRemocaoPiso3.getText().toString()) * precoRemoverPiso;
        varRemoverPiso3_1 = Double.parseDouble(valorRemocaoPiso3_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia3 = Double.parseDouble(valorRemocaoPia3.getText().toString()) * precoRemoverPia;
        varRemoverPia3_1 = Double.parseDouble(valorRemocaoPia3_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria3 = Double.parseDouble(valorRemocacAlvenaria3.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria3_1 = Double.parseDouble(valorRemocacAlvenaria3_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque3 = Double.parseDouble(valorRemocaoTanque3.getText().toString()) * precoRemoverTanque;
        varRemoverTanque3_1 = Double.parseDouble(valorRemocaoTanque3_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_3 = Double.parseDouble(valorRasgarCaixinha4x2_3.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_3_1 = Double.parseDouble(valorRasgarCaixinha4x2_3_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_3 = Double.parseDouble(valorRasgarCaixinha4x4_3.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_3_1 = Double.parseDouble(valorRasgarCaixinha4x4_3_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica3 = Double.parseDouble(valorRasgarHidraulica3.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica3_1 = Double.parseDouble(valorRasgarHidraulica3_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso3 = Double.parseDouble(valorRemoverGesso3.getText().toString()) * precoRemoverGesso;
        varRemoverGesso3_1 = Double.parseDouble(valorRemoverGesso3_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario3 = Double.parseDouble(valorRemoverVaso3.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario3_1 = Double.parseDouble(valorRemoverVaso3_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao3 = Double.parseDouble(valorRemoverVao3.getText().toString()) * precoRemoverVao;
        varRemoverVao3_1 = Double.parseDouble(valorRemoverVao3_1.getText().toString()) * precoRemoverVao;


        valorTotalAreaServico = varRemoverRevestimentoParede3 + varRemoverRevestimentoParede3_1 + varRemoverPiso3 + varRemoverPiso3_1 + varRemoverPia3 + varRemoverPia3_1 + varRemoverAlvenaria3 + varRemoverAlvenaria3_1 + varRemoverTanque3 + varRemoverTanque3_1 + varRemoverCaixinha4x2_3 + varRemoverCaixinha4x2_3_1 + varRemoverCaixinha4x4_3 + varRemoverCaixinha4x4_3_1 + varRemoverHidraulica3 + varRemoverHidraulica3_1 + varRemoverGesso3 + varRemoverGesso3_1 + varRemoverVasoSanitario3 + varRemoverVasoSanitario3_1 + varRemoverVao3 + varRemoverVao3_1;


        //Calculando Valores Banheiro 2

        varRemoverRevestimentoParede4 = Double.parseDouble(valorRevestimentoParede4.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede4_1 = Double.parseDouble(valorRevestimentoParede4_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso4 = Double.parseDouble(valorRemocaoPiso4.getText().toString()) * precoRemoverPiso;
        varRemoverPiso4_1 = Double.parseDouble(valorRemocaoPiso4_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia4 = Double.parseDouble(valorRemocaoPia4.getText().toString()) * precoRemoverPia;
        varRemoverPia4_1 = Double.parseDouble(valorRemocaoPia4_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria4 = Double.parseDouble(valorRemocacAlvenaria4.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria4_1 = Double.parseDouble(valorRemocacAlvenaria4_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque4 = Double.parseDouble(valorRemocaoTanque4.getText().toString()) * precoRemoverTanque;
        varRemoverTanque4_1 = Double.parseDouble(valorRemocaoTanque4_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_4 = Double.parseDouble(valorRasgarCaixinha4x2_4.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_4_1 = Double.parseDouble(valorRasgarCaixinha4x2_4_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_4 = Double.parseDouble(valorRasgarCaixinha4x4_4.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_4_1 = Double.parseDouble(valorRasgarCaixinha4x4_4_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica4 = Double.parseDouble(valorRasgarHidraulica4.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica4_1 = Double.parseDouble(valorRasgarHidraulica4_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso4 = Double.parseDouble(valorRemoverGesso4.getText().toString()) * precoRemoverGesso;
        varRemoverGesso4_1 = Double.parseDouble(valorRemoverGesso4_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario4 = Double.parseDouble(valorRemoverVaso4.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario4_1 = Double.parseDouble(valorRemoverVaso4_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao4 = Double.parseDouble(valorRemoverVao4.getText().toString()) * precoRemoverVao;
        varRemoverVao4_1 = Double.parseDouble(valorRemoverVao4_1.getText().toString()) * precoRemoverVao;


        valorTotalBanheiro2 = varRemoverRevestimentoParede4 + varRemoverRevestimentoParede4_1 + varRemoverPiso4 + varRemoverPiso4_1 + varRemoverPia4 + varRemoverPia4_1 + varRemoverAlvenaria4 + varRemoverAlvenaria4_1 + varRemoverTanque4 + varRemoverTanque4_1 + varRemoverCaixinha4x2_4 + varRemoverCaixinha4x2_4_1 + varRemoverCaixinha4x4_4 + varRemoverCaixinha4x4_4_1 + varRemoverHidraulica4 + varRemoverHidraulica4_1 + varRemoverGesso4 + varRemoverGesso4_1 + varRemoverVasoSanitario4 + varRemoverVasoSanitario4_1 + varRemoverVao4 + varRemoverVao4_1;


        //Calculando Valores Lavabo

        varRemoverRevestimentoParede5 = Double.parseDouble(valorRevestimentoParede5.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede5_1 = Double.parseDouble(valorRevestimentoParede5_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso5 = Double.parseDouble(valorRemocaoPiso5.getText().toString()) * precoRemoverPiso;
        varRemoverPiso5_1 = Double.parseDouble(valorRemocaoPiso5_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia5 = Double.parseDouble(valorRemocaoPia5.getText().toString()) * precoRemoverPia;
        varRemoverPia5_1 = Double.parseDouble(valorRemocaoPia5_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria5 = Double.parseDouble(valorRemocacAlvenaria5.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria5_1 = Double.parseDouble(valorRemocacAlvenaria5_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque5 = Double.parseDouble(valorRemocaoTanque5.getText().toString()) * precoRemoverTanque;
        varRemoverTanque5_1 = Double.parseDouble(valorRemocaoTanque5_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_5 = Double.parseDouble(valorRasgarCaixinha4x2_5.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_5_1 = Double.parseDouble(valorRasgarCaixinha4x2_5_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_5 = Double.parseDouble(valorRasgarCaixinha4x4_5.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_5_1 = Double.parseDouble(valorRasgarCaixinha4x4_5_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica5 = Double.parseDouble(valorRasgarHidraulica5.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica5_1 = Double.parseDouble(valorRasgarHidraulica5_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso5 = Double.parseDouble(valorRemoverGesso5.getText().toString()) * precoRemoverGesso;
        varRemoverGesso5_1 = Double.parseDouble(valorRemoverGesso5_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario5 = Double.parseDouble(valorRemoverVaso5.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario5_1 = Double.parseDouble(valorRemoverVaso5_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao5 = Double.parseDouble(valorRemoverVao5.getText().toString()) * precoRemoverVao;
        varRemoverVao5_1 = Double.parseDouble(valorRemoverVao5_1.getText().toString()) * precoRemoverVao;


        valorTotalLavabo = varRemoverRevestimentoParede5 + varRemoverRevestimentoParede5_1 + varRemoverPiso5 + varRemoverPiso5_1 + varRemoverPia5 + varRemoverPia5_1 + varRemoverAlvenaria5 + varRemoverAlvenaria5_1 + varRemoverTanque5 + varRemoverTanque5_1 + varRemoverCaixinha4x2_5 + varRemoverCaixinha4x2_5_1 + varRemoverCaixinha4x4_5 + varRemoverCaixinha4x4_5_1 + varRemoverHidraulica5 + varRemoverHidraulica5_1 + varRemoverGesso5 + varRemoverGesso5_1 + varRemoverVasoSanitario5 + varRemoverVasoSanitario5_1 + varRemoverVao5 + varRemoverVao5_1;

        //Calculando Valores Sacada Varanda

        varRemoverRevestimentoParede6 = Double.parseDouble(valorRevestimentoParede6.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede6_1 = Double.parseDouble(valorRevestimentoParede6_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso6 = Double.parseDouble(valorRemocaoPiso6.getText().toString()) * precoRemoverPiso;
        varRemoverPiso6_1 = Double.parseDouble(valorRemocaoPiso6_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia6 = Double.parseDouble(valorRemocaoPia6.getText().toString()) * precoRemoverPia;
        varRemoverPia6_1 = Double.parseDouble(valorRemocaoPia6_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria6 = Double.parseDouble(valorRemocacAlvenaria6.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria6_1 = Double.parseDouble(valorRemocacAlvenaria6_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque6 = Double.parseDouble(valorRemocaoTanque6.getText().toString()) * precoRemoverTanque;
        varRemoverTanque6_1 = Double.parseDouble(valorRemocaoTanque6_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_6 = Double.parseDouble(valorRasgarCaixinha4x2_6.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_6_1 = Double.parseDouble(valorRasgarCaixinha4x2_6_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_6 = Double.parseDouble(valorRasgarCaixinha4x4_6.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_6_1 = Double.parseDouble(valorRasgarCaixinha4x4_6_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica6 = Double.parseDouble(valorRasgarHidraulica6.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica6_1 = Double.parseDouble(valorRasgarHidraulica6_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso6 = Double.parseDouble(valorRemoverGesso6.getText().toString()) * precoRemoverGesso;
        varRemoverGesso6_1 = Double.parseDouble(valorRemoverGesso6_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario6 = Double.parseDouble(valorRemoverVaso6.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario6_1 = Double.parseDouble(valorRemoverVaso6_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao6 = Double.parseDouble(valorRemoverVao6.getText().toString()) * precoRemoverVao;
        varRemoverVao6_1 = Double.parseDouble(valorRemoverVao6_1.getText().toString()) * precoRemoverVao;


        valorTotalSacadaVaranda = varRemoverRevestimentoParede6 + varRemoverRevestimentoParede6_1 + varRemoverPiso6 + varRemoverPiso6_1 + varRemoverPia6 + varRemoverPia6_1 + varRemoverAlvenaria6 + varRemoverAlvenaria6_1 + varRemoverTanque6 + varRemoverTanque6_1 + varRemoverCaixinha4x2_6 + varRemoverCaixinha4x2_6_1 + varRemoverCaixinha4x4_6 + varRemoverCaixinha4x4_6_1 + varRemoverHidraulica6 + varRemoverHidraulica6_1 + varRemoverGesso6 + varRemoverGesso6_1 + varRemoverVasoSanitario6 + varRemoverVasoSanitario6_1 + varRemoverVao6 + varRemoverVao6_1;

        //Calculando Valores Sala de Jantar

        varRemoverRevestimentoParede7 = Double.parseDouble(valorRevestimentoParede7.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede7_1 = Double.parseDouble(valorRevestimentoParede7_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso7 = Double.parseDouble(valorRemocaoPiso7.getText().toString()) * precoRemoverPiso;
        varRemoverPiso7_1 = Double.parseDouble(valorRemocaoPiso7_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia7 = Double.parseDouble(valorRemocaoPia7.getText().toString()) * precoRemoverPia;
        varRemoverPia7_1 = Double.parseDouble(valorRemocaoPia7_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria7 = Double.parseDouble(valorRemocacAlvenaria7.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria7_1 = Double.parseDouble(valorRemocacAlvenaria7_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque7 = Double.parseDouble(valorRemocaoTanque7.getText().toString()) * precoRemoverTanque;
        varRemoverTanque7_1 = Double.parseDouble(valorRemocaoTanque7_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_7 = Double.parseDouble(valorRasgarCaixinha4x2_7.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_7_1 = Double.parseDouble(valorRasgarCaixinha4x2_7_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_7 = Double.parseDouble(valorRasgarCaixinha4x4_7.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_7_1 = Double.parseDouble(valorRasgarCaixinha4x4_7_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica7 = Double.parseDouble(valorRasgarHidraulica7.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica7_1 = Double.parseDouble(valorRasgarHidraulica7_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso7 = Double.parseDouble(valorRemoverGesso7.getText().toString()) * precoRemoverGesso;
        varRemoverGesso7_1 = Double.parseDouble(valorRemoverGesso7_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario7 = Double.parseDouble(valorRemoverVaso7.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario7_1 = Double.parseDouble(valorRemoverVaso7_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao7 = Double.parseDouble(valorRemoverVao7.getText().toString()) * precoRemoverVao;
        varRemoverVao7_1 = Double.parseDouble(valorRemoverVao7_1.getText().toString()) * precoRemoverVao;


        valorTotalSalaJantar = varRemoverRevestimentoParede7 + varRemoverRevestimentoParede7_1 + varRemoverPiso7 + varRemoverPiso7_1 + varRemoverPia7 + varRemoverPia7_1 + varRemoverAlvenaria7 + varRemoverAlvenaria7_1 + varRemoverTanque7 + varRemoverTanque7_1 + varRemoverCaixinha4x2_7 + varRemoverCaixinha4x2_7_1 + varRemoverCaixinha4x4_7 + varRemoverCaixinha4x4_7_1 + varRemoverHidraulica7 + varRemoverHidraulica7_1 + varRemoverGesso7 + varRemoverGesso7_1 + varRemoverVasoSanitario7 + varRemoverVasoSanitario7_1 + varRemoverVao7 + varRemoverVao7_1;

        //Calculando Valores Sala de Estar

        varRemoverRevestimentoParede8 = Double.parseDouble(valorRevestimentoParede8.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede8_1 = Double.parseDouble(valorRevestimentoParede8_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso8 = Double.parseDouble(valorRemocaoPiso8.getText().toString()) * precoRemoverPiso;
        varRemoverPiso8_1 = Double.parseDouble(valorRemocaoPiso8_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia8 = Double.parseDouble(valorRemocaoPia8.getText().toString()) * precoRemoverPia;
        varRemoverPia8_1 = Double.parseDouble(valorRemocaoPia8_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria8 = Double.parseDouble(valorRemocacAlvenaria8.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria8_1 = Double.parseDouble(valorRemocacAlvenaria8_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque8 = Double.parseDouble(valorRemocaoTanque8.getText().toString()) * precoRemoverTanque;
        varRemoverTanque8_1 = Double.parseDouble(valorRemocaoTanque8_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_8 = Double.parseDouble(valorRasgarCaixinha4x2_8.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_8_1 = Double.parseDouble(valorRasgarCaixinha4x2_8_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_8 = Double.parseDouble(valorRasgarCaixinha4x4_8.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_8_1 = Double.parseDouble(valorRasgarCaixinha4x4_8_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica8 = Double.parseDouble(valorRasgarHidraulica8.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica8_1 = Double.parseDouble(valorRasgarHidraulica8_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso8 = Double.parseDouble(valorRemoverGesso8.getText().toString()) * precoRemoverGesso;
        varRemoverGesso8_1 = Double.parseDouble(valorRemoverGesso8_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario8 = Double.parseDouble(valorRemoverVaso8.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario8_1 = Double.parseDouble(valorRemoverVaso8_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao8 = Double.parseDouble(valorRemoverVao8.getText().toString()) * precoRemoverVao;
        varRemoverVao8_1 = Double.parseDouble(valorRemoverVao8_1.getText().toString()) * precoRemoverVao;


        valorTotalSalaEstar = varRemoverRevestimentoParede8 + varRemoverRevestimentoParede8_1 + varRemoverPiso8 + varRemoverPiso8_1 + varRemoverPia8 + varRemoverPia8_1 + varRemoverAlvenaria8 + varRemoverAlvenaria8_1 + varRemoverTanque8 + varRemoverTanque8_1 + varRemoverCaixinha4x2_8 + varRemoverCaixinha4x2_8_1 + varRemoverCaixinha4x4_8 + varRemoverCaixinha4x4_8_1 + varRemoverHidraulica8 + varRemoverHidraulica8_1 + varRemoverGesso8 + varRemoverGesso8_1 + varRemoverVasoSanitario8 + varRemoverVasoSanitario8_1 + varRemoverVao8 + varRemoverVao8_1;

        //Calculando Valores Quarto1

        varRemoverRevestimentoParede9 = Double.parseDouble(valorRevestimentoParede9.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede9_1 = Double.parseDouble(valorRevestimentoParede9_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso9 = Double.parseDouble(valorRemocaoPiso9.getText().toString()) * precoRemoverPiso;
        varRemoverPiso9_1 = Double.parseDouble(valorRemocaoPiso9_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia9 = Double.parseDouble(valorRemocaoPia9.getText().toString()) * precoRemoverPia;
        varRemoverPia9_1 = Double.parseDouble(valorRemocaoPia9_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria9 = Double.parseDouble(valorRemocacAlvenaria9.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria9_1 = Double.parseDouble(valorRemocacAlvenaria9_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque9 = Double.parseDouble(valorRemocaoTanque9.getText().toString()) * precoRemoverTanque;
        varRemoverTanque9_1 = Double.parseDouble(valorRemocaoTanque9_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_9 = Double.parseDouble(valorRasgarCaixinha4x2_9.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_9_1 = Double.parseDouble(valorRasgarCaixinha4x2_9_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_9 = Double.parseDouble(valorRasgarCaixinha4x4_9.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_9_1 = Double.parseDouble(valorRasgarCaixinha4x4_9_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica9 = Double.parseDouble(valorRasgarHidraulica9.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica9_1 = Double.parseDouble(valorRasgarHidraulica9_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso9 = Double.parseDouble(valorRemoverGesso9.getText().toString()) * precoRemoverGesso;
        varRemoverGesso9_1 = Double.parseDouble(valorRemoverGesso9_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario9 = Double.parseDouble(valorRemoverVaso9.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario9_1 = Double.parseDouble(valorRemoverVaso9_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao9 = Double.parseDouble(valorRemoverVao9.getText().toString()) * precoRemoverVao;
        varRemoverVao9_1 = Double.parseDouble(valorRemoverVao9_1.getText().toString()) * precoRemoverVao;


        valorTotalQuarto1 = varRemoverRevestimentoParede9 + varRemoverRevestimentoParede9_1 + varRemoverPiso9 + varRemoverPiso9_1 + varRemoverPia9 + varRemoverPia9_1 + varRemoverAlvenaria9 + varRemoverAlvenaria9_1 + varRemoverTanque9 + varRemoverTanque9_1 + varRemoverCaixinha4x2_9 + varRemoverCaixinha4x2_9_1 + varRemoverCaixinha4x4_9 + varRemoverCaixinha4x4_9_1 + varRemoverHidraulica9 + varRemoverHidraulica9_1 + varRemoverGesso9 + varRemoverGesso9_1 + varRemoverVasoSanitario9 + varRemoverVasoSanitario9_1 + varRemoverVao9 + varRemoverVao9_1;

        //Calculando Valores Quarto2

        varRemoverRevestimentoParede10 = Double.parseDouble(valorRevestimentoParede10.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede10_1 = Double.parseDouble(valorRevestimentoParede10_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso10 = Double.parseDouble(valorRemocaoPiso10.getText().toString()) * precoRemoverPiso;
        varRemoverPiso10_1 = Double.parseDouble(valorRemocaoPiso10_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia10 = Double.parseDouble(valorRemocaoPia10.getText().toString()) * precoRemoverPia;
        varRemoverPia10_1 = Double.parseDouble(valorRemocaoPia10_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria10 = Double.parseDouble(valorRemocacAlvenaria10.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria10_1 = Double.parseDouble(valorRemocacAlvenaria10_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque10 = Double.parseDouble(valorRemocaoTanque10.getText().toString()) * precoRemoverTanque;
        varRemoverTanque10_1 = Double.parseDouble(valorRemocaoTanque10_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_10 = Double.parseDouble(valorRasgarCaixinha4x2_10.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_10_1 = Double.parseDouble(valorRasgarCaixinha4x2_10_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_10 = Double.parseDouble(valorRasgarCaixinha4x4_10.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_10_1 = Double.parseDouble(valorRasgarCaixinha4x4_10_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica10 = Double.parseDouble(valorRasgarHidraulica10.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica10_1 = Double.parseDouble(valorRasgarHidraulica10_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso10 = Double.parseDouble(valorRemoverGesso10.getText().toString()) * precoRemoverGesso;
        varRemoverGesso10_1 = Double.parseDouble(valorRemoverGesso10_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario10 = Double.parseDouble(valorRemoverVaso10.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario10_1 = Double.parseDouble(valorRemoverVaso10_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao10 = Double.parseDouble(valorRemoverVao10.getText().toString()) * precoRemoverVao;
        varRemoverVao10_1 = Double.parseDouble(valorRemoverVao10_1.getText().toString()) * precoRemoverVao;


        valorTotalQuarto2 = varRemoverRevestimentoParede10 + varRemoverRevestimentoParede10_1 + varRemoverPiso10 + varRemoverPiso10_1 + varRemoverPia10 + varRemoverPia10_1 + varRemoverAlvenaria10 + varRemoverAlvenaria10_1 + varRemoverTanque10 + varRemoverTanque10_1 + varRemoverCaixinha4x2_10 + varRemoverCaixinha4x2_10_1 + varRemoverCaixinha4x4_10 + varRemoverCaixinha4x4_10_1 + varRemoverHidraulica10 + varRemoverHidraulica10_1 + varRemoverGesso10 + varRemoverGesso10_1 + varRemoverVasoSanitario10 + varRemoverVasoSanitario10_1 + varRemoverVao10 + varRemoverVao10_1;

        //Calculando Valores Quarto3

        varRemoverRevestimentoParede11 = Double.parseDouble(valorRevestimentoParede11.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverRevestimentoParede11_1 = Double.parseDouble(valorRevestimentoParede11_1.getText().toString()) * precoRemoverRevestimentoParede;
        varRemoverPiso11 = Double.parseDouble(valorRemocaoPiso11.getText().toString()) * precoRemoverPiso;
        varRemoverPiso11_1 = Double.parseDouble(valorRemocaoPiso11_1.getText().toString()) * precoRemoverPiso;
        varRemoverPia11 = Double.parseDouble(valorRemocaoPia11.getText().toString()) * precoRemoverPia;
        varRemoverPia11_1 = Double.parseDouble(valorRemocaoPia11_1.getText().toString()) * precoRemoverPia;
        varRemoverAlvenaria11 = Double.parseDouble(valorRemocacAlvenaria11.getText().toString()) * precoRemoverAlvenaria;
        varRemoverAlvenaria11_1 = Double.parseDouble(valorRemocacAlvenaria11_1.getText().toString()) * precoRemoverAlvenaria;
        varRemoverTanque11 = Double.parseDouble(valorRemocaoTanque11.getText().toString()) * precoRemoverTanque;
        varRemoverTanque11_1 = Double.parseDouble(valorRemocaoTanque11_1.getText().toString()) * precoRemoverTanque;
        varRemoverCaixinha4x2_11 = Double.parseDouble(valorRasgarCaixinha4x2_11.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x2_11_1 = Double.parseDouble(valorRasgarCaixinha4x2_11_1.getText().toString()) * precoRasgarCaixinha4x2;
        varRemoverCaixinha4x4_11 = Double.parseDouble(valorRasgarCaixinha4x4_11.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverCaixinha4x4_11_1 = Double.parseDouble(valorRasgarCaixinha4x4_11_1.getText().toString()) * precoRasgarCaixinha4x4;
        varRemoverHidraulica11 = Double.parseDouble(valorRasgarHidraulica11.getText().toString()) * precoRemoverHidraulica;
        varRemoverHidraulica11_1 = Double.parseDouble(valorRasgarHidraulica11_1.getText().toString()) * precoRemoverHidraulica;
        varRemoverGesso11 = Double.parseDouble(valorRemoverGesso11.getText().toString()) * precoRemoverGesso;
        varRemoverGesso11_1 = Double.parseDouble(valorRemoverGesso11_1.getText().toString()) * precoRemoverGesso;
        varRemoverVasoSanitario11 = Double.parseDouble(valorRemoverVaso11.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVasoSanitario11_1 = Double.parseDouble(valorRemoverVaso11_1.getText().toString()) * precoRemoverVasoSanitario;
        varRemoverVao11 = Double.parseDouble(valorRemoverVao11.getText().toString()) * precoRemoverVao;
        varRemoverVao11_1 = Double.parseDouble(valorRemoverVao11_1.getText().toString()) * precoRemoverVao;

        valorTotalQuarto3 = varRemoverRevestimentoParede11 + varRemoverRevestimentoParede11_1 + varRemoverPiso11 + varRemoverPiso11_1 + varRemoverPia11 + varRemoverPia11_1 + varRemoverAlvenaria11 + varRemoverAlvenaria11_1 + varRemoverTanque11 + varRemoverTanque11_1 + varRemoverCaixinha4x2_11 + varRemoverCaixinha4x2_11_1 + varRemoverCaixinha4x4_11 + varRemoverCaixinha4x4_11_1 + varRemoverHidraulica11 + varRemoverHidraulica11_1 + varRemoverGesso11 + varRemoverGesso11_1 + varRemoverVasoSanitario11 + varRemoverVasoSanitario11_1 + varRemoverVao11 + varRemoverVao11_1;


        totalDemolicao = valorTotalCozinha + valorTotalBanheiro1 + valorTotalBanheiro2 + valorTotalBanheiro2 + valorTotalLavabo + valorTotalSacadaVaranda + valorTotalSalaEstar + valorTotalSalaJantar + valorTotalQuarto1 + valorTotalQuarto2 + valorTotalQuarto3;

        //Valores Revestimento Cozinha


        varAdicionarAlvenaria = Double.parseDouble(valorRevestimentoAlvenariaBase1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria1 = Double.parseDouble(valorRevestimentoAlvenariaBase1_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso = Double.parseDouble(valorRevestimentoContraPiso1.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso1 = Double.parseDouble(valorRevestimentoContraPiso1_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante = Double.parseDouble(valorRevestimentoImpermeabilidade1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante1 = Double.parseDouble(valorRevestimentoImpermeabilidade1_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior = Double.parseDouble(valorRevestimentoPorcelanatoAcima1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima1_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor = Double.parseDouble(valorRevestimentoPorcelanatoMenor1.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor1_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro = Double.parseDouble(valorRevestimentoPastilhaVidro1.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_1 = Double.parseDouble(valorRevestimentoPastilhaVidro1_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D = Double.parseDouble(valorRevestimento3D1.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_1 = Double.parseDouble(valorRevestimento3D1_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoCozinha = varAdicionarAlvenaria + varAdicionarAlvenaria1 + varAdicionarContraPiso + varAdicionarContraPiso1 + varAplicacaoImpermeabilizante + varAplicacaoImpermeabilizante1 + varAplicarPorcelanatoMaior + varAplicarPorcelanatoMaior1 + varAplicarPorcelanatoMenor + varAplicarPorcelanatoMenor1 + varPastilhaVidro + varPastilhaVidro_1 + varRevestimento3D + varRevestimento3D_1;

        //Valores Revestimento Banheiro Social
        varAdicionarAlvenaria2 = Double.parseDouble(valorRevestimentoAlvenariaBase2.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria2_1 = Double.parseDouble(valorRevestimentoAlvenariaBase2_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso2 = Double.parseDouble(valorRevestimentoContraPiso2.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso2_1 = Double.parseDouble(valorRevestimentoContraPiso2_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante2 = Double.parseDouble(valorRevestimentoImpermeabilidade2.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante2_1 = Double.parseDouble(valorRevestimentoImpermeabilidade2_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior2 = Double.parseDouble(valorRevestimentoPorcelanatoAcima2.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior2_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima2_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor2 = Double.parseDouble(valorRevestimentoPorcelanatoMenor2.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor2_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor2_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_2 = Double.parseDouble(valorRevestimentoPastilhaVidro2.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_2_1 = Double.parseDouble(valorRevestimentoPastilhaVidro2_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_2 = Double.parseDouble(valorRevestimento3D2.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_2_1 = Double.parseDouble(valorRevestimento3D2_1.getText().toString()) * precoRevestimento3D;

        valorTotalRevestimentoBanheiroSocial = varAdicionarAlvenaria2 + varAdicionarAlvenaria2_1 + varAdicionarContraPiso2 + varAdicionarContraPiso2_1 + varAplicacaoImpermeabilizante2 + varAplicacaoImpermeabilizante2_1 + varAplicarPorcelanatoMaior2 + varAplicarPorcelanatoMaior2_1 + varAplicarPorcelanatoMenor2 + varAplicarPorcelanatoMenor2_1 + varPastilhaVidro_2 + varPastilhaVidro_2_1 + varRevestimento3D_2 + varRevestimento3D_2_1;


        // Valores Revestimento Area de Servico
        varAdicionarAlvenaria3 = Double.parseDouble(valorRevestimentoAlvenariaBase3.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria3_1 = Double.parseDouble(valorRevestimentoAlvenariaBase3_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso3 = Double.parseDouble(valorRevestimentoContraPiso3.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso3_1 = Double.parseDouble(valorRevestimentoContraPiso3_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante3 = Double.parseDouble(valorRevestimentoImpermeabilidade3.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante3_1 = Double.parseDouble(valorRevestimentoImpermeabilidade3_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior3 = Double.parseDouble(valorRevestimentoPorcelanatoAcima3.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior3_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima3_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor3 = Double.parseDouble(valorRevestimentoPorcelanatoMenor3.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor3_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor3_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_3 = Double.parseDouble(valorRevestimentoPastilhaVidro3.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_3_1 = Double.parseDouble(valorRevestimentoPastilhaVidro3_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_3 = Double.parseDouble(valorRevestimento3D3.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_3_1 = Double.parseDouble(valorRevestimento3D3_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoAreaServico = varAdicionarAlvenaria3 + varAdicionarAlvenaria3_1 + varAdicionarContraPiso3 + varAdicionarContraPiso3_1 + varAplicacaoImpermeabilizante3 + varAplicacaoImpermeabilizante3_1 + varAplicarPorcelanatoMaior3 + varAplicarPorcelanatoMaior3_1 + varAplicarPorcelanatoMenor3 + varAplicarPorcelanatoMenor3_1 + varPastilhaVidro_3 + varPastilhaVidro_3_1 + varRevestimento3D_3 + varRevestimento3D_3_1;


        //Valores Revestimento Banheiro Suite
        varAdicionarAlvenaria4 = Double.parseDouble(valorRevestimentoAlvenariaBase4.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria4_1 = Double.parseDouble(valorRevestimentoAlvenariaBase4_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso4 = Double.parseDouble(valorRevestimentoContraPiso4.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso4_1 = Double.parseDouble(valorRevestimentoContraPiso4_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante4 = Double.parseDouble(valorRevestimentoImpermeabilidade4.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante4_1 = Double.parseDouble(valorRevestimentoImpermeabilidade4_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior4 = Double.parseDouble(valorRevestimentoPorcelanatoAcima4.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior4_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima4_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor4 = Double.parseDouble(valorRevestimentoPorcelanatoMenor4.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor4_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor4_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_4 = Double.parseDouble(valorRevestimentoPastilhaVidro4.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_4_1 = Double.parseDouble(valorRevestimentoPastilhaVidro4_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_4 = Double.parseDouble(valorRevestimento3D4.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_4_1 = Double.parseDouble(valorRevestimento3D4_1.getText().toString()) * precoRevestimento3D;

        valorTotalRevestimentoBanheiroSuite = varRevestimento3D_4_1 + varRevestimento3D_4 + varPastilhaVidro_4_1 + varPastilhaVidro_4 + varAplicarPorcelanatoMenor4 + varAplicarPorcelanatoMaior4_1 + varAplicarPorcelanatoMaior4 + varAdicionarAlvenaria4 + varAdicionarAlvenaria4_1 + varAdicionarContraPiso4 + varAdicionarContraPiso4_1 + varAplicacaoImpermeabilizante4 + varAplicacaoImpermeabilizante4_1;


        //Valores Revestimento Lavabo
        varAdicionarAlvenaria5 = Double.parseDouble(valorRevestimentoAlvenariaBase5.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria5_1 = Double.parseDouble(valorRevestimentoAlvenariaBase5_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso5 = Double.parseDouble(valorRevestimentoContraPiso5.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso5_1 = Double.parseDouble(valorRevestimentoContraPiso5_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante5 = Double.parseDouble(valorRevestimentoImpermeabilidade5.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante5_1 = Double.parseDouble(valorRevestimentoImpermeabilidade5_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior5 = Double.parseDouble(valorRevestimentoPorcelanatoAcima5.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior5_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima5_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor5 = Double.parseDouble(valorRevestimentoPorcelanatoMenor5.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor5_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor5_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_5 = Double.parseDouble(valorRevestimentoPastilhaVidro5.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_5_1 = Double.parseDouble(valorRevestimentoPastilhaVidro5_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_5 = Double.parseDouble(valorRevestimento3D5.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_5_1 = Double.parseDouble(valorRevestimento3D5_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoLavabo = varAdicionarAlvenaria5 + varAdicionarAlvenaria5_1 + varAdicionarContraPiso5 + varAdicionarContraPiso5_1 + varAplicacaoImpermeabilizante5 + varAplicacaoImpermeabilizante5_1 + varAplicarPorcelanatoMaior5 + varAplicarPorcelanatoMaior5_1 + varAplicarPorcelanatoMenor5 + varAplicarPorcelanatoMenor5_1 + varPastilhaVidro_5 + varPastilhaVidro_5_1 + varRevestimento3D_5 + varRevestimento3D_5_1;

        //Valores Revestimento Sacada

        varAdicionarAlvenaria6 = Double.parseDouble(valorRevestimentoAlvenariaBase6.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria6_1 = Double.parseDouble(valorRevestimentoAlvenariaBase6_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso6 = Double.parseDouble(valorRevestimentoContraPiso6.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso6_1 = Double.parseDouble(valorRevestimentoContraPiso6_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante6 = Double.parseDouble(valorRevestimentoImpermeabilidade6.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante6_1 = Double.parseDouble(valorRevestimentoImpermeabilidade6_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior6 = Double.parseDouble(valorRevestimentoPorcelanatoAcima6.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior6_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima6_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor6 = Double.parseDouble(valorRevestimentoPorcelanatoMenor6.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor6_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor6_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_6 = Double.parseDouble(valorRevestimentoPastilhaVidro6.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_6_1 = Double.parseDouble(valorRevestimentoPastilhaVidro6_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_6 = Double.parseDouble(valorRevestimento3D6.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_6_1 = Double.parseDouble(valorRevestimento3D6_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoSacada = varAdicionarAlvenaria6 + varAdicionarAlvenaria6_1 + varAdicionarContraPiso6 + varAdicionarContraPiso6_1 + varAplicacaoImpermeabilizante6 + varAplicacaoImpermeabilizante6_1 + varAplicarPorcelanatoMaior6 + varAplicarPorcelanatoMaior6_1 + varAplicarPorcelanatoMenor6 + varAplicarPorcelanatoMenor6_1 + varPastilhaVidro_6 + varPastilhaVidro_6_1 + varRevestimento3D_6 + varRevestimento3D_6_1;

        //Revestimento de Sala de Jantar
        varAdicionarAlvenaria7 = Double.parseDouble(valorRevestimentoAlvenariaBase7.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria7_1 = Double.parseDouble(valorRevestimentoAlvenariaBase7_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso7 = Double.parseDouble(valorRevestimentoContraPiso7.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso7_1 = Double.parseDouble(valorRevestimentoContraPiso7_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante7 = Double.parseDouble(valorRevestimentoImpermeabilidade7.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante7_1 = Double.parseDouble(valorRevestimentoImpermeabilidade7_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior7 = Double.parseDouble(valorRevestimentoPorcelanatoAcima7.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior7_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima7_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor7 = Double.parseDouble(valorRevestimentoPorcelanatoMenor7.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor7_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor7_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_7 = Double.parseDouble(valorRevestimentoPastilhaVidro7.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_7_1 = Double.parseDouble(valorRevestimentoPastilhaVidro7_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_7 = Double.parseDouble(valorRevestimento3D7.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_7_1 = Double.parseDouble(valorRevestimento3D7_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoSalaJantar = varAdicionarAlvenaria7 + varAdicionarAlvenaria7_1 + varAdicionarContraPiso7 + varAdicionarContraPiso7_1 + varAplicacaoImpermeabilizante7 + varAplicacaoImpermeabilizante7_1 + varAplicarPorcelanatoMaior7 + varAplicarPorcelanatoMaior7_1 + varAplicarPorcelanatoMenor7 + varAplicarPorcelanatoMenor7_1 + varPastilhaVidro_7 + varPastilhaVidro_7_1 + varRevestimento3D_7 + varRevestimento3D_7_1;


        //Valores Revestimento Sala Estar
        varAdicionarAlvenaria8 = Double.parseDouble(valorRevestimentoAlvenariaBase8.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria8_1 = Double.parseDouble(valorRevestimentoAlvenariaBase8_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso8 = Double.parseDouble(valorRevestimentoContraPiso8.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso8_1 = Double.parseDouble(valorRevestimentoContraPiso8_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante8 = Double.parseDouble(valorRevestimentoImpermeabilidade8.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante8_1 = Double.parseDouble(valorRevestimentoImpermeabilidade8_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior8 = Double.parseDouble(valorRevestimentoPorcelanatoAcima8.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior8_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima8_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor8 = Double.parseDouble(valorRevestimentoPorcelanatoMenor8.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor8_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor8_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_8 = Double.parseDouble(valorRevestimentoPastilhaVidro8.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_8_1 = Double.parseDouble(valorRevestimentoPastilhaVidro8_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_8 = Double.parseDouble(valorRevestimento3D8.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_8_1 = Double.parseDouble(valorRevestimento3D8_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoSalaEstar = varAdicionarAlvenaria8 + varAdicionarAlvenaria8_1 + varAdicionarContraPiso8 + varAdicionarContraPiso8_1 + varAplicacaoImpermeabilizante8 + varAplicacaoImpermeabilizante8_1 + varAplicarPorcelanatoMaior8 + varAplicarPorcelanatoMaior8_1 + varAplicarPorcelanatoMenor8 + varAplicarPorcelanatoMenor8_1 + varPastilhaVidro_8 + varPastilhaVidro_8_1 + varRevestimento3D_8 + varRevestimento3D_8_1;


        //Valores Revestimento Quarto Suite
        varAdicionarAlvenaria9 = Double.parseDouble(valorRevestimentoAlvenariaBase9.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria9_1 = Double.parseDouble(valorRevestimentoAlvenariaBase9_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso9 = Double.parseDouble(valorRevestimentoContraPiso9.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso9_1 = Double.parseDouble(valorRevestimentoContraPiso9_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante9 = Double.parseDouble(valorRevestimentoImpermeabilidade9.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante9_1 = Double.parseDouble(valorRevestimentoImpermeabilidade9_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior9 = Double.parseDouble(valorRevestimentoPorcelanatoAcima9.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior9_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima9_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor9 = Double.parseDouble(valorRevestimentoPorcelanatoMenor9.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor9_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor9_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_9 = Double.parseDouble(valorRevestimentoPastilhaVidro9.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_9_1 = Double.parseDouble(valorRevestimentoPastilhaVidro9_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_9 = Double.parseDouble(valorRevestimento3D9.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_9_1 = Double.parseDouble(valorRevestimento3D9_1.getText().toString()) * precoRevestimento3D;

        valorTotalRevestimentoQuartoSuite = varAdicionarAlvenaria9 + varAdicionarAlvenaria9_1 + varAdicionarContraPiso9 + varAdicionarContraPiso9_1 + varAplicacaoImpermeabilizante9 + varAplicacaoImpermeabilizante9_1 + varAplicarPorcelanatoMaior9 + varAplicarPorcelanatoMaior9_1 + varAplicarPorcelanatoMenor9 + varAplicarPorcelanatoMenor9_1 + varPastilhaVidro_9 + varPastilhaVidro_9_1 + varRevestimento3D_9 + varRevestimento3D_9_1;

        //Valores Revstimento Quarto 1
        varAdicionarAlvenaria10 = Double.parseDouble(valorRevestimentoAlvenariaBase10.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria10_1 = Double.parseDouble(valorRevestimentoAlvenariaBase10_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso10 = Double.parseDouble(valorRevestimentoContraPiso10.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso10_1 = Double.parseDouble(valorRevestimentoContraPiso10_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante10 = Double.parseDouble(valorRevestimentoImpermeabilidade10.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante10_1 = Double.parseDouble(valorRevestimentoImpermeabilidade10_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior10 = Double.parseDouble(valorRevestimentoPorcelanatoAcima10.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior10_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima10_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor10 = Double.parseDouble(valorRevestimentoPorcelanatoMenor10.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor10_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor10_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_10 = Double.parseDouble(valorRevestimentoPastilhaVidro10.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_10_1 = Double.parseDouble(valorRevestimentoPastilhaVidro10_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_10 = Double.parseDouble(valorRevestimento3D10.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_10_1 = Double.parseDouble(valorRevestimento3D10_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoQuarto1 = varAdicionarAlvenaria10 + varAdicionarAlvenaria10_1 + varAdicionarContraPiso10 + varAdicionarContraPiso10_1 + varAplicacaoImpermeabilizante10 + varAplicacaoImpermeabilizante10_1 + varAplicarPorcelanatoMaior10 + varAplicarPorcelanatoMaior10_1 + varAplicarPorcelanatoMenor10 + varAplicarPorcelanatoMenor10_1 + varPastilhaVidro_10 + varPastilhaVidro_10_1 + varRevestimento3D_10 + varRevestimento3D_10_1;


        //Valores Revestimento Quarto 2
        varAdicionarAlvenaria11 = Double.parseDouble(valorRevestimentoAlvenariaBase11.getText().toString()) * precoCriarAlvenaria;
        varAdicionarAlvenaria11_1 = Double.parseDouble(valorRevestimentoAlvenariaBase11_1.getText().toString()) * precoCriarAlvenaria;
        varAdicionarContraPiso11 = Double.parseDouble(valorRevestimentoContraPiso11.getText().toString()) * precoCriarContraPiso;
        varAdicionarContraPiso11_1 = Double.parseDouble(valorRevestimentoContraPiso11_1.getText().toString()) * precoCriarContraPiso;
        varAplicacaoImpermeabilizante11 = Double.parseDouble(valorRevestimentoImpermeabilidade11.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicacaoImpermeabilizante11_1 = Double.parseDouble(valorRevestimentoImpermeabilidade11_1.getText().toString()) * precoAplicarImpermeabilizante;
        varAplicarPorcelanatoMaior11 = Double.parseDouble(valorRevestimentoPorcelanatoAcima11.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMaior11_1 = Double.parseDouble(valorRevestimentoPorcelanatoAcima11_1.getText().toString()) * precoPorcelanatoMaior;
        varAplicarPorcelanatoMenor11 = Double.parseDouble(valorRevestimentoPorcelanatoMenor11.getText().toString()) * precoPorcelanatoMenor;
        varAplicarPorcelanatoMenor11_1 = Double.parseDouble(valorRevestimentoPorcelanatoMenor11_1.getText().toString()) * precoPorcelanatoMenor;
        varPastilhaVidro_11 = Double.parseDouble(valorRevestimentoPastilhaVidro11.getText().toString()) * precoPastilhaVidro;
        varPastilhaVidro_11_1 = Double.parseDouble(valorRevestimentoPastilhaVidro11_1.getText().toString()) * precoPastilhaVidro;
        varRevestimento3D_11 = Double.parseDouble(valorRevestimento3D11.getText().toString()) * precoRevestimento3D;
        varRevestimento3D_11_1 = Double.parseDouble(valorRevestimento3D11_1.getText().toString()) * precoRevestimento3D;


        valorTotalRevestimentoQuarto2 = varAdicionarAlvenaria11 + varAdicionarAlvenaria11_1 + varAdicionarContraPiso11 + varAdicionarContraPiso11_1 + varAplicacaoImpermeabilizante11 + varAplicacaoImpermeabilizante11_1 + varAplicarPorcelanatoMaior11 + varAplicarPorcelanatoMaior11_1 + varAplicarPorcelanatoMenor11 + varAplicarPorcelanatoMenor11_1 + varPastilhaVidro_11 + varPastilhaVidro_11_1 + varRevestimento3D_11 + varRevestimento3D_11_1;

        valorTotalCategoriaRevestimento = valorTotalRevestimentoAreaServico + valorTotalRevestimentoQuarto2 + valorTotalRevestimentoQuarto1 + valorTotalRevestimentoQuartoSuite + valorTotalRevestimentoSalaEstar + valorTotalRevestimentoSalaJantar + valorTotalRevestimentoSacada + valorTotalRevestimentoLavabo + valorTotalRevestimentoAreaServico + valorTotalRevestimentoBanheiroSocial + valorTotalRevestimentoCozinha;


        //Valores Hidraulica
        varAdicionarTorneiraEletrica = Double.parseDouble(valorHidraulicaTorneiraEletrica1.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraEletrica1 = Double.parseDouble(valorHidraulicaTorneiraEletrica1_1.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraMonocomando = Double.parseDouble(valorHidraulicaTorneiraMonocomando1.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraMonocomando1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando1_1.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraSimples = Double.parseDouble(valorHidraulicaTorneiraSimples1.getText().toString()) * precoTorneiraSimples;
        varAdicionarTorneiraSimples1 = Double.parseDouble(valorHidraulicaTorneiraSimples1_1.getText().toString()) * precoTorneiraSimples;
        varAdicionarValvulaSifao = Double.parseDouble(valorHidraulicaValvulaSifao1.getText().toString()) * precoValvulaSifao;
        varAdicionarValvulaSifao1 = Double.parseDouble(valorHidraulicaValvulaSifao1_1.getText().toString()) * precoValvulaSifao;
        varAdicionarRegistroAcabamento = Double.parseDouble(valorHidraulicaRegistroAcabamento1.getText().toString()) * precoRegistroAcabamento;
        varAdicionarRegistroAcabamento1 = Double.parseDouble(valorHidraulicaRegistroAcabamento1_1.getText().toString()) * precoRegistroAcabamento;
        varAdicionarPontoAgua = Double.parseDouble(valorHidraulicaCriacaoAgua1.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua1_1.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoEsgoto = Double.parseDouble(valorHidraulicaCriacaoEsgoto1.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto1_1.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarRalo10cm = Double.parseDouble(valorHidraulicaRalo10cm1.getText().toString()) * precoRalo10cm;
        varAdicionarRalo10cm1 = Double.parseDouble(valorHidraulicaRalo10cm1_1.getText().toString()) * precoRalo10cm;
        varAdicionarRalo15cm = Double.parseDouble(valorHidraulicaRalo15cm1.getText().toString()) * precoRalo15cm;
        varAdicionarRalo15cm1 = Double.parseDouble(valorHidraulicaRalo15cm1_1.getText().toString()) * precoRalo15cm;

        valorTotalHidraulicaCozinha = varAdicionarTorneiraEletrica + varAdicionarTorneiraEletrica1 + varAdicionarTorneiraMonocomando + varAdicionarTorneiraMonocomando1 + varAdicionarTorneiraSimples + varAdicionarTorneiraSimples1 + varAdicionarValvulaSifao + varAdicionarValvulaSifao1 + varAdicionarRegistroAcabamento + varAdicionarRegistroAcabamento1 + varAdicionarPontoAgua + varAdicionarPontoAgua_1 + varAdicionarPontoEsgoto + varAdicionarPontoEsgoto_1 + varAdicionarRalo10cm + varAdicionarRalo10cm1 + varAdicionarRalo15cm + varAdicionarRalo15cm1;


        //Valores Hidraulica
        varAdicionarTorneiraEletrica2 = Double.parseDouble(valorHidraulicaTorneiraEletrica2.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraEletrica2 = Double.parseDouble(valorHidraulicaTorneiraEletrica2_1.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraMonocomando2 = Double.parseDouble(valorHidraulicaTorneiraMonocomando2.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraMonocomando2_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando2_1.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraSimples2 = Double.parseDouble(valorHidraulicaTorneiraSimples2.getText().toString()) * precoTorneiraSimples;
        varAdicionarTorneiraSimples2_1 = Double.parseDouble(valorHidraulicaTorneiraSimples2_1.getText().toString()) * precoTorneiraSimples;
        varAdicionarValvulaSifao2 = Double.parseDouble(valorHidraulicaValvulaSifao2.getText().toString()) * precoValvulaSifao;
        varAdicionarValvulaSifao2_1 = Double.parseDouble(valorHidraulicaValvulaSifao2_1.getText().toString()) * precoValvulaSifao;
        varAdicionarRegistroAcabamento2 = Double.parseDouble(valorHidraulicaRegistroAcabamento2.getText().toString()) * precoRegistroAcabamento;
        varAdicionarRegistroAcabamento = Double.parseDouble(valorHidraulicaRegistroAcabamento2_1.getText().toString()) * precoRegistroAcabamento;
        varAdicionarPontoAgua_2 = Double.parseDouble(valorHidraulicaCriacaoAgua2.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua2_1.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoEsgoto_2 = Double.parseDouble(valorHidraulicaCriacaoEsgoto2.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto2_1.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarRalo10cm2 = Double.parseDouble(valorHidraulicaRalo10cm2.getText().toString()) * precoRalo10cm;
        varAdicionarRalo10cm2_1 = Double.parseDouble(valorHidraulicaRalo10cm2_1.getText().toString()) * precoRalo10cm;
        varAdicionarRalo15cm2 = Double.parseDouble(valorHidraulicaRalo15cm2.getText().toString()) * precoRalo15cm;
        varAdicionarRalo15cm2_1 = Double.parseDouble(valorHidraulicaRalo15cm2_1.getText().toString()) * precoRalo15cm;
        varAdicionarChuveiro2 = Double.parseDouble(valorHidraulicaChuveiro2.getText().toString()) * precoChuveiro;
        varAdicionarChuveiro2_1 = Double.parseDouble(valorHidraulicaChuveiro2_1.getText().toString()) * precoChuveiro;
        varAdicionarVasoSanitario2 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario2.getText().toString()) * precoInstalarVasoSanitario;
        varAdicionarVasoSanitario2_1 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario2_1.getText().toString()) * precoInstalarVasoSanitario;
        varAdicionarRaloLinear2 = Double.parseDouble(valorHidraulicaRaloLinear2.getText().toString()) * precoRaloLinear;
        varAdicionarRaloLinear2_1 = Double.parseDouble(valorHidraulicaRaloLinear2_1.getText().toString()) * precoRaloLinear;

        valorTotalHidraulicaBanheiroSocial = varAdicionarChuveiro2 + varAdicionarChuveiro2_1 + varAdicionarVasoSanitario2 + varAdicionarVasoSanitario2_1 + varAdicionarRaloLinear2 + varAdicionarRaloLinear2_1 + varAdicionarTorneiraEletrica2 + varAdicionarTorneiraEletrica2_1 + varAdicionarTorneiraMonocomando2 + varAdicionarTorneiraMonocomando2_1 + varAdicionarTorneiraSimples2 + varAdicionarTorneiraSimples2_1 + varAdicionarValvulaSifao2 + varAdicionarValvulaSifao2_1 + varAdicionarRegistroAcabamento2 + varAdicionarRegistroAcabamento2_1 + varAdicionarPontoAgua_2 + varAdicionarPontoAgua_2_1 + varAdicionarPontoEsgoto_2 + varAdicionarPontoEsgoto_2_1 + varAdicionarRalo10cm2 + varAdicionarRalo10cm2_1 + varAdicionarRalo15cm2 + varAdicionarRalo15cm2_1;


        varAdicionarTorneiraEletrica3 = Double.parseDouble(valorHidraulicaTorneiraEletrica3.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraEletrica3 = Double.parseDouble(valorHidraulicaTorneiraEletrica3_1.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraMonocomando3 = Double.parseDouble(valorHidraulicaTorneiraMonocomando3.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraMonocomando3_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando3_1.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraSimples3 = Double.parseDouble(valorHidraulicaTorneiraSimples3.getText().toString()) * precoTorneiraSimples;
        varAdicionarTorneiraSimples3_1 = Double.parseDouble(valorHidraulicaTorneiraSimples3_1.getText().toString()) * precoTorneiraSimples;
        varAdicionarValvulaSifao3 = Double.parseDouble(valorHidraulicaValvulaSifao3.getText().toString()) * precoValvulaSifao;
        varAdicionarValvulaSifao3_1 = Double.parseDouble(valorHidraulicaValvulaSifao3_1.getText().toString()) * precoValvulaSifao;
        varAdicionarRegistroAcabamento3 = Double.parseDouble(valorHidraulicaRegistroAcabamento3.getText().toString()) * precoRegistroAcabamento;
        varAdicionarRegistroAcabamento3_1 = Double.parseDouble(valorHidraulicaRegistroAcabamento3_1.getText().toString()) * precoRegistroAcabamento;
        varAdicionarPontoAgua_3 = Double.parseDouble(valorHidraulicaCriacaoAgua3.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoAgua_3_1 = Double.parseDouble(valorHidraulicaCriacaoAgua3_1.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoEsgoto_3 = Double.parseDouble(valorHidraulicaCriacaoEsgoto3.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarPontoEsgoto_3_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto3_1.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarRalo10cm3 = Double.parseDouble(valorHidraulicaRalo10cm3.getText().toString()) * precoRalo10cm;
        varAdicionarRalo10cm3_1 = Double.parseDouble(valorHidraulicaRalo10cm3_1.getText().toString()) * precoRalo10cm;
        varAdicionarRalo15cm3 = Double.parseDouble(valorHidraulicaRalo15cm3.getText().toString()) * precoRalo15cm;
        varAdicionarRalo15cm3_1 = Double.parseDouble(valorHidraulicaRalo15cm3_1.getText().toString()) * precoRalo15cm;


        valorTotalHidraulicaAreaServico = varAdicionarTorneiraEletrica3 + varAdicionarTorneiraEletrica3_1 + varAdicionarTorneiraMonocomando3 + varAdicionarTorneiraMonocomando3_1 + varAdicionarTorneiraSimples3 + varAdicionarTorneiraSimples3_1 + varAdicionarValvulaSifao3 + varAdicionarValvulaSifao3_1 + varAdicionarRegistroAcabamento3 + varAdicionarRegistroAcabamento3_1 + varAdicionarPontoAgua_3 + varAdicionarPontoAgua_3_1 + varAdicionarPontoEsgoto_3 + varAdicionarPontoEsgoto_3_1 + varAdicionarRalo10cm3 + varAdicionarRalo10cm3_1 + varAdicionarRalo15cm3 + varAdicionarRalo15cm3_1;

        varAdicionarTorneiraEletrica4 = Double.parseDouble(valorHidraulicaTorneiraEletrica4.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraEletrica4 = Double.parseDouble(valorHidraulicaTorneiraEletrica4_1.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraMonocomando4 = Double.parseDouble(valorHidraulicaTorneiraMonocomando4.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraMonocomando4_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando4_1.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraSimples4 = Double.parseDouble(valorHidraulicaTorneiraSimples4.getText().toString()) * precoTorneiraSimples;
        varAdicionarTorneiraSimples4_1 = Double.parseDouble(valorHidraulicaTorneiraSimples4_1.getText().toString()) * precoTorneiraSimples;
        varAdicionarValvulaSifao4 = Double.parseDouble(valorHidraulicaValvulaSifao4.getText().toString()) * precoValvulaSifao;
        varAdicionarValvulaSifao4_1 = Double.parseDouble(valorHidraulicaValvulaSifao4_1.getText().toString()) * precoValvulaSifao;
        varAdicionarRegistroAcabamento4 = Double.parseDouble(valorHidraulicaRegistroAcabamento4.getText().toString()) * precoRegistroAcabamento;
        varAdicionarRegistroAcabamento4_1 = Double.parseDouble(valorHidraulicaRegistroAcabamento4_1.getText().toString()) * precoRegistroAcabamento;
        varAdicionarPontoAgua_4 = Double.parseDouble(valorHidraulicaCriacaoAgua4.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoAgua_4_1 = Double.parseDouble(valorHidraulicaCriacaoAgua4_1.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoEsgoto_4 = Double.parseDouble(valorHidraulicaCriacaoEsgoto4.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarPontoEsgoto_4_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto4_1.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarRalo10cm4 = Double.parseDouble(valorHidraulicaRalo10cm4.getText().toString()) * precoRalo10cm;
        varAdicionarRalo10cm4_1 = Double.parseDouble(valorHidraulicaRalo10cm4_1.getText().toString()) * precoRalo10cm;
        varAdicionarRalo15cm4 = Double.parseDouble(valorHidraulicaRalo15cm4.getText().toString()) * precoRalo15cm;
        varAdicionarRalo15cm4_1 = Double.parseDouble(valorHidraulicaRalo15cm4_1.getText().toString()) * precoRalo15cm;
        varAdicionarChuveiro4 = Double.parseDouble(valorHidraulicaChuveiro4.getText().toString()) * precoChuveiro;
        varAdicionarChuveiro4_1 = Double.parseDouble(valorHidraulicaChuveiro4_1.getText().toString()) * precoChuveiro;
        varAdicionarVasoSanitario4 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario4.getText().toString()) * precoInstalarVasoSanitario;
        varAdicionarVasoSanitario4_1 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario4_1.getText().toString()) * precoInstalarVasoSanitario;
        varAdicionarRaloLinear4 = Double.parseDouble(valorHidraulicaRaloLinear4.getText().toString()) * precoRaloLinear;
        varAdicionarRaloLinear4_1 = Double.parseDouble(valorHidraulicaRaloLinear4_1.getText().toString()) * precoRaloLinear;

        valorTotalHidraulicaBanheiroSuite = varAdicionarChuveiro4 + varAdicionarChuveiro4_1 + varAdicionarVasoSanitario4 + varAdicionarVasoSanitario4_1 + varAdicionarRaloLinear4 + varAdicionarRaloLinear4_1 + varAdicionarTorneiraEletrica4 + varAdicionarTorneiraEletrica4_1 + varAdicionarTorneiraMonocomando4 + varAdicionarTorneiraMonocomando4_1 + varAdicionarTorneiraSimples4 + varAdicionarTorneiraSimples4_1 + varAdicionarValvulaSifao4 + varAdicionarValvulaSifao4_1 + varAdicionarRegistroAcabamento4 + varAdicionarRegistroAcabamento4_1 + varAdicionarPontoAgua_4 + varAdicionarPontoAgua_4_1 + varAdicionarPontoEsgoto_4 + varAdicionarPontoEsgoto_4_1 + varAdicionarRalo10cm4 + varAdicionarRalo10cm4_1 + varAdicionarRalo15cm4 + varAdicionarRalo15cm4_1;


        varAdicionarTorneiraEletrica5 = Double.parseDouble(valorHidraulicaTorneiraEletrica5.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraEletrica5 = Double.parseDouble(valorHidraulicaTorneiraEletrica5_1.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraMonocomando5 = Double.parseDouble(valorHidraulicaTorneiraMonocomando5.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraMonocomando5_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando5_1.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraSimples5 = Double.parseDouble(valorHidraulicaTorneiraSimples5.getText().toString()) * precoTorneiraSimples;
        varAdicionarTorneiraSimples5_1 = Double.parseDouble(valorHidraulicaTorneiraSimples5_1.getText().toString()) * precoTorneiraSimples;
        varAdicionarValvulaSifao5 = Double.parseDouble(valorHidraulicaValvulaSifao5.getText().toString()) * precoValvulaSifao;
        varAdicionarValvulaSifao5_1 = Double.parseDouble(valorHidraulicaValvulaSifao5_1.getText().toString()) * precoValvulaSifao;
        varAdicionarRegistroAcabamento5 = Double.parseDouble(valorHidraulicaRegistroAcabamento5.getText().toString()) * precoRegistroAcabamento;
        varAdicionarRegistroAcabamento5_1 = Double.parseDouble(valorHidraulicaRegistroAcabamento5_1.getText().toString()) * precoRegistroAcabamento;
        varAdicionarPontoAgua_5 = Double.parseDouble(valorHidraulicaCriacaoAgua5.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua5_1.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoEsgoto_5 = Double.parseDouble(valorHidraulicaCriacaoEsgoto5.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto5_1.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarRalo10cm5 = Double.parseDouble(valorHidraulicaRalo10cm5.getText().toString()) * precoRalo10cm;
        varAdicionarRalo10cm5_1 = Double.parseDouble(valorHidraulicaRalo10cm5_1.getText().toString()) * precoRalo10cm;
        varAdicionarRalo15cm5 = Double.parseDouble(valorHidraulicaRalo15cm5.getText().toString()) * precoRalo15cm;
        varAdicionarRalo15cm5_1 = Double.parseDouble(valorHidraulicaRalo15cm5_1.getText().toString()) * precoRalo15cm;
        varAdicionarVasoSanitario5 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario5.getText().toString()) * precoInstalarVasoSanitario;
        varAdicionarVasoSanitario5_1 = Double.parseDouble(valorHidraulicaInstalarVasoSanitario5_1.getText().toString()) * precoInstalarVasoSanitario;
        varAdicionarRaloLinear5 = Double.parseDouble(valorHidraulicaRaloLinear5.getText().toString()) * precoRaloLinear;
        varAdicionarRaloLinear5_1 = Double.parseDouble(valorHidraulicaRaloLinear5_1.getText().toString()) * precoRaloLinear;
        varAdicionarRaloLinear5_1 = Double.parseDouble(valorHidraulicaRaloLinear5_1.getText().toString()) * precoRaloLinear;


        valorTotalHidraulicaLavabo = +varAdicionarVasoSanitario5 + varAdicionarVasoSanitario5_1 + varAdicionarRaloLinear5 + varAdicionarRaloLinear5_1 + varAdicionarTorneiraEletrica5 + varAdicionarTorneiraEletrica5_1 + varAdicionarTorneiraMonocomando5 + varAdicionarTorneiraMonocomando5_1 + varAdicionarTorneiraSimples5 + varAdicionarTorneiraSimples5_1 + varAdicionarValvulaSifao5 + varAdicionarValvulaSifao5_1 + varAdicionarRegistroAcabamento5 + varAdicionarRegistroAcabamento5_1 + varAdicionarPontoAgua_5 + varAdicionarPontoAgua_5_1 + varAdicionarPontoEsgoto_5 + varAdicionarPontoEsgoto_5_1 + varAdicionarRalo10cm5 + varAdicionarRalo10cm5_1 + varAdicionarRalo15cm5 + varAdicionarRalo15cm5_1;

        varAdicionarTorneiraEletrica6 = Double.parseDouble(valorHidraulicaTorneiraEletrica6.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraEletrica6 = Double.parseDouble(valorHidraulicaTorneiraEletrica6_1.getText().toString()) * precoTorneiraEletrica;
        varAdicionarTorneiraMonocomando6 = Double.parseDouble(valorHidraulicaTorneiraMonocomando6.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraMonocomando6_1 = Double.parseDouble(valorHidraulicaTorneiraMonocomando6_1.getText().toString()) * precoTorneiraMonocomando;
        varAdicionarTorneiraSimples6 = Double.parseDouble(valorHidraulicaTorneiraSimples6.getText().toString()) * precoTorneiraSimples;
        varAdicionarTorneiraSimples6_1 = Double.parseDouble(valorHidraulicaTorneiraSimples6_1.getText().toString()) * precoTorneiraSimples;
        varAdicionarValvulaSifao6 = Double.parseDouble(valorHidraulicaValvulaSifao6.getText().toString()) * precoValvulaSifao;
        varAdicionarValvulaSifao6_1 = Double.parseDouble(valorHidraulicaValvulaSifao6_1.getText().toString()) * precoValvulaSifao;
        varAdicionarRegistroAcabamento6 = Double.parseDouble(valorHidraulicaRegistroAcabamento6.getText().toString()) * precoRegistroAcabamento;
        varAdicionarRegistroAcabamento = Double.parseDouble(valorHidraulicaRegistroAcabamento6_1.getText().toString()) * precoRegistroAcabamento;
        varAdicionarPontoAgua_6 = Double.parseDouble(valorHidraulicaCriacaoAgua6.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoAgua_1 = Double.parseDouble(valorHidraulicaCriacaoAgua6_1.getText().toString()) * precoCriacaoAgua;
        varAdicionarPontoEsgoto_6 = Double.parseDouble(valorHidraulicaCriacaoEsgoto6.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarPontoEsgoto_1 = Double.parseDouble(valorHidraulicaCriacaoEsgoto6_1.getText().toString()) * precoCriacaoEsgoto;
        varAdicionarRalo10cm6 = Double.parseDouble(valorHidraulicaRalo10cm6.getText().toString()) * precoRalo10cm;
        varAdicionarRalo10cm6_1 = Double.parseDouble(valorHidraulicaRalo10cm6_1.getText().toString()) * precoRalo10cm;
        varAdicionarRalo15cm6 = Double.parseDouble(valorHidraulicaRalo15cm6.getText().toString()) * precoRalo15cm;
        varAdicionarRalo15cm6_1 = Double.parseDouble(valorHidraulicaRalo15cm6_1.getText().toString()) * precoRalo15cm;


        valorTotalHidraulicaSacada = varAdicionarTorneiraEletrica6 + varAdicionarTorneiraEletrica6_1 + varAdicionarTorneiraMonocomando6 + varAdicionarTorneiraMonocomando6_1 + varAdicionarTorneiraSimples6 + varAdicionarTorneiraSimples6_1 + varAdicionarValvulaSifao6 + varAdicionarValvulaSifao6_1 + varAdicionarRegistroAcabamento6 + varAdicionarRegistroAcabamento6_1 + varAdicionarPontoAgua_6 + varAdicionarPontoAgua_6_1 + varAdicionarPontoEsgoto_6 + varAdicionarPontoEsgoto_6_1 + varAdicionarRalo10cm6 + varAdicionarRalo10cm6_1 + varAdicionarRalo15cm6 + varAdicionarRalo15cm6_1;


        valorTotalCategoriaHidraulica = valorTotalHidraulicaSacada + valorTotalHidraulicaLavabo + valorTotalHidraulicaBanheiroSuite + valorTotalHidraulicaAreaServico + valorTotalHidraulicaBanheiroSocial + valorTotalHidraulicaCozinha;

        //Pintura
        varPorta = Double.parseDouble(valorPinturaPorta1.getText().toString()) * precoPinturaPorta;
        varPorta1 = Double.parseDouble(valorPinturaPorta1_1.getText().toString()) * precoPinturaPorta;
        varJanela = Double.parseDouble(valorPinturaJanela1.getText().toString()) * precoPinturaJanela;
        varJanela1 = Double.parseDouble(valorPinturaJanela1_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo = Double.parseDouble(valorPinturaEfeitoDecorativo1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo1 = Double.parseDouble(valorPinturaEfeitoDecorativo1_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso = Double.parseDouble(valorPinturaReparoGesso1.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso1 = Double.parseDouble(valorPinturaReparoGesso1_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaCozinha = varPorta + varPorta1 + varJanela + varJanela1 + varEfeitoDecorativo + varEfeitoDecorativo1 + varReparoGesso + varReparoGesso1;


        varPorta2 = Double.parseDouble(valorPinturaPorta2.getText().toString()) * precoPinturaPorta;
        varPorta2_1 = Double.parseDouble(valorPinturaPorta2_1.getText().toString()) * precoPinturaPorta;
        varJanela2 = Double.parseDouble(valorPinturaJanela2.getText().toString()) * precoPinturaJanela;
        varJanela2_1 = Double.parseDouble(valorPinturaJanela2_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo2 = Double.parseDouble(valorPinturaEfeitoDecorativo2.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo2_1 = Double.parseDouble(valorPinturaEfeitoDecorativo2_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso2 = Double.parseDouble(valorPinturaReparoGesso2.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso2_1 = Double.parseDouble(valorPinturaReparoGesso2_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaBanheiroSocial = varPorta2 + varPorta2_1 + varJanela2 + varJanela2_1 + varEfeitoDecorativo2 + varEfeitoDecorativo2_1 + varReparoGesso2 + varReparoGesso2_1;


        varPorta3 = Double.parseDouble(valorPinturaPorta3.getText().toString()) * precoPinturaPorta;
        varPorta3_1 = Double.parseDouble(valorPinturaPorta3_1.getText().toString()) * precoPinturaPorta;
        varJanela3 = Double.parseDouble(valorPinturaJanela3.getText().toString()) * precoPinturaJanela;
        varJanela3_1 = Double.parseDouble(valorPinturaJanela3_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo3 = Double.parseDouble(valorPinturaEfeitoDecorativo3.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo3_1 = Double.parseDouble(valorPinturaEfeitoDecorativo3_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso3 = Double.parseDouble(valorPinturaReparoGesso3.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso3_1 = Double.parseDouble(valorPinturaReparoGesso3_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaAreaServico = varPorta3 + varPorta3_1 + varJanela3 + varJanela3_1 + varEfeitoDecorativo3 + varEfeitoDecorativo3_1 + varReparoGesso3 + varReparoGesso3_1;


        varPorta4 = Double.parseDouble(valorPinturaPorta4.getText().toString()) * precoPinturaPorta;
        varPorta4_1 = Double.parseDouble(valorPinturaPorta4_1.getText().toString()) * precoPinturaPorta;
        varJanela4 = Double.parseDouble(valorPinturaJanela4.getText().toString()) * precoPinturaJanela;
        varJanela4_1 = Double.parseDouble(valorPinturaJanela4_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo4 = Double.parseDouble(valorPinturaEfeitoDecorativo4.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo4_1 = Double.parseDouble(valorPinturaEfeitoDecorativo4_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso4 = Double.parseDouble(valorPinturaReparoGesso4.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso4_1 = Double.parseDouble(valorPinturaReparoGesso4_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaBanheiroSuite = varPorta4 + varPorta4_1 + varJanela4 + varJanela4_1 + varEfeitoDecorativo4 + varEfeitoDecorativo4_1 + varReparoGesso4 + varReparoGesso4_1;


        varPorta5 = Double.parseDouble(valorPinturaPorta5.getText().toString()) * precoPinturaPorta;
        varPorta5_1 = Double.parseDouble(valorPinturaPorta5_1.getText().toString()) * precoPinturaPorta;
        varJanela5 = Double.parseDouble(valorPinturaJanela5.getText().toString()) * precoPinturaJanela;
        varJanela5_1 = Double.parseDouble(valorPinturaJanela5_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo5 = Double.parseDouble(valorPinturaEfeitoDecorativo5.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo5_1 = Double.parseDouble(valorPinturaEfeitoDecorativo5_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso5 = Double.parseDouble(valorPinturaReparoGesso5.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso5_1 = Double.parseDouble(valorPinturaReparoGesso5_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaLavabo = varPorta5 + varPorta5_1 + varJanela5 + varJanela5_1 + varEfeitoDecorativo5 + varEfeitoDecorativo5_1 + varReparoGesso5 + varReparoGesso5_1;


        varPorta6 = Double.parseDouble(valorPinturaPorta6.getText().toString()) * precoPinturaPorta;
        varPorta6_1 = Double.parseDouble(valorPinturaPorta6_1.getText().toString()) * precoPinturaPorta;
        varJanela6 = Double.parseDouble(valorPinturaJanela6.getText().toString()) * precoPinturaJanela;
        varJanela6_1 = Double.parseDouble(valorPinturaJanela6_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo6 = Double.parseDouble(valorPinturaEfeitoDecorativo6.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo6_1 = Double.parseDouble(valorPinturaEfeitoDecorativo6_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso6 = Double.parseDouble(valorPinturaReparoGesso6.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso6_1 = Double.parseDouble(valorPinturaReparoGesso6_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaSacada = varPorta6 + varPorta6_1 + varJanela6 + varJanela6_1 + varEfeitoDecorativo6 + varEfeitoDecorativo6_1 + varReparoGesso6 + varReparoGesso6_1;


        varPorta7 = Double.parseDouble(valorPinturaPorta7.getText().toString()) * precoPinturaPorta;
        varPorta7_1 = Double.parseDouble(valorPinturaPorta7_1.getText().toString()) * precoPinturaPorta;
        varJanela7 = Double.parseDouble(valorPinturaJanela7.getText().toString()) * precoPinturaJanela;
        varJanela7_1 = Double.parseDouble(valorPinturaJanela7_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo7 = Double.parseDouble(valorPinturaEfeitoDecorativo7.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo7_1 = Double.parseDouble(valorPinturaEfeitoDecorativo7_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso7 = Double.parseDouble(valorPinturaReparoGesso7.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso7_1 = Double.parseDouble(valorPinturaReparoGesso7_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaSalaJantar = varPorta7 + varPorta7_1 + varJanela7 + varJanela7_1 + varEfeitoDecorativo7 + varEfeitoDecorativo7_1 + varReparoGesso7 + varReparoGesso7_1;


        varPorta8 = Double.parseDouble(valorPinturaPorta8.getText().toString()) * precoPinturaPorta;
        varPorta8_1 = Double.parseDouble(valorPinturaPorta8_1.getText().toString()) * precoPinturaPorta;
        varJanela8 = Double.parseDouble(valorPinturaJanela8.getText().toString()) * precoPinturaJanela;
        varJanela8_1 = Double.parseDouble(valorPinturaJanela8_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo8 = Double.parseDouble(valorPinturaEfeitoDecorativo8.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo8_1 = Double.parseDouble(valorPinturaEfeitoDecorativo8_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso8 = Double.parseDouble(valorPinturaReparoGesso8.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso8_1 = Double.parseDouble(valorPinturaReparoGesso8_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaSalaEstar = varPorta8 + varPorta8_1 + varJanela8 + varJanela8_1 + varEfeitoDecorativo8 + varEfeitoDecorativo8_1 + varReparoGesso8 + varReparoGesso8_1;


        varPorta9 = Double.parseDouble(valorPinturaPorta9.getText().toString()) * precoPinturaPorta;
        varPorta9_1 = Double.parseDouble(valorPinturaPorta9_1.getText().toString()) * precoPinturaPorta;
        varJanela9 = Double.parseDouble(valorPinturaJanela9.getText().toString()) * precoPinturaJanela;
        varJanela9_1 = Double.parseDouble(valorPinturaJanela9_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo9 = Double.parseDouble(valorPinturaEfeitoDecorativo9.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo9_1 = Double.parseDouble(valorPinturaEfeitoDecorativo9_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso9 = Double.parseDouble(valorPinturaReparoGesso9.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso9_1 = Double.parseDouble(valorPinturaReparoGesso9_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaQuarto1 = varPorta9 + varPorta9_1 + varJanela9 + varJanela9_1 + varEfeitoDecorativo9 + varEfeitoDecorativo9_1 + varReparoGesso9 + varReparoGesso9_1;


        varPorta10 = Double.parseDouble(valorPinturaPorta10.getText().toString()) * precoPinturaPorta;
        varPorta10_1 = Double.parseDouble(valorPinturaPorta10_1.getText().toString()) * precoPinturaPorta;
        varJanela10 = Double.parseDouble(valorPinturaJanela10.getText().toString()) * precoPinturaJanela;
        varJanela10_1 = Double.parseDouble(valorPinturaJanela10_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo10 = Double.parseDouble(valorPinturaEfeitoDecorativo10.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo10_1 = Double.parseDouble(valorPinturaEfeitoDecorativo10_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso10 = Double.parseDouble(valorPinturaReparoGesso10.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso10_1 = Double.parseDouble(valorPinturaReparoGesso10_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaQuarto2 = varPorta10 + varPorta10_1 + varJanela10 + varJanela10_1 + varEfeitoDecorativo10 + varEfeitoDecorativo10_1 + varReparoGesso10 + varReparoGesso10_1;


        varPorta11 = Double.parseDouble(valorPinturaPorta11.getText().toString()) * precoPinturaPorta;
        varPorta11_1 = Double.parseDouble(valorPinturaPorta11_1.getText().toString()) * precoPinturaPorta;
        varJanela11 = Double.parseDouble(valorPinturaJanela11.getText().toString()) * precoPinturaJanela;
        varJanela11_1 = Double.parseDouble(valorPinturaJanela11_1.getText().toString()) * precoPinturaJanela;
        varEfeitoDecorativo11 = Double.parseDouble(valorPinturaEfeitoDecorativo11.getText().toString()) * precoPinturaEfeitoDecorativo;
        varEfeitoDecorativo11_1 = Double.parseDouble(valorPinturaEfeitoDecorativo11_1.getText().toString()) * precoPinturaEfeitoDecorativo;
        varReparoGesso11 = Double.parseDouble(valorPinturaReparoGesso11.getText().toString()) * precoPinturaReparoGesso;
        varReparoGesso11_1 = Double.parseDouble(valorPinturaReparoGesso11_1.getText().toString()) * precoPinturaReparoGesso;

        valorTotalPinturaQuartoSuite = varPorta11 + varPorta11_1 + varJanela11 + varJanela11_1 + varEfeitoDecorativo11 + varEfeitoDecorativo11_1 + varReparoGesso11 + varReparoGesso11_1;


        valorTotalCategoriaPintura = valorTotalPinturaQuarto2 + valorTotalPinturaQuarto1 + valorTotalPinturaQuartoSuite + valorTotalPinturaSalaEstar + valorTotalPinturaSalaJantar + valorTotalPinturaSacada + valorTotalPinturaLavabo + valorTotalPinturaBanheiroSocial + valorTotalPinturaBanheiroSuite + valorTotalPinturaCozinha + valorTotalPinturaAreaServico;

        latitude2 = totalDemolicao + valorTotalCategoriaHidraulica + valorTotalCategoriaPintura + valorTotalCategoriaRevestimento + valorTotalCategoriaArt2;



        valorTotalCategoriaArt2 = 0;
        if (BtncheckBoxArtArCondicionado.isChecked() || BtncheckboxArtEnvidracamento.isChecked() || BtncheckboxArtPedrasMarmore.isChecked() || BtncheckboxArtNovosRevestimentos.isChecked() || BtncheckboxArtEletrica.isChecked() || BtncheckboxArtHidraulica.isChecked() || BtncheckboxArtBox.isChecked() || BtncheckboxArtGesso.isChecked() || BtncheckboxArtDemolicao.isChecked() || BtncheckboxArtMoveisPlanejados.isChecked() || BtncheckboxArtDeslocamento.isChecked() )
        {


            if (BtncheckBoxArtArCondicionado.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtArcondicionado;
            }
            if (BtncheckboxArtEnvidracamento.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtEnvidracamento;
            }
            if (BtncheckboxArtPedrasMarmore.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtPedrasMarmore;
            }
            if (BtncheckboxArtNovosRevestimentos.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtNovosRevestimentos;
            }
            if (BtncheckboxArtEletrica.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtEletrica;
            }
            if (BtncheckboxArtHidraulica.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtHidraulica;

            }
            if (BtncheckboxArtBox.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtBox;

            }
            if (BtncheckboxArtGesso.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtGesso;


            }
            if (BtncheckboxArtDemolicao.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtDemolicaoParedeNaoEstrutural;

            }
            if (BtncheckboxArtMoveisPlanejados.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtMoveisPlanejados;

            }
            if (BtncheckboxArtDeslocamento.isChecked()) {
                valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtDeslocamentoPontoGas;

            }

        }
        else {
            valorTotalCategoriaArt2 = 0;
        }

        if (valorTotalCategoriaArt2 > 0){
            valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtTaxa ;
        }
        latitude2 = 0;
        latitude2 = totalDemolicao + valorTotalCategoriaHidraulica + valorTotalCategoriaPintura + valorTotalCategoriaRevestimento + valorTotalCategoriaArt2;


        String valorNotaTexto = df.format(latitude2);

        alterarValorNota = Integer.toString(numeroNotaAtual);
        exibirValorNota.setText(valorNotaTexto);






    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.



        // etc.
        super.onSaveInstanceState(savedInstanceState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance

    }
    public void openFolder()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RelatóriosRagonezi/Orcamentos");
        Uri uri = FileProvider.getUriForFile(this, "vostore.apporcamentoragonezi", docsFolder);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(Intent.createChooser(intent, "Abrindo pasta"));
    }
}
