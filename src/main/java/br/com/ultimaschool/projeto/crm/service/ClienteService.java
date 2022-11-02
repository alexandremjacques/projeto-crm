package br.com.ultimaschool.projeto.crm.service;

import br.com.ultimaschool.projeto.crm.repository.Cliente;
import br.com.ultimaschool.projeto.crm.repository.ClienteRepository;
import br.com.ultimaschool.projeto.crm.service.erros.ClienteNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente retornaCliente(Integer id) throws ClienteNaoEncontradoException {
        Cliente cliente;

        cliente = clienteRepository.leClientePorId(id);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com id: " + id);
        }

        return cliente;
    }

    public void removeCliente(Integer id) throws ClienteNaoEncontradoException {
        int clientesApagados = clienteRepository.apagaClientePorId(id);

        if (clientesApagados == 0) {
            throw new ClienteNaoEncontradoException("Cliente com id: " + id + " não encontrado para exclusão");
        }
    }

    public void alteraDadosCliente(Cliente novosDadosCliente) throws ClienteNaoEncontradoException {
        int clientesAtualizados = clienteRepository.alteraClientePorId(novosDadosCliente.getId(), novosDadosCliente.getNome(), novosDadosCliente.getEndereco(), novosDadosCliente.getTelefone(),
                novosDadosCliente.getSalario(), novosDadosCliente.getNrFilhos(), novosDadosCliente.getDataCadastro());

        if (clientesAtualizados == 0) {
            throw new ClienteNaoEncontradoException("Cliente com id: " + novosDadosCliente.getId() + " não encontrado para atualização");
        }
    }

    public void insereCliente(Cliente novoCliente) {
        clienteRepository.escreveCliente(novoCliente.getNome(), novoCliente.getEndereco(), novoCliente.getTelefone(),
                novoCliente.getSalario(), novoCliente.getNrFilhos(), novoCliente.getDataCadastro());
    }
}
