package com.app.Doceria.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.Doceria.model.Cliente;
import com.app.Doceria.model.FormaDePagamento;
import com.app.Doceria.model.ItensVenda;
import com.app.Doceria.model.Produto;
import com.app.Doceria.model.Venda;
import com.app.Doceria.repository.ClienteRepository;
import com.app.Doceria.repository.FormaPagamentoRepository;
import com.app.Doceria.repository.ItensVendaRepository;
import com.app.Doceria.repository.ProdutoRepository;
import com.app.Doceria.repository.VendaRepository;

@Controller
public class CarrinhoController {

	@Autowired
	private ProdutoRepository repositoryProduto;
	
	@Autowired
	private ClienteRepository repositoryCliente;

	@Autowired
	private FormaPagamentoRepository repositoryFormaPg;
	
	@Autowired
	private VendaRepository repositoryVenda;
	
	@Autowired
	private ItensVendaRepository repositoryItens;
	
	private String mensagemQuantidade = " ";
	private List<ItensVenda> ItensVenda = new ArrayList<ItensVenda>();
	private Venda venda = new Venda();
	private Cliente cliente;
	
	private void calcularTotal() {
		venda.setValorTotal(0.);
		for(ItensVenda it: ItensVenda) {
			venda.setValorTotal(venda.getValorTotal()+it.getValorTotal());
		}
	}
	
	@GetMapping("/cart")
	public ModelAndView carrinho() {
		ModelAndView mv = new ModelAndView("clientes/cart");
		calcularTotal();
		mv.addObject("venda", venda);
		mv.addObject("listaItens", ItensVenda);
		return mv;
	}
	
	@GetMapping("/adicionarCart/{id}")
	public String addcarrinho(@PathVariable Long id) {
		Optional<Produto> prod = repositoryProduto.findById(id);
		Produto produto = prod.get();

		if (verificaEstoque(produto)) {
			int controle = 0;
			for (ItensVenda iv : ItensVenda) {
				if (iv.getProduto().getId() == produto.getId()) {
					iv.setQuantidade(iv.getQuantidade() + 1);
					iv.setValorTotal(0.);
					iv.setValorTotal(iv.getValorTotal()+(iv.getQuantidade() * iv.getValorUnitario()));
					controle = 1;
					break;
				}
			}

			if (controle == 0) {
				ItensVenda itens = new ItensVenda();
				itens.setProduto(produto);
				itens.setValorUnitario(produto.getValor());
				itens.setQuantidade(itens.getQuantidade() + 1);
				itens.setValorTotal(itens.getValorTotal()+(itens.getQuantidade() * itens.getValorUnitario()));
				ItensVenda.add(itens);
			}
			
			return "redirect:/cart";
			
		} else {
			mensagemQuantidade = "ESTOQUE INSUFICIENTE!";
			return "redirect:/";
		}
		
	}
	
	public Boolean verificaEstoque(Produto produtoAux) {
		Boolean validador = true;
		Boolean flag = false;
		
		for (ItensVenda iv : ItensVenda) {
			if (iv.getProduto().getId() == produtoAux.getId()) {
			    flag = true;
				
			    if (produtoAux.getQuantidadeEstoque() < (iv.getQuantidade() + 1)){
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
	public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao, Model model) {
		
		for (ItensVenda iv : ItensVenda) {
			if (iv.getProduto().getId() == (id)) {
				if(acao.equals(1)) {
					if (verificaEstoque(iv.getProduto())) {
						iv.setQuantidade(iv.getQuantidade()+1);
						iv.setValorTotal(0.);
						iv.setValorTotal(iv.getValorTotal()+(iv.getQuantidade() * iv.getValorUnitario()));
					} else {
						System.out.println("ESTOQUE INSUFICIENTE");
						model.addAttribute("msg", "ESTOQUE INSUFICIENTE");
//						ModelAndView mv = new ModelAndView(mensagemQuantidade);
//						mv.addObject("mensagem", "ESTOQUE INSUFICIENTE");
//						RequestContext.getCurrentInstance().execute("openAlert();");
					}
				}else if (acao == 0){
					iv.setQuantidade(iv.getQuantidade() - 1);
					iv.setValorTotal(0.);
					iv.setValorTotal(iv.getValorTotal()+(iv.getQuantidade() * iv.getValorUnitario()));
				}
				break;
			}
		}
		
		return "redirect:/cart";
	}
	
	@GetMapping("/calcularvalor/{id}/{quantidade}")
	public String calcularvalor(@PathVariable Long id, @PathVariable Integer quantidade){
		venda.setValorTotal(0.);
				
		for (ItensVenda iv : ItensVenda) {
			if (iv.getProduto().getId() == (id)) {
					iv.setQuantidade(quantidade);
					iv.setValorTotal(0.);
					iv.setValorTotal(iv.getValorTotal()+(iv.getQuantidade() * iv.getValorUnitario()));
//					iv.setValorTotal(getValorFormatado(iv.getValorTotal());
					venda.setValorTotal(venda.getValorTotal() + iv.getValorTotal());
			} else {
				venda.setValorTotal(venda.getValorTotal() + iv.getValorTotal());
			}
		}
		return "redirect:/cart";
	}
	
	private Double getValorFormatado(Double valorAux) {
		DecimalFormat formato = new DecimalFormat("#.##");      
		return Double.valueOf(formato.format(valorAux));
	}
	
	@GetMapping("/removerProduto/{id}")
	public String removerProdutoCarrinho(@PathVariable Long id) {
		
		for (ItensVenda iv : ItensVenda) {
			if (iv.getProduto().getId() == (id)) {
				ItensVenda.remove(iv);
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
	public ModelAndView finalizarVenda() {
		buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("clientes/finalizar");
		calcularTotal();
		mv.addObject("venda", venda);
		mv.addObject("listaItens", ItensVenda);
		mv.addObject("cliente", cliente);
		mv.addObject("formapg", repositoryFormaPg.findAll());
		return mv;
	}
	
//	@PostMapping("/alterarQuantidade")
//	public ModelAndView alterarQuantidade() {
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("mensagem", mensagemQuantidade);
//		return mv;
//	}
	
	@PostMapping("/finalizar/confirmar")
	public ModelAndView confirmarCompra(Long idForma) {
		
		Optional<FormaDePagamento> op = repositoryFormaPg.findById(idForma);
		FormaDePagamento formaPagamento = op.get();
		ModelAndView mv = new ModelAndView("clientes/finalizou");
		venda.setFormaPagamento(formaPagamento);
		venda.setCliente(cliente);
		repositoryVenda.saveAndFlush(venda);
	
		for (ItensVenda v : ItensVenda) {
			v.setVenda(venda);
			Optional<Produto> prod = repositoryProduto.findById(v.getProduto().getId());
			Produto produto = prod.get();
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - v.getQuantidade());
			repositoryProduto.saveAndFlush(produto);
			repositoryItens.saveAndFlush(v);
		}
		ItensVenda = new ArrayList<>();
		venda = new Venda();
		
		return mv;
	}
}
