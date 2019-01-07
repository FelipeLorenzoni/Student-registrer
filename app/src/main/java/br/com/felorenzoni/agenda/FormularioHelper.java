package br.com.felorenzoni.agenda;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.felorenzoni.agenda.modelo.Aluno;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    private Aluno aluno;



    public FormularioHelper (FormularioActivity activity){

        campoNome = activity.findViewById(R.id.formulario_nome);
        campoEndereco = activity.findViewById(R.id.formulario_endereco);
        campoTelefone = activity.findViewById(R.id.formulario_telefone);
        campoSite = activity.findViewById(R.id.formulario_site);
        campoNota = activity.findViewById(R.id.formulario_ratingbar);
        campoFoto = activity.findViewById(R.id.formulario_foto);

        aluno = new Aluno();
    }

    public Aluno PegaAluno(){

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota((double) campoNota.getProgress());
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void PreencheFormulario(Aluno aluno){

        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setRating(aluno.getNota().intValue());
        carregaFoto(aluno.getCaminhoFoto());

        this.aluno = aluno;

    }

    public void carregaFoto(String caminhoFoto) {
        if(caminhoFoto != null) {
            CarregadorDeFoto carregadorDeFoto = new CarregadorDeFoto();

            Bitmap bitmap = carregadorDeFoto.carrega(caminhoFoto);
            Bitmap bitmapreduzido = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
            campoFoto.setImageBitmap(bitmapreduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
