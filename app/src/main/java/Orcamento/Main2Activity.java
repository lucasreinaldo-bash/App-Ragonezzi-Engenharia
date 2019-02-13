package Orcamento;

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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
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

import Firebase.ConfiguracaoFirebase;
import Permissoes.PermissionsChecker;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import vostore.apporcamentoragonezi.R;



public class Main2Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private String caminhoDaImagem;
    private Uri uri;
    //  private ImageButton btn1, btn2, btn3, btn4, btn5, btn6;
    private Button btnART, btnCam, btnDemolicao, btnRevestimento, btnHidraulica, btnPintura, btnCalculadora, btnFinalizar;
    private LinearLayout linearART, linearDemolicao, linearPintura, linearRevestimento, linearHidraulica, linearFinalizar;
    FirebaseAuth mAuth, auth;
    GoogleApiClient googleApiClient1;
    private final int TAB_FIRST = FragNavController.TAB1;
    private final int TAB_SECOND = FragNavController.TAB2;
    private final int TAB_THIRD = FragNavController.TAB3;
    private FragNavController fragNavController;


    private static final int CALC_REQUEST_CODE = 0;

    private BigDecimal value;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private RelativeLayout relativeLayout;
    private Document document;
    private CheckBox checkBoxCozinha;
    private CheckBox BtncheckBoxArtArCondicionado, BtncheckboxArtEnvidracamento, BtncheckboxArtPedrasMarmore, BtncheckboxArtNovosRevestimentos, BtncheckboxArtEletrica, BtncheckboxArtHidraulica, BtncheckboxArtBox, BtncheckboxArtGesso, BtncheckboxArtDemolicao, BtncheckboxArtMoveisPlanejados, BtncheckboxArtDeslocamento;
    private CheckBox checkBoxPinturaCozinha, checkBoxPinturaBanheiroSocial, checkBoxPinturaAreaServico, checkBoxPinturaBanheiroSuite, checkBoxPinturaLavabo, checkBoxPinturaSacadaVaranda, checkBoxPinturaSalaJantar, checkBoxPinturaSalaEstar, checkBoxPinturaQuartoSuite, checkBoxPinturaQuarto1, checkBoxPinturaQuarto2;
    private CheckBox checkBoxHidraulicaCozinha, checkBoxHidraulicaBanheiroSocial, checkBoxHidraulicaAreaServico, checkBoxHidraulicaBanheiroSuite, checkBoxHidraulicaLavabo, checkBoxHidraulicaSacadaVaranda, checkBoxHidraulicaSalaJantar, checkBoxHidraulicaSalaEstar, checkBoxHidraulicaQuartoSuite, checkBoxHidraulicaQuarto1, checkBoxHidraulicaQuarto2;
    private CheckBox checkBoxRevestimentoCozinha, checkBoxRevestimentoBanheiroSocial, checkBoxRevestimentoAreaServico, checkBoxRevestimentoBanheiroSuite, checkBoxRevestimentoLavabo, checkBoxRevestimentoSacadaVaranda, checkBoxRevestimentoSalaJantar, checkBoxRevestimentoSalaEstar, checkBoxRevestimentoQuartoSuite, checkBoxRevestimentoQuarto1, checkBoxRevestimentoQuarto2;
    private CheckBox checkBoxBanheiroSocial, checkBoxAreaServico, checkBoxBanheiroSuite, checkBoxLavabo, checkBoxSacadaVaranda, checkBoxSalaJantar, checkBoxSalaEstar, checkBoxQuartoSuite, checkBoxQuarto1, checkBoxQuarto2;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9, linearLayout10, linearLayout11;
    private LinearLayout linearLayoutPintura1, linearLayoutPintura2, linearLayoutPintura3, linearLayoutPintura4, linearLayoutPintura5, linearLayoutPintura6, linearLayoutPintura7, linearLayoutPintura8, linearLayoutPintura9, linearLayoutPintura10, linearLayoutPintura11;
    private LinearLayout linearLayoutHidraulica1, linearLayoutHidraulica2, linearLayoutHidraulica3, linearLayoutHidraulica4, linearLayoutHidraulica5, linearLayoutHidraulica6, linearLayoutHidraulica7, linearLayoutHidraulica8, linearLayoutHidraulica9, linearLayoutHidraulica10, linearLayoutHidraulica11;
    private LinearLayout linearLayoutRevestimento1, linearLayoutRevestimento2, linearLayoutRevestimento3, linearLayoutRevestimento4, linearLayoutRevestimento5, linearLayoutRevestimento6, linearLayoutRevestimento7, linearLayoutRevestimento8, linearLayoutRevestimento9, linearLayoutRevestimento10, linearLayoutRevestimento11;
    private EditText valorRevestimentoParede1, valorRevestimentoParede1_1, valorRemocaoPiso1, valorRemocaoPiso1_1, valorRemocaoPia1, valorRemocaoPia1_1, valorRemocacAlvenaria1, valorRemocacAlvenaria1_1, valorRemocaoTanque1, valorRemocaoTanque1_1, valorRasgarCaixinha4x2_1, valorRasgarCaixinha4x2_1_1, valorRasgarCaixinha4x4_1, valorRasgarCaixinha4x4_1_1, valorRasgarHidraulica1, valorRasgarHidraulica1_1, valorRemoverGesso1, valorRemoverGesso1_1, valorRemoverVaso1, valorRemoverVaso1_1, valorRemoverVao1, valorRemoverVao1_1,
            valorRevestimentoParede2, valorRevestimentoParede2_1, valorRemocaoPiso2, valorRemocaoPiso2_1, valorRemocaoPia2, valorRemocaoPia2_1, valorRemocacAlvenaria2, valorRemocacAlvenaria2_1, valorRemocaoTanque2, valorRemocaoTanque2_1, valorRasgarCaixinha4x2_2, valorRasgarCaixinha4x2_2_1, valorRasgarCaixinha4x4_2, valorRasgarCaixinha4x4_2_1, valorRasgarHidraulica2, valorRasgarHidraulica2_1, valorRemoverGesso2, valorRemoverGesso2_1, valorRemoverVaso2, valorRemoverVaso2_1, valorRemoverVao2, valorRemoverVao2_1,
            valorRevestimentoParede3, valorRevestimentoParede3_1, valorRemocaoPiso3, valorRemocaoPiso3_1, valorRemocaoPia3, valorRemocaoPia3_1, valorRemocacAlvenaria3, valorRemocacAlvenaria3_1, valorRemocaoTanque3, valorRemocaoTanque3_1, valorRasgarCaixinha4x2_3, valorRasgarCaixinha4x2_3_1, valorRasgarCaixinha4x4_3, valorRasgarCaixinha4x4_3_1, valorRasgarHidraulica3, valorRasgarHidraulica3_1, valorRemoverGesso3, valorRemoverGesso3_1, valorRemoverVaso3, valorRemoverVaso3_1, valorRemoverVao3, valorRemoverVao3_1,
            valorRevestimentoParede4, valorRevestimentoParede4_1, valorRemocaoPiso4, valorRemocaoPiso4_1, valorRemocaoPia4, valorRemocaoPia4_1, valorRemocacAlvenaria4, valorRemocacAlvenaria4_1, valorRemocaoTanque4, valorRemocaoTanque4_1, valorRasgarCaixinha4x2_4, valorRasgarCaixinha4x2_4_1, valorRasgarCaixinha4x4_4, valorRasgarCaixinha4x4_4_1, valorRasgarHidraulica4, valorRasgarHidraulica4_1, valorRemoverGesso4, valorRemoverGesso4_1, valorRemoverVaso4, valorRemoverVaso4_1, valorRemoverVao4, valorRemoverVao4_1,
            valorRevestimentoParede5, valorRevestimentoParede5_1, valorRemocaoPiso5, valorRemocaoPiso5_1, valorRemocaoPia5, valorRemocaoPia5_1, valorRemocacAlvenaria5, valorRemocacAlvenaria5_1, valorRemocaoTanque5, valorRemocaoTanque5_1, valorRasgarCaixinha4x2_5, valorRasgarCaixinha4x2_5_1, valorRasgarCaixinha4x4_5, valorRasgarCaixinha4x4_5_1, valorRasgarHidraulica5, valorRasgarHidraulica5_1, valorRemoverGesso5, valorRemoverGesso5_1, valorRemoverVaso5, valorRemoverVaso5_1, valorRemoverVao5, valorRemoverVao5_1,
            valorRevestimentoParede6, valorRevestimentoParede6_1, valorRemocaoPiso6, valorRemocaoPiso6_1, valorRemocaoPia6, valorRemocaoPia6_1, valorRemocacAlvenaria6, valorRemocacAlvenaria6_1, valorRemocaoTanque6, valorRemocaoTanque6_1, valorRasgarCaixinha4x2_6, valorRasgarCaixinha4x2_6_1, valorRasgarCaixinha4x4_6, valorRasgarCaixinha4x4_6_1, valorRasgarHidraulica6, valorRasgarHidraulica6_1, valorRemoverGesso6, valorRemoverGesso6_1, valorRemoverVaso6, valorRemoverVaso6_1, valorRemoverVao6, valorRemoverVao6_1,
            valorRevestimentoParede7, valorRevestimentoParede7_1, valorRemocaoPiso7, valorRemocaoPiso7_1, valorRemocaoPia7, valorRemocaoPia7_1, valorRemocacAlvenaria7, valorRemocacAlvenaria7_1, valorRemocaoTanque7, valorRemocaoTanque7_1, valorRasgarCaixinha4x2_7, valorRasgarCaixinha4x2_7_1, valorRasgarCaixinha4x4_7, valorRasgarCaixinha4x4_7_1, valorRasgarHidraulica7, valorRasgarHidraulica7_1, valorRemoverGesso7, valorRemoverGesso7_1, valorRemoverVaso7, valorRemoverVaso7_1, valorRemoverVao7, valorRemoverVao7_1,
            valorRevestimentoParede8, valorRevestimentoParede8_1, valorRemocaoPiso8, valorRemocaoPiso8_1, valorRemocaoPia8, valorRemocaoPia8_1, valorRemocacAlvenaria8, valorRemocacAlvenaria8_1, valorRemocaoTanque8, valorRemocaoTanque8_1, valorRasgarCaixinha4x2_8, valorRasgarCaixinha4x2_8_1, valorRasgarCaixinha4x4_8, valorRasgarCaixinha4x4_8_1, valorRasgarHidraulica8, valorRasgarHidraulica8_1, valorRemoverGesso8, valorRemoverGesso8_1, valorRemoverVaso8, valorRemoverVaso8_1, valorRemoverVao8, valorRemoverVao8_1,
            valorRevestimentoParede9, valorRevestimentoParede9_1, valorRemocaoPiso9, valorRemocaoPiso9_1, valorRemocaoPia9, valorRemocaoPia9_1, valorRemocacAlvenaria9, valorRemocacAlvenaria9_1, valorRemocaoTanque9, valorRemocaoTanque9_1, valorRasgarCaixinha4x2_9, valorRasgarCaixinha4x2_9_1, valorRasgarCaixinha4x4_9, valorRasgarCaixinha4x4_9_1, valorRasgarHidraulica9, valorRasgarHidraulica9_1, valorRemoverGesso9, valorRemoverGesso9_1, valorRemoverVaso9, valorRemoverVaso9_1, valorRemoverVao9, valorRemoverVao9_1,
            valorRevestimentoParede10, valorRevestimentoParede10_1, valorRemocaoPiso10, valorRemocaoPiso10_1, valorRemocaoPia10, valorRemocaoPia10_1, valorRemocacAlvenaria10, valorRemocacAlvenaria10_1, valorRemocaoTanque10, valorRemocaoTanque10_1, valorRasgarCaixinha4x2_10, valorRasgarCaixinha4x2_10_1, valorRasgarCaixinha4x4_10, valorRasgarCaixinha4x4_10_1, valorRasgarHidraulica10, valorRasgarHidraulica10_1, valorRemoverGesso10, valorRemoverGesso10_1, valorRemoverVaso10, valorRemoverVaso10_1, valorRemoverVao10, valorRemoverVao10_1,
            valorRevestimentoParede11, valorRevestimentoParede11_1, valorRemocaoPiso11, valorRemocaoPiso11_1, valorRemocaoPia11, valorRemocaoPia11_1, valorRemocacAlvenaria11, valorRemocacAlvenaria11_1, valorRemocaoTanque11, valorRemocaoTanque11_1, valorRasgarCaixinha4x2_11, valorRasgarCaixinha4x2_11_1, valorRasgarCaixinha4x4_11, valorRasgarCaixinha4x4_11_1, valorRasgarHidraulica11, valorRasgarHidraulica11_1, valorRemoverGesso11, valorRemoverGesso11_1, valorRemoverVaso11, valorRemoverVaso11_1, valorRemoverVao11, valorRemoverVao11_1;

    //Revestimento
    private EditText valorRevestimentoAlvenariaBase1, valorRevestimentoAlvenariaBase1_1, valorRevestimentoAlvenariaBase2, valorRevestimentoAlvenariaBase2_1, valorRevestimentoAlvenariaBase3, valorRevestimentoAlvenariaBase3_1, valorRevestimentoAlvenariaBase4, valorRevestimentoAlvenariaBase4_1, valorRevestimentoAlvenariaBase5, valorRevestimentoAlvenariaBase5_1, valorRevestimentoAlvenariaBase6, valorRevestimentoAlvenariaBase6_1, valorRevestimentoAlvenariaBase7, valorRevestimentoAlvenariaBase7_1, valorRevestimentoAlvenariaBase8, valorRevestimentoAlvenariaBase8_1, valorRevestimentoAlvenariaBase9, valorRevestimentoAlvenariaBase9_1, valorRevestimentoAlvenariaBase10, valorRevestimentoAlvenariaBase10_1, valorRevestimentoAlvenariaBase11, valorRevestimentoAlvenariaBase11_1,
            valorRevestimentoContraPiso1, valorRevestimentoContraPiso1_1, valorRevestimentoContraPiso2, valorRevestimentoContraPiso2_1, valorRevestimentoContraPiso3, valorRevestimentoContraPiso3_1, valorRevestimentoContraPiso4, valorRevestimentoContraPiso4_1, valorRevestimentoContraPiso5, valorRevestimentoContraPiso5_1, valorRevestimentoContraPiso6, valorRevestimentoContraPiso6_1, valorRevestimentoContraPiso7, valorRevestimentoContraPiso7_1, valorRevestimentoContraPiso8, valorRevestimentoContraPiso8_1, valorRevestimentoContraPiso9, valorRevestimentoContraPiso9_1, valorRevestimentoContraPiso10, valorRevestimentoContraPiso10_1, valorRevestimentoContraPiso11, valorRevestimentoContraPiso11_1,
            valorRevestimentoImpermeabilidade1, valorRevestimentoImpermeabilidade1_1, valorRevestimentoImpermeabilidade2, valorRevestimentoImpermeabilidade2_1, valorRevestimentoImpermeabilidade3, valorRevestimentoImpermeabilidade3_1, valorRevestimentoImpermeabilidade4, valorRevestimentoImpermeabilidade4_1, valorRevestimentoImpermeabilidade5, valorRevestimentoImpermeabilidade5_1, valorRevestimentoImpermeabilidade6, valorRevestimentoImpermeabilidade6_1, valorRevestimentoImpermeabilidade7, valorRevestimentoImpermeabilidade7_1, valorRevestimentoImpermeabilidade8, valorRevestimentoImpermeabilidade8_1, valorRevestimentoImpermeabilidade9, valorRevestimentoImpermeabilidade9_1, valorRevestimentoImpermeabilidade10, valorRevestimentoImpermeabilidade10_1, valorRevestimentoImpermeabilidade11, valorRevestimentoImpermeabilidade11_1,
            valorRevestimentoPorcelanatoMenor1, valorRevestimentoPorcelanatoMenor1_1, valorRevestimentoPorcelanatoMenor2, valorRevestimentoPorcelanatoMenor2_1, valorRevestimentoPorcelanatoMenor3, valorRevestimentoPorcelanatoMenor3_1, valorRevestimentoPorcelanatoMenor4, valorRevestimentoPorcelanatoMenor4_1, valorRevestimentoPorcelanatoMenor5, valorRevestimentoPorcelanatoMenor5_1, valorRevestimentoPorcelanatoMenor6, valorRevestimentoPorcelanatoMenor6_1, valorRevestimentoPorcelanatoMenor7, valorRevestimentoPorcelanatoMenor7_1, valorRevestimentoPorcelanatoMenor8, valorRevestimentoPorcelanatoMenor8_1, valorRevestimentoPorcelanatoMenor9, valorRevestimentoPorcelanatoMenor9_1, valorRevestimentoPorcelanatoMenor10, valorRevestimentoPorcelanatoMenor10_1, valorRevestimentoPorcelanatoMenor11, valorRevestimentoPorcelanatoMenor11_1,
            valorRevestimentoPorcelanatoAcima1, valorRevestimentoPorcelanatoAcima1_1, valorRevestimentoPorcelanatoAcima2, valorRevestimentoPorcelanatoAcima2_1, valorRevestimentoPorcelanatoAcima3, valorRevestimentoPorcelanatoAcima3_1, valorRevestimentoPorcelanatoAcima4, valorRevestimentoPorcelanatoAcima4_1, valorRevestimentoPorcelanatoAcima5, valorRevestimentoPorcelanatoAcima5_1, valorRevestimentoPorcelanatoAcima6, valorRevestimentoPorcelanatoAcima6_1, valorRevestimentoPorcelanatoAcima7, valorRevestimentoPorcelanatoAcima7_1, valorRevestimentoPorcelanatoAcima8, valorRevestimentoPorcelanatoAcima8_1, valorRevestimentoPorcelanatoAcima9, valorRevestimentoPorcelanatoAcima9_1, valorRevestimentoPorcelanatoAcima10, valorRevestimentoPorcelanatoAcima10_1, valorRevestimentoPorcelanatoAcima11, valorRevestimentoPorcelanatoAcima11_1,
            valorRevestimentoPastilhaVidro1, valorRevestimentoPastilhaVidro1_1, valorRevestimentoPastilhaVidro2, valorRevestimentoPastilhaVidro2_1, valorRevestimentoPastilhaVidro3, valorRevestimentoPastilhaVidro3_1, valorRevestimentoPastilhaVidro4, valorRevestimentoPastilhaVidro4_1, valorRevestimentoPastilhaVidro5, valorRevestimentoPastilhaVidro5_1, valorRevestimentoPastilhaVidro6, valorRevestimentoPastilhaVidro6_1, valorRevestimentoPastilhaVidro7, valorRevestimentoPastilhaVidro7_1, valorRevestimentoPastilhaVidro8, valorRevestimentoPastilhaVidro8_1, valorRevestimentoPastilhaVidro9, valorRevestimentoPastilhaVidro9_1, valorRevestimentoPastilhaVidro10, valorRevestimentoPastilhaVidro10_1, valorRevestimentoPastilhaVidro11, valorRevestimentoPastilhaVidro11_1,
            valorRevestimento3D1, valorRevestimento3D1_1, valorRevestimento3D2, valorRevestimento3D2_1, valorRevestimento3D3, valorRevestimento3D3_1, valorRevestimento3D4, valorRevestimento3D4_1, valorRevestimento3D5, valorRevestimento3D5_1, valorRevestimento3D6, valorRevestimento3D6_1, valorRevestimento3D7, valorRevestimento3D7_1, valorRevestimento3D8, valorRevestimento3D8_1, valorRevestimento3D9, valorRevestimento3D9_1, valorRevestimento3D10, valorRevestimento3D10_1, valorRevestimento3D11, valorRevestimento3D11_1;

    //Hidraulica
    private EditText valorHidraulicaTorneiraEletrica1, valorHidraulicaTorneiraEletrica1_1, valorHidraulicaTorneiraEletrica2, valorHidraulicaTorneiraEletrica2_1, valorHidraulicaTorneiraEletrica3, valorHidraulicaTorneiraEletrica3_1, valorHidraulicaTorneiraEletrica4, valorHidraulicaTorneiraEletrica4_1, valorHidraulicaTorneiraEletrica5, valorHidraulicaTorneiraEletrica5_1, valorHidraulicaTorneiraEletrica6, valorHidraulicaTorneiraEletrica6_1, valorHidraulicaTorneiraEletrica7, valorHidraulicaTorneiraEletrica7_1, valorHidraulicaTorneiraEletrica8, valorHidraulicaTorneiraEletrica8_1, valorHidraulicaTorneiraEletrica9, valorHidraulicaTorneiraEletrica9_1, valorHidraulicaTorneiraEletrica10, valorHidraulicaTorneiraEletrica10_1, valorHidraulicaTorneiraEletrica11, valorHidraulicaTorneiraEletrica11_1,
            valorHidraulicaTorneiraMonocomando1, valorHidraulicaTorneiraMonocomando1_1, valorHidraulicaTorneiraMonocomando2, valorHidraulicaTorneiraMonocomando2_1, valorHidraulicaTorneiraMonocomando3, valorHidraulicaTorneiraMonocomando3_1, valorHidraulicaTorneiraMonocomando4, valorHidraulicaTorneiraMonocomando4_1, valorHidraulicaTorneiraMonocomando5, valorHidraulicaTorneiraMonocomando5_1, valorHidraulicaTorneiraMonocomando6, valorHidraulicaTorneiraMonocomando6_1, valorHidraulicaTorneiraMonocomando7, valorHidraulicaTorneiraMonocomando7_1, valorHidraulicaTorneiraMonocomando8, valorHidraulicaTorneiraMonocomando8_1, valorHidraulicaTorneiraMonocomando9, valorHidraulicaTorneiraMonocomando9_1, valorHidraulicaTorneiraMonocomando10, valorHidraulicaTorneiraMonocomando10_1, valorHidraulicaTorneiraMonocomando11, valorHidraulicaTorneiraMonocomando11_1,
            valorHidraulicaTorneiraSimples1, valorHidraulicaTorneiraSimples1_1, valorHidraulicaTorneiraSimples2, valorHidraulicaTorneiraSimples2_1, valorHidraulicaTorneiraSimples3, valorHidraulicaTorneiraSimples3_1, valorHidraulicaTorneiraSimples4, valorHidraulicaTorneiraSimples4_1, valorHidraulicaTorneiraSimples5, valorHidraulicaTorneiraSimples5_1, valorHidraulicaTorneiraSimples6, valorHidraulicaTorneiraSimples6_1, valorHidraulicaTorneiraSimples7, valorHidraulicaTorneiraSimples7_1, valorHidraulicaTorneiraSimples8, valorHidraulicaTorneiraSimples8_1, valorHidraulicaTorneiraSimples9, valorHidraulicaTorneiraSimples9_1, valorHidraulicaTorneiraSimples10, valorHidraulicaTorneiraSimples10_1, valorHidraulicaTorneiraSimples11, valorHidraulicaTorneiraSimples11_1,
            valorHidraulicaValvulaSifao1, valorHidraulicaValvulaSifao1_1, valorHidraulicaValvulaSifao2, valorHidraulicaValvulaSifao2_1, valorHidraulicaValvulaSifao3, valorHidraulicaValvulaSifao3_1, valorHidraulicaValvulaSifao4, valorHidraulicaValvulaSifao4_1, valorHidraulicaValvulaSifao5, valorHidraulicaValvulaSifao5_1, valorHidraulicaValvulaSifao6, valorHidraulicaValvulaSifao6_1, valorHidraulicaValvulaSifao7, valorHidraulicaValvulaSifao7_1, valorHidraulicaValvulaSifao8, valorHidraulicaValvulaSifao8_1, valorHidraulicaValvulaSifao9, valorHidraulicaValvulaSifao9_1, valorHidraulicaValvulaSifao10, valorHidraulicaValvulaSifao10_1, valorHidraulicaValvulaSifao11, valorHidraulicaValvulaSifao11_1,
            valorHidraulicaRegistroAcabamento1, valorHidraulicaRegistroAcabamento1_1, valorHidraulicaRegistroAcabamento2, valorHidraulicaRegistroAcabamento2_1, valorHidraulicaRegistroAcabamento3, valorHidraulicaRegistroAcabamento3_1, valorHidraulicaRegistroAcabamento4, valorHidraulicaRegistroAcabamento4_1, valorHidraulicaRegistroAcabamento5, valorHidraulicaRegistroAcabamento5_1, valorHidraulicaRegistroAcabamento6, valorHidraulicaRegistroAcabamento6_1, valorHidraulicaRegistroAcabamento7, valorHidraulicaRegistroAcabamento7_1, valorHidraulicaRegistroAcabamento8, valorHidraulicaRegistroAcabamento8_1, valorHidraulicaRegistroAcabamento9, valorHidraulicaRegistroAcabamento9_1, valorHidraulicaRegistroAcabamento10, valorHidraulicaRegistroAcabamento10_1, valorHidraulicaRegistroAcabamento11, valorHidraulicaRegistroAcabamento11_1,
            valorHidraulicaCriacaoAgua1, valorHidraulicaCriacaoAgua1_1, valorHidraulicaCriacaoAgua2, valorHidraulicaCriacaoAgua2_1, valorHidraulicaCriacaoAgua3, valorHidraulicaCriacaoAgua3_1, valorHidraulicaCriacaoAgua4, valorHidraulicaCriacaoAgua4_1, valorHidraulicaCriacaoAgua5, valorHidraulicaCriacaoAgua5_1, valorHidraulicaCriacaoAgua6, valorHidraulicaCriacaoAgua6_1, valorHidraulicaCriacaoAgua7, valorHidraulicaCriacaoAgua7_1, valorHidraulicaCriacaoAgua8, valorHidraulicaCriacaoAgua8_1, valorHidraulicaCriacaoAgua9, valorHidraulicaCriacaoAgua9_1, valorHidraulicaCriacaoAgua10, valorHidraulicaCriacaoAgua10_1, valorHidraulicaCriacaoAgua11, valorHidraulicaCriacaoAgua11_1,
            valorHidraulicaCriacaoEsgoto1, valorHidraulicaCriacaoEsgoto1_1, valorHidraulicaCriacaoEsgoto2, valorHidraulicaCriacaoEsgoto2_1, valorHidraulicaCriacaoEsgoto3, valorHidraulicaCriacaoEsgoto3_1, valorHidraulicaCriacaoEsgoto4, valorHidraulicaCriacaoEsgoto4_1, valorHidraulicaCriacaoEsgoto5, valorHidraulicaCriacaoEsgoto5_1, valorHidraulicaCriacaoEsgoto6, valorHidraulicaCriacaoEsgoto6_1, valorHidraulicaCriacaoEsgoto7, valorHidraulicaCriacaoEsgoto7_1, valorHidraulicaCriacaoEsgoto8, valorHidraulicaCriacaoEsgoto8_1, valorHidraulicaCriacaoEsgoto9, valorHidraulicaCriacaoEsgoto9_1, valorHidraulicaCriacaoEsgoto10, valorHidraulicaCriacaoEsgoto10_1, valorHidraulicaCriacaoEsgoto11, valorHidraulicaCriacaoEsgoto11_1,
            valorHidraulicaRalo10cm1, valorHidraulicaRalo10cm1_1, valorHidraulicaRalo10cm2, valorHidraulicaRalo10cm2_1, valorHidraulicaRalo10cm3, valorHidraulicaRalo10cm3_1, valorHidraulicaRalo10cm4, valorHidraulicaRalo10cm4_1, valorHidraulicaRalo10cm5, valorHidraulicaRalo10cm5_1, valorHidraulicaRalo10cm6, valorHidraulicaRalo10cm6_1, valorHidraulicaRalo10cm7, valorHidraulicaRalo10cm7_1, valorHidraulicaRalo10cm8, valorHidraulicaRalo10cm8_1, valorHidraulicaRalo10cm9, valorHidraulicaRalo10cm9_1, valorHidraulicaRalo10cm10, valorHidraulicaRalo10cm10_1, valorHidraulicaRalo10cm11, valorHidraulicaRalo10cm11_1,
            valorHidraulicaRalo15cm1, valorHidraulicaRalo15cm1_1, valorHidraulicaRalo15cm2, valorHidraulicaRalo15cm2_1, valorHidraulicaRalo15cm3, valorHidraulicaRalo15cm3_1, valorHidraulicaRalo15cm4, valorHidraulicaRalo15cm4_1, valorHidraulicaRalo15cm5, valorHidraulicaRalo15cm5_1, valorHidraulicaRalo15cm6, valorHidraulicaRalo15cm6_1, valorHidraulicaRalo15cm7, valorHidraulicaRalo15cm7_1, valorHidraulicaRalo15cm8, valorHidraulicaRalo15cm8_1, valorHidraulicaRalo15cm9, valorHidraulicaRalo15cm9_1, valorHidraulicaRalo15cm10, valorHidraulicaRalo15cm10_1, valorHidraulicaRalo15cm11, valorHidraulicaRalo15cm11_1,
            valorHidraulicaChuveiro1, valorHidraulicaChuveiro1_1, valorHidraulicaChuveiro2, valorHidraulicaChuveiro2_1, valorHidraulicaChuveiro3, valorHidraulicaChuveiro3_1, valorHidraulicaChuveiro4, valorHidraulicaChuveiro4_1, valorHidraulicaChuveiro5, valorHidraulicaChuveiro5_1, valorHidraulicaChuveiro6, valorHidraulicaChuveiro6_1, valorHidraulicaChuveiro7, valorHidraulicaChuveiro7_1, valorHidraulicaChuveiro8, valorHidraulicaChuveiro8_1, valorHidraulicaChuveiro9, valorHidraulicaChuveiro9_1, valorHidraulicaChuveiro10, valorHidraulicaChuveiro10_1, valorHidraulicaChuveiro11, valorHidraulicaChuveiro11_1,
            valorHidraulicaInstalarVasoSanitario1, valorHidraulicaInstalarVasoSanitario1_1, valorHidraulicaInstalarVasoSanitario2, valorHidraulicaInstalarVasoSanitario2_1, valorHidraulicaInstalarVasoSanitario3, valorHidraulicaInstalarVasoSanitario3_1, valorHidraulicaInstalarVasoSanitario4, valorHidraulicaInstalarVasoSanitario4_1, valorHidraulicaInstalarVasoSanitario5, valorHidraulicaInstalarVasoSanitario5_1, valorHidraulicaInstalarVasoSanitario6, valorHidraulicaInstalarVasoSanitario6_1, valorHidraulicaInstalarVasoSanitario7, valorHidraulicaInstalarVasoSanitario7_1, valorHidraulicaInstalarVasoSanitario8, valorHidraulicaInstalarVasoSanitario8_1, valorHidraulicaInstalarVasoSanitario9, valorHidraulicaInstalarVasoSanitario9_1, valorHidraulicaInstalarVasoSanitario10, valorHidraulicaInstalarVasoSanitario10_1, valorHidraulicaInstalarVasoSanitario11, valorHidraulicaInstalarVasoSanitario11_1,
            valorHidraulicaRaloLinear1, valorHidraulicaRaloLinear1_1, valorHidraulicaRaloLinear2, valorHidraulicaRaloLinear2_1, valorHidraulicaRaloLinear3, valorHidraulicaRaloLinear3_1, valorHidraulicaRaloLinear4, valorHidraulicaRaloLinear4_1, valorHidraulicaRaloLinear5, valorHidraulicaRaloLinear5_1, valorHidraulicaRaloLinear6, valorHidraulicaRaloLinear6_1, valorHidraulicaRaloLinear7, valorHidraulicaRaloLinear7_1, valorHidraulicaRaloLinear8, valorHidraulicaRaloLinear8_1, valorHidraulicaRaloLinear9, valorHidraulicaRaloLinear9_1, valorHidraulicaRaloLinear10, valorHidraulicaRaloLinear10_1, valorHidraulicaRaloLinear11, valorHidraulicaRaloLinear11_1;


    //Pintura
    private EditText valorPinturaPorta1, valorPinturaPorta1_1, valorPinturaPorta2, valorPinturaPorta2_1, valorPinturaPorta3, valorPinturaPorta3_1, valorPinturaPorta4, valorPinturaPorta4_1, valorPinturaPorta5, valorPinturaPorta5_1, valorPinturaPorta6, valorPinturaPorta6_1, valorPinturaPorta7, valorPinturaPorta7_1, valorPinturaPorta8, valorPinturaPorta8_1, valorPinturaPorta9, valorPinturaPorta9_1, valorPinturaPorta10, valorPinturaPorta10_1, valorPinturaPorta11, valorPinturaPorta11_1,
            valorPinturaJanela1, valorPinturaJanela1_1, valorPinturaJanela2, valorPinturaJanela2_1, valorPinturaJanela3, valorPinturaJanela3_1, valorPinturaJanela4, valorPinturaJanela4_1, valorPinturaJanela5, valorPinturaJanela5_1, valorPinturaJanela6, valorPinturaJanela6_1, valorPinturaJanela7, valorPinturaJanela7_1, valorPinturaJanela8, valorPinturaJanela8_1, valorPinturaJanela9, valorPinturaJanela9_1, valorPinturaJanela10, valorPinturaJanela10_1, valorPinturaJanela11, valorPinturaJanela11_1,
            valorPinturaEfeitoDecorativo1, valorPinturaEfeitoDecorativo1_1, valorPinturaEfeitoDecorativo2, valorPinturaEfeitoDecorativo2_1, valorPinturaEfeitoDecorativo3, valorPinturaEfeitoDecorativo3_1, valorPinturaEfeitoDecorativo4, valorPinturaEfeitoDecorativo4_1, valorPinturaEfeitoDecorativo5, valorPinturaEfeitoDecorativo5_1, valorPinturaEfeitoDecorativo6, valorPinturaEfeitoDecorativo6_1, valorPinturaEfeitoDecorativo7, valorPinturaEfeitoDecorativo7_1, valorPinturaEfeitoDecorativo8, valorPinturaEfeitoDecorativo8_1, valorPinturaEfeitoDecorativo9, valorPinturaEfeitoDecorativo9_1, valorPinturaEfeitoDecorativo10, valorPinturaEfeitoDecorativo10_1, valorPinturaEfeitoDecorativo11, valorPinturaEfeitoDecorativo11_1,
            valorPinturaReparoGesso1, valorPinturaReparoGesso1_1, valorPinturaReparoGesso2, valorPinturaReparoGesso2_1, valorPinturaReparoGesso3, valorPinturaReparoGesso3_1, valorPinturaReparoGesso4, valorPinturaReparoGesso4_1, valorPinturaReparoGesso5, valorPinturaReparoGesso5_1, valorPinturaReparoGesso6, valorPinturaReparoGesso6_1, valorPinturaReparoGesso7, valorPinturaReparoGesso7_1, valorPinturaReparoGesso8, valorPinturaReparoGesso8_1, valorPinturaReparoGesso9, valorPinturaReparoGesso9_1, valorPinturaReparoGesso10, valorPinturaReparoGesso10_1, valorPinturaReparoGesso11, valorPinturaReparoGesso11_1;
    private Button btn_finish;
    PermissionsChecker checker;
    Context mContext;
    // double varRemoverRevestimentoParede2,varRemoverRevestimentoParede3,varRemoverRevestimentoParede4,varRemoverRevestimentoParede5,varRemoverRevestimentoParede6,varRemoverRevestimentoParede7,varRemoverRevestimentoParede8,varRemoverRevestimentoParede9,varRemoverRevestimentoParede10,varRemoverRevestimentoParede11;


    //Valores dos serviços de Demolicao

    double precoRemoverRevestimentoParede = 30.00;
    double precoRemoverPiso = 30.00;
    double precoRemoverAlvenaria = 60.00;
    double precoRemoverPia = 72.00;
    double precoRemoverTanque = 52.00;
    double precoRasgarCaixinha4x2 = 50.40;
    double precoRasgarCaixinha4x4 = 54.40;
    double precoRemoverVasoSanitario = 68.00;
    double precoRemoverVao = 120.00;
    double precoRemoverHidraulica = 54.40;
    double precoRemoverGesso = 30.00;
    public int numeroNotaAtual;
    public float valorNotaAtual;
    int numNota;
    String valorNota;


    private static int TAKE_PICTURE = 1;

    private TextView exibirNota, exibirValorNota;

    //Valores dos serviços de Revestimento
    double precoCriarAlvenaria = 72.00;
    double precoCriarContraPiso = 42.00;
    double precoAplicarImpermeabilizante = 22.00;
    double precoPorcelanatoMenor = 82.00;
    double precoPorcelanatoMaior = 102.00;
    double precoPastilhaVidro = 230.00;
    double precoRevestimento3D = 82.00;
    double precoTotalRevestimento = 0;

    //Valores dos serviços de Hidraulica
    double precoTorneiraEletrica = 90.00;
    double precoTorneiraMonocomando = 90.00;
    double precoTorneiraSimples = 65.00;
    double precoValvulaSifao = 50.00;
    double precoRegistroAcabamento = 15.00;
    double precoCriacaoAgua = 95.00;
    double precoCriacaoEsgoto = 70.00;
    double precoRalo10cm = 12.00;
    double precoRalo15cm = 12.00;
    double precoChuveiro = 90.00;
    double precoRaloLinear = 95.00;
    double precoInstalarVasoSanitario = 95.00;

    //Valores dos serviços de Pintura
    double precoPinturaPorta = 150.00;
    double precoPinturaJanela = 150.00;
    double precoPinturaEfeitoDecorativo = 90.00;
    double precoPinturaReparoGesso = 80.00;


    //Variaveis Demolicao
    private double varRemoverRevestimentoParede = 0;
    private double varRemoverRevestimentoParede1 = 0;
    private double varRemoverRevestimentoParede2 = 0;
    private double varRemoverRevestimentoParede2_1 = 0;
    private double varRemoverRevestimentoParede3 = 0;
    private double varRemoverRevestimentoParede3_1 = 0;
    private double varRemoverRevestimentoParede4 = 0;
    private double varRemoverRevestimentoParede4_1 = 0;
    private double varRemoverRevestimentoParede5 = 0;
    private double varRemoverRevestimentoParede5_1 = 0;
    private double varRemoverRevestimentoParede6 = 0;
    private double varRemoverRevestimentoParede6_1 = 0;
    private double varRemoverRevestimentoParede7 = 0;
    private double varRemoverRevestimentoParede7_1 = 0;
    private double varRemoverRevestimentoParede8 = 0;
    private double varRemoverRevestimentoParede8_1 = 0;
    private double varRemoverRevestimentoParede9 = 0;
    private double varRemoverRevestimentoParede9_1 = 0;
    private double varRemoverRevestimentoParede10 = 0;
    private double varRemoverRevestimentoParede10_1 = 0;
    private double varRemoverRevestimentoParede11 = 0;
    private double varRemoverRevestimentoParede11_1 = 0;
    private double varRemoverPiso = 0;
    private double varRemoverPiso1 = 0;
    private double varRemoverPiso2 = 0;
    private double varRemoverPiso2_1 = 0;
    private double varRemoverPiso3 = 0;
    private double varRemoverPiso3_1 = 0;
    private double varRemoverPiso4 = 0;
    private double varRemoverPiso4_1 = 0;
    private double varRemoverPiso5 = 0;
    private double varRemoverPiso5_1 = 0;
    private double varRemoverPiso6 = 0;
    private double varRemoverPiso6_1 = 0;
    private double varRemoverPiso7 = 0;
    private double varRemoverPiso7_1 = 0;
    private double varRemoverPiso8 = 0;
    private double varRemoverPiso8_1 = 0;
    private double varRemoverPiso9 = 0;
    private double varRemoverPiso9_1 = 0;
    private double varRemoverPiso10 = 0;
    private double varRemoverPiso10_1 = 0;
    private double varRemoverPiso11 = 0;
    private double varRemoverPiso11_1 = 0;
    private double varRemoverPia = 0;
    private double varRemoverPia1 = 0;
    private double varRemoverPia2 = 0;
    private double varRemoverPia2_1 = 0;
    private double varRemoverPia3 = 0;
    private double varRemoverPia3_1 = 0;
    private double varRemoverPia4 = 0;
    private double varRemoverPia4_1 = 0;
    private double varRemoverPia5 = 0;
    private double varRemoverPia5_1 = 0;
    private double varRemoverPia6 = 0;
    private double varRemoverPia6_1 = 0;
    private double varRemoverPia7 = 0;
    private double varRemoverPia7_1 = 0;
    private double varRemoverPia8 = 0;
    private double varRemoverPia8_1 = 0;
    private double varRemoverPia9 = 0;
    private double varRemoverPia9_1 = 0;
    private double varRemoverPia10 = 0;
    private double varRemoverPia10_1 = 0;
    private double varRemoverPia11 = 0;
    private double varRemoverPia11_1 = 0;
    private double varRemoverAlvenaria = 0;
    private double varRemoverAlvenaria1 = 0;
    private double varRemoverAlvenaria2 = 0;
    private double varRemoverAlvenaria2_1 = 0;
    private double varRemoverAlvenaria3 = 0;
    private double varRemoverAlvenaria3_1 = 0;
    private double varRemoverAlvenaria4 = 0;
    private double varRemoverAlvenaria4_1 = 0;
    private double varRemoverAlvenaria5 = 0;
    private double varRemoverAlvenaria5_1 = 0;
    private double varRemoverAlvenaria6 = 0;
    private double varRemoverAlvenaria6_1 = 0;
    private double varRemoverAlvenaria7 = 0;
    private double varRemoverAlvenaria7_1 = 0;
    private double varRemoverAlvenaria8 = 0;
    private double varRemoverAlvenaria8_1 = 0;
    private double varRemoverAlvenaria9 = 0;
    private double varRemoverAlvenaria9_1 = 0;
    private double varRemoverAlvenaria10 = 0;
    private double varRemoverAlvenaria10_1 = 0;
    private double varRemoverAlvenaria11 = 0;
    private double varRemoverAlvenaria11_1 = 0;
    private double varRemoverTanque = 0;
    private double varRemoverTanque1 = 0;
    private double varRemoverTanque2 = 0;
    private double varRemoverTanque2_1 = 0;
    private double varRemoverTanque3 = 0;
    private double varRemoverTanque3_1 = 0;
    private double varRemoverTanque4 = 0;
    private double varRemoverTanque4_1 = 0;
    private double varRemoverTanque5 = 0;
    private double varRemoverTanque5_1 = 0;
    private double varRemoverTanque6 = 0;
    private double varRemoverTanque6_1 = 0;
    private double varRemoverTanque7 = 0;
    private double varRemoverTanque7_1 = 0;
    private double varRemoverTanque8 = 0;
    private double varRemoverTanque8_1 = 0;
    private double varRemoverTanque9 = 0;
    private double varRemoverTanque9_1 = 0;
    private double varRemoverTanque10 = 0;
    private double varRemoverTanque10_1 = 0;
    private double varRemoverTanque11 = 0;
    private double varRemoverTanque11_1 = 0;
    private double varRemoverCaixinha4x2 = 0;
    private double varRemoverCaixinha4x2_1 = 0;
    private double varRemoverCaixinha4x2_2 = 0;
    private double varRemoverCaixinha4x2_2_1 = 0;
    private double varRemoverCaixinha4x2_3 = 0;
    private double varRemoverCaixinha4x2_3_1 = 0;
    private double varRemoverCaixinha4x2_4 = 0;
    private double varRemoverCaixinha4x2_4_1 = 0;
    private double varRemoverCaixinha4x2_5 = 0;
    private double varRemoverCaixinha4x2_5_1 = 0;
    private double varRemoverCaixinha4x2_6 = 0;
    private double varRemoverCaixinha4x2_6_1 = 0;
    private double varRemoverCaixinha4x2_7 = 0;
    private double varRemoverCaixinha4x2_7_1 = 0;
    private double varRemoverCaixinha4x2_8 = 0;
    private double varRemoverCaixinha4x2_8_1 = 0;
    private double varRemoverCaixinha4x2_9 = 0;
    private double varRemoverCaixinha4x2_9_1 = 0;
    private double varRemoverCaixinha4x2_10 = 0;
    private double varRemoverCaixinha4x2_10_1 = 0;
    private double varRemoverCaixinha4x2_11 = 0;
    private double varRemoverCaixinha4x2_11_1 = 0;
    private double varRemoverCaixinha4x4 = 0;
    private double varRemoverCaixinha4x4_1 = 0;
    private double varRemoverCaixinha4x4_2 = 0;
    private double varRemoverCaixinha4x4_2_1 = 0;
    private double varRemoverCaixinha4x4_3 = 0;
    private double varRemoverCaixinha4x4_3_1 = 0;
    private double varRemoverCaixinha4x4_4 = 0;
    private double varRemoverCaixinha4x4_4_1 = 0;
    private double varRemoverCaixinha4x4_5 = 0;
    private double varRemoverCaixinha4x4_5_1 = 0;
    private double varRemoverCaixinha4x4_6 = 0;
    private double varRemoverCaixinha4x4_6_1 = 0;
    private double varRemoverCaixinha4x4_7 = 0;
    private double varRemoverCaixinha4x4_7_1 = 0;
    private double varRemoverCaixinha4x4_8 = 0;
    private double varRemoverCaixinha4x4_8_1 = 0;
    private double varRemoverCaixinha4x4_9 = 0;
    private double varRemoverCaixinha4x4_9_1 = 0;
    private double varRemoverCaixinha4x4_10 = 0;
    private double varRemoverCaixinha4x4_10_1 = 0;
    private double varRemoverCaixinha4x4_11 = 0;
    private double varRemoverCaixinha4x4_11_1 = 0;
    private double varRemoverHidraulica = 0;
    private double varRemoverHidraulica1 = 0;
    private double varRemoverHidraulica2 = 0;
    private double varRemoverHidraulica2_1 = 0;
    private double varRemoverHidraulica3 = 0;
    private double varRemoverHidraulica3_1 = 0;
    private double varRemoverHidraulica4 = 0;
    private double varRemoverHidraulica4_1 = 0;
    private double varRemoverHidraulica5 = 0;
    private double varRemoverHidraulica5_1 = 0;
    private double varRemoverHidraulica6 = 0;
    private double varRemoverHidraulica6_1 = 0;
    private double varRemoverHidraulica7 = 0;
    private double varRemoverHidraulica7_1 = 0;
    private double varRemoverHidraulica8 = 0;
    private double varRemoverHidraulica8_1 = 0;
    private double varRemoverHidraulica9 = 0;
    private double varRemoverHidraulica9_1 = 0;
    private double varRemoverHidraulica10 = 0;
    private double varRemoverHidraulica10_1 = 0;
    private double varRemoverHidraulica11 = 0;
    private double varRemoverHidraulica11_1 = 0;
    private double varRemoverGesso = 0;
    private double varRemoverGesso1 = 0;
    private double varRemoverGesso2 = 0;
    private double varRemoverGesso2_1 = 0;
    private double varRemoverGesso3 = 0;
    private double varRemoverGesso3_1 = 0;
    private double varRemoverGesso4 = 0;
    private double varRemoverGesso4_1 = 0;
    private double varRemoverGesso5 = 0;
    private double varRemoverGesso5_1 = 0;
    private double varRemoverGesso6 = 0;
    private double varRemoverGesso6_1 = 0;
    private double varRemoverGesso7 = 0;
    private double varRemoverGesso7_1 = 0;
    private double varRemoverGesso8 = 0;
    private double varRemoverGesso8_1 = 0;
    private double varRemoverGesso9 = 0;
    private double varRemoverGesso9_1 = 0;
    private double varRemoverGesso10 = 0;
    private double varRemoverGesso10_1 = 0;
    private double varRemoverGesso11 = 0;
    private double varRemoverGesso11_1 = 0;
    private double varRemoverVasoSanitario = 0;
    private double varRemoverVasoSanitario1 = 0;
    private double varRemoverVasoSanitario2 = 0;
    private double varRemoverVasoSanitario2_1 = 0;
    private double varRemoverVasoSanitario3 = 0;
    private double varRemoverVasoSanitario3_1 = 0;
    private double varRemoverVasoSanitario4 = 0;
    private double varRemoverVasoSanitario4_1 = 0;
    private double varRemoverVasoSanitario5 = 0;
    private double varRemoverVasoSanitario5_1 = 0;
    private double varRemoverVasoSanitario6 = 0;
    private double varRemoverVasoSanitario6_1 = 0;
    private double varRemoverVasoSanitario7 = 0;
    private double varRemoverVasoSanitario7_1 = 0;
    private double varRemoverVasoSanitario8 = 0;
    private double varRemoverVasoSanitario8_1 = 0;
    private double varRemoverVasoSanitario9 = 0;
    private double varRemoverVasoSanitario9_1 = 0;
    private double varRemoverVasoSanitario10 = 0;
    private double varRemoverVasoSanitario10_1 = 0;
    private double varRemoverVasoSanitario11 = 0;
    private double varRemoverVasoSanitario11_1 = 0;
    private double varRemoverVao = 0;
    private double varRemoverVao1 = 0;
    private double varRemoverVao2 = 0;
    private double varRemoverVao2_1 = 0;
    private double varRemoverVao3 = 0;
    private double varRemoverVao3_1 = 0;
    private double varRemoverVao4 = 0;
    private double varRemoverVao4_1 = 0;
    private double varRemoverVao5 = 0;
    private double varRemoverVao5_1 = 0;
    private double varRemoverVao6 = 0;
    private double varRemoverVao6_1 = 0;
    private double varRemoverVao7 = 0;
    private double varRemoverVao7_1 = 0;
    private double varRemoverVao8 = 0;
    private double varRemoverVao8_1 = 0;
    private double varRemoverVao9 = 0;
    private double varRemoverVao9_1 = 0;
    private double varRemoverVao10 = 0;
    private double varRemoverVao10_1 = 0;
    private double varRemoverVao11 = 0;
    private double varRemoverVao11_1 = 0;

    //Variaveis Hidraulica
    private double varAdicionarTorneiraEletrica = 0;
    private double varAdicionarTorneiraEletrica1 = 0;
    private double varAdicionarTorneiraEletrica2 = 0;
    private double varAdicionarTorneiraEletrica2_1 = 0;
    private double varAdicionarTorneiraEletrica3 = 0;
    private double varAdicionarTorneiraEletrica3_1 = 0;
    private double varAdicionarTorneiraEletrica4 = 0;
    private double varAdicionarTorneiraEletrica4_1 = 0;
    private double varAdicionarTorneiraEletrica5 = 0;
    private double varAdicionarTorneiraEletrica5_1 = 0;
    private double varAdicionarTorneiraEletrica6 = 0;
    private double varAdicionarTorneiraEletrica6_1 = 0;
    private double varAdicionarTorneiraEletrica7 = 0;
    private double varAdicionarTorneiraEletrica7_1 = 0;
    private double varAdicionarTorneiraEletrica8 = 0;
    private double varAdicionarTorneiraEletrica8_1 = 0;
    private double varAdicionarTorneiraEletrica9 = 0;
    private double varAdicionarTorneiraEletrica9_1 = 0;
    private double varAdicionarTorneiraEletrica10 = 0;
    private double varAdicionarTorneiraEletrica10_1 = 0;
    private double varAdicionarTorneiraEletrica11 = 0;
    private double varAdicionarTorneiraEletrica11_1 = 0;
    private double varAdicionarTorneiraMonocomando = 0;
    private double varAdicionarTorneiraMonocomando1 = 0;
    private double varAdicionarTorneiraMonocomando2 = 0;
    private double varAdicionarTorneiraMonocomando2_1 = 0;
    private double varAdicionarTorneiraMonocomando3 = 0;
    private double varAdicionarTorneiraMonocomando3_1 = 0;
    private double varAdicionarTorneiraMonocomando4 = 0;
    private double varAdicionarTorneiraMonocomando4_1 = 0;
    private double varAdicionarTorneiraMonocomando5 = 0;
    private double varAdicionarTorneiraMonocomando5_1 = 0;
    private double varAdicionarTorneiraMonocomando6 = 0;
    private double varAdicionarTorneiraMonocomando6_1 = 0;
    private double varAdicionarTorneiraMonocomando7 = 0;
    private double varAdicionarTorneiraMonocomando7_1 = 0;
    private double varAdicionarTorneiraMonocomando8 = 0;
    private double varAdicionarTorneiraMonocomando8_1 = 0;
    private double varAdicionarTorneiraMonocomando9 = 0;
    private double varAdicionarTorneiraMonocomando9_1 = 0;
    private double varAdicionarTorneiraMonocomando10 = 0;
    private double varAdicionarTorneiraMonocomando10_1 = 0;
    private double varAdicionarTorneiraMonocomando11 = 0;
    private double varAdicionarTorneiraMonocomando11_1 = 0;
    private double varAdicionarTorneiraSimples = 0;
    private double varAdicionarTorneiraSimples1 = 0;
    private double varAdicionarTorneiraSimples2 = 0;
    private double varAdicionarTorneiraSimples2_1 = 0;
    private double varAdicionarTorneiraSimples3 = 0;
    private double varAdicionarTorneiraSimples3_1 = 0;
    private double varAdicionarTorneiraSimples4 = 0;
    private double varAdicionarTorneiraSimples4_1 = 0;
    private double varAdicionarTorneiraSimples5 = 0;
    private double varAdicionarTorneiraSimples5_1 = 0;
    private double varAdicionarTorneiraSimples6 = 0;
    private double varAdicionarTorneiraSimples6_1 = 0;
    private double varAdicionarTorneiraSimples7 = 0;
    private double varAdicionarTorneiraSimples7_1 = 0;
    private double varAdicionarTorneiraSimples8 = 0;
    private double varAdicionarTorneiraSimples8_1 = 0;
    private double varAdicionarTorneiraSimples9 = 0;
    private double varAdicionarTorneiraSimples9_1 = 0;
    private double varAdicionarTorneiraSimples10 = 0;
    private double varAdicionarTorneiraSimples10_1 = 0;
    private double varAdicionarTorneiraSimples11 = 0;
    private double varAdicionarTorneiraSimples11_1 = 0;
    private double varAdicionarValvulaSifao = 0;
    private double varAdicionarValvulaSifao1 = 0;
    private double varAdicionarValvulaSifao2 = 0;
    private double varAdicionarValvulaSifao2_1 = 0;
    private double varAdicionarValvulaSifao3 = 0;
    private double varAdicionarValvulaSifao3_1 = 0;
    private double varAdicionarValvulaSifao4 = 0;
    private double varAdicionarValvulaSifao4_1 = 0;
    private double varAdicionarValvulaSifao5 = 0;
    private double varAdicionarValvulaSifao5_1 = 0;
    private double varAdicionarValvulaSifao6 = 0;
    private double varAdicionarValvulaSifao6_1 = 0;
    private double varAdicionarValvulaSifao7 = 0;
    private double varAdicionarValvulaSifao7_1 = 0;
    private double varAdicionarValvulaSifao8 = 0;
    private double varAdicionarValvulaSifao8_1 = 0;
    private double varAdicionarValvulaSifao9 = 0;
    private double varAdicionarValvulaSifao9_1 = 0;
    private double varAdicionarValvulaSifao10 = 0;
    private double varAdicionarValvulaSifao10_1 = 0;
    private double varAdicionarValvulaSifao11 = 0;
    private double varAdicionarValvulaSifao11_1 = 0;
    private double varAdicionarRegistroAcabamento = 0;
    private double varAdicionarRegistroAcabamento1 = 0;
    private double varAdicionarRegistroAcabamento2 = 0;
    private double varAdicionarRegistroAcabamento2_1 = 0;
    private double varAdicionarRegistroAcabamento3 = 0;
    private double varAdicionarRegistroAcabamento3_1 = 0;
    private double varAdicionarRegistroAcabamento4 = 0;
    private double varAdicionarRegistroAcabamento4_1 = 0;
    private double varAdicionarRegistroAcabamento5 = 0;
    private double varAdicionarRegistroAcabamento5_1 = 0;
    private double varAdicionarRegistroAcabamento6 = 0;
    private double varAdicionarRegistroAcabamento6_1 = 0;
    private double varAdicionarRegistroAcabamento7 = 0;
    private double varAdicionarRegistroAcabamento7_1 = 0;
    private double varAdicionarRegistroAcabamento8 = 0;
    private double varAdicionarRegistroAcabamento8_1 = 0;
    private double varAdicionarRegistroAcabamento9 = 0;
    private double varAdicionarRegistroAcabamento9_1 = 0;
    private double varAdicionarRegistroAcabamento10 = 0;
    private double varAdicionarRegistroAcabamento10_1 = 0;
    private double varAdicionarRegistroAcabamento11 = 0;
    private double varAdicionarRegistroAcabamento11_1 = 0;
    private double varAdicionarPontoAgua = 0;
    private double varAdicionarPontoAgua_1 = 0;
    private double varAdicionarPontoAgua_2 = 0;
    private double varAdicionarPontoAgua_2_1 = 0;
    private double varAdicionarPontoAgua_3 = 0;
    private double varAdicionarPontoAgua_3_1 = 0;
    private double varAdicionarPontoAgua_4 = 0;
    private double varAdicionarPontoAgua_4_1 = 0;
    private double varAdicionarPontoAgua_5 = 0;
    private double varAdicionarPontoAgua_5_1 = 0;
    private double varAdicionarPontoAgua_6 = 0;
    private double varAdicionarPontoAgua_6_1 = 0;
    private double varAdicionarPontoAgua_7 = 0;
    private double varAdicionarPontoAgua_7_1 = 0;
    private double varAdicionarPontoAgua_8 = 0;
    private double varAdicionarPontoAgua_8_1 = 0;
    private double varAdicionarPontoAgua_9 = 0;
    private double varAdicionarPontoAgua_9_1 = 0;
    private double varAdicionarPontoAgua_10 = 0;
    private double varAdicionarPontoAgua_10_1 = 0;
    private double varAdicionarPontoAgua_11 = 0;
    private double varAdicionarPontoAgua_11_1 = 0;
    private double varAdicionarPontoEsgoto = 0;
    private double varAdicionarPontoEsgoto_1 = 0;
    private double varAdicionarPontoEsgoto_2 = 0;
    private double varAdicionarPontoEsgoto_2_1 = 0;
    private double varAdicionarPontoEsgoto_3 = 0;
    private double varAdicionarPontoEsgoto_3_1 = 0;
    private double varAdicionarPontoEsgoto_4 = 0;
    private double varAdicionarPontoEsgoto_4_1 = 0;
    private double varAdicionarPontoEsgoto_5 = 0;
    private double varAdicionarPontoEsgoto_5_1 = 0;
    private double varAdicionarPontoEsgoto_6 = 0;
    private double varAdicionarPontoEsgoto_6_1 = 0;
    private double varAdicionarPontoEsgoto_7 = 0;
    private double varAdicionarPontoEsgoto_7_1 = 0;
    private double varAdicionarPontoEsgoto_8 = 0;
    private double varAdicionarPontoEsgoto_8_1 = 0;
    private double varAdicionarPontoEsgoto_9 = 0;
    private double varAdicionarPontoEsgoto_9_1 = 0;
    private double varAdicionarPontoEsgoto_10 = 0;
    private double varAdicionarPontoEsgoto_10_1 = 0;
    private double varAdicionarPontoEsgoto_11 = 0;
    private double varAdicionarPontoEsgoto_11_1 = 0;
    private double varAdicionarRalo10cm = 0;
    private double varAdicionarRalo10cm1 = 0;
    private double varAdicionarRalo10cm2 = 0;
    private double varAdicionarRalo10cm2_1 = 0;
    private double varAdicionarRalo10cm3 = 0;
    private double varAdicionarRalo10cm3_1 = 0;
    private double varAdicionarRalo10cm4 = 0;
    private double varAdicionarRalo10cm4_1 = 0;
    private double varAdicionarRalo10cm5 = 0;
    private double varAdicionarRalo10cm5_1 = 0;
    private double varAdicionarRalo10cm6 = 0;
    private double varAdicionarRalo10cm6_1 = 0;
    private double varAdicionarRalo10cm7 = 0;
    private double varAdicionarRalo10cm7_1 = 0;
    private double varAdicionarRalo10cm8 = 0;
    private double varAdicionarRalo10cm8_1 = 0;
    private double varAdicionarRalo10cm9 = 0;
    private double varAdicionarRalo10cm9_1 = 0;
    private double varAdicionarRalo10cm10 = 0;
    private double varAdicionarRalo10cm10_1 = 0;
    private double varAdicionarRalo10cm11 = 0;
    private double varAdicionarRalo10cm11_1 = 0;
    private double varAdicionarRalo15cm = 0;
    private double varAdicionarRalo15cm1 = 0;
    private double varAdicionarRalo15cm2 = 0;
    private double varAdicionarRalo15cm2_1 = 0;
    private double varAdicionarRalo15cm3 = 0;
    private double varAdicionarRalo15cm3_1 = 0;
    private double varAdicionarRalo15cm4 = 0;
    private double varAdicionarRalo15cm4_1 = 0;
    private double varAdicionarRalo15cm5 = 0;
    private double varAdicionarRalo15cm5_1 = 0;
    private double varAdicionarRalo15cm6 = 0;
    private double varAdicionarRalo15cm6_1 = 0;
    private double varAdicionarRalo15cm7 = 0;
    private double varAdicionarRalo15cm7_1 = 0;
    private double varAdicionarRalo15cm8 = 0;
    private double varAdicionarRalo15cm8_1 = 0;
    private double varAdicionarRalo15cm9 = 0;
    private double varAdicionarRalo15cm9_1 = 0;
    private double varAdicionarRalo15cm10 = 0;
    private double varAdicionarRalo15cm10_1 = 0;
    private double varAdicionarRalo15cm11 = 0;
    private double varAdicionarRalo15cm11_1 = 0;
    private double varAdicionarChuveiro = 0;
    private double varAdicionarChuveiro1 = 0;
    private double varAdicionarChuveiro2 = 0;
    private double varAdicionarChuveiro2_1 = 0;
    private double varAdicionarChuveiro3 = 0;
    private double varAdicionarChuveiro3_1 = 0;
    private double varAdicionarChuveiro4 = 0;
    private double varAdicionarChuveiro4_1 = 0;
    private double varAdicionarChuveiro5 = 0;
    private double varAdicionarChuveiro5_1 = 0;
    private double varAdicionarChuveiro6 = 0;
    private double varAdicionarChuveiro6_1 = 0;
    private double varAdicionarChuveiro7 = 0;
    private double varAdicionarChuveiro7_1 = 0;
    private double varAdicionarChuveiro8 = 0;
    private double varAdicionarChuveiro8_1 = 0;
    private double varAdicionarChuveiro9 = 0;
    private double varAdicionarChuveiro9_1 = 0;
    private double varAdicionarChuveiro10 = 0;
    private double varAdicionarChuveiro10_1 = 0;
    private double varAdicionarChuveiro11 = 0;
    private double varAdicionarChuveiro11_1 = 0;
    private double varAdicionarRaloLinear = 0;
    private double varAdicionarRaloLinear1 = 0;
    private double varAdicionarRaloLinear2 = 0;
    private double varAdicionarRaloLinear2_1 = 0;
    private double varAdicionarRaloLinear3 = 0;
    private double varAdicionarRaloLinear3_1 = 0;
    private double varAdicionarRaloLinear4 = 0;
    private double varAdicionarRaloLinear4_1 = 0;
    private double varAdicionarRaloLinear5 = 0;
    private double varAdicionarRaloLinear5_1 = 0;
    private double varAdicionarRaloLinear6 = 0;
    private double varAdicionarRaloLinear6_1 = 0;
    private double varAdicionarRaloLinear7 = 0;
    private double varAdicionarRaloLinear7_1 = 0;
    private double varAdicionarRaloLinear8 = 0;
    private double varAdicionarRaloLinear8_1 = 0;
    private double varAdicionarRaloLinear9 = 0;
    private double varAdicionarRaloLinear9_1 = 0;
    private double varAdicionarRaloLinear10 = 0;
    private double varAdicionarRaloLinear10_1 = 0;
    private double varAdicionarRaloLinear11 = 0;
    private double varAdicionarRaloLinear11_1 = 0;
    private double varAdicionarVasoSanitario = 0;
    private double varAdicionarVasoSanitario1 = 0;
    private double varAdicionarVasoSanitario2 = 0;
    private double varAdicionarVasoSanitario2_1 = 0;
    private double varAdicionarVasoSanitario3 = 0;
    private double varAdicionarVasoSanitario3_1 = 0;
    private double varAdicionarVasoSanitario4 = 0;
    private double varAdicionarVasoSanitario4_1 = 0;
    private double varAdicionarVasoSanitario5 = 0;
    private double varAdicionarVasoSanitario5_1 = 0;
    private double varAdicionarVasoSanitario6 = 0;
    private double varAdicionarVasoSanitario6_1 = 0;

    //Variaveis Revestimento
    private double varAdicionarAlvenaria = 0;
    private double varAdicionarAlvenaria1 = 0;
    private double varAdicionarAlvenaria2 = 0;
    private double varAdicionarAlvenaria2_1 = 0;
    private double varAdicionarAlvenaria3 = 0;
    private double varAdicionarAlvenaria3_1 = 0;
    private double varAdicionarAlvenaria4 = 0;
    private double varAdicionarAlvenaria4_1 = 0;
    private double varAdicionarAlvenaria5 = 0;
    private double varAdicionarAlvenaria5_1 = 0;
    private double varAdicionarAlvenaria6 = 0;
    private double varAdicionarAlvenaria6_1 = 0;
    private double varAdicionarAlvenaria7 = 0;
    private double varAdicionarAlvenaria7_1 = 0;
    private double varAdicionarAlvenaria8 = 0;
    private double varAdicionarAlvenaria8_1 = 0;
    private double varAdicionarAlvenaria9 = 0;
    private double varAdicionarAlvenaria9_1 = 0;
    private double varAdicionarAlvenaria10 = 0;
    private double varAdicionarAlvenaria10_1 = 0;
    private double varAdicionarAlvenaria11 = 0;
    private double varAdicionarAlvenaria11_1 = 0;
    private double varAdicionarContraPiso = 0;
    private double varAdicionarContraPiso1 = 0;
    private double varAdicionarContraPiso2 = 0;
    private double varAdicionarContraPiso2_1 = 0;
    private double varAdicionarContraPiso3 = 0;
    private double varAdicionarContraPiso3_1 = 0;
    private double varAdicionarContraPiso4 = 0;
    private double varAdicionarContraPiso4_1 = 0;
    private double varAdicionarContraPiso5 = 0;
    private double varAdicionarContraPiso5_1 = 0;
    private double varAdicionarContraPiso6 = 0;
    private double varAdicionarContraPiso6_1 = 0;
    private double varAdicionarContraPiso7 = 0;
    private double varAdicionarContraPiso7_1 = 0;
    private double varAdicionarContraPiso8 = 0;
    private double varAdicionarContraPiso8_1 = 0;
    private double varAdicionarContraPiso9 = 0;
    private double varAdicionarContraPiso9_1 = 0;
    private double varAdicionarContraPiso10 = 0;
    private double varAdicionarContraPiso10_1 = 0;
    private double varAdicionarContraPiso11 = 0;
    private double varAdicionarContraPiso11_1 = 0;
    private double varAplicacaoImpermeabilizante = 0;
    private double varAplicacaoImpermeabilizante1 = 0;
    private double varAplicacaoImpermeabilizante2 = 0;
    private double varAplicacaoImpermeabilizante2_1 = 0;
    private double varAplicacaoImpermeabilizante3 = 0;
    private double varAplicacaoImpermeabilizante3_1 = 0;
    private double varAplicacaoImpermeabilizante4 = 0;
    private double varAplicacaoImpermeabilizante4_1 = 0;
    private double varAplicacaoImpermeabilizante5 = 0;
    private double varAplicacaoImpermeabilizante5_1 = 0;
    private double varAplicacaoImpermeabilizante6 = 0;
    private double varAplicacaoImpermeabilizante6_1 = 0;
    private double varAplicacaoImpermeabilizante7 = 0;
    private double varAplicacaoImpermeabilizante7_1 = 0;
    private double varAplicacaoImpermeabilizante8 = 0;
    private double varAplicacaoImpermeabilizante8_1 = 0;
    private double varAplicacaoImpermeabilizante9 = 0;
    private double varAplicacaoImpermeabilizante9_1 = 0;
    private double varAplicacaoImpermeabilizante10 = 0;
    private double varAplicacaoImpermeabilizante10_1 = 0;
    private double varAplicacaoImpermeabilizante11 = 0;
    private double varAplicacaoImpermeabilizante11_1 = 0;
    private double varAplicarPorcelanatoMenor = 0;
    private double varAplicarPorcelanatoMenor1 = 0;
    private double varAplicarPorcelanatoMenor2 = 0;
    private double varAplicarPorcelanatoMenor2_1 = 0;
    private double varAplicarPorcelanatoMenor3 = 0;
    private double varAplicarPorcelanatoMenor3_1 = 0;
    private double varAplicarPorcelanatoMenor4 = 0;
    private double varAplicarPorcelanatoMenor4_1 = 0;
    private double varAplicarPorcelanatoMenor5 = 0;
    private double varAplicarPorcelanatoMenor5_1 = 0;
    private double varAplicarPorcelanatoMenor6 = 0;
    private double varAplicarPorcelanatoMenor6_1 = 0;
    private double varAplicarPorcelanatoMenor7 = 0;
    private double varAplicarPorcelanatoMenor7_1 = 0;
    private double varAplicarPorcelanatoMenor8 = 0;
    private double varAplicarPorcelanatoMenor8_1 = 0;
    private double varAplicarPorcelanatoMenor9 = 0;
    private double varAplicarPorcelanatoMenor9_1 = 0;
    private double varAplicarPorcelanatoMenor10 = 0;
    private double varAplicarPorcelanatoMenor10_1 = 0;
    private double varAplicarPorcelanatoMenor11 = 0;
    private double varAplicarPorcelanatoMenor11_1 = 0;
    private double varAplicarPorcelanatoMaior = 0;
    private double varAplicarPorcelanatoMaior1 = 0;
    private double varAplicarPorcelanatoMaior2 = 0;
    private double varAplicarPorcelanatoMaior2_1 = 0;
    private double varAplicarPorcelanatoMaior3 = 0;
    private double varAplicarPorcelanatoMaior3_1 = 0;
    private double varAplicarPorcelanatoMaior4 = 0;
    private double varAplicarPorcelanatoMaior4_1 = 0;
    private double varAplicarPorcelanatoMaior5 = 0;
    private double varAplicarPorcelanatoMaior5_1 = 0;
    private double varAplicarPorcelanatoMaior6 = 0;
    private double varAplicarPorcelanatoMaior6_1 = 0;
    private double varAplicarPorcelanatoMaior7 = 0;
    private double varAplicarPorcelanatoMaior7_1 = 0;
    private double varAplicarPorcelanatoMaior8 = 0;
    private double varAplicarPorcelanatoMaior8_1 = 0;
    private double varAplicarPorcelanatoMaior9 = 0;
    private double varAplicarPorcelanatoMaior9_1 = 0;
    private double varAplicarPorcelanatoMaior10 = 0;
    private double varAplicarPorcelanatoMaior10_1 = 0;
    private double varAplicarPorcelanatoMaior11 = 0;
    private double varAplicarPorcelanatoMaior11_1 = 0;
    private double varPastilhaVidro = 0;
    private double varPastilhaVidro_1 = 0;
    private double varPastilhaVidro_2 = 0;
    private double varPastilhaVidro_2_1 = 0;
    private double varPastilhaVidro_3 = 0;
    private double varPastilhaVidro_3_1 = 0;
    private double varPastilhaVidro_4 = 0;
    private double varPastilhaVidro_4_1 = 0;
    private double varPastilhaVidro_5 = 0;
    private double varPastilhaVidro_5_1 = 0;
    private double varPastilhaVidro_6 = 0;
    private double varPastilhaVidro_6_1 = 0;
    private double varPastilhaVidro_7 = 0;
    private double varPastilhaVidro_7_1 = 0;
    private double varPastilhaVidro_8 = 0;
    private double varPastilhaVidro_8_1 = 0;
    private double varPastilhaVidro_9 = 0;
    private double varPastilhaVidro_9_1 = 0;
    private double varPastilhaVidro_10 = 0;
    private double varPastilhaVidro_10_1 = 0;
    private double varPastilhaVidro_11 = 0;
    private double varPastilhaVidro_11_1 = 0;
    private double varRevestimento3D = 0;
    private double varRevestimento3D_1 = 0;
    private double varRevestimento3D_2 = 0;
    private double varRevestimento3D_2_1 = 0;
    private double varRevestimento3D_3 = 0;
    private double varRevestimento3D_3_1 = 0;
    private double varRevestimento3D_4 = 0;
    private double varRevestimento3D_4_1 = 0;
    private double varRevestimento3D_5 = 0;
    private double varRevestimento3D_5_1 = 0;
    private double varRevestimento3D_6 = 0;
    private double varRevestimento3D_6_1 = 0;
    private double varRevestimento3D_7 = 0;
    private double varRevestimento3D_7_1 = 0;
    private double varRevestimento3D_8 = 0;
    private double varRevestimento3D_8_1 = 0;
    private double varRevestimento3D_9 = 0;
    private double varRevestimento3D_9_1 = 0;
    private double varRevestimento3D_10 = 0;
    private double varRevestimento3D_10_1 = 0;
    private double varRevestimento3D_11 = 0;
    private double varRevestimento3D_11_1 = 0;


    //Variaveis Pintura
    private double varJanela = 0;
    private double varJanela1 = 0;
    private double varJanela2 = 0;
    private double varJanela2_1 = 0;
    private double varJanela3 = 0;
    private double varJanela3_1 = 0;
    private double varJanela4 = 0;
    private double varJanela4_1 = 0;
    private double varJanela5 = 0;
    private double varJanela5_1 = 0;
    private double varJanela6 = 0;
    private double varJanela6_1 = 0;
    private double varJanela7 = 0;
    private double varJanela7_1 = 0;
    private double varJanela8 = 0;
    private double varJanela8_1 = 0;
    private double varJanela9 = 0;
    private double varJanela9_1 = 0;
    private double varJanela10 = 0;
    private double varJanela10_1 = 0;
    private double varJanela11 = 0;
    private double varJanela11_1 = 0;
    private double varPorta = 0;
    private double varPorta1 = 0;
    private double varPorta2 = 0;
    private double varPorta2_1 = 0;
    private double varPorta3 = 0;
    private double varPorta3_1 = 0;
    private double varPorta4 = 0;
    private double varPorta4_1 = 0;
    private double varPorta5 = 0;
    private double varPorta5_1 = 0;
    private double varPorta6 = 0;
    private double varPorta6_1 = 0;
    private double varPorta7 = 0;
    private double varPorta7_1 = 0;
    private double varPorta8 = 0;
    private double varPorta8_1 = 0;
    private double varPorta9 = 0;
    private double varPorta9_1 = 0;
    private double varPorta10 = 0;
    private double varPorta10_1 = 0;
    private double varPorta11 = 0;
    private double varPorta11_1 = 0;
    private double varEfeitoDecorativo = 0;
    private double varEfeitoDecorativo1 = 0;
    private double varEfeitoDecorativo2 = 0;
    private double varEfeitoDecorativo2_1 = 0;
    private double varEfeitoDecorativo3 = 0;
    private double varEfeitoDecorativo3_1 = 0;
    private double varEfeitoDecorativo4 = 0;
    private double varEfeitoDecorativo4_1 = 0;
    private double varEfeitoDecorativo5 = 0;
    private double varEfeitoDecorativo5_1 = 0;
    private double varEfeitoDecorativo6 = 0;
    private double varEfeitoDecorativo6_1 = 0;
    private double varEfeitoDecorativo7 = 0;
    private double varEfeitoDecorativo7_1 = 0;
    private double varEfeitoDecorativo8 = 0;
    private double varEfeitoDecorativo8_1 = 0;
    private double varEfeitoDecorativo9 = 0;
    private double varEfeitoDecorativo9_1 = 0;
    private double varEfeitoDecorativo10 = 0;
    private double varEfeitoDecorativo10_1 = 0;
    private double varEfeitoDecorativo11 = 0;
    private double varEfeitoDecorativo11_1 = 0;
    private double varReparoGesso = 0;
    private double varReparoGesso1 = 0;
    private double varReparoGesso2 = 0;
    private double varReparoGesso2_1 = 0;
    private double varReparoGesso3 = 0;
    private double varReparoGesso3_1 = 0;
    private double varReparoGesso4 = 0;
    private double varReparoGesso4_1 = 0;
    private double varReparoGesso5 = 0;
    private double varReparoGesso5_1 = 0;
    private double varReparoGesso6 = 0;
    private double varReparoGesso6_1 = 0;
    private double varReparoGesso7 = 0;
    private double varReparoGesso7_1 = 0;
    private double varReparoGesso8 = 0;
    private double varReparoGesso8_1 = 0;
    private double varReparoGesso9 = 0;
    private double varReparoGesso9_1 = 0;
    private double varReparoGesso10 = 0;
    private double varReparoGesso10_1 = 0;
    private double varReparoGesso11 = 0;
    private double varReparoGesso11_1 = 0;


    private double latitude = 0;
    private double latitude2 = 0;
    //Criacao do PDF
    private static final String TAG = "PdfCreatorActivity";
    final DecimalFormat decimalFormat = new DecimalFormat("0000");
    private String nomeCliente;
    private File pdfFile;
    private String alterarNumeroNota, alterarValorNota;


    Uri imageUri;

    //
    private double valorTotalCozinha;
    private double valorTotalBanheiro1;
    private double valorTotalBanheiro2;
    private double valorTotalAreaServico;
    private double valorTotalLavabo;
    private double valorTotalSacadaVaranda;
    private double valorTotalSalaJantar;
    private double valorTotalSalaEstar;
    private double valorTotalQuarto1;
    private double valorTotalQuarto2;
    private double valorTotalQuarto3;
    private double totalDemolicao;

    //Revestimento Valores Totais
    private double valorTotalRevestimentoCozinha = 0;
    private double valorTotalRevestimentoBanheiroSocial = 0;
    private double valorTotalRevestimentoBanheiroSuite = 0;
    private double valorTotalRevestimentoQuarto1 = 0;
    private double valorTotalRevestimentoQuarto2 = 0;
    private double valorTotalRevestimentoAreaServico = 0;
    private double valorTotalRevestimentoLavabo = 0;
    private double valorTotalRevestimentoSacada = 0;
    private double valorTotalRevestimentoSalaJantar = 0;
    private double valorTotalCategoriaRevestimento = 0;
    private double valorTotalRevestimentoSalaEstar = 0;
    private double valorTotalRevestimentoQuartoSuite = 0;

    //Art
    private double valorTotalCategoriaArt = 0;
    private double valorTotalCategoriaArt2 = 0;

    //Hidraulica Valores Totais
    private double valorTotalHidraulicaCozinha = 0;
    private double valorTotalHidraulicaBanheiroSocial = 0;
    private double valorTotalHidraulicaBanheiroSuite = 0;
    private double valorTotalHidraulicaQuarto1 = 0;
    private double valorTotalHidraulicaQuarto2 = 0;
    private double valorTotalHidraulicaAreaServico = 0;
    private double valorTotalHidraulicaLavabo = 0;
    private double valorTotalHidraulicaSacada = 0;
    private double valorTotalHidraulicaSalaJantar = 0;
    private double valorTotalCategoriaHidraulica = 0;
    private double valorTotalHidraulicaSalaEstar = 0;
    private double valorTotalHidraulicaQuartoSuite = 0;

    //Pintura Valores Totais
    private double valorTotalPinturaCozinha = 0;
    private double valorTotalPinturaBanheiroSocial = 0;
    private double valorTotalPinturaBanheiroSuite = 0;
    private double valorTotalPinturaQuarto1 = 0;
    private double valorTotalPinturaQuarto2 = 0;
    private double valorTotalPinturaAreaServico = 0;
    private double valorTotalPinturaLavabo = 0;
    private double valorTotalPinturaSacada = 0;
    private double valorTotalPinturaSalaJantar = 0;
    private double valorTotalCategoriaPintura = 0;
    private double valorTotalPinturaSalaEstar = 0;
    private double valorTotalPinturaQuartoSuite = 0;


    //ART Valores Totais
    private double valorTotalArtArcondicionado = 68.12;
    private double valorTotalArtEnvidracamento = 162.35;
    private double valorTotalArtPedrasMarmore = 62.13;
    private double valorTotalArtNovosRevestimentos = 62.77;
    private double valorTotalArtEletrica = 35.12;
    private double valorTotalArtHidraulica = 31.15;
    private double valorTotalArtBox = 35.12;
    private double valorTotalArtGesso = 35.12;
    private double valorTotalArtDemolicaoParedeNaoEstrutural = 111.75;
    private double valorTotalArtMoveisPlanejados = 12.67;
    private double valorTotalArtDeslocamentoPontoGas = 85.96;
    private double valorTotalArtTaxa = 82.94;


    private int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int CAMERA = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static String mCurrentPhotoPath;


    //Logof

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demolicao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        //Casts nos Lineares
        linearART = findViewById(R.id.linearART);
        linearDemolicao = findViewById(R.id.linearDemolition);
        linearRevestimento = findViewById(R.id.linearRevestimento);
        linearHidraulica = findViewById(R.id.linearHidraulica);
        linearPintura = findViewById(R.id.linearPintura);


        //Firebase


        // use Shared preferences to save the best score
        SharedPreferences mypref = getPreferences(MODE_PRIVATE);
        SharedPreferences mypref2 = getPreferences(MODE_PRIVATE);
        numNota = mypref.getInt("numeroNota", 0001);
        valorNota = mypref.getString("valorNota", "0");
        exibirNota = findViewById(R.id.exibirNota);
        exibirValorNota = findViewById(R.id.textView91);
        exibirNota.setText("000" + Integer.toString(numNota));
        exibirValorNota.setText(valorNota);


        mContext = getApplicationContext();


        checker = new PermissionsChecker(this);

        //Botao Gerar Relatorio
        btn_finish = findViewById(R.id.btnFinalizar);
        relativeLayout = findViewById(R.id.layout_demolicao);

        //Fazendo cast/ instanciando os checklists aos seus respectivos views
        checkBoxCozinha = findViewById(R.id.checkbox_cozinha);
        checkBoxBanheiroSocial = findViewById(R.id.checkbox_banheiro_social);
        checkBoxBanheiroSuite = findViewById(R.id.checkbox_banheiro_suite);
        checkBoxAreaServico = findViewById(R.id.checkbox_areaservico);
        checkBoxLavabo = findViewById(R.id.checkbox_banheiro_lavabo);
        checkBoxSacadaVaranda = findViewById(R.id.checkbox_sacada);
        checkBoxSalaEstar = findViewById(R.id.checkbox_salaEstar);
        checkBoxSalaJantar = findViewById(R.id.checkbox_salaJantar);
        checkBoxQuarto1 = findViewById(R.id.checkbox_quarto1);
        checkBoxQuarto2 = findViewById(R.id.checkbox_quarto2);
        checkBoxQuartoSuite = findViewById(R.id.checkbox_quartoSuite);

        //CheckBox Revestimento
        checkBoxRevestimentoCozinha = findViewById(R.id.checkboxRevestimento_cozinha);
        checkBoxRevestimentoBanheiroSocial = findViewById(R.id.checkboxRevestimento_banheiro_social);
        checkBoxRevestimentoBanheiroSuite = findViewById(R.id.checkboxRevestimento_banheiro_suite);
        checkBoxRevestimentoAreaServico = findViewById(R.id.checkboxRevestimento_areaservico);
        checkBoxRevestimentoLavabo = findViewById(R.id.checkboxRevestimento_banheiro_lavabo);
        checkBoxRevestimentoSalaEstar = findViewById(R.id.checkboxRevestimento_salaEstar);
        checkBoxRevestimentoSalaJantar = findViewById(R.id.checkboxRevestimento_salaJantar);
        checkBoxRevestimentoQuarto1 = findViewById(R.id.checkboxRevestimento_quarto1);
        checkBoxRevestimentoQuarto2 = findViewById(R.id.checkboxRevestimento_quarto2);
        checkBoxRevestimentoQuartoSuite = findViewById(R.id.checkboxRevestimento_quartoSuite);
        checkBoxRevestimentoSacadaVaranda = findViewById(R.id.checkboxRevestimento_sacada);

        //CheckBox Hidraulica
        checkBoxHidraulicaCozinha = findViewById(R.id.checkboxHidraulica_cozinha);
        checkBoxHidraulicaBanheiroSocial = findViewById(R.id.checkboxHidraulica_banheiro_social);
        checkBoxHidraulicaBanheiroSuite = findViewById(R.id.checkboxHidraulica_banheiro_suite);
        checkBoxHidraulicaAreaServico = findViewById(R.id.checkboxHidraulica_areaservico);
        checkBoxHidraulicaLavabo = findViewById(R.id.checkboxHidraulica_banheiro_lavabo);
        checkBoxHidraulicaSacadaVaranda = findViewById(R.id.checkboxHidraulica_sacada);


        //Checkbox Pintura
        checkBoxPinturaCozinha = findViewById(R.id.checkboxPintura_cozinha);
        checkBoxPinturaBanheiroSocial = findViewById(R.id.checkboxPintura_banheiro_social);
        checkBoxPinturaBanheiroSuite = findViewById(R.id.checkboxPintura_banheiro_suite);
        checkBoxPinturaAreaServico = findViewById(R.id.checkboxPintura_areaservico);
        checkBoxPinturaLavabo = findViewById(R.id.checkboxPintura_banheiro_lavabo);
        checkBoxPinturaSalaEstar = findViewById(R.id.checkboxPintura_salaEstar);
        checkBoxPinturaSalaJantar = findViewById(R.id.checkboxPintura_salaJantar);
        checkBoxPinturaQuarto1 = findViewById(R.id.checkboxPintura_quarto1);
        checkBoxPinturaQuarto2 = findViewById(R.id.checkboxPintura_quarto2);
        checkBoxPinturaQuartoSuite = findViewById(R.id.checkboxPintura_quartoSuite);
        checkBoxPinturaSacadaVaranda = findViewById(R.id.checkboxPintura_sacada);


        //CheckBox ART
        BtncheckBoxArtArCondicionado = findViewById(R.id.checknoxArtArcondicionado);
        BtncheckboxArtEnvidracamento = findViewById(R.id.checkboxArtEnvidracamento);
        BtncheckboxArtPedrasMarmore = findViewById(R.id.checkboxArtPedrasMarmore);
        BtncheckboxArtNovosRevestimentos = findViewById(R.id.checkboxArtNovosRevestimentos);
        BtncheckboxArtEletrica = findViewById(R.id.checkboxArtEletrica);
        BtncheckboxArtHidraulica = findViewById(R.id.checkboxArtHidraulica);
        BtncheckboxArtBox = findViewById(R.id.checkboxArtBox);
        BtncheckboxArtGesso = findViewById(R.id.checkboxArtGesso);
        BtncheckboxArtDemolicao = findViewById(R.id.checkboxArtDemolicao);
        BtncheckboxArtMoveisPlanejados = findViewById(R.id.checkboxArtMoveisPlanejados);
        BtncheckboxArtDeslocamento = findViewById(R.id.checkboxArtDeslocamento);

        //Cast dos LinearLayouts
        linearLayout1 = findViewById(R.id.linearCozinha);
        linearLayout2 = findViewById(R.id.linearBanheiroSocial);
        linearLayout3 = findViewById(R.id.linearAreaServico);
        linearLayout4 = findViewById(R.id.linearBanheiroSuite);
        linearLayout5 = findViewById(R.id.linearLavabo);
        linearLayout6 = findViewById(R.id.linearSacada);
        linearLayout7 = findViewById(R.id.linearSalaJantar);
        linearLayout8 = findViewById(R.id.linearSalaEstar);
        linearLayout9 = findViewById(R.id.linearQuarto1);
        linearLayout10 = findViewById(R.id.linearQuarto2);
        linearLayout11 = findViewById(R.id.linearQuartoSuite);


        //Linear Revestimento
        linearLayoutRevestimento1 = findViewById(R.id.linearRevestimentoCozinha);
        linearLayoutRevestimento2 = findViewById(R.id.linearRevestimentoBanheiroSocial);
        linearLayoutRevestimento3 = findViewById(R.id.linearRevestimentoAreaServico);
        linearLayoutRevestimento4 = findViewById(R.id.linearRevestimentoBanheiroSuite);
        linearLayoutRevestimento5 = findViewById(R.id.linearRevestimentoLavabo);
        linearLayoutRevestimento6 = findViewById(R.id.linearRevestimentoSacada);
        linearLayoutRevestimento7 = findViewById(R.id.linearRevestimentoSalaJantar);
        linearLayoutRevestimento8 = findViewById(R.id.linearRevestimentoSalaEstar);
        linearLayoutRevestimento9 = findViewById(R.id.linearRevestimentoQuarto1);
        linearLayoutRevestimento10 = findViewById(R.id.linearRevestimentoQuarto2);
        linearLayoutRevestimento11 = findViewById(R.id.linearRevestimentoQuartoSuite);


        //Linear Hidraulica

        linearLayoutHidraulica1 = findViewById(R.id.linearHidraulicaCozinha);
        linearLayoutHidraulica2 = findViewById(R.id.linearHidraulicaBanheiroSocial);
        linearLayoutHidraulica3 = findViewById(R.id.linearHidraulicaAreaServico);
        linearLayoutHidraulica4 = findViewById(R.id.linearHidraulicaBanheiroSuite);
        linearLayoutHidraulica5 = findViewById(R.id.linearHidraulicaLavabo);
        linearLayoutHidraulica6 = findViewById(R.id.linearHidraulicaSacada);
        linearLayoutHidraulica7 = findViewById(R.id.linearHidraulicaSalaJantar);
        linearLayoutHidraulica8 = findViewById(R.id.linearHidraulicaSalaEstar);
        linearLayoutHidraulica9 = findViewById(R.id.linearHidraulicaQuarto1);
        linearLayoutHidraulica10 = findViewById(R.id.linearHidraulicaQuarto2);
        linearLayoutHidraulica11 = findViewById(R.id.linearHidraulicaQuartoSuite);


        //Linear Pintura
        linearLayoutPintura1 = findViewById(R.id.linearCozinhaPintura);
        linearLayoutPintura2 = findViewById(R.id.linearPinturaBanheiroSocial);
        linearLayoutPintura3 = findViewById(R.id.linearPinturaAreaServico);
        linearLayoutPintura4 = findViewById(R.id.linearPinturaBanheiroSuite);
        linearLayoutPintura5 = findViewById(R.id.linearPinturaLavabo);
        linearLayoutPintura6 = findViewById(R.id.linearPinturaSacada);
        linearLayoutPintura7 = findViewById(R.id.linearPinturaSalaJantar);
        linearLayoutPintura8 = findViewById(R.id.linearPinturaSalaEstar);
        linearLayoutPintura9 = findViewById(R.id.linearPinturaQuarto1);
        linearLayoutPintura10 = findViewById(R.id.linearPinturaQuarto2);
        linearLayoutPintura11 = findViewById(R.id.linearPinturaQuartoSuite);


        //Revestimento Parede
        valorRevestimentoParede1 = findViewById(R.id.removerRevestimentoParede);
        valorRevestimentoParede1_1 = findViewById(R.id.removerRevestimentoParede1);
        valorRevestimentoParede2 = findViewById(R.id.removerRevestimentoParede2);
        valorRevestimentoParede2_1 = findViewById(R.id.removerRevestimentoParede2_1);
        valorRevestimentoParede3 = findViewById(R.id.removerRevestimentoParede3);
        valorRevestimentoParede3_1 = findViewById(R.id.removerRevestimentoParede3);
        valorRevestimentoParede4 = findViewById(R.id.removerRevestimentoParede4);
        valorRevestimentoParede4_1 = findViewById(R.id.removerRevestimentoParede4_1);
        valorRevestimentoParede5 = findViewById(R.id.removerRevestimentoParede5);
        valorRevestimentoParede5_1 = findViewById(R.id.removerRevestimentoParede5_1);
        valorRevestimentoParede6 = findViewById(R.id.removerRevestimentoParede6);
        valorRevestimentoParede6_1 = findViewById(R.id.removerRevestimentoParede6_1);
        valorRevestimentoParede7 = findViewById(R.id.removerRevestimentoParede7);
        valorRevestimentoParede7_1 = findViewById(R.id.removerRevestimentoParede7_1);
        valorRevestimentoParede8 = findViewById(R.id.removerRevestimentoParede8);
        valorRevestimentoParede8_1 = findViewById(R.id.removerRevestimentoParede8_1);
        valorRevestimentoParede9 = findViewById(R.id.removerRevestimentoParede9);
        valorRevestimentoParede9_1 = findViewById(R.id.removerRevestimentoParede9_1);
        valorRevestimentoParede10 = findViewById(R.id.removerRevestimentoParede10);
        valorRevestimentoParede10_1 = findViewById(R.id.removerRevestimentoParede10_1);
        valorRevestimentoParede11 = findViewById(R.id.removerRevestimentoParede11);
        valorRevestimentoParede11_1 = findViewById(R.id.removerRevestimentoParede11_1);

        //Remocao de Piso
        valorRemocaoPiso1 = findViewById(R.id.removerPiso);
        valorRemocaoPiso1_1 = findViewById(R.id.removerPiso1);
        valorRemocaoPiso2 = findViewById(R.id.removerPiso2);
        valorRemocaoPiso2_1 = findViewById(R.id.removerPiso2_1);
        valorRemocaoPiso3 = findViewById(R.id.removerPiso3);
        valorRemocaoPiso3_1 = findViewById(R.id.removerPiso3_2);
        valorRemocaoPiso4 = findViewById(R.id.removerPiso4);
        valorRemocaoPiso4_1 = findViewById(R.id.removerPiso4_1);
        valorRemocaoPiso5 = findViewById(R.id.removerPiso5);
        valorRemocaoPiso5_1 = findViewById(R.id.removerPiso5_1);
        valorRemocaoPiso6 = findViewById(R.id.removerPiso6);
        valorRemocaoPiso6_1 = findViewById(R.id.removerPiso6_1);
        valorRemocaoPiso7 = findViewById(R.id.removerPiso7);
        valorRemocaoPiso7_1 = findViewById(R.id.removerPiso7_1);
        valorRemocaoPiso8 = findViewById(R.id.removerPiso8);
        valorRemocaoPiso8_1 = findViewById(R.id.removerPiso8_1);
        valorRemocaoPiso9 = findViewById(R.id.removerPiso9);
        valorRemocaoPiso9_1 = findViewById(R.id.removerPiso9_1);
        valorRemocaoPiso10 = findViewById(R.id.removerPiso10);
        valorRemocaoPiso10_1 = findViewById(R.id.removerPiso10_1);
        valorRemocaoPiso11 = findViewById(R.id.removerPiso11);
        valorRemocaoPiso11_1 = findViewById(R.id.removerPiso11_1);

        //Remocao de Pia
        valorRemocaoPia1 = findViewById(R.id.removerPia);
        valorRemocaoPia1_1 = findViewById(R.id.removerPia1);
        valorRemocaoPia2 = findViewById(R.id.removerPia2);
        valorRemocaoPia2_1 = findViewById(R.id.removerPia2_1);
        valorRemocaoPia3 = findViewById(R.id.removerPia3);
        valorRemocaoPia3_1 = findViewById(R.id.removerPia3_1);
        valorRemocaoPia4 = findViewById(R.id.removerPia4);
        valorRemocaoPia4_1 = findViewById(R.id.removerPia4_1);
        valorRemocaoPia5 = findViewById(R.id.removerPia5);
        valorRemocaoPia5_1 = findViewById(R.id.removerPia5_1);
        valorRemocaoPia6 = findViewById(R.id.removerPia6);
        valorRemocaoPia6_1 = findViewById(R.id.removerPia6_1);
        valorRemocaoPia7 = findViewById(R.id.removerPia7);
        valorRemocaoPia7_1 = findViewById(R.id.removerPia7_1);
        valorRemocaoPia8 = findViewById(R.id.removerPia8);
        valorRemocaoPia8_1 = findViewById(R.id.removerPia8_1);
        valorRemocaoPia9 = findViewById(R.id.removerPia9);
        valorRemocaoPia9_1 = findViewById(R.id.removerPia9_1);
        valorRemocaoPia10 = findViewById(R.id.removerPia10);
        valorRemocaoPia10_1 = findViewById(R.id.removerPia10_1);
        valorRemocaoPia11 = findViewById(R.id.removerPia11);
        valorRemocaoPia11_1 = findViewById(R.id.removerPia11_1);

        //Remocao de Alvenaria
        valorRemocacAlvenaria1 = findViewById(R.id.removerAlvenaria);
        valorRemocacAlvenaria1_1 = findViewById(R.id.removerAlvenaria1);
        valorRemocacAlvenaria2 = findViewById(R.id.removerAlvenaria2);
        valorRemocacAlvenaria2_1 = findViewById(R.id.removerAlvenaria2_1);
        valorRemocacAlvenaria3 = findViewById(R.id.removerAlvenaria3);
        valorRemocacAlvenaria3_1 = findViewById(R.id.removerAlvenaria2_3);
        valorRemocacAlvenaria4 = findViewById(R.id.removerAlvenaria4);
        valorRemocacAlvenaria4_1 = findViewById(R.id.removerAlvenaria4_1);
        valorRemocacAlvenaria5 = findViewById(R.id.removerAlvenaria5);
        valorRemocacAlvenaria5_1 = findViewById(R.id.removerAlvenaria5_1);
        valorRemocacAlvenaria6 = findViewById(R.id.removerAlvenaria6);
        valorRemocacAlvenaria6_1 = findViewById(R.id.removerAlvenaria6_1);
        valorRemocacAlvenaria7 = findViewById(R.id.removerAlvenaria7);
        valorRemocacAlvenaria7_1 = findViewById(R.id.removerAlvenaria7_1);
        valorRemocacAlvenaria8 = findViewById(R.id.removerAlvenaria8);
        valorRemocacAlvenaria8_1 = findViewById(R.id.removerAlvenaria8_1);
        valorRemocacAlvenaria9 = findViewById(R.id.removerAlvenaria9);
        valorRemocacAlvenaria9_1 = findViewById(R.id.removerAlvenaria9_1);
        valorRemocacAlvenaria10 = findViewById(R.id.removerAlvenaria10);
        valorRemocacAlvenaria10_1 = findViewById(R.id.removerAlvenaria10_1);
        valorRemocacAlvenaria11 = findViewById(R.id.removerAlvenaria11);
        valorRemocacAlvenaria11_1 = findViewById(R.id.removerAlvenaria11_1);

        //Remover Tanque
        valorRemocaoTanque1 = findViewById(R.id.removerTanque);
        valorRemocaoTanque1_1 = findViewById(R.id.removerTanque1);
        valorRemocaoTanque2 = findViewById(R.id.removerTanque2);
        valorRemocaoTanque2_1 = findViewById(R.id.removerTanque2_1);
        valorRemocaoTanque3 = findViewById(R.id.removerTanque3);
        valorRemocaoTanque3_1 = findViewById(R.id.removerTanque2_3);
        valorRemocaoTanque4 = findViewById(R.id.removerTanque4);
        valorRemocaoTanque4_1 = findViewById(R.id.removerTanque4_1);
        valorRemocaoTanque5 = findViewById(R.id.removerTanque5);
        valorRemocaoTanque5_1 = findViewById(R.id.removerTanque5_1);
        valorRemocaoTanque6 = findViewById(R.id.removerTanque6);
        valorRemocaoTanque6_1 = findViewById(R.id.removerTanque6_1);
        valorRemocaoTanque7 = findViewById(R.id.removerTanque7);
        valorRemocaoTanque7_1 = findViewById(R.id.removerTanque7_1);
        valorRemocaoTanque8 = findViewById(R.id.removerTanque8);
        valorRemocaoTanque8_1 = findViewById(R.id.removerTanque8_1);
        valorRemocaoTanque9 = findViewById(R.id.removerTanque9);
        valorRemocaoTanque9_1 = findViewById(R.id.removerTanque9_1);
        valorRemocaoTanque10 = findViewById(R.id.removerTanque10);
        valorRemocaoTanque10_1 = findViewById(R.id.removerTanque10_1);
        valorRemocaoTanque11 = findViewById(R.id.removerTanque11);
        valorRemocaoTanque11_1 = findViewById(R.id.removerTanque11_1);

        //Rasgar Caixnha
        valorRasgarCaixinha4x2_1 = findViewById(R.id.rasgarCaixinha4x2);
        valorRasgarCaixinha4x2_1_1 = findViewById(R.id.rasgarCaixinha4x2_1);
        valorRasgarCaixinha4x2_2 = findViewById(R.id.rasgarCaixinha4x2_2);
        valorRasgarCaixinha4x2_2_1 = findViewById(R.id.rasgarCaixinha4x2_2_1);
        valorRasgarCaixinha4x2_3 = findViewById(R.id.rasgarCaixinha4x2_3);
        valorRasgarCaixinha4x2_3_1 = findViewById(R.id.rasgarCaixinha4x2_1);
        valorRasgarCaixinha4x2_4 = findViewById(R.id.rasgarCaixinha4x2_4);
        valorRasgarCaixinha4x2_4_1 = findViewById(R.id.rasgarCaixinha4x2_4_1);
        valorRasgarCaixinha4x2_5 = findViewById(R.id.rasgarCaixinha4x2_5);
        valorRasgarCaixinha4x2_5_1 = findViewById(R.id.rasgarCaixinha4x2_5_1);
        valorRasgarCaixinha4x2_6 = findViewById(R.id.rasgarCaixinha4x2_6);
        valorRasgarCaixinha4x2_6_1 = findViewById(R.id.rasgarCaixinha4x2_6_1);
        valorRasgarCaixinha4x2_7 = findViewById(R.id.rasgarCaixinha4x2_7);
        valorRasgarCaixinha4x2_7_1 = findViewById(R.id.rasgarCaixinha4x2_7_1);
        valorRasgarCaixinha4x2_8 = findViewById(R.id.rasgarCaixinha4x2_8);
        valorRasgarCaixinha4x2_8_1 = findViewById(R.id.rasgarCaixinha4x2_8_1);
        valorRasgarCaixinha4x2_9 = findViewById(R.id.rasgarCaixinha4x2_9);
        valorRasgarCaixinha4x2_9_1 = findViewById(R.id.rasgarCaixinha4x2_9_1);
        valorRasgarCaixinha4x2_10 = findViewById(R.id.rasgarCaixinha4x2_10);
        valorRasgarCaixinha4x2_10_1 = findViewById(R.id.rasgarCaixinha4x2_10_1);
        valorRasgarCaixinha4x2_11 = findViewById(R.id.rasgarCaixinha4x2_11);
        valorRasgarCaixinha4x2_11_1 = findViewById(R.id.rasgarCaixinha4x2_11_1);

        //Rasgar Caixinha 4x4

        valorRasgarCaixinha4x4_1 = findViewById(R.id.rasgarCaixinha4x4);
        valorRasgarCaixinha4x4_1_1 = findViewById(R.id.rasgarCaixinha4x4_1);
        valorRasgarCaixinha4x4_2 = findViewById(R.id.rasgarCaixinha4x4_2);
        valorRasgarCaixinha4x4_2_1 = findViewById(R.id.rasgarCaixinha4x4_2_1);
        valorRasgarCaixinha4x4_3 = findViewById(R.id.rasgarCaixinha4x4_3);
        valorRasgarCaixinha4x4_3_1 = findViewById(R.id.rasgarCaixinha4x4_3_1);
        valorRasgarCaixinha4x4_4 = findViewById(R.id.rasgarCaixinha4x4_4);
        valorRasgarCaixinha4x4_4_1 = findViewById(R.id.rasgarCaixinha4x4_4_1);
        valorRasgarCaixinha4x4_5 = findViewById(R.id.rasgarCaixinha4x4_5);
        valorRasgarCaixinha4x4_5_1 = findViewById(R.id.rasgarCaixinha4x4_5_1);
        valorRasgarCaixinha4x4_6 = findViewById(R.id.rasgarCaixinha4x4_6);
        valorRasgarCaixinha4x4_6_1 = findViewById(R.id.rasgarCaixinha4x4_6_1);
        valorRasgarCaixinha4x4_7 = findViewById(R.id.rasgarCaixinha4x4_7);
        valorRasgarCaixinha4x4_7_1 = findViewById(R.id.rasgarCaixinha4x4_7_1);
        valorRasgarCaixinha4x4_8 = findViewById(R.id.rasgarCaixinha4x4_8);
        valorRasgarCaixinha4x4_8_1 = findViewById(R.id.rasgarCaixinha4x4_8_1);
        valorRasgarCaixinha4x4_9 = findViewById(R.id.rasgarCaixinha4x4_9);
        valorRasgarCaixinha4x4_9_1 = findViewById(R.id.rasgarCaixinha4x4_9_1);
        valorRasgarCaixinha4x4_10 = findViewById(R.id.rasgarCaixinha4x4_10);
        valorRasgarCaixinha4x4_10_1 = findViewById(R.id.rasgarCaixinha4x4_10_1);
        valorRasgarCaixinha4x4_11 = findViewById(R.id.rasgarCaixinha4x4_11);
        valorRasgarCaixinha4x4_11_1 = findViewById(R.id.rasgarCaixinha4x4_11_1);

        //Rasgar Hidraulica
        valorRasgarHidraulica1 = findViewById(R.id.rasgarHidraulica);
        valorRasgarHidraulica1_1 = findViewById(R.id.rasgarHidraulica1);
        valorRasgarHidraulica2 = findViewById(R.id.rasgarHidraulica2);
        valorRasgarHidraulica2_1 = findViewById(R.id.rasgarHidraulica2_1);
        valorRasgarHidraulica3 = findViewById(R.id.rasgarHidraulica3);
        valorRasgarHidraulica3_1 = findViewById(R.id.rasgarHidraulica3_1);
        valorRasgarHidraulica4 = findViewById(R.id.rasgarHidraulica4);
        valorRasgarHidraulica4_1 = findViewById(R.id.rasgarHidraulica4_1);
        valorRasgarHidraulica5 = findViewById(R.id.rasgarHidraulica5);
        valorRasgarHidraulica5_1 = findViewById(R.id.rasgarHidraulica5_1);
        valorRasgarHidraulica6 = findViewById(R.id.rasgarHidraulica6);
        valorRasgarHidraulica6_1 = findViewById(R.id.rasgarHidraulica6_1);
        valorRasgarHidraulica7 = findViewById(R.id.rasgarHidraulica7);
        valorRasgarHidraulica7_1 = findViewById(R.id.rasgarHidraulica7_1);
        valorRasgarHidraulica8 = findViewById(R.id.rasgarHidraulica8);
        valorRasgarHidraulica8_1 = findViewById(R.id.rasgarHidraulica8_1);
        valorRasgarHidraulica9 = findViewById(R.id.rasgarHidraulica9);
        valorRasgarHidraulica9_1 = findViewById(R.id.rasgarHidraulica9_1);
        valorRasgarHidraulica10 = findViewById(R.id.rasgarHidraulica10);
        valorRasgarHidraulica10_1 = findViewById(R.id.rasgarHidraulica10_1);
        valorRasgarHidraulica11 = findViewById(R.id.rasgarHidraulica11);
        valorRasgarHidraulica11_1 = findViewById(R.id.rasgarHidraulica11_1);

        //Remocao Gesso

        valorRemoverGesso1 = findViewById(R.id.removerGesso);
        valorRemoverGesso1_1 = findViewById(R.id.removerGesso1);
        valorRemoverGesso2 = findViewById(R.id.removerGesso2);
        valorRemoverGesso2_1 = findViewById(R.id.removerGesso2_1);
        valorRemoverGesso3 = findViewById(R.id.removerGesso3);
        valorRemoverGesso3_1 = findViewById(R.id.removerGesso3_1);
        valorRemoverGesso4 = findViewById(R.id.removerGesso4);
        valorRemoverGesso4_1 = findViewById(R.id.removerGesso4_1);
        valorRemoverGesso5 = findViewById(R.id.removerGesso5);
        valorRemoverGesso5_1 = findViewById(R.id.removerGesso5_1);
        valorRemoverGesso6 = findViewById(R.id.removerGesso6);
        valorRemoverGesso6_1 = findViewById(R.id.removerGesso6_1);
        valorRemoverGesso7 = findViewById(R.id.removerGesso7);
        valorRemoverGesso7_1 = findViewById(R.id.removerGesso7_1);
        valorRemoverGesso8 = findViewById(R.id.removerGesso8);
        valorRemoverGesso8_1 = findViewById(R.id.removerGesso8_1);
        valorRemoverGesso9 = findViewById(R.id.removerGesso9);
        valorRemoverGesso9_1 = findViewById(R.id.removerGesso9_1);
        valorRemoverGesso10 = findViewById(R.id.removerGesso10);
        valorRemoverGesso10_1 = findViewById(R.id.removerGesso10_1);
        valorRemoverGesso11 = findViewById(R.id.removerGesso11);
        valorRemoverGesso11_1 = findViewById(R.id.removerGesso11_1);

        //Remocao Vaso Sanitario

        valorRemoverVaso1 = findViewById(R.id.removerVaso);
        valorRemoverVaso1_1 = findViewById(R.id.removerVaso1);
        valorRemoverVaso2 = findViewById(R.id.removerVaso2);
        valorRemoverVaso2_1 = findViewById(R.id.removerVaso2_1);
        valorRemoverVaso3 = findViewById(R.id.removerVaso3);
        valorRemoverVaso3_1 = findViewById(R.id.removerVaso3_1);
        valorRemoverVaso4 = findViewById(R.id.removerVaso4);
        valorRemoverVaso4_1 = findViewById(R.id.removerVaso4_1);
        valorRemoverVaso5 = findViewById(R.id.removerVaso5);
        valorRemoverVaso5_1 = findViewById(R.id.removerVaso5_1);
        valorRemoverVaso6 = findViewById(R.id.removerVaso6);
        valorRemoverVaso6_1 = findViewById(R.id.removerVaso6_1);
        valorRemoverVaso7 = findViewById(R.id.removerVaso7);
        valorRemoverVaso7_1 = findViewById(R.id.removerVaso7_1);
        valorRemoverVaso8 = findViewById(R.id.removerVaso8);
        valorRemoverVaso8_1 = findViewById(R.id.removerVaso8_1);
        valorRemoverVaso9 = findViewById(R.id.removerVaso9);
        valorRemoverVaso9_1 = findViewById(R.id.removerVaso9_1);
        valorRemoverVaso10 = findViewById(R.id.removerVaso10);
        valorRemoverVaso10_1 = findViewById(R.id.removerVaso10_1);
        valorRemoverVaso11 = findViewById(R.id.removerVaso11);
        valorRemoverVaso11_1 = findViewById(R.id.removerVaso11_1);

        //vao

        valorRemoverVao1 = findViewById(R.id.removerVao);
        valorRemoverVao1_1 = findViewById(R.id.removerVao1);
        valorRemoverVao2 = findViewById(R.id.removerVao2);
        valorRemoverVao2_1 = findViewById(R.id.removerVao2_1);
        valorRemoverVao3 = findViewById(R.id.removerVao3);
        valorRemoverVao3_1 = findViewById(R.id.removerVao3_1);
        valorRemoverVao4 = findViewById(R.id.removerVao4);
        valorRemoverVao4_1 = findViewById(R.id.removerVao4_1);
        valorRemoverVao5 = findViewById(R.id.removerVao5);
        valorRemoverVao5_1 = findViewById(R.id.removerVao5_1);
        valorRemoverVao6 = findViewById(R.id.removerVao6);
        valorRemoverVao6_1 = findViewById(R.id.removerVao6_1);
        valorRemoverVao7 = findViewById(R.id.removerVao7);
        valorRemoverVao7_1 = findViewById(R.id.removerVao7_1);
        valorRemoverVao8 = findViewById(R.id.removerVao8);
        valorRemoverVao8_1 = findViewById(R.id.removerVao8_1);
        valorRemoverVao9 = findViewById(R.id.removerVao9);
        valorRemoverVao9_1 = findViewById(R.id.removerVao9_1);
        valorRemoverVao10 = findViewById(R.id.removerVao10);
        valorRemoverVao10_1 = findViewById(R.id.removerVao10_1);
        valorRemoverVao11 = findViewById(R.id.removerVao11);
        valorRemoverVao11_1 = findViewById(R.id.removerVao11_1);


        //Revestimento
        valorRevestimentoAlvenariaBase1 = findViewById(R.id.criarAlvenariaBase);
        valorRevestimentoAlvenariaBase1_1 = findViewById(R.id.criarAlvenariaBase1);
        valorRevestimentoAlvenariaBase2 = findViewById(R.id.criarAlvenariaBase2);
        valorRevestimentoAlvenariaBase2_1 = findViewById(R.id.criarAlvenariaBase2_1);
        valorRevestimentoAlvenariaBase3 = findViewById(R.id.criarAlvenariaBase3);
        valorRevestimentoAlvenariaBase3_1 = findViewById(R.id.criarAlvenariaBase3_1);
        valorRevestimentoAlvenariaBase4 = findViewById(R.id.criarAlvenariaBase4);
        valorRevestimentoAlvenariaBase4_1 = findViewById(R.id.criarAlvenariaBase4_1);
        valorRevestimentoAlvenariaBase5 = findViewById(R.id.criarAlvenariaBase5);
        valorRevestimentoAlvenariaBase5_1 = findViewById(R.id.criarAlvenariaBase5_1);
        valorRevestimentoAlvenariaBase6 = findViewById(R.id.criarAlvenariaBase6);
        valorRevestimentoAlvenariaBase6_1 = findViewById(R.id.criarAlvenariaBase6_1);
        valorRevestimentoAlvenariaBase7 = findViewById(R.id.criarAlvenariaBase7);
        valorRevestimentoAlvenariaBase7_1 = findViewById(R.id.criarAlvenariaBase7_1);
        valorRevestimentoAlvenariaBase8 = findViewById(R.id.criarAlvenariaBase8);
        valorRevestimentoAlvenariaBase8_1 = findViewById(R.id.criarAlvenariaBase8_1);
        valorRevestimentoAlvenariaBase9 = findViewById(R.id.criarAlvenariaBase9);
        valorRevestimentoAlvenariaBase9_1 = findViewById(R.id.criarAlvenariaBase9_1);
        valorRevestimentoAlvenariaBase10 = findViewById(R.id.criarAlvenariaBase10);
        valorRevestimentoAlvenariaBase10_1 = findViewById(R.id.criarAlvenariaBase10_1);
        valorRevestimentoAlvenariaBase11 = findViewById(R.id.criarAlvenariaBase11);
        valorRevestimentoAlvenariaBase11_1 = findViewById(R.id.criarAlvenariaBase11_1);

        valorRevestimentoContraPiso1 = findViewById(R.id.criarContraPiso);
        valorRevestimentoContraPiso1_1 = findViewById(R.id.criarContraPiso1);
        valorRevestimentoContraPiso2 = findViewById(R.id.criarContraPiso2);
        valorRevestimentoContraPiso2_1 = findViewById(R.id.criarContraPiso2_1);
        valorRevestimentoContraPiso3 = findViewById(R.id.criarContraPiso3);
        valorRevestimentoContraPiso3_1 = findViewById(R.id.criarContraPiso3_2);
        valorRevestimentoContraPiso4 = findViewById(R.id.criarContraPiso4);
        valorRevestimentoContraPiso4_1 = findViewById(R.id.criarContraPiso4_1);
        valorRevestimentoContraPiso5 = findViewById(R.id.criarContraPiso5);
        valorRevestimentoContraPiso5_1 = findViewById(R.id.criarContraPiso5_1);
        valorRevestimentoContraPiso6 = findViewById(R.id.criarContraPiso6);
        valorRevestimentoContraPiso6_1 = findViewById(R.id.criarContraPiso6_1);
        valorRevestimentoContraPiso7 = findViewById(R.id.criarContraPiso7);
        valorRevestimentoContraPiso7_1 = findViewById(R.id.criarContraPiso7_1);
        valorRevestimentoContraPiso8 = findViewById(R.id.criarContraPiso8);
        valorRevestimentoContraPiso8_1 = findViewById(R.id.criarContraPiso8_1);
        valorRevestimentoContraPiso9 = findViewById(R.id.criarContraPiso9);
        valorRevestimentoContraPiso9_1 = findViewById(R.id.criarContraPiso9_1);
        valorRevestimentoContraPiso10 = findViewById(R.id.criarContraPiso10);
        valorRevestimentoContraPiso10_1 = findViewById(R.id.criarContraPiso10_1);
        valorRevestimentoContraPiso11 = findViewById(R.id.criarContraPiso11);
        valorRevestimentoContraPiso11_1 = findViewById(R.id.criarContraPiso11_1);

        valorRevestimentoImpermeabilidade1 = findViewById(R.id.aplicarImpermeabilidade);
        valorRevestimentoImpermeabilidade1_1 = findViewById(R.id.aplicarImpermeabilidade1);
        valorRevestimentoImpermeabilidade2 = findViewById(R.id.aplicarImpermeabilidade2);
        valorRevestimentoImpermeabilidade2_1 = findViewById(R.id.aplicarImpermeabilidade2_1);
        valorRevestimentoImpermeabilidade3 = findViewById(R.id.aplicarImpermeabilidade3);
        valorRevestimentoImpermeabilidade3_1 = findViewById(R.id.aplicarImpermeabilidade3_1);
        valorRevestimentoImpermeabilidade4 = findViewById(R.id.aplicarImpermeabilidade4);
        valorRevestimentoImpermeabilidade4_1 = findViewById(R.id.aplicarImpermeabilidade4_1);
        valorRevestimentoImpermeabilidade5 = findViewById(R.id.aplicarImpermeabilidade5);
        valorRevestimentoImpermeabilidade5_1 = findViewById(R.id.aplicarImpermeabilidade5_1);
        valorRevestimentoImpermeabilidade6 = findViewById(R.id.aplicarImpermeabilidade6);
        valorRevestimentoImpermeabilidade6_1 = findViewById(R.id.aplicarImpermeabilidade6_1);
        valorRevestimentoImpermeabilidade7 = findViewById(R.id.aplicarImpermeabilidade7);
        valorRevestimentoImpermeabilidade7_1 = findViewById(R.id.aplicarImpermeabilidade7_1);
        valorRevestimentoImpermeabilidade8 = findViewById(R.id.aplicarImpermeabilidade8);
        valorRevestimentoImpermeabilidade8_1 = findViewById(R.id.aplicarImpermeabilidade8_1);
        valorRevestimentoImpermeabilidade9 = findViewById(R.id.aplicarImpermeabilidade9);
        valorRevestimentoImpermeabilidade9_1 = findViewById(R.id.aplicarImpermeabilidade9_1);
        valorRevestimentoImpermeabilidade10 = findViewById(R.id.aplicarImpermeabilidade10);
        valorRevestimentoImpermeabilidade10_1 = findViewById(R.id.aplicarImpermeabilidade10_1);
        valorRevestimentoImpermeabilidade11 = findViewById(R.id.aplicarImpermeabilidade11);
        valorRevestimentoImpermeabilidade11_1 = findViewById(R.id.aplicarImpermeabilidade11_1);

        valorRevestimentoPorcelanatoAcima1 = findViewById(R.id.porcelanatoMaior);
        valorRevestimentoPorcelanatoAcima1_1 = findViewById(R.id.porcelanatoMaior1);
        valorRevestimentoPorcelanatoAcima2 = findViewById(R.id.porcelanatoMaior2);
        valorRevestimentoPorcelanatoAcima2_1 = findViewById(R.id.porcelanatoMaior2_1);
        valorRevestimentoPorcelanatoAcima3 = findViewById(R.id.porcelanatoMaior3);
        valorRevestimentoPorcelanatoAcima3_1 = findViewById(R.id.porcelanatoMaior2_3);
        valorRevestimentoPorcelanatoAcima4 = findViewById(R.id.porcelanatoMaior4);
        valorRevestimentoPorcelanatoAcima4_1 = findViewById(R.id.porcelanatoMaior4_1);
        valorRevestimentoPorcelanatoAcima5 = findViewById(R.id.porcelanatoMaior5);
        valorRevestimentoPorcelanatoAcima5_1 = findViewById(R.id.porcelanatoMaior5_1);
        valorRevestimentoPorcelanatoAcima6 = findViewById(R.id.porcelanatoMaior6);
        valorRevestimentoPorcelanatoAcima6_1 = findViewById(R.id.porcelanatoMaior6_1);
        valorRevestimentoPorcelanatoAcima7 = findViewById(R.id.porcelanatoMaior7);
        valorRevestimentoPorcelanatoAcima7_1 = findViewById(R.id.porcelanatoMaior7_1);
        valorRevestimentoPorcelanatoAcima8 = findViewById(R.id.porcelanatoMaior8);
        valorRevestimentoPorcelanatoAcima8_1 = findViewById(R.id.porcelanatoMaior8_1);
        valorRevestimentoPorcelanatoAcima9 = findViewById(R.id.porcelanatoMaior9);
        valorRevestimentoPorcelanatoAcima9_1 = findViewById(R.id.porcelanatoMaior9_1);
        valorRevestimentoPorcelanatoAcima10 = findViewById(R.id.porcelanatoMaior10);
        valorRevestimentoPorcelanatoAcima10_1 = findViewById(R.id.porcelanatoMaior10_1);
        valorRevestimentoPorcelanatoAcima11 = findViewById(R.id.porcelanatoMaior11);
        valorRevestimentoPorcelanatoAcima11_1 = findViewById(R.id.porcelanatoMaior11_1);


        valorRevestimentoPorcelanatoMenor1 = findViewById(R.id.porcelanatoMenor);
        valorRevestimentoPorcelanatoMenor1_1 = findViewById(R.id.porcelanatoMenor1);
        valorRevestimentoPorcelanatoMenor2 = findViewById(R.id.porcelanatoMenor2);
        valorRevestimentoPorcelanatoMenor2_1 = findViewById(R.id.porcelanatoMenor2_1);
        valorRevestimentoPorcelanatoMenor3 = findViewById(R.id.porcelanatoMenor3);
        valorRevestimentoPorcelanatoMenor3_1 = findViewById(R.id.porcelanatoMenor2_3);
        valorRevestimentoPorcelanatoMenor4 = findViewById(R.id.porcelanatoMenor4);
        valorRevestimentoPorcelanatoMenor4_1 = findViewById(R.id.porcelanatoMenor4_1);
        valorRevestimentoPorcelanatoMenor5 = findViewById(R.id.porcelanatoMenor5);
        valorRevestimentoPorcelanatoMenor5_1 = findViewById(R.id.porcelanatoMenor5_1);
        valorRevestimentoPorcelanatoMenor6 = findViewById(R.id.porcelanatoMenor6);
        valorRevestimentoPorcelanatoMenor6_1 = findViewById(R.id.porcelanatoMenor6_1);
        valorRevestimentoPorcelanatoMenor7 = findViewById(R.id.porcelanatoMenor7);
        valorRevestimentoPorcelanatoMenor7_1 = findViewById(R.id.porcelanatoMenor7_1);
        valorRevestimentoPorcelanatoMenor8 = findViewById(R.id.porcelanatoMenor8);
        valorRevestimentoPorcelanatoMenor8_1 = findViewById(R.id.porcelanatoMenor8_1);
        valorRevestimentoPorcelanatoMenor9 = findViewById(R.id.porcelanatoMenor9);
        valorRevestimentoPorcelanatoMenor9_1 = findViewById(R.id.porcelanatoMenor9_1);
        valorRevestimentoPorcelanatoMenor10 = findViewById(R.id.porcelanatoMenor10);
        valorRevestimentoPorcelanatoMenor10_1 = findViewById(R.id.porcelanatoMenor10_1);
        valorRevestimentoPorcelanatoMenor11 = findViewById(R.id.porcelanatoMenor11);
        valorRevestimentoPorcelanatoMenor11_1 = findViewById(R.id.porcelanatoMenor11_1);


        valorRevestimentoPastilhaVidro1 = findViewById(R.id.pastilhaVidro);
        valorRevestimentoPastilhaVidro1_1 = findViewById(R.id.pastilhaVidro_1);
        valorRevestimentoPastilhaVidro2 = findViewById(R.id.pastilhaVidro_2);
        valorRevestimentoPastilhaVidro2_1 = findViewById(R.id.pastilhaVidro_2_1);
        valorRevestimentoPastilhaVidro3 = findViewById(R.id.pastilhaVidro_3);
        valorRevestimentoPastilhaVidro3_1 = findViewById(R.id.pastilhaVidro_3_1);
        valorRevestimentoPastilhaVidro4 = findViewById(R.id.pastilhaVidro_4);
        valorRevestimentoPastilhaVidro4_1 = findViewById(R.id.pastilhaVidro_4_1);
        valorRevestimentoPastilhaVidro5 = findViewById(R.id.pastilhaVidro_5);
        valorRevestimentoPastilhaVidro5_1 = findViewById(R.id.pastilhaVidro_5_1);
        valorRevestimentoPastilhaVidro6 = findViewById(R.id.pastilhaVidro_6);
        valorRevestimentoPastilhaVidro6_1 = findViewById(R.id.pastilhaVidro_6_1);
        valorRevestimentoPastilhaVidro7 = findViewById(R.id.pastilhaVidro_7);
        valorRevestimentoPastilhaVidro7_1 = findViewById(R.id.pastilhaVidro_7_1);
        valorRevestimentoPastilhaVidro8 = findViewById(R.id.pastilhaVidro_8);
        valorRevestimentoPastilhaVidro8_1 = findViewById(R.id.pastilhaVidro_8_1);
        valorRevestimentoPastilhaVidro9 = findViewById(R.id.pastilhaVidro_9);
        valorRevestimentoPastilhaVidro9_1 = findViewById(R.id.pastilhaVidro_9_1);
        valorRevestimentoPastilhaVidro10 = findViewById(R.id.pastilhaVidro_10);
        valorRevestimentoPastilhaVidro10_1 = findViewById(R.id.pastilhaVidro_10_1);
        valorRevestimentoPastilhaVidro11 = findViewById(R.id.pastilhaVidro_11);
        valorRevestimentoPastilhaVidro11_1 = findViewById(R.id.pastilhaVidro_11_1);

        valorRevestimento3D1 = findViewById(R.id.revestimento3D);
        valorRevestimento3D1_1 = findViewById(R.id.revestimento3D_1);
        valorRevestimento3D2 = findViewById(R.id.revestimento3D_2);
        valorRevestimento3D2_1 = findViewById(R.id.revestimento3D_2_1);
        valorRevestimento3D3 = findViewById(R.id.revestimento3D_3);
        valorRevestimento3D3_1 = findViewById(R.id.revestimento3D_3_1);
        valorRevestimento3D4 = findViewById(R.id.revestimento3D_4);
        valorRevestimento3D4_1 = findViewById(R.id.revestimento3D_4_1);
        valorRevestimento3D5 = findViewById(R.id.revestimento3D_5);
        valorRevestimento3D5_1 = findViewById(R.id.revestimento3D_5_1);
        valorRevestimento3D6 = findViewById(R.id.revestimento3D_6);
        valorRevestimento3D6_1 = findViewById(R.id.revestimento3D_6_1);
        valorRevestimento3D7 = findViewById(R.id.revestimento3D_7);
        valorRevestimento3D7_1 = findViewById(R.id.revestimento3D_7_1);
        valorRevestimento3D8 = findViewById(R.id.revestimento3D_8);
        valorRevestimento3D8_1 = findViewById(R.id.revestimento3D_8_1);
        valorRevestimento3D9 = findViewById(R.id.revestimento3D_9);
        valorRevestimento3D9_1 = findViewById(R.id.revestimento3D_9_1);
        valorRevestimento3D10 = findViewById(R.id.revestimento3D_10);
        valorRevestimento3D10_1 = findViewById(R.id.revestimento3D_10_1);
        valorRevestimento3D11 = findViewById(R.id.revestimento3D_11);
        valorRevestimento3D11_1 = findViewById(R.id.revestimento3D_11_1);


        //Valores Pintura

        valorPinturaPorta1 = findViewById(R.id.pinturaPorta);
        valorPinturaPorta1_1 = findViewById(R.id.pinturaPorta1);
        valorPinturaPorta2 = findViewById(R.id.pinturaPorta2);
        valorPinturaPorta2_1 = findViewById(R.id.pinturaPorta2_1);
        valorPinturaPorta3 = findViewById(R.id.pinturaPorta3);
        valorPinturaPorta3_1 = findViewById(R.id.pinturaPorta3_1);
        valorPinturaPorta4 = findViewById(R.id.pinturaPorta4);
        valorPinturaPorta4_1 = findViewById(R.id.pinturaPorta4_1);
        valorPinturaPorta5 = findViewById(R.id.pinturaPorta5);
        valorPinturaPorta5_1 = findViewById(R.id.pinturaPorta5_1);
        valorPinturaPorta6 = findViewById(R.id.pinturaPorta6);
        valorPinturaPorta6_1 = findViewById(R.id.pinturaPorta6_1);
        valorPinturaPorta7 = findViewById(R.id.pinturaPorta7);
        valorPinturaPorta7_1 = findViewById(R.id.pinturaPorta7_1);
        valorPinturaPorta8 = findViewById(R.id.pinturaPorta8);
        valorPinturaPorta8_1 = findViewById(R.id.pinturaPorta8_1);
        valorPinturaPorta9 = findViewById(R.id.pinturaPorta9);
        valorPinturaPorta9_1 = findViewById(R.id.pinturaPorta9_1);
        valorPinturaPorta10 = findViewById(R.id.pinturaPorta10);
        valorPinturaPorta10_1 = findViewById(R.id.pinturaPorta10_1);
        valorPinturaPorta11 = findViewById(R.id.pinturaPorta11);
        valorPinturaPorta11_1 = findViewById(R.id.pinturaPorta11_1);

        valorPinturaJanela1 = findViewById(R.id.pinturaJanela);
        valorPinturaJanela1_1 = findViewById(R.id.pinturaJanela1);
        valorPinturaJanela2 = findViewById(R.id.pinturaJanela2);
        valorPinturaJanela2_1 = findViewById(R.id.pinturaJanela2_1);
        valorPinturaJanela3 = findViewById(R.id.pinturaJanela3);
        valorPinturaJanela3_1 = findViewById(R.id.pinturaJanela3_2);
        valorPinturaJanela4 = findViewById(R.id.pinturaJanela4);
        valorPinturaJanela4_1 = findViewById(R.id.pinturaJanela4_1);
        valorPinturaJanela5 = findViewById(R.id.pinturaJanela5);
        valorPinturaJanela5_1 = findViewById(R.id.pinturaJanela5_1);
        valorPinturaJanela6 = findViewById(R.id.pinturaJanela6);
        valorPinturaJanela6_1 = findViewById(R.id.pinturaJanela6_1);
        valorPinturaJanela7 = findViewById(R.id.pinturaJanela7);
        valorPinturaJanela7_1 = findViewById(R.id.pinturaJanela7_1);
        valorPinturaJanela8 = findViewById(R.id.pinturaJanela8);
        valorPinturaJanela8_1 = findViewById(R.id.pinturaJanela8_1);
        valorPinturaJanela9 = findViewById(R.id.pinturaJanela9);
        valorPinturaJanela9_1 = findViewById(R.id.pinturaJanela9_1);
        valorPinturaJanela10 = findViewById(R.id.pinturaJanela10);
        valorPinturaJanela10_1 = findViewById(R.id.pinturaJanela10_1);
        valorPinturaJanela11 = findViewById(R.id.pinturaJanela11);
        valorPinturaJanela11_1 = findViewById(R.id.pinturaJanela11_1);


        valorPinturaEfeitoDecorativo1 = findViewById(R.id.pinturaEfeitoDecorativo);
        valorPinturaEfeitoDecorativo1_1 = findViewById(R.id.pinturaEfeitoDecorativo1);
        valorPinturaEfeitoDecorativo2 = findViewById(R.id.pinturaEfeitoDecorativo2);
        valorPinturaEfeitoDecorativo2_1 = findViewById(R.id.pinturaEfeitoDecorativo2_1);
        valorPinturaEfeitoDecorativo3 = findViewById(R.id.pinturaEfeitoDecorativo3);
        valorPinturaEfeitoDecorativo3_1 = findViewById(R.id.pinturaEfeitoDecorativo3_1);
        valorPinturaEfeitoDecorativo4 = findViewById(R.id.pinturaEfeitoDecorativo4);
        valorPinturaEfeitoDecorativo4_1 = findViewById(R.id.pinturaEfeitoDecorativo4_1);
        valorPinturaEfeitoDecorativo5 = findViewById(R.id.pinturaEfeitoDecorativo5);
        valorPinturaEfeitoDecorativo5_1 = findViewById(R.id.pinturaEfeitoDecorativo5_1);
        valorPinturaEfeitoDecorativo6 = findViewById(R.id.pinturaEfeitoDecorativo6);
        valorPinturaEfeitoDecorativo6_1 = findViewById(R.id.pinturaEfeitoDecorativo6_1);
        valorPinturaEfeitoDecorativo7 = findViewById(R.id.pinturaEfeitoDecorativo7);
        valorPinturaEfeitoDecorativo7_1 = findViewById(R.id.pinturaEfeitoDecorativo7_1);
        valorPinturaEfeitoDecorativo8 = findViewById(R.id.pinturaEfeitoDecorativo8);
        valorPinturaEfeitoDecorativo8_1 = findViewById(R.id.pinturaEfeitoDecorativo8_1);
        valorPinturaEfeitoDecorativo9 = findViewById(R.id.pinturaEfeitoDecorativo9);
        valorPinturaEfeitoDecorativo9_1 = findViewById(R.id.pinturaEfeitoDecorativo9_1);
        valorPinturaEfeitoDecorativo10 = findViewById(R.id.pinturaEfeitoDecorativo10);
        valorPinturaEfeitoDecorativo10_1 = findViewById(R.id.pinturaEfeitoDecorativo10_1);
        valorPinturaEfeitoDecorativo11 = findViewById(R.id.pinturaEfeitoDecorativo11);
        valorPinturaEfeitoDecorativo11_1 = findViewById(R.id.pinturaEfeitoDecorativo11_1);

        valorPinturaReparoGesso1 = findViewById(R.id.pinturaReparoGesso);
        valorPinturaReparoGesso1_1 = findViewById(R.id.pinturaReparoGesso1);
        valorPinturaReparoGesso2 = findViewById(R.id.pinturaReparoGesso2);
        valorPinturaReparoGesso2_1 = findViewById(R.id.pinturaReparoGesso2_1);
        valorPinturaReparoGesso3 = findViewById(R.id.pinturaReparoGesso3);
        valorPinturaReparoGesso3_1 = findViewById(R.id.pinturaReparoGesso2_3);
        valorPinturaReparoGesso4 = findViewById(R.id.pinturaReparoGesso4);
        valorPinturaReparoGesso4_1 = findViewById(R.id.pinturaReparoGesso4_1);
        valorPinturaReparoGesso5 = findViewById(R.id.pinturaReparoGesso5);
        valorPinturaReparoGesso5_1 = findViewById(R.id.pinturaReparoGesso5_1);
        valorPinturaReparoGesso6 = findViewById(R.id.pinturaReparoGesso6);
        valorPinturaReparoGesso6_1 = findViewById(R.id.pinturaReparoGesso6_1);
        valorPinturaReparoGesso7 = findViewById(R.id.pinturaReparoGesso7);
        valorPinturaReparoGesso7_1 = findViewById(R.id.pinturaReparoGesso7_1);
        valorPinturaReparoGesso8 = findViewById(R.id.pinturaReparoGesso8);
        valorPinturaReparoGesso8_1 = findViewById(R.id.pinturaReparoGesso8_1);
        valorPinturaReparoGesso9 = findViewById(R.id.pinturaReparoGesso9);
        valorPinturaReparoGesso9_1 = findViewById(R.id.pinturaReparoGesso9_1);
        valorPinturaReparoGesso10 = findViewById(R.id.pinturaReparoGesso10);
        valorPinturaReparoGesso10_1 = findViewById(R.id.pinturaReparoGesso10_1);
        valorPinturaReparoGesso11 = findViewById(R.id.pinturaReparoGesso11);
        valorPinturaReparoGesso11_1 = findViewById(R.id.pinturaReparoGesso11_1);


        //Hidraulica

        valorHidraulicaTorneiraEletrica1 = findViewById(R.id.torneiraEletrica);
        valorHidraulicaTorneiraEletrica1_1 = findViewById(R.id.torneiraEletrica1);
        valorHidraulicaTorneiraEletrica2 = findViewById(R.id.torneiraEletrica2);
        valorHidraulicaTorneiraEletrica2_1 = findViewById(R.id.torneiraEletrica2_1);
        valorHidraulicaTorneiraEletrica3 = findViewById(R.id.torneiraEletrica3);
        valorHidraulicaTorneiraEletrica3_1 = findViewById(R.id.torneiraEletrica3_1);
        valorHidraulicaTorneiraEletrica4 = findViewById(R.id.torneiraEletrica4);
        valorHidraulicaTorneiraEletrica4_1 = findViewById(R.id.torneiraEletrica4_1);
        valorHidraulicaTorneiraEletrica5 = findViewById(R.id.torneiraEletrica5);
        valorHidraulicaTorneiraEletrica5_1 = findViewById(R.id.torneiraEletrica5_1);
        valorHidraulicaTorneiraEletrica6 = findViewById(R.id.torneiraEletrica6);
        valorHidraulicaTorneiraEletrica6_1 = findViewById(R.id.torneiraEletrica6_1);
        valorHidraulicaTorneiraEletrica7 = findViewById(R.id.torneiraEletrica7);
        valorHidraulicaTorneiraEletrica7_1 = findViewById(R.id.torneiraEletrica7_1);
        valorHidraulicaTorneiraEletrica8 = findViewById(R.id.torneiraEletrica8);
        valorHidraulicaTorneiraEletrica8_1 = findViewById(R.id.torneiraEletrica8_1);
        valorHidraulicaTorneiraEletrica9 = findViewById(R.id.torneiraEletrica9);
        valorHidraulicaTorneiraEletrica9_1 = findViewById(R.id.torneiraEletrica9_1);
        valorHidraulicaTorneiraEletrica10 = findViewById(R.id.torneiraEletrica10);
        valorHidraulicaTorneiraEletrica10_1 = findViewById(R.id.torneiraEletrica10_1);
        valorHidraulicaTorneiraEletrica11 = findViewById(R.id.torneiraEletrica11);
        valorHidraulicaTorneiraEletrica11_1 = findViewById(R.id.torneiraEletrica11_1);


        valorHidraulicaTorneiraSimples1 = findViewById(R.id.torneiraSimples);
        valorHidraulicaTorneiraSimples1_1 = findViewById(R.id.torneiraSimples1);
        valorHidraulicaTorneiraSimples2 = findViewById(R.id.torneiraSimples2);
        valorHidraulicaTorneiraSimples2_1 = findViewById(R.id.torneiraSimples2_1);
        valorHidraulicaTorneiraSimples3 = findViewById(R.id.torneiraSimples3);
        valorHidraulicaTorneiraSimples3_1 = findViewById(R.id.torneiraSimples3_1);
        valorHidraulicaTorneiraSimples4 = findViewById(R.id.torneiraSimples4);
        valorHidraulicaTorneiraSimples4_1 = findViewById(R.id.torneiraSimples4_1);
        valorHidraulicaTorneiraSimples5 = findViewById(R.id.torneiraSimples5);
        valorHidraulicaTorneiraSimples5_1 = findViewById(R.id.torneiraSimples5_1);
        valorHidraulicaTorneiraSimples6 = findViewById(R.id.torneiraSimples6);
        valorHidraulicaTorneiraSimples6_1 = findViewById(R.id.torneiraSimples6_1);
        valorHidraulicaTorneiraSimples7 = findViewById(R.id.torneiraSimples7);
        valorHidraulicaTorneiraSimples7_1 = findViewById(R.id.torneiraSimples7_1);
        valorHidraulicaTorneiraSimples8 = findViewById(R.id.torneiraSimples8);
        valorHidraulicaTorneiraSimples8_1 = findViewById(R.id.torneiraSimples8_1);
        valorHidraulicaTorneiraSimples9 = findViewById(R.id.torneiraSimples9);
        valorHidraulicaTorneiraSimples9_1 = findViewById(R.id.torneiraSimples9_1);
        valorHidraulicaTorneiraSimples10 = findViewById(R.id.torneiraSimples10);
        valorHidraulicaTorneiraSimples10_1 = findViewById(R.id.torneiraSimples10_1);
        valorHidraulicaTorneiraSimples11 = findViewById(R.id.torneiraSimples11);
        valorHidraulicaTorneiraSimples11_1 = findViewById(R.id.torneiraSimples11_1);


        valorHidraulicaTorneiraMonocomando1 = findViewById(R.id.torneiraMonocomando);
        valorHidraulicaTorneiraMonocomando1_1 = findViewById(R.id.torneiraMonocomando1);
        valorHidraulicaTorneiraMonocomando2 = findViewById(R.id.torneiraMonocomando2);
        valorHidraulicaTorneiraMonocomando2_1 = findViewById(R.id.torneiraMonocomando2_1);
        valorHidraulicaTorneiraMonocomando3 = findViewById(R.id.torneiraMonocomando3);
        valorHidraulicaTorneiraMonocomando3_1 = findViewById(R.id.torneiraMonocomando3_2);
        valorHidraulicaTorneiraMonocomando4 = findViewById(R.id.torneiraMonocomando4);
        valorHidraulicaTorneiraMonocomando4_1 = findViewById(R.id.torneiraMonocomando4_1);
        valorHidraulicaTorneiraMonocomando5 = findViewById(R.id.torneiraMonocomando5);
        valorHidraulicaTorneiraMonocomando5_1 = findViewById(R.id.torneiraMonocomando5_1);
        valorHidraulicaTorneiraMonocomando6 = findViewById(R.id.torneiraMonocomando6);
        valorHidraulicaTorneiraMonocomando6_1 = findViewById(R.id.torneiraMonocomando6_1);
        valorHidraulicaTorneiraMonocomando7 = findViewById(R.id.torneiraMonocomando7);
        valorHidraulicaTorneiraMonocomando7_1 = findViewById(R.id.torneiraMonocomando7_1);
        valorHidraulicaTorneiraMonocomando8 = findViewById(R.id.torneiraMonocomando8);
        valorHidraulicaTorneiraMonocomando8_1 = findViewById(R.id.torneiraMonocomando8_1);
        valorHidraulicaTorneiraMonocomando9 = findViewById(R.id.torneiraMonocomando9);
        valorHidraulicaTorneiraMonocomando9_1 = findViewById(R.id.torneiraMonocomando9_1);
        valorHidraulicaTorneiraMonocomando10 = findViewById(R.id.torneiraMonocomando10);
        valorHidraulicaTorneiraMonocomando10_1 = findViewById(R.id.torneiraMonocomando10_1);
        valorHidraulicaTorneiraMonocomando11 = findViewById(R.id.torneiraMonocomando11);
        valorHidraulicaTorneiraMonocomando11_1 = findViewById(R.id.torneiraMonocomando11_1);


        valorHidraulicaValvulaSifao1 = findViewById(R.id.valvulaSifao);
        valorHidraulicaValvulaSifao1_1 = findViewById(R.id.valvulaSifao1);
        valorHidraulicaValvulaSifao2 = findViewById(R.id.valvulaSifao2);
        valorHidraulicaValvulaSifao2_1 = findViewById(R.id.valvulaSifao2_1);
        valorHidraulicaValvulaSifao3 = findViewById(R.id.valvulaSifao3);
        valorHidraulicaValvulaSifao3_1 = findViewById(R.id.valvulaSifao2_3);
        valorHidraulicaValvulaSifao4 = findViewById(R.id.valvulaSifao4);
        valorHidraulicaValvulaSifao4_1 = findViewById(R.id.valvulaSifao4_1);
        valorHidraulicaValvulaSifao5 = findViewById(R.id.valvulaSifao5);
        valorHidraulicaValvulaSifao5_1 = findViewById(R.id.valvulaSifao5_1);
        valorHidraulicaValvulaSifao6 = findViewById(R.id.valvulaSifao6);
        valorHidraulicaValvulaSifao6_1 = findViewById(R.id.valvulaSifao6_1);
        valorHidraulicaValvulaSifao7 = findViewById(R.id.valvulaSifao7);
        valorHidraulicaValvulaSifao7_1 = findViewById(R.id.valvulaSifao7_1);
        valorHidraulicaValvulaSifao8 = findViewById(R.id.valvulaSifao8);
        valorHidraulicaValvulaSifao8_1 = findViewById(R.id.valvulaSifao8_1);
        valorHidraulicaValvulaSifao9 = findViewById(R.id.valvulaSifao9);
        valorHidraulicaValvulaSifao9_1 = findViewById(R.id.valvulaSifao9_1);
        valorHidraulicaValvulaSifao10 = findViewById(R.id.valvulaSifao10);
        valorHidraulicaValvulaSifao10_1 = findViewById(R.id.valvulaSifao10_1);
        valorHidraulicaValvulaSifao11 = findViewById(R.id.valvulaSifao11);
        valorHidraulicaValvulaSifao11_1 = findViewById(R.id.valvulaSifao11_1);


        valorHidraulicaRegistroAcabamento1 = findViewById(R.id.registrosAcabamento);
        valorHidraulicaRegistroAcabamento1_1 = findViewById(R.id.registrosAcabamento1);
        valorHidraulicaRegistroAcabamento2 = findViewById(R.id.registrosAcabamento2);
        valorHidraulicaRegistroAcabamento2_1 = findViewById(R.id.registrosAcabamento2_1);
        valorHidraulicaRegistroAcabamento3 = findViewById(R.id.registrosAcabamento3);
        valorHidraulicaRegistroAcabamento3_1 = findViewById(R.id.registrosAcabamento2_3);
        valorHidraulicaRegistroAcabamento4 = findViewById(R.id.registrosAcabamento4);
        valorHidraulicaRegistroAcabamento4_1 = findViewById(R.id.registrosAcabamento4_1);
        valorHidraulicaRegistroAcabamento5 = findViewById(R.id.registrosAcabamento5);
        valorHidraulicaRegistroAcabamento5_1 = findViewById(R.id.registrosAcabamento5_1);
        valorHidraulicaRegistroAcabamento6 = findViewById(R.id.registrosAcabamento6);
        valorHidraulicaRegistroAcabamento6_1 = findViewById(R.id.registrosAcabamento6_1);
        valorHidraulicaRegistroAcabamento7 = findViewById(R.id.registrosAcabamento7);
        valorHidraulicaRegistroAcabamento7_1 = findViewById(R.id.registrosAcabamento7_1);
        valorHidraulicaRegistroAcabamento8 = findViewById(R.id.registrosAcabamento8);
        valorHidraulicaRegistroAcabamento8_1 = findViewById(R.id.registrosAcabamento8_1);
        valorHidraulicaRegistroAcabamento9 = findViewById(R.id.registrosAcabamento9);
        valorHidraulicaRegistroAcabamento9_1 = findViewById(R.id.registrosAcabamento9_1);
        valorHidraulicaRegistroAcabamento10 = findViewById(R.id.registrosAcabamento10);
        valorHidraulicaRegistroAcabamento10_1 = findViewById(R.id.registrosAcabamento10_1);
        valorHidraulicaRegistroAcabamento11 = findViewById(R.id.registrosAcabamento11);
        valorHidraulicaRegistroAcabamento11_1 = findViewById(R.id.registrosAcabamento11_1);


        valorHidraulicaCriacaoAgua1 = findViewById(R.id.criacaoPontoAgua);
        valorHidraulicaCriacaoAgua1_1 = findViewById(R.id.criacaoPontoAgua_1);
        valorHidraulicaCriacaoAgua2 = findViewById(R.id.criacaoPontoAgua_2);
        valorHidraulicaCriacaoAgua2_1 = findViewById(R.id.criacaoPontoAgua_2_1);
        valorHidraulicaCriacaoAgua3 = findViewById(R.id.criacaoPontoAgua_3);
        valorHidraulicaCriacaoAgua3_1 = findViewById(R.id.criacaoPontoAgua_3_1);
        valorHidraulicaCriacaoAgua4 = findViewById(R.id.criacaoPontoAgua_4);
        valorHidraulicaCriacaoAgua4_1 = findViewById(R.id.criacaoPontoAgua_4_1);
        valorHidraulicaCriacaoAgua5 = findViewById(R.id.criacaoPontoAgua_5);
        valorHidraulicaCriacaoAgua5_1 = findViewById(R.id.criacaoPontoAgua_5_1);
        valorHidraulicaCriacaoAgua6 = findViewById(R.id.criacaoPontoAgua_6);
        valorHidraulicaCriacaoAgua6_1 = findViewById(R.id.criacaoPontoAgua_6_1);
        valorHidraulicaCriacaoAgua7 = findViewById(R.id.criacaoPontoAgua_7);
        valorHidraulicaCriacaoAgua7_1 = findViewById(R.id.criacaoPontoAgua_7_1);
        valorHidraulicaCriacaoAgua8 = findViewById(R.id.criacaoPontoAgua_8);
        valorHidraulicaCriacaoAgua8_1 = findViewById(R.id.criacaoPontoAgua_8_1);
        valorHidraulicaCriacaoAgua9 = findViewById(R.id.criacaoPontoAgua_9);
        valorHidraulicaCriacaoAgua9_1 = findViewById(R.id.criacaoPontoAgua_9_1);
        valorHidraulicaCriacaoAgua10 = findViewById(R.id.criacaoPontoAgua_10);
        valorHidraulicaCriacaoAgua10_1 = findViewById(R.id.criacaoPontoAgua_10_1);
        valorHidraulicaCriacaoAgua11 = findViewById(R.id.criacaoPontoAgua_11);
        valorHidraulicaCriacaoAgua11_1 = findViewById(R.id.criacaoPontoAgua_11_1);

        valorHidraulicaCriacaoEsgoto1 = findViewById(R.id.criacaoPontoEsgoto);
        valorHidraulicaCriacaoEsgoto1_1 = findViewById(R.id.criacaoPontoEsgoto_1);
        valorHidraulicaCriacaoEsgoto2 = findViewById(R.id.criacaoPontoEsgoto_2);
        valorHidraulicaCriacaoEsgoto2_1 = findViewById(R.id.criacaoPontoEsgoto_2_1);
        valorHidraulicaCriacaoEsgoto3 = findViewById(R.id.criacaoPontoEsgoto_3);
        valorHidraulicaCriacaoEsgoto3_1 = findViewById(R.id.criacaoPontoEsgoto_3_1);
        valorHidraulicaCriacaoEsgoto4 = findViewById(R.id.criacaoPontoEsgoto_4);
        valorHidraulicaCriacaoEsgoto4_1 = findViewById(R.id.criacaoPontoEsgoto_4_1);
        valorHidraulicaCriacaoEsgoto5 = findViewById(R.id.criacaoPontoEsgoto_5);
        valorHidraulicaCriacaoEsgoto5_1 = findViewById(R.id.criacaoPontoEsgoto_5_1);
        valorHidraulicaCriacaoEsgoto6 = findViewById(R.id.criacaoPontoEsgoto_6);
        valorHidraulicaCriacaoEsgoto6_1 = findViewById(R.id.criacaoPontoEsgoto_6_1);
        valorHidraulicaCriacaoEsgoto7 = findViewById(R.id.criacaoPontoEsgoto_7);
        valorHidraulicaCriacaoEsgoto7_1 = findViewById(R.id.criacaoPontoEsgoto_7_1);
        valorHidraulicaCriacaoEsgoto8 = findViewById(R.id.criacaoPontoEsgoto_8);
        valorHidraulicaCriacaoEsgoto8_1 = findViewById(R.id.criacaoPontoEsgoto_8_1);
        valorHidraulicaCriacaoEsgoto9 = findViewById(R.id.criacaoPontoEsgoto_9);
        valorHidraulicaCriacaoEsgoto9_1 = findViewById(R.id.criacaoPontoEsgoto_9_1);
        valorHidraulicaCriacaoEsgoto10 = findViewById(R.id.criacaoPontoEsgoto_10);
        valorHidraulicaCriacaoEsgoto10_1 = findViewById(R.id.criacaoPontoEsgoto_10_1);
        valorHidraulicaCriacaoEsgoto11 = findViewById(R.id.criacaoPontoEsgoto_11);
        valorHidraulicaCriacaoEsgoto11_1 = findViewById(R.id.criacaoPontoEsgoto_11_1);


        valorHidraulicaRalo10cm1 = findViewById(R.id.instalacaoRalo10cm);
        valorHidraulicaRalo10cm1_1 = findViewById(R.id.instalacaoRalo10cm1);
        valorHidraulicaRalo10cm2 = findViewById(R.id.instalacaoRalo10cm2);
        valorHidraulicaRalo10cm2_1 = findViewById(R.id.instalacaoRalo10cm2_1);
        valorHidraulicaRalo10cm3 = findViewById(R.id.instalacaoRalo10cm3);
        valorHidraulicaRalo10cm3_1 = findViewById(R.id.instalacaoRalo10cm3_1);
        valorHidraulicaRalo10cm4 = findViewById(R.id.instalacaoRalo10cm4);
        valorHidraulicaRalo10cm4_1 = findViewById(R.id.instalacaoRalo10cm4_1);
        valorHidraulicaRalo10cm5 = findViewById(R.id.instalacaoRalo10cm5);
        valorHidraulicaRalo10cm5_1 = findViewById(R.id.instalacaoRalo10cm5_1);
        valorHidraulicaRalo10cm6 = findViewById(R.id.instalacaoRalo10cm6);
        valorHidraulicaRalo10cm6_1 = findViewById(R.id.instalacaoRalo10cm6_1);
        valorHidraulicaRalo10cm7 = findViewById(R.id.instalacaoRalo10cm7);
        valorHidraulicaRalo10cm7_1 = findViewById(R.id.instalacaoRalo10cm7_1);
        valorHidraulicaRalo10cm8 = findViewById(R.id.instalacaoRalo10cm8);
        valorHidraulicaRalo10cm8_1 = findViewById(R.id.instalacaoRalo10cm8_1);
        valorHidraulicaRalo10cm9 = findViewById(R.id.instalacaoRalo10cm9);
        valorHidraulicaRalo10cm9_1 = findViewById(R.id.instalacaoRalo10cm9_1);
        valorHidraulicaRalo10cm10 = findViewById(R.id.instalacaoRalo10cm10);
        valorHidraulicaRalo10cm10_1 = findViewById(R.id.instalacaoRalo10cm10_1);
        valorHidraulicaRalo10cm11 = findViewById(R.id.instalacaoRalo10cm11);
        valorHidraulicaRalo10cm11_1 = findViewById(R.id.instalacaoRalo10cm11_1);


        valorHidraulicaRalo15cm1 = findViewById(R.id.instalacaoRalo15cm);
        valorHidraulicaRalo15cm1_1 = findViewById(R.id.instalacaoRalo15cm1);
        valorHidraulicaRalo15cm2 = findViewById(R.id.instalacaoRalo15cm2);
        valorHidraulicaRalo15cm2_1 = findViewById(R.id.instalacaoRalo15cm2_1);
        valorHidraulicaRalo15cm3 = findViewById(R.id.instalacaoRalo15cm3);
        valorHidraulicaRalo15cm3_1 = findViewById(R.id.instalacaoRalo15cm3_1);
        valorHidraulicaRalo15cm4 = findViewById(R.id.instalacaoRalo15cm4);
        valorHidraulicaRalo15cm4_1 = findViewById(R.id.instalacaoRalo15cm4_1);
        valorHidraulicaRalo15cm5 = findViewById(R.id.instalacaoRalo15cm5);
        valorHidraulicaRalo15cm5_1 = findViewById(R.id.instalacaoRalo15cm5_1);
        valorHidraulicaRalo15cm6 = findViewById(R.id.instalacaoRalo15cm6);
        valorHidraulicaRalo15cm6_1 = findViewById(R.id.instalacaoRalo15cm6_1);
        valorHidraulicaRalo15cm7 = findViewById(R.id.instalacaoRalo15cm7);
        valorHidraulicaRalo15cm7_1 = findViewById(R.id.instalacaoRalo15cm7_1);
        valorHidraulicaRalo15cm8 = findViewById(R.id.instalacaoRalo15cm8);
        valorHidraulicaRalo15cm8_1 = findViewById(R.id.instalacaoRalo15cm8_1);
        valorHidraulicaRalo15cm9 = findViewById(R.id.instalacaoRalo15cm9);
        valorHidraulicaRalo15cm9_1 = findViewById(R.id.instalacaoRalo15cm9_1);
        valorHidraulicaRalo15cm10 = findViewById(R.id.instalacaoRalo15cm10);
        valorHidraulicaRalo15cm10_1 = findViewById(R.id.instalacaoRalo15cm10_1);
        valorHidraulicaRalo15cm11 = findViewById(R.id.instalacaoRalo15cm11);
        valorHidraulicaRalo15cm11_1 = findViewById(R.id.instalacaoRalo15cm11_1);


        valorHidraulicaChuveiro2 = findViewById(R.id.chuveiro2);
        valorHidraulicaChuveiro2_1 = findViewById(R.id.chuveiro2_1);
        valorHidraulicaChuveiro4 = findViewById(R.id.chuveiro4);
        valorHidraulicaChuveiro4_1 = findViewById(R.id.chuveiro4_1);
        valorHidraulicaChuveiro5 = findViewById(R.id.chuveiro4_1);


        valorHidraulicaInstalarVasoSanitario2 = findViewById(R.id.instalacaoVasoSanitario2);
        valorHidraulicaInstalarVasoSanitario2_1 = findViewById(R.id.instalacaoVasoSanitario2_1);
        valorHidraulicaInstalarVasoSanitario4 = findViewById(R.id.instalacaoVasoSanitario4);
        valorHidraulicaInstalarVasoSanitario4_1 = findViewById(R.id.instalacaoVasoSanitario4_1);
        valorHidraulicaInstalarVasoSanitario5 = findViewById(R.id.instalarVaso5);
        valorHidraulicaInstalarVasoSanitario5_1 = findViewById(R.id.instalarVaso5_1);


        valorHidraulicaRaloLinear2 = findViewById(R.id.instalacaoRaloLinear2);
        valorHidraulicaRaloLinear2_1 = findViewById(R.id.instalacaoRaloLinear2_1);
        valorHidraulicaRaloLinear4 = findViewById(R.id.instalacaoRaloLinear4);
        valorHidraulicaRaloLinear4_1 = findViewById(R.id.instalacaoRaloLinear4_1);
        valorHidraulicaRaloLinear5 = findViewById(R.id.instalarRaloLinear5);
        valorHidraulicaRaloLinear5_1 = findViewById(R.id.instalarRaloLinear5_1);


        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, camera.class);
                startActivity(intent);

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
                    checkBoxPinturaQuartoSuite.setChecked(false);
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
                } else if (isChecked == false) {
                    linearLayoutPintura11.setVisibility(View.GONE);
                    checkBoxPinturaQuartoSuite.setBackgroundResource(R.drawable.btn_servico1);

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

    private void createPdf() throws FileNotFoundException, DocumentException {


        numeroNotaAtual = numNota + 1;
        SharedPreferences mypref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mypref.edit();
        editor.putInt("numeroNota", numeroNotaAtual);
        editor.commit();
        alterarNumeroNota = Integer.toString(numeroNotaAtual);
        exibirNota.setText("000" + alterarNumeroNota);


        Date now = new Date();
        android.text.format.DateFormat.format("dd-MM-yyyy_hh:mm:ss", now);

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RelatóriosRagonezi/Orcamentos");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            // Log.i(TAG, "Created a new directory for PDF");
        }


        Font fonteEndereco = FontFactory.getFont("Times-Roman", 14, Font.NORMAL);
        Font fonteOrcamento = FontFactory.getFont("Times-Roman", 13, Font.NORMAL);
        Font bold = FontFactory.getFont("Times-Roman, Bold", 15, Font.BOLD);
        Font boldTitulo = FontFactory.getFont("Times-Roman, Bold", 22, Font.BOLD);
        Font boldTitulo2 = FontFactory.getFont("Times-Roman, Bold", 16, Font.BOLD);
        Font boldTotal = FontFactory.getFont("Times-Roman, Bold", 17, Font.BOLD);
        Font boldTota2 = FontFactory.getFont("Times-Roman, Bold", 17, Font.NORMAL);
        Font boldServicos = FontFactory.getFont("Times-Roman, Bold", 14, Font.NORMAL);
        Font boldServicosPrestados = FontFactory.getFont("Times-Roman, Bold", 12, Font.NORMAL);
        Font fontData = FontFactory.getFont("Times-Roman, Bold", 10, Font.BOLD);
        pdfFile = new File(docsFolder.getAbsolutePath(), "Orcamento" + "000" + alterarNumeroNota + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();


        //Adicionando Logo
        Drawable d = getResources().getDrawable(R.drawable.logo_branca_th);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 100, 50, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        Image image = null;
        try {
            image = Image.getInstance(bitmapData);
            image.setAlignment(Element.ALIGN_CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Data
        Paragraph dataParagrafo = new Paragraph(String.valueOf(now), fontData);
        dataParagrafo.setAlignment(Element.ALIGN_LEFT);
        Paragraph espacoBranco = new Paragraph("", boldTitulo2);
        Paragraph tituloParagrafo = new Paragraph("Orçamento", boldTitulo2);
        Paragraph paragrafoDemolicao = new Paragraph("Demolição", boldTitulo2);
        Paragraph paragrafoArt = new Paragraph("ART - Anotação de Responsabilidade Técnica ", boldTitulo2);
        paragrafoDemolicao.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragrafoRevestimento = new Paragraph("Revestimento", boldTitulo2);
        paragrafoRevestimento.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragrafoPintura = new Paragraph("Pintura", boldTitulo2);
        paragrafoPintura.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragrafoHidraulica = new Paragraph("Hidraulica\n", boldTitulo2);
        paragrafoHidraulica.setAlignment(Element.ALIGN_LEFT);


        //Paragrafo Demolicao
        Paragraph paragrafoCozinha = new Paragraph("Cozinha                                                                                    Total R$" + valorTotalCozinha + "0", boldServicos);
        Paragraph paragrafoBanheiro = new Paragraph("Banheiro                                                                                   Total R$" + valorTotalBanheiro1 + "0", boldServicos);
        Paragraph paragrafoAreaServico = new Paragraph("Área de Serviço                                                                            Total R$" + valorTotalAreaServico + "0", boldServicos);
        Paragraph paragrafoBanheiro2 = new Paragraph("Banheiro Suíte                                                                             Total R$" + valorTotalBanheiro2 + "0", boldServicos);
        Paragraph paragrafoLavabo = new Paragraph("Lavabo                                                                                     Total R$" + valorTotalLavabo + "0", boldServicos);
        Paragraph paragrafoSacadaVaranda = new Paragraph("Sacada Varanda                                                                             Total R$" + valorTotalSacadaVaranda + "0", boldServicos);
        Paragraph paragrafoSalaJantar = new Paragraph("Sala Jantar                                                                                Total R$" + valorTotalSalaJantar + "0", boldServicos);
        Paragraph paragrafoSalaEstar = new Paragraph("Sala Estar                                                                                 Total R$" + valorTotalSalaEstar + "0", boldServicos);
        Paragraph paragrafoQuarto1 = new Paragraph("Quarto 1                                                                                   Total R$" + valorTotalQuarto1 + "0", boldServicos);
        Paragraph paragrafoQuarto2 = new Paragraph("Quarto 2                                                                                   Total R$" + valorTotalQuarto2 + "0", boldServicos);
        Paragraph paragrafoQuarto3 = new Paragraph("Quarto Suíte                                                                               Total R$" + valorTotalQuarto3 + "0", boldServicos);


        //Paragrafo Revestimento
        Paragraph paragrafoRevestimentoCozinha = new Paragraph("Cozinha                                                                                    Total R$" + valorTotalRevestimentoCozinha + "0", boldServicos);
        Paragraph paragrafoRevestimentoBanheiro = new Paragraph("Banheiro                                                                                   Total R$" + valorTotalRevestimentoBanheiroSocial + "0", boldServicos);
        Paragraph paragrafoRevestimentoAreaServico = new Paragraph("Área de Serviço                                                                            Total R$" + valorTotalRevestimentoAreaServico + "0", boldServicos);
        Paragraph paragrafoRevestimentoBanheiro2 = new Paragraph("Banheiro Suíte                                                                             Total R$" + valorTotalRevestimentoBanheiroSuite + "0", boldServicos);
        Paragraph paragrafoRevestimentoLavabo = new Paragraph("Lavabo                                                                                     Total R$" + valorTotalRevestimentoLavabo + "0", boldServicos);
        Paragraph paragrafoRevestimentoSacadaVaranda = new Paragraph("Sacada Varanda                                                                             Total R$" + valorTotalRevestimentoSacada + "0", boldServicos);
        Paragraph paragrafoRevestimentoSalaJantar = new Paragraph("Sala Jantar                                                                                Total R$" + valorTotalRevestimentoSalaJantar + "0", boldServicos);
        Paragraph paragrafoRevestimentoSalaEstar = new Paragraph("Sala Estar                                                                                 Total R$" + valorTotalRevestimentoSalaEstar + "0", boldServicos);
        Paragraph paragrafoRevestimentoQuarto1 = new Paragraph("Quarto 1                                                                                   Total R$" + valorTotalRevestimentoQuarto1 + "0", boldServicos);
        Paragraph paragrafoRevestimentoQuarto2 = new Paragraph("Quarto 2                                                                                   Total R$" + valorTotalRevestimentoQuarto2 + "0", boldServicos);
        Paragraph paragrafoRevestimentoQuarto3 = new Paragraph("Quarto Suíte                                                                               Total R$" + valorTotalRevestimentoQuartoSuite + "0", boldServicos);


        //Paragrafo Pintura
        Paragraph paragrafoPinturaCozinha = new Paragraph("Cozinha                                                                                    Total R$" + valorTotalPinturaCozinha + "0", boldServicos);
        Paragraph paragrafoPinturaBanheiro = new Paragraph("Banheiro                                                                                   Total R$" + valorTotalPinturaBanheiroSocial + "0", boldServicos);
        Paragraph paragrafoPinturaAreaServico = new Paragraph("Área de Serviço                                                                            Total R$" + valorTotalPinturaAreaServico + "0", boldServicos);
        Paragraph paragrafoPinturaBanheiro2 = new Paragraph("Banheiro Suíte                                                                             Total R$" + valorTotalPinturaBanheiroSuite + "0", boldServicos);
        Paragraph paragrafoPinturaLavabo = new Paragraph("Lavabo                                                                                     Total R$" + valorTotalPinturaLavabo + "0", boldServicos);
        Paragraph paragrafoPinturaSacadaVaranda = new Paragraph("Sacada Varanda                                                                             Total R$" + valorTotalPinturaSacada + "0", boldServicos);
        Paragraph paragrafoPinturaSalaJantar = new Paragraph("Sala Jantar                                                                                Total R$" + valorTotalPinturaSalaJantar + "0", boldServicos);
        Paragraph paragrafoPinturaSalaEstar = new Paragraph("Sala Estar                                                                                 Total R$" + valorTotalPinturaSalaEstar + "0", boldServicos);
        Paragraph paragrafoPinturaQuarto1 = new Paragraph("Quarto 1                                                                                   Total R$" + valorTotalPinturaQuarto1 + "0", boldServicos);
        Paragraph paragrafoPinturaQuarto2 = new Paragraph("Quarto 2                                                                                   Total R$" + valorTotalPinturaQuarto2 + "0", boldServicos);
        Paragraph paragrafoPinturaQuarto3 = new Paragraph("Quarto Suíte                                                                               Total R$" + valorTotalPinturaQuartoSuite + "0", boldServicos);

        //Paragrafo Hidraulica

        Paragraph paragrafoHidraulicaCozinha = new Paragraph("Cozinha                                                                                    Total R$" + valorTotalHidraulicaCozinha + "0", boldServicos);
        Paragraph paragrafoHidraulicaBanheiro = new Paragraph("Banheiro                                                                                   Total R$" + valorTotalHidraulicaBanheiroSocial + "0", boldServicos);
        Paragraph paragrafoHidraulicaAreaServico = new Paragraph("Área de Serviço                                                                            Total R$" + valorTotalHidraulicaAreaServico + "0", boldServicos);
        Paragraph paragrafoHidraulicaBanheiro2 = new Paragraph("Banheiro Suíte                                                                             Total R$" + valorTotalHidraulicaBanheiroSuite + "0", boldServicos);
        Paragraph paragrafoHidraulicaLavabo = new Paragraph("Lavabo                                                                                     Total R$" + valorTotalHidraulicaLavabo + "0", boldServicos);
        Paragraph paragrafoHidraulicaSacadaVaranda = new Paragraph("Sacada Varanda                                                                             Total R$" + valorTotalHidraulicaSacada + "0", boldServicos);
        Paragraph paragrafoHidraulicaSalaJantar = new Paragraph("Sala Jantar                                                                                Total R$" + valorTotalHidraulicaSalaJantar + "0", boldServicos);
        Paragraph paragrafoHidraulicaSalaEstar = new Paragraph("Sala Estar                                                                                 Total R$" + valorTotalHidraulicaSalaEstar + "0", boldServicos);
        Paragraph paragrafoHidraulicaQuarto1 = new Paragraph("Quarto 1                                                                                   Total R$" + valorTotalHidraulicaQuarto1 + "0", boldServicos);
        Paragraph paragrafoHidraulicaQuarto2 = new Paragraph("Quarto 2                                                                                   Total R$" + valorTotalHidraulicaQuarto2 + "0", boldServicos);
        Paragraph paragrafoHidraulicaQuarto3 = new Paragraph("Quarto Suíte                                                                               Total R$" + valorTotalHidraulicaQuartoSuite + "0", boldServicos);

        Paragraph paragrafoServicos = new Paragraph("Quarto Suíte", boldServicosPrestados);


        //Tabela Endereco
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 2});


        //Adicionando logo e retirando a borda
        PdfPCell cell = new PdfPCell();
        cell.addElement(image);
        cell.setBorder(Rectangle.NO_BORDER);

        PdfPTable table2 = new PdfPTable(1);
        table2.setWidthPercentage(100);


        //Adicionando a logo no documento com o texto do endereço no lado direito
        table.addCell(cell);
        try {
            table.addCell(createTextCell("RAGONEZI - Engenharia e Reformas\nTEL: (19) 97402-3202 - Engº Felipe / (19) 99112-2676 Engº Fabio\n" +
                    "ragoneziengenharia@gmail.com\nRua João Burato, 88 - Barão Geraldo - Campinas/SP"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Adicionando titulo

        PdfPTable tableOrcamento = new PdfPTable(1);
        tableOrcamento.setWidthPercentage(100.0f);
        // tableOrcamento.setWidths(new int[]{1, 2});
        PdfPCell cellOrcamento = new PdfPCell();
        PdfPCell cellNumeroNota = new PdfPCell();
        PdfPCell cellBranco = new PdfPCell();
        PdfPCell cellBranco2 = new PdfPCell();


        Paragraph paragrafoOrcamento = new Paragraph("ORÇAMENTO 000" + alterarNumeroNota, fonteOrcamento);
        Paragraph paragrafobranco = new Paragraph("", fonteOrcamento);

        // paragrafoOrcamento.setLeading(1f);
        Paragraph paragrafoNumeroNota = new Paragraph("000" + alterarNumeroNota, bold);
        //paragrafoNumeroNota.setLeading(1f);
        paragrafoOrcamento.setAlignment(Element.ALIGN_RIGHT);
        paragrafoNumeroNota.setAlignment(Element.ALIGN_RIGHT);
        cellOrcamento.addElement(paragrafoOrcamento);

        cellBranco.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cellBranco2.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cellOrcamento.setVerticalAlignment(Element.ALIGN_RIGHT);
        cellNumeroNota.setVerticalAlignment(Element.ALIGN_RIGHT);
        cellOrcamento.setBorder(Rectangle.NO_BORDER);
        cellNumeroNota.setBorder(Rectangle.NO_BORDER);
        cellBranco.setBorder(Rectangle.NO_BORDER);
        cellBranco2.setBorder(Rectangle.NO_BORDER);
        //cellNumeroNota.setBorder(Rectangle.NO_BORDER);
        //cellNumeroNota.addElement(paragrafoNumeroNota);
        //cellOrcamento.addElement(paragrafoNumeroNota);
        cellBranco.addElement(paragrafobranco);
        cellBranco2.addElement(paragrafobranco);
        //tableOrcamento.addCell(cellBranco);
        // tableOrcamento.addCell(cellBranco2);
        tableOrcamento.addCell(cellOrcamento);
        //ableOrcamento.addCell(cellNumeroNota);


        PdfPTable table5 = new PdfPTable(1);
        table5.setWidthPercentage(100);
        //table5.setWidths(new int[]{1, 2});


        Drawable d2 = getResources().getDrawable(R.drawable.faixa);
        Bitmap bitmap2 = ((BitmapDrawable) d2).getBitmap();
        Bitmap resizedBitmap2 = Bitmap.createScaledBitmap(bitmap, 100, 50, true);
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] bitmapData2 = stream2.toByteArray();
        Image image2 = null;
        try {
            image2 = Image.getInstance(bitmapData2);
            image2.setAlignment(Element.ALIGN_CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Adicionando logo e retirando a borda
        PdfPCell cell5 = new PdfPCell();
        cell5.addElement(image2);
        cell5.setBorder(Rectangle.NO_BORDER);
        table5.addCell(cell5);


        Paragraph paragrafoCliente = new Paragraph(nomeCliente, fonteOrcamento);


        //Rodape
        PdfPTable tableProposta = new PdfPTable(1);
        tableProposta.setWidthPercentage(100);
        PdfPCell cellProposta = new PdfPCell();
        PdfPCell cellTextao = new PdfPCell();


        Paragraph paragrafoDisposicao = new Paragraph("Fico à disposição para qualquer dúvida e negociação\n" +
                "Atenciosamente, Eng° Felipe Ragonezi\n" +
                "RAGONEZI – Engenharia e Reformas\n", fonteOrcamento);
        paragrafoDisposicao.setAlignment(Element.ALIGN_RIGHT);
        Paragraph paragrafoProposta = new Paragraph("PROPOSTA", bold);
        paragrafoProposta.setAlignment(Element.ALIGN_CENTER);
        Paragraph paragrafoTextao = new Paragraph("Todos os serviços citados serão acompanhados por um engenheiro capacitado e credenciado. Nota fiscal para todos os itens apresentados.\n" +
                "Forma de pagamento 40% na entrada, fechamento de contrato + 60% na conclusão do processo.\n" +
                "São aceitos pagamentos com cartão de débito e crédito, tambem poderá ser efetuado o pagamento por boleto bancário e/ou transferência.\n" +
                "Esses valor não estão inclusos os materiais que serão usados.\n", fonteOrcamento);
        paragrafoTextao.setAlignment(Element.ALIGN_CENTER);


        cellProposta.addElement(paragrafoProposta);
        cellTextao.addElement(paragrafoTextao);
        tableProposta.addCell(cellProposta);
        tableProposta.addCell(cellTextao);


        //


        espacoBranco.add(new Paragraph("", boldTitulo));
        espacoBranco.add(new Paragraph("", boldTitulo));
        //Paragrafos
        Paragraph titulo = new Paragraph("Orçamento", boldTitulo);
        //Paragraph banheiro = new Paragraph("Remover revestimento 1", bold);
        //Paragraph cozinha = new Paragraph("Teste Bold", bold);


        //Alinhar paragrafos
        titulo.setAlignment(Element.ALIGN_CENTER);
        tituloParagrafo.setAlignment(Element.ALIGN_CENTER);

        //Valores de Banheiro
        // banheiro.add(new Paragraph ("Remover revestimento 1"));
        //Valores de Cozinha
        //cozinha.add(new Paragraph("Revestimento 2"));


        String numeroNotaExibir = Integer.toString(numeroNotaAtual);

        // document.add(image);
        document.add(table);
        document.add(tableOrcamento);
        document.add(Chunk.NEWLINE);


        //Nome do Cliente

        PdfPTable tableNomeCliente = new PdfPTable(1);
        tableNomeCliente.setWidthPercentage(100);
        //tableNomeCliente.setWidths(new int[]{1, 2});
        PdfPCell cellNomeCliente = new PdfPCell();
        //PdfPCell cellCliente = new PdfPCell();

        //cellCliente.setBorder(Rectangle.NO_BORDER);
        cellNomeCliente.setBorder(Rectangle.NO_BORDER);

        Paragraph paragrafoNomeCliente = new Paragraph("Nome do Cliente:" + nomeCliente, bold);
        cellNomeCliente.addElement(paragrafoNomeCliente);
        tableNomeCliente.addCell(cellNomeCliente);
        document.add(table5);
        document.add(tableNomeCliente);
        // document.add(tableNomeCliente);

        //document.add(dataParagrafo);
        //document.addTitle("Orçamento de Demolição");
        //document.add(new Paragraph("Número da nota:000"+numeroNotaExibir));


        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        //document.add(image);
        document.add(Chunk.NEWLINE);
        document.add(titulo);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);


        if (totalDemolicao > 0) {

            PdfPTable tableDemolicao = new PdfPTable(1);
            tableDemolicao.setWidthPercentage(100);
            PdfPCell demolicao = new PdfPCell();
            demolicao.setBorder(Rectangle.NO_BORDER);
            demolicao.addElement(paragrafoDemolicao);

            document.add(paragrafoDemolicao);
            document.add(espacoBranco);


            if (valorTotalCozinha > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2;

                tableCozinha.addCell(paragrafoCozinha);
                document.add(Chunk.NEWLINE);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede > 0 || varRemoverRevestimentoParede1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede1.getText().toString()) + Double.parseDouble(valorRevestimentoParede1_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede + varRemoverRevestimentoParede1) + "0", boldServicosPrestados));
                if (varRemoverPiso > 0 || varRemoverPiso1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso1.getText().toString()) + Double.parseDouble(valorRemocaoPiso1_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso1 + varRemoverPiso) + "0", boldServicosPrestados));
                if (varRemoverPia > 0 || varRemoverPia1 > 0)
                    document.add(new Paragraph(">>>Remover Pia: " + (Double.parseDouble(valorRemocaoPia1.getText().toString()) + Double.parseDouble(valorRemocaoPia1_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia1 + varRemoverPia) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria > 0 || varRemoverAlvenaria1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria1.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria1.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria1 + varRemoverAlvenaria) + "0", boldServicosPrestados));
                if (varRemoverTanque > 0 || varRemoverTanque1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque1.getText().toString()) + Double.parseDouble(valorRemocaoTanque1.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque1 + varRemoverTanque) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2 > 0 || varRemoverCaixinha4x2_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x1 : " + (Double.parseDouble(valorRasgarCaixinha4x2_1.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_1_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_1 + varRemoverCaixinha4x2) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4 > 0 || varRemoverCaixinha4x4_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x1 : " + (Double.parseDouble(valorRasgarCaixinha4x4_1.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_1_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_1 + varRemoverCaixinha4x4) + "0", boldServicosPrestados));
                if (varRemoverHidraulica > 0 || varRemoverHidraulica1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica1.getText().toString()) + Double.parseDouble(valorRasgarHidraulica1.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica1 + varRemoverHidraulica) + "0", boldServicosPrestados));
                if (varRemoverGesso > 0 || varRemoverGesso1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso1_1.getText().toString()) + Double.parseDouble(valorRemoverGesso1.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso1 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario > 0 || varRemoverVasoSanitario1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso1.getText().toString()) + Double.parseDouble(valorRemoverVaso1_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario1 + varRemoverVasoSanitario) + "0", boldServicosPrestados));
                if (varRemoverVao > 0 || varRemoverVao1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao1.getText().toString()) + Double.parseDouble(valorRemoverVao1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao1 + varRemoverVao) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);

            }
            if (valorTotalBanheiro1 > 0) {
                PdfPTable tableBanheiro = new PdfPTable(1);
                tableBanheiro.setWidthPercentage(100);
                PdfPCell cellBanheiro;

                tableBanheiro.addCell(paragrafoBanheiro);
                document.add(Chunk.NEWLINE);
                document.add(tableBanheiro);

                if (varRemoverRevestimentoParede2 > 0 || varRemoverRevestimentoParede2_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede2.getText().toString()) + Double.parseDouble(valorRevestimentoParede2_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede2 + varRemoverRevestimentoParede2_1) + "0", boldServicosPrestados));
                if (varRemoverPiso2 > 0 || varRemoverPiso2_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso2.getText().toString()) + Double.parseDouble(valorRemocaoPiso2_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso2 + varRemoverPiso2_1) + "0", boldServicosPrestados));
                if (varRemoverPia2 > 0 || varRemoverPia2_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia2.getText().toString()) + Double.parseDouble(valorRemocaoPia2_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia2 + varRemoverPia2_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria2 > 0 || varRemoverAlvenaria2_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria2.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria2.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria2 + varRemoverAlvenaria2_1) + "0", boldServicosPrestados));
                if (varRemoverTanque2 > 0 || varRemoverTanque2_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque2.getText().toString()) + Double.parseDouble(valorRemocaoTanque2.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque2 + varRemoverTanque2_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_2 > 0 || varRemoverCaixinha4x2_2_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_2.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_2_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_2 + varRemoverCaixinha4x2_2_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_2 > 0 || varRemoverCaixinha4x4_2_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_2.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_2_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_2 + varRemoverCaixinha4x4_2_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica2 > 0 || varRemoverHidraulica2_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica2.getText().toString()) + Double.parseDouble(valorRasgarHidraulica2.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica2 + varRemoverHidraulica2_1) + "0", boldServicosPrestados));
                if (varRemoverGesso2 > 0 || varRemoverGesso2_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso2_1.getText().toString()) + Double.parseDouble(valorRemoverGesso2.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso2 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario2 > 0 || varRemoverVasoSanitario2_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso2.getText().toString()) + Double.parseDouble(valorRemoverVaso2_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario2 + varRemoverVasoSanitario2_1) + "0", boldServicosPrestados));
                if (varRemoverVao2 > 0 || varRemoverVao2_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao2.getText().toString()) + Double.parseDouble(valorRemoverVao2.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao2 + varRemoverVao2_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalAreaServico > 0) {

                PdfPTable tableAreaServico = new PdfPTable(1);
                tableAreaServico.setWidthPercentage(100);
                PdfPCell cellAreaServico;

                tableAreaServico.addCell(paragrafoAreaServico);
                document.add(Chunk.NEWLINE);
                document.add(tableAreaServico);

                if (varRemoverRevestimentoParede3 > 0 || varRemoverRevestimentoParede3_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede3.getText().toString()) + Double.parseDouble(valorRevestimentoParede3_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede3 + varRemoverRevestimentoParede3_1) + "0", boldServicosPrestados));
                if (varRemoverPiso3 > 0 || varRemoverPiso3_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso3.getText().toString()) + Double.parseDouble(valorRemocaoPiso3_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso3 + varRemoverPiso3_1) + "0", boldServicosPrestados));
                if (varRemoverPia3 > 0 || varRemoverPia3_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia3.getText().toString()) + Double.parseDouble(valorRemocaoPia3_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia3 + varRemoverPia3_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria3 > 0 || varRemoverAlvenaria3_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria3.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria3.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria3 + varRemoverAlvenaria3_1) + "0", boldServicosPrestados));
                if (varRemoverTanque3 > 0 || varRemoverTanque3_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque3.getText().toString()) + Double.parseDouble(valorRemocaoTanque3.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque3 + varRemoverTanque3_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_3 > 0 || varRemoverCaixinha4x2_3_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_3.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_3_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_3 + varRemoverCaixinha4x2_3_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_3 > 0 || varRemoverCaixinha4x4_3_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_3.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_3_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_3 + varRemoverCaixinha4x4_3_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica3 > 0 || varRemoverHidraulica3_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica3.getText().toString()) + Double.parseDouble(valorRasgarHidraulica3.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica3 + varRemoverHidraulica3_1) + "0", boldServicosPrestados));
                if (varRemoverGesso3 > 0 || varRemoverGesso3_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso3_1.getText().toString()) + Double.parseDouble(valorRemoverGesso3.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso3 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario3 > 0 || varRemoverVasoSanitario3_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso3.getText().toString()) + Double.parseDouble(valorRemoverVaso3_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario3 + varRemoverVasoSanitario3_1) + "0", boldServicosPrestados));
                if (varRemoverVao3 > 0 || varRemoverVao3_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao3.getText().toString()) + Double.parseDouble(valorRemoverVao3.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao3 + varRemoverVao3_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalBanheiro2 > 0) {
                PdfPTable tableBanheiro2 = new PdfPTable(1);
                tableBanheiro2.setWidthPercentage(100);
                PdfPCell cellAreaServico;

                tableBanheiro2.addCell(paragrafoBanheiro2);
                document.add(Chunk.NEWLINE);
                document.add(tableBanheiro2);


                if (varRemoverRevestimentoParede4 > 0 || varRemoverRevestimentoParede4_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede4.getText().toString()) + Double.parseDouble(valorRevestimentoParede4_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede4 + varRemoverRevestimentoParede4_1) + "0", boldServicosPrestados));
                if (varRemoverPiso4 > 0 || varRemoverPiso4_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso4.getText().toString()) + Double.parseDouble(valorRemocaoPiso4_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso4 + varRemoverPiso4_1) + "0", boldServicosPrestados));
                if (varRemoverPia4 > 0 || varRemoverPia4_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia4.getText().toString()) + Double.parseDouble(valorRemocaoPia4_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia4 + varRemoverPia4_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria4 > 0 || varRemoverAlvenaria4_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria4.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria4.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria4 + varRemoverAlvenaria4_1) + "0", boldServicosPrestados));
                if (varRemoverTanque4 > 0 || varRemoverTanque4_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque4.getText().toString()) + Double.parseDouble(valorRemocaoTanque4.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque4 + varRemoverTanque4_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_4 > 0 || varRemoverCaixinha4x2_4_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_4.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_4_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_4 + varRemoverCaixinha4x2_4_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_4 > 0 || varRemoverCaixinha4x4_4_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_4.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_4_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_4 + varRemoverCaixinha4x4_4_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica4 > 0 || varRemoverHidraulica4_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica4.getText().toString()) + Double.parseDouble(valorRasgarHidraulica4.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica4 + varRemoverHidraulica4_1) + "0", boldServicosPrestados));
                if (varRemoverGesso4 > 0 || varRemoverGesso4_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso4_1.getText().toString()) + Double.parseDouble(valorRemoverGesso4.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso4 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario4 > 0 || varRemoverVasoSanitario4_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso4.getText().toString()) + Double.parseDouble(valorRemoverVaso4_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario4 + varRemoverVasoSanitario4_1) + "0", boldServicosPrestados));
                if (varRemoverVao4 > 0 || varRemoverVao4_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao4.getText().toString()) + Double.parseDouble(valorRemoverVao4.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao4 + varRemoverVao4_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalLavabo > 0) {


                PdfPTable tableAreaServico = new PdfPTable(1);
                tableAreaServico.setWidthPercentage(100);
                PdfPCell cellAreaServico;

                tableAreaServico.addCell(paragrafoLavabo);
                document.add(Chunk.NEWLINE);
                document.add(tableAreaServico);


                if (varRemoverRevestimentoParede5 > 0 || varRemoverRevestimentoParede5_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede5.getText().toString()) + Double.parseDouble(valorRevestimentoParede5_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede5 + varRemoverRevestimentoParede5_1) + "0", boldServicosPrestados));
                if (varRemoverPiso5 > 0 || varRemoverPiso5_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso5.getText().toString()) + Double.parseDouble(valorRemocaoPiso5_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso5 + varRemoverPiso5_1) + "0", boldServicosPrestados));
                if (varRemoverPia5 > 0 || varRemoverPia5_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia5.getText().toString()) + Double.parseDouble(valorRemocaoPia5_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia5 + varRemoverPia5_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria5 > 0 || varRemoverAlvenaria5_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria5.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria5.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria5 + varRemoverAlvenaria5_1) + "0", boldServicosPrestados));
                if (varRemoverTanque5 > 0 || varRemoverTanque5_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque5.getText().toString()) + Double.parseDouble(valorRemocaoTanque5.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque5 + varRemoverTanque5_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_5 > 0 || varRemoverCaixinha4x2_5_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_5.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_5_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_5 + varRemoverCaixinha4x2_5_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_5 > 0 || varRemoverCaixinha4x4_5_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_5.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_5_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_5 + varRemoverCaixinha4x4_5_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica5 > 0 || varRemoverHidraulica5_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica5.getText().toString()) + Double.parseDouble(valorRasgarHidraulica5.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica5 + varRemoverHidraulica5_1) + "0", boldServicosPrestados));
                if (varRemoverGesso5 > 0 || varRemoverGesso5_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso5_1.getText().toString()) + Double.parseDouble(valorRemoverGesso5.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso5 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario5 > 0 || varRemoverVasoSanitario5_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso5.getText().toString()) + Double.parseDouble(valorRemoverVaso5_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario5 + varRemoverVasoSanitario5_1) + "0", boldServicosPrestados));
                if (varRemoverVao5 > 0 || varRemoverVao5_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao5.getText().toString()) + Double.parseDouble(valorRemoverVao5.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao5 + varRemoverVao5_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }

            if (valorTotalSacadaVaranda > 0) {

                PdfPTable tableAreaServico = new PdfPTable(1);
                tableAreaServico.setWidthPercentage(100);
                PdfPCell cellAreaServico;

                tableAreaServico.addCell(paragrafoSacadaVaranda);
                document.add(Chunk.NEWLINE);
                document.add(tableAreaServico);
                if (varRemoverRevestimentoParede6 > 0 || varRemoverRevestimentoParede6_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede6.getText().toString()) + Double.parseDouble(valorRevestimentoParede6_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede6 + varRemoverRevestimentoParede6_1) + "0", boldServicosPrestados));
                if (varRemoverPiso6 > 0 || varRemoverPiso6_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso6.getText().toString()) + Double.parseDouble(valorRemocaoPiso6_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso6 + varRemoverPiso6_1) + "0", boldServicosPrestados));
                if (varRemoverPia6 > 0 || varRemoverPia6_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia6.getText().toString()) + Double.parseDouble(valorRemocaoPia6_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia6 + varRemoverPia6_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria6 > 0 || varRemoverAlvenaria6_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria6.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria6.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria6 + varRemoverAlvenaria6_1) + "0", boldServicosPrestados));
                if (varRemoverTanque6 > 0 || varRemoverTanque6_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque6.getText().toString()) + Double.parseDouble(valorRemocaoTanque6.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque6 + varRemoverTanque6_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_6 > 0 || varRemoverCaixinha4x2_6_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_6.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_6_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_6 + varRemoverCaixinha4x2_6_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_6 > 0 || varRemoverCaixinha4x4_6_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_6.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_6_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_6 + varRemoverCaixinha4x4_6_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica6 > 0 || varRemoverHidraulica6_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica6.getText().toString()) + Double.parseDouble(valorRasgarHidraulica6.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica6 + varRemoverHidraulica6_1) + "0", boldServicosPrestados));
                if (varRemoverGesso6 > 0 || varRemoverGesso6_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso6_1.getText().toString()) + Double.parseDouble(valorRemoverGesso6.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso6 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario6 > 0 || varRemoverVasoSanitario6_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso6.getText().toString()) + Double.parseDouble(valorRemoverVaso6_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario6 + varRemoverVasoSanitario6_1) + "0", boldServicosPrestados));
                if (varRemoverVao6 > 0 || varRemoverVao6_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao6.getText().toString()) + Double.parseDouble(valorRemoverVao6.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao6 + varRemoverVao6_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalSalaJantar > 0) {
                PdfPTable tableAreaServico = new PdfPTable(1);
                tableAreaServico.setWidthPercentage(100);
                PdfPCell cellAreaServico;

                tableAreaServico.addCell(paragrafoSalaJantar);
                document.add(Chunk.NEWLINE);
                document.add(tableAreaServico);


                if (varRemoverRevestimentoParede7 > 0 || varRemoverRevestimentoParede7_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede7.getText().toString()) + Double.parseDouble(valorRevestimentoParede7_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede7 + varRemoverRevestimentoParede7_1) + "0", boldServicosPrestados));
                if (varRemoverPiso7 > 0 || varRemoverPiso7_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso7.getText().toString()) + Double.parseDouble(valorRemocaoPiso7_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso7 + varRemoverPiso7_1) + "0", boldServicosPrestados));
                if (varRemoverPia7 > 0 || varRemoverPia7_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia7.getText().toString()) + Double.parseDouble(valorRemocaoPia7_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia7 + varRemoverPia7_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria7 > 0 || varRemoverAlvenaria7_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria7.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria7.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria7 + varRemoverAlvenaria7_1) + "0", boldServicosPrestados));
                if (varRemoverTanque7 > 0 || varRemoverTanque7_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque7.getText().toString()) + Double.parseDouble(valorRemocaoTanque7.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque7 + varRemoverTanque7_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_7 > 0 || varRemoverCaixinha4x2_7_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_7.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_7_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_7 + varRemoverCaixinha4x2_7_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_7 > 0 || varRemoverCaixinha4x4_7_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_7.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_7_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_7 + varRemoverCaixinha4x4_7_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica7 > 0 || varRemoverHidraulica7_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica7.getText().toString()) + Double.parseDouble(valorRasgarHidraulica7.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica7 + varRemoverHidraulica7_1) + "0", boldServicosPrestados));
                if (varRemoverGesso7 > 0 || varRemoverGesso7_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso7_1.getText().toString()) + Double.parseDouble(valorRemoverGesso7.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso7 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario7 > 0 || varRemoverVasoSanitario7_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso7.getText().toString()) + Double.parseDouble(valorRemoverVaso7_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario7 + varRemoverVasoSanitario7_1) + "0", boldServicosPrestados));
                if (varRemoverVao7 > 0 || varRemoverVao7_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao7.getText().toString()) + Double.parseDouble(valorRemoverVao7.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao7 + varRemoverVao7_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalSalaEstar > 0) {

                PdfPTable tableAreaServico = new PdfPTable(1);
                tableAreaServico.setWidthPercentage(100);
                PdfPCell cellAreaServico;

                tableAreaServico.addCell(paragrafoSalaEstar);
                document.add(Chunk.NEWLINE);
                document.add(tableAreaServico);

                if (varRemoverRevestimentoParede8 > 0 || varRemoverRevestimentoParede8_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede8.getText().toString()) + Double.parseDouble(valorRevestimentoParede8_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede8 + varRemoverRevestimentoParede8_1) + "0", boldServicosPrestados));
                if (varRemoverPiso8 > 0 || varRemoverPiso8_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso8.getText().toString()) + Double.parseDouble(valorRemocaoPiso8_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso8 + varRemoverPiso8_1) + "0", boldServicosPrestados));
                if (varRemoverPia8 > 0 || varRemoverPia8_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia8.getText().toString()) + Double.parseDouble(valorRemocaoPia8_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia8 + varRemoverPia8_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria8 > 0 || varRemoverAlvenaria8_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria8.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria8.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria8 + varRemoverAlvenaria8_1) + "0", boldServicosPrestados));
                if (varRemoverTanque8 > 0 || varRemoverTanque8_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque8.getText().toString()) + Double.parseDouble(valorRemocaoTanque8.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque8 + varRemoverTanque8_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_8 > 0 || varRemoverCaixinha4x2_8_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_8.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_8_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_8 + varRemoverCaixinha4x2_8_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_8 > 0 || varRemoverCaixinha4x4_8_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_8.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_8_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_8 + varRemoverCaixinha4x4_8_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica8 > 0 || varRemoverHidraulica8_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica8.getText().toString()) + Double.parseDouble(valorRasgarHidraulica8.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica8 + varRemoverHidraulica8_1) + "0", boldServicosPrestados));
                if (varRemoverGesso8 > 0 || varRemoverGesso8_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso8_1.getText().toString()) + Double.parseDouble(valorRemoverGesso8.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso8 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario8 > 0 || varRemoverVasoSanitario8_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso8.getText().toString()) + Double.parseDouble(valorRemoverVaso8_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario8 + varRemoverVasoSanitario8_1) + "0", boldServicosPrestados));
                if (varRemoverVao8 > 0 || varRemoverVao8_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao8.getText().toString()) + Double.parseDouble(valorRemoverVao8.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao8 + varRemoverVao8_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalQuarto1 > 0) {

                PdfPTable tableAreaServico = new PdfPTable(1);
                tableAreaServico.setWidthPercentage(100);
                PdfPCell cellAreaServico;

                tableAreaServico.addCell(paragrafoQuarto1);
                document.add(Chunk.NEWLINE);
                document.add(tableAreaServico);


                if (varRemoverRevestimentoParede9 > 0 || varRemoverRevestimentoParede9_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede9.getText().toString()) + Double.parseDouble(valorRevestimentoParede9_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede9 + varRemoverRevestimentoParede9_1) + "0", boldServicosPrestados));
                if (varRemoverPiso9 > 0 || varRemoverPiso9_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso9.getText().toString()) + Double.parseDouble(valorRemocaoPiso9_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso9 + varRemoverPiso9_1) + "0", boldServicosPrestados));
                if (varRemoverPia9 > 0 || varRemoverPia9_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia9.getText().toString()) + Double.parseDouble(valorRemocaoPia9_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia9 + varRemoverPia9_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria9 > 0 || varRemoverAlvenaria9_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria9.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria9.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria9 + varRemoverAlvenaria9_1) + "0", boldServicosPrestados));
                if (varRemoverTanque9 > 0 || varRemoverTanque9_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque9.getText().toString()) + Double.parseDouble(valorRemocaoTanque9.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque9 + varRemoverTanque9_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_9 > 0 || varRemoverCaixinha4x2_9_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_9.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_9_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_9 + varRemoverCaixinha4x2_9_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_9 > 0 || varRemoverCaixinha4x4_9_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_9.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_9_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_9 + varRemoverCaixinha4x4_9_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica9 > 0 || varRemoverHidraulica9_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica9.getText().toString()) + Double.parseDouble(valorRasgarHidraulica9.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica9 + varRemoverHidraulica9_1) + "0", boldServicosPrestados));
                if (varRemoverGesso9 > 0 || varRemoverGesso9_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso9_1.getText().toString()) + Double.parseDouble(valorRemoverGesso9.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso9 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario9 > 0 || varRemoverVasoSanitario9_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso9.getText().toString()) + Double.parseDouble(valorRemoverVaso9_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario9 + varRemoverVasoSanitario9_1) + "0", boldServicosPrestados));
                if (varRemoverVao9 > 0 || varRemoverVao9_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao9.getText().toString()) + Double.parseDouble(valorRemoverVao9.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao9 + varRemoverVao9_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalQuarto2 > 0) {

                PdfPTable tableDaCategoria = new PdfPTable(1);
                tableDaCategoria.setWidthPercentage(100);
                PdfPCell cell2;

                tableDaCategoria.addCell(paragrafoQuarto2);
                document.add(Chunk.NEWLINE);
                document.add(tableDaCategoria);


                if (varRemoverRevestimentoParede10 > 0 || varRemoverRevestimentoParede10_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede10.getText().toString()) + Double.parseDouble(valorRevestimentoParede10_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede10 + varRemoverRevestimentoParede10_1) + "0", boldServicosPrestados));
                if (varRemoverPiso10 > 0 || varRemoverPiso10_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso10.getText().toString()) + Double.parseDouble(valorRemocaoPiso10_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso10 + varRemoverPiso10_1) + "0", boldServicosPrestados));
                if (varRemoverPia10 > 0 || varRemoverPia10_1 > 0)
                    document.add(new Paragraph("Remover Pia: " + (Double.parseDouble(valorRemocaoPia10.getText().toString()) + Double.parseDouble(valorRemocaoPia10_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia10 + varRemoverPia10_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria10 > 0 || varRemoverAlvenaria10_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria10.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria10.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria10 + varRemoverAlvenaria10_1) + "0", boldServicosPrestados));
                if (varRemoverTanque10 > 0 || varRemoverTanque10_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque10.getText().toString()) + Double.parseDouble(valorRemocaoTanque10.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque10 + varRemoverTanque10_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_10 > 0 || varRemoverCaixinha4x2_10_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x2 : " + (Double.parseDouble(valorRasgarCaixinha4x2_10.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_10_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_10 + varRemoverCaixinha4x2_10_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_10 > 0 || varRemoverCaixinha4x4_10_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_10.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_10_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_10 + varRemoverCaixinha4x4_10_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica10 > 0 || varRemoverHidraulica10_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso10_1.getText().toString()) + Double.parseDouble(valorRemoverGesso10.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso10 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverGesso10 > 0 || varRemoverGesso10_1 > 0)
                    document.add(new Paragraph("Remover Gesso : " + (varRemoverGesso10 + varRemoverGesso1) + " m² -----" + "    R$" + (varRemoverGesso10 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario10 > 0 || varRemoverVasoSanitario10_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso10.getText().toString()) + Double.parseDouble(valorRemoverVaso10_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario10 + varRemoverVasoSanitario10_1) + "0", boldServicosPrestados));
                if (varRemoverVao10 > 0 || varRemoverVao10_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao10.getText().toString()) + Double.parseDouble(valorRemoverVao10.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao10 + varRemoverVao10_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalQuarto3 > 0) {
                PdfPTable tableDaCategoria = new PdfPTable(1);
                tableDaCategoria.setWidthPercentage(100);
                PdfPCell cell3;

                tableDaCategoria.addCell(paragrafoQuarto2);
                document.add(Chunk.NEWLINE);
                document.add(paragrafoQuarto3);


                if (varRemoverRevestimentoParede11 > 0 || varRemoverRevestimentoParede11_1 > 0)
                    document.add(new Paragraph(">>>Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede11.getText().toString()) + Double.parseDouble(valorRevestimentoParede11_1.getText().toString())) + " m² -----" + "R$" + (varRemoverRevestimentoParede11 + varRemoverRevestimentoParede11_1) + "0", boldServicosPrestados));
                if (varRemoverPiso11 > 0 || varRemoverPiso11_1 > 0)
                    document.add(new Paragraph(">>>Remover Piso: " + (Double.parseDouble(valorRemocaoPiso11.getText().toString()) + Double.parseDouble(valorRemocaoPiso11_1.getText().toString())) + " m² -----" + "R$" + (varRemoverPiso11 + varRemoverPiso11_1) + "0", boldServicosPrestados));
                if (varRemoverPia11 > 0 || varRemoverPia11_1 > 0)
                    document.add(new Paragraph(">>>Remover Pia: " + (Double.parseDouble(valorRemocaoPia11.getText().toString()) + Double.parseDouble(valorRemocaoPia11_1.getText().toString())) + " un ----- " + "R$" + (varRemoverPia11 + varRemoverPia11_1) + "0", boldServicosPrestados));
                if (varRemoverAlvenaria11 > 0 || varRemoverAlvenaria11_1 > 0)
                    document.add(new Paragraph(">>>Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria11.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria11.getText().toString())) + " m² -----" + "  R$" + (varRemoverAlvenaria11 + varRemoverAlvenaria11_1) + "0", boldServicosPrestados));
                if (varRemoverTanque11 > 0 || varRemoverTanque11_1 > 0)
                    document.add(new Paragraph(">>>Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque11.getText().toString()) + Double.parseDouble(valorRemocaoTanque11.getText().toString())) + " un ----- " + "    R$" + (varRemoverTanque11 + varRemoverTanque11_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x2_11 > 0 || varRemoverCaixinha4x2_11_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x11 : " + (Double.parseDouble(valorRasgarCaixinha4x2_11.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_11_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverCaixinha4x2_11 + varRemoverCaixinha4x2_11_1) + "0", boldServicosPrestados));
                if (varRemoverCaixinha4x4_11 > 0 || varRemoverCaixinha4x4_11_1 > 0)
                    document.add(new Paragraph(">>>Rasgar Caixinha 4x4 : " + (Double.parseDouble(valorRasgarCaixinha4x4_11.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_11_1.getText().toString())) + " un ----- " + "   R$" + (varRemoverCaixinha4x4_11 + varRemoverCaixinha4x4_11_1) + "0", boldServicosPrestados));
                if (varRemoverHidraulica11 > 0 || varRemoverHidraulica11_1 > 0)
                    document.add(new Paragraph(">>>Rasgar  Hidráulica : " + (Double.parseDouble(valorRasgarHidraulica11.getText().toString()) + Double.parseDouble(valorRasgarHidraulica11.getText().toString())) + " un ----- " + "    R$" + (varRemoverHidraulica11 + varRemoverHidraulica11_1) + "0", boldServicosPrestados));
                if (varRemoverGesso11 > 0 || varRemoverGesso11_1 > 0)
                    document.add(new Paragraph(">>>Remover Gesso : " + (Double.parseDouble(valorRemoverGesso11_1.getText().toString()) + Double.parseDouble(valorRemoverGesso11.getText().toString())) + " m² -----" + "    R$" + (varRemoverGesso11 + varRemoverGesso1) + "0", boldServicosPrestados));
                if (varRemoverVasoSanitario11 > 0 || varRemoverVasoSanitario11_1 > 0)
                    document.add(new Paragraph(">>>Remover Vaso Sanitário : " + (Double.parseDouble(valorRemoverVaso11.getText().toString()) + Double.parseDouble(valorRemoverVaso11_1.getText().toString())) + " un ----- " + "    R$" + (varRemoverVasoSanitario11 + varRemoverVasoSanitario11_1) + "0", boldServicosPrestados));
                if (varRemoverVao11 > 0 || varRemoverVao11_1 > 0)
                    document.add(new Paragraph(">>>Remover Vão para Nicho : " + (Double.parseDouble(valorRemoverVao11.getText().toString()) + Double.parseDouble(valorRemoverVao11.getText().toString())) + " un ----- " + "    R$" + (varRemoverVao11 + varRemoverVao11_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
        }

        //Impressao dos valores de Revestimento
        if (valorTotalCategoriaRevestimento > 0) {
            PdfPTable tableRevestimento = new PdfPTable(1);
            tableRevestimento.setWidthPercentage(100);
            PdfPCell revestimento = new PdfPCell();
            revestimento.setBorder(Rectangle.NO_BORDER);
            revestimento.addElement(paragrafoHidraulica);

            document.add(paragrafoRevestimento);
            document.add(espacoBranco);
            document.add(Chunk.NEWLINE);

            if (valorTotalRevestimentoCozinha > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoCozinha);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria > 0 || varAdicionarAlvenaria1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase1.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase1_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria + varAdicionarAlvenaria) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso > 0 || varAdicionarContraPiso1 > 0)
                    document.add(new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso1.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso1_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso + varAdicionarContraPiso1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante > 0 || varAplicacaoImpermeabilizante1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade1.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade1_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante + varAplicacaoImpermeabilizante1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor > 0 || varAplicarPorcelanatoMenor1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor1.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor1_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMenor + varAplicarPorcelanatoMenor1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior > 0 || varAplicarPorcelanatoMaior1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima1.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima1_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior1 + varAplicarPorcelanatoMaior) + "0", boldServicosPrestados));

                if (varPastilhaVidro > 0 || varPastilhaVidro_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro1.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro1_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro + varPastilhaVidro_1) + "0", boldServicosPrestados));
                if (varRevestimento3D > 0 || varRevestimento3D_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D1.getText().toString()) + Double.parseDouble(valorRevestimento3D1_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D + varRevestimento3D_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalRevestimentoBanheiroSocial > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoBanheiro);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria2 > 0 || varAdicionarAlvenaria2_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase2.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase2_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria2 + varAdicionarAlvenaria2_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso2 > 0 || varAdicionarContraPiso2_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso2.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso2_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso2 + varAdicionarContraPiso2_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante2 > 0 || varAplicacaoImpermeabilizante2_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade2.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade2_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante2 + varAplicacaoImpermeabilizante2_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor2 > 0 || varAplicarPorcelanatoMenor2_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor2.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor2_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior2_1 + varAplicarPorcelanatoMaior2) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior2 > 0 || varAplicarPorcelanatoMaior2_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima2.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima2_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior2 + varAplicarPorcelanatoMaior2_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_2 > 0 || varPastilhaVidro_2_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro2.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro2_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_2_1 + varPastilhaVidro_2) + "0", boldServicosPrestados));
                if (varRevestimento3D_2 > 0 || varRevestimento3D_2_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D2.getText().toString()) + Double.parseDouble(valorRevestimento3D2_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_2 + varRevestimento3D_2_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }

            if (valorTotalRevestimentoAreaServico > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoAreaServico);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria3 > 0 || varAdicionarAlvenaria3_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase3.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase3_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria3 + varAdicionarAlvenaria3_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso3 > 0 || varAdicionarContraPiso3_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso3.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso3_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso3 + varAdicionarContraPiso3_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante3 > 0 || varAplicacaoImpermeabilizante3_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade3.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade3_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante3 + varAplicacaoImpermeabilizante3_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor3 > 0 || varAplicarPorcelanatoMenor3_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor3.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor3_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior3_1 + varAplicarPorcelanatoMaior3) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior3 > 0 || varAplicarPorcelanatoMaior3_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima3.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima3_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior3 + varAplicarPorcelanatoMaior3_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_3 > 0 || varPastilhaVidro_3_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro3.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro3_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_3_1 + varPastilhaVidro_3) + "0", boldServicosPrestados));
                if (varRevestimento3D_3 > 0 || varRevestimento3D_3_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D3.getText().toString()) + Double.parseDouble(valorRevestimento3D3_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_3 + varRevestimento3D_3_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalRevestimentoBanheiroSuite > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoBanheiro2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria4 > 0 || varAdicionarAlvenaria4_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase4.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase4_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria4 + varAdicionarAlvenaria4_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso4 > 0 || varAdicionarContraPiso4_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso4.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso4_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso4 + varAdicionarContraPiso4_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante4 > 0 || varAplicacaoImpermeabilizante4_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade4.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade4_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante4 + varAplicacaoImpermeabilizante4_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor4 > 0 || varAplicarPorcelanatoMenor4_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor4.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor4_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior4_1 + varAplicarPorcelanatoMaior4) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior4 > 0 || varAplicarPorcelanatoMaior4_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima4.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima4_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior4 + varAplicarPorcelanatoMaior4_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_4 > 0 || varPastilhaVidro_4_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro4.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro4_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_4_1 + varPastilhaVidro_4) + "0", boldServicosPrestados));
                if (varRevestimento3D_4 > 0 || varRevestimento3D_4_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D4.getText().toString()) + Double.parseDouble(valorRevestimento3D4_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_4 + varRevestimento3D_4_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalRevestimentoLavabo > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoLavabo);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria5 > 0 || varAdicionarAlvenaria5_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase5.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase5_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria5 + varAdicionarAlvenaria5_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso5 > 0 || varAdicionarContraPiso5_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso5.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso5_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso5 + varAdicionarContraPiso5_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante5 > 0 || varAplicacaoImpermeabilizante5_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade5.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade5_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante5 + varAplicacaoImpermeabilizante5_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor5 > 0 || varAplicarPorcelanatoMenor5_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor5.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor5_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior5_1 + varAplicarPorcelanatoMaior5) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior5 > 0 || varAplicarPorcelanatoMaior5_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima5.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima5_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior5 + varAplicarPorcelanatoMaior5_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_5 > 0 || varPastilhaVidro_5_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro5.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro5_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_5_1 + varPastilhaVidro_5) + "0", boldServicosPrestados));
                if (varRevestimento3D_5 > 0 || varRevestimento3D_5_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D5.getText().toString()) + Double.parseDouble(valorRevestimento3D5_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_5 + varRevestimento3D_5_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalRevestimentoSacada > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoSacadaVaranda);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria6 > 0 || varAdicionarAlvenaria6_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase6.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase6_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria6 + varAdicionarAlvenaria6_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso6 > 0 || varAdicionarContraPiso6_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso6.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso6_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso6 + varAdicionarContraPiso6_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante6 > 0 || varAplicacaoImpermeabilizante6_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade6.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade6_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante6 + varAplicacaoImpermeabilizante6_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor6 > 0 || varAplicarPorcelanatoMenor6_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor6.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor6_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior6_1 + varAplicarPorcelanatoMaior6) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior6 > 0 || varAplicarPorcelanatoMaior6_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima6.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima6_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior6 + varAplicarPorcelanatoMaior6_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_6 > 0 || varPastilhaVidro_6_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro6.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro6_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_6_1 + varPastilhaVidro_6) + "0", boldServicosPrestados));
                if (varRevestimento3D_6 > 0 || varRevestimento3D_6_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D6.getText().toString()) + Double.parseDouble(valorRevestimento3D6_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_6 + varRevestimento3D_6_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalRevestimentoSalaJantar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoSalaJantar);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria7 > 0 || varAdicionarAlvenaria7_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase7.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase7_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria7 + varAdicionarAlvenaria7_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso7 > 0 || varAdicionarContraPiso7_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso7.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso7_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso7 + varAdicionarContraPiso7_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante7 > 0 || varAplicacaoImpermeabilizante7_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade7.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade7_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante7 + varAplicacaoImpermeabilizante7_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor7 > 0 || varAplicarPorcelanatoMenor7_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 70cm x 70cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor7.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor7_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior7_1 + varAplicarPorcelanatoMaior7) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior7 > 0 || varAplicarPorcelanatoMaior7_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 70cm x 70cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima7.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima7_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior7 + varAplicarPorcelanatoMaior7_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_7 > 0 || varPastilhaVidro_7_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro7.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro7_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_7_1 + varPastilhaVidro_7) + "0", boldServicosPrestados));
                if (varRevestimento3D_7 > 0 || varRevestimento3D_7_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D7.getText().toString()) + Double.parseDouble(valorRevestimento3D7_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_7 + varRevestimento3D_7_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalRevestimentoSalaEstar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoSalaEstar);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria8 > 0 || varAdicionarAlvenaria8_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase8.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase8_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria8 + varAdicionarAlvenaria8_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso8 > 0 || varAdicionarContraPiso8_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso8.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso8_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso8 + varAdicionarContraPiso8_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante8 > 0 || varAplicacaoImpermeabilizante8_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade8.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade8_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante8 + varAplicacaoImpermeabilizante8_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor8 > 0 || varAplicarPorcelanatoMenor8_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor8.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor8_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior8_1 + varAplicarPorcelanatoMaior8) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior8 > 0 || varAplicarPorcelanatoMaior8_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima8.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima8_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior8 + varAplicarPorcelanatoMaior8_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_8 > 0 || varPastilhaVidro_8_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro8.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro8_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_8_1 + varPastilhaVidro_8) + "0", boldServicosPrestados));
                if (varRevestimento3D_8 > 0 || varRevestimento3D_8_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D8.getText().toString()) + Double.parseDouble(valorRevestimento3D8_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_8 + varRevestimento3D_8_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }

            if (valorTotalRevestimentoQuarto1 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoQuarto1);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria9 > 0 || varAdicionarAlvenaria9_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase9.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase9_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria9 + varAdicionarAlvenaria9_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso9 > 0 || varAdicionarContraPiso9_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso9.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso9_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso9 + varAdicionarContraPiso9_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante9 > 0 || varAplicacaoImpermeabilizante9_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade9.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade9_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante9 + varAplicacaoImpermeabilizante9_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor9 > 0 || varAplicarPorcelanatoMenor9_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor9.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor9_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior9_1 + varAplicarPorcelanatoMaior9) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior9 > 0 || varAplicarPorcelanatoMaior9_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima9.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima9_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior9 + varAplicarPorcelanatoMaior9_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_9 > 0 || varPastilhaVidro_9_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro9.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro9_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_9_1 + varPastilhaVidro_9) + "0", boldServicosPrestados));
                if (varRevestimento3D_9 > 0 || varRevestimento3D_9_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D9.getText().toString()) + Double.parseDouble(valorRevestimento3D9_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_9 + varRevestimento3D_9_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalRevestimentoQuarto2 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoQuarto2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria10 > 0 || varAdicionarAlvenaria10_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase10.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase10_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria10 + varAdicionarAlvenaria10_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso10 > 0 || varAdicionarContraPiso10_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso10.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso10_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso10 + varAdicionarContraPiso10_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante10 > 0 || varAplicacaoImpermeabilizante10_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade10.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade10_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante10 + varAplicacaoImpermeabilizante10_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor10 > 0 || varAplicarPorcelanatoMenor10_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor10.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor10_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior10_1 + varAplicarPorcelanatoMaior10) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior10 > 0 || varAplicarPorcelanatoMaior10_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima10.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima10_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior10 + varAplicarPorcelanatoMaior10_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_10 > 0 || varPastilhaVidro_10_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro10.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro10_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_10_1 + varPastilhaVidro_10) + "0", boldServicosPrestados));
                if (varRevestimento3D_10 > 0 || varRevestimento3D_10_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D10.getText().toString()) + Double.parseDouble(valorRevestimento3D10_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_10 + varRevestimento3D_10_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }

            if (valorTotalRevestimentoQuartoSuite > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoRevestimentoQuarto3);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria11 > 0 || varAdicionarAlvenaria11_1 > 0)
                    document.add(new Paragraph(">>> Criar Alvenaria /Base " + (Double.parseDouble(valorRevestimentoAlvenariaBase11.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase11_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarAlvenaria11 + varAdicionarAlvenaria11_1) + "0", boldServicosPrestados));
                if (varAdicionarContraPiso11 > 0 || varAdicionarContraPiso11_1 > 0)
                    document.add(new Paragraph(">>>Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso11.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso11_1.getText().toString())) + " m² -----" + "R$" + (varAdicionarContraPiso11 + varAdicionarContraPiso11_1) + "0", boldServicosPrestados));
                if (varAplicacaoImpermeabilizante11 > 0 || varAplicacaoImpermeabilizante11_1 > 0)
                    document.add(new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade11.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade11_1.getText().toString())) + " m² ----- " + "R$" + (varAplicacaoImpermeabilizante11 + varAplicacaoImpermeabilizante11_1) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMenor11 > 0 || varAplicarPorcelanatoMenor11_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor11.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor11_1.getText().toString())) + " m² ----- " + "    R$" + (varAplicarPorcelanatoMaior11_1 + varAplicarPorcelanatoMaior11) + "0", boldServicosPrestados));
                if (varAplicarPorcelanatoMaior11 > 0 || varAplicarPorcelanatoMaior11_1 > 0)
                    document.add(new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima11.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima11_1.getText().toString())) + " m² -----" + "  R$" + (varAplicarPorcelanatoMaior11 + varAplicarPorcelanatoMaior11_1) + "0", boldServicosPrestados));
                if (varPastilhaVidro_11 > 0 || varPastilhaVidro_11_1 > 0)
                    document.add(new Paragraph(">>> Pastilha de Vidro : " + (Double.parseDouble(valorRevestimentoPastilhaVidro11.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro11_1.getText().toString())) + " m² ----- " + "    R$" + (varPastilhaVidro_11_1 + varPastilhaVidro_11) + "0", boldServicosPrestados));
                if (varRevestimento3D_11 > 0 || varRevestimento3D_11_1 > 0)
                    document.add(new Paragraph(">>> Revestimento 3D : " + (Double.parseDouble(valorRevestimento3D11.getText().toString()) + Double.parseDouble(valorRevestimento3D11_1.getText().toString())) + " m² ----- " + "   R$" + (varRevestimento3D_11 + varRevestimento3D_11_1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
        }

        if (valorTotalCategoriaPintura > 0) {

            PdfPTable tablePintura = new PdfPTable(1);
            tablePintura.setWidthPercentage(100);
            PdfPCell pintura = new PdfPCell();
            pintura.setBorder(Rectangle.NO_BORDER);
            pintura.addElement(paragrafoPintura);

            document.add(paragrafoPintura);
            document.add(espacoBranco);
            document.add(Chunk.NEWLINE);

            if (valorTotalPinturaCozinha > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaCozinha);
                document.add(tableCozinha);
                if (varPorta1 > 0 || varPorta > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta1.getText().toString()) + Double.parseDouble(valorPinturaPorta1_1.getText().toString())) + " un -----" + "R$" + (varPorta1 + varPorta) + "0", boldServicosPrestados));
                if (varJanela > 0 || varJanela1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela1.getText().toString()) + Double.parseDouble(valorPinturaJanela1_1.getText().toString())) + " un -----" + "R$" + (varJanela1 + varJanela) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo > 0 || varEfeitoDecorativo1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo1.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo1_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo1 + varEfeitoDecorativo) + "0", boldServicosPrestados));
                if (varReparoGesso > 0 || varReparoGesso1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo1.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo1_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso1 + varReparoGesso) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaBanheiroSocial > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaBanheiro);
                document.add(tableCozinha);
                if (varPorta2 > 0 || varPorta2_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta2.getText().toString()) + Double.parseDouble(valorPinturaPorta2_1.getText().toString())) + " un -----" + "R$" + (varPorta2 + varPorta2_1) + "0", boldServicosPrestados));
                if (varJanela2 > 0 || varJanela2_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela2.getText().toString()) + Double.parseDouble(valorPinturaJanela2_1.getText().toString())) + " un -----" + "R$" + (varJanela2_1 + varJanela2) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo2 > 0 || varEfeitoDecorativo2_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo2.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo2_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo2_1 + varEfeitoDecorativo2) + "0", boldServicosPrestados));
                if (varReparoGesso2 > 0 || varReparoGesso2_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo2.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo2_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso2_1 + varReparoGesso2) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaAreaServico > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaAreaServico);
                document.add(tableCozinha);
                if (varPorta3 > 0 || varPorta3_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta3.getText().toString()) + Double.parseDouble(valorPinturaPorta3_1.getText().toString())) + " un -----" + "R$" + (varPorta3 + varPorta3_1) + "0", boldServicosPrestados));
                if (varJanela3 > 0 || varJanela3_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela3.getText().toString()) + Double.parseDouble(valorPinturaJanela3_1.getText().toString())) + " un -----" + "R$" + (varJanela3_1 + varJanela3) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo3 > 0 || varEfeitoDecorativo3_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo3.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo3_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo3_1 + varEfeitoDecorativo3) + "0", boldServicosPrestados));
                if (varReparoGesso3 > 0 || varReparoGesso3_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo3.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo3_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso3_1 + varReparoGesso3) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaBanheiroSuite > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaBanheiro2);
                document.add(tableCozinha);
                if (varPorta4 > 0 || varPorta4_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta4.getText().toString()) + Double.parseDouble(valorPinturaPorta4_1.getText().toString())) + " un -----" + "R$" + (varPorta4 + varPorta4_1) + "0", boldServicosPrestados));
                if (varJanela4 > 0 || varJanela4_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela4.getText().toString()) + Double.parseDouble(valorPinturaJanela4_1.getText().toString())) + " un -----" + "R$" + (varJanela4_1 + varJanela4) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo4 > 0 || varEfeitoDecorativo4_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo4.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo4_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo4_1 + varEfeitoDecorativo4) + "0", boldServicosPrestados));
                if (varReparoGesso4 > 0 || varReparoGesso4_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo4.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo4_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso4_1 + varReparoGesso4) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaLavabo > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaLavabo);
                document.add(tableCozinha);
                if (varPorta5 > 0 || varPorta5_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta5.getText().toString()) + Double.parseDouble(valorPinturaPorta5_1.getText().toString())) + " un -----" + "R$" + (varPorta5 + varPorta5_1) + "0", boldServicosPrestados));
                if (varJanela5 > 0 || varJanela5_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela5.getText().toString()) + Double.parseDouble(valorPinturaJanela5_1.getText().toString())) + " un -----" + "R$" + (varJanela5_1 + varJanela5) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo5 > 0 || varEfeitoDecorativo5_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo5.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo5_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo5_1 + varEfeitoDecorativo5) + "0", boldServicosPrestados));
                if (varReparoGesso5 > 0 || varReparoGesso5_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo5.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo5_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso5_1 + varReparoGesso5) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaSacada > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaSacadaVaranda);
                document.add(tableCozinha);
                if (varPorta6 > 0 || varPorta6_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta6.getText().toString()) + Double.parseDouble(valorPinturaPorta6_1.getText().toString())) + " un -----" + "R$" + (varPorta6 + varPorta6_1) + "0", boldServicosPrestados));
                if (varJanela6 > 0 || varJanela6_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela6.getText().toString()) + Double.parseDouble(valorPinturaJanela6_1.getText().toString())) + " un -----" + "R$" + (varJanela6_1 + varJanela6) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo6 > 0 || varEfeitoDecorativo6_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo6.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo6_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo6_1 + varEfeitoDecorativo6) + "0", boldServicosPrestados));
                if (varReparoGesso6 > 0 || varReparoGesso6_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo6.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo6_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso6_1 + varReparoGesso6) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaSalaJantar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaSalaJantar);
                document.add(tableCozinha);
                if (varPorta7 > 0 || varPorta7_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta7.getText().toString()) + Double.parseDouble(valorPinturaPorta7_1.getText().toString())) + " un -----" + "R$" + (varPorta7 + varPorta7_1) + "0", boldServicosPrestados));
                if (varJanela7 > 0 || varJanela7_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela7.getText().toString()) + Double.parseDouble(valorPinturaJanela7_1.getText().toString())) + " un -----" + "R$" + (varJanela7_1 + varJanela7) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo7 > 0 || varEfeitoDecorativo7_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo7.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo7_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo7_1 + varEfeitoDecorativo7) + "0", boldServicosPrestados));
                if (varReparoGesso7 > 0 || varReparoGesso7_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaReparoGesso7.getText().toString()) + Double.parseDouble(valorPinturaReparoGesso7_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso7_1 + varReparoGesso7) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaSalaEstar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaSalaEstar);
                document.add(tableCozinha);
                if (varPorta8 > 0 || varPorta8_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta8.getText().toString()) + Double.parseDouble(valorPinturaPorta8_1.getText().toString())) + " un -----" + "R$" + (varPorta8 + varPorta8_1) + "0", boldServicosPrestados));
                if (varJanela8 > 0 || varJanela8_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela8.getText().toString()) + Double.parseDouble(valorPinturaJanela8_1.getText().toString())) + " un -----" + "R$" + (varJanela8_1 + varJanela8) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo8 > 0 || varEfeitoDecorativo8_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo8.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo8_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo8_1 + varEfeitoDecorativo8) + "0", boldServicosPrestados));
                if (varReparoGesso8 > 0 || varReparoGesso8_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo8.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo8_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso8_1 + varReparoGesso8) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaQuarto1 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaQuarto1);
                document.add(tableCozinha);
                if (varPorta9 > 0 || varPorta9_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta9.getText().toString()) + Double.parseDouble(valorPinturaPorta9_1.getText().toString())) + " un -----" + "R$" + (varPorta9 + varPorta9_1) + "0", boldServicosPrestados));
                if (varJanela9 > 0 || varJanela9_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela9.getText().toString()) + Double.parseDouble(valorPinturaJanela9_1.getText().toString())) + " un -----" + "R$" + (varJanela9_1 + varJanela9) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo9 > 0 || varEfeitoDecorativo9_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo9.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo9_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo9_1 + varEfeitoDecorativo9) + "0", boldServicosPrestados));
                if (varReparoGesso9 > 0 || varReparoGesso9_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo9.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo9_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso9_1 + varReparoGesso9) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaQuarto2 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaQuarto2);
                document.add(tableCozinha);
                if (varPorta10 > 0 || varPorta10_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta10.getText().toString()) + Double.parseDouble(valorPinturaPorta10_1.getText().toString())) + " un -----" + "R$" + (varPorta10 + varPorta10_1) + "0", boldServicosPrestados));
                if (varJanela10 > 0 || varJanela10_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela10.getText().toString()) + Double.parseDouble(valorPinturaJanela10_1.getText().toString())) + " un -----" + "R$" + (varJanela10_1 + varJanela10) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo10 > 0 || varEfeitoDecorativo10_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo10.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo10_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo10_1 + varEfeitoDecorativo10) + "0", boldServicosPrestados));
                if (varReparoGesso10 > 0 || varReparoGesso10_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo10.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo10_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso10_1 + varReparoGesso10) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalPinturaQuartoSuite > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(110);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoPinturaQuarto3);
                document.add(tableCozinha);
                if (varPorta11 > 0 || varPorta11_1 > 0)
                    document.add(new Paragraph(">>> Porta " + (Double.parseDouble(valorPinturaPorta11.getText().toString()) + Double.parseDouble(valorPinturaPorta11_1.getText().toString())) + " un -----" + "R$" + (varPorta11 + varPorta11_1) + "0", boldServicosPrestados));
                if (varJanela11 > 0 || varJanela11_1 > 0)
                    document.add(new Paragraph(">>> Janela: " + (Double.parseDouble(valorPinturaJanela11.getText().toString()) + Double.parseDouble(valorPinturaJanela11_1.getText().toString())) + " un -----" + "R$" + (varJanela11_1 + varJanela11) + "0", boldServicosPrestados));
                if (varEfeitoDecorativo11 > 0 || varEfeitoDecorativo11_1 > 0)
                    document.add(new Paragraph(">>> Efeitos Decorativos: " + (Double.parseDouble(valorPinturaEfeitoDecorativo11.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo11_1.getText().toString())) + " m² ----- " + "R$" + (varEfeitoDecorativo11_1 + varEfeitoDecorativo11) + "0", boldServicosPrestados));
                if (varReparoGesso11 > 0 || varReparoGesso11_1 > 0)
                    document.add(new Paragraph(">>> Reparos de Gesso: " + (Double.parseDouble(valorPinturaEfeitoDecorativo11.getText().toString()) + Double.parseDouble(valorPinturaEfeitoDecorativo11_1.getText().toString())) + " un ----- " + "    R$" + (varReparoGesso11_1 + varReparoGesso11) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);


            }
        }

        if (valorTotalCategoriaHidraulica > 0) {
            PdfPTable tableHidraulica = new PdfPTable(1);
            tableHidraulica.setWidthPercentage(100);
            PdfPCell hidraulica = new PdfPCell();
            hidraulica.setBorder(Rectangle.NO_BORDER);
            hidraulica.addElement(paragrafoHidraulica);


            document.add(paragrafoHidraulica);
            document.add(Chunk.NEWLINE);
            document.add(espacoBranco);
            if (valorTotalHidraulicaCozinha > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoHidraulicaCozinha);
                document.add(tableCozinha);
                if (varAdicionarTorneiraEletrica > 0 || varAdicionarTorneiraEletrica1 > 0)
                    document.add(new Paragraph(">>> Torneira elétrica :" + (Double.parseDouble(valorHidraulicaTorneiraEletrica1.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica1_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraEletrica1 + varAdicionarTorneiraEletrica) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraMonocomando > 0 || varAdicionarTorneiraMonocomando1 > 0)
                    document.add(new Paragraph(">>> Torneira monocomando : " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando1.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando1_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraMonocomando + varAdicionarTorneiraMonocomando1) + "0", boldServicosPrestados));

                if (varAdicionarTorneiraSimples > 0 || varAdicionarTorneiraSimples1 > 0)
                    document.add(new Paragraph(">>> Torneira simples : " + (Double.parseDouble(valorHidraulicaTorneiraSimples1.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples1_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraSimples1 + varAdicionarTorneiraSimples) + "0", boldServicosPrestados));
                if (varAdicionarValvulaSifao > 0 || varAdicionarValvulaSifao1 > 0)
                    document.add(new Paragraph(">>> Válvula e sifão simples : " + (Double.parseDouble(valorHidraulicaValvulaSifao1_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao1.getText().toString())) + " un ----- " + "R$" + (varAdicionarValvulaSifao + varAdicionarValvulaSifao1) + "0", boldServicosPrestados));
                if (varAdicionarRegistroAcabamento > 0 || varAdicionarRegistroAcabamento1 > 0)
                    document.add(new Paragraph(">>> Registros e acabamento : " + (Double.parseDouble(valorHidraulicaRegistroAcabamento1_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento1.getText().toString())) + " un ----- " + "    R$" + (varAdicionarRegistroAcabamento + varAdicionarRegistroAcabamento1) + "0", boldServicosPrestados));
                if (varAdicionarPontoAgua > 0 || varAdicionarPontoAgua_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de água : " + (Double.parseDouble(valorHidraulicaCriacaoAgua1.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua1_1.getText().toString())) + " un -----" + "  R$" + (varAdicionarPontoAgua_1 + varAdicionarPontoAgua) + "0", boldServicosPrestados));
                if (varAdicionarPontoEsgoto > 0 || varAdicionarPontoEsgoto_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de esgoto : " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto1.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto1_1.getText().toString())) + " un ----- " + "    R$" + (varAdicionarPontoEsgoto_1 + varAdicionarPontoEsgoto) + "0", boldServicosPrestados));
                if (varAdicionarRalo10cm > 0 || varAdicionarRalo10cm1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo de 10cm : " + (Double.parseDouble(valorHidraulicaRalo10cm1_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo10cm + varAdicionarRalo10cm1) + "0", boldServicosPrestados));
                if (varAdicionarRalo15cm > 0 || varAdicionarRalo15cm1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo 15cm : " + (Double.parseDouble(valorHidraulicaRalo15cm1_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo15cm + varAdicionarRalo15cm1) + "0", boldServicosPrestados));
                if (varAdicionarChuveiro > 0 || varAdicionarChuveiro1 > 0)
                    document.add(new Paragraph(">>> Chuveiro : " + (Double.parseDouble(valorHidraulicaChuveiro1.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro1_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarChuveiro + varAdicionarChuveiro1) + "0", boldServicosPrestados));
                if (varAdicionarRaloLinear > 0 || varAdicionarRaloLinear1 > 0)
                    document.add(new Paragraph(">>> Instalação de ralo linear : " + (Double.parseDouble(valorHidraulicaRaloLinear1.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear1_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRaloLinear + varAdicionarRaloLinear1) + "0", boldServicosPrestados));
                if (varAdicionarVasoSanitario > 0 || varAdicionarVasoSanitario1 > 0)
                    document.add(new Paragraph(">>> Instalar Vaso Sanitário : " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario1.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario1_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarVasoSanitario + varAdicionarVasoSanitario1) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalHidraulicaBanheiroSocial > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoHidraulicaBanheiro);
                document.add(tableCozinha);
                if (varAdicionarTorneiraEletrica2 > 0 || varAdicionarTorneiraEletrica2_1 > 0)
                    document.add(new Paragraph(">>> Torneira elétrica :" + (Double.parseDouble(valorHidraulicaTorneiraEletrica2.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica2_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraEletrica2_1 + varAdicionarTorneiraEletrica2) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraMonocomando > 0 || varAdicionarTorneiraMonocomando2_1 > 0)
                    document.add(new Paragraph(">>> Torneira monocomando : " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando2.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando2_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraMonocomando2 + varAdicionarTorneiraMonocomando2_1) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraSimples2 > 0 || varAdicionarTorneiraSimples2_1 > 0)
                    document.add(new Paragraph(">>> Torneira simples : " + (Double.parseDouble(valorHidraulicaTorneiraSimples2.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples2_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraSimples2_1 + varAdicionarTorneiraSimples2) + "0", boldServicosPrestados));
                if (varAdicionarValvulaSifao2 > 0 || varAdicionarValvulaSifao2_1 > 0)
                    document.add(new Paragraph(">>> Válvula e sifão simples : " + (Double.parseDouble(valorHidraulicaValvulaSifao2_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao2.getText().toString())) + " un ----- " + "R$" + (varAdicionarValvulaSifao2 + varAdicionarValvulaSifao2_1) + "0", boldServicosPrestados));
                if (varAdicionarRegistroAcabamento2 > 0 || varAdicionarRegistroAcabamento2_1 > 0)
                    document.add(new Paragraph(">>> Registros e acabamento : " + (Double.parseDouble(valorHidraulicaRegistroAcabamento2_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento2.getText().toString())) + " un ----- " + "    R$" + (varAdicionarRegistroAcabamento2 + varAdicionarRegistroAcabamento2_1) + "0", boldServicosPrestados));
                if (varAdicionarPontoAgua_2 > 0 || varAdicionarPontoAgua_2_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de água : " + (Double.parseDouble(valorHidraulicaCriacaoAgua2.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua2_1.getText().toString())) + " un -----" + "  R$" + (varAdicionarPontoAgua_2_1 + varAdicionarPontoAgua_2) + "0", boldServicosPrestados));
                if (varAdicionarPontoEsgoto_2 > 0 || varAdicionarPontoEsgoto_2_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de esgoto : " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto2.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto2_1.getText().toString())) + " un ----- " + "    R$" + (varAdicionarPontoEsgoto_2_1 + varAdicionarPontoEsgoto_2) + "0", boldServicosPrestados));
                if (varAdicionarRalo10cm2 > 0 || varAdicionarRalo10cm2_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo de 10cm : " + (Double.parseDouble(valorHidraulicaRalo10cm2_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm2.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo10cm2 + varAdicionarRalo10cm2_1) + "0", boldServicosPrestados));
                if (varAdicionarRalo15cm2 > 0 || varAdicionarRalo15cm2_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo 15cm : " + (Double.parseDouble(valorHidraulicaRalo15cm2_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm2.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo15cm2 + varAdicionarRalo15cm2_1) + "0", boldServicosPrestados));
                if (varAdicionarChuveiro2 > 0 || varAdicionarChuveiro2_1 > 0)
                    document.add(new Paragraph(">>> Chuveiro : " + (Double.parseDouble(valorHidraulicaChuveiro2.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro2_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarChuveiro2_1 + varAdicionarChuveiro2) + "0", boldServicosPrestados));
                if (varAdicionarRaloLinear2 > 0 || varAdicionarRaloLinear2_1 > 0)
                    document.add(new Paragraph(">>> Instalação de ralo linear : " + (Double.parseDouble(valorHidraulicaRaloLinear2.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear2_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRaloLinear2_1 + varAdicionarRaloLinear2) + "0", boldServicosPrestados));
                if (varAdicionarVasoSanitario2 > 0 || varAdicionarVasoSanitario2_1 > 0)
                    document.add(new Paragraph(">>> Instalar Vaso Sanitário : " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario2.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario2_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarVasoSanitario2_1 + varAdicionarVasoSanitario2) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalHidraulicaAreaServico > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoHidraulicaAreaServico);
                document.add(tableCozinha);
                if (varAdicionarTorneiraEletrica3 > 0 || varAdicionarTorneiraEletrica3_1 > 0)
                    document.add(new Paragraph(">>> Torneira elétrica :" + (Double.parseDouble(valorHidraulicaTorneiraEletrica3.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica3_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraEletrica3_1 + varAdicionarTorneiraEletrica3) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraMonocomando > 0 || varAdicionarTorneiraMonocomando3_1 > 0)
                    document.add(new Paragraph(">>> Torneira monocomando : " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando3.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando3_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraMonocomando3 + varAdicionarTorneiraMonocomando3_1) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraSimples3 > 0 || varAdicionarTorneiraSimples3_1 > 0)
                    document.add(new Paragraph(">>> Torneira simples : " + (Double.parseDouble(valorHidraulicaTorneiraSimples3.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples3_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraSimples3_1 + varAdicionarTorneiraSimples3) + "0", boldServicosPrestados));
                if (varAdicionarValvulaSifao3 > 0 || varAdicionarValvulaSifao3_1 > 0)
                    document.add(new Paragraph(">>> Válvula e sifão simples : " + (Double.parseDouble(valorHidraulicaValvulaSifao3_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao3.getText().toString())) + " un ----- " + "R$" + (varAdicionarValvulaSifao3 + varAdicionarValvulaSifao3_1) + "0", boldServicosPrestados));
                if (varAdicionarRegistroAcabamento3 > 0 || varAdicionarRegistroAcabamento3_1 > 0)
                    document.add(new Paragraph(">>> Registros e acabamento : " + (Double.parseDouble(valorHidraulicaRegistroAcabamento3_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento3.getText().toString())) + " un ----- " + "    R$" + (varAdicionarRegistroAcabamento3 + varAdicionarRegistroAcabamento3_1) + "0", boldServicosPrestados));
                if (varAdicionarPontoAgua_3 > 0 || varAdicionarPontoAgua_3_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de água : " + (Double.parseDouble(valorHidraulicaCriacaoAgua3.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua3_1.getText().toString())) + " un -----" + "  R$" + (varAdicionarPontoAgua_3_1 + varAdicionarPontoAgua_3) + "0", boldServicosPrestados));
                if (varAdicionarPontoEsgoto_3 > 0 || varAdicionarPontoEsgoto_3_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de esgoto : " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto3.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto3_1.getText().toString())) + " un ----- " + "    R$" + (varAdicionarPontoEsgoto_3_1 + varAdicionarPontoEsgoto_3) + "0", boldServicosPrestados));
                if (varAdicionarRalo10cm3 > 0 || varAdicionarRalo10cm3_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo de 10cm : " + (Double.parseDouble(valorHidraulicaRalo10cm3_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm3.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo10cm3 + varAdicionarRalo10cm3_1) + "0", boldServicosPrestados));
                if (varAdicionarRalo15cm3 > 0 || varAdicionarRalo15cm3_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo 15cm : " + (Double.parseDouble(valorHidraulicaRalo15cm3_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm3.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo15cm3 + varAdicionarRalo15cm3_1) + "0", boldServicosPrestados));
                if (varAdicionarChuveiro3 > 0 || varAdicionarChuveiro3_1 > 0)
                    document.add(new Paragraph(">>> Chuveiro : " + (Double.parseDouble(valorHidraulicaChuveiro3.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro3_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarChuveiro3_1 + varAdicionarChuveiro3) + "0", boldServicosPrestados));
                if (varAdicionarRaloLinear3 > 0 || varAdicionarRaloLinear3_1 > 0)
                    document.add(new Paragraph(">>> Instalação de ralo linear : " + (Double.parseDouble(valorHidraulicaRaloLinear3.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear3_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRaloLinear3_1 + varAdicionarRaloLinear3) + "0", boldServicosPrestados));
                if (varAdicionarVasoSanitario3 > 0 || varAdicionarVasoSanitario3_1 > 0)
                    document.add(new Paragraph(">>> Instalar Vaso Sanitário : " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario3.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario3_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarVasoSanitario3_1 + varAdicionarVasoSanitario3) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalHidraulicaBanheiroSuite > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoHidraulicaBanheiro2);
                document.add(tableCozinha);
                if (varAdicionarTorneiraEletrica4 > 0 || varAdicionarTorneiraEletrica4_1 > 0)
                    document.add(new Paragraph(">>> Torneira elétrica :" + (Double.parseDouble(valorHidraulicaTorneiraEletrica4.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica4_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraEletrica4_1 + varAdicionarTorneiraEletrica4) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraMonocomando > 0 || varAdicionarTorneiraMonocomando4_1 > 0)
                    document.add(new Paragraph(">>> Torneira monocomando : " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando4.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando4_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraMonocomando4 + varAdicionarTorneiraMonocomando4_1) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraSimples4 > 0 || varAdicionarTorneiraSimples4_1 > 0)
                    document.add(new Paragraph(">>> Torneira simples : " + (Double.parseDouble(valorHidraulicaTorneiraSimples4.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples4_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraSimples4_1 + varAdicionarTorneiraSimples4) + "0", boldServicosPrestados));
                if (varAdicionarValvulaSifao4 > 0 || varAdicionarValvulaSifao4_1 > 0)
                    document.add(new Paragraph(">>> Válvula e sifão simples : " + (Double.parseDouble(valorHidraulicaValvulaSifao4_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao4.getText().toString())) + " un ----- " + "R$" + (varAdicionarValvulaSifao4 + varAdicionarValvulaSifao4_1) + "0", boldServicosPrestados));
                if (varAdicionarRegistroAcabamento4 > 0 || varAdicionarRegistroAcabamento4_1 > 0)
                    document.add(new Paragraph(">>> Registros e acabamento : " + (Double.parseDouble(valorHidraulicaRegistroAcabamento4_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento4.getText().toString())) + " un ----- " + "    R$" + (varAdicionarRegistroAcabamento4 + varAdicionarRegistroAcabamento4_1) + "0", boldServicosPrestados));
                if (varAdicionarPontoAgua_4 > 0 || varAdicionarPontoAgua_4_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de água : " + (Double.parseDouble(valorHidraulicaCriacaoAgua4.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua4_1.getText().toString())) + " un -----" + "  R$" + (varAdicionarPontoAgua_4_1 + varAdicionarPontoAgua_4) + "0", boldServicosPrestados));
                if (varAdicionarPontoEsgoto_4 > 0 || varAdicionarPontoEsgoto_4_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de esgoto : " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto4.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto4_1.getText().toString())) + " un ----- " + "    R$" + (varAdicionarPontoEsgoto_4_1 + varAdicionarPontoEsgoto_4) + "0", boldServicosPrestados));
                if (varAdicionarRalo10cm4 > 0 || varAdicionarRalo10cm4_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo de 10cm : " + (Double.parseDouble(valorHidraulicaRalo10cm4_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm4.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo10cm4 + varAdicionarRalo10cm4_1) + "0", boldServicosPrestados));
                if (varAdicionarRalo15cm4 > 0 || varAdicionarRalo15cm4_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo 15cm : " + (Double.parseDouble(valorHidraulicaRalo15cm4_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm4.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo15cm4 + varAdicionarRalo15cm4_1) + "0", boldServicosPrestados));
                if (varAdicionarChuveiro4 > 0 || varAdicionarChuveiro4_1 > 0)
                    document.add(new Paragraph(">>> Chuveiro : " + (Double.parseDouble(valorHidraulicaChuveiro4.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro4_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarChuveiro4_1 + varAdicionarChuveiro4) + "0", boldServicosPrestados));
                if (varAdicionarRaloLinear4 > 0 || varAdicionarRaloLinear4_1 > 0)
                    document.add(new Paragraph(">>> Instalação de ralo linear : " + (Double.parseDouble(valorHidraulicaRaloLinear4.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear4_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRaloLinear4_1 + varAdicionarRaloLinear4) + "0", boldServicosPrestados));
                if (varAdicionarVasoSanitario4 > 0 || varAdicionarVasoSanitario4_1 > 0)
                    document.add(new Paragraph(">>> Instalar Vaso Sanitário : " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario4.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario4_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarVasoSanitario4_1 + varAdicionarVasoSanitario4) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalHidraulicaLavabo > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoHidraulicaLavabo);
                document.add(tableCozinha);
                if (varAdicionarTorneiraEletrica5 > 0 || varAdicionarTorneiraEletrica5_1 > 0)
                    document.add(new Paragraph(">>> Torneira elétrica :" + (Double.parseDouble(valorHidraulicaTorneiraEletrica5.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica5_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraEletrica5_1 + varAdicionarTorneiraEletrica5) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraMonocomando > 0 || varAdicionarTorneiraMonocomando5_1 > 0)
                    document.add(new Paragraph(">>> Torneira monocomando : " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando5.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando5_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraMonocomando5 + varAdicionarTorneiraMonocomando5_1) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraSimples5 > 0 || varAdicionarTorneiraSimples5_1 > 0)
                    document.add(new Paragraph(">>> Torneira simples : " + (Double.parseDouble(valorHidraulicaTorneiraSimples5.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples5_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraSimples5_1 + varAdicionarTorneiraSimples5) + "0", boldServicosPrestados));
                if (varAdicionarValvulaSifao5 > 0 || varAdicionarValvulaSifao5_1 > 0)
                    document.add(new Paragraph(">>> Válvula e sifão simples : " + (Double.parseDouble(valorHidraulicaValvulaSifao5_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao5.getText().toString())) + " un ----- " + "R$" + (varAdicionarValvulaSifao5 + varAdicionarValvulaSifao5_1) + "0", boldServicosPrestados));
                if (varAdicionarRegistroAcabamento5 > 0 || varAdicionarRegistroAcabamento5_1 > 0)
                    document.add(new Paragraph(">>> Registros e acabamento : " + (Double.parseDouble(valorHidraulicaRegistroAcabamento5_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento5.getText().toString())) + " un ----- " + "    R$" + (varAdicionarRegistroAcabamento5 + varAdicionarRegistroAcabamento5_1) + "0", boldServicosPrestados));
                if (varAdicionarPontoAgua_5 > 0 || varAdicionarPontoAgua_5_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de água : " + (Double.parseDouble(valorHidraulicaCriacaoAgua5.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua5_1.getText().toString())) + " un -----" + "  R$" + (varAdicionarPontoAgua_5_1 + varAdicionarPontoAgua_5) + "0", boldServicosPrestados));
                if (varAdicionarPontoEsgoto_5 > 0 || varAdicionarPontoEsgoto_5_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de esgoto : " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto5.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto5_1.getText().toString())) + " un ----- " + "    R$" + (varAdicionarPontoEsgoto_5_1 + varAdicionarPontoEsgoto_5) + "0", boldServicosPrestados));
                if (varAdicionarRalo10cm5 > 0 || varAdicionarRalo10cm5_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo de 10cm : " + (Double.parseDouble(valorHidraulicaRalo10cm5_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm5.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo10cm5 + varAdicionarRalo10cm5_1) + "0", boldServicosPrestados));
                if (varAdicionarRalo15cm5 > 0 || varAdicionarRalo15cm5_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo 15cm : " + (Double.parseDouble(valorHidraulicaRalo15cm5_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm5.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo15cm5 + varAdicionarRalo15cm5_1) + "0", boldServicosPrestados));
                if (varAdicionarChuveiro5 > 0 || varAdicionarChuveiro5_1 > 0)
                    document.add(new Paragraph(">>> Chuveiro : " + (Double.parseDouble(valorHidraulicaChuveiro5.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro5_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarChuveiro5_1 + varAdicionarChuveiro5) + "0", boldServicosPrestados));
                if (varAdicionarRaloLinear5 > 0 || varAdicionarRaloLinear5_1 > 0)
                    document.add(new Paragraph(">>> Instalação de ralo linear : " + (Double.parseDouble(valorHidraulicaRaloLinear5.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear5_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRaloLinear5_1 + varAdicionarRaloLinear5) + "0", boldServicosPrestados));
                if (varAdicionarVasoSanitario5 > 0 || varAdicionarVasoSanitario5_1 > 0)
                    document.add(new Paragraph(">>> Instalar Vaso Sanitário : " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario5.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario5_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarVasoSanitario5_1 + varAdicionarVasoSanitario5) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
            if (valorTotalHidraulicaSacada > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha;

                tableCozinha.addCell(paragrafoHidraulicaSacadaVaranda);
                document.add(tableCozinha);
                if (varAdicionarTorneiraEletrica6 > 0 || varAdicionarTorneiraEletrica6_1 > 0)
                    document.add(new Paragraph(">>> Torneira elétrica :" + (Double.parseDouble(valorHidraulicaTorneiraEletrica6.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica6_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraEletrica6_1 + varAdicionarTorneiraEletrica6) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraMonocomando > 0 || varAdicionarTorneiraMonocomando6_1 > 0)
                    document.add(new Paragraph(">>> Torneira monocomando : " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando6.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando6_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraMonocomando6 + varAdicionarTorneiraMonocomando6_1) + "0", boldServicosPrestados));
                if (varAdicionarTorneiraSimples6 > 0 || varAdicionarTorneiraSimples6_1 > 0)
                    document.add(new Paragraph(">>> Torneira simples : " + (Double.parseDouble(valorHidraulicaTorneiraSimples6.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples6_1.getText().toString())) + " un -----" + "R$" + (varAdicionarTorneiraSimples6_1 + varAdicionarTorneiraSimples6) + "0", boldServicosPrestados));
                if (varAdicionarValvulaSifao6 > 0 || varAdicionarValvulaSifao6_1 > 0)
                    document.add(new Paragraph(">>> Válvula e sifão simples : " + (Double.parseDouble(valorHidraulicaValvulaSifao6_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao6.getText().toString())) + " un ----- " + "R$" + (varAdicionarValvulaSifao6 + varAdicionarValvulaSifao6_1) + "0", boldServicosPrestados));
                if (varAdicionarRegistroAcabamento6 > 0 || varAdicionarRegistroAcabamento6_1 > 0)
                    document.add(new Paragraph(">>> Registros e acabamento : " + (Double.parseDouble(valorHidraulicaRegistroAcabamento6_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento6.getText().toString())) + " un ----- " + "    R$" + (varAdicionarRegistroAcabamento6 + varAdicionarRegistroAcabamento6_1) + "0", boldServicosPrestados));
                if (varAdicionarPontoAgua_6 > 0 || varAdicionarPontoAgua_6_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de água : " + (Double.parseDouble(valorHidraulicaCriacaoAgua6.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua6_1.getText().toString())) + " un -----" + "  R$" + (varAdicionarPontoAgua_6_1 + varAdicionarPontoAgua_6) + "0", boldServicosPrestados));
                if (varAdicionarPontoEsgoto_6 > 0 || varAdicionarPontoEsgoto_6_1 > 0)
                    document.add(new Paragraph(">>> Criação de ponto de esgoto : " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto6.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto6_1.getText().toString())) + " un ----- " + "    R$" + (varAdicionarPontoEsgoto_6_1 + varAdicionarPontoEsgoto_6) + "0", boldServicosPrestados));
                if (varAdicionarRalo10cm6 > 0 || varAdicionarRalo10cm6_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo de 10cm : " + (Double.parseDouble(valorHidraulicaRalo10cm6_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm6.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo10cm6 + varAdicionarRalo10cm6_1) + "0", boldServicosPrestados));
                if (varAdicionarRalo15cm6 > 0 || varAdicionarRalo15cm6_1 > 0)
                    document.add(new Paragraph(">>> Instalação Ralo 16cm : " + (Double.parseDouble(valorHidraulicaRalo15cm6_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm6.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRalo15cm6 + varAdicionarRalo15cm6_1) + "0", boldServicosPrestados));
                if (varAdicionarChuveiro6 > 0 || varAdicionarChuveiro6_1 > 0)
                    document.add(new Paragraph(">>> Chuveiro : " + (Double.parseDouble(valorHidraulicaChuveiro6.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro6_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarChuveiro6_1 + varAdicionarChuveiro6) + "0", boldServicosPrestados));
                if (varAdicionarRaloLinear6 > 0 || varAdicionarRaloLinear6_1 > 0)
                    document.add(new Paragraph(">>> Instalação de ralo linear : " + (Double.parseDouble(valorHidraulicaRaloLinear6.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear6_1.getText().toString())) + " un ----- " + "   R$" + (varAdicionarRaloLinear6_1 + varAdicionarRaloLinear6) + "0", boldServicosPrestados));
                document.add(Chunk.NEWLINE);
            }
        }

        if (BtncheckBoxArtArCondicionado.isChecked() || BtncheckboxArtBox.isChecked() || BtncheckboxArtDemolicao.isChecked() || BtncheckboxArtDeslocamento.isChecked() || BtncheckboxArtEletrica.isChecked() || BtncheckboxArtEnvidracamento.isChecked() || BtncheckboxArtHidraulica.isChecked() || BtncheckboxArtMoveisPlanejados.isChecked() || BtncheckboxArtGesso.isChecked() || BtncheckboxArtNovosRevestimentos.isChecked() || BtncheckboxArtPedrasMarmore.isChecked()) {

            PdfPTable tableDemolicao = new PdfPTable(1);
            tableDemolicao.setWidthPercentage(100);
            PdfPCell demolicao = new PdfPCell();
            demolicao.setBorder(Rectangle.NO_BORDER);
            demolicao.addElement(paragrafoAreaServico);

            document.add(paragrafoArt);
            document.add(espacoBranco);
            document.add(Chunk.NEWLINE);

            PdfPTable tableCozinha = new PdfPTable(1);
            tableCozinha.setWidthPercentage(100);
            PdfPCell cellCozinha;


            if (BtncheckBoxArtArCondicionado.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtArcondicionado;
            }
            if (BtncheckboxArtEnvidracamento.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtEnvidracamento;
            }
            if (BtncheckboxArtPedrasMarmore.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtPedrasMarmore;
            }
            if (BtncheckboxArtNovosRevestimentos.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtNovosRevestimentos;
            }
            if (BtncheckboxArtEletrica.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtEletrica;
            }
            if (BtncheckboxArtHidraulica.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtHidraulica;

            }
            if (BtncheckboxArtBox.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtBox;

            }
            if (BtncheckboxArtGesso.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtGesso;


            }
            if (BtncheckboxArtDemolicao.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtDemolicaoParedeNaoEstrutural;

            }
            if (BtncheckboxArtMoveisPlanejados.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtMoveisPlanejados;

            }
            if (BtncheckboxArtDeslocamento.isChecked()) {
                valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtDeslocamentoPontoGas;

            }


            Paragraph paragrafoArtTotal = new Paragraph("ART                                                                                        Total R$" + (valorTotalCategoriaArt + valorTotalArtTaxa) , boldServicos);

            tableCozinha.addCell(paragrafoArtTotal);
            document.add(tableCozinha);


            if (BtncheckBoxArtArCondicionado.isChecked()) {
                document.add(new Paragraph(">>> Ar Condicionado " + "R$" + (valorTotalArtArcondicionado), boldServicosPrestados));
            }
            if (BtncheckboxArtEnvidracamento.isChecked()) {
                document.add(new Paragraph(">>> Envidraçamento de sacada " + "R$" + (valorTotalArtEnvidracamento), boldServicosPrestados));
            }
            if (BtncheckboxArtPedrasMarmore.isChecked()) {
                document.add(new Paragraph(">>> Pedras de mármores/granito (Pia, lavatório e tanque) " + "R$" + (valorTotalArtPedrasMarmore), boldServicosPrestados));
            }
            if (BtncheckboxArtNovosRevestimentos.isChecked()) {
                document.add(new Paragraph(">>> Novos revestimentos (demolição e instalação de porcelanato/cerâmica)" + "R$" + (valorTotalArtNovosRevestimentos), boldServicosPrestados));
            }
            if (BtncheckboxArtEletrica.isChecked()) {
                document.add(new Paragraph(">>> Elétrica (Instalações, ramificações e deslocamento)" + "R$" + (valorTotalArtEletrica), boldServicosPrestados));
            }
            if (BtncheckboxArtHidraulica.isChecked()) {
                document.add(new Paragraph(">>> Hidráulica (Instalações, ramificações e deslocamento)" + "R$" + (valorTotalArtHidraulica), boldServicosPrestados));

            }
            if (BtncheckboxArtBox.isChecked()) {
                document.add(new Paragraph(">>> Box, espelhos, laminados/vinílico e Acessórios (chuveiro, prateleira, cortinas, etc.)" + "R$" + (valorTotalArtBox), boldServicosPrestados));

            }
            if (BtncheckboxArtGesso.isChecked()) {
                document.add(new Paragraph(">>> Gesso (Sanca, rebaixo, golas, molduras, Dry-wall) e Pintura Decorativa" + "R$" + (valorTotalArtGesso), boldServicosPrestados));


            }
            if (BtncheckboxArtDemolicao.isChecked()) {
                document.add(new Paragraph(">>> Demolição de parede não estrutural" + "R$" + (valorTotalArtDemolicaoParedeNaoEstrutural), boldServicosPrestados));

            }
            if (BtncheckboxArtMoveisPlanejados.isChecked()) {
                document.add(new Paragraph(">>> Móveis planejados" + "R$" + (valorTotalArtMoveisPlanejados), boldServicosPrestados));

            }
            if (BtncheckboxArtDeslocamento.isChecked()) {
                document.add(new Paragraph(">>> Deslocamento do ponto de abastecimento de gás." + "R$" + (valorTotalArtDeslocamentoPontoGas), boldServicosPrestados));

            }
            document.add(new Paragraph(">>> Taxas não inclusas (R$ 82,94) ", boldServicosPrestados));

            document.add(Chunk.NEWLINE);
            valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtTaxa;
            latitude = totalDemolicao + valorTotalCategoriaHidraulica + valorTotalCategoriaPintura + valorTotalCategoriaRevestimento + valorTotalCategoriaArt;
            DecimalFormat df = new DecimalFormat("###,##0.00");

            String valorNotaTexto = df.format(latitude);
            SharedPreferences mypref2 = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor2 = mypref.edit();
            editor2.putString("valorNota", valorNotaTexto);
            editor2.commit();
            alterarValorNota = Integer.toString(numeroNotaAtual);
            exibirNota.setText("000" + alterarNumeroNota);
            exibirValorNota.setText(valorNotaTexto);




            //Valor Total
            Paragraph valorTotal = new Paragraph("TOTAL R$ " + valorNotaTexto , boldTotal);
            valorTotal.setAlignment(Element.ALIGN_CENTER);
            document.add(Chunk.NEWLINE);
            document.add(valorTotal);

        }


        document.add(Chunk.NEWLINE);
        document.add(tableProposta);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("- Validade desde documento 30 dias a contar da data de envio.\n" +
                "- Itens não listados acima acordar valor em outra planilha.\n" +
                "- Este orçamento não tem validade para itens orçados separadamente.\n"));
        // document.add(new LineSeparator());
        document.add(Chunk.NEWLINE);
        document.add(paragrafoDisposicao);
        document.close();
         viewPdf();

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
            pdfFile = new File(docsFolder.getAbsolutePath(), "Orcamento" + "000" + alterarNumeroNota + ".pdf");
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
            createPdf();
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
    private void viewPdf(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT,"Orcamento");
        email.putExtra(Intent.EXTRA_TEXT,"Olá ! \n\nSegue por anexo, o nosso Orçamento");
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RelatóriosRagonezi/Orcamentos");
        pdfFile = new File(docsFolder.getAbsolutePath(), "Orcamento" + "000" + alterarNumeroNota + ".pdf");
        Uri uri = FileProvider.getUriForFile(this, "vostore.apporcamentoragonezi", pdfFile);
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("message/rfc822");
        startActivity(email);
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
        // Firebase sign out

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


        valorTotalCategoriaArt2 = 0;
        if (BtncheckBoxArtArCondicionado.isChecked() || BtncheckboxArtEnvidracamento.isChecked() || BtncheckboxArtPedrasMarmore.isChecked() || BtncheckboxArtNovosRevestimentos.isChecked() || BtncheckboxArtEletrica.isChecked() || BtncheckboxArtHidraulica.isChecked() || BtncheckboxArtBox.isChecked() || BtncheckboxArtGesso.isChecked() || BtncheckboxArtDemolicao.isChecked() || BtncheckboxArtMoveisPlanejados.isChecked() || BtncheckboxArtDeslocamento.isChecked() )
        {
            valorTotalCategoriaArt2 = valorTotalCategoriaArt2 + valorTotalArtTaxa ;

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

        latitude2 = 0;
        latitude2 = totalDemolicao + valorTotalCategoriaHidraulica + valorTotalCategoriaPintura + valorTotalCategoriaRevestimento + valorTotalCategoriaArt2;
        DecimalFormat df = new DecimalFormat("###,##0.00");

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
