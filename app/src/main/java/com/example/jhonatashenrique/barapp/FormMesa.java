package com.example.jhonatashenrique.barapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class FormMesa extends AppCompatActivity {

    TextView txttotal;
    EditText cliente_nome, produto, nmesa, valor_produto;
    Button btgravar, btlancar, btfechar;
    ListView lista;
    ListaDAO dao;
    List<Produtos> listMesas;
    ArrayAdapter adapter;
    MesaDAO daomesa;
    Mesas mesatoedit,mesas;
    Produtos produt;
    Double valortotal,valorP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mesa);

        txttotal = (TextView) findViewById(R.id.textView6);
        Intent intent = getIntent();
       mesatoedit = (Mesas) intent.getSerializableExtra("mesa-selecionada");
        valortotal=0.0;
        if (mesatoedit != null  ) {
            valortotal = Double.parseDouble(String.valueOf(mesatoedit.getValortotal()));
        }
        txttotal.setText("TOTAL: R$"+valortotal);

        produt = new Produtos();
        mesas = new Mesas();
        dao = new ListaDAO(FormMesa.this);
        daomesa = new MesaDAO(FormMesa.this);

        cliente_nome = (EditText) findViewById(R.id.txtcliente_nome);
        produto = (EditText) findViewById(R.id.txtproduto);
        nmesa = (EditText) findViewById(R.id.txtmesa);
        valor_produto = (EditText) findViewById(R.id.txtvalor);


        btlancar = (Button) findViewById(R.id.btlancar);
        btlancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(produto.getText().toString())== false){
                    Toast.makeText(FormMesa.this, "Produto esta vazio", Toast.LENGTH_SHORT).show();
                } else if (isEmpty(valor_produto.getText().toString())== false){
                    Toast.makeText(FormMesa.this, "Valor do produto esta vazio", Toast.LENGTH_SHORT).show();
                } else{

                    valorP = Double.parseDouble(valor_produto.getText().toString());
                    valortotal = valortotal+valorP;

                    txttotal.setText("TOTAL: R$"+valortotal);

                    produt.setId_nmesa(mesatoedit.getId());
                    produt.setProduto(produto.getText().toString());
                    produt.setValor_produto(Double.parseDouble(valor_produto.getText().toString()));
                    mesas.setValortotal(valortotal);
                    mesas.setId(mesatoedit.getId());

                    dao.alteraDados(mesas);
                    dao.close();
                    daomesa.salvaDados(produt);
                    daomesa.close();

                    produto.setText("");
                    valor_produto.setText("");

                    carregaMesas();
                }


            }
        });

        btfechar = (Button) findViewById(R.id.btfechar);
        btfechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alerta();
            }
        });

        btgravar = (Button) findViewById(R.id.btgravar);
        btgravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(cliente_nome.getText().toString())== false) {
                    Toast.makeText(FormMesa.this, "Nome do cliente vazio", Toast.LENGTH_SHORT).show();
                } else if (isEmpty(nmesa.getText().toString()) == false){
                    Toast.makeText(FormMesa.this, "Numero da mesa vazio", Toast.LENGTH_SHORT).show();
                }else{

                    mesas.setCliente_nome(cliente_nome.getText().toString());
                    mesas.setN_mesa(Integer.parseInt(nmesa.getText().toString()));
                    mesas.setValortotal(0.0);


                    dao.salvaDados(mesas);
                    dao.close();

                    Toast.makeText(FormMesa.this, "Dados Salvos", Toast.LENGTH_LONG).show();

                    cliente_nome.setEnabled(false);
                    nmesa.setEnabled(false);
                    btgravar.setEnabled(false);
                    btlancar.setEnabled(true);
                    btfechar.setEnabled(true);
                    produto.setEnabled(true);
                    valor_produto.setEnabled(true);

                    finish();

                }




            }
        });

        lista = (ListView) findViewById(R.id.listprodutos);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produt = (Produtos) adapter.getItemAtPosition(position);
                return false;
            }
        });

        registerForContextMenu(lista);

        if (mesatoedit == null){
            btlancar.setEnabled(false);
            btfechar.setEnabled(false);
            produto.setEnabled(false);
            valor_produto.setEnabled(false);
        }else {
            cliente_nome.setText(mesatoedit.getCliente_nome());
            cliente_nome.setEnabled(false);
            nmesa.setText(String.valueOf(mesatoedit.getN_mesa()));
            nmesa.setEnabled(false);
            btgravar.setEnabled(false);
        }

    }

    //Testando se o Edittext esta vazio
    private boolean isEmpty(String etText) {
        String text = etText.trim();
        if (text.length()<1)
            return false;
        return true;
    }

    //Dialogo de alerta para fechar conta
    private AlertDialog alerta;

    private void Alerta() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Quer fechar a conta?");
        //define a mensagem
        builder.setMessage("Tem certeza: ");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                mesas.setId(mesatoedit.getId());
                dao.deletaDados(mesas);
                dao.close();
                finish();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    //Metodo para criar uma opcao de delete
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Deletar Pedido");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                valorP = produt.getValor_produto();
                valortotal = valortotal-valorP;

                txttotal.setText("TOTAL: R$"+valortotal);

                mesas.setValortotal(valortotal);
                mesas.setId(mesatoedit.getId());

                dao.alteraDados(mesas);
                dao.close();

                daomesa = new MesaDAO(FormMesa.this);
                daomesa.deletaDados(produt);
                daomesa.close();

                carregaMesas();

                return true;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    protected void onResume() {
        super.onResume();
        carregaMesas();
    }

    public void carregaMesas(){
        daomesa = new MesaDAO (FormMesa.this);
        if (mesatoedit != null) {
            listMesas = daomesa.getLista(mesatoedit.getId());
            daomesa.close();
            if (listMesas != null) {
                adapter = new ArrayAdapter<Produtos>(FormMesa.this, android.R.layout.simple_list_item_1, listMesas);
                lista.setAdapter(adapter);
            }
        }
    }

}
