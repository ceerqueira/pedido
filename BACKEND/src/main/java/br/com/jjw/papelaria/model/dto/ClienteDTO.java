package br.com.jjw.papelaria.model.dto;

import br.com.jjw.papelaria.util.ConstantesUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class ClienteDTO {

    private String nome;


    private String endereco;


    private String telefone;

    private int numeroPedido;

    @Builder.Default
    private String status = ConstantesUtils.EM_PROGRESSO;

}
