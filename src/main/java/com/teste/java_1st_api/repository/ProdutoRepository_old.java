package com.teste.java_1st_api.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.teste.java_1st_api.model.Produto;
import com.teste.java_1st_api.model.exception.ResourceNotFoundException;

@Repository
public class ProdutoRepository_old {
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    /**
     * 
     * @return return a product list
     */
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }
    
    public Optional<Produto> getProductId(Integer id){
        return produtos.stream().filter(produto -> produto.getId() == id).findFirst();
    }

    public Produto addProduct(Produto produto){
        ultimoId++;
        produto.setId(ultimoId);
        produtos.add(produto);
        return produto;
    }

    public void delete (Integer id){
        produtos.removeIf(produto -> produto.getId() == id);
    }

    public Produto update(Integer id, Produto produto){
        Optional<Produto> finded = getProductId(produto.getId());

        if (finded.isEmpty()){
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        delete(produto.getId());

        produtos.add(produto);

        return produto;
    }
}
