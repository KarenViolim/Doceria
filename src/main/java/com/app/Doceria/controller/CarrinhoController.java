package com.app.Doceria.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.metamodel.SetAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.Cliente;
import com.app.Doceria.model.Compra;
import com.app.Doceria.model.FormaDePagamento;
import com.app.Doceria.model.ItensCompra;
import com.app.Doceria.model.Produto;
import com.app.Doceria.repository.ClienteRepository;
import com.app.Doceria.repository.CompraRepository;
import com.app.Doceria.repository.FormaPagamentoRepository;
import com.app.Doceria.repository.ItensCompraRepository;
import com.app.Doceria.repository.ProdutoRepository;

@Controller
public class CarrinhoController {

	@Autowired
	private ProdutoRepository repositoryProduto;
	
	@Autowired
	private ClienteRepository repositoryCliente;

	@Autowired
	private FormaPagamentoRepository repositoryFormaPg;
	
	@Autowired
	private CompraRepository repositoryCompra;
	
	@Autowired
	private ItensCompraRepository repositoryItens;
	

	private List<ItensCompra> ItensCompra = new ArrayList<ItensCompra>();
	private Compra compra = new Compra();
	private Cliente cliente;
	
	private void calcularTotal() {
		compra.setValorTotal(0.);
		for(ItensCompra it: ItensCompra) {
			compra.setValorTotal(compra.getValorTotal()+it.getValorTotal());
		}
	}
	
	@GetMapping("/cart")
	public ModelAndView carrinho() {
		ModelAndView mv = new ModelAndView("clientes/cart");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", ItensCompra);
		return mv;
	}
	
	@GetMapping("/adicionarCart/{id}")
	public String addcarrinho(@PathVariable Long id) {
		Optional<Produto> prod = repositoryProduto.findById(id);
		Produto produto = prod.get();

		if (verificaEstoque(produto)) {
			int controle = 0;
			for (ItensCompra it : ItensCompra) {
				if (it.getProduto().getId() == produto.getId()) {
					it.setQuantidade(it.getQuantidade() + 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal()+(it.getQuantidade() * it.getValorUnitario()));
					controle = 1;
					break;
				}
			}

			if (controle == 0) {
				ItensCompra itens = new ItensCompra();
				itens.setProduto(produto);
				itens.setValorUnitario(produto.getValor());
				itens.setQuantidade(itens.getQuantidade() + 1);
				itens.setValorTotal(itens.getValorTotal()+(itens.getQuantidade() * itens.getValorUnitario()));
				ItensCompra.add(itens);
			}
			
			return "redirect:/cart";
			
		} else {
			System.out.println("ESTOQUE INSUFICIENTE!");
			return "redirect:/";
		}
		
	}
	
	public Boolean verificaEstoque(Produto produtoAux) {
		Boolean validador = true;
		Boolean flag = false;
		
		for (ItensCompra it : ItensCompra) {
			if (it.getProduto().getId() == produtoAux.getId()) {
			    flag = true;
				
			    if (produtoAux.getQuantidadeEstoque() < (it.getQuantidade() + 1)){
					validador = false;
					
				}
				
				break;
			}
		}
		
		if (flag == Boolean.FALSE) {
			if (produtoAux.getQuantidadeEstoque() < (1)) {
					validador = false;
					
			}
		}
		
		return validador;
	}
	
	@GetMapping("/alterarQuantidade/{id}/{acao}")
	public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
		
		for (ItensCompra it : ItensCompra) {
			if (it.getProduto().getId() == (id)) {
				if(acao.equals(1)) {
					if (verificaEstoque(it.getProduto())) {
						it.setQuantidade(it.getQuantidade()+1);
						it.setValorTotal(0.);
						it.setValorTotal(it.getValorTotal()+(it.getQuantidade() * it.getValorUnitario()));
					} else {
						System.out.println("ESTOQUE INSUFICIENTE!");
					}
				}else if (acao == 0){
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal()+(it.getQuantidade() * it.getValorUnitario()));
				}
				break;
			}
		}
		
		return "redirect:/cart";
	}
	
	@GetMapping("/calcularvalor/{id}/{quantidade}")
	public String calcularvalor(@PathVariable Long id, @PathVariable Integer quantidade){
		compra.setValorTotal(0.);
				
		for (ItensCompra it : ItensCompra) {
			if (it.getProduto().getId() == (id)) {
					it.setQuantidade(quantidade);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal()+(it.getQuantidade() * it.getValorUnitario()));
					compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
			} else {
				compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
			}
		}
		return "redirect:/cart";
	}
	
	@GetMapping("/removerProduto/{id}")
	public String removerProdutoCarrinho(@PathVariable Long id) {
		
		for (ItensCompra it : ItensCompra) {
			if (it.getProduto().getId() == (id)) {
				ItensCompra.remove(it);
				break;
			}
		}
		
		return "redirect:/cart";
	}
	
	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if(!(autenticado instanceof AnonymousAuthenticationToken)) {
			String email = autenticado.getName();
			cliente = repositoryCliente.buscarClienteEmail(email).get(0);
		}
	}
	
	@GetMapping("/finalizar")
	public ModelAndView finalizarCompra() {
		buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("clientes/finalizar");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", ItensCompra);
		mv.addObject("cliente", cliente);
		mv.addObject("formapg", repositoryFormaPg.findAll());
		return mv;
	}
	
	@PostMapping("/finalizar/confirmar")
	public ModelAndView confirmarCompra(Long idForma) {
		
		Optional<FormaDePagamento> op = repositoryFormaPg.findById(idForma);
		FormaDePagamento formaPagamento = op.get();
		ModelAndView mv = new ModelAndView("clientes/finalizou");
		compra.setFormaPagamento(formaPagamento);
		compra.setCliente(cliente);
		repositoryCompra.saveAndFlush(compra);
		
		for (ItensCompra c : ItensCompra) {
			c.setCompra(compra);
			Optional<Produto> prod = repositoryProduto.findById(c.getProduto().getId());
			Produto produto = prod.get();
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - c.getQuantidade());
			repositoryProduto.saveAndFlush(produto);
			repositoryItens.saveAndFlush(c);
		}
		ItensCompra = new ArrayList<>();
		compra = new Compra();
		
		return mv;
	}
}
