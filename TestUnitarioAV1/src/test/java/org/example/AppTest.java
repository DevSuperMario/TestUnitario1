package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

public class AppTest {

    Produto produto;

    @BeforeEach
    public void setup() {
        produto = new Produto("Guitarra", 2500.00, 10);
    }

    @Test
    public void testCriacaoProdutoValoresValidos() {
        Produto p = new Produto("Afinador", 99.90, 5);
        Assertions.assertEquals("Afinador", p.getNome());
        Assertions.assertEquals(99.90, p.getPreco());
        Assertions.assertEquals(5, p.getEstoque());
    }

    @Test
    public void testCriacaoProdutoPrecoNegativo() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Afinador", -10.00, 5);
        });
    }

    @Test
    public void testCriacaoProdutoEstoqueNegativo() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Produto("Afinador", 10.00, -5);
        });
    }

    @Test
    public void testAlteracaoNomeProduto() {
        produto.setNome("Pedal");
        Assertions.assertEquals("Pedal", produto.getNome());
    }

    @Test
    public void testAlteracaoPrecoProduto() {
        produto.setPreco(2800.00);
        Assertions.assertEquals(2800.00, produto.getPreco());
    }

    @Test
    public void testAumentoEstoqueValorPositivo() {
        produto.aumentarEstoque(5);
        Assertions.assertEquals(15, produto.getEstoque());
    }

    @Test
    public void testAumentoEstoqueValorNegativoNaoAfeta() {
        produto.aumentarEstoque(-5);
        Assertions.assertEquals(10, produto.getEstoque());
    }

    @Test
    public void testVendaQuantidadeMenorQueEstoque() {
        Venda venda = new Venda(produto, 3);
        boolean sucesso = venda.realizarVenda();
        Assertions.assertTrue(sucesso);
        Assertions.assertEquals(7, produto.getEstoque());
    }

    @Test
    public void testVendaQuantidadeIgualEstoque() {
        Venda venda = new Venda(produto, 10);
        boolean sucesso = venda.realizarVenda();
        Assertions.assertTrue(sucesso);
        Assertions.assertEquals(0, produto.getEstoque());
    }

    @Test
    public void testVendaQuantidadeMaiorQueEstoque() {
        Venda venda = new Venda(produto, 15);
        boolean sucesso = venda.realizarVenda();
        Assertions.assertFalse(sucesso);
        Assertions.assertEquals(10, produto.getEstoque());
    }

    @Test
    public void testCalculoTotalVenda() {
        Venda venda = new Venda(produto, 2);
        venda.realizarVenda();
        Assertions.assertEquals(7000.00, venda.getTotalVenda());
    }

    @Test
    public void testAumentoEstoqueAposVenda() {
        Venda venda = new Venda(produto, 5);
        venda.realizarVenda();
        produto.aumentarEstoque(2);
        Assertions.assertEquals(7, produto.getEstoque());
    }

    @Test
    public void testDiminuicaoEstoqueAposVenda() {
        Venda venda = new Venda(produto, 4);
        venda.realizarVenda();
        Assertions.assertEquals(6, produto.getEstoque());
    }

    @Test
    public void testVendaProdutoInexistente() {
        Produto nulo = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Venda(nulo, 2).realizarVenda();
        });
    }

    @Test
    public void testCriacaoVendaQuantidadeNegativa() {
        Venda venda = new Venda(produto, -3);
        boolean sucesso = venda.realizarVenda();
        Assertions.assertFalse(sucesso);
    }

    @Test
    public void testVendaComEstoqueInsuficiente() {
        produto = new Produto("Cabop10", 100, 1);
        Venda venda = new Venda(produto, 2);
        boolean sucesso = venda.realizarVenda();
        Assertions.assertFalse(sucesso);
    }

    @Test
    public void testVendaEstoqueCompartilhado() {
        Produto p = new Produto("Pedaleira", 800.00, 10);
        Venda v1 = new Venda(p, 6);
        Venda v2 = new Venda(p, 4);
        Assertions.assertTrue(v1.realizarVenda());
        Assertions.assertTrue(v2.realizarVenda());
        Assertions.assertEquals(0, p.getEstoque());
    }

    @Test
    public void testAlterarPrecoAntesDaVenda() {
        produto.setPreco(4000.00);
        Venda venda = new Venda(produto, 2);
        venda.realizarVenda();
        Assertions.assertEquals(8000.00, venda.getTotalVenda());
    }

    @Test
    public void testVendaEstoqueInicialZero() {
        Produto p = new Produto("Ponte", 300, 0);
        Venda venda = new Venda(p, 1);
        boolean sucesso = venda.realizarVenda();
        Assertions.assertFalse(sucesso);
    }

    @Test
    public void testVendaAposReposicaoEstoque() {
        Produto p = new Produto("Knobs", 50, 0);
        p.aumentarEstoque(3);
        Venda venda = new Venda(p, 2);
        Assertions.assertTrue(venda.realizarVenda());
        Assertions.assertEquals(1, p.getEstoque());
    }
}
