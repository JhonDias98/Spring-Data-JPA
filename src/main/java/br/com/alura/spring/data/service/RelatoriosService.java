package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	

	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual acao de relatorio deseja executar");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionario pelo o nome");
			System.out.println("2 - Busca funcionario pelo o nome, salario e data");
			System.out.println("3 - Busca funcionario pela data contratacao");
			System.out.println("4 - Busca funcionario salario");
			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				buscaFuncionario(scanner);
				break;
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioDataContratacao(scanner);
				break;
			case 4:
				buscaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}		
		}	
	}
	
	private void buscaFuncionario(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar:");
		String name = scanner.next();
		List<Funcionario> list = funcionarioRepository.findByNome(name);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar:");
		String name = scanner.next();
		
		System.out.println("Qual data contratacao deseja pesquisar:");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		System.out.println("Qual salario deseja pesquisar:");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(name, salario, localDate);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		System.out.println("Qual data contratacao deseja pesquisar:");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);

		List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(localDate);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionario: id: " + f.getId()
		+ " | nome: " + f.getNome() + " | salario: " + f.getSalario()));
	}
}
