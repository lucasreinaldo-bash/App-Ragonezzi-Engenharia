package vostore.orcamento.ragonezi.app.Firebase;

public class ValoresCompartilhados {

    private String valorTotal;
    private String numeroNota;

    public ValoresCompartilhados(String valorTotal, String numeroNota) {
        this.valorTotal = valorTotal;
        this.numeroNota = numeroNota;
    }
    public ValoresCompartilhados() {

    }
    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }
}
