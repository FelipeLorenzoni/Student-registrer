package br.com.felorenzoni.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.felorenzoni.agenda.ListaAlunoActivity;
import br.com.felorenzoni.agenda.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO (Context context){
        super(context,"Agenda",null,1);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE Alunos(" +
                "id INTEGER PRIMARY KEY " +
                ", nome TEXT NOT NULL" +
                ", endereco TEXT " +
                ", telefone TEXT" +
                ", site TEXT" +
                ", nota REAL) ";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova){
        String sql = "DROP TABLE IF EXISTS Alunos;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Aluno aluno){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues novoaluno = new ContentValues();

        novoaluno.put("nome",aluno.getNome());
        novoaluno.put("endereco",aluno.getEndereco());
        novoaluno.put("site",aluno.getSite());
        novoaluno.put("telefone",aluno.getTelefone());
        novoaluno.put("nota",aluno.getNota());

        db.insert("Alunos",null,novoaluno);

    }

    public List<Aluno> buscaAlunos(ListaAlunoActivity listaAlunoActivity) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alunos;",null);
        List<Aluno> alunos = new ArrayList<>();
        while (c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

            alunos.add(aluno);
        }
        c.close();
        return alunos;


    }
}
