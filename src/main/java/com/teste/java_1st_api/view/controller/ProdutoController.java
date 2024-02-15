package com.teste.java_1st_api.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.java_1st_api.model.Produto;
import com.teste.java_1st_api.services.ProdutoService;
import com.teste.java_1st_api.shared.ProdutoDTO;
import com.teste.java_1st_api.view.model.ProdutoRequest;
import com.teste.java_1st_api.view.model.ProdutoResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/products")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAll(){
        List<ProdutoDTO> produtos = produtoService.getProdutcs();

        ModelMapper modelMapper = new ModelMapper();

        List<ProdutoResponse> response = produtos.stream().
        map(produtoDTo -> modelMapper.map(produtoDTo, ProdutoResponse.class)).
        collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> getById(@PathVariable Integer id) {
        
        Optional<ProdutoDTO> dto =  produtoService.getProductById(id);

        ProdutoResponse produto = new ModelMapper().map(dto.get(), ProdutoResponse.class);

        return new ResponseEntity<>(Optional.of(produto), HttpStatus.OK);

        
    }
    
    @PostMapping
    public ResponseEntity<ProdutoResponse> addProduct(@RequestBody ProdutoRequest produtoReq){
        
        ModelMapper modelMapper = new ModelMapper();

        ProdutoDTO dto = modelMapper.map(produtoReq, ProdutoDTO.class);

        dto = produtoService.addProduct(dto);

        return new ResponseEntity<>(modelMapper.map(dto, ProdutoResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        produtoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable Integer id, @RequestBody ProdutoRequest produtoRequest){
        ModelMapper modelMapper = new ModelMapper();

        ProdutoDTO produtoDto = modelMapper.map(produtoRequest, ProdutoDTO.class);

        produtoDto = produtoService.update(id, produtoDto);

        return new ResponseEntity<>(
            modelMapper.map(produtoDto, ProdutoResponse.class), 
            HttpStatus.OK);
    }
}   
