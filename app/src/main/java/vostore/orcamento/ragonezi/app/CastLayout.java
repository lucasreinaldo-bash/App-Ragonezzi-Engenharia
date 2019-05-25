package vostore.orcamento.ragonezi.app;

import android.content.Context;
import android.net.Uri;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.ncapdevi.fragnav.FragNavController;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import vostore.apporcamentoragonezi.R;

public class CastLayout extends AppCompatActivity {
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
    private CheckBox checkBoxPinturaCozinha, checkBoxPinturaBanheiroSocial, checkBoxPinturaAreaServico, checkBoxPinturaBanheiroSuite, checkBoxPinturaLavabo, checkBoxPinturaSacadaVaranda, checkBoxPinturaSalaJantar, checkBoxPinturaSalaEstar, checkBoxPinturaQuartoSuite, checkBoxPinturaQuarto1, checkBoxPinturaQuarto2,checkBoxPinturaM2;
    private CheckBox checkBoxHidraulicaCozinha, checkBoxHidraulicaBanheiroSocial, checkBoxHidraulicaAreaServico, checkBoxHidraulicaBanheiroSuite, checkBoxHidraulicaLavabo, checkBoxHidraulicaSacadaVaranda, checkBoxHidraulicaSalaJantar, checkBoxHidraulicaSalaEstar, checkBoxHidraulicaQuartoSuite, checkBoxHidraulicaQuarto1, checkBoxHidraulicaQuarto2;
    private CheckBox checkBoxRevestimentoCozinha, checkBoxRevestimentoBanheiroSocial, checkBoxRevestimentoAreaServico, checkBoxRevestimentoBanheiroSuite, checkBoxRevestimentoLavabo, checkBoxRevestimentoSacadaVaranda, checkBoxRevestimentoSalaJantar, checkBoxRevestimentoSalaEstar, checkBoxRevestimentoQuartoSuite, checkBoxRevestimentoQuarto1, checkBoxRevestimentoQuarto2;
    private CheckBox checkBoxBanheiroSocial, checkBoxAreaServico, checkBoxBanheiroSuite, checkBoxLavabo, checkBoxSacadaVaranda, checkBoxSalaJantar, checkBoxSalaEstar, checkBoxQuartoSuite, checkBoxQuarto1, checkBoxQuarto2;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9, linearLayout10, linearLayout11;
    private LinearLayout linearLayoutPintura1, linearLayoutPintura2, linearLayoutPintura3, linearLayoutPintura4, linearLayoutPintura5, linearLayoutPintura6, linearLayoutPintura7, linearLayoutPintura8, linearLayoutPintura9, linearLayoutPintura10, linearLayoutPintura11,linearLayoutPintura12;
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
            valorPinturaReparoGesso1, valorPinturaReparoGesso1_1, valorPinturaReparoGesso2, valorPinturaReparoGesso2_1, valorPinturaReparoGesso3, valorPinturaReparoGesso3_1, valorPinturaReparoGesso4, valorPinturaReparoGesso4_1, valorPinturaReparoGesso5, valorPinturaReparoGesso5_1, valorPinturaReparoGesso6, valorPinturaReparoGesso6_1, valorPinturaReparoGesso7, valorPinturaReparoGesso7_1, valorPinturaReparoGesso8, valorPinturaReparoGesso8_1, valorPinturaReparoGesso9, valorPinturaReparoGesso9_1, valorPinturaReparoGesso10, valorPinturaReparoGesso10_1, valorPinturaReparoGesso11, valorPinturaReparoGesso11_1,valorPinturaApartamento1,valorPinturaApartamento1_1;
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
    double precoPinturaApartament0 = 40.00;


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
    private double varM2 = 0;
    private double varM2_1 = 0;


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
    private double valorTotalPinturaApartamento = 0;


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
    private double valorTotalArtTaxa = 85.96;


    private int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int CAMERA = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static String mCurrentPhotoPath;


    //Base de Dados
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    public void casts(){
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
    }
}
