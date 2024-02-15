package com.teste.java_1st_api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.java_1st_api.model.Produto;
import com.teste.java_1st_api.model.exception.ResourceNotFoundException;
import com.teste.java_1st_api.repository.ProdutoRepository;
import com.teste.java_1st_api.repository.ProdutoRepository_old;
import com.teste.java_1st_api.shared.ProdutoDTO;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> getProdutcs(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
        .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class)).
        collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> getProductById(Integer id){
        Optional<Produto> produto = produtoRepository.findById(id);

        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        ProdutoDTO produtoDTO = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        return Optional.of(produtoDTO);
    }

    public ProdutoDTO addProduct(ProdutoDTO produtoDTO){

        Produto produto = new ModelMapper().map(produtoDTO, Produto.class);

        produto = produtoRepository.save(produto);

        produtoDTO.setId(produto.getId());

        return produtoDTO;
    }

    public void delete(Integer id){
        Optional<Produto> produto = produtoRepository.findById(id);
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO update(Integer id, ProdutoDTO produtoDTO){
        
        produtoDTO.setId(id);

        ModelMapper mapper = new ModelMapper();

        Produto produto = mapper.map(produtoDTO, Produto.class);

        produto = produtoRepository.save(produto);

        return produtoDTO;
    }
}
