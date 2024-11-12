package com.example.api_produtos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    public void testSalvarProduto() {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");

        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto resultado = produtoService.criarProduto(produto);

        assertNotNull(resultado);
        assertEquals("Produto Teste", resultado.getNome());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void testBuscarProdutoPorId() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> resultado = produtoService.buscarProdutoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Produto Teste", resultado.get().getNome());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeletarProduto() {
        Long id = 1L;
        produtoService.deletarProduto(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }
}
