package com.example.api_produtos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAdicionarProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição Teste");

        when(produtoService.criarProduto(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.descricao").value("Descrição Teste"));
    }

    @Test
    public void testBuscarProdutoPorId() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        when(produtoService.buscarProdutoPorId(1L)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void testDeletarProduto() throws Exception {
        Long id = 1L;

        doNothing().when(produtoService).deletarProduto(id);

        mockMvc.perform(delete("/api/produtos/" + id))
                .andExpect(status().isNoContent());

        verify(produtoService, times(1)).deletarProduto(id);
    }
}

