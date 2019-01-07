package br.com.felorenzoni.agenda;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.security.Permission;
import java.util.List;

import br.com.felorenzoni.agenda.dao.AlunoDAO;
import br.com.felorenzoni.agenda.modelo.Aluno;

public class ListaAlunoActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lista_aluno);
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        Button novoAluno = findViewById(R.id.novo_aluno);

        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id){
                Aluno aluno = (Aluno) lista.getItemAtPosition(position);
                Intent intent = new Intent(ListaAlunoActivity.this,FormularioActivity.class);
                intent.putExtra("Aluno",aluno);
                startActivity(intent);
            }

        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    public void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos(this);
        dao.close();

        ListView listaAlunos = findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);


        MenuItem itemLigar = menu.add("Ligar");

        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
               if(ActivityCompat.checkSelfPermission(ListaAlunoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);

                }else{
                   Intent intentLigar = new Intent(Intent.ACTION_CALL);
                   intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                   startActivity(intentLigar);

               }
            return false;
            }
        });



        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Vizualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Visualizar Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = aluno.getSite();
        if(!site.startsWith("http://")){
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlunoDAO dao = new AlunoDAO(ListaAlunoActivity.this );
                dao.deleta(aluno);
                dao.close();

                carregaLista();
                return false;
            }
        });



    }




}
