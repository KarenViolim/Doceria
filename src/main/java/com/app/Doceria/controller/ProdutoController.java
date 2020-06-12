package com.app.Doceria.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.Produto;
import com.app.Doceria.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	private static String caminhoImagens = "C:\\Users\\Karen Violim\\Pictures\\imagens\\";

	@Autowired
	public ProdutoRepository repositoryProduto;

	@GetMapping("administrativo/produtos/cadastrar")
	public ModelAndView add(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("administrativo/produtos/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
		mv.addObject("produto", repositoryProduto.findAll());
		return mv;
	}

	@GetMapping("administrativo/produtos/editarProduto/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		Optional<Produto> op = repositoryProduto.findById(id);
		Produto prod = op.get();
		prod.setQuantidadeEstoque(prod.getQuantidadeEstoque());
		prod.setImagem(prod.getImagem());
		return add(prod);
	}

	@GetMapping("administrativo/produtos/removerProduto/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
		Optional<Produto> op = repositoryProduto.findById(id);
		Produto prod = op.get();
		repositoryProduto.delete(prod);
		return listar();
	}

	@GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {

			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

	@PostMapping("/administrativo/produtos/salvarProduto")
	public ModelAndView save(@Valid Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {

		if (result.hasErrors()) {
			return add(produto);
		}
		repositoryProduto.saveAndFlush(produto);
		try {
			// se for diferente do conteúdo vázio, pegar conteúdo em um array de bytes
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths
						.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				produto.setImagem(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				repositoryProduto.saveAndFlush(produto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listar();
	}
}
