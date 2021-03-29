package com.app.Doceria.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.Entrada;
import com.app.Doceria.model.ItensEntrada;
import com.app.Doceria.model.Produto;
import com.app.Doceria.repository.EntradaRepository;
import com.app.Doceria.repository.FuncionarioRepository;
import com.app.Doceria.repository.ItensEntradaRepository;
import com.app.Doceria.repository.ProdutoRepository;

@Controller
public class EntradaController {

	private List<ItensEntrada> listaItens = new ArrayList<ItensEntrada>();

	@Autowired
	private EntradaRepository repositoryEntrada;

	@Autowired
	private ItensEntradaRepository repositoryItens;

	@Autowired
	public FuncionarioRepository repositoryFuncionario;

	@Autowired
	public ProdutoRepository repositoryProduto;

	@GetMapping("administrativo/entradas/cadastrar")
	public ModelAndView add(Entrada entrada, ItensEntrada itens) {
		ModelAndView mv = new ModelAndView("/administrativo/entradas/cadastro");
		mv.addObject("entrada", entrada);
		mv.addObject("listaItens", this.listaItens);
		mv.addObject("itensEntrada", itens);
		mv.addObject("funcionarios", repositoryFuncionario.findAll());
		mv.addObject("produtos", repositoryProduto.findAll());
		return mv;
	}

	@PostMapping("administrativo/entradas/salvarEntrada")
	public ModelAndView salvar(String acao, Entrada entrada, ItensEntrada itens) {
		if (acao.equals("itens")) {
			this.listaItens.add(itens);
		} else if (acao.equals("salvar")) {
			repositoryEntrada.saveAndFlush(entrada);
			for (ItensEntrada it : listaItens) {
				it.setEntrada(entrada);
				repositoryItens.saveAndFlush(it);
				Optional<Produto> prod = repositoryProduto.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValor(it.getValorVenda());
				repositoryProduto.saveAndFlush(produto);
				this.listaItens = new ArrayList<ItensEntrada>();
			}
			return add(new Entrada(), new ItensEntrada());
		}
		System.out.println(this.listaItens.size());
		return add(entrada, new ItensEntrada());
	}

}