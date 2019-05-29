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



public class Main2Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public String caminhoDaImagem;
    public Uri uri;
    //  public ImageButton btn1, btn2, btn3, btn4, btn5, btn6;
    public Button btnART, btnCam, btnDemolicao, btnRevestimento, btnHidraulica, btnPintura, btnCalculadora, btnFinalizar;
    public LinearLayout linearART, linearDemolicao, linearPintura, linearRevestimento, linearHidraulica, linearFinalizar;
    public FirebaseAuth mAuth, auth;
    public GoogleApiClient googleApiClient1;
    public final int TAB_FIRST = FragNavController.TAB1;
    public final int TAB_SECOND = FragNavController.TAB2;
    public final int TAB_THIRD = FragNavController.TAB3;
    public FragNavController fragNavController;


    public static final int CALC_REQUEST_CODE = 0;

    public BigDecimal value;

    final public int REQUEST_CODE_ASK_PERMISSIONS = 111;
    public RelativeLayout relativeLayout;
    public Document document;
    public CheckBox checkBoxCozinha;
    public CheckBox BtncheckBoxArtArCondicionado, BtncheckboxArtEnvidracamento, BtncheckboxArtPedrasMarmore, BtncheckboxArtNovosRevestimentos, BtncheckboxArtEletrica, BtncheckboxArtHidraulica, BtncheckboxArtBox, BtncheckboxArtGesso, BtncheckboxArtDemolicao, BtncheckboxArtMoveisPlanejados, BtncheckboxArtDeslocamento;
    public CheckBox checkBoxPinturaCozinha, checkBoxPinturaBanheiroSocial, checkBoxPinturaAreaServico, checkBoxPinturaBanheiroSuite, checkBoxPinturaLavabo, checkBoxPinturaSacadaVaranda, checkBoxPinturaSalaJantar, checkBoxPinturaSalaEstar, checkBoxPinturaQuartoSuite, checkBoxPinturaQuarto1, checkBoxPinturaQuarto2, checkBoxPinturaM2;
    public CheckBox checkBoxHidraulicaCozinha, checkBoxHidraulicaBanheiroSocial, checkBoxHidraulicaAreaServico, checkBoxHidraulicaBanheiroSuite, checkBoxHidraulicaLavabo, checkBoxHidraulicaSacadaVaranda, checkBoxHidraulicaSalaJantar, checkBoxHidraulicaSalaEstar, checkBoxHidraulicaQuartoSuite, checkBoxHidraulicaQuarto1, checkBoxHidraulicaQuarto2;
    public CheckBox checkBoxRevestimentoCozinha, checkBoxRevestimentoBanheiroSocial, checkBoxRevestimentoAreaServico, checkBoxRevestimentoBanheiroSuite, checkBoxRevestimentoLavabo, checkBoxRevestimentoSacadaVaranda, checkBoxRevestimentoSalaJantar, checkBoxRevestimentoSalaEstar, checkBoxRevestimentoQuartoSuite, checkBoxRevestimentoQuarto1, checkBoxRevestimentoQuarto2;
    public CheckBox checkBoxBanheiroSocial, checkBoxAreaServico, checkBoxBanheiroSuite, checkBoxLavabo, checkBoxSacadaVaranda, checkBoxSalaJantar, checkBoxSalaEstar, checkBoxQuartoSuite, checkBoxQuarto1, checkBoxQuarto2;
    public LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9, linearLayout10, linearLayout11;
    public LinearLayout linearLayoutPintura1, linearLayoutPintura2, linearLayoutPintura3, linearLayoutPintura4, linearLayoutPintura5, linearLayoutPintura6, linearLayoutPintura7, linearLayoutPintura8, linearLayoutPintura9, linearLayoutPintura10, linearLayoutPintura11, linearLayoutPintura12;
    public LinearLayout linearLayoutHidraulica1, linearLayoutHidraulica2, linearLayoutHidraulica3, linearLayoutHidraulica4, linearLayoutHidraulica5, linearLayoutHidraulica6, linearLayoutHidraulica7, linearLayoutHidraulica8, linearLayoutHidraulica9, linearLayoutHidraulica10, linearLayoutHidraulica11;
    public LinearLayout linearLayoutRevestimento1, linearLayoutRevestimento2, linearLayoutRevestimento3, linearLayoutRevestimento4, linearLayoutRevestimento5, linearLayoutRevestimento6, linearLayoutRevestimento7, linearLayoutRevestimento8, linearLayoutRevestimento9, linearLayoutRevestimento10, linearLayoutRevestimento11;
    public EditText valorRevestimentoParede1, valorRevestimentoParede1_1, valorRemocaoPiso1, valorRemocaoPiso1_1, valorRemocaoPia1, valorRemocaoPia1_1, valorRemocacAlvenaria1, valorRemocacAlvenaria1_1, valorRemocaoTanque1, valorRemocaoTanque1_1, valorRasgarCaixinha4x2_1, valorRasgarCaixinha4x2_1_1, valorRasgarCaixinha4x4_1, valorRasgarCaixinha4x4_1_1, valorRasgarHidraulica1, valorRasgarHidraulica1_1, valorRemoverGesso1, valorRemoverGesso1_1, valorRemoverVaso1, valorRemoverVaso1_1, valorRemoverVao1, valorRemoverVao1_1,
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
    public EditText valorRevestimentoAlvenariaBase1, valorRevestimentoAlvenariaBase1_1, valorRevestimentoAlvenariaBase2, valorRevestimentoAlvenariaBase2_1, valorRevestimentoAlvenariaBase3, valorRevestimentoAlvenariaBase3_1, valorRevestimentoAlvenariaBase4, valorRevestimentoAlvenariaBase4_1, valorRevestimentoAlvenariaBase5, valorRevestimentoAlvenariaBase5_1, valorRevestimentoAlvenariaBase6, valorRevestimentoAlvenariaBase6_1, valorRevestimentoAlvenariaBase7, valorRevestimentoAlvenariaBase7_1, valorRevestimentoAlvenariaBase8, valorRevestimentoAlvenariaBase8_1, valorRevestimentoAlvenariaBase9, valorRevestimentoAlvenariaBase9_1, valorRevestimentoAlvenariaBase10, valorRevestimentoAlvenariaBase10_1, valorRevestimentoAlvenariaBase11, valorRevestimentoAlvenariaBase11_1,
            valorRevestimentoContraPiso1, valorRevestimentoContraPiso1_1, valorRevestimentoContraPiso2, valorRevestimentoContraPiso2_1, valorRevestimentoContraPiso3, valorRevestimentoContraPiso3_1, valorRevestimentoContraPiso4, valorRevestimentoContraPiso4_1, valorRevestimentoContraPiso5, valorRevestimentoContraPiso5_1, valorRevestimentoContraPiso6, valorRevestimentoContraPiso6_1, valorRevestimentoContraPiso7, valorRevestimentoContraPiso7_1, valorRevestimentoContraPiso8, valorRevestimentoContraPiso8_1, valorRevestimentoContraPiso9, valorRevestimentoContraPiso9_1, valorRevestimentoContraPiso10, valorRevestimentoContraPiso10_1, valorRevestimentoContraPiso11, valorRevestimentoContraPiso11_1,
            valorRevestimentoImpermeabilidade1, valorRevestimentoImpermeabilidade1_1, valorRevestimentoImpermeabilidade2, valorRevestimentoImpermeabilidade2_1, valorRevestimentoImpermeabilidade3, valorRevestimentoImpermeabilidade3_1, valorRevestimentoImpermeabilidade4, valorRevestimentoImpermeabilidade4_1, valorRevestimentoImpermeabilidade5, valorRevestimentoImpermeabilidade5_1, valorRevestimentoImpermeabilidade6, valorRevestimentoImpermeabilidade6_1, valorRevestimentoImpermeabilidade7, valorRevestimentoImpermeabilidade7_1, valorRevestimentoImpermeabilidade8, valorRevestimentoImpermeabilidade8_1, valorRevestimentoImpermeabilidade9, valorRevestimentoImpermeabilidade9_1, valorRevestimentoImpermeabilidade10, valorRevestimentoImpermeabilidade10_1, valorRevestimentoImpermeabilidade11, valorRevestimentoImpermeabilidade11_1,
            valorRevestimentoPorcelanatoMenor1, valorRevestimentoPorcelanatoMenor1_1, valorRevestimentoPorcelanatoMenor2, valorRevestimentoPorcelanatoMenor2_1, valorRevestimentoPorcelanatoMenor3, valorRevestimentoPorcelanatoMenor3_1, valorRevestimentoPorcelanatoMenor4, valorRevestimentoPorcelanatoMenor4_1, valorRevestimentoPorcelanatoMenor5, valorRevestimentoPorcelanatoMenor5_1, valorRevestimentoPorcelanatoMenor6, valorRevestimentoPorcelanatoMenor6_1, valorRevestimentoPorcelanatoMenor7, valorRevestimentoPorcelanatoMenor7_1, valorRevestimentoPorcelanatoMenor8, valorRevestimentoPorcelanatoMenor8_1, valorRevestimentoPorcelanatoMenor9, valorRevestimentoPorcelanatoMenor9_1, valorRevestimentoPorcelanatoMenor10, valorRevestimentoPorcelanatoMenor10_1, valorRevestimentoPorcelanatoMenor11, valorRevestimentoPorcelanatoMenor11_1,
            valorRevestimentoPorcelanatoAcima1, valorRevestimentoPorcelanatoAcima1_1, valorRevestimentoPorcelanatoAcima2, valorRevestimentoPorcelanatoAcima2_1, valorRevestimentoPorcelanatoAcima3, valorRevestimentoPorcelanatoAcima3_1, valorRevestimentoPorcelanatoAcima4, valorRevestimentoPorcelanatoAcima4_1, valorRevestimentoPorcelanatoAcima5, valorRevestimentoPorcelanatoAcima5_1, valorRevestimentoPorcelanatoAcima6, valorRevestimentoPorcelanatoAcima6_1, valorRevestimentoPorcelanatoAcima7, valorRevestimentoPorcelanatoAcima7_1, valorRevestimentoPorcelanatoAcima8, valorRevestimentoPorcelanatoAcima8_1, valorRevestimentoPorcelanatoAcima9, valorRevestimentoPorcelanatoAcima9_1, valorRevestimentoPorcelanatoAcima10, valorRevestimentoPorcelanatoAcima10_1, valorRevestimentoPorcelanatoAcima11, valorRevestimentoPorcelanatoAcima11_1,
            valorRevestimentoPastilhaVidro1, valorRevestimentoPastilhaVidro1_1, valorRevestimentoPastilhaVidro2, valorRevestimentoPastilhaVidro2_1, valorRevestimentoPastilhaVidro3, valorRevestimentoPastilhaVidro3_1, valorRevestimentoPastilhaVidro4, valorRevestimentoPastilhaVidro4_1, valorRevestimentoPastilhaVidro5, valorRevestimentoPastilhaVidro5_1, valorRevestimentoPastilhaVidro6, valorRevestimentoPastilhaVidro6_1, valorRevestimentoPastilhaVidro7, valorRevestimentoPastilhaVidro7_1, valorRevestimentoPastilhaVidro8, valorRevestimentoPastilhaVidro8_1, valorRevestimentoPastilhaVidro9, valorRevestimentoPastilhaVidro9_1, valorRevestimentoPastilhaVidro10, valorRevestimentoPastilhaVidro10_1, valorRevestimentoPastilhaVidro11, valorRevestimentoPastilhaVidro11_1,
            valorRevestimento3D1, valorRevestimento3D1_1, valorRevestimento3D2, valorRevestimento3D2_1, valorRevestimento3D3, valorRevestimento3D3_1, valorRevestimento3D4, valorRevestimento3D4_1, valorRevestimento3D5, valorRevestimento3D5_1, valorRevestimento3D6, valorRevestimento3D6_1, valorRevestimento3D7, valorRevestimento3D7_1, valorRevestimento3D8, valorRevestimento3D8_1, valorRevestimento3D9, valorRevestimento3D9_1, valorRevestimento3D10, valorRevestimento3D10_1, valorRevestimento3D11, valorRevestimento3D11_1;

    //Hidraulica
    public EditText valorHidraulicaTorneiraEletrica1, valorHidraulicaTorneiraEletrica1_1, valorHidraulicaTorneiraEletrica2, valorHidraulicaTorneiraEletrica2_1, valorHidraulicaTorneiraEletrica3, valorHidraulicaTorneiraEletrica3_1, valorHidraulicaTorneiraEletrica4, valorHidraulicaTorneiraEletrica4_1, valorHidraulicaTorneiraEletrica5, valorHidraulicaTorneiraEletrica5_1, valorHidraulicaTorneiraEletrica6, valorHidraulicaTorneiraEletrica6_1, valorHidraulicaTorneiraEletrica7, valorHidraulicaTorneiraEletrica7_1, valorHidraulicaTorneiraEletrica8, valorHidraulicaTorneiraEletrica8_1, valorHidraulicaTorneiraEletrica9, valorHidraulicaTorneiraEletrica9_1, valorHidraulicaTorneiraEletrica10, valorHidraulicaTorneiraEletrica10_1, valorHidraulicaTorneiraEletrica11, valorHidraulicaTorneiraEletrica11_1,
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
    public EditText valorPinturaPorta1, valorPinturaPorta1_1, valorPinturaPorta2, valorPinturaPorta2_1, valorPinturaPorta3, valorPinturaPorta3_1, valorPinturaPorta4, valorPinturaPorta4_1, valorPinturaPorta5, valorPinturaPorta5_1, valorPinturaPorta6, valorPinturaPorta6_1, valorPinturaPorta7, valorPinturaPorta7_1, valorPinturaPorta8, valorPinturaPorta8_1, valorPinturaPorta9, valorPinturaPorta9_1, valorPinturaPorta10, valorPinturaPorta10_1, valorPinturaPorta11, valorPinturaPorta11_1,
            valorPinturaJanela1, valorPinturaJanela1_1, valorPinturaJanela2, valorPinturaJanela2_1, valorPinturaJanela3, valorPinturaJanela3_1, valorPinturaJanela4, valorPinturaJanela4_1, valorPinturaJanela5, valorPinturaJanela5_1, valorPinturaJanela6, valorPinturaJanela6_1, valorPinturaJanela7, valorPinturaJanela7_1, valorPinturaJanela8, valorPinturaJanela8_1, valorPinturaJanela9, valorPinturaJanela9_1, valorPinturaJanela10, valorPinturaJanela10_1, valorPinturaJanela11, valorPinturaJanela11_1,
            valorPinturaEfeitoDecorativo1, valorPinturaEfeitoDecorativo1_1, valorPinturaEfeitoDecorativo2, valorPinturaEfeitoDecorativo2_1, valorPinturaEfeitoDecorativo3, valorPinturaEfeitoDecorativo3_1, valorPinturaEfeitoDecorativo4, valorPinturaEfeitoDecorativo4_1, valorPinturaEfeitoDecorativo5, valorPinturaEfeitoDecorativo5_1, valorPinturaEfeitoDecorativo6, valorPinturaEfeitoDecorativo6_1, valorPinturaEfeitoDecorativo7, valorPinturaEfeitoDecorativo7_1, valorPinturaEfeitoDecorativo8, valorPinturaEfeitoDecorativo8_1, valorPinturaEfeitoDecorativo9, valorPinturaEfeitoDecorativo9_1, valorPinturaEfeitoDecorativo10, valorPinturaEfeitoDecorativo10_1, valorPinturaEfeitoDecorativo11, valorPinturaEfeitoDecorativo11_1,
            valorPinturaReparoGesso1, valorPinturaReparoGesso1_1, valorPinturaReparoGesso2, valorPinturaReparoGesso2_1, valorPinturaReparoGesso3, valorPinturaReparoGesso3_1, valorPinturaReparoGesso4, valorPinturaReparoGesso4_1, valorPinturaReparoGesso5, valorPinturaReparoGesso5_1, valorPinturaReparoGesso6, valorPinturaReparoGesso6_1, valorPinturaReparoGesso7, valorPinturaReparoGesso7_1, valorPinturaReparoGesso8, valorPinturaReparoGesso8_1, valorPinturaReparoGesso9, valorPinturaReparoGesso9_1, valorPinturaReparoGesso10, valorPinturaReparoGesso10_1, valorPinturaReparoGesso11, valorPinturaReparoGesso11_1, valorPinturaApartamento1, valorPinturaApartamento1_1;
    public Button btn_finish;
    public PermissionsChecker checker;
    public Context mContext;
    // double varRemoverRevestimentoParede2,varRemoverRevestimentoParede3,varRemoverRevestimentoParede4,varRemoverRevestimentoParede5,varRemoverRevestimentoParede6,varRemoverRevestimentoParede7,varRemoverRevestimentoParede8,varRemoverRevestimentoParede9,varRemoverRevestimentoParede10,varRemoverRevestimentoParede11;


    //Valores dos serviços de Demolicao

    public double precoRemoverRevestimentoParede = 30.00;
    public double precoRemoverPiso = 30.00;
    public double precoRemoverAlvenaria = 60.00;
    public double precoRemoverPia = 72.00;
    public double precoRemoverTanque = 52.00;
    public double precoRasgarCaixinha4x2 = 50.40;
    public double precoRasgarCaixinha4x4 = 54.40;
    public double precoRemoverVasoSanitario = 68.00;
    public double precoRemoverVao = 120.00;
    public double precoRemoverHidraulica = 54.40;
    public double precoRemoverGesso = 30.00;
    public int numeroNotaAtual;
    public float valorNotaAtual;
    public int numNota;
    public String valorNota;


    public static int TAKE_PICTURE = 1;

    public TextView exibirNota, exibirValorNota;

    //Valores dos serviços de Revestimento
    public double precoCriarAlvenaria = 72.00;
    public double precoCriarContraPiso = 42.00;
    public double precoAplicarImpermeabilizante = 22.00;
    public double precoPorcelanatoMenor = 82.00;
    public double precoPorcelanatoMaior = 102.00;
    public double precoPastilhaVidro = 230.00;
    public double precoRevestimento3D = 82.00;
    public double precoTotalRevestimento = 0;

    //Valores dos serviços de Hidraulica
    public double precoTorneiraEletrica = 90.00;
    public double precoTorneiraMonocomando = 90.00;
    public double precoTorneiraSimples = 65.00;
    public double precoValvulaSifao = 50.00;
    public double precoRegistroAcabamento = 15.00;
    public double precoCriacaoAgua = 95.00;
    public double precoCriacaoEsgoto = 70.00;
    public double precoRalo10cm = 12.00;
    public double precoRalo15cm = 12.00;
    public double precoChuveiro = 90.00;
    public double precoRaloLinear = 95.00;
    public double precoInstalarVasoSanitario = 95.00;

    //Valores dos serviços de Pintura
    public double precoPinturaPorta = 150.00;
    public double precoPinturaJanela = 150.00;
    public double precoPinturaEfeitoDecorativo = 90.00;
    public double precoPinturaReparoGesso = 80.00;
    public double precoPinturaApartament0 = 40.00;


    //Variaveis Demolicao
    public double varRemoverRevestimentoParede = 0;
    public double varRemoverRevestimentoParede1 = 0;
    public double varRemoverRevestimentoParede2 = 0;
    public double varRemoverRevestimentoParede2_1 = 0;
    public double varRemoverRevestimentoParede3 = 0;
    public double varRemoverRevestimentoParede3_1 = 0;
    public double varRemoverRevestimentoParede4 = 0;
    public double varRemoverRevestimentoParede4_1 = 0;
    public double varRemoverRevestimentoParede5 = 0;
    public double varRemoverRevestimentoParede5_1 = 0;
    public double varRemoverRevestimentoParede6 = 0;
    public double varRemoverRevestimentoParede6_1 = 0;
    public double varRemoverRevestimentoParede7 = 0;
    public double varRemoverRevestimentoParede7_1 = 0;
    public double varRemoverRevestimentoParede8 = 0;
    public double varRemoverRevestimentoParede8_1 = 0;
    public double varRemoverRevestimentoParede9 = 0;
    public double varRemoverRevestimentoParede9_1 = 0;
    public double varRemoverRevestimentoParede10 = 0;
    public double varRemoverRevestimentoParede10_1 = 0;
    public double varRemoverRevestimentoParede11 = 0;
    public double varRemoverRevestimentoParede11_1 = 0;
    public double varRemoverPiso = 0;
    public double varRemoverPiso1 = 0;
    public double varRemoverPiso2 = 0;
    public double varRemoverPiso2_1 = 0;
    public double varRemoverPiso3 = 0;
    public double varRemoverPiso3_1 = 0;
    public double varRemoverPiso4 = 0;
    public double varRemoverPiso4_1 = 0;
    public double varRemoverPiso5 = 0;
    public double varRemoverPiso5_1 = 0;
    public double varRemoverPiso6 = 0;
    public double varRemoverPiso6_1 = 0;
    public double varRemoverPiso7 = 0;
    public double varRemoverPiso7_1 = 0;
    public double varRemoverPiso8 = 0;
    public double varRemoverPiso8_1 = 0;
    public double varRemoverPiso9 = 0;
    public double varRemoverPiso9_1 = 0;
    public double varRemoverPiso10 = 0;
    public double varRemoverPiso10_1 = 0;
    public double varRemoverPiso11 = 0;
    public double varRemoverPiso11_1 = 0;
    public double varRemoverPia = 0;
    public double varRemoverPia1 = 0;
    public double varRemoverPia2 = 0;
    public double varRemoverPia2_1 = 0;
    public double varRemoverPia3 = 0;
    public double varRemoverPia3_1 = 0;
    public double varRemoverPia4 = 0;
    public double varRemoverPia4_1 = 0;
    public double varRemoverPia5 = 0;
    public double varRemoverPia5_1 = 0;
    public double varRemoverPia6 = 0;
    public double varRemoverPia6_1 = 0;
    public double varRemoverPia7 = 0;
    public double varRemoverPia7_1 = 0;
    public double varRemoverPia8 = 0;
    public double varRemoverPia8_1 = 0;
    public double varRemoverPia9 = 0;
    public double varRemoverPia9_1 = 0;
    public double varRemoverPia10 = 0;
    public double varRemoverPia10_1 = 0;
    public double varRemoverPia11 = 0;
    public double varRemoverPia11_1 = 0;
    public double varRemoverAlvenaria = 0;
    public double varRemoverAlvenaria1 = 0;
    public double varRemoverAlvenaria2 = 0;
    public double varRemoverAlvenaria2_1 = 0;
    public double varRemoverAlvenaria3 = 0;
    public double varRemoverAlvenaria3_1 = 0;
    public double varRemoverAlvenaria4 = 0;
    public double varRemoverAlvenaria4_1 = 0;
    public double varRemoverAlvenaria5 = 0;
    public double varRemoverAlvenaria5_1 = 0;
    public double varRemoverAlvenaria6 = 0;
    public double varRemoverAlvenaria6_1 = 0;
    public double varRemoverAlvenaria7 = 0;
    public double varRemoverAlvenaria7_1 = 0;
    public double varRemoverAlvenaria8 = 0;
    public double varRemoverAlvenaria8_1 = 0;
    public double varRemoverAlvenaria9 = 0;
    public double varRemoverAlvenaria9_1 = 0;
    public double varRemoverAlvenaria10 = 0;
    public double varRemoverAlvenaria10_1 = 0;
    public double varRemoverAlvenaria11 = 0;
    public double varRemoverAlvenaria11_1 = 0;
    public double varRemoverTanque = 0;
    public double varRemoverTanque1 = 0;
    public double varRemoverTanque2 = 0;
    public double varRemoverTanque2_1 = 0;
    public double varRemoverTanque3 = 0;
    public double varRemoverTanque3_1 = 0;
    public double varRemoverTanque4 = 0;
    public double varRemoverTanque4_1 = 0;
    public double varRemoverTanque5 = 0;
    public double varRemoverTanque5_1 = 0;
    public double varRemoverTanque6 = 0;
    public double varRemoverTanque6_1 = 0;
    public double varRemoverTanque7 = 0;
    public double varRemoverTanque7_1 = 0;
    public double varRemoverTanque8 = 0;
    public double varRemoverTanque8_1 = 0;
    public double varRemoverTanque9 = 0;
    public double varRemoverTanque9_1 = 0;
    public double varRemoverTanque10 = 0;
    public double varRemoverTanque10_1 = 0;
    public double varRemoverTanque11 = 0;
    public double varRemoverTanque11_1 = 0;
    public double varRemoverCaixinha4x2 = 0;
    public double varRemoverCaixinha4x2_1 = 0;
    public double varRemoverCaixinha4x2_2 = 0;
    public double varRemoverCaixinha4x2_2_1 = 0;
    public double varRemoverCaixinha4x2_3 = 0;
    public double varRemoverCaixinha4x2_3_1 = 0;
    public double varRemoverCaixinha4x2_4 = 0;
    public double varRemoverCaixinha4x2_4_1 = 0;
    public double varRemoverCaixinha4x2_5 = 0;
    public double varRemoverCaixinha4x2_5_1 = 0;
    public double varRemoverCaixinha4x2_6 = 0;
    public double varRemoverCaixinha4x2_6_1 = 0;
    public double varRemoverCaixinha4x2_7 = 0;
    public double varRemoverCaixinha4x2_7_1 = 0;
    public double varRemoverCaixinha4x2_8 = 0;
    public double varRemoverCaixinha4x2_8_1 = 0;
    public double varRemoverCaixinha4x2_9 = 0;
    public double varRemoverCaixinha4x2_9_1 = 0;
    public double varRemoverCaixinha4x2_10 = 0;
    public double varRemoverCaixinha4x2_10_1 = 0;
    public double varRemoverCaixinha4x2_11 = 0;
    public double varRemoverCaixinha4x2_11_1 = 0;
    public double varRemoverCaixinha4x4 = 0;
    public double varRemoverCaixinha4x4_1 = 0;
    public double varRemoverCaixinha4x4_2 = 0;
    public double varRemoverCaixinha4x4_2_1 = 0;
    public double varRemoverCaixinha4x4_3 = 0;
    public double varRemoverCaixinha4x4_3_1 = 0;
    public double varRemoverCaixinha4x4_4 = 0;
    public double varRemoverCaixinha4x4_4_1 = 0;
    public double varRemoverCaixinha4x4_5 = 0;
    public double varRemoverCaixinha4x4_5_1 = 0;
    public double varRemoverCaixinha4x4_6 = 0;
    public double varRemoverCaixinha4x4_6_1 = 0;
    public double varRemoverCaixinha4x4_7 = 0;
    public double varRemoverCaixinha4x4_7_1 = 0;
    public double varRemoverCaixinha4x4_8 = 0;
    public double varRemoverCaixinha4x4_8_1 = 0;
    public double varRemoverCaixinha4x4_9 = 0;
    public double varRemoverCaixinha4x4_9_1 = 0;
    public double varRemoverCaixinha4x4_10 = 0;
    public double varRemoverCaixinha4x4_10_1 = 0;
    public double varRemoverCaixinha4x4_11 = 0;
    public double varRemoverCaixinha4x4_11_1 = 0;
    public double varRemoverHidraulica = 0;
    public double varRemoverHidraulica1 = 0;
    public double varRemoverHidraulica2 = 0;
    public double varRemoverHidraulica2_1 = 0;
    public double varRemoverHidraulica3 = 0;
    public double varRemoverHidraulica3_1 = 0;
    public double varRemoverHidraulica4 = 0;
    public double varRemoverHidraulica4_1 = 0;
    public double varRemoverHidraulica5 = 0;
    public double varRemoverHidraulica5_1 = 0;
    public double varRemoverHidraulica6 = 0;
    public double varRemoverHidraulica6_1 = 0;
    public double varRemoverHidraulica7 = 0;
    public double varRemoverHidraulica7_1 = 0;
    public double varRemoverHidraulica8 = 0;
    public double varRemoverHidraulica8_1 = 0;
    public double varRemoverHidraulica9 = 0;
    public double varRemoverHidraulica9_1 = 0;
    public double varRemoverHidraulica10 = 0;
    public double varRemoverHidraulica10_1 = 0;
    public double varRemoverHidraulica11 = 0;
    public double varRemoverHidraulica11_1 = 0;
    public double varRemoverGesso = 0;
    public double varRemoverGesso1 = 0;
    public double varRemoverGesso2 = 0;
    public double varRemoverGesso2_1 = 0;
    public double varRemoverGesso3 = 0;
    public double varRemoverGesso3_1 = 0;
    public double varRemoverGesso4 = 0;
    public double varRemoverGesso4_1 = 0;
    public double varRemoverGesso5 = 0;
    public double varRemoverGesso5_1 = 0;
    public double varRemoverGesso6 = 0;
    public double varRemoverGesso6_1 = 0;
    public double varRemoverGesso7 = 0;
    public double varRemoverGesso7_1 = 0;
    public double varRemoverGesso8 = 0;
    public double varRemoverGesso8_1 = 0;
    public double varRemoverGesso9 = 0;
    public double varRemoverGesso9_1 = 0;
    public double varRemoverGesso10 = 0;
    public double varRemoverGesso10_1 = 0;
    public double varRemoverGesso11 = 0;
    public double varRemoverGesso11_1 = 0;
    public double varRemoverVasoSanitario = 0;
    public double varRemoverVasoSanitario1 = 0;
    public double varRemoverVasoSanitario2 = 0;
    public double varRemoverVasoSanitario2_1 = 0;
    public double varRemoverVasoSanitario3 = 0;
    public double varRemoverVasoSanitario3_1 = 0;
    public double varRemoverVasoSanitario4 = 0;
    public double varRemoverVasoSanitario4_1 = 0;
    public double varRemoverVasoSanitario5 = 0;
    public double varRemoverVasoSanitario5_1 = 0;
    public double varRemoverVasoSanitario6 = 0;
    public double varRemoverVasoSanitario6_1 = 0;
    public double varRemoverVasoSanitario7 = 0;
    public double varRemoverVasoSanitario7_1 = 0;
    public double varRemoverVasoSanitario8 = 0;
    public double varRemoverVasoSanitario8_1 = 0;
    public double varRemoverVasoSanitario9 = 0;
    public double varRemoverVasoSanitario9_1 = 0;
    public double varRemoverVasoSanitario10 = 0;
    public double varRemoverVasoSanitario10_1 = 0;
    public double varRemoverVasoSanitario11 = 0;
    public double varRemoverVasoSanitario11_1 = 0;
    public double varRemoverVao = 0;
    public double varRemoverVao1 = 0;
    public double varRemoverVao2 = 0;
    public double varRemoverVao2_1 = 0;
    public double varRemoverVao3 = 0;
    public double varRemoverVao3_1 = 0;
    public double varRemoverVao4 = 0;
    public double varRemoverVao4_1 = 0;
    public double varRemoverVao5 = 0;
    public double varRemoverVao5_1 = 0;
    public double varRemoverVao6 = 0;
    public double varRemoverVao6_1 = 0;
    public double varRemoverVao7 = 0;
    public double varRemoverVao7_1 = 0;
    public double varRemoverVao8 = 0;
    public double varRemoverVao8_1 = 0;
    public double varRemoverVao9 = 0;
    public double varRemoverVao9_1 = 0;
    public double varRemoverVao10 = 0;
    public double varRemoverVao10_1 = 0;
    public double varRemoverVao11 = 0;
    public double varRemoverVao11_1 = 0;

    //Variaveis Hidraulica
    public double varAdicionarTorneiraEletrica = 0;
    public double varAdicionarTorneiraEletrica1 = 0;
    public double varAdicionarTorneiraEletrica2 = 0;
    public double varAdicionarTorneiraEletrica2_1 = 0;
    public double varAdicionarTorneiraEletrica3 = 0;
    public double varAdicionarTorneiraEletrica3_1 = 0;
    public double varAdicionarTorneiraEletrica4 = 0;
    public double varAdicionarTorneiraEletrica4_1 = 0;
    public double varAdicionarTorneiraEletrica5 = 0;
    public double varAdicionarTorneiraEletrica5_1 = 0;
    public double varAdicionarTorneiraEletrica6 = 0;
    public double varAdicionarTorneiraEletrica6_1 = 0;
    public double varAdicionarTorneiraEletrica7 = 0;
    public double varAdicionarTorneiraEletrica7_1 = 0;
    public double varAdicionarTorneiraEletrica8 = 0;
    public double varAdicionarTorneiraEletrica8_1 = 0;
    public double varAdicionarTorneiraEletrica9 = 0;
    public double varAdicionarTorneiraEletrica9_1 = 0;
    public double varAdicionarTorneiraEletrica10 = 0;
    public double varAdicionarTorneiraEletrica10_1 = 0;
    public double varAdicionarTorneiraEletrica11 = 0;
    public double varAdicionarTorneiraEletrica11_1 = 0;
    public double varAdicionarTorneiraMonocomando = 0;
    public double varAdicionarTorneiraMonocomando1 = 0;
    public double varAdicionarTorneiraMonocomando2 = 0;
    public double varAdicionarTorneiraMonocomando2_1 = 0;
    public double varAdicionarTorneiraMonocomando3 = 0;
    public double varAdicionarTorneiraMonocomando3_1 = 0;
    public double varAdicionarTorneiraMonocomando4 = 0;
    public double varAdicionarTorneiraMonocomando4_1 = 0;
    public double varAdicionarTorneiraMonocomando5 = 0;
    public double varAdicionarTorneiraMonocomando5_1 = 0;
    public double varAdicionarTorneiraMonocomando6 = 0;
    public double varAdicionarTorneiraMonocomando6_1 = 0;
    public double varAdicionarTorneiraMonocomando7 = 0;
    public double varAdicionarTorneiraMonocomando7_1 = 0;
    public double varAdicionarTorneiraMonocomando8 = 0;
    public double varAdicionarTorneiraMonocomando8_1 = 0;
    public double varAdicionarTorneiraMonocomando9 = 0;
    public double varAdicionarTorneiraMonocomando9_1 = 0;
    public double varAdicionarTorneiraMonocomando10 = 0;
    public double varAdicionarTorneiraMonocomando10_1 = 0;
    public double varAdicionarTorneiraMonocomando11 = 0;
    public double varAdicionarTorneiraMonocomando11_1 = 0;
    public double varAdicionarTorneiraSimples = 0;
    public double varAdicionarTorneiraSimples1 = 0;
    public double varAdicionarTorneiraSimples2 = 0;
    public double varAdicionarTorneiraSimples2_1 = 0;
    public double varAdicionarTorneiraSimples3 = 0;
    public double varAdicionarTorneiraSimples3_1 = 0;
    public double varAdicionarTorneiraSimples4 = 0;
    public double varAdicionarTorneiraSimples4_1 = 0;
    public double varAdicionarTorneiraSimples5 = 0;
    public double varAdicionarTorneiraSimples5_1 = 0;
    public double varAdicionarTorneiraSimples6 = 0;
    public double varAdicionarTorneiraSimples6_1 = 0;
    public double varAdicionarTorneiraSimples7 = 0;
    public double varAdicionarTorneiraSimples7_1 = 0;
    public double varAdicionarTorneiraSimples8 = 0;
    public double varAdicionarTorneiraSimples8_1 = 0;
    public double varAdicionarTorneiraSimples9 = 0;
    public double varAdicionarTorneiraSimples9_1 = 0;
    public double varAdicionarTorneiraSimples10 = 0;
    public double varAdicionarTorneiraSimples10_1 = 0;
    public double varAdicionarTorneiraSimples11 = 0;
    public double varAdicionarTorneiraSimples11_1 = 0;
    public double varAdicionarValvulaSifao = 0;
    public double varAdicionarValvulaSifao1 = 0;
    public double varAdicionarValvulaSifao2 = 0;
    public double varAdicionarValvulaSifao2_1 = 0;
    public double varAdicionarValvulaSifao3 = 0;
    public double varAdicionarValvulaSifao3_1 = 0;
    public double varAdicionarValvulaSifao4 = 0;
    public double varAdicionarValvulaSifao4_1 = 0;
    public double varAdicionarValvulaSifao5 = 0;
    public double varAdicionarValvulaSifao5_1 = 0;
    public double varAdicionarValvulaSifao6 = 0;
    public double varAdicionarValvulaSifao6_1 = 0;
    public double varAdicionarValvulaSifao7 = 0;
    public double varAdicionarValvulaSifao7_1 = 0;
    public double varAdicionarValvulaSifao8 = 0;
    public double varAdicionarValvulaSifao8_1 = 0;
    public double varAdicionarValvulaSifao9 = 0;
    public double varAdicionarValvulaSifao9_1 = 0;
    public double varAdicionarValvulaSifao10 = 0;
    public double varAdicionarValvulaSifao10_1 = 0;
    public double varAdicionarValvulaSifao11 = 0;
    public double varAdicionarValvulaSifao11_1 = 0;
    public double varAdicionarRegistroAcabamento = 0;
    public double varAdicionarRegistroAcabamento1 = 0;
    public double varAdicionarRegistroAcabamento2 = 0;
    public double varAdicionarRegistroAcabamento2_1 = 0;
    public double varAdicionarRegistroAcabamento3 = 0;
    public double varAdicionarRegistroAcabamento3_1 = 0;
    public double varAdicionarRegistroAcabamento4 = 0;
    public double varAdicionarRegistroAcabamento4_1 = 0;
    public double varAdicionarRegistroAcabamento5 = 0;
    public double varAdicionarRegistroAcabamento5_1 = 0;
    public double varAdicionarRegistroAcabamento6 = 0;
    public double varAdicionarRegistroAcabamento6_1 = 0;
    public double varAdicionarRegistroAcabamento7 = 0;
    public double varAdicionarRegistroAcabamento7_1 = 0;
    public double varAdicionarRegistroAcabamento8 = 0;
    public double varAdicionarRegistroAcabamento8_1 = 0;
    public double varAdicionarRegistroAcabamento9 = 0;
    public double varAdicionarRegistroAcabamento9_1 = 0;
    public double varAdicionarRegistroAcabamento10 = 0;
    public double varAdicionarRegistroAcabamento10_1 = 0;
    public double varAdicionarRegistroAcabamento11 = 0;
    public double varAdicionarRegistroAcabamento11_1 = 0;
    public double varAdicionarPontoAgua = 0;
    public double varAdicionarPontoAgua_1 = 0;
    public double varAdicionarPontoAgua_2 = 0;
    public double varAdicionarPontoAgua_2_1 = 0;
    public double varAdicionarPontoAgua_3 = 0;
    public double varAdicionarPontoAgua_3_1 = 0;
    public double varAdicionarPontoAgua_4 = 0;
    public double varAdicionarPontoAgua_4_1 = 0;
    public double varAdicionarPontoAgua_5 = 0;
    public double varAdicionarPontoAgua_5_1 = 0;
    public double varAdicionarPontoAgua_6 = 0;
    public double varAdicionarPontoAgua_6_1 = 0;
    public double varAdicionarPontoAgua_7 = 0;
    public double varAdicionarPontoAgua_7_1 = 0;
    public double varAdicionarPontoAgua_8 = 0;
    public double varAdicionarPontoAgua_8_1 = 0;
    public double varAdicionarPontoAgua_9 = 0;
    public double varAdicionarPontoAgua_9_1 = 0;
    public double varAdicionarPontoAgua_10 = 0;
    public double varAdicionarPontoAgua_10_1 = 0;
    public double varAdicionarPontoAgua_11 = 0;
    public double varAdicionarPontoAgua_11_1 = 0;
    public double varAdicionarPontoEsgoto = 0;
    public double varAdicionarPontoEsgoto_1 = 0;
    public double varAdicionarPontoEsgoto_2 = 0;
    public double varAdicionarPontoEsgoto_2_1 = 0;
    public double varAdicionarPontoEsgoto_3 = 0;
    public double varAdicionarPontoEsgoto_3_1 = 0;
    public double varAdicionarPontoEsgoto_4 = 0;
    public double varAdicionarPontoEsgoto_4_1 = 0;
    public double varAdicionarPontoEsgoto_5 = 0;
    public double varAdicionarPontoEsgoto_5_1 = 0;
    public double varAdicionarPontoEsgoto_6 = 0;
    public double varAdicionarPontoEsgoto_6_1 = 0;
    public double varAdicionarPontoEsgoto_7 = 0;
    public double varAdicionarPontoEsgoto_7_1 = 0;
    public double varAdicionarPontoEsgoto_8 = 0;
    public double varAdicionarPontoEsgoto_8_1 = 0;
    public double varAdicionarPontoEsgoto_9 = 0;
    public double varAdicionarPontoEsgoto_9_1 = 0;
    public double varAdicionarPontoEsgoto_10 = 0;
    public double varAdicionarPontoEsgoto_10_1 = 0;
    public double varAdicionarPontoEsgoto_11 = 0;
    public double varAdicionarPontoEsgoto_11_1 = 0;
    public double varAdicionarRalo10cm = 0;
    public double varAdicionarRalo10cm1 = 0;
    public double varAdicionarRalo10cm2 = 0;
    public double varAdicionarRalo10cm2_1 = 0;
    public double varAdicionarRalo10cm3 = 0;
    public double varAdicionarRalo10cm3_1 = 0;
    public double varAdicionarRalo10cm4 = 0;
    public double varAdicionarRalo10cm4_1 = 0;
    public double varAdicionarRalo10cm5 = 0;
    public double varAdicionarRalo10cm5_1 = 0;
    public double varAdicionarRalo10cm6 = 0;
    public double varAdicionarRalo10cm6_1 = 0;
    public double varAdicionarRalo10cm7 = 0;
    public double varAdicionarRalo10cm7_1 = 0;
    public double varAdicionarRalo10cm8 = 0;
    public double varAdicionarRalo10cm8_1 = 0;
    public double varAdicionarRalo10cm9 = 0;
    public double varAdicionarRalo10cm9_1 = 0;
    public double varAdicionarRalo10cm10 = 0;
    public double varAdicionarRalo10cm10_1 = 0;
    public double varAdicionarRalo10cm11 = 0;
    public double varAdicionarRalo10cm11_1 = 0;
    public double varAdicionarRalo15cm = 0;
    public double varAdicionarRalo15cm1 = 0;
    public double varAdicionarRalo15cm2 = 0;
    public double varAdicionarRalo15cm2_1 = 0;
    public double varAdicionarRalo15cm3 = 0;
    public double varAdicionarRalo15cm3_1 = 0;
    public double varAdicionarRalo15cm4 = 0;
    public double varAdicionarRalo15cm4_1 = 0;
    public double varAdicionarRalo15cm5 = 0;
    public double varAdicionarRalo15cm5_1 = 0;
    public double varAdicionarRalo15cm6 = 0;
    public double varAdicionarRalo15cm6_1 = 0;
    public double varAdicionarRalo15cm7 = 0;
    public double varAdicionarRalo15cm7_1 = 0;
    public double varAdicionarRalo15cm8 = 0;
    public double varAdicionarRalo15cm8_1 = 0;
    public double varAdicionarRalo15cm9 = 0;
    public double varAdicionarRalo15cm9_1 = 0;
    public double varAdicionarRalo15cm10 = 0;
    public double varAdicionarRalo15cm10_1 = 0;
    public double varAdicionarRalo15cm11 = 0;
    public double varAdicionarRalo15cm11_1 = 0;
    public double varAdicionarChuveiro = 0;
    public double varAdicionarChuveiro1 = 0;
    public double varAdicionarChuveiro2 = 0;
    public double varAdicionarChuveiro2_1 = 0;
    public double varAdicionarChuveiro3 = 0;
    public double varAdicionarChuveiro3_1 = 0;
    public double varAdicionarChuveiro4 = 0;
    public double varAdicionarChuveiro4_1 = 0;
    public double varAdicionarChuveiro5 = 0;
    public double varAdicionarChuveiro5_1 = 0;
    public double varAdicionarChuveiro6 = 0;
    public double varAdicionarChuveiro6_1 = 0;
    public double varAdicionarChuveiro7 = 0;
    public double varAdicionarChuveiro7_1 = 0;
    public double varAdicionarChuveiro8 = 0;
    public double varAdicionarChuveiro8_1 = 0;
    public double varAdicionarChuveiro9 = 0;
    public double varAdicionarChuveiro9_1 = 0;
    public double varAdicionarChuveiro10 = 0;
    public double varAdicionarChuveiro10_1 = 0;
    public double varAdicionarChuveiro11 = 0;
    public double varAdicionarChuveiro11_1 = 0;
    public double varAdicionarRaloLinear = 0;
    public double varAdicionarRaloLinear1 = 0;
    public double varAdicionarRaloLinear2 = 0;
    public double varAdicionarRaloLinear2_1 = 0;
    public double varAdicionarRaloLinear3 = 0;
    public double varAdicionarRaloLinear3_1 = 0;
    public double varAdicionarRaloLinear4 = 0;
    public double varAdicionarRaloLinear4_1 = 0;
    public double varAdicionarRaloLinear5 = 0;
    public double varAdicionarRaloLinear5_1 = 0;
    public double varAdicionarRaloLinear6 = 0;
    public double varAdicionarRaloLinear6_1 = 0;
    public double varAdicionarRaloLinear7 = 0;
    public double varAdicionarRaloLinear7_1 = 0;
    public double varAdicionarRaloLinear8 = 0;
    public double varAdicionarRaloLinear8_1 = 0;
    public double varAdicionarRaloLinear9 = 0;
    public double varAdicionarRaloLinear9_1 = 0;
    public double varAdicionarRaloLinear10 = 0;
    public double varAdicionarRaloLinear10_1 = 0;
    public double varAdicionarRaloLinear11 = 0;
    public double varAdicionarRaloLinear11_1 = 0;
    public double varAdicionarVasoSanitario = 0;
    public double varAdicionarVasoSanitario1 = 0;
    public double varAdicionarVasoSanitario2 = 0;
    public double varAdicionarVasoSanitario2_1 = 0;
    public double varAdicionarVasoSanitario3 = 0;
    public double varAdicionarVasoSanitario3_1 = 0;
    public double varAdicionarVasoSanitario4 = 0;
    public double varAdicionarVasoSanitario4_1 = 0;
    public double varAdicionarVasoSanitario5 = 0;
    public double varAdicionarVasoSanitario5_1 = 0;
    public double varAdicionarVasoSanitario6 = 0;
    public double varAdicionarVasoSanitario6_1 = 0;

    //Variaveis Revestimento
    public double varAdicionarAlvenaria = 0;
    public double varAdicionarAlvenaria1 = 0;
    public double varAdicionarAlvenaria2 = 0;
    public double varAdicionarAlvenaria2_1 = 0;
    public double varAdicionarAlvenaria3 = 0;
    public double varAdicionarAlvenaria3_1 = 0;
    public double varAdicionarAlvenaria4 = 0;
    public double varAdicionarAlvenaria4_1 = 0;
    public double varAdicionarAlvenaria5 = 0;
    public double varAdicionarAlvenaria5_1 = 0;
    public double varAdicionarAlvenaria6 = 0;
    public double varAdicionarAlvenaria6_1 = 0;
    public double varAdicionarAlvenaria7 = 0;
    public double varAdicionarAlvenaria7_1 = 0;
    public double varAdicionarAlvenaria8 = 0;
    public double varAdicionarAlvenaria8_1 = 0;
    public double varAdicionarAlvenaria9 = 0;
    public double varAdicionarAlvenaria9_1 = 0;
    public double varAdicionarAlvenaria10 = 0;
    public double varAdicionarAlvenaria10_1 = 0;
    public double varAdicionarAlvenaria11 = 0;
    public double varAdicionarAlvenaria11_1 = 0;
    public double varAdicionarContraPiso = 0;
    public double varAdicionarContraPiso1 = 0;
    public double varAdicionarContraPiso2 = 0;
    public double varAdicionarContraPiso2_1 = 0;
    public double varAdicionarContraPiso3 = 0;
    public double varAdicionarContraPiso3_1 = 0;
    public double varAdicionarContraPiso4 = 0;
    public double varAdicionarContraPiso4_1 = 0;
    public double varAdicionarContraPiso5 = 0;
    public double varAdicionarContraPiso5_1 = 0;
    public double varAdicionarContraPiso6 = 0;
    public double varAdicionarContraPiso6_1 = 0;
    public double varAdicionarContraPiso7 = 0;
    public double varAdicionarContraPiso7_1 = 0;
    public double varAdicionarContraPiso8 = 0;
    public double varAdicionarContraPiso8_1 = 0;
    public double varAdicionarContraPiso9 = 0;
    public double varAdicionarContraPiso9_1 = 0;
    public double varAdicionarContraPiso10 = 0;
    public double varAdicionarContraPiso10_1 = 0;
    public double varAdicionarContraPiso11 = 0;
    public double varAdicionarContraPiso11_1 = 0;
    public double varAplicacaoImpermeabilizante = 0;
    public double varAplicacaoImpermeabilizante1 = 0;
    public double varAplicacaoImpermeabilizante2 = 0;
    public double varAplicacaoImpermeabilizante2_1 = 0;
    public double varAplicacaoImpermeabilizante3 = 0;
    public double varAplicacaoImpermeabilizante3_1 = 0;
    public double varAplicacaoImpermeabilizante4 = 0;
    public double varAplicacaoImpermeabilizante4_1 = 0;
    public double varAplicacaoImpermeabilizante5 = 0;
    public double varAplicacaoImpermeabilizante5_1 = 0;
    public double varAplicacaoImpermeabilizante6 = 0;
    public double varAplicacaoImpermeabilizante6_1 = 0;
    public double varAplicacaoImpermeabilizante7 = 0;
    public double varAplicacaoImpermeabilizante7_1 = 0;
    public double varAplicacaoImpermeabilizante8 = 0;
    public double varAplicacaoImpermeabilizante8_1 = 0;
    public double varAplicacaoImpermeabilizante9 = 0;
    public double varAplicacaoImpermeabilizante9_1 = 0;
    public double varAplicacaoImpermeabilizante10 = 0;
    public double varAplicacaoImpermeabilizante10_1 = 0;
    public double varAplicacaoImpermeabilizante11 = 0;
    public double varAplicacaoImpermeabilizante11_1 = 0;
    public double varAplicarPorcelanatoMenor = 0;
    public double varAplicarPorcelanatoMenor1 = 0;
    public double varAplicarPorcelanatoMenor2 = 0;
    public double varAplicarPorcelanatoMenor2_1 = 0;
    public double varAplicarPorcelanatoMenor3 = 0;
    public double varAplicarPorcelanatoMenor3_1 = 0;
    public double varAplicarPorcelanatoMenor4 = 0;
    public double varAplicarPorcelanatoMenor4_1 = 0;
    public double varAplicarPorcelanatoMenor5 = 0;
    public double varAplicarPorcelanatoMenor5_1 = 0;
    public double varAplicarPorcelanatoMenor6 = 0;
    public double varAplicarPorcelanatoMenor6_1 = 0;
    public double varAplicarPorcelanatoMenor7 = 0;
    public double varAplicarPorcelanatoMenor7_1 = 0;
    public double varAplicarPorcelanatoMenor8 = 0;
    public double varAplicarPorcelanatoMenor8_1 = 0;
    public double varAplicarPorcelanatoMenor9 = 0;
    public double varAplicarPorcelanatoMenor9_1 = 0;
    public double varAplicarPorcelanatoMenor10 = 0;
    public double varAplicarPorcelanatoMenor10_1 = 0;
    public double varAplicarPorcelanatoMenor11 = 0;
    public double varAplicarPorcelanatoMenor11_1 = 0;
    public double varAplicarPorcelanatoMaior = 0;
    public double varAplicarPorcelanatoMaior1 = 0;
    public double varAplicarPorcelanatoMaior2 = 0;
    public double varAplicarPorcelanatoMaior2_1 = 0;
    public double varAplicarPorcelanatoMaior3 = 0;
    public double varAplicarPorcelanatoMaior3_1 = 0;
    public double varAplicarPorcelanatoMaior4 = 0;
    public double varAplicarPorcelanatoMaior4_1 = 0;
    public double varAplicarPorcelanatoMaior5 = 0;
    public double varAplicarPorcelanatoMaior5_1 = 0;
    public double varAplicarPorcelanatoMaior6 = 0;
    public double varAplicarPorcelanatoMaior6_1 = 0;
    public double varAplicarPorcelanatoMaior7 = 0;
    public double varAplicarPorcelanatoMaior7_1 = 0;
    public double varAplicarPorcelanatoMaior8 = 0;
    public double varAplicarPorcelanatoMaior8_1 = 0;
    public double varAplicarPorcelanatoMaior9 = 0;
    public double varAplicarPorcelanatoMaior9_1 = 0;
    public double varAplicarPorcelanatoMaior10 = 0;
    public double varAplicarPorcelanatoMaior10_1 = 0;
    public double varAplicarPorcelanatoMaior11 = 0;
    public double varAplicarPorcelanatoMaior11_1 = 0;
    public double varPastilhaVidro = 0;
    public double varPastilhaVidro_1 = 0;
    public double varPastilhaVidro_2 = 0;
    public double varPastilhaVidro_2_1 = 0;
    public double varPastilhaVidro_3 = 0;
    public double varPastilhaVidro_3_1 = 0;
    public double varPastilhaVidro_4 = 0;
    public double varPastilhaVidro_4_1 = 0;
    public double varPastilhaVidro_5 = 0;
    public double varPastilhaVidro_5_1 = 0;
    public double varPastilhaVidro_6 = 0;
    public double varPastilhaVidro_6_1 = 0;
    public double varPastilhaVidro_7 = 0;
    public double varPastilhaVidro_7_1 = 0;
    public double varPastilhaVidro_8 = 0;
    public double varPastilhaVidro_8_1 = 0;
    public double varPastilhaVidro_9 = 0;
    public double varPastilhaVidro_9_1 = 0;
    public double varPastilhaVidro_10 = 0;
    public double varPastilhaVidro_10_1 = 0;
    public double varPastilhaVidro_11 = 0;
    public double varPastilhaVidro_11_1 = 0;
    public double varRevestimento3D = 0;
    public double varRevestimento3D_1 = 0;
    public double varRevestimento3D_2 = 0;
    public double varRevestimento3D_2_1 = 0;
    public double varRevestimento3D_3 = 0;
    public double varRevestimento3D_3_1 = 0;
    public double varRevestimento3D_4 = 0;
    public double varRevestimento3D_4_1 = 0;
    public double varRevestimento3D_5 = 0;
    public double varRevestimento3D_5_1 = 0;
    public double varRevestimento3D_6 = 0;
    public double varRevestimento3D_6_1 = 0;
    public double varRevestimento3D_7 = 0;
    public double varRevestimento3D_7_1 = 0;
    public double varRevestimento3D_8 = 0;
    public double varRevestimento3D_8_1 = 0;
    public double varRevestimento3D_9 = 0;
    public double varRevestimento3D_9_1 = 0;
    public double varRevestimento3D_10 = 0;
    public double varRevestimento3D_10_1 = 0;
    public double varRevestimento3D_11 = 0;
    public double varRevestimento3D_11_1 = 0;


    //Variaveis Pintura
    public double varJanela = 0;
    public double varJanela1 = 0;
    public double varJanela2 = 0;
    public double varJanela2_1 = 0;
    public double varJanela3 = 0;
    public double varJanela3_1 = 0;
    public double varJanela4 = 0;
    public double varJanela4_1 = 0;
    public double varJanela5 = 0;
    public double varJanela5_1 = 0;
    public double varJanela6 = 0;
    public double varJanela6_1 = 0;
    public double varJanela7 = 0;
    public double varJanela7_1 = 0;
    public double varJanela8 = 0;
    public double varJanela8_1 = 0;
    public double varJanela9 = 0;
    public double varJanela9_1 = 0;
    public double varJanela10 = 0;
    public double varJanela10_1 = 0;
    public double varJanela11 = 0;
    public double varJanela11_1 = 0;
    public double varPorta = 0;
    public double varPorta1 = 0;
    public double varPorta2 = 0;
    public double varPorta2_1 = 0;
    public double varPorta3 = 0;
    public double varPorta3_1 = 0;
    public double varPorta4 = 0;
    public double varPorta4_1 = 0;
    public double varPorta5 = 0;
    public double varPorta5_1 = 0;
    public double varPorta6 = 0;
    public double varPorta6_1 = 0;
    public double varPorta7 = 0;
    public double varPorta7_1 = 0;
    public double varPorta8 = 0;
    public double varPorta8_1 = 0;
    public double varPorta9 = 0;
    public double varPorta9_1 = 0;
    public double varPorta10 = 0;
    public double varPorta10_1 = 0;
    public double varPorta11 = 0;
    public double varPorta11_1 = 0;
    public double varEfeitoDecorativo = 0;
    public double varEfeitoDecorativo1 = 0;
    public double varEfeitoDecorativo2 = 0;
    public double varEfeitoDecorativo2_1 = 0;
    public double varEfeitoDecorativo3 = 0;
    public double varEfeitoDecorativo3_1 = 0;
    public double varEfeitoDecorativo4 = 0;
    public double varEfeitoDecorativo4_1 = 0;
    public double varEfeitoDecorativo5 = 0;
    public double varEfeitoDecorativo5_1 = 0;
    public double varEfeitoDecorativo6 = 0;
    public double varEfeitoDecorativo6_1 = 0;
    public double varEfeitoDecorativo7 = 0;
    public double varEfeitoDecorativo7_1 = 0;
    public double varEfeitoDecorativo8 = 0;
    public double varEfeitoDecorativo8_1 = 0;
    public double varEfeitoDecorativo9 = 0;
    public double varEfeitoDecorativo9_1 = 0;
    public double varEfeitoDecorativo10 = 0;
    public double varEfeitoDecorativo10_1 = 0;
    public double varEfeitoDecorativo11 = 0;
    public double varEfeitoDecorativo11_1 = 0;
    public double varReparoGesso = 0;
    public double varReparoGesso1 = 0;
    public double varReparoGesso2 = 0;
    public double varReparoGesso2_1 = 0;
    public double varReparoGesso3 = 0;
    public double varReparoGesso3_1 = 0;
    public double varReparoGesso4 = 0;
    public double varReparoGesso4_1 = 0;
    public double varReparoGesso5 = 0;
    public double varReparoGesso5_1 = 0;
    public double varReparoGesso6 = 0;
    public double varReparoGesso6_1 = 0;
    public double varReparoGesso7 = 0;
    public double varReparoGesso7_1 = 0;
    public double varReparoGesso8 = 0;
    public double varReparoGesso8_1 = 0;
    public double varReparoGesso9 = 0;
    public double varReparoGesso9_1 = 0;
    public double varReparoGesso10 = 0;
    public double varReparoGesso10_1 = 0;
    public double varReparoGesso11 = 0;
    public double varReparoGesso11_1 = 0;
    public double varM2 = 0;
    public double varM2_1 = 0;


    public double latitude = 0;
    public double latitude2 = 0;
    //Criacao do PDF
    public static final String TAG = "PdfCreatorActivity";
    final DecimalFormat decimalFormat = new DecimalFormat("0000");
    public String nomeCliente;
    public File pdfFile;
    public String alterarNumeroNota, alterarValorNota;


    Uri imageUri;

    //
    public double valorTotalCozinha;
    public double valorTotalBanheiro1;
    public double valorTotalBanheiro2;
    public double valorTotalAreaServico;
    public double valorTotalLavabo;
    public double valorTotalSacadaVaranda;
    public double valorTotalSalaJantar;
    public double valorTotalSalaEstar;
    public double valorTotalQuarto1;
    public double valorTotalQuarto2;
    public double valorTotalQuarto3;
    public double totalDemolicao;

    //Revestimento Valores Totais
    public double valorTotalRevestimentoCozinha = 0;
    public double valorTotalRevestimentoBanheiroSocial = 0;
    public double valorTotalRevestimentoBanheiroSuite = 0;
    public double valorTotalRevestimentoQuarto1 = 0;
    public double valorTotalRevestimentoQuarto2 = 0;
    public double valorTotalRevestimentoAreaServico = 0;
    public double valorTotalRevestimentoLavabo = 0;
    public double valorTotalRevestimentoSacada = 0;
    public double valorTotalRevestimentoSalaJantar = 0;
    public double valorTotalCategoriaRevestimento = 0;
    public double valorTotalRevestimentoSalaEstar = 0;
    public double valorTotalRevestimentoQuartoSuite = 0;

    //Art
    public double valorTotalCategoriaArt = 0;
    public double valorTotalCategoriaArt2 = 0;

    //Hidraulica Valores Totais
    public double valorTotalHidraulicaCozinha = 0;
    public double valorTotalHidraulicaBanheiroSocial = 0;
    public double valorTotalHidraulicaBanheiroSuite = 0;
    public double valorTotalHidraulicaQuarto1 = 0;
    public double valorTotalHidraulicaQuarto2 = 0;
    public double valorTotalHidraulicaAreaServico = 0;
    public double valorTotalHidraulicaLavabo = 0;
    public double valorTotalHidraulicaSacada = 0;
    public double valorTotalHidraulicaSalaJantar = 0;
    public double valorTotalCategoriaHidraulica = 0;
    public double valorTotalHidraulicaSalaEstar = 0;
    public double valorTotalHidraulicaQuartoSuite = 0;

    //Pintura Valores Totais
    public double valorTotalPinturaCozinha = 0;
    public double valorTotalPinturaBanheiroSocial = 0;
    public double valorTotalPinturaBanheiroSuite = 0;
    public double valorTotalPinturaQuarto1 = 0;
    public double valorTotalPinturaQuarto2 = 0;
    public double valorTotalPinturaAreaServico = 0;
    public double valorTotalPinturaLavabo = 0;
    public double valorTotalPinturaSacada = 0;
    public double valorTotalPinturaSalaJantar = 0;
    public double valorTotalCategoriaPintura = 0;
    public double valorTotalPinturaSalaEstar = 0;
    public double valorTotalPinturaQuartoSuite = 0;
    public double valorTotalPinturaApartamento = 0;


    //ART Valores Totais
    public double valorTotalArtArcondicionado = 68.12;
    public double valorTotalArtEnvidracamento = 162.35;
    public double valorTotalArtPedrasMarmore = 62.13;
    public double valorTotalArtNovosRevestimentos = 62.77;
    public double valorTotalArtEletrica = 35.12;
    public double valorTotalArtHidraulica = 31.15;
    public double valorTotalArtBox = 35.12;
    public double valorTotalArtGesso = 35.12;
    public double valorTotalArtDemolicaoParedeNaoEstrutural = 111.75;
    public double valorTotalArtMoveisPlanejados = 12.67;
    public double valorTotalArtDeslocamentoPontoGas = 85.96;
    public double valorTotalArtTaxa = 85.96;


    public int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public static final int CAMERA = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static String mCurrentPhotoPath;


    //Base de Dados
    public DatabaseReference databaseReference;
    public FirebaseDatabase database;

    public Font fonteEndereco = FontFactory.getFont("Times-Roman", 14, Font.NORMAL);
    public Font fonteOrcamento = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
    public Font bold = FontFactory.getFont("Times-Roman, Bold", 15, Font.BOLD);
    public Font boldTitulo = FontFactory.getFont("Times-Roman, Bold", 22, Font.BOLD);
    public Font boldTitulo2 = FontFactory.getFont("Times-Roman, Bold", 13, Font.BOLD);
    public Font boldTotal = FontFactory.getFont("Times-Roman, Bold", 17, Font.BOLD);
    public Font boldTota2 = FontFactory.getFont("Times-Roman, Bold", 17, Font.NORMAL);
    public Font boldServicos = FontFactory.getFont("Times-Roman, Bold", 13, Font.NORMAL);
    public Font boldServicosPrestados = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
    public Font fontData = FontFactory.getFont("Times-Roman, Bold", 10, Font.BOLD);


    private Button btnAjustar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demolicao);
        MultiDex.install(this);

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
        checkBoxPinturaM2 = findViewById(R.id.checkboxPintura_m2);
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
        linearLayoutPintura12 = findViewById(R.id.linearPinturam2);


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


        //Pintura Apartamento
        valorPinturaApartamento1 = findViewById(R.id.pinturaApartamento1);
        valorPinturaApartamento1_1 = findViewById(R.id.pinturaApartamento1_1);
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


                totalDemolicao = valorTotalCozinha + valorTotalAreaServico + valorTotalBanheiro1 + valorTotalBanheiro2 + valorTotalLavabo + valorTotalSacadaVaranda + valorTotalSalaEstar + valorTotalSalaJantar + valorTotalQuarto1 + valorTotalQuarto2 + valorTotalQuarto3;

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

                valorTotalRevestimentoBanheiroSuite = varRevestimento3D_4_1 + varRevestimento3D_4 + varPastilhaVidro_4_1 + varPastilhaVidro_4 + varAplicarPorcelanatoMenor4 +varAplicarPorcelanatoMenor4_1 + varAplicarPorcelanatoMaior4_1 + varAplicarPorcelanatoMaior4 + varAdicionarAlvenaria4 + varAdicionarAlvenaria4_1 + varAdicionarContraPiso4 + varAdicionarContraPiso4_1 + varAplicacaoImpermeabilizante4 + varAplicacaoImpermeabilizante4_1;


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

                valorTotalCategoriaRevestimento =  valorTotalRevestimentoQuarto2 + valorTotalRevestimentoQuarto1 + valorTotalRevestimentoQuartoSuite + valorTotalRevestimentoSalaEstar + valorTotalRevestimentoSalaJantar + valorTotalRevestimentoSacada + valorTotalRevestimentoLavabo + valorTotalRevestimentoAreaServico + valorTotalRevestimentoBanheiroSocial + valorTotalRevestimentoBanheiroSuite + valorTotalRevestimentoCozinha;


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
    public void kk() throws FileNotFoundException, DocumentException {

        numeroNotaAtual = numNota + 1;
        SharedPreferences mypref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mypref.edit();
        editor.putInt("numeroNota", numeroNotaAtual);
        editor.commit();
        alterarNumeroNota = Integer.toString(numeroNotaAtual);
        exibirNota.setText("00" + alterarNumeroNota);


        Date now = new Date();
        android.text.format.DateFormat.format("dd-MM-yyyy_hh:mm:ss", now);

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RelatóriosRagonezi/Orcamentos");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            // Log.i(TAG, "Created a new directory for PDF");
        }


        pdfFile = new File(docsFolder.getAbsolutePath(), "Orcamento" + "00" + alterarNumeroNota + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();


        //Adicionando Logo
        Drawable d = getResources().getDrawable(R.drawable.cabecalho);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
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

        Drawable d7 = getResources().getDrawable(R.drawable.endereco);
        Bitmap bitmap7 = ((BitmapDrawable) d7).getBitmap();
        ByteArrayOutputStream stream7 = new ByteArrayOutputStream();
        bitmap7.compress(Bitmap.CompressFormat.PNG, 100, stream7);
        byte[] bitmapData7 = stream7.toByteArray();
        Image image7 = null;
        try {
            image7 = Image.getInstance(bitmapData7);
            image7.setAlignment(Element.ALIGN_CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d8 = getResources().getDrawable(R.drawable.logo_effes);
        Bitmap bitmap8 = ((BitmapDrawable) d8).getBitmap();
        ByteArrayOutputStream stream8 = new ByteArrayOutputStream();
        bitmap7.compress(Bitmap.CompressFormat.PNG, 100, stream8);
        byte[] bitmapData8 = stream8.toByteArray();
        Image image8 = null;
        try {
            image8 = Image.getInstance(bitmapData8);
            image8.setAlignment(Element.ALIGN_CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("###,##0.00");

        //Data
        Paragraph dataParagrafo = new Paragraph(String.valueOf(now), fontData);
        dataParagrafo.setAlignment(Element.ALIGN_LEFT);
        Paragraph espacoBranco = new Paragraph("", boldTitulo2);
        Paragraph espacoBranco1 = new Paragraph("", boldTitulo2);
        espacoBranco1.setSpacingBefore(5);
        espacoBranco.setLeading(1.25f);
        Paragraph tituloParagrafo = new Paragraph("Orçamento", boldTitulo2);
        Paragraph paragrafoDemolicao = new Paragraph("Demolição", boldTitulo2);
        paragrafoDemolicao.setSpacingAfter(7);
        Paragraph paragrafoArt = new Paragraph("ART - Anotação de Responsabilidade Técnica ", boldTitulo2);
        paragrafoArt.setSpacingAfter(7);
        paragrafoDemolicao.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragrafoRevestimento = new Paragraph("Revestimento", boldTitulo2);
        paragrafoRevestimento.setSpacingAfter(7);
        paragrafoRevestimento.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragrafoPintura = new Paragraph("Pintura", boldTitulo2);
        paragrafoPintura.setSpacingAfter(7);
        paragrafoPintura.setAlignment(Element.ALIGN_LEFT);
        Paragraph paragrafoHidraulica = new Paragraph("Hidraulica\n", boldTitulo2);
        paragrafoHidraulica.setSpacingAfter(7);
        paragrafoHidraulica.setAlignment(Element.ALIGN_LEFT);


        //Paragrafo Demolicao
        Paragraph paragrafoCozinha = new Paragraph(0, "Cozinha", boldServicos);
        Paragraph paragrafoCozinhaValor = new Paragraph("Total R$" + df.format(valorTotalCozinha), boldServicos);
        paragrafoCozinhaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoCozinha.add(0, paragrafoCozinhaValor);
        paragrafoCozinha.setLeading(0.70f);
//        paragrafoCozinha.setExtraParagraphSpace(10);
        Paragraph paragrafoBanheiro = new Paragraph(0, "Banheiro", boldServicos);
        Paragraph paragrafoBanheiroValor = new Paragraph("Total R$" + df.format(valorTotalBanheiro1), boldServicos);
        paragrafoBanheiroValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoBanheiro.add(0, paragrafoBanheiroValor);
        paragrafoBanheiro.setLeading(0.70f);
        Paragraph paragrafoAreaServico = new Paragraph(0, "Área de Serviço", boldServicos);
        Paragraph paragrafoAreaServicoValor = new Paragraph("Total R$" + df.format(valorTotalAreaServico), boldServicos);
        paragrafoAreaServicoValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoAreaServico.add(0, paragrafoAreaServicoValor);
        paragrafoAreaServico.setLeading(0.70f);
        Paragraph paragrafoBanheiro2 = new Paragraph(0, "Banheiro Suíte", boldServicos);
        Paragraph paragrafoBanheiro2Valor = new Paragraph("Total R$" + df.format(valorTotalBanheiro2), boldServicos);
        paragrafoBanheiro2Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoBanheiro2.add(0, paragrafoBanheiro2Valor);
        paragrafoBanheiro2.setLeading(0.70f);
        Paragraph paragrafoLavabo = new Paragraph(0, "Lavabo", boldServicos);
        Paragraph paragrafoLavaboValor = new Paragraph("Total R$" + df.format(valorTotalLavabo), boldServicos);
        paragrafoLavaboValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoLavabo.add(0, paragrafoLavaboValor);
        paragrafoLavabo.setLeading(0.70f);
        Paragraph paragrafoSacadaVaranda = new Paragraph(0, "Sacada/Varanda", boldServicos);
        Paragraph paragrafoSacadaVarandaValor = new Paragraph("Total R$" + df.format(valorTotalSacadaVaranda), boldServicos);
        paragrafoSacadaVarandaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoSacadaVaranda.add(0, paragrafoSacadaVarandaValor);
        paragrafoSacadaVaranda.setLeading(0.70f);
        Paragraph paragrafoSalaJantar = new Paragraph(0, "Sala Jantar", boldServicos);
        Paragraph paragrafoSalaJantarValor = new Paragraph("Total R$" + df.format(valorTotalSalaJantar), boldServicos);
        paragrafoSalaJantarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoSalaJantar.add(0, paragrafoSalaJantarValor);
        paragrafoSalaJantar.setLeading(0.70f);
        Paragraph paragrafoSalaEstar = new Paragraph(0, "Sala Estar", boldServicos);
        Paragraph paragrafoSalaEstarValor = new Paragraph("Total R$" + df.format(valorTotalSalaEstar), boldServicos);
        paragrafoSalaEstarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoSalaEstar.add(0, paragrafoSalaEstarValor);
        paragrafoSalaEstar.setLeading(0.70f);
        Paragraph paragrafoQuarto1 = new Paragraph(0, "Quarto 1", boldServicos);
        Paragraph paragrafoQuarto1Valor = new Paragraph("Total R$" + df.format(valorTotalQuarto2), boldServicos);
        paragrafoQuarto1Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoQuarto1.add(0, paragrafoQuarto1Valor);
        paragrafoQuarto1.setLeading(0.70f);
        Paragraph paragrafoQuarto2 = new Paragraph(0, "Quarto 2", boldServicos);
        Paragraph paragrafoQuarto2Valor = new Paragraph("Total R$" + df.format(valorTotalQuarto3), boldServicos);
        paragrafoQuarto2Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoQuarto2.add(0, paragrafoQuarto2Valor);
        paragrafoQuarto2.setLeading(0.70f);
        Paragraph paragrafoQuarto3 = new Paragraph(0, "Quarto Suíte", boldServicos);
        Paragraph paragrafoQuarto3Valor = new Paragraph("Total R$" + df.format(valorTotalQuarto1), boldServicos);
        paragrafoQuarto3Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoQuarto3.add(0, paragrafoQuarto3Valor);
        paragrafoQuarto3.setLeading(0.70f);
        Paragraph paragrafoDemolicaoQuarto3 = new Paragraph(0, "Quarto Suíte", boldServicos);
        Paragraph paragrafoDemolicaoQuarto3Valor = new Paragraph("Total R$" + df.format(valorTotalQuarto1), boldServicos);
        paragrafoDemolicaoQuarto3Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoDemolicaoQuarto3.add(0, paragrafoDemolicaoQuarto3Valor);
        paragrafoDemolicaoQuarto3.setLeading(0.70f);

        //ART


        //Paragrafo Revestimento

        Paragraph paragrafoRevestimentoCozinha = new Paragraph(0, "Cozinha", boldServicos);
        Paragraph paragrafoRevestimentoCozinhaValor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoCozinha), boldServicos);
        paragrafoRevestimentoCozinhaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoCozinha.add(0, paragrafoRevestimentoCozinhaValor);
        paragrafoRevestimentoCozinha.setLeading(0.70f);
        Paragraph paragrafoRevestimentoBanheiro = new Paragraph(0, "Banheiro", boldServicos);
        Paragraph paragrafoRevestimentoBanheiroValor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoBanheiroSocial), boldServicos);
        paragrafoRevestimentoBanheiroValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoBanheiro.add(0, paragrafoRevestimentoBanheiroValor);
        paragrafoRevestimentoBanheiro.setLeading(0.70f);
        Paragraph paragrafoRevestimentoAreaServico = new Paragraph(0, "Área de Serviço", boldServicos);
        Paragraph paragrafoRevestimentoAreaServicoValor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoAreaServico), boldServicos);
        paragrafoRevestimentoAreaServicoValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoAreaServico.add(0, paragrafoRevestimentoAreaServicoValor);
        paragrafoRevestimentoAreaServico.setLeading(0.70f);
        Paragraph paragrafoRevestimentoBanheiro2 = new Paragraph(0, "Banheiro Suíte", boldServicos);
        Paragraph paragrafoRevestimentoBanheiro2Valor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoBanheiroSuite), boldServicos);
        paragrafoRevestimentoBanheiro2Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoBanheiro2.add(0, paragrafoRevestimentoBanheiro2Valor);
        paragrafoRevestimentoBanheiro2.setLeading(0.70f);
        Paragraph paragrafoRevestimentoLavabo = new Paragraph(0, "Lavabo", boldServicos);
        Paragraph paragrafoRevestimentoLavaboValor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoLavabo), boldServicos);
        paragrafoRevestimentoLavaboValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoLavabo.add(0, paragrafoRevestimentoLavaboValor);
        paragrafoRevestimentoLavabo.setLeading(0.70f);
        Paragraph paragrafoRevestimentoSacadaVaranda = new Paragraph(0, "Sacada Varanda", boldServicos);
        Paragraph paragrafoRevestimentoSacadaVarandaValor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoSacada), boldServicos);
        paragrafoRevestimentoSacadaVarandaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoSacadaVaranda.add(0, paragrafoRevestimentoSacadaVarandaValor);
        paragrafoRevestimentoSacadaVaranda.setLeading(0.70f);
        Paragraph paragrafoRevestimentoSalaJantar = new Paragraph(0, "Sala Jantar", boldServicos);
        Paragraph paragrafoRevestimentoSalaJantarValor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoSalaJantar), boldServicos);
        paragrafoRevestimentoSalaJantarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoSalaJantar.add(0, paragrafoRevestimentoSalaJantarValor);
        paragrafoRevestimentoSalaJantar.setLeading(0.70f);
        Paragraph paragrafoRevestimentoSalaEstar = new Paragraph(0, "Sala Estar", boldServicos);
        Paragraph paragrafoRevestimentoSalaEstarValor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoSalaEstar), boldServicos);
        paragrafoRevestimentoSalaEstarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoSalaEstar.add(0, paragrafoRevestimentoSalaEstarValor);
        paragrafoRevestimentoSalaEstar.setLeading(0.70f);
        Paragraph paragrafoRevestimentoQuarto1 = new Paragraph(0, "Quarto 2", boldServicos);
        Paragraph paragrafoRevestimentoQuarto1Valor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoQuarto1), boldServicos);
        paragrafoRevestimentoQuarto1Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoQuarto1.add(0, paragrafoRevestimentoQuarto1Valor);
        paragrafoRevestimentoQuarto1.setLeading(0.70f);
        Paragraph paragrafoRevestimentoQuarto2 = new Paragraph(0, "Quarto Suíte", boldServicos);
        Paragraph paragrafoRevestimentoQuarto2Valor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoQuarto2), boldServicos);
        paragrafoRevestimentoQuarto2Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoQuarto2.add(0, paragrafoRevestimentoQuarto2Valor);
        paragrafoRevestimentoQuarto2.setLeading(0.70f);
        Paragraph paragrafoRevestimentoQuarto3 = new Paragraph(0, "Quarto 1", boldServicos);
        Paragraph paragrafoRevestimentoQuarto3Valor = new Paragraph("Total R$" + df.format(valorTotalRevestimentoQuartoSuite), boldServicos);
        paragrafoRevestimentoQuarto3Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoRevestimentoQuarto3.add(0, paragrafoRevestimentoQuarto3Valor);
        paragrafoRevestimentoQuarto3.setLeading(0.70f);


        //Paragrafo Pintura
        Paragraph paragrafoPinturaCozinha = new Paragraph(0, "Cozinha", boldServicos);
        Paragraph paragrafoPinturaCozinhaValor = new Paragraph("Total R$" + df.format(valorTotalPinturaCozinha), boldServicos);
        paragrafoPinturaCozinhaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaCozinha.add(0, paragrafoPinturaCozinhaValor);
        paragrafoPinturaCozinha.setLeading(0.70f);
        Paragraph paragrafoPinturaApartamento = new Paragraph(0, "Apartamento m²", boldServicos);
        Paragraph paragrafoPinturaApartamentoValor = new Paragraph("Total R$" + df.format(valorTotalPinturaApartamento), boldServicos);
        paragrafoPinturaApartamentoValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaApartamento.add(0, paragrafoPinturaApartamentoValor);
        paragrafoPinturaApartamento.setLeading(0.70f);
        Paragraph paragrafoPinturaBanheiro = new Paragraph(0, "Banheiro", boldServicos);
        Paragraph paragrafoPinturaBanheiroValor = new Paragraph("Total R$" + df.format(valorTotalPinturaBanheiroSocial), boldServicos);
        paragrafoPinturaBanheiroValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaBanheiro.add(0, paragrafoPinturaBanheiroValor);
        paragrafoPinturaBanheiro.setLeading(0.70f);
        Paragraph paragrafoPinturaAreaServico = new Paragraph(0, "Área de Serviço", boldServicos);
        Paragraph paragrafoPinturaAreaServicoValor = new Paragraph("Total R$" + df.format(valorTotalPinturaAreaServico), boldServicos);
        paragrafoPinturaAreaServicoValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaAreaServico.add(0, paragrafoPinturaAreaServicoValor);
        paragrafoPinturaAreaServico.setLeading(0.70f);
        Paragraph paragrafoPinturaBanheiro2 = new Paragraph(0, "Banheiro Suíte", boldServicos);
        Paragraph paragrafoPinturaBanheiro2Valor = new Paragraph("Total R$" + df.format(valorTotalPinturaBanheiroSuite), boldServicos);
        paragrafoPinturaBanheiro2Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaBanheiro2.add(0, paragrafoPinturaBanheiro2Valor);
        paragrafoPinturaBanheiro2.setLeading(0.70f);
        Paragraph paragrafoPinturaLavabo = new Paragraph(0, "Lavabo", boldServicos);
        Paragraph paragrafoPinturaLavaboValor = new Paragraph("Total R$" + df.format(valorTotalPinturaLavabo), boldServicos);
        paragrafoPinturaLavaboValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaLavabo.add(0, paragrafoPinturaLavaboValor);
        paragrafoPinturaLavabo.setLeading(0.70f);
        Paragraph paragrafoPinturaSacadaVaranda = new Paragraph(0, "Sacada Varanda", boldServicos);
        Paragraph paragrafoPinturaSacadaVarandaValor = new Paragraph("Total R$" + df.format(valorTotalPinturaSacada), boldServicos);
        paragrafoPinturaSacadaVarandaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaSacadaVaranda.add(0, paragrafoPinturaSacadaVarandaValor);
        paragrafoPinturaSacadaVaranda.setLeading(0.70f);
        Paragraph paragrafoPinturaSalaJantar = new Paragraph(0, "Sala Jantar", boldServicos);
        Paragraph paragrafoPinturaSalaJantarValor = new Paragraph("Total R$" + df.format(valorTotalPinturaSalaJantar), boldServicos);
        paragrafoPinturaSalaJantarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaSalaJantar.add(0, paragrafoPinturaSalaJantarValor);
        paragrafoPinturaSalaJantar.setLeading(0.70f);
        Paragraph paragrafoPinturaSalaEstar = new Paragraph(0, "Sala Estar", boldServicos);
        Paragraph paragrafoPinturaSalaEstarValor = new Paragraph("Total R$" + df.format(valorTotalPinturaSalaEstar), boldServicos);
        paragrafoPinturaSalaEstarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaSalaEstar.add(0, paragrafoPinturaSalaEstarValor);
        paragrafoPinturaSalaEstar.setLeading(0.70f);
        Paragraph paragrafoPinturaQuarto1 = new Paragraph(0, "Quarto 1", boldServicos);
        Paragraph paragrafoPinturaQuarto1Valor = new Paragraph("Total R$" + df.format(valorTotalPinturaQuarto1), boldServicos);
        paragrafoPinturaQuarto1Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaQuarto1.add(0, paragrafoPinturaQuarto1Valor);
        paragrafoPinturaQuarto1.setLeading(0.70f);
        Paragraph paragrafoPinturaQuarto2 = new Paragraph(0, "Quarto 2", boldServicos);
        Paragraph paragrafoPinturaQuarto2Valor = new Paragraph("Total R$" + df.format(valorTotalPinturaQuarto2), boldServicos);
        paragrafoPinturaQuarto2Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaQuarto2.add(0, paragrafoPinturaQuarto2Valor);
        paragrafoPinturaQuarto2.setLeading(0.70f);
        Paragraph paragrafoPinturaQuarto3 = new Paragraph(0, "Quarto 2", boldServicos);
        Paragraph paragrafoPinturaQuarto3Valor = new Paragraph("Total R$" + df.format(valorTotalPinturaQuartoSuite), boldServicos);
        paragrafoPinturaQuarto3Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoPinturaQuarto3.add(0, paragrafoPinturaQuarto3Valor);
        paragrafoPinturaQuarto3.setLeading(0.70f);

        //Paragrafo Hidraulica

        Paragraph paragrafoHidraulicaCozinha = new Paragraph(0, "Cozinha", boldServicos);
        Paragraph paragrafoHidraulicaCozinhaValor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaCozinha), boldServicos);
        paragrafoHidraulicaCozinhaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaCozinha.add(0, paragrafoHidraulicaCozinhaValor);
        paragrafoHidraulicaCozinha.setLeading(0.70f);
        Paragraph paragrafoHidraulicaBanheiro = new Paragraph(0, "Banheiro", boldServicos);
        Paragraph paragrafoHidraulicaBanheiroValor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaBanheiroSocial), boldServicos);
        paragrafoHidraulicaBanheiroValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaBanheiro.add(0, paragrafoHidraulicaBanheiroValor);
        paragrafoHidraulicaBanheiro.setLeading(0.70f);
        Paragraph paragrafoHidraulicaAreaServico = new Paragraph(0, "Área de Serviço", boldServicos);
        Paragraph paragrafoHidraulicaAreaServicoValor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaAreaServico), boldServicos);
        paragrafoHidraulicaAreaServicoValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaAreaServico.add(0, paragrafoHidraulicaAreaServicoValor);
        paragrafoHidraulicaAreaServico.setLeading(0.70f);
        Paragraph paragrafoHidraulicaBanheiro2 = new Paragraph("Banheiro Suíte", boldServicos);
        Paragraph paragrafoHidraulicaBanheiro2Valor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaBanheiroSuite), boldServicos);
        paragrafoHidraulicaBanheiro2Valor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaBanheiro2.add(0, paragrafoHidraulicaBanheiro2Valor);
        paragrafoHidraulicaBanheiro2.setLeading(0.70f);
        Paragraph paragrafoHidraulicaLavabo = new Paragraph(0, "Lavabo", boldServicos);
        Paragraph paragrafoHidraulicaLavaboValor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaLavabo), boldServicos);
        paragrafoHidraulicaLavaboValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaLavabo.add(0, paragrafoHidraulicaLavaboValor);
        paragrafoHidraulicaLavabo.setLeading(0.70f);
        Paragraph paragrafoHidraulicaSacadaVaranda = new Paragraph(0, "Sacada Varanda", boldServicos);
        Paragraph paragrafoHidraulicaSacadaVarandaValor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaSacada), boldServicos);
        paragrafoHidraulicaSacadaVarandaValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaSacadaVaranda.add(0, paragrafoHidraulicaSacadaVarandaValor);
        paragrafoHidraulicaSacadaVaranda.setLeading(0.70f);
        Paragraph paragrafoHidraulicaSalaJantar = new Paragraph(0, "Sala Jantar", boldServicos);
        Paragraph paragrafoHidraulicaSalaJantarValor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaSalaJantar), boldServicos);
        paragrafoHidraulicaSalaJantarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaSalaJantar.add(0, paragrafoHidraulicaSalaJantarValor);
        paragrafoHidraulicaSalaJantar.setLeading(0.70f);
        Paragraph paragrafoHidraulicaSalaEstar = new Paragraph(0, "Sala Estar", boldServicos);
        Paragraph paragrafoHidraulicaSalaEstarValor = new Paragraph("Total R$" + df.format(valorTotalHidraulicaSalaEstar), boldServicos);
        paragrafoHidraulicaSalaEstarValor.setAlignment(Element.ALIGN_RIGHT);
        paragrafoHidraulicaSalaEstar.add(0, paragrafoHidraulicaSalaEstarValor);
        paragrafoHidraulicaSalaEstar.setLeading(0.70f);
        Paragraph paragrafoHidraulicaQuarto1 = new Paragraph("Quarto 1                                                                                   Total R$" + df.format(valorTotalHidraulicaQuarto1), boldServicos);
        Paragraph paragrafoHidraulicaQuarto2 = new Paragraph("Quarto 2                                                                                   Total R$" + df.format(valorTotalHidraulicaQuarto2), boldServicos);
        Paragraph paragrafoHidraulicaQuarto3 = new Paragraph("Quarto Suíte                                                                               Total R$" + df.format(valorTotalHidraulicaQuartoSuite), boldServicos);

        Paragraph paragrafoServicos = new Paragraph("Quarto Suíte", boldServicosPrestados);


        //Tabela Endereco
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);


        //Adicionando logo e retirando a borda
        PdfPCell cell = new PdfPCell();
        PdfPCell cellEndereco = new PdfPCell();
        PdfPCell cellEffes = new PdfPCell();
        cell.addElement(image);
        cellEndereco.addElement(image7);
        cellEffes.addElement(image8);
        cell.setBorder(Rectangle.NO_BORDER);
        cellEndereco.setBorder(Rectangle.NO_BORDER);
        cellEffes.setBorder(Rectangle.NO_BORDER);

        PdfPTable table2 = new PdfPTable(1);
        table2.setWidthPercentage(100);


        //Adicionando a logo no documento com o texto do endereço no lado direito
        table.addCell(cell);


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
        Paragraph paragrafoNumeroNota = new Paragraph("000" + alterarNumeroNota, bold);
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
        cellBranco.addElement(paragrafobranco);
        cellBranco2.addElement(paragrafobranco);
        tableOrcamento.addCell(cellOrcamento);
        PdfPTable table5 = new PdfPTable(1);
        table5.setWidthPercentage(100);
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
        PdfPCell cell5 = new PdfPCell();
        cell5.addElement(image2);
        cell5.setBorder(Rectangle.NO_BORDER);
        table5.addCell(cell5);
        Paragraph paragrafoCliente = new Paragraph(nomeCliente, fonteOrcamento);
        PdfPTable tableProposta = new PdfPTable(1);
        tableProposta.setWidthPercentage(100);
        PdfPCell cellProposta = new PdfPCell();
        PdfPCell cellTextao = new PdfPCell();
        PdfPCell cellFaixa = new PdfPCell();
        cellFaixa.addElement(image2);
        cellFaixa.setBorder(Rectangle.NO_BORDER);
        Paragraph paragrafoDisposicao = new Paragraph("Fico à disposição para qualquer dúvida e negociação, atenciosamente;\n" +
                "Ragonezi Engenharia | effes revestimentos\n", fonteOrcamento);
        paragrafoDisposicao.setAlignment(Element.ALIGN_RIGHT);
        Paragraph paragrafoProposta = new Paragraph("PROPOSTA", bold);
        paragrafoProposta.setAlignment(Element.ALIGN_CENTER);
        Paragraph paragrafoTextao = new Paragraph("-Todos os serviços citados serão acompanhados por um engenheiro capacitado e credenciado.\n" +
                "-Forma de pagamento 30% na entrada, fechamento de contrato + 70% na conclusão do processo.\n" +
                "-Pagamentos com cartão de débito e crédito, também poderá ser efetuado o pagamento por boleto bancário e/ou transferência.\n" +
                "-Validade desse documento 30 dias a contar da data de envio.\n" +
                "-Itens não listados acima acordar valor em outra planilha.\n" +
                "-Este orçamento não tem validade para itens orçados separadamente.\n", fonteOrcamento);
        paragrafoTextao.setAlignment(Element.ALIGN_LEFT);
        cellProposta.addElement(paragrafoProposta);
        cellProposta.setBorder(Rectangle.NO_BORDER);
        cellTextao.addElement(paragrafoTextao);
        cellTextao.setBorder(Rectangle.NO_BORDER);
        tableProposta.addCell(cellProposta);
        tableProposta.addCell(cellTextao);
        tableProposta.addCell(cellFaixa);
        espacoBranco.add(new Paragraph("", boldTitulo));
        espacoBranco.add(new Paragraph("", boldTitulo));
        //Paragrafos
        Paragraph titulo = new Paragraph("Orçamento", boldTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        tituloParagrafo.setAlignment(Element.ALIGN_CENTER);
        //Valores de Banheiro
        String numeroNotaExibir = Integer.toString(numeroNotaAtual);
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
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(titulo);

        if (totalDemolicao > 0) {
            document.add(espacoBranco);
            PdfPTable tableDemolicao = new PdfPTable(1);
            tableDemolicao.setWidthPercentage(100);
            PdfPCell demolicao = new PdfPCell();
            demolicao.setBorder(Rectangle.NO_BORDER);
            demolicao.addElement(paragrafoDemolicao);
            tableDemolicao.addCell(demolicao);
            document.add(paragrafoDemolicao);
            document.add(espacoBranco);
            if (valorTotalCozinha > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoCozinha);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede > 0 || varRemoverRevestimentoParede1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede1.getText().toString()) + Double.parseDouble(valorRevestimentoParede1_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varRemoverRevestimentoParede + varRemoverRevestimentoParede1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso > 0 || varRemoverPiso1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso1.getText().toString()) + Double.parseDouble(valorRemocaoPiso1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso1 + varRemoverPiso)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia > 0 || varRemoverPia1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia1.getText().toString()) + Double.parseDouble(valorRemocaoPia1_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia1 + varRemoverPia)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria > 0 || varRemoverAlvenaria1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria1.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria1 + varRemoverAlvenaria)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque > 0 || varRemoverTanque1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque1.getText().toString()) + Double.parseDouble(valorRemocaoTanque1_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque1 + varRemoverTanque)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2 > 0 || varRemoverCaixinha4x2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_1.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_1_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_1 + varRemoverCaixinha4x2)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4 > 0 || varRemoverCaixinha4x4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_1.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_1_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_1 + varRemoverCaixinha4x4)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica > 0 || varRemoverHidraulica1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica1.getText().toString()) + Double.parseDouble(valorRasgarHidraulica1_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica1 + varRemoverHidraulica)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso > 0 || varRemoverGesso1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso1_1.getText().toString()) + Double.parseDouble(valorRemoverGesso1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso1 + varRemoverGesso)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao > 0 || varRemoverVao1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao1.getText().toString()) + Double.parseDouble(valorRemoverVao1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao1 + varRemoverVao)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalBanheiro1 > 0) {
                PdfPTable tableBanheiro = new PdfPTable(1);
                tableBanheiro.setWidthPercentage(100);
                PdfPCell cellBanheiro = new PdfPCell();
                cellBanheiro.addElement(paragrafoBanheiro);
                tableBanheiro.addCell(cellBanheiro);
                document.add(tableBanheiro);
                if (varRemoverRevestimentoParede2 > 0 || varRemoverRevestimentoParede2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede2.getText().toString()) + Double.parseDouble(valorRevestimentoParede2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede2 + varRemoverRevestimentoParede2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso2 > 0 || varRemoverPiso2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso2.getText().toString()) + Double.parseDouble(valorRemocaoPiso2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso2 + varRemoverPiso2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia2 > 0 || varRemoverPia2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia2.getText().toString()) + Double.parseDouble(valorRemocaoPia2_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia2 + varRemoverPia2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria2 > 0 || varRemoverAlvenaria2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria2.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria2 + varRemoverAlvenaria2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }

                if (varRemoverTanque2 > 0 || varRemoverTanque2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque2.getText().toString()) + Double.parseDouble(valorRemocaoTanque2_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque2_1 + varRemoverTanque2)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_2 > 0 || varRemoverCaixinha4x2_2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_2.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_2_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_2 + varRemoverCaixinha4x2_2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_2 > 0 || varRemoverCaixinha4x4_2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_2.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_2_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_2 + varRemoverCaixinha4x4_2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica2 > 0 || varRemoverHidraulica2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica2.getText().toString()) + Double.parseDouble(valorRasgarHidraulica2_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica2 + varRemoverHidraulica2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso2 > 0 || varRemoverGesso2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRemoverGesso2_1.getText().toString()) + Double.parseDouble(valorRemoverGesso2.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso2 + varRemoverGesso2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVasoSanitario2 > 0 || varRemoverVasoSanitario2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vaso Sanitário: " + (Double.parseDouble(valorRemoverVaso2.getText().toString()) + Double.parseDouble(valorRemoverVaso2_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVasoSanitario2 + varRemoverVasoSanitario2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao2 > 0 || varRemoverVao2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao2.getText().toString()) + Double.parseDouble(valorRemoverVao2_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao2 + varRemoverVao2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalAreaServico > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoAreaServico);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede3 > 0 || varRemoverRevestimentoParede3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede3.getText().toString()) + Double.parseDouble(valorRevestimentoParede3_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede3 + varRemoverRevestimentoParede3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso3 > 0 || varRemoverPiso3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso3_1.getText().toString()) + Double.parseDouble(valorRemocaoPiso3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso3 + varRemoverPiso3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia3 > 0 || varRemoverPia3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia3.getText().toString()) + Double.parseDouble(valorRemocaoPia3_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia3 + varRemoverPia3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria3 > 0 || varRemoverAlvenaria3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria3.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria3 + varRemoverAlvenaria3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque3 > 0 || varRemoverTanque3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque3.getText().toString()) + Double.parseDouble(valorRemocaoTanque3_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque3 + varRemoverTanque3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_3 > 0 || varRemoverCaixinha4x2_3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_3.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_3_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_3 + varRemoverCaixinha4x2_3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_3 > 0 || varRemoverCaixinha4x4_3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_3.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_3_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_3 + varRemoverCaixinha4x4_3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica3 > 0 || varRemoverHidraulica3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica3.getText().toString()) + Double.parseDouble(valorRasgarHidraulica3_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica3 + varRemoverHidraulica3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso3 > 0 || varRemoverGesso3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso3_1.getText().toString()) + Double.parseDouble(valorRemoverGesso3.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso3 + varRemoverGesso3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao3 > 0 || varRemoverVao3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao3.getText().toString()) + Double.parseDouble(valorRemoverVao3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao3 + varRemoverVao3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalBanheiro2 > 0) {
                PdfPTable tableBanheiro2 = new PdfPTable(1);
                tableBanheiro2.setWidthPercentage(100);
                PdfPCell cellAreaServico = new PdfPCell();
                cellAreaServico.addElement(paragrafoBanheiro2);
                tableBanheiro2.addCell(cellAreaServico);
                document.add(tableBanheiro2);
                if (varRemoverRevestimentoParede4 > 0 || varRemoverRevestimentoParede4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede4.getText().toString()) + Double.parseDouble(valorRevestimentoParede4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede4 + varRemoverRevestimentoParede4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso4 > 0 || varRemoverPiso4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso4.getText().toString()) + Double.parseDouble(valorRemocaoPiso4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso4 + varRemoverPiso4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia4 > 0 || varRemoverPia4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia4.getText().toString()) + Double.parseDouble(valorRemocaoPia4_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia4 + varRemoverPia4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria4 > 0 || varRemoverAlvenaria4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria4.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria4 + varRemoverAlvenaria4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque4 > 0 || varRemoverTanque4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque4.getText().toString()) + Double.parseDouble(valorRemocaoTanque4_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque4_1 + varRemoverTanque4)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_4 > 0 || varRemoverCaixinha4x2_4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_4.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_4_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_4_1 + varRemoverCaixinha4x2_4)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_4 > 0 || varRemoverCaixinha4x4_4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_4.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_4_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_4 + varRemoverCaixinha4x4_4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica4 > 0 || varRemoverHidraulica4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica4.getText().toString()) + Double.parseDouble(valorRasgarHidraulica4_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica4 + varRemoverHidraulica4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso4 > 0 || varRemoverGesso4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRemoverGesso4_1.getText().toString()) + Double.parseDouble(valorRemoverGesso4.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso4 + varRemoverGesso4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao4 > 0 || varRemoverVao4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao4.getText().toString()) + Double.parseDouble(valorRemoverVao4_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao4 + varRemoverVao4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalLavabo > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoLavabo);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede5 > 0 || varRemoverRevestimentoParede5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede5.getText().toString()) + Double.parseDouble(valorRevestimentoParede5_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede5_1 + varRemoverRevestimentoParede5)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso5 > 0 || varRemoverPiso5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso5.getText().toString()) + Double.parseDouble(valorRemocaoPiso5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso5 + varRemoverPiso5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia5 > 0 || varRemoverPia5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia5.getText().toString()) + Double.parseDouble(valorRemocaoPia5_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia5 + varRemoverPia5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria5 > 0 || varRemoverAlvenaria5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria5.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria5_1 + varRemoverAlvenaria5)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque5 > 0 || varRemoverTanque5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque5.getText().toString()) + Double.parseDouble(valorRemocaoTanque5_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque5 + varRemoverTanque5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_5 > 0 || varRemoverCaixinha4x2_5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_5.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_5_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_5 + varRemoverCaixinha4x2_5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_5 > 0 || varRemoverCaixinha4x4_5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_5.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_5_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_5 + varRemoverCaixinha4x4_5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica5 > 0 || varRemoverHidraulica5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica5.getText().toString()) + Double.parseDouble(valorRasgarHidraulica5_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica5 + varRemoverHidraulica5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso5 > 0 || varRemoverGesso5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso5_1.getText().toString()) + Double.parseDouble(valorRemoverGesso5.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso5 + varRemoverGesso5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao5 > 0 || varRemoverVao5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao5.getText().toString()) + Double.parseDouble(valorRemoverVao5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao5 + varRemoverVao5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }

            if (valorTotalSacadaVaranda > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoSacadaVaranda);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede6 > 0 || varRemoverRevestimentoParede6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede6.getText().toString()) + Double.parseDouble(valorRevestimentoParede6_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede6_1 + varRemoverRevestimentoParede6)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso6 > 0 || varRemoverPiso6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso6_1.getText().toString()) + Double.parseDouble(valorRemocaoPiso6.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso6 + varRemoverPiso6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia6 > 0 || varRemoverPia6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia6.getText().toString()) + Double.parseDouble(valorRemocaoPia6_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia6 + varRemoverPia6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria6 > 0 || varRemoverAlvenaria6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria6.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria6_1 + varRemoverAlvenaria6)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque6 > 0 || varRemoverTanque6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque6.getText().toString()) + Double.parseDouble(valorRemocaoTanque6_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque6 + varRemoverTanque6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_6 > 0 || varRemoverCaixinha4x2_6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_6.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_6_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_6 + varRemoverCaixinha4x2_6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_6 > 0 || varRemoverCaixinha4x4_6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_6.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_6_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_6 + varRemoverCaixinha4x4_6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica6 > 0 || varRemoverHidraulica6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica6.getText().toString()) + Double.parseDouble(valorRasgarHidraulica6_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica6 + varRemoverHidraulica6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso6 > 0 || varRemoverGesso6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso6_1.getText().toString()) + Double.parseDouble(valorRemoverGesso6.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso6 + varRemoverGesso6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao6 > 0 || varRemoverVao6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao6.getText().toString()) + Double.parseDouble(valorRemoverVao6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao6 + varRemoverVao6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalSalaJantar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoSalaJantar);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede7 > 0 || varRemoverRevestimentoParede7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede7.getText().toString()) + Double.parseDouble(valorRevestimentoParede7_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede7_1 + varRemoverRevestimentoParede7)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso7 > 0 || varRemoverPiso7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso7.getText().toString()) + Double.parseDouble(valorRemocaoPiso7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso7 + varRemoverPiso7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia7 > 0 || varRemoverPia7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia7.getText().toString()) + Double.parseDouble(valorRemocaoPia7_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia7 + varRemoverPia7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria7 > 0 || varRemoverAlvenaria7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria7.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria7_1 + varRemoverAlvenaria7)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque7 > 0 || varRemoverTanque7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque7.getText().toString()) + Double.parseDouble(valorRemocaoTanque7_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque7 + varRemoverTanque7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_7 > 0 || varRemoverCaixinha4x2_7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_7.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_7_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_7 + varRemoverCaixinha4x2_7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_7 > 0 || varRemoverCaixinha4x4_7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_7.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_7_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_7 + varRemoverCaixinha4x4_7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica7 > 0 || varRemoverHidraulica7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica7.getText().toString()) + Double.parseDouble(valorRasgarHidraulica7_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica7 + varRemoverHidraulica7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso7 > 0 || varRemoverGesso7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso7_1.getText().toString()) + Double.parseDouble(valorRemoverGesso7.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso7 + varRemoverGesso7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao7 > 0 || varRemoverVao7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao7.getText().toString()) + Double.parseDouble(valorRemoverVao7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao7 + varRemoverVao7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalSalaEstar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoSalaEstar);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede8 > 0 || varRemoverRevestimentoParede8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede8.getText().toString()) + Double.parseDouble(valorRevestimentoParede8_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede8_1 + varRemoverRevestimentoParede8)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso8 > 0 || varRemoverPiso8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso8_1.getText().toString()) + Double.parseDouble(valorRemocaoPiso8.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso8 + varRemoverPiso8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia8 > 0 || varRemoverPia8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia8.getText().toString()) + Double.parseDouble(valorRemocaoPia8_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia8 + varRemoverPia8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria8 > 0 || varRemoverAlvenaria8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria8.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria8_1 + varRemoverAlvenaria8)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque8 > 0 || varRemoverTanque8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque8.getText().toString()) + Double.parseDouble(valorRemocaoTanque8_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque8 + varRemoverTanque8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_8 > 0 || varRemoverCaixinha4x2_8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_8.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_8_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_8 + varRemoverCaixinha4x2_8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_8 > 0 || varRemoverCaixinha4x4_8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_8.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_8_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_8 + varRemoverCaixinha4x4_8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica8 > 0 || varRemoverHidraulica8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica8.getText().toString()) + Double.parseDouble(valorRasgarHidraulica8_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica8 + varRemoverHidraulica8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso8 > 0 || varRemoverGesso8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso8_1.getText().toString()) + Double.parseDouble(valorRemoverGesso8.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso8 + varRemoverGesso8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao8 > 0 || varRemoverVao8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao8.getText().toString()) + Double.parseDouble(valorRemoverVao8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao8 + varRemoverVao8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalQuarto1 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoDemolicaoQuarto3);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede9 > 0 || varRemoverRevestimentoParede9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede9.getText().toString()) + Double.parseDouble(valorRevestimentoParede9_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede9_1 + varRemoverRevestimentoParede9)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso9 > 0 || varRemoverPiso9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso9.getText().toString()) + Double.parseDouble(valorRemocaoPiso9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso9 + varRemoverPiso9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia9 > 0 || varRemoverPia9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia9.getText().toString()) + Double.parseDouble(valorRemocaoPia9_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia9 + varRemoverPia9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria9 > 0 || varRemoverAlvenaria9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria9.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria9_1 + varRemoverAlvenaria9)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque9 > 0 || varRemoverTanque9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque9.getText().toString()) + Double.parseDouble(valorRemocaoTanque9_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque9 + varRemoverTanque9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_9 > 0 || varRemoverCaixinha4x2_9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_9.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_9_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_9 + varRemoverCaixinha4x2_9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_9 > 0 || varRemoverCaixinha4x4_9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_9.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_9_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_9 + varRemoverCaixinha4x4_9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica9 > 0 || varRemoverHidraulica9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica9.getText().toString()) + Double.parseDouble(valorRasgarHidraulica9_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica9 + varRemoverHidraulica9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso9 > 0 || varRemoverGesso9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso9_1.getText().toString()) + Double.parseDouble(valorRemoverGesso9.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso9 + varRemoverGesso9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao9 > 0 || varRemoverVao9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao9.getText().toString()) + Double.parseDouble(valorRemoverVao9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao9 + varRemoverVao9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalQuarto2 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoQuarto1);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede10 > 0 || varRemoverRevestimentoParede10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede10.getText().toString()) + Double.parseDouble(valorRevestimentoParede10_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede10_1 + varRemoverRevestimentoParede10)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso10 > 0 || varRemoverPiso10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso10.getText().toString()) + Double.parseDouble(valorRemocaoPiso10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso10 + varRemoverPiso10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia10 > 0 || varRemoverPia10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia10.getText().toString()) + Double.parseDouble(valorRemocaoPia10_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia10 + varRemoverPia10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria10 > 0 || varRemoverAlvenaria10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria10.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria10 + varRemoverAlvenaria10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque10 > 0 || varRemoverTanque10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque10.getText().toString()) + Double.parseDouble(valorRemocaoTanque10_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque10 + varRemoverTanque10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_10 > 0 || varRemoverCaixinha4x2_10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_10.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_10_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_10 + varRemoverCaixinha4x2_10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_10 > 0 || varRemoverCaixinha4x4_10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_10.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_10_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_10 + varRemoverCaixinha4x4_10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica10 > 0 || varRemoverHidraulica10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica10.getText().toString()) + Double.parseDouble(valorRasgarHidraulica10_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica10 + varRemoverHidraulica10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso10 > 0 || varRemoverGesso10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso10_1.getText().toString()) + Double.parseDouble(valorRemoverGesso10.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso10 + varRemoverGesso10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao10 > 0 || varRemoverVao10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao10.getText().toString()) + Double.parseDouble(valorRemoverVao10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao10 + varRemoverVao10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalQuarto3 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoQuarto2);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varRemoverRevestimentoParede11 > 0 || varRemoverRevestimentoParede11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Remover Revestimento de Parede: " + (Double.parseDouble(valorRevestimentoParede11.getText().toString()) + Double.parseDouble(valorRevestimentoParede11_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverRevestimentoParede11_1 + varRemoverRevestimentoParede11)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPiso11 > 0 || varRemoverPiso11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Piso: " + (Double.parseDouble(valorRemocaoPiso11.getText().toString()) + Double.parseDouble(valorRemocaoPiso11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPiso11 + varRemoverPiso11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverPia11 > 0 || varRemoverPia11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Pia: " + (Double.parseDouble(valorRemocaoPia11.getText().toString()) + Double.parseDouble(valorRemocaoPia11_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverPia11 + varRemoverPia11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverAlvenaria11 > 0 || varRemoverAlvenaria11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Alvenaria: " + (Double.parseDouble(valorRemocacAlvenaria11.getText().toString()) + Double.parseDouble(valorRemocacAlvenaria11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverAlvenaria11_1 + varRemoverAlvenaria11)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverTanque11 > 0 || varRemoverTanque11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Tanque: " + (Double.parseDouble(valorRemocaoTanque11.getText().toString()) + Double.parseDouble(valorRemocaoTanque11_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverTanque11 + varRemoverTanque11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(110);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x2_11 > 0 || varRemoverCaixinha4x2_11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x2: " + (Double.parseDouble(valorRasgarCaixinha4x2_11.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x2_11_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x2_11 + varRemoverCaixinha4x2_11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverCaixinha4x4_11 > 0 || varRemoverCaixinha4x4_11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Caixinha 4x4: " + (Double.parseDouble(valorRasgarCaixinha4x4_11.getText().toString()) + Double.parseDouble(valorRasgarCaixinha4x4_11_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverCaixinha4x4_11 + varRemoverCaixinha4x4_11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverHidraulica11 > 0 || varRemoverHidraulica11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Rasgar Hidráulica: " + (Double.parseDouble(valorRasgarHidraulica11.getText().toString()) + Double.parseDouble(valorRasgarHidraulica11_1.getText().toString())) + " un", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverHidraulica11 + varRemoverHidraulica11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverGesso11 > 0 || varRemoverGesso11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Gesso: " + (Double.parseDouble(valorRemoverGesso11_1.getText().toString()) + Double.parseDouble(valorRemoverGesso11.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverGesso11 + varRemoverGesso11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRemoverVao11 > 0 || varRemoverVao11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Remover Vão para Nicho: " + (Double.parseDouble(valorRemoverVao11.getText().toString()) + Double.parseDouble(valorRemoverVao11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRemoverVao11 + varRemoverVao11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
        }
        if (valorTotalCategoriaRevestimento > 0) {
            PdfPTable tableRevestimento = new PdfPTable(1);
            tableRevestimento.setWidthPercentage(100);
            PdfPCell revestimento = new PdfPCell();
            revestimento.setBorder(Rectangle.NO_BORDER);
            revestimento.addElement(paragrafoRevestimento);
            tableRevestimento.addCell(revestimento);
            document.add(paragrafoRevestimento);
            document.add(espacoBranco);
            document.add(espacoBranco);
            if (valorTotalRevestimentoCozinha > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoCozinha);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria > 0 || varAdicionarAlvenaria1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase1.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase1_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria + varAdicionarAlvenaria1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso > 0 || varAdicionarContraPiso1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso1.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso + varAdicionarContraPiso1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante > 0 || varAplicacaoImpermeabilizante1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade1.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante + varAplicacaoImpermeabilizante1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor > 0 || varAplicarPorcelanatoMenor1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor1.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor + varAplicarPorcelanatoMenor1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior > 0 || varAplicarPorcelanatoMaior1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima1.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior + varAplicarPorcelanatoMaior1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro > 0 || varPastilhaVidro_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro1.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro + varPastilhaVidro_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D > 0 || varRevestimento3D_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D1.getText().toString()) + Double.parseDouble(valorRevestimento3D1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D + varRevestimento3D_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoBanheiroSocial > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoBanheiro);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria2 > 0 || varAdicionarAlvenaria2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase2.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase2_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria2 + varAdicionarAlvenaria2_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso2 > 0 || varAdicionarContraPiso2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso2.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso2 + varAdicionarContraPiso2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante2 > 0 || varAplicacaoImpermeabilizante2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade2.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante2 + varAplicacaoImpermeabilizante2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor2 > 0 || varAplicarPorcelanatoMenor2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor2.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor2 + varAplicarPorcelanatoMenor2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior2 > 0 || varAplicarPorcelanatoMaior2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima2.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior2 + varAplicarPorcelanatoMaior2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_2 > 0 || varPastilhaVidro_2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro2.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_2 + varPastilhaVidro_2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_2 > 0 || varRevestimento3D_2_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D2.getText().toString()) + Double.parseDouble(valorRevestimento3D2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_2 + varRevestimento3D_2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoAreaServico > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoAreaServico);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria3 > 0 || varAdicionarAlvenaria3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase3.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase3_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria3 + varAdicionarAlvenaria3_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso3 > 0 || varAdicionarContraPiso3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso3.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso3 + varAdicionarContraPiso3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante3 > 0 || varAplicacaoImpermeabilizante3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade3.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante3 + varAplicacaoImpermeabilizante3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor3 > 0 || varAplicarPorcelanatoMenor3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor3.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor3 + varAplicarPorcelanatoMenor3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior3 > 0 || varAplicarPorcelanatoMaior3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima3.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior3 + varAplicarPorcelanatoMaior3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_3 > 0 || varPastilhaVidro_3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro3.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_3 + varPastilhaVidro_3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_3 > 0 || varRevestimento3D_3_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D3.getText().toString()) + Double.parseDouble(valorRevestimento3D3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_3 + varRevestimento3D_3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoBanheiroSuite > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoBanheiro2);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria4 > 0 || varAdicionarAlvenaria4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase4.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase4_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria4 + varAdicionarAlvenaria4_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso4 > 0 || varAdicionarContraPiso4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso4.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso4 + varAdicionarContraPiso4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante4 > 0 || varAplicacaoImpermeabilizante4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade4.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante4 + varAplicacaoImpermeabilizante4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor4 > 0 || varAplicarPorcelanatoMenor4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor4.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor4 + varAplicarPorcelanatoMenor4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior4 > 0 || varAplicarPorcelanatoMaior4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima4.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior4 + varAplicarPorcelanatoMaior4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_4 > 0 || varPastilhaVidro_4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro4.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_4 + varPastilhaVidro_4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_4 > 0 || varRevestimento3D_4_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D4.getText().toString()) + Double.parseDouble(valorRevestimento3D4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_4 + varRevestimento3D_4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoLavabo > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoLavabo);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria5 > 0 || varAdicionarAlvenaria5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase5.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase5_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria5 + varAdicionarAlvenaria5_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso5 > 0 || varAdicionarContraPiso5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso5.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso5 + varAdicionarContraPiso5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante5 > 0 || varAplicacaoImpermeabilizante5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade5.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante5 + varAplicacaoImpermeabilizante5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor5 > 0 || varAplicarPorcelanatoMenor5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor5.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor5 + varAplicarPorcelanatoMenor5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior5 > 0 || varAplicarPorcelanatoMaior5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima5.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior5 + varAplicarPorcelanatoMaior5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_5 > 0 || varPastilhaVidro_5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro5.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_5 + varPastilhaVidro_5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_5 > 0 || varRevestimento3D_5_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D5.getText().toString()) + Double.parseDouble(valorRevestimento3D5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_5 + varRevestimento3D_5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoSacada > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoSacadaVaranda);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria6 > 0 || varAdicionarAlvenaria6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase6.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase6_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria6 + varAdicionarAlvenaria6_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso6 > 0 || varAdicionarContraPiso6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso6.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso6 + varAdicionarContraPiso6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante6 > 0 || varAplicacaoImpermeabilizante6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade6.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante6 + varAplicacaoImpermeabilizante6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor6 > 0 || varAplicarPorcelanatoMenor6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor6.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor6 + varAplicarPorcelanatoMenor6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior6 > 0 || varAplicarPorcelanatoMaior6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima6.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior6 + varAplicarPorcelanatoMaior6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_6 > 0 || varPastilhaVidro_6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro6.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_6 + varPastilhaVidro_6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_6 > 0 || varRevestimento3D_6_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D6.getText().toString()) + Double.parseDouble(valorRevestimento3D6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_6 + varRevestimento3D_6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoSalaJantar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoSalaJantar);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria7 > 0 || varAdicionarAlvenaria7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase7.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase7_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria7 + varAdicionarAlvenaria7_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso7 > 0 || varAdicionarContraPiso7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso7.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso7 + varAdicionarContraPiso7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante7 > 0 || varAplicacaoImpermeabilizante7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade7.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante7 + varAplicacaoImpermeabilizante7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor7 > 0 || varAplicarPorcelanatoMenor7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor7.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor7 + varAplicarPorcelanatoMenor7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior7 > 0 || varAplicarPorcelanatoMaior7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima7.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior7 + varAplicarPorcelanatoMaior7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_7 > 0 || varPastilhaVidro_7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro7.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_7 + varPastilhaVidro_7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_7 > 0 || varRevestimento3D_7_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D7.getText().toString()) + Double.parseDouble(valorRevestimento3D7_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_7 + varRevestimento3D_7_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }

            if (valorTotalRevestimentoSalaEstar > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoSalaEstar);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria8 > 0 || varAdicionarAlvenaria8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase8.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase8_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria8 + varAdicionarAlvenaria8_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso8 > 0 || varAdicionarContraPiso8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso8.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso8 + varAdicionarContraPiso8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante8 > 0 || varAplicacaoImpermeabilizante8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade8.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante8 + varAplicacaoImpermeabilizante8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor8 > 0 || varAplicarPorcelanatoMenor8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor8.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor8 + varAplicarPorcelanatoMenor8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior8 > 0 || varAplicarPorcelanatoMaior8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima8.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior8 + varAplicarPorcelanatoMaior8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_8 > 0 || varPastilhaVidro_8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro8.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_8 + varPastilhaVidro_8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_8 > 0 || varRevestimento3D_8_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D8.getText().toString()) + Double.parseDouble(valorRevestimento3D8_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_8 + varRevestimento3D_8_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoQuartoSuite > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoQuarto3);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria9 > 0 || varAdicionarAlvenaria9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase9.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase9_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria9 + varAdicionarAlvenaria9_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso9 > 0 || varAdicionarContraPiso9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso9.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso9 + varAdicionarContraPiso9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante9 > 0 || varAplicacaoImpermeabilizante9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade9.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante9 + varAplicacaoImpermeabilizante9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor9 > 0 || varAplicarPorcelanatoMenor9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor9.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor9 + varAplicarPorcelanatoMenor9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior9 > 0 || varAplicarPorcelanatoMaior9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima9.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior9 + varAplicarPorcelanatoMaior9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_9 > 0 || varPastilhaVidro_9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro9.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_9 + varPastilhaVidro_9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_9 > 0 || varRevestimento3D_9_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D9.getText().toString()) + Double.parseDouble(valorRevestimento3D9_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_9 + varRevestimento3D_9_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoQuarto1 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoQuarto1);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria10 > 0 || varAdicionarAlvenaria10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase10.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase10_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria10 + varAdicionarAlvenaria10_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso10 > 0 || varAdicionarContraPiso10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso10.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso10 + varAdicionarContraPiso10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante10 > 0 || varAplicacaoImpermeabilizante10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade10.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante10 + varAplicacaoImpermeabilizante10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor10 > 0 || varAplicarPorcelanatoMenor10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor10.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor10 + varAplicarPorcelanatoMenor10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior10 > 0 || varAplicarPorcelanatoMaior10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima10.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior10 + varAplicarPorcelanatoMaior10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_10 > 0 || varPastilhaVidro_10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro10.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_10 + varPastilhaVidro_10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_10 > 0 || varRevestimento3D_10_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D10.getText().toString()) + Double.parseDouble(valorRevestimento3D10_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_10 + varRevestimento3D_10_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalRevestimentoQuarto2 > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoRevestimentoQuarto2);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);
                document.add(tableCozinha);
                if (varAdicionarAlvenaria11 > 0 || varAdicionarAlvenaria11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(0, ">>> Criar Alvenaria / Base  " + (Double.parseDouble(valorRevestimentoAlvenariaBase11.getText().toString()) + Double.parseDouble(valorRevestimentoAlvenariaBase11_1.getText().toString())) + " m² ", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(varAdicionarAlvenaria11 + varAdicionarAlvenaria11_1), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarContraPiso11 > 0 || varAdicionarContraPiso11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Adicionar Contra Piso: " + (Double.parseDouble(valorRevestimentoContraPiso11.getText().toString()) + Double.parseDouble(valorRevestimentoContraPiso11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarContraPiso11 + varAdicionarContraPiso11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicacaoImpermeabilizante11 > 0 || varAplicacaoImpermeabilizante11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Aplicação de Impermeabilizante: " + (Double.parseDouble(valorRevestimentoImpermeabilidade11.getText().toString()) + Double.parseDouble(valorRevestimentoImpermeabilidade11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicacaoImpermeabilizante11 + varAplicacaoImpermeabilizante11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMenor11 > 0 || varAplicarPorcelanatoMenor11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Menor que 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoMenor11.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoMenor11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMenor11 + varAplicarPorcelanatoMenor11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAplicarPorcelanatoMaior11 > 0 || varAplicarPorcelanatoMaior11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Porcelanato Maior/Igual a 60cm x 60cm: " + (Double.parseDouble(valorRevestimentoPorcelanatoAcima11.getText().toString()) + Double.parseDouble(valorRevestimentoPorcelanatoAcima11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAplicarPorcelanatoMaior11 + varAplicarPorcelanatoMaior11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varPastilhaVidro_11 > 0 || varPastilhaVidro_11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Pastilha de Vidro: " + (Double.parseDouble(valorRevestimentoPastilhaVidro11.getText().toString()) + Double.parseDouble(valorRevestimentoPastilhaVidro11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varPastilhaVidro_11 + varPastilhaVidro_11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varRevestimento3D_11 > 0 || varRevestimento3D_11_1 > 0) {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Revestimento 3D: " + (Double.parseDouble(valorRevestimento3D11.getText().toString()) + Double.parseDouble(valorRevestimento3D11_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varRevestimento3D_11 + varRevestimento3D_11_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();
                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
        }
        if (valorTotalCategoriaHidraulica > 0) {
            PdfPTable tableHidraulica = new PdfPTable(1);
            tableHidraulica.setWidthPercentage(100);
            PdfPCell hidraulica = new PdfPCell();
            hidraulica.setBorder(Rectangle.NO_BORDER);
            hidraulica.addElement(paragrafoHidraulica);


            document.add(paragrafoHidraulica);
            document.add(espacoBranco);


            if (valorTotalHidraulicaCozinha > 0) {

                PdfPTable tableCozinha = new PdfPTable(1);
//                PdfPTable tableCozinhaServicos = new PdfPTable(qtdDemoCozinha);
//                tableCozinhaServicos.
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();

//                cellCozinha2.addHeader(paragrafoCozinha);
                cellCozinha2.addElement(paragrafoHidraulicaCozinha);
                cellCozinha1.setBorder(Rectangle.BOX);
//                tableCozinha.addCell(cellCozinha1);
                tableCozinha.addCell(cellCozinha2);

                document.add(tableCozinha);
                if (varAdicionarTorneiraEletrica > 0 || varAdicionarTorneiraEletrica1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira elétrica: " + (Double.parseDouble(valorHidraulicaTorneiraEletrica1.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraEletrica1 + varAdicionarTorneiraEletrica)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarTorneiraMonocomando > 0 || varAdicionarTorneiraMonocomando1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira monocomando: " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando1.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraMonocomando1 + varAdicionarTorneiraMonocomando)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }

                if (varAdicionarTorneiraSimples > 0 || varAdicionarTorneiraSimples1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira Simples: " + (Double.parseDouble(valorHidraulicaTorneiraSimples1.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraSimples + varAdicionarTorneiraSimples1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarValvulaSifao > 0 || varAdicionarValvulaSifao1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Válvula e Sifão simples: " + (Double.parseDouble(valorHidraulicaValvulaSifao1_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarValvulaSifao + varAdicionarValvulaSifao1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRegistroAcabamento > 0 || varAdicionarRegistroAcabamento1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Registros e acabamento: " + (Double.parseDouble(valorHidraulicaRegistroAcabamento1_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRegistroAcabamento + varAdicionarRegistroAcabamento1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoAgua > 0 || varAdicionarPontoAgua_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de água: " + (Double.parseDouble(valorHidraulicaCriacaoAgua1.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoAgua_1 + varAdicionarPontoAgua)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoEsgoto > 0 || varAdicionarPontoEsgoto_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de esgoto: " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto1.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoEsgoto_1 + varAdicionarPontoEsgoto)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo10cm > 0 || varAdicionarRalo10cm1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 10cm: " + (Double.parseDouble(valorHidraulicaRalo10cm1_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo10cm1 + varAdicionarRalo10cm)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo15cm > 0 || varAdicionarRalo15cm1 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 15cm: " + (Double.parseDouble(valorHidraulicaRalo15cm1_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo15cm1 + varAdicionarRalo15cm)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarChuveiro > 0 || varAdicionarChuveiro1 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Chuveiro: " + (Double.parseDouble(valorHidraulicaChuveiro1.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarChuveiro + varAdicionarChuveiro1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarRaloLinear > 0 || varAdicionarRaloLinear1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação de ralo linear: " + (Double.parseDouble(valorHidraulicaRaloLinear1.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear1_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRaloLinear + varAdicionarRaloLinear1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarVasoSanitario > 0 || varAdicionarVasoSanitario1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalar Vaso Sanitário: " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario1.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarVasoSanitario + varAdicionarVasoSanitario1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalHidraulicaBanheiroSocial > 0) {

                PdfPTable tableCozinha = new PdfPTable(1);
//                PdfPTable tableCozinhaServicos = new PdfPTable(qtdDemoCozinha);
//                tableCozinhaServicos.
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();

//                cellCozinha2.addHeader(paragrafoCozinha);
                cellCozinha2.addElement(paragrafoHidraulicaBanheiro);
                cellCozinha1.setBorder(Rectangle.BOX);
//                tableCozinha.addCell(cellCozinha1);
                tableCozinha.addCell(cellCozinha2);

                document.add(tableCozinha);

                if (varAdicionarTorneiraEletrica2 > 0 || varAdicionarTorneiraEletrica2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira elétrica: " + (Double.parseDouble(valorHidraulicaTorneiraEletrica2.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraEletrica2 + varAdicionarTorneiraEletrica2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarTorneiraMonocomando2 > 0 || varAdicionarTorneiraMonocomando2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira monocomando: " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando2.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraMonocomando2_1 + varAdicionarTorneiraMonocomando2)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }

                if (varAdicionarTorneiraSimples2_1 > 0 || varAdicionarTorneiraSimples2 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira Simples: " + (Double.parseDouble(valorHidraulicaTorneiraSimples2.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraSimples2 + varAdicionarTorneiraSimples2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarValvulaSifao2 > 0 || varAdicionarValvulaSifao2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Válvula e Sifão simples: " + (Double.parseDouble(valorHidraulicaValvulaSifao2_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao2.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarValvulaSifao2 + varAdicionarValvulaSifao2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRegistroAcabamento2 > 0 || varAdicionarRegistroAcabamento2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Registros e acabamento: " + (Double.parseDouble(valorHidraulicaRegistroAcabamento2_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento2.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRegistroAcabamento2_1 + varAdicionarRegistroAcabamento2)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoAgua_2 > 0 || varAdicionarPontoAgua_2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de água: " + (Double.parseDouble(valorHidraulicaCriacaoAgua2.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoAgua_2 + varAdicionarPontoAgua_2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoEsgoto_2 > 0 || varAdicionarPontoEsgoto_2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de esgoto: " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto2.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoEsgoto_2 + varAdicionarPontoEsgoto_2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo10cm2_1 > 0 || varAdicionarRalo10cm2 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 10cm: " + (Double.parseDouble(valorHidraulicaRalo10cm2_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm2.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo10cm2 + varAdicionarRalo10cm2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo15cm2 > 0 || varAdicionarRalo15cm2_1 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 15cm: " + (Double.parseDouble(valorHidraulicaRalo15cm2_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm2.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo15cm2 + varAdicionarRalo15cm2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarChuveiro2_1 > 0 || varAdicionarChuveiro2 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Chuveiro: " + (Double.parseDouble(valorHidraulicaChuveiro2.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarChuveiro2_1 + varAdicionarChuveiro2)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarRaloLinear2 > 0 || varAdicionarRaloLinear2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação de ralo linear: " + (Double.parseDouble(valorHidraulicaRaloLinear2.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRaloLinear2 + varAdicionarRaloLinear2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarVasoSanitario2 > 0 || varAdicionarVasoSanitario2_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalar Vaso Sanitário: " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario2.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario2_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarVasoSanitario2 + varAdicionarVasoSanitario2_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalHidraulicaAreaServico > 0) {

                PdfPTable tableCozinha = new PdfPTable(1);
//                PdfPTable tableCozinhaServicos = new PdfPTable(qtdDemoCozinha);
//                tableCozinhaServicos.
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();

//                cellCozinha2.addHeader(paragrafoCozinha);
                cellCozinha2.addElement(paragrafoHidraulicaAreaServico);
                cellCozinha1.setBorder(Rectangle.BOX);
//                tableCozinha.addCell(cellCozinha1);
                tableCozinha.addCell(cellCozinha2);

                document.add(tableCozinha);

                if (varAdicionarTorneiraEletrica3 > 0 || varAdicionarTorneiraEletrica3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira elétrica: " + (Double.parseDouble(valorHidraulicaTorneiraEletrica3.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraEletrica3 + varAdicionarTorneiraEletrica3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarTorneiraMonocomando3 > 0 || varAdicionarTorneiraMonocomando3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira monocomando: " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando3.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraMonocomando3_1 + varAdicionarTorneiraMonocomando3)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }

                if (varAdicionarTorneiraSimples3_1 > 0 || varAdicionarTorneiraSimples3 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira Simples: " + (Double.parseDouble(valorHidraulicaTorneiraSimples3.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraSimples3 + varAdicionarTorneiraSimples3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarValvulaSifao3 > 0 || varAdicionarValvulaSifao3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Válvula e Sifão simples: " + (Double.parseDouble(valorHidraulicaValvulaSifao3_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao3.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarValvulaSifao3 + varAdicionarValvulaSifao3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRegistroAcabamento3 > 0 || varAdicionarRegistroAcabamento3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Registros e acabamento: " + (Double.parseDouble(valorHidraulicaRegistroAcabamento3_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento3.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRegistroAcabamento3_1 + varAdicionarRegistroAcabamento3)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoAgua_3 > 0 || varAdicionarPontoAgua_3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de água: " + (Double.parseDouble(valorHidraulicaCriacaoAgua3.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoAgua_3 + varAdicionarPontoAgua_3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoEsgoto_3 > 0 || varAdicionarPontoEsgoto_3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de esgoto: " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto3.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoEsgoto_3 + varAdicionarPontoEsgoto_3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo10cm3_1 > 0 || varAdicionarRalo10cm3 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 10cm: " + (Double.parseDouble(valorHidraulicaRalo10cm3_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm3.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo10cm3 + varAdicionarRalo10cm3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo15cm3 > 0 || varAdicionarRalo15cm3_1 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 15cm: " + (Double.parseDouble(valorHidraulicaRalo15cm3_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm3.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo15cm3 + varAdicionarRalo15cm3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarChuveiro3_1 > 0 || varAdicionarChuveiro3 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Chuveiro: " + (Double.parseDouble(valorHidraulicaChuveiro3.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarChuveiro3_1 + varAdicionarChuveiro3)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarRaloLinear3 > 0 || varAdicionarRaloLinear3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação de ralo linear: " + (Double.parseDouble(valorHidraulicaRaloLinear3.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRaloLinear3 + varAdicionarRaloLinear3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarVasoSanitario3 > 0 || varAdicionarVasoSanitario3_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalar Vaso Sanitário: " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario3.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario3_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarVasoSanitario3 + varAdicionarVasoSanitario3_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalHidraulicaBanheiroSuite > 0) {

                PdfPTable tableCozinha = new PdfPTable(1);
//                PdfPTable tableCozinhaServicos = new PdfPTable(qtdDemoCozinha);
//                tableCozinhaServicos.
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();

//                cellCozinha2.addHeader(paragrafoCozinha);
                cellCozinha2.addElement(paragrafoHidraulicaBanheiro2);
                cellCozinha1.setBorder(Rectangle.BOX);
//                tableCozinha.addCell(cellCozinha1);
                tableCozinha.addCell(cellCozinha2);

                document.add(tableCozinha);

                if (varAdicionarTorneiraEletrica4 > 0 || varAdicionarTorneiraEletrica4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira elétrica: " + (Double.parseDouble(valorHidraulicaTorneiraEletrica4.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraEletrica4 + varAdicionarTorneiraEletrica4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarTorneiraMonocomando4 > 0 || varAdicionarTorneiraMonocomando4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira monocomando: " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando4.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraMonocomando4_1 + varAdicionarTorneiraMonocomando4)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }

                if (varAdicionarTorneiraSimples4_1 > 0 || varAdicionarTorneiraSimples4 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira Simples: " + (Double.parseDouble(valorHidraulicaTorneiraSimples4.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraSimples4 + varAdicionarTorneiraSimples4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarValvulaSifao4 > 0 || varAdicionarValvulaSifao4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Válvula e Sifão simples: " + (Double.parseDouble(valorHidraulicaValvulaSifao4_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao4.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarValvulaSifao4 + varAdicionarValvulaSifao4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRegistroAcabamento4 > 0 || varAdicionarRegistroAcabamento4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Registros e acabamento: " + (Double.parseDouble(valorHidraulicaRegistroAcabamento4_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento4.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRegistroAcabamento4_1 + varAdicionarRegistroAcabamento4)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoAgua_4 > 0 || varAdicionarPontoAgua_4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de água: " + (Double.parseDouble(valorHidraulicaCriacaoAgua4.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoAgua_4 + varAdicionarPontoAgua_4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoEsgoto_4 > 0 || varAdicionarPontoEsgoto_4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de esgoto: " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto4.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoEsgoto_4 + varAdicionarPontoEsgoto_4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo10cm4_1 > 0 || varAdicionarRalo10cm4 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 10cm: " + (Double.parseDouble(valorHidraulicaRalo10cm4_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm4.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo10cm4 + varAdicionarRalo10cm4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo15cm4 > 0 || varAdicionarRalo15cm4_1 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 15cm: " + (Double.parseDouble(valorHidraulicaRalo15cm4_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm4.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo15cm4 + varAdicionarRalo15cm4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarChuveiro4_1 > 0 || varAdicionarChuveiro4 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Chuveiro: " + (Double.parseDouble(valorHidraulicaChuveiro4.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarChuveiro4_1 + varAdicionarChuveiro4)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarRaloLinear4 > 0 || varAdicionarRaloLinear4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação de ralo linear: " + (Double.parseDouble(valorHidraulicaRaloLinear4.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRaloLinear4 + varAdicionarRaloLinear4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarVasoSanitario4 > 0 || varAdicionarVasoSanitario4_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalar Vaso Sanitário: " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario4.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario4_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarVasoSanitario4 + varAdicionarVasoSanitario4_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalHidraulicaLavabo > 0) {
                PdfPTable tableCozinha = new PdfPTable(1);
//                PdfPTable tableCozinhaServicos = new PdfPTable(qtdDemoCozinha);
//                tableCozinhaServicos.
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();

//                cellCozinha2.addHeader(paragrafoCozinha);
                cellCozinha2.addElement(paragrafoHidraulicaLavabo);
                cellCozinha1.setBorder(Rectangle.BOX);
//                tableCozinha.addCell(cellCozinha1);
                tableCozinha.addCell(cellCozinha2);

                document.add(tableCozinha);

                if (varAdicionarTorneiraEletrica5 > 0 || varAdicionarTorneiraEletrica5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira elétrica: " + (Double.parseDouble(valorHidraulicaTorneiraEletrica5.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraEletrica5 + varAdicionarTorneiraEletrica5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarTorneiraMonocomando5 > 0 || varAdicionarTorneiraMonocomando5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira monocomando: " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando5.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraMonocomando5_1 + varAdicionarTorneiraMonocomando5)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }

                if (varAdicionarTorneiraSimples5_1 > 0 || varAdicionarTorneiraSimples5 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira Simples: " + (Double.parseDouble(valorHidraulicaTorneiraSimples5.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraSimples5 + varAdicionarTorneiraSimples5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarValvulaSifao5 > 0 || varAdicionarValvulaSifao5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Válvula e Sifão simples: " + (Double.parseDouble(valorHidraulicaValvulaSifao5_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao5.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarValvulaSifao5 + varAdicionarValvulaSifao5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRegistroAcabamento5 > 0 || varAdicionarRegistroAcabamento5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Registros e acabamento: " + (Double.parseDouble(valorHidraulicaRegistroAcabamento5_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento5.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRegistroAcabamento5_1 + varAdicionarRegistroAcabamento5)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoAgua_5 > 0 || varAdicionarPontoAgua_5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de água: " + (Double.parseDouble(valorHidraulicaCriacaoAgua5.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoAgua_5 + varAdicionarPontoAgua_5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoEsgoto_5 > 0 || varAdicionarPontoEsgoto_5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de esgoto: " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto5.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoEsgoto_5 + varAdicionarPontoEsgoto_5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo10cm5_1 > 0 || varAdicionarRalo10cm5 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 10cm: " + (Double.parseDouble(valorHidraulicaRalo10cm5_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm5.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo10cm5 + varAdicionarRalo10cm5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo15cm5 > 0 || varAdicionarRalo15cm5_1 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 15cm: " + (Double.parseDouble(valorHidraulicaRalo15cm5_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm5.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo15cm5 + varAdicionarRalo15cm5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarChuveiro5_1 > 0 || varAdicionarChuveiro5 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Chuveiro: " + (Double.parseDouble(valorHidraulicaChuveiro5.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarChuveiro5_1 + varAdicionarChuveiro5)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarRaloLinear5 > 0 || varAdicionarRaloLinear5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação de ralo linear: " + (Double.parseDouble(valorHidraulicaRaloLinear5.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRaloLinear5 + varAdicionarRaloLinear5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarVasoSanitario5 > 0 || varAdicionarVasoSanitario5_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalar Vaso Sanitário: " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario5.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario5_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarVasoSanitario5 + varAdicionarVasoSanitario5_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
            if (valorTotalHidraulicaSacada > 0) {

                PdfPTable tableCozinha = new PdfPTable(1);
                tableCozinha.setWidthPercentage(100);
                PdfPCell cellCozinha2 = new PdfPCell();
                PdfPCell cellCozinha1 = new PdfPCell();
                cellCozinha2.addElement(paragrafoHidraulicaSacadaVaranda);
                cellCozinha1.setBorder(Rectangle.BOX);
                tableCozinha.addCell(cellCozinha2);

                document.add(tableCozinha);




                if (varAdicionarTorneiraEletrica6 > 0 || varAdicionarTorneiraEletrica6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira elétrica: " + (Double.parseDouble(valorHidraulicaTorneiraEletrica6.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraEletrica6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraEletrica6 + varAdicionarTorneiraEletrica6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarTorneiraMonocomando6 > 0 || varAdicionarTorneiraMonocomando6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira monocomando: " + (Double.parseDouble(valorHidraulicaTorneiraMonocomando6.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraMonocomando6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraMonocomando6_1 + varAdicionarTorneiraMonocomando6)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }

                if (varAdicionarTorneiraSimples6_1 > 0 || varAdicionarTorneiraSimples6 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Torneira Simples: " + (Double.parseDouble(valorHidraulicaTorneiraSimples6.getText().toString()) + Double.parseDouble(valorHidraulicaTorneiraSimples6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarTorneiraSimples6 + varAdicionarTorneiraSimples6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarValvulaSifao6 > 0 || varAdicionarValvulaSifao6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Válvula e Sifão simples: " + (Double.parseDouble(valorHidraulicaValvulaSifao6_1.getText().toString()) + Double.parseDouble(valorHidraulicaValvulaSifao6.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarValvulaSifao6 + varAdicionarValvulaSifao6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRegistroAcabamento6 > 0 || varAdicionarRegistroAcabamento6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Registros e acabamento: " + (Double.parseDouble(valorHidraulicaRegistroAcabamento6_1.getText().toString()) + Double.parseDouble(valorHidraulicaRegistroAcabamento6.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRegistroAcabamento6_1 + varAdicionarRegistroAcabamento6)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoAgua_6 > 0 || varAdicionarPontoAgua_6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de água: " + (Double.parseDouble(valorHidraulicaCriacaoAgua6.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoAgua6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoAgua_6 + varAdicionarPontoAgua_6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarPontoEsgoto_6 > 0 || varAdicionarPontoEsgoto_6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Criação de ponto de esgoto: " + (Double.parseDouble(valorHidraulicaCriacaoEsgoto6.getText().toString()) + Double.parseDouble(valorHidraulicaCriacaoEsgoto6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarPontoEsgoto_6 + varAdicionarPontoEsgoto_6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo10cm6_1 > 0 || varAdicionarRalo10cm6 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 10cm: " + (Double.parseDouble(valorHidraulicaRalo10cm6_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo10cm6.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo10cm6 + varAdicionarRalo10cm6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarRalo15cm6 > 0 || varAdicionarRalo15cm6_1 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação Ralo de 15cm: " + (Double.parseDouble(valorHidraulicaRalo15cm6_1.getText().toString()) + Double.parseDouble(valorHidraulicaRalo15cm6.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRalo15cm6 + varAdicionarRalo15cm6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarChuveiro6_1 > 0 || varAdicionarChuveiro6 > 0)
                {
                    Paragraph removerRevestimentoParede = new Paragraph(">>> Chuveiro: " + (Double.parseDouble(valorHidraulicaChuveiro6.getText().toString()) + Double.parseDouble(valorHidraulicaChuveiro6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarChuveiro6_1 + varAdicionarChuveiro6)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);

                }
                if (varAdicionarRaloLinear6 > 0 || varAdicionarRaloLinear6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalação de ralo linear: " + (Double.parseDouble(valorHidraulicaRaloLinear6.getText().toString()) + Double.parseDouble(valorHidraulicaRaloLinear6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarRaloLinear6 + varAdicionarRaloLinear6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                if (varAdicionarVasoSanitario6 > 0 || varAdicionarVasoSanitario6_1 > 0)
                {

                    Paragraph removerRevestimentoParede = new Paragraph(">>> Instalar Vaso Sanitário: " + (Double.parseDouble(valorHidraulicaInstalarVasoSanitario6.getText().toString()) + Double.parseDouble(valorHidraulicaInstalarVasoSanitario6_1.getText().toString())) + " m²", boldServicosPrestados);
                    Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format((varAdicionarVasoSanitario6 + varAdicionarVasoSanitario6_1)), boldServicosPrestados);
                    valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                    removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                    removerRevestimentoParede.setLeading(0.70f);

                    //
                    PdfPTable tabela = new PdfPTable(1);
                    tabela.setWidthPercentage(100);
                    PdfPCell celula = new PdfPCell();

                    celula.addElement(removerRevestimentoParede);
                    celula.setBorder(Rectangle.NO_BORDER);
                    tabela.addCell(celula);
                    document.add(tabela);
                }
                document.add(espacoBranco1);
            }
        }

        if (BtncheckBoxArtArCondicionado.isChecked() || BtncheckboxArtBox.isChecked() || BtncheckboxArtDemolicao.isChecked() || BtncheckboxArtDeslocamento.isChecked() || BtncheckboxArtEletrica.isChecked() || BtncheckboxArtEnvidracamento.isChecked() || BtncheckboxArtHidraulica.isChecked() || BtncheckboxArtMoveisPlanejados.isChecked() || BtncheckboxArtGesso.isChecked() || BtncheckboxArtNovosRevestimentos.isChecked() || BtncheckboxArtPedrasMarmore.isChecked()) {

//            PdfPTable tableDemolicao = new PdfPTable(1);
//            tableDemolicao.setWidthPercentage(100);
//            PdfPCell demolicao = new PdfPCell();
//            demolicao.setBorder(Rectangle.NO_BORDER);
//            demolicao.addElement(paragrafoAreaServico);
//
//            document.add(paragrafoArt);
//            document.add(espacoBranco1);



            document.add(paragrafoArt);
            art();


//            tableCozinha.addCell(paragrafoArtTotal);
            valorTotalCategoriaArt = valorTotalCategoriaArt + valorTotalArtTaxa;

            Paragraph paragrafoArtTotal = new Paragraph(0, "ART", boldServicos);
            Paragraph paragrafoArtTotalValor = new Paragraph("Total R$" + df.format(valorTotalCategoriaArt));
            paragrafoArtTotalValor.setAlignment(Element.ALIGN_RIGHT);
            paragrafoArtTotal.add(0, paragrafoArtTotalValor);
            paragrafoArtTotal.setLeading(0.70f);





            PdfPTable tableCozinhaa = new PdfPTable(1);
//                PdfPTable tableCozinhaServicos = new PdfPTable(qtdDemoCozinha);
//                tableCozinhaServicos.
            tableCozinhaa.setWidthPercentage(100);
            PdfPCell cellCozinha22 = new PdfPCell();
            PdfPCell cellCozinha12 = new PdfPCell();

//                cellCozinha2.addHeader(paragrafoCozinha);
            cellCozinha22.addElement(paragrafoRevestimentoCozinha);
            cellCozinha12.setBorder(Rectangle.BOX);
            cellCozinha12.addElement(paragrafoArtTotal);
            tableCozinhaa.addCell(cellCozinha12);
            document.add(tableCozinhaa);


            if (BtncheckBoxArtArCondicionado.isChecked()) {

                Paragraph removerRevestimentoParede = new Paragraph(">>> Ar Condicionado: ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParedee = new Paragraph("R$" + df.format(valorTotalArtArcondicionado), boldServicosPrestados);
                valorRemoverRevestimentoParedee.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParedee);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }

            if (BtncheckboxArtEnvidracamento.isChecked()) {

                Paragraph removerRevestimentoParede = new Paragraph(">>> Envidraçamento de sacada: ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtEnvidracamento), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtPedrasMarmore.isChecked()) {


                Paragraph removerRevestimentoParede = new Paragraph(">>> Pedras de mármores/granito (Pia, lavatório e tanque): ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtPedrasMarmore), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtNovosRevestimentos.isChecked()) {


                Paragraph removerRevestimentoParede = new Paragraph(">>> Novos revestimentos (demolição e instalação de porcelanato/cerâmica): ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtNovosRevestimentos), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtEletrica.isChecked()) {

                Paragraph removerRevestimentoParede = new Paragraph(">>> Elétrica (Instalações, ramificações e deslocamento): ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtEletrica), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtHidraulica.isChecked()) {

                Paragraph removerRevestimentoParede = new Paragraph(">>> Hidráulica (Instalações, ramificações e deslocamento): ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtHidraulica), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtBox.isChecked()) {


                Paragraph removerRevestimentoParede = new Paragraph(">>> Box, espelhos, laminados/vinílico e Acessórios (chuveiro, prateleira, cortinas, etc.): ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtBox), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtGesso.isChecked()) {


                Paragraph removerRevestimentoParede = new Paragraph(">>> Gesso (Sanca, rebaixo, golas, molduras, Dry-wall) e Pintura Decorativa: ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtBox), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtDemolicao.isChecked()) {

                Paragraph removerRevestimentoParede = new Paragraph(">>> Demolição de parede não estrutural: ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtDemolicaoParedeNaoEstrutural), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);
            }
            if (BtncheckboxArtMoveisPlanejados.isChecked()) {
                Paragraph removerRevestimentoParede = new Paragraph(">>> Móveis planejados: ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtMoveisPlanejados), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);


            }
            if (BtncheckboxArtDeslocamento.isChecked()) {


                Paragraph removerRevestimentoParede = new Paragraph(">>> Deslocamento do ponto de abastecimento de gás: ", boldServicosPrestados);
                Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtDeslocamentoPontoGas), boldServicosPrestados);
                valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
                removerRevestimentoParede.add(0, valorRemoverRevestimentoParede);
                removerRevestimentoParede.setLeading(0.70f);

                //
                PdfPTable tabela = new PdfPTable(1);
                tabela.setWidthPercentage(100);
                PdfPCell celula = new PdfPCell();

                celula.addElement(removerRevestimentoParede);
                celula.setBorder(Rectangle.NO_BORDER);
                tabela.addCell(celula);
                document.add(tabela);

            }
            Paragraph removerRevestimentoParedee = new Paragraph(">>> Taxas do CREA: ", boldServicosPrestados);
            Paragraph valorRemoverRevestimentoParede = new Paragraph("R$" + df.format(valorTotalArtTaxa), boldServicosPrestados);
            valorRemoverRevestimentoParede.setAlignment(Element.ALIGN_RIGHT);
            removerRevestimentoParedee.add(0, valorRemoverRevestimentoParede);
            removerRevestimentoParedee.setLeading(0.70f);

            //
            PdfPTable tabela = new PdfPTable(1);
            tabela.setWidthPercentage(100);
            PdfPCell celula = new PdfPCell();

            celula.addElement(removerRevestimentoParedee);
            celula.setBorder(Rectangle.NO_BORDER);
            tabela.addCell(celula);
            document.add(tabela);
            document.add(espacoBranco1);

        }
        latitude = latitude + totalDemolicao + valorTotalCategoriaHidraulica + valorTotalCategoriaPintura + valorTotalCategoriaRevestimento + valorTotalCategoriaArt;


        String valorNotaTexto = df.format(latitude);
        SharedPreferences mypref2 = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor2 = mypref.edit();
        editor2.putString("valorNota", valorNotaTexto);
        editor2.commit();
        alterarValorNota = Integer.toString(numeroNotaAtual);
        exibirNota.setText("000" + alterarNumeroNota);
        exibirValorNota.setText(valorNotaTexto);
        String valorNumeroNota = alterarNumeroNota.toString();



        //Valor Total
        ValoresCompartilhados valoresCompartilhados = new ValoresCompartilhados();
        valoresCompartilhados.setValorTotal(valorNotaTexto);
        valoresCompartilhados.setNumeroNota(valorNumeroNota);
        DatabaseReference reference = database.getReference("ValoresCompartilhados/");
        reference.setValue(valoresCompartilhados);
        Paragraph valorTotal = new Paragraph("TOTAL R$ " + valorNotaTexto , boldTotal);
        valorTotal.setAlignment(Element.ALIGN_CENTER);
        valorTotal.setSpacingBefore(15);
        document.add(valorTotal);


        document.add(tableProposta);


        // document.add(new LineSeparator());
        document.add(paragrafoDisposicao);
        document.close();

        viewPdf();

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
    public void art(){
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
    }
    public void viewPdf(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, "vostore/orcamento/ragonezi/app/Orcamento");
        email.putExtra(Intent.EXTRA_TEXT,"Olá ! \n\nSegue por anexo, o nosso Orçamento");
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RelatóriosRagonezi/Orcamentos");
        pdfFile = new File(docsFolder.getAbsolutePath(), "vostore/orcamento/ragonezi/app/Orcamento" + "000" + alterarNumeroNota + ".pdf");
        Uri uri = FileProvider.getUriForFile(this, "vostore.apporcamentoragonezi", pdfFile);
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("message/rfc822");
        startActivity(email);
        onStop();
    }
}
