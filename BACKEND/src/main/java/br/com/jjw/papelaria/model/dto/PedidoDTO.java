package br.com.jjw.papelaria.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @NotNull(message = "produto precisa ser prenchido")
    private Long idProduto;

    private Long idPedido;

    private int quantidade;

    private int numeroPedido;

    private double valorTotal;

}
