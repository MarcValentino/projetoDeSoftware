package com.carlosribeiro.excecao;

public class EstadoDeObjetoObsoletoException extends Exception{
    private final static long serialVersionUID = 2;

    private int codigo;

    public EstadoDeObjetoObsoletoException(String msg)
    {	super(msg);
    }

    public EstadoDeObjetoObsoletoException(int codigo, String msg)
    {	super(msg);
        this.codigo = codigo;
    }

    public int getCodigoDeErro()
    {	return codigo;
    }
}
